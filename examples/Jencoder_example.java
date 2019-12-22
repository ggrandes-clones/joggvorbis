package examples;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;

import libogg.Jogg_packet;
import libogg.Jogg_page;
import libogg.Jogg_stream_state;
import libvorbis.Jvorbis_block;
import libvorbis.Jvorbis_comment;
import libvorbis.Jvorbis_dsp_state;
import libvorbis.Jvorbis_info;
import libvorbis.Jvorbis_pcm;

/** function: simple example encoder */

/**
 * takes a stereo 16bit 44.1kHz WAV file from an input file and encodes it into
 * a Vorbis output file
 */
public final class Jencoder_example {
	private static final int READ = 1024;
	private static byte readbuffer[] = new byte[READ * 4 + 44]; /* out of the data segment, not the stack */
	//
	public static void main(String[] args) {
		if( args.length != 2 ) {
			System.out.println("Usage:");
			System.out.println("java -jar OggVorbis_encoder_example.jar <Input File[.wav]> <Output File[.ogg]>");
			System.exit( 0 );
			return;
		}
		//
		final Jogg_stream_state os = new Jogg_stream_state(); /* take physical pages, weld into a logical
							  								stream of packets */
		final Jogg_page         og = new Jogg_page(); /* one Ogg bitstream page.  Vorbis packets are inside */
		final Jogg_packet       op = new Jogg_packet(); /* one raw packet of data for decode */

		final Jvorbis_info      vi = new Jvorbis_info(); /* struct that stores all the static vorbis bitstream
							  							settings */
		final Jvorbis_comment   vc = new Jvorbis_comment(); /* struct that stores all the user comments */

		final Jvorbis_dsp_state vd = new Jvorbis_dsp_state(); /* central working state for the packet->PCM decoder */
		final Jvorbis_block     vb = new Jvorbis_block(); /* local working space for packet->PCM decode */

		boolean eos = false;//, founddata; FIXME founddata not used
		int i, ret;

		InputStream ins = null;// System.in;
		OutputStream outs = null;// System.out;
		/* we cheat on the WAV header; we just bypass 44 bytes (simplest WAV
		 header is 44 bytes) and assume that the data is 44.1khz, stereo, 16 bit
		 little endian pcm samples. This is just an example, after all. */

		/* we cheat on the WAV header; we just bypass the header and never
		 verify that it matches 16bit/stereo/44.1kHz.  This is just an
		 example, after all. */

		readbuffer[0] = 0;
		try {
			ins = new FileInputStream( args[0] );
			outs = new FileOutputStream( args[1] );

			for( i = 0/*, founddata = false*/; i < 30; i++ ) {
				ins.read( readbuffer, 0, 2 );

				if( readbuffer[0] == 'd' && readbuffer[1] == 'a' ) {
					/*founddata = true;*/
					ins.read( readbuffer, 0, 6 );
					break;
				}
			}

			/********** Encode setup ************/

			vi.vorbis_info_init();

			/* choose an encoding mode.  A few possibilities commented out, one
			 actually used: */

			/*********************************************************************
			Encoding using a VBR quality mode.  The usable range is -.1
			(lowest quality, smallest file) to 1. (highest quality, largest file).
			Example quality mode .4: 44kHz stereo coupled, roughly 128kbps VBR

			ret = vorbis_encode_init_vbr(&vi,2,44100,.4);

			---------------------------------------------------------------------

			Encoding using an average bitrate mode (ABR).
			example: 44kHz stereo coupled, average 128kbps VBR

			ret = vorbis_encode_init(&vi,2,44100,-1,128000,-1);

			---------------------------------------------------------------------

			Encode using a quality mode, but select that quality mode by asking for
			an approximate bitrate.  This is not ABR, it is true VBR, but selected
			using the bitrate interface, and then turning bitrate management off:

			ret = ( vorbis_encode_setup_managed(&vi,2,44100,-1,128000,-1) ||
				   vorbis_encode_ctl(&vi,OV_ECTL_RATEMANAGE2_SET,NULL) ||
				   vorbis_encode_setup_init(&vi));

			*********************************************************************/

			ret = vi.vorbis_encode_init_vbr( 2, 44100, 0.1f );

			/* do not continue if setup failed; this can happen if we ask for a
			 mode that libVorbis does not support (eg, too low a bitrate, etc,
			 will return 'OV_EIMPL') */

			if( ret != 0 ) System.exit( 1 );// FIXME exit without free resources

			/* add a comment */
			vc.vorbis_comment_init();
			vc.vorbis_comment_add_tag( "ENCODER", "encoder_example.c" );

			/* set up the analysis state and auxiliary encoding storage */
			vd.vorbis_analysis_init( vi );
			vd.vorbis_block_init( vb );

			/* set up our packet->stream encoder */
			/* pick a random serial number; that way we can more likely build
			 chained streams just by concatenation */
			os.ogg_stream_init( new Random( System.currentTimeMillis() ).nextInt( Integer.MAX_VALUE ) );

			/* Vorbis streams begin with three headers; the initial header (with
			 most of the codec setup parameters) which is mandated by the Ogg
			 bitstream spec.  The second header holds any comment fields.  The
			 third header holds the bitstream codebook.  We merely need to
			 make the headers, then pass them to libvorbis one at a time;
			 libvorbis handles the additional Ogg bitstream constraints */

			{
				final Jogg_packet header = new Jogg_packet();
				final Jogg_packet header_comm = new Jogg_packet();
				final Jogg_packet header_code = new Jogg_packet();

				vd.vorbis_analysis_headerout( vc, header, header_comm, header_code );
				os.ogg_stream_packetin( header ); /* automatically placed in its own page */
				os.ogg_stream_packetin( header_comm );
				os.ogg_stream_packetin( header_code );

				/* This ensures the actual
				 * audio data will start on a new page, as per spec
				 */
				while( ! eos ) {// FIXME eos not changed?
					final int result = os.ogg_stream_flush( og );
					if( result == 0 ) break;
					outs.write( og.header_base, og.header, og.header_len );
					outs.write( og.body_base, og.body, og.body_len );
				}

			}

			while( ! eos ) {
				final int bytes = ins.read( readbuffer, 0, READ * 4 ); /* stereo hardwired here */

				if( bytes <= 0 ) {
					/* end of file.  this can be done implicitly in the mainline,
					 but it's easier to see here in non-clever fashion.
					 Tell the library we're at end of stream so that it can handle
					 the last frame and mark end of stream in the output properly */
					vd.vorbis_analysis_wrote( 0 );

				} else {
					/* data to encode */

					/* expose the buffer to submit data */
					final Jvorbis_pcm vpcm = vd.vorbis_analysis_buffer( READ );
					final float[][] pcm = vpcm.pcm;
					int offset = vpcm.pcmret;

					/* uninterleave samples */
					for( i = 0; i < (bytes >>> 2); i++, offset++ ) {
						final int i4 = i << 2;
						pcm[0][offset] = ((float)(((int)readbuffer[i4 + 1] << 8) |
								(0x00ff & (int)readbuffer[i4]))) / 32768.f;
						pcm[1][offset] = ((float)(((int)readbuffer[i4 + 3] << 8) |
								(0x00ff & (int)readbuffer[i4 + 2]))) / 32768.f;
					}

					/* tell the library how much we actually submitted */
					vd.vorbis_analysis_wrote( i );
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
						while( ! eos ) {
							final int result = os.ogg_stream_pageout( og );
							if( result == 0 ) break;
							outs.write( og.header_base, og.header, og.header_len );
							outs.write( og.body_base, og.body, og.body_len );

							/* this could be set above, but for illustrative purposes, I do
							 it here (to show that vorbis does know where the stream ends) */

							if( og.ogg_page_eos() ) eos = true;
						}
					}
				}
			}

			/* clean up and exit.  vorbis_info_clear() must be called last */
			/* moved to finally to fix original example cleanup bug
			os.ogg_stream_clear();
			vb.vorbis_block_clear();
			vd.vorbis_dsp_clear();
			vc.vorbis_comment_clear();
			vi.vorbis_info_clear();*/

			/* ogg_page and ogg_packet structs always point to storage in
			 libvorbis.  They're never freed or manipulated directly */

			System.err.print("Done.\n");
			System.exit( 0 );
			return;
		} catch(Exception e) {
			System.err.println( e.getMessage() );
			e.printStackTrace();
		} finally {
			if( ins != null ) try { ins.close(); } catch( IOException e ) {}
			if( outs != null ) try { outs.close(); } catch( IOException e ) {}
			/* clean up and exit.  vorbis_info_clear() must be called last */

			os.ogg_stream_clear();
			vb.vorbis_block_clear();
			vd.vorbis_dsp_clear();
			vc.vorbis_comment_clear();
			vi.vorbis_info_clear();
		}
		System.exit( 1 );
		return;
	}
}

