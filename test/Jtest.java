package test;

import java.io.File;

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

 function: vorbis coded test suite using vorbisfile
 last mod: $Id: test.c 13293 2007-07-24 00:09:47Z erikd $

 ********************************************************************/

public class Jtest {
	private static final int DATA_LEN = 2048;
	private static final float data_out[] = new float[DATA_LEN];
	private static final float data_in[] = new float[DATA_LEN];
	//
	@SuppressWarnings("boxing")
	private static int check_output(final float[] in, final int len, final float allowable) {
		float max_abs = 0.0f;

		for( int k = 0; k < len; k++) {
			final float temp = Math.abs( in[k] );
			max_abs = Math.max( max_abs, temp );
		}

		if( max_abs < 0.95f - allowable ) {
			System.out.printf("Error : max_abs (%f) too small.\n", max_abs);
			return 1;
		} else if( max_abs > .95f + allowable ) {
			System.out.printf("Error : max_abs (%f) too big.\n", max_abs);
			return 1;
		}

		return 0;
	}
	@SuppressWarnings("boxing")
	public static void main(final String[] args) {
		/* Do safest and most used sample rates first. */
		final int sample_rates [] = { 44100, 48000, 32000, 22050, 16000, 96000 };
		int errors = 0;

		Jutil.gen_windowed_sine( data_out, data_out.length, 0.95f );

		for( int ch = 1; ch <= 8; ch++ ) {
			float q = -.05f;
			System.out.printf("\nTesting %d channel%s\n\n", ch, ch == 1 ? "" : "s");
			while( q < 1.f ) {
				for( int k = 0; k < sample_rates.length; k++ ) {
					final String filename = String.format("vorbis_%dch_q%.1f_%d.ogg", ch, q * 10, sample_rates[k] );

					System.out.printf("    %-20s : ", filename );
					System.out.flush();

					/* Set to know value. */
					Jutil.set_data_in( data_in, data_in.length, 3.141f );

					Jwrite_read.write_vorbis_data_or_die( filename, sample_rates[k], q, data_out, data_out.length, ch );
					Jwrite_read.read_vorbis_data_or_die( filename, sample_rates[k], data_in, data_in.length );

					if( check_output( data_in, data_in.length, (.15f - .1f * q) ) != 0 ) {
						errors++;
					} else {
						System.out.println("ok");
						new File( filename ).delete();
					}
				}
				q += .1f;
			}
		}

		if( errors != 0 ) {
			System.exit( 1 );
		}

		System.exit( 0 );
	}
}

