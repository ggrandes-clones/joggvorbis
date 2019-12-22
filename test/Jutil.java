package test;

import java.util.Arrays;

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

public class Jutil {
	private static final double M_PI = (3.141592653589793);// to compare with c-version
	public static void gen_windowed_sine(final float[] data, int len, final float maximum) {
		Arrays.fill( data, 0, len, 0 );

		len >>= 1;

		for( int k = 0; k < len; k++ ) {
			data[k] = (float)Math.sin( 2.0 * k * M_PI * 1.0 / 32.0 + 0.4 );

			/* Apply Hanning Window. */
			data[k] *= maximum * (0.5 - 0.5 * Math.cos( 2.0 * M_PI * k / (len - 1) ));
		}

		return;
	}

	public static void set_data_in(final float[] data, final int len, final float value) {
		for( int k = 0; k < len; k++ ) {
			data[k] = value;
		}
	}
}