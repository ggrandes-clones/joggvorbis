package libvorbis.modes;

import libvorbis.Jatt3;
import libvorbis.Jnoise3;
import libvorbis.Jvp_adjblock;

/** 11kHz settings */

public final class Jpsych_11 {

	protected static final double _psy_lowpass_11[] = {4.5,5.5,30.,};// [3]

	protected static final Jatt3 _psy_tone_masteratt_11[] = {// [3]
		new Jatt3(new int[]{ 30,  25,  12},  0,   0),  /* 0 */
		new Jatt3(new int[]{ 30,  25,  12},  0,   0),  /* 0 */
		new Jatt3(new int[]{ 20,   0, -14},  0,   0),  /* 0 */
	};

	protected static final Jvp_adjblock _vp_tonemask_adj_11[] = {// [3]
		/* adjust for mode zero */
		/* 63     125     250     500     1     2     4     8    16 */
		new Jvp_adjblock(new int[]{-20,-20,-20,-20,-20,-16,-10, 0, 0, 0, 0,10, 2, 0,99,99,99}), /* 0 */
		new Jvp_adjblock(new int[]{-20,-20,-20,-20,-20,-16,-10, 0, 0, 0, 0, 5, 0, 0,99,99,99}), /* 1 */
		new Jvp_adjblock(new int[]{-20,-20,-20,-20,-20,-16,-10, 0, 0, 0, 0, 0, 0, 0,99,99,99}), /* 2 */
	};

	protected static final Jnoise3 _psy_noisebias_11[] = {// [3]
		/*  63     125     250     500      1k       2k      4k      8k     16k*/
		new Jnoise3(
		new int[][]{{-10,-10,-10,-10, -5, -5, -5,  0,  4, 10, 10, 12, 12, 12, 99, 99, 99},
		{-15,-15,-15,-15,-10,-10, -5,  0,  0,  4,  4,  5,  5, 10, 99, 99, 99},
		{-30,-30,-30,-30,-30,-24,-20,-14,-10, -6, -8, -8, -6, -6, 99, 99, 99}}),

		new Jnoise3(
		new int[][]{{-10,-10,-10,-10, -5, -5, -5,  0,  4, 10, 10, 12, 12, 12, 99, 99, 99},
		{-15,-15,-15,-15,-10,-10, -5, -5, -5,  0,  0,  0,  0,  0, 99, 99, 99},
		{-30,-30,-30,-30,-30,-24,-20,-14,-10, -6, -8, -8, -6, -6, 99, 99, 99}}),

		new Jnoise3(
		new int[][]{{-15,-15,-15,-15,-15,-12,-10, -8,  0,  2,  4,  4,  5,  5, 99, 99, 99},
		{-30,-30,-30,-30,-26,-22,-20,-14,-12,-12,-10,-10,-10,-10, 99, 99, 99},
		{-30,-30,-30,-30,-26,-26,-26,-26,-26,-26,-26,-26,-26,-24, 99, 99, 99}}),
	};

	protected static final double _noise_thresh_11[] = { .3,.5,.5 };// [3]
}
