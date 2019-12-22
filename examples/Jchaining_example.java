package examples;

import java.io.IOException;
import java.io.InputStream;

import libvorbis.JOggVorbis_File;
import libvorbis.Jvorbis_info;

/** function: illustrate simple use of chained bitstream and JOggVorbis_File */

public final class Jchaining_example {
	@SuppressWarnings("boxing")
	public static void main(final String[] args) {
		if( args.length != 1 ) {
			System.out.println("Usage:");
			System.out.println("java -jar OggVorbis_chaining_example.jar <Input File[.ogg]>");
			System.exit( 0 );
			return;
		}
		final JOggVorbis_File ov = new JOggVorbis_File();
		int i;
		InputStream ins = null;// System.in
		try {
			//ins = new java.io.FileInputStream( args[0] );/* not seekable InputStream  */
			ins = JOggVorbis_File.ov_open( args[0] );/* get seekable InputStream */

			/* open the file/pipe on stdin */
			if( ov.ov_open_callbacks( ins, null, -1, JOggVorbis_File.OV_CALLBACKS_NOCLOSE ) < 0 ) {
				System.out.print("Could not open input as an OggVorbis file.\n\n");
				System.exit( 1 );// FIXME exit without free resources
			}

			/* print details about each logical bitstream in the input */
			if( ov.ov_seekable() ) {
				System.out.printf("Input bitstream contained %d logical bitstream section(s).\n",
							ov.ov_streams());
				System.out.printf("Total bitstream samples: %d\n\n",
							ov.ov_pcm_total( -1 ));
				System.out.printf("Total bitstream playing time: %d seconds\n\n",
							(long)ov.ov_time_total( -1 ));

			} else {
				System.out.print("Standard input was not seekable.\nFirst logical bitstream information:\n\n");
			}

			for( i = 0; i < ov.ov_streams(); i++ ) {
				final Jvorbis_info vi = ov.ov_info( i );
				System.out.printf("\tlogical bitstream section %d information:\n", i + 1);
				System.out.printf("\t\t%dHz %d channels bitrate %dkbps serial number=%d\n",
						vi.rate, vi.channels, ov.ov_bitrate( i ) / 1000,
						ov.ov_serialnumber( i ));
				System.out.printf("\t\theader length: %d bytes\n", (ov.dataoffsets[i] - ov.offsets[i]));
				System.out.printf("\t\tcompressed length: %d bytes\n", (ov.ov_raw_total( i )));
				System.out.printf("\t\tplay time: %ds\n", (long) ov.ov_time_total( i ));
			}
			ov.ov_clear();
			System.exit( 0 );
			return;
		} catch(final Exception e) {
			System.err.println( e.getMessage() );
			e.printStackTrace();
		} finally {
			if( ins != null ) {
				try{ ins.close(); } catch(final IOException e) {}
			}
		}
		ov.ov_clear();
		System.exit( 1 );
		return;
	}
}
