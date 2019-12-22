package examples;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import libogg.Jogg_packet;
import libogg.Jogg_page;
import libogg.Jogg_stream_state;
import libogg.Jogg_sync_state;
import libvorbis.Jvorbis_block;
import libvorbis.Jvorbis_comment;
import libvorbis.Jvorbis_dsp_state;
import libvorbis.Jvorbis_info;
import libvorbis.Jvorbis_pcm;

/** simple example decoder */

/**
 * Takes a vorbis bitstream from an input file and writes raw stereo PCM to
 * output file. Decodes simple and chained OggVorbis files from beginning
 * to end. JOggVorbis_File is somewhat more complex than the code below.
 */
public final class Jdecoder_example {
	private static int convsize = 4096;// samples, 16-bit
	private static final byte convbuffer[] = new byte[4096 * 2]; /* take 8k out of the data segment, not the stack */
	//
	@SuppressWarnings("boxing")
	public static void main(String[] args) {
		if( args.length != 2 ) {
			System.out.println("Usage:");
			System.out.println("java -jar OggVorbis_decoder_example.jar <Input File[.ogg]> <Output File[.raw]>");
			System.exit( 0 );
			return;
		}
		//
		final Jogg_sync_state   oy = new Jogg_sync_state(); /* sync and verify incoming physical bitstream */
		final Jogg_stream_state os = new Jogg_stream_state(); /* take physical pages, weld into a logical
																stream of packets */
		final Jogg_page         og = new Jogg_page(); /* one Ogg bitstream page. Vorbis packets are inside */
		final Jogg_packet       op = new Jogg_packet(); /* one raw packet of data for decode */

		final Jvorbis_info      vi = new Jvorbis_info(); /* struct that stores all the static vorbis bitstream
							  								settings */
		final Jvorbis_comment   vc = new Jvorbis_comment(); /* struct that stores all the bitstream user comments */
		final Jvorbis_dsp_state vd = new Jvorbis_dsp_state(); /* central working state for the packet->PCM decoder */
		final Jvorbis_block     vb = new Jvorbis_block(); /* local working space for packet->PCM decode */

		int buffer;
		int bytes;

		InputStream ins = null;// System.in;
		OutputStream outs = null;// System.out;
		try {
			ins = new FileInputStream( args[0] );
			outs = new FileOutputStream( args[1] );
			/********** Decode setup ************/

			oy.ogg_sync_init(); /* Now we can read pages */

			while( true ) { /* we repeat if the bitstream is chained */
				boolean eos = false;
				int i;

				/* grab some data at the head of the stream. We want the first page
				   (which is guaranteed to be small and only contain the Vorbis
				   stream initial header) We need the first page to get the stream
				   serialno. */

				/* submit a 4k block to libvorbis' Ogg layer */
				buffer = oy.ogg_sync_buffer( 4096 );
				bytes = ins.read( oy.data, buffer, 4096 );
				if( bytes < 0 ) bytes = 0;// fread returns 0 if eos
				oy.ogg_sync_wrote( bytes );

				/* Get the first page. */
				if( oy.ogg_sync_pageout( og ) != 1 ) {
					/* have we simply run out of data?  If so, we're done. */
					if( bytes < 4096 ) break;

					/* error case.  Must not be Vorbis data */
					System.err.print("Input does not appear to be an Ogg bitstream.\n");
					System.exit( 1 );// FIXME exit without free resources
				}

				/* Get the serial number and set up the rest of decode. */
				/* serialno first; use it to set up a logical stream */
				os.ogg_stream_init( og.ogg_page_serialno() );

				/* extract the initial header from the first page and verify that the
				   Ogg bitstream is in fact Vorbis data */

				/* I handle the initial header first instead of just having the code
				   read all three Vorbis headers at once because reading the initial
				   header is an easy way to identify a Vorbis bitstream and it's
				   useful to see that functionality seperated out. */

				vi.vorbis_info_init();
				vc.vorbis_comment_init();
				if( os.ogg_stream_pagein( og ) < 0 ) {
					/* error; stream version mismatch perhaps */
					System.err.print("Error reading first page of Ogg bitstream data.\n");
					System.exit( 1 );// FIXME exit without free resources
				}

				if( os.ogg_stream_packetout( op ) != 1 ) {
					/* no page? must not be vorbis */
					System.err.print("Error reading initial header packet.\n");
					System.exit( 1 );// FIXME exit without free resources
				}

				if( Jvorbis_info.vorbis_synthesis_headerin( vi, vc, op ) < 0 ) {
					/* error case; not a vorbis header */
					System.err.print("This Ogg bitstream does not contain Vorbis audio data.\n");
					System.exit( 1 );// FIXME exit without free resources
				}

				/* At this point, we're sure we're Vorbis. We've set up the logical
				   (Ogg) bitstream decoder. Get the comment and codebook headers and
				   set up the Vorbis decoder */

				/* The next two packets in order are the comment and codebook headers.
				   They're likely large and may span multiple pages. Thus we read
				   and submit data until we get our two packets, watching that no
				   pages are missing. If a page is missing, error out; losing a
				   header page is the only place where missing data is fatal. */

				i = 0;
				while( i < 2 ) {
					while( i < 2 ) {
						int result = oy.ogg_sync_pageout( og );
						if( result == 0 ) break; /* Need more data */
						/* Don't complain about missing or corrupt data yet. We'll
						   catch it at the packet output phase */
						if( result == 1 ) {
							os.ogg_stream_pagein( og ); /* we can ignore any errors here
														 as they'll also become apparent
														 at packetout */
							while( i < 2 ) {
								result = os.ogg_stream_packetout( op );
								if( result == 0 ) break;
								if( result < 0 ) {
									/* Uh oh; data at some point was corrupted or missing!
									 We can't tolerate that in a header.  Die. */
									System.err.print("Corrupt secondary header.  Exiting.\n");
									System.exit( 1 );// FIXME exit without free resources
								}
								result = Jvorbis_info.vorbis_synthesis_headerin( vi, vc, op );
								if( result < 0 ) {
									System.err.print("Corrupt secondary header.  Exiting.\n");
									System.exit( 1 );// FIXME exit without free resources
								}
								i++;
							}
						}
					}
					/* no harm in not checking before adding more */
					buffer = oy.ogg_sync_buffer( 4096 );
					bytes = ins.read( oy.data, buffer, 4096 );
					if( bytes < 0 ) bytes = 0;// java: fread returns 0 if eos
					if( bytes == 0 && i < 2 ) {
						System.err.print("End of file before finding all Vorbis headers!\n");
						System.exit( 1 );// FIXME exit without free resources
					}
					oy.ogg_sync_wrote( bytes );
				}

				/* Throw the comments plus a few lines about the bitstream we're
				   decoding */
				{
					final String[] user_comments = vc.user_comments;
					for( i = 0; i < user_comments.length; i++ ) {
						System.err.println( user_comments[i] );
					}
					System.err.printf("\nBitstream is %d channel, %dHz\n", vi.channels, vi.rate );
					System.err.printf("Encoded by: %s\n\n", vc.vendor );
				}
		
				convsize = 4096 / vi.channels;

				/* OK, got and parsed all three headers. Initialize the Vorbis
				   packet->PCM decoder. */
				if( ! vd.vorbis_synthesis_init( vi ) ) { /* central decode state */
					vd.vorbis_block_init( vb );        /* local state for most of the decode
														  so multiple block decodes can
														  proceed in parallel. We could init
														  multiple vorbis_block structures
														  for vd here */
	  
					/* The rest is just a straight decode loop until end of stream */
					while( ! eos ) {
						while( ! eos ) {
							int result = oy.ogg_sync_pageout( og );
							if( result == 0 ) break; /* need more data */
							if( result < 0 ) { /* missing or corrupt data at this page position */
								System.err.print("Corrupt or missing data in bitstream; continuing...\n");
							} else {
								os.ogg_stream_pagein( og ); /* can safely ignore errors at
																			this point */
								while( true ) {
									result = os.ogg_stream_packetout( op );

									if( result == 0 ) break; /* need more data */
									if( result < 0 ) { /* missing or corrupt data at this page position */
										/* no reason to complain; already complained above */
									} else {
										/* we have a packet.  Decode it */
										Jvorbis_pcm vpcm;

										if( vb.vorbis_synthesis( op ) == 0 ) /* test for success! */
											vd.vorbis_synthesis_blockin( vb );
										/* 

										**pcm is a multichannel float vector.  In stereo, for
										example, pcm[0] is left, and pcm[1] is right.  samples is
										the size of each channel.  Convert the float values
										(-1.<=range<=1.) to whatever PCM format and write it out */
					
										while( (vpcm = vd.vorbis_synthesis_pcmout( true ) ).samples > 0 ) {
											int j;
											boolean clipflag = false;
											final int bout = (vpcm.samples < convsize ? vpcm.samples : convsize);

											/* convert floats to 16 bit signed ints (host order) and
											 interleave */
											for( i = 0; i < vi.channels; i++ ) {
												int ptr = i << 1;
												final float[] mono = vpcm.pcm[i];
												int offset = vpcm.pcmret;
												for( j = 0; j < bout; j++ ) {
//if( true ) {
													int val = (int)Math.floor( mono[offset++] * 32767.f + .5f );
//} else {/* optional dither */
//													int val = mono[j] * 32767.f + drand48() - 0.5f;
//}
													/* might as well guard against clipping */
													if( val > 32767 ) {
														val = 32767;
														clipflag = true;
													}
													if( val < -32768 ) {
														val = -32768;
														clipflag = true;
													}
													convbuffer[ptr] = (byte)val;
													convbuffer[ptr + 1] = (byte)(val >> 8);
													ptr += vi.channels << 1;// int16 to bytes
												}
											}

											if( clipflag )
												System.err.printf("Clipping in frame %d\n", vd.sequence );

											outs.write( convbuffer, 0, (bout * vi.channels) << 1 );
					  
											vd.vorbis_synthesis_read( bout ); /* tell libvorbis how
																			  many samples we
																			  actually consumed */
										}            
									}
								}
								if( og.ogg_page_eos() ) eos = true;
							}
						}
						if( ! eos ) {
							buffer = oy.ogg_sync_buffer( 4096 );
							bytes = ins.read( oy.data, buffer, 4096 );
							if( bytes < 0 ) bytes = 0;// java: fread returns 0 if eos
							oy.ogg_sync_wrote( bytes );
							if( bytes <= 0 ) eos = true;
						}
					}
		  
					/* ogg_page and ogg_packet structs always point to storage in
					 libvorbis.  They're never freed or manipulated directly */

					vb.vorbis_block_clear();
					vd.vorbis_dsp_clear();
				} else {
					System.err.print("Error: Corrupt header during playback initialization.\n");
				}

				/* clean up this logical bitstream; before exit we see if we're
				   followed by another [chained] */
		
				os.ogg_stream_clear();
				vc.vorbis_comment_clear();
				vi.vorbis_info_clear();  /* must be called last */
			}

			/* OK, clean up the framer */
			oy.ogg_sync_clear();

			System.err.print("Done.\n");
			System.exit( 0 );
			return;
		} catch(Exception e) {
			System.err.println( e.getMessage() );
			e.printStackTrace();
		} finally {
			if( ins != null ) try { ins.close(); } catch( IOException e ) {}
			if( outs != null ) try { outs.close(); } catch( IOException e ) {}
		}
		vb.vorbis_block_clear();
		vd.vorbis_dsp_clear();
		os.ogg_stream_clear();
		vc.vorbis_comment_clear();
		vi.vorbis_info_clear();  /* must be called last */
		oy.ogg_sync_clear();
		System.exit( 1 );
	}
}
