package examples;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Random;

import libvorbis.JOggVorbis_File;
import libvorbis.Jvorbis_info;
import libvorbis.Jvorbis_pcm;

/** illustrate seeking, and test it too */

public final class Jseeking_example {
	@SuppressWarnings("boxing")
	private static void _verify(final JOggVorbis_File ov,
					final long val, final long pcmval, final double timeval,
					final long pcmlength,
					final byte[] bigassbuffer) {
		int i, j;
		final int bread;
		final byte buffer[] = new byte[4096];
		//int dummy;
		final long pos;
		final int hs = ov.ov_halfrate_p();

		/* verify the raw position, the pcm position and position decode */
		if( val != -1 && ov.ov_raw_tell() < val ) {
			System.err.printf("raw position out of tolerance: requested %d, got %d\n",
						val, ov.ov_raw_tell());
			ov.ov_clear();// added to free resources
			System.exit( 1 );// FIXME exit without free resources
			return;
		}
		if( pcmval != -1 && ov.ov_pcm_tell() > pcmval ) {
			System.err.printf("pcm position out of tolerance: requested %d, got %d\n",
						pcmval, ov.ov_pcm_tell() );
			ov.ov_clear();// added to free resources
			System.exit( 1 );// FIXME exit without free resources
			return;
		}
		if( timeval != -1 && ov.ov_time_tell() > timeval ) {
			System.err.printf("time position out of tolerance: requested %f, got %f\n",
						timeval, ov.ov_time_tell());
			ov.ov_clear();// added to free resources
			System.exit( 1 );// FIXME exit without free resources
			return;
		}
		pos = ov.ov_pcm_tell();
		if( pos < 0 || pos > pcmlength ) {
			System.err.printf("pcm position out of bounds: got %d\n", pos);
			ov.ov_clear();// added to free resources
			System.exit( 1 );// FIXME exit without free resources
			return;
		}
		final Jvorbis_pcm vpcm = ov.ov_read( buffer, 0, 4096, true, 1, true );
		if( vpcm != null ) {
			//dummy = pcm_data.data;
			bread = vpcm.samples;
			for( j = 0; j < bread; j++ ) {
				if( buffer[j] != bigassbuffer[j + (int)((pos >> hs) << 1)] ) {
					System.err.printf("data after seek doesn't match declared pcm position %d\n", pos);

					for( i = 0; i < ((int)(pcmlength >> hs) << 1) - bread; i++ ) {
						for( j = 0; j < bread; j++ ) {
							if( buffer[j] != bigassbuffer[i + j] ) {
								break;
							}
						}
						if( j == bread ) {
							System.err.printf("data after seek appears to match position %d\n", ((i >> 1) << hs));
						}
					}
					{
						PrintStream f = null;
						try {
							f = new PrintStream("a.m");
							for( j = 0; j < bread; j++ ) {
								f.printf("%d %d\n", j, (int)buffer[j] );
							}
							f.close();
							f = new PrintStream("b.m", "w");
							for( j = -4096; j < bread + 4096; j++ ) {
								if( j + (int)((pos << 1) >> hs) >= 0 && (j + (int)((pos << 1) >> hs)) < ((int)(pcmlength >> hs) << 1) ) {
									f.printf("%d %d\n", j, (int)bigassbuffer[j + (int)((pos << 1) >> hs)]);
								}
							}
							f.close();
							f = null;
						} catch(final IOException e) {
						} finally {
							if( f != null ) {
								f.close();
							}
						}
					}

					ov.ov_clear();// added to free resources
					System.exit( 1 );// FIXME exit without free resources
					return;
				}
			}
		}
	}

	@SuppressWarnings("boxing")
	public static void main(final String[] args) {
		if( args.length != 1 ) {
			System.out.println("Usage:");
			System.out.println("java -jar OggVorbis_seeking_example.jar <Input File[.ogg]>");
			System.exit( 0 );
			return;
		}
		final JOggVorbis_File ov = new JOggVorbis_File();
		int i, ret;
		long pcmlength;
		final double timelength;
		final byte[] bigassbuffer;
		//int dummy;
		final int hs = 0;
		final Random rand = new Random();

		InputStream ins = null;// System.in
		try {
			//ins = new java.io.FileInputStream( args[0] );/* not seekable InputStream  */
			ins = JOggVorbis_File.ov_open( args[0] );/* get seekable InputStream */

			/* open the file/pipe on stdin */
			if( ov.ov_open_callbacks( ins, null, -1, JOggVorbis_File.OV_CALLBACKS_NOCLOSE ) < 0 ) {
				System.err.printf("Could not open input as an OggVorbis file.\n\n");
				ov.ov_clear();// added to free resources
				System.exit(1);// FIXME exit without free resources
			}

/*if( false ) {// enable this code to test seeking with halfrate decode
			if( ov.ov_halfrate( true ) != 0 ) {
				System.err.print("Sorry; unable to set half-rate decode.\n\n");
				System.exit( 1 );
			} else
				hs = 1;
}*/

			if( ov.ov_seekable() ) {

				/* to simplify our own lives, we want to assume the whole file is
				   stereo.  Verify this to avoid potentially mystifying users
				   (pissing them off is OK, just don't confuse them) */
				for( i = 0; i < ov.ov_streams(); i++ ) {
					final Jvorbis_info vi = ov.ov_info( i );
					if( vi.channels != 2 ) {
						System.err.printf("Sorry; right now seeking_test can only use Vorbis files\n" +
									"that are entirely stereo.\n\n");
						ov.ov_clear();// added to free resources
						System.exit( 1 );// FIXME exit without free resources
					}
				}

				/* because we want to do sample-level verification that the seek
				   does what it claimed, decode the entire file into memory */
				pcmlength = ov.ov_pcm_total( -1 );
				timelength = ov.ov_time_total( -1 );
				bigassbuffer = new byte[(int)(pcmlength >> hs) << 1]; /* w00t */
				i = 0;
				while( i < (int)((pcmlength >> hs) << 1) ) {
					final Jvorbis_pcm vpcm = ov.ov_read( bigassbuffer, i, ((int)(pcmlength >> hs) << 1) - i, true, 1, true);
					if( vpcm == null || vpcm.samples < 0 ) {
						System.err.print("Error reading file.\n");
						ov.ov_clear();// added to free resources
						System.exit( 1 );// FIXME exit without free resources
						return;
					} else {
						//dummy = pcm_data.data;
						if( vpcm.samples != 0 ) {
							i += vpcm.samples;
						} else {
							pcmlength = (i >>> 1) << hs;
						}
					}
					System.err.printf("\rloading.... [%d left]              ", (((pcmlength >> hs) << 1) - i));
				}

				{
					final long length = ov.end;
					System.err.printf("\rtesting raw seeking to random places in %d bytes....\n", length);

					for( i = 0; i < 1000; i++ ) {
						final long val = (long)((double)rand.nextInt( 32768 ) / 32767 * length);
						System.err.printf("\r\t%d [raw position %d]...     ", i, val);
						ret = ov.ov_raw_seek( val );
						if( ret < 0 ) {
							System.err.printf("seek failed: %d\n", ret);
							ov.ov_clear();// added to free resources
							System.exit( 1 );// FIXME exit without free resources
						}

						_verify( ov, val, -1, -1., pcmlength, bigassbuffer );

					}
				}

				System.err.print("\r");
				{
					System.err.printf("testing pcm page seeking to random places in %d samples....\n",
									pcmlength);

					for( i = 0; i < 1000; i++ ) {
						final long val = i == 0 ? 0 : (long)((double)rand.nextInt( 32768 ) / 32767 * pcmlength);
						System.err.printf("\r\t%d [pcm position %d]...     ", i, val);
						ret = ov.ov_pcm_seek_page( val );
						if( ret < 0 ) {
							System.err.printf("seek failed: %d\n", ret);
							ov.ov_clear();// added to free resources
							System.exit( 1 );// FIXME exit without free resources
						}

						_verify( ov, -1, val, -1., pcmlength, bigassbuffer );

					}
				}

				System.err.print("\r");
				{
					System.err.printf("testing pcm exact seeking to random places in %f seconds....\n",
									timelength);

					for( i = 0; i < 1000; i++ ) {
						final long val = i == 0 ? 0 : (long)((double)rand.nextInt( 32768 ) / 32767 * pcmlength);
						System.err.printf("\r\t%d [pcm position %d]...     ", i, val);
						ret = ov.ov_pcm_seek( val );
						if( ret < 0 ) {
							System.err.printf("seek failed: %d\n", ret);
							ov.ov_clear();// added to free resources
							System.exit( 1 );// FIXME exit without free resources
						}
						if( ov.ov_pcm_tell() != ((val >> hs) << hs) ) {
							System.err.printf("Declared position didn't perfectly match request: %d != %d\n",
										val, ov.ov_pcm_tell());
							ov.ov_clear();// added to free resources
							System.exit( 1 );// FIXME exit without free resources
						}

						_verify( ov, -1, val, -1., pcmlength, bigassbuffer );

					}
				}

				System.err.print("\r");
				{
					System.err.printf("testing time page seeking to random places in %f seconds....\n",
							timelength);

					for( i = 0; i < 1000; i++ ) {
						final double val = (double)rand.nextInt( 32768 ) / 32767 * timelength;
						System.err.printf("\r\t%d [time position %f]...     ", i, val);
						ret = ov.ov_time_seek_page( val );
						if( ret < 0 ) {
							System.err.printf("seek failed: %d\n", ret);
							ov.ov_clear();// added to free resources
							System.exit( 1 );// FIXME exit without free resources
						}

						_verify( ov, -1, -1, val, pcmlength, bigassbuffer );

					}
				}

				System.err.print("\r");
				{
					System.err.printf("testing time exact seeking to random places in %f seconds....\n",
								timelength);

					for( i = 0; i < 1000; i++ ) {
						final double val = (double)rand.nextInt( 32768 ) / 32767 * timelength;
						System.err.printf("\r\t%d [time position %f]...     ", i, val);
						ret = ov.ov_time_seek( val );
						if( ret < 0 ) {
							System.err.printf("seek failed: %d\n", ret);
							ov.ov_clear();// added to free resources
							System.exit( 1 );// FIXME exit without free resources
						}
						if( ov.ov_time_tell() < val - 1 || ov.ov_time_tell() > val + 1 ) {
							System.err.printf("Declared position didn't perfectly match request: %f != %f\n",
											val, ov.ov_time_tell());
							ov.ov_clear();// added to free resources
							System.exit( 1 );// FIXME exit without free resources
						}

						_verify( ov, -1, -1, val, pcmlength, bigassbuffer );

					}
				}

				System.err.print("\r                                           \nOK.\n\n");

			} else {
				System.err.print("Standard input was not seekable.\n");
			}
		} catch (final Exception e) {
			System.err.println( e.getMessage() );
			e.printStackTrace();
		} finally {
			if( ins != null ) {
				try{ ins.close(); } catch(final IOException e) {}
			}
		}

		ov.ov_clear();
		System.exit( 0 );
		return;
	}
}

