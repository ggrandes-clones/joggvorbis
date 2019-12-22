package examples;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import libvorbis.JOggVorbis_File;
import libvorbis.Jcodec;
import libvorbis.Jvorbis_info;
import libvorbis.Jvorbis_pcm;

/** simple example decoder using JOggVorbis_File */

/**
 * Takes a vorbis bitstream from an input file and writes raw stereo PCM to
 * an output file using JOggVorbis_File. Using JOggVorbis_File is much simpler than
 * dealing with libvorbis.
 */
public final class Jvorbisfile_example {
	private static final byte pcmout[] = new byte[4096]; /* take 4k out of the data segment, not the stack */

	@SuppressWarnings("boxing")
	public static void main(final String[] args) {
		if( args.length != 2 ) {
			System.out.println("Usage:");
			System.out.println("java -jar OggVorbisFile_example.jar <Input File[.ogg]> <Output File[.raw]>");
			System.exit( 0 );
			return;
		}
		final JOggVorbis_File vf = new JOggVorbis_File();
		boolean eof = false;
		//int current_section;// java: returns in Jvorbis_pcm.data

		InputStream ins = null;// System.in
		OutputStream outs = null;// System.out
		try {
			//ins = new java.io.FileInputStream( args[0] );/* not seekable InputStream  */
			ins = JOggVorbis_File.ov_open( args[0] );/* get seekable InputStream */
			outs = new FileOutputStream( args[1] );
			if( vf.ov_open_callbacks( ins, null, 0, JOggVorbis_File.OV_CALLBACKS_NOCLOSE ) < 0 ) {
				System.err.print("Input does not appear to be an Ogg bitstream.\n");
				vf.ov_clear();// added to free resources
				System.exit( 1 );// FIXME exit without free resources
				return;
			}

			/* Throw the comments plus a few lines about the bitstream we're
			 decoding */
			{
				final String[] comments = vf.ov_comment( -1 ).user_comments;
				final Jvorbis_info vi = vf.ov_info( -1 );
				for( int i = 0; i < comments.length; i++ ) {
					System.err.println( comments[i] );
				}
				System.err.printf("\nBitstream is %d channel, %dHz\n", vi.channels, vi.rate);
				System.err.printf("\nDecoded length: %d samples\n", vf.ov_pcm_total( -1 ));
				System.err.printf("Encoded by: %s\n\n", vf.ov_comment( -1 ).vendor);
			}

			while( ! eof ) {
				final Jvorbis_pcm vpcm = vf.ov_read( pcmout, 0, pcmout.length, false, 2, true );
				if( vpcm == null || vpcm.samples < 0 ) {
					if( vpcm == null || vpcm.samples == Jcodec.OV_EBADLINK ) {
						System.err.print("Corrupt bitstream section! Exiting.\n");
						vf.ov_clear();// added to free resources
						System.exit( 1 );// FIXME exit without free resources
						return;
					}

					/* some other error in the stream.  Not a problem, just reporting it in
					 case we (the app) cares.  In this case, we don't. */
				} else if( vpcm.samples == 0 ) {
					/* EOF */
					eof = true;
				} else {
					//current_section = pcm_data.data;
					/* we don't bother dealing with sample rate changes, etc, but
					 you'll have to*/
					outs.write( pcmout, 0, vpcm.samples );
				}
			}
		} catch(final Exception e) {
			System.err.println( e.getMessage() );
			e.printStackTrace();
		} finally {
			if( ins != null ) {
				try{ ins.close(); } catch(final IOException e) {}
			}
			if( outs != null ) {
				try{ outs.close(); } catch(final IOException e) {}
			}
		}

		/* cleanup */
		vf.ov_clear();

		System.err.print("Done.\n");
		System.exit( 0 );
		return;
	}
}

