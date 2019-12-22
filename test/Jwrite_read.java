package test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import libogg.Jogg_packet;
import libogg.Jogg_page;
import libogg.Jogg_stream_state;
import libogg.Jogg_sync_state;
import libvorbis.Jvorbis_block;
import libvorbis.Jvorbis_comment;
import libvorbis.Jvorbis_dsp_state;
import libvorbis.Jvorbis_info;
import libvorbis.Jvorbis_pcm;

/********************************************************************
 *                                                                  *
 * THIS FILE IS PART OF THE OggVorbis SOFTWARE CODEC SOURCE CODE.   *
 * USE, DISTRIBUTION AND REPRODUCTION OF THIS LIBRARY SOURCE IS     *
 * GOVERNED BY A BSD-STYLE SOURCE LICENSE INCLUDED WITH THIS SOURCE *
 * IN 'COPYING'. PLEASE READ THESE TERMS BEFORE DISTRIBUTING.       *
 *                                                                  *
 * THE OggVorbis SOURCE CODE IS (C) COPYRIGHT 1994-2007             *
 * by the Xiph.Org Foundation http://www.xiph.org/                  *
 *                                                                  *
 ********************************************************************

 function: utility functions for vorbis codec test suite.
 last mod: $Id: util.c 13293 2007-07-24 00:09:47Z erikd $

 ********************************************************************/

public class Jwrite_read {

	/* The following function is basically a hacked version of the code in
	 * examples/encoder_example.c */
	@SuppressWarnings("boxing")
	public static void write_vorbis_data_or_die(final String filename,
			final int srate, final float q, final float[] data, final int count, final int ch)
	{
		FileOutputStream file = null;
		final Jogg_stream_state os = new Jogg_stream_state();
		final Jogg_page         og = new Jogg_page();
		final Jogg_packet       op = new Jogg_packet();
		final Jvorbis_info      vi = new Jvorbis_info();
		final Jvorbis_comment   vc = new Jvorbis_comment();
		final Jvorbis_dsp_state vd = new Jvorbis_dsp_state();
		final Jvorbis_block     vb = new Jvorbis_block();

		boolean eos = false;

		try {
			file = new FileOutputStream( filename );

			/********** Encode setup ************/

			vi.vorbis_info_init();

			final int ret = vi.vorbis_encode_init_vbr( ch, srate, q );
			if( ret != 0 ) {
				System.out.printf("vorbis_encode_init_vbr return %d\n", ret) ;
				System.exit( 1 );// FIXME exit without free resources
			}

			vc.vorbis_comment_init();
			vc.vorbis_comment_add_tag( "ENCODER", "test/util.c" );
			vd.vorbis_analysis_init( vi );
			vd.vorbis_block_init( vb );

			os.ogg_stream_init( 12345678 );

			{
				final Jogg_packet header = new Jogg_packet();
				final Jogg_packet header_comm = new Jogg_packet();
				final Jogg_packet header_code = new Jogg_packet();

				vd.vorbis_analysis_headerout( vc, header, header_comm, header_code );
				os.ogg_stream_packetin( header );
				os.ogg_stream_packetin( header_comm );
				os.ogg_stream_packetin( header_code );

				/* Ensures the audio data will start on a new page. */
				while( ! eos ) {
					final int result = os.ogg_stream_flush( og );
					if( result == 0 ) {
						break;
					}
					file.write( og.header_base, og.header, og.header_len );
					file.write( og.body_base, og.body, og.body_len );
				}

			}

			{
				/* expose the buffer to submit data */
				final Jvorbis_pcm pcm_data = vd.vorbis_analysis_buffer( count );

				for( int i = 0; i < ch; i++ ) {
					System.arraycopy( data, 0, pcm_data.pcm[i], pcm_data.pcmret, count );
				}

				/* tell the library how much we actually submitted */
				vd.vorbis_analysis_wrote( count );
				vd.vorbis_analysis_wrote( 0 );
			}

			while( vd.vorbis_analysis_blockout( vb ) ) {
				vb.vorbis_analysis( null );
				vb.vorbis_bitrate_addblock();

				while( vd.vorbis_bitrate_flushpacket( op ) ) {
					os.ogg_stream_packetin( op );

					while( ! eos ) {
						final int result = os.ogg_stream_pageout( og );
						if( result == 0 ) {
							break;
						}
						file.write( og.header_base, og.header, og.header_len );
						file.write( og.body_base, og.body, og.body_len );

						if( og.ogg_page_eos() ) {
							eos = true;
						}
					}
				}
			}

			/* moved to finally to fix cleanup
			os.ogg_stream_clear();
			vb.vorbis_block_clear();
			vd.vorbis_dsp_clear();
			vc.vorbis_comment_clear();
			vi.vorbis_info_clear();*/
		} catch( final Exception e ) {
			System.out.println( e.getMessage() );
			e.printStackTrace();
			System.exit( 1 );
		} finally {
			if( file != null ) {
				try{ file.close(); } catch(final IOException e){}
			}
			os.ogg_stream_clear();
			vb.vorbis_block_clear();
			vd.vorbis_dsp_clear();
			vc.vorbis_comment_clear();
			vi.vorbis_info_clear();
		}
	}

	/* The following function is basically a hacked version of the code in
	 * examples/decoder_example.c */
	@SuppressWarnings("boxing")
	public static void read_vorbis_data_or_die(final String filename, final int srate, final float[] data, final int count) {
		final Jogg_sync_state   oy = new Jogg_sync_state();
		final Jogg_stream_state os = new Jogg_stream_state();
		final Jogg_page         og = new Jogg_page();
		final Jogg_packet       op = new Jogg_packet();

		final Jvorbis_info      vi = new Jvorbis_info();
		final Jvorbis_comment   vc = new Jvorbis_comment();
		final Jvorbis_dsp_state vd = new Jvorbis_dsp_state();
		final Jvorbis_block     vb = new Jvorbis_block();

		FileInputStream file = null;
		int buffer;
		int bytes;
		boolean eos = false;
		int i;
		int read_total = 0;

		try {
			file = new FileInputStream( filename );

			oy.ogg_sync_init();

			/* fragile!  Assumes all of our headers will fit in the first 8kB,
			   which currently they will */
			buffer = oy.ogg_sync_buffer( 8192 );
			bytes = file.read( oy.data, buffer, 8192 );
			oy.ogg_sync_wrote( bytes );

			if( oy.ogg_sync_pageout( og ) != 1 ) {
				if( bytes < 8192 ) {
					System.out.print("Out of data.\n") ;
					oy.ogg_sync_clear();//goto done_decode ;
					return;
				}

				System.err.print("Input does not appear to be an Ogg bitstream.\n");
				System.exit( 1 );// FIXME exit without free resources
			}

			os.ogg_stream_init( og.ogg_page_serialno() );

			vi.vorbis_info_init();
			vc.vorbis_comment_init();
			if( os.ogg_stream_pagein( og ) < 0 ) {
				System.err.print("Error reading first page of Ogg bitstream data.\n");
				System.exit( 1 );// FIXME exit without free resources
			}

			if( os.ogg_stream_packetout( op ) != 1 ) {
				System.err.print("Error reading initial header packet.\n");
				System.exit( 1 );
			}

			if( Jvorbis_info.vorbis_synthesis_headerin( vi, vc, op ) < 0 ) {
				System.err.print("This Ogg bitstream does not contain Vorbis audio data.\n");
				System.exit( 1 );// FIXME exit without free resources
			}

			i = 0;
			while ( i < 2 ) {
				while( i < 2 ) {

					int result = oy.ogg_sync_pageout( og );
					if( result == 0 ) {
						break;
					}
					if( result == 1 ) {
						os.ogg_stream_pagein( og );

						while( i < 2 ) {
							result = os.ogg_stream_packetout( op );
							if( result == 0 ) {
								break;
							}
							if( result < 0 ) {
								System.err.print("Corrupt secondary header.  Exiting.\n");
								System.exit( 1 );
							}
							Jvorbis_info.vorbis_synthesis_headerin( vi, vc, op );
							i++;
						}
					}
				}

				buffer = oy.ogg_sync_buffer( 4096 );
				bytes = file.read( oy.data, buffer, 4096 );
				if( bytes < 0 )
				 {
					bytes = 0;// java: fread returns 0 if end of stream
				}
				if( bytes == 0 && i < 2 ) {
					System.err.print("End of file before finding all Vorbis headers!\n");
					System.exit( 1 );// FIXME exit without free resources
				}

				oy.ogg_sync_wrote( bytes );
			}

			if( vi.rate != srate ) {
				System.out.printf("\n\nError : File '%s' has sample rate of %d when it should be %d.\n\n", filename, vi.rate, srate);
				System.exit( 1 );
			}

			vd.vorbis_synthesis_init( vi );
			vd.vorbis_block_init( vb );

			while( ! eos ) {
				while( ! eos ) {
					int result = oy.ogg_sync_pageout( og );
					if( result == 0 ) {
						break;
					}
					if( result < 0 ) {
						System.err.print("Corrupt or missing data in bitstream; continuing...\n");
					} else {
						os.ogg_stream_pagein( og );
						while( true ) {
							result = os.ogg_stream_packetout( op );

							if( result == 0 ) {
								break;
							}
							if( result < 0 ) {
								/* no reason to complain; already complained above */
							} else {
								Jvorbis_pcm pcm;

								if( vb.vorbis_synthesis( op ) == 0 ) {
									vd.vorbis_synthesis_blockin( vb );
								}
								while( (pcm = vd.vorbis_synthesis_pcmout( true ) ).samples > 0 && read_total < count ) {
									int bout = pcm.samples < count ? pcm.samples : count;
									bout = read_total + bout > count ? count - read_total : bout;

									System.arraycopy( pcm.pcm[0], pcm.pcmret, data, read_total, bout );

									vd.vorbis_synthesis_read( bout );
									read_total += bout;
								}
							}
						}

						if( og.ogg_page_eos() ) {
							eos = true;
						}
					}
				}

				if( ! eos ) {
					buffer = oy.ogg_sync_buffer( 4096 );
					bytes = file.read( oy.data, buffer, 4096 );
					oy.ogg_sync_wrote( bytes );
					if( bytes <= 0 ) {
						eos = true;
					}
				}
			}
			/* moved to finally to fix cleanup
			os.ogg_stream_clear();

			vb.vorbis_block_clear();
			vd.vorbis_dsp_clear();
			vc.vorbis_comment_clear();
			vi.vorbis_info_clear();*/
//done_decode:

			/* OK, clean up the framer */
			oy.ogg_sync_clear();

		} catch( final Exception e ) {
			System.out.println( e.getMessage() );
			e.printStackTrace();
			System.exit( 1 );
		} finally {
			if( file != null ) {
				try{ file.close(); } catch(final IOException e) {}
			}
			os.ogg_stream_clear();

			vb.vorbis_block_clear();
			vd.vorbis_dsp_clear();
			vc.vorbis_comment_clear();
			vi.vorbis_info_clear();
		}
	}
}
