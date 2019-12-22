package spi.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.sound.sampled.AudioFileFormat.Type;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.spi.AudioFileWriter;

import libogg.Jogg_packet;
import libogg.Jogg_page;
import libogg.Jogg_stream_state;
import libvorbis.Jvorbis_block;
import libvorbis.Jvorbis_comment;
import libvorbis.Jvorbis_dsp_state;
import libvorbis.Jvorbis_info;
import libvorbis.Jvorbis_pcm;

//TODO what must return getSourceEncodings(), getAudioFileTypes() ?
public final class OggVorbisAudioFileWriter extends AudioFileWriter {
	private final Type[] OGGVORBIS = { new Type("OggVorbis", "ogg") };
	private static final float QUALITY = 1.0f;

	@Override
	public Type[] getAudioFileTypes() {
		return OGGVORBIS;
	}

	@Override
	public Type[] getAudioFileTypes(final AudioInputStream stream) {
		return OGGVORBIS;
	}

	@Override
	public int write(final AudioInputStream stream, final Type fileType, final OutputStream out)
			throws IOException {
		if( ! fileType.equals( OGGVORBIS[0] ) ) {
			throw new IllegalArgumentException();
		}

		final int channels = stream.getFormat().getChannels();
		final int channels2 = channels << 1;// 2 bytes per sample
		final boolean is_big_endian = stream.getFormat().isBigEndian();

		final byte readbuffer[] = new byte[4096];
		final int max_samples_count = readbuffer.length / channels2;// 2 bytes per sample
		int written = 0;
		boolean eos = false;
		final Jogg_stream_state os = new Jogg_stream_state();
		final Jogg_page         og = new Jogg_page();
		final Jogg_packet       op = new Jogg_packet();
		final Jvorbis_info      vi = new Jvorbis_info();
		final Jvorbis_comment   vc = new Jvorbis_comment();
		final Jvorbis_dsp_state vd = new Jvorbis_dsp_state();
		final Jvorbis_block     vb = new Jvorbis_block();
		//
		vi.vorbis_info_init();
		float quality = QUALITY;
		boolean is_vbr = true;
		if( fileType instanceof EncoderFileFormatType ) {// user input
			final int type = ((EncoderFileFormatType) fileType).mStreamType;
			if( type == EncoderFileFormatType.VBR ) {
				is_vbr = true;
			}
			if( type == EncoderFileFormatType.CBR ) {
				is_vbr = false;
			}
			if( type == EncoderFileFormatType.ABR ) {
				is_vbr = true;
			}
			quality = ((EncoderFileFormatType) fileType).mStreamTypeParameter;
		}
		if( is_vbr ) {
			if( vi.vorbis_encode_init_vbr( channels, (int)stream.getFormat().getSampleRate(), quality ) < 0 ) {
				throw new IOException("Can't init encoder");
			}
		} else {
			if( vi.vorbis_encode_init( channels, (int)stream.getFormat().getSampleRate(), -1, (int)(quality * 1000f), -1 ) < 0 ) {
				throw new IOException("Can't init encoder");
			}
		}
		// add a comment
		vc.vorbis_comment_init();
		vc.vorbis_comment_add_tag( "ENCODER", "OggVorbisAudioFileWriter" );
		// set up the analysis state and auxiliary encoding storage
		vd.vorbis_analysis_init( vi );
		vd.vorbis_block_init( vb );
		os.ogg_stream_init( (int)System.currentTimeMillis() & 0x7fffffff );
		try {
			{
				final Jogg_packet header = new Jogg_packet();
				final Jogg_packet header_comm = new Jogg_packet();
				final Jogg_packet header_code = new Jogg_packet();

				vd.vorbis_analysis_headerout( vc, header, header_comm, header_code );
				os.ogg_stream_packetin( header );
				os.ogg_stream_packetin( header_comm );
				os.ogg_stream_packetin( header_code );

				/* Ensures the audio data will start on a new page. */
				while( ! eos && os.ogg_stream_flush( og ) != 0 ) {
					out.write( og.header_base, og.header, og.header_len );
					out.write( og.body_base, og.body, og.body_len );
					written += og.header_len;
					written += og.body_len;
				}
			}
			while( ! eos ) {
				final int bytes = stream.read( readbuffer, 0, readbuffer.length );

				if( bytes <= 0 ) {
					/* end of file.  this can be done implicitly in the mainline,
					 but it's easier to see here in non-clever fashion.
					 Tell the library we're at end of stream so that it can handle
					 the last frame and mark end of stream in the output properly */
					vd.vorbis_analysis_wrote( 0 );

				} else {
					/* data to encode */

					/* expose the buffer to submit data */
					final Jvorbis_pcm vpcm = vd.vorbis_analysis_buffer( max_samples_count );
					final float[][] pcm = vpcm.pcm;
					final int offset = vpcm.pcmret;

					/* uninterleave samples */
					int i;
					if( is_big_endian ) {
						for( int c = 0; c < channels; c++ ) {
							final float[] p = pcm[c];
							int dest = offset;
							for( i = c << 1; i < bytes; i += channels2 ) {
								p[dest++] = ((float)(((int)readbuffer[i] << 8) |
										(0x00ff & (int)readbuffer[i + 1]))) / 32768.f;
							}
						}
					} else {
						for( int c = 0; c < channels; c++ ) {
							final float[] p = pcm[c];
							int dest = offset;
							for( i = c << 1; i < bytes; i += channels2 ) {
								p[dest++] = ((float)(((int)readbuffer[i + 1] << 8) |
										(0x00ff & (int)readbuffer[i]))) / 32768.f;
							}
						}
					}

					/* tell the library how much we actually submitted */
					vd.vorbis_analysis_wrote( bytes / channels2 );
				}

				/* vorbis does some data preanalysis, then divvies up blocks for
				   more involved (potentially parallel) processing.  Get a single
				   block for encoding now */
				while( vd.vorbis_analysis_blockout( vb ) ) {

					/* analysis, assume we want to use bitrate management */
					vb.vorbis_analysis( null );
					vb.vorbis_bitrate_addblock();

					while( vd.vorbis_bitrate_flushpacket( op ) ) {

						/* weld the packet into the bitstream */
						os.ogg_stream_packetin( op );

						/* write out pages (if any) */
						while( ! eos && os.ogg_stream_pageout( og ) != 0 ) {
							out.write( og.header_base, og.header, og.header_len );
							out.write( og.body_base, og.body, og.body_len );
							written += og.header_len;
							written += og.body_len;

							/* this could be set above, but for illustrative purposes, I do
							 it here (to show that vorbis does know where the stream ends) */

							if( og.ogg_page_eos() ) {
								eos = true;
							}
						}
					}
				}
			}
		} catch(final IOException e) {
			//e.printStackTrace();
			throw e;
		} finally {
			os.ogg_stream_clear();
			vb.vorbis_block_clear();
			vd.vorbis_dsp_clear();
			vc.vorbis_comment_clear();
			vi.vorbis_info_clear();
		}
		return written;
	}

	@Override
	public int write(final AudioInputStream stream, final Type fileType, final File file) throws IOException {
		if( ! fileType.equals( OGGVORBIS[0] ) ) {
			throw new IllegalArgumentException();
		}
		FileOutputStream outs = null;
		try {
			outs = new FileOutputStream( file );
			return write( stream, fileType, outs );
		} catch(final IOException e) {
			throw e;
		} finally {
			if( outs != null ) {
				try { outs.close(); } catch( final IOException e ) {}
			}
		}
	}
}
