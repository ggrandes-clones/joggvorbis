package libvorbis.modes;

import libvorbis.Jadj_stereo;
import libvorbis.Jatt3;
import libvorbis.Jcompandblock;
import libvorbis.Jnoise3;
import libvorbis.Jnoiseguard;
import libvorbis.Jvorbis_info_psy;
import libvorbis.Jvorbis_info_psy_global;
import libvorbis.Jvp_adjblock;

/** key psychoacoustic settings for 44.1/48kHz */

public final class Jpsych_44 {

	protected static final Jvorbis_info_psy_global _psy_global_44[] = {// [5]
		// FIXME why doubles, not integers ?
		new Jvorbis_info_psy_global(
		8,   /* lines per eighth octave */
		new float[] {20.f,14.f,12.f,12.f,12.f,12.f,12.f},
		new float[] {-60.f,-30.f,-40.f,-40.f,-40.f,-40.f,-40.f}, 2,-75.f,
		-6.f,
		new int[] {99},new int[][] {{99},{99}},new int[] {0},new int[] {0},new int[][] {{0},{0}}
		),
		new Jvorbis_info_psy_global(
		8,   /* lines per eighth octave */
		new float[] {14.f,10.f,10.f,10.f,10.f,10.f,10.f},
		new float[] {-40.f,-30.f,-25.f,-25.f,-25.f,-25.f,-25.f}, 2,-80.f,
		-6.f,
		new int[] {99},new int[][] {{99},{99}},new int[] {0},new int[] {0},new int[][] {{0},{0}}
		),
		new Jvorbis_info_psy_global(
		8,   /* lines per eighth octave */
		new float[] {12.f,10.f,10.f,10.f,10.f,10.f,10.f},
		new float[] {-20.f,-20.f,-15.f,-15.f,-15.f,-15.f,-15.f}, 0,-80.f,
		-6.f,
		new int[] {99},new int[][] {{99},{99}},new int[] {0},new int[] {0},new int[][] {{0},{0}}
		),
		new Jvorbis_info_psy_global(
		8,   /* lines per eighth octave */
		new float[] {10.f,8.f,8.f,8.f,8.f,8.f,8.f},
		new float[] {-20.f,-15.f,-12.f,-12.f,-12.f,-12.f,-12.f}, 0,-80.f,
		-6.f,
		new int[] {99},new int[][] {{99},{99}},new int[] {0},new int[] {0},new int[][] {{0},{0}}
		),
		new Jvorbis_info_psy_global(
		8,   /* lines per eighth octave */
		new float[] {10.f,6.f,6.f,6.f,6.f,6.f,6.f},
		new float[] {-15.f,-15.f,-12.f,-12.f,-12.f,-12.f,-12.f}, 0,-85.f,
		-6.f,
		new int[] {99},new int[][] {{99},{99}},new int[] {0},new int[] {0},new int[][] {{0},{0}}
		),
	};

	/** noise compander lookups * low, mid, high quality ****************/
	protected static final Jcompandblock _psy_compand_44[] = {// [6]
		/* sub-mode Z short */
		new Jcompandblock( new int[]
		{0, 1, 2, 3, 4, 5, 6,  7,     /* 7dB */
		8, 9,10,11,12,13,14, 15,     /* 15dB */
		16,17,18,19,20,21,22, 23,     /* 23dB */
		24,25,26,27,28,29,30, 31,     /* 31dB */
		32,33,34,35,36,37,38, 39,     /* 39dB */
		} ),
		/* mode_Z nominal short */
		new Jcompandblock( new int[]
		{
		0, 1, 2, 3, 4, 5, 6,  6,     /* 7dB */
		7, 7, 7, 7, 6, 6, 6,  7,     /* 15dB */
		7, 8, 9,10,11,12,13, 14,     /* 23dB */
		15,16,17,17,17,18,18, 19,     /* 31dB */
		19,19,20,21,22,23,24, 25,     /* 39dB */
		} ),
		/* mode A short */
		new Jcompandblock( new int[]
		{
		0, 1, 2, 3, 4, 5, 5,  5,     /* 7dB */
		6, 6, 6, 5, 4, 4, 4,  4,     /* 15dB */
		4, 4, 5, 5, 5, 6, 6,  6,     /* 23dB */
		7, 7, 7, 8, 8, 8, 9, 10,     /* 31dB */
		11,12,13,14,15,16,17, 18,     /* 39dB */
		} ),
		/* sub-mode Z long */
		new Jcompandblock( new int[]
		{
		0, 1, 2, 3, 4, 5, 6,  7,     /* 7dB */
		8, 9,10,11,12,13,14, 15,     /* 15dB */
		16,17,18,19,20,21,22, 23,     /* 23dB */
		24,25,26,27,28,29,30, 31,     /* 31dB */
		32,33,34,35,36,37,38, 39,     /* 39dB */
		} ),
		/* mode_Z nominal long */
		new Jcompandblock( new int[]
		{
		0, 1, 2, 3, 4, 5, 6,  7,     /* 7dB */
		8, 9,10,11,12,12,13, 13,     /* 15dB */
		13,14,14,14,15,15,15, 15,     /* 23dB */
		16,16,17,17,17,18,18, 19,     /* 31dB */
		19,19,20,21,22,23,24, 25,     /* 39dB */
		} ),
		/* mode A long */
		new Jcompandblock( new int[]
		{
		0, 1, 2, 3, 4, 5, 6,  7,     /* 7dB */
		8, 8, 7, 6, 5, 4, 4,  4,     /* 15dB */
		4, 4, 5, 5, 5, 6, 6,  6,     /* 23dB */
		7, 7, 7, 8, 8, 8, 9, 10,     /* 31dB */
		11,12,13,14,15,16,17, 18,     /* 39dB */
		})
	};

	/** tonal masking curve level adjustments *************************/
	protected static final Jvp_adjblock _vp_tonemask_adj_longblock[] = {// [12]

		/* 63     125     250     500       1       2       4       8      16 */
		new Jvp_adjblock( new int[]
		{ -3, -8,-13,-15,-10,-10,-10,-10,-10,-10,-10,  0,  0,  0,  0,  0,  0} ), /* -1 */

		new Jvp_adjblock( new int[]
		/* {{-15,-15,-15,-15,-10, -8, -4, -2,  0,  0,  0, 10,  0,  0,  0,  0,  0}},    0 */
		{ -4,-10,-14,-16,-15,-14,-13,-12,-12,-12,-11, -1, -1, -1, -1, -1,  0} ), /* 0 */

		new Jvp_adjblock( new int[]
		/* {{-15,-15,-15,-15,-15,-12,-10, -8,  0,  0,  0,  5,  0,  0,  0,  0,  0}},    1 */
		{ -6,-12,-14,-16,-15,-15,-14,-13,-13,-12,-12, -2, -2, -1, -1, -1,  0} ), /* 1 */

		new Jvp_adjblock( new int[]
		/* {{-15,-15,-15,-15,-15,-12,-10, -8,  0,  0,  0,  0,  0,  0,  0,  0,  0}},    2 */
		{-12,-13,-14,-16,-16,-16,-15,-14,-13,-12,-12, -6, -3, -1, -1, -1,  0} ), /* 2 */

		new Jvp_adjblock( new int[]
		/* {{-15,-15,-15,-15,-15,-12,-10, -8,  0,  0,  0,  0,  0,  0,  0,  0,  0}},    3 */
		{-15,-15,-15,-16,-16,-16,-16,-14,-13,-13,-13,-10, -4, -2, -1, -1,  0} ), /* 3 */

		new Jvp_adjblock( new int[]
		/* {{-15,-15,-15,-15,-15,-12,-10, -8,  0,  0,  0,  0,  0,  0,  0,  0,  0}}, *//* 4 */
		{-16,-16,-16,-16,-16,-16,-16,-15,-14,-14,-13,-11, -7  -3, -1, -1 , 0} ), /* 4 */

		new Jvp_adjblock( new int[]
		/* {{-15,-15,-15,-15,-15,-12,-10, -8,  0,  0,  0,  0,  0,  0,  0,  0,  0}},    5 */
		{-16,-16,-16,-16,-16,-16,-16,-15,-14,-14,-13,-11, -7  -3, -1, -1 , 0} ), /* 5 */

		new Jvp_adjblock( new int[]
		/* {{-15,-15,-15,-15,-15,-12,-10, -8,  0,  0,  0,  0,  0,  0,  0,  0,  0}},    6 */
		{-16,-16,-16,-16,-16,-16,-16,-15,-14,-14,-14,-12, -8, -4, -2, -2,  0} ), /* 6 */

		new Jvp_adjblock( new int[]
		/* {{-15,-15,-15,-15,-15,-12,-10, -8,  0,  0,  0,  0,  0,  0,  0,  0,  0}},    7 */
		{-16,-16,-16,-16,-16,-16,-16,-15,-14,-14,-14,-12, -9, -4, -2, -2,  0} ), /* 7 */

		new Jvp_adjblock( new int[]
		/* {{-15,-15,-15,-15,-15,-12,-10, -8,  0,  0,  0,  0,  0,  0,  0,  0,  0}},    8 */
		{-16,-16,-16,-16,-16,-16,-16,-15,-14,-14,-14,-12, -9, -4, -2, -2,  0} ), /* 8 */

		new Jvp_adjblock( new int[]
		/* {{-15,-15,-15,-15,-15,-12,-10, -8,  0,  0,  0,  0,  0,  0,  0,  0,  0}},    9 */
		{-16,-16,-16,-16,-16,-16,-16,-15,-14,-14,-14,-12, -9, -4, -2, -2,  0} ), /* 9 */

		new Jvp_adjblock( new int[]
		/* {{-15,-15,-15,-15,-15,-12,-10, -8,  0,  0,  0,  0,  0,  0,  0,  0,  0}},    10 */
		{-16,-16,-16,-16,-16,-16,-16,-15,-14,-14,-14,-12, -9, -4, -2, -2,  0} ), /* 10 */
	};

	protected static final Jvp_adjblock _vp_tonemask_adj_otherblock[] = {// [12]
		/* 63     125     250     500       1       2       4       8      16 */
		new Jvp_adjblock( new int[]
		{ -3, -8,-13,-15,-10,-10, -9, -9, -9, -9, -9,  1,  1,  1,  1,  1,  1} ), /* -1 */

		new Jvp_adjblock( new int[]
		/* {{-20,-20,-20,-20,-14,-12,-10, -8, -4,  0,  0, 10,  0,  0,  0,  0,  0}},    0 */
		{ -4,-10,-14,-16,-14,-13,-12,-12,-11,-11,-10,  0,  0,  0,  0,  0,  0} ), /* 0 */

		new Jvp_adjblock( new int[]
		/* {{-20,-20,-20,-20,-20,-18,-16,-14,-10,  0,  0,  5,  0,  0,  0,  0,  0}},    1 */
		{ -6,-12,-14,-16,-15,-15,-14,-13,-13,-12,-12, -2, -2, -1,  0,  0,  0} ), /* 1 */

		new Jvp_adjblock( new int[]
		/* {{-20,-20,-20,-20,-20,-18,-16,-14,-10,  0,  0,  0,  0,  0,  0,  0,  0}},    2 */
		{-12,-13,-14,-16,-16,-16,-15,-14,-13,-12,-12, -5, -2, -1,  0,  0,  0} ), /* 2 */

		new Jvp_adjblock( new int[]
		/* {{-20,-20,-20,-20,-20,-18,-16,-14,-10,  0,  0,  0,  0,  0,  0,  0,  0}},    3 */
		{-15,-15,-15,-16,-16,-16,-16,-14,-13,-13,-13,-10, -4, -2,  0,  0,  0} ), /* 3 */

		new Jvp_adjblock( new int[]
		/* {{-20,-20,-20,-20,-20,-18,-16,-14,-10,  0,  0,  0,  0,  0,  0,  0,  0}},    4 */
		{-16,-16,-16,-16,-16,-16,-16,-15,-14,-14,-13,-11, -7  -3, -1, -1 , 0} ), /* 4 */

		new Jvp_adjblock( new int[]
		/* {{-20,-20,-20,-20,-20,-18,-16,-14,-10,  0,  0,  0,  0,  0,  0,  0,  0}},    5 */
		{-16,-16,-16,-16,-16,-16,-16,-15,-14,-14,-13,-11, -7  -3, -1, -1 , 0} ), /* 5 */

		new Jvp_adjblock( new int[]
		/* {{-20,-20,-20,-20,-20,-18,-16,-14,-10,  0,  0,  0,  0,  0,  0,  0,  0}},    6 */
		{-16,-16,-16,-16,-16,-16,-16,-15,-14,-14,-14,-12, -8, -4, -2, -2,  0} ), /* 6 */

		new Jvp_adjblock( new int[]
		/* {{-20,-20,-20,-20,-20,-18,-16,-14,-10,  0,  0,  0,  0,  0,  0,  0,  0}},    7 */
		{-16,-16,-16,-16,-16,-16,-16,-15,-14,-14,-14,-12, -9, -4, -2, -2,  0} ), /* 7 */

		new Jvp_adjblock( new int[]
		/* {{-20,-20,-20,-20,-20,-18,-16,-14,-10,  0,  0,  0,  0,  0,  0,  0,  0}},    8 */
		{-16,-16,-16,-16,-16,-16,-16,-15,-14,-14,-14,-12, -9, -4, -2, -2,  0} ), /* 8 */

		new Jvp_adjblock( new int[]
		/* {{-20,-20,-20,-20,-20,-18,-16,-14,-10,  0,  0,  0,  0,  0,  0,  0,  0}},    9 */
		{-16,-16,-16,-16,-16,-16,-16,-15,-14,-14,-14,-12, -9, -4, -2, -2,  0} ), /* 9 */

		new Jvp_adjblock( new int[]
		/* {{-20,-20,-20,-20,-20,-18,-16,-14,-10,  0,  0,  0,  0,  0,  0,  0,  0}},    10 */
		{-16,-16,-16,-16,-16,-16,-16,-15,-14,-14,-14,-12, -9, -4, -2, -2,  0} ), /* 10 */
	};

	/** noise bias (transition block) */
	protected static final Jnoise3 _psy_noisebias_trans[] = {// [12]
		/*  63     125     250     500      1k       2k      4k      8k     16k*/
		/* -1 */
		new Jnoise3( new int[][]
		{{-10,-10,-10,-10,-10, -4,  0,  0,  4,  8,  8,  8,  8, 10, 12, 14, 20},
		{-30,-30,-30,-30,-26,-20,-16, -8, -6, -6, -2,  2,  2,  3,  6,  6, 15},
		{-30,-30,-30,-30,-30,-24,-20,-14,-10, -6, -8, -8, -6, -6, -6, -4, -2}} ),
		/* 0
		{{{-15,-15,-15,-15,-15,-12,-10, -8,  0,  2,  4,  4,  5,  5,  5,  8,  10},
		{-30,-30,-30,-30,-26,-22,-20,-14, -8, -4,  0,  0,  0,  0,  2,  4,  10},
		{-30,-30,-30,-30,-26,-22,-20,-14,-10, -6, -6, -6, -6, -4, -4, -4,  -2}}},*/
		new Jnoise3( new int[][]
		{{-15,-15,-15,-15,-15,-12, -6, -4,  0,  2,  4,  4,  5,  5,  5,  8,  10},
		{-30,-30,-30,-30,-26,-22,-20,-14, -8, -4,  0,  0,  0,  0,  2,  3,   6},
		{-30,-30,-30,-30,-26,-22,-20,-14,-10, -6, -6, -6, -6, -4, -4, -4,  -2}} ),
		/* 1
		{{{-15,-15,-15,-15,-15,-12,-10, -8,  0,  2,  4,  4,  5,  5,  5,  8,  10},
		{-30,-30,-30,-30,-26,-22,-20,-14,-10, -4, -2, -2, -2, -2,  0,  2,  8},
		{-30,-30,-30,-30,-26,-22,-20,-14,-10, -8, -8, -8, -8, -6, -6, -6, -4}}},*/
		new Jnoise3( new int[][]
		{{-15,-15,-15,-15,-15,-12,-10, -8,  0,  2,  4,  4,  5,  5,  5,  8,  10},
		{-30,-30,-30,-30,-26,-22,-20,-14,-10, -4, -2, -2, -2, -2,  0,  1,   4},
		{-30,-30,-30,-30,-26,-22,-20,-14,-10, -8, -8, -8, -8, -6, -6, -6,  -4}} ),
		/* 2
		{{{-15,-15,-15,-15,-15,-12,-10, -8,  0,  2,  2,  2,  4,  4,  5,  6,  10},
		{-30,-30,-30,-30,-26,-22,-20,-14,-10, -4, -2, -2, -2, -2,  0,  2,  6},
		{-30,-30,-30,-30,-26,-22,-20,-14,-10,-10,-10,-10,-10, -8, -8, -8, -4}}}, */
		new Jnoise3( new int[][]
		{{-15,-15,-15,-15,-15,-12,-10, -8,  0,  2,  2,  2,  4,  4,  5,  6,  10},
		{-30,-30,-30,-30,-26,-22,-20,-14,-10, -4, -3, -3, -3, -2, -1,  0,  3},
		{-30,-30,-30,-30,-26,-22,-20,-14,-10,-10,-10,-10,-10, -8, -8, -7, -4}} ),
		/* 3
		{{{-15,-15,-15,-15,-15,-12,-10, -8,  0,  2,  2,  2,  4,  4,  4,  5,  8},
		{-30,-30,-30,-30,-26,-22,-20,-14,-10, -4, -3, -3, -3, -3, -1,  1,  6},
		{-30,-30,-30,-30,-26,-22,-20,-14,-10,-10,-10,-10,-10, -8, -8, -8, -4}}},*/
		new Jnoise3( new int[][]
		{{-15,-15,-15,-15,-15,-12,-10, -8,  0,  2,  2,  2,  4,  4,  4,  5,  8},
		{-30,-30,-30,-30,-26,-22,-20,-14,-10, -4, -3, -3, -3, -3, -2,  0,  2},
		{-30,-30,-30,-30,-26,-22,-20,-14,-10,-10,-10,-10,-10, -8, -8, -8, -4}} ),
		/* 4
		{{{-20,-20,-20,-20,-20,-18,-14, -8, -1,  1,  1,  1,  2,  3,  3,  4,  7},
		{-30,-30,-30,-30,-26,-22,-20,-14,-10, -4, -3, -3, -3, -3, -1,  1,  5},
		{-30,-30,-30,-30,-26,-22,-20,-14,-10,-10,-10,-10,-10, -8, -8, -8, -4}}},*/
		new Jnoise3( new int[][]
		{{-20,-20,-20,-20,-20,-18,-14, -8, -1,  1,  1,  1,  2,  3,  3,  4,  7},
		{-30,-30,-30,-30,-26,-22,-20,-14,-10, -4, -3, -3, -3, -3, -2, -1,  1},
		{-30,-30,-30,-30,-26,-22,-20,-14,-10,-10,-10,-10,-10, -8, -8, -8, -4}} ),
		/* 5
		{{{-24,-24,-24,-24,-20,-18,-14, -8, -1,  1,  1,  1,  2,  3,  3,  4,  7},
		{-32,-32,-32,-32,-28,-24,-22,-16,-12, -6, -4, -4, -4, -4, -2, -1,  2},
		{-34,-34,-34,-34,-30,-24,-24,-18,-14,-12,-12,-12,-12,-10,-10, -9, -5}}}, */
		new Jnoise3( new int[][]
		{{-24,-24,-24,-24,-20,-18,-14, -8, -1,  1,  1,  1,  2,  3,  3,  4,  7},
		{-32,-32,-32,-32,-28,-24,-22,-16,-12, -6, -4, -4, -4, -4, -3, -1,  0},
		{-34,-34,-34,-34,-30,-24,-24,-18,-14,-12,-12,-12,-12,-10,-10, -9, -5}} ),
		/* 6
		{{{-24,-24,-24,-24,-20,-18,-14, -8, -1,  1,  1,  1,  2,  3,  3,  4,  7},
		{-32,-32,-32,-32,-28,-24,-24,-18,-14, -8, -6, -6, -6, -6, -4, -2,  1},
		{-34,-34,-34,-34,-30,-26,-24,-18,-17,-15,-15,-15,-15,-13,-13,-12, -8}}},*/
		new Jnoise3( new int[][]
		{{-24,-24,-24,-24,-20,-18,-14, -8, -1,  1,  1,  1,  2,  3,  3,  4,  7},
		{-32,-32,-32,-32,-28,-24,-24,-18,-14, -8, -6, -6, -6, -6, -5, -2,  0},
		{-34,-34,-34,-34,-30,-26,-26,-24,-22,-19,-19,-19,-19,-18,-17,-16,-12}} ),
		/* 7
		{{{-24,-24,-24,-24,-20,-18,-14, -8, -1,  1,  1,  1,  2,  3,  3,  4,  7},
		{-32,-32,-32,-32,-28,-24,-24,-18,-14,-12,-10, -8, -8, -8, -6, -4,  0},
		{-34,-34,-34,-34,-30,-26,-26,-24,-22,-19,-19,-19,-19,-18,-17,-16,-12}}},*/
		new Jnoise3( new int[][]
		{{-24,-24,-24,-24,-20,-18,-14, -8, -1,  1,  1,  1,  2,  3,  3,  4,  7},
		{-32,-32,-32,-32,-28,-24,-24,-24,-18,-14,-12,-10,-10,-10, -8, -6, -2},
		{-34,-34,-34,-34,-30,-26,-26,-26,-24,-24,-24,-24,-24,-24,-24,-20,-16}} ),
		/* 8
		{{{-24,-24,-24,-24,-22,-20,-15,-10, -8, -2,  0,  0,  0,  1,  2,  3,  7},
		{-36,-36,-36,-36,-30,-30,-30,-24,-18,-14,-12,-10,-10,-10, -8, -6, -2},
		{-36,-36,-36,-36,-34,-30,-28,-26,-24,-24,-24,-24,-24,-24,-24,-20,-16}}},*/
		new Jnoise3( new int[][]
		{{-24,-24,-24,-24,-22,-20,-15,-10, -8, -2,  0,  0,  0,  1,  2,  3,  7},
		{-36,-36,-36,-36,-30,-30,-30,-24,-20,-16,-16,-16,-16,-14,-12,-10, -7},
		{-36,-36,-36,-36,-34,-30,-28,-26,-24,-30,-30,-30,-30,-30,-30,-24,-20}} ),
		/* 9
		{{{-28,-28,-28,-28,-28,-28,-28,-20,-14, -8, -4, -4, -4, -4, -4, -2,  2},
		{-36,-36,-36,-36,-34,-32,-32,-28,-20,-16,-16,-16,-16,-14,-12,-10, -7},
		{-40,-40,-40,-40,-40,-40,-40,-32,-30,-30,-30,-30,-30,-30,-30,-24,-20}}},*/
		new Jnoise3( new int[][]
		{{-28,-28,-28,-28,-28,-28,-28,-20,-14, -8, -4, -4, -4, -4, -4, -2,  2},
		{-38,-38,-38,-38,-36,-34,-34,-30,-24,-20,-20,-20,-20,-18,-16,-12,-10},
		{-40,-40,-40,-40,-40,-40,-40,-38,-35,-35,-35,-35,-35,-35,-35,-35,-30}} ),
		/* 10 */
		new Jnoise3( new int[][]
		{{-30,-30,-30,-30,-30,-30,-30,-28,-20,-14,-14,-14,-14,-14,-14,-12,-10},
		{-40,-40,-40,-40,-40,-40,-40,-40,-35,-30,-30,-30,-30,-30,-30,-30,-20},
		{-40,-40,-40,-40,-40,-40,-40,-40,-40,-40,-40,-40,-40,-40,-40,-40,-40}} ),
	};

	/**  noise bias (long block) */
	protected static final Jnoise3 _psy_noisebias_long[] = {// [12]
		/*63     125     250     500      1k       2k      4k      8k     16k*/
		/* -1 */
		new Jnoise3( new int[][]
		{{-10,-10,-10,-10,-10, -4,  0,  0,  0,  6,  6,  6,  6, 10, 10, 12,  20},
		{-20,-20,-20,-20,-20,-20,-10, -2,  0,  0,  0,  0,  0,  2,  4,  6,  15},
		{-20,-20,-20,-20,-20,-20,-20,-10, -6, -6, -6, -6, -6, -4, -4, -4, -2}} ),

		/* 0 */
		new Jnoise3( new int[][]
		/*  {{{-10,-10,-10,-10,-10,-10, -8,  2,  2,  2,  4,  4,  5,  5,  5,  8,  10},
		  {-20,-20,-20,-20,-20,-20,-20,-14, -6,  0,  0,  0,  0,  0,  2,  4,  10},
		  {-20,-20,-20,-20,-20,-20,-20,-14, -8, -6, -6, -6, -6, -4, -4, -4, -2}}},*/
		{{-10,-10,-10,-10,-10,-10, -8,  2,  2,  2,  4,  4,  5,  5,  5,  8,  10},
		{-20,-20,-20,-20,-20,-20,-20,-14, -6,  0,  0,  0,  0,  0,  2,  3,  6},
		{-20,-20,-20,-20,-20,-20,-20,-14, -8, -6, -6, -6, -6, -4, -4, -4, -2}} ),
		/* 1 */
		new Jnoise3( new int[][]
		/*  {{{-10,-10,-10,-10,-10,-10, -8, -4,  0,  2,  4,  4,  5,  5,  5,  8,  10},
		  {-20,-20,-20,-20,-20,-20,-20,-14,-10, -4, -2, -2, -2, -2,  0,  2,  8},
		  {-20,-20,-20,-20,-20,-20,-20,-14,-10, -8, -8, -8, -8, -6, -6, -6, -4}}},*/
		{{-10,-10,-10,-10,-10,-10, -8, -4,  0,  2,  4,  4,  5,  5,  5,  8,  10},
		{-20,-20,-20,-20,-20,-20,-20,-14,-10, -4, -2, -2, -2, -2,  0,  1,  4},
		{-20,-20,-20,-20,-20,-20,-20,-14,-10, -8, -8, -8, -8, -6, -6, -6, -4}} ),
		/* 2 */
		new Jnoise3( new int[][]
		/*  {{{-10,-10,-10,-10,-10,-10,-10, -8,  0,  2,  2,  2,  4,  4,  5,  6,  10},
		  {-20,-20,-20,-20,-20,-20,-20,-14,-10, -4, -2, -2, -2, -2,  0,  2,  6},
		  {-20,-20,-20,-20,-20,-20,-20,-14,-10,-10,-10,-10,-10, -8, -8, -8, -4}}},*/
		{{-10,-10,-10,-10,-10,-10,-10, -8,  0,  2,  2,  2,  4,  4,  5,  6,  10},
		{-20,-20,-20,-20,-20,-20,-20,-14,-10, -4, -3, -3, -3, -2, -1,  0,  3},
		{-20,-20,-20,-20,-20,-20,-20,-14,-10,-10,-10,-10,-10, -8, -8, -8, -4}} ),
		/* 3 */
		new Jnoise3( new int[][]
		/*  {{{-10,-10,-10,-10,-10,-10,-10, -8,  0,  2,  2,  2,  4,  4,  4,  5,  8},
		  {-20,-20,-20,-20,-20,-20,-20,-14,-10, -4, -3, -3, -3, -3, -1,  1,  6},
		  {-20,-20,-20,-20,-20,-20,-20,-14,-10,-10,-10,-10,-10, -8, -8, -8, -4}}},*/
		{{-10,-10,-10,-10,-10,-10,-10, -8,  0,  2,  2,  2,  4,  4,  4,  5,  8},
		{-20,-20,-20,-20,-20,-20,-20,-14,-10, -4, -3, -3, -3, -3, -2,  0,  2},
		{-20,-20,-20,-20,-20,-20,-20,-14,-10,-10,-10,-10,-10, -8, -8, -8, -5}} ),
		/* 4 */
		new Jnoise3( new int[][]
		/*  {{{-15,-15,-15,-15,-15,-15,-15,-10, -4,  1,  1,  1,  2,  3,  3,  4,  7},
		  {-20,-20,-20,-20,-20,-20,-20,-14,-10, -4, -3, -3, -3, -3, -1,  1,  5},
		  {-20,-20,-20,-20,-20,-20,-20,-14,-10,-10,-10,-10,-10, -8, -8, -8, -4}}},*/
		{{-15,-15,-15,-15,-15,-15,-15,-10, -4,  1,  1,  1,  2,  3,  3,  4,  7},
		{-20,-20,-20,-20,-20,-20,-20,-14,-10, -4, -3, -3, -3, -3, -2, -1,  1},
		{-20,-20,-20,-20,-20,-20,-20,-14,-10,-10,-10,-10,-10, -8, -8, -8, -7}} ),
		/* 5 */
		new Jnoise3( new int[][]
		/*  {{{-15,-15,-15,-15,-15,-15,-15,-10, -4,  1,  1,  1,  2,  3,  3,  4,  7},
		  {-22,-22,-22,-22,-22,-22,-22,-16,-12, -6, -4, -4, -4, -4, -2, -1,  2},
		  {-24,-24,-24,-24,-24,-24,-24,-18,-14,-12,-12,-12,-12,-10,-10, -9, -5}}},*/
		{{-15,-15,-15,-15,-15,-15,-15,-10, -4,  1,  1,  1,  2,  3,  3,  4,  7},
		{-22,-22,-22,-22,-22,-22,-22,-16,-12, -6, -4, -4, -4, -4, -3, -1,  0},
		{-24,-24,-24,-24,-24,-24,-24,-18,-14,-12,-12,-12,-12,-10,-10, -9, -8}} ),
		/* 6 */
		new Jnoise3( new int[][]
		/*  {{{-15,-15,-15,-15,-15,-15,-15,-10, -4,  1,  1,  1,  2,  3,  3,  4,  7},
		  {-24,-24,-24,-24,-24,-24,-24,-18,-14, -8, -6, -6, -6, -6, -4, -2,  1},
		  {-26,-26,-26,-26,-26,-26,-26,-18,-16,-15,-15,-15,-15,-13,-13,-12, -8}}},*/
		{{-15,-15,-15,-15,-15,-15,-15,-10, -4,  1,  1,  1,  2,  3,  3,  4,  7},
		{-24,-24,-24,-24,-24,-24,-24,-18,-14, -8, -6, -6, -6, -6, -5, -2,  0},
		{-26,-26,-26,-26,-26,-26,-26,-18,-16,-15,-15,-15,-15,-13,-13,-12,-10}} ),
		/* 7 */
		new Jnoise3( new int[][]
		{{-15,-15,-15,-15,-15,-15,-15,-10, -4,  1,  1,  1,  2,  3,  3,  4,  7},
		{-24,-24,-24,-24,-24,-24,-24,-18,-14,-10, -8, -8, -8, -8, -6, -4,  0},
		{-26,-26,-26,-26,-26,-26,-26,-22,-20,-19,-19,-19,-19,-18,-17,-16,-12}} ),
		/* 8 */
		new Jnoise3( new int[][]
		{{-15,-15,-15,-15,-15,-15,-15,-10, -4,  0,  0,  0,  0,  1,  2,  3,  7},
		{-26,-26,-26,-26,-26,-26,-26,-20,-16,-12,-10,-10,-10,-10, -8, -6, -2},
		{-28,-28,-28,-28,-28,-28,-28,-26,-24,-24,-24,-24,-24,-24,-24,-20,-16}} ),
		/* 9 */
		new Jnoise3( new int[][]
		{{-22,-22,-22,-22,-22,-22,-22,-18,-14, -8, -4, -4, -4, -4, -4, -2,  2},
		{-26,-26,-26,-26,-26,-26,-26,-22,-18,-16,-16,-16,-16,-14,-12,-10, -7},
		{-30,-30,-30,-30,-30,-30,-30,-30,-30,-30,-30,-30,-30,-30,-30,-24,-20}} ),
		/* 10 */
		new Jnoise3( new int[][]
		{{-24,-24,-24,-24,-24,-24,-24,-24,-24,-18,-14,-14,-14,-14,-14,-12,-10},
		{-30,-30,-30,-30,-30,-30,-30,-30,-30,-30,-30,-30,-30,-30,-30,-30,-20},
		{-40,-40,-40,-40,-40,-40,-40,-40,-40,-40,-40,-40,-40,-40,-40,-40,-40}} ),
	};

	/** noise bias (impulse block) */
	protected static final Jnoise3 _psy_noisebias_impulse[] = {// [12]
		/*  63     125     250     500      1k      2k      4k      8k     16k*/
		/* -1 */
		new Jnoise3( new int[][]
		{{-10,-10,-10,-10,-10, -4,  0,  0,  4,  8,  8,  8,  8, 10, 12, 14, 20},
		{-30,-30,-30,-30,-26,-20,-16, -8, -6, -6, -2,  2,  2,  3,  6,  6, 15},
		{-30,-30,-30,-30,-30,-24,-20,-14,-10, -6, -8, -8, -6, -6, -6, -4, -2}} ),

		/* 0 */
		new Jnoise3( new int[][]
		/*  {{{-10,-10,-10,-10,-10, -4,  0,  0,  4,  4,  8,  8,  8, 10, 12, 14, 20},
		{-30,-30,-30,-30,-26,-22,-20,-14, -6, -2,  0,  0,  0,  0,  2,  4,  10},
		{-30,-30,-30,-30,-30,-24,-20,-14,-10, -6, -8, -8, -6, -6, -6, -4, -2}}},*/
		{{-10,-10,-10,-10,-10, -4,  0,  0,  4,  4,  8,  8,  8, 10, 12, 14, 20},
		{-30,-30,-30,-30,-26,-22,-20,-14, -6, -2,  0,  0,  0,  0,  2,  3,  6},
		{-30,-30,-30,-30,-30,-24,-20,-14,-10, -6, -8, -8, -6, -6, -6, -4, -2}} ),
		/* 1 */
		new Jnoise3( new int[][]
		{{-12,-12,-12,-12,-12, -8, -6, -4,  0,  4,  4,  4,  4, 10, 12, 14, 20},
		{-30,-30,-30,-30,-26,-22,-20,-14,-10, -6, -4, -4, -2, -2, -2, -2,  2},
		{-30,-30,-30,-30,-26,-22,-20,-14,-10, -8,-10,-10, -8, -8, -8, -6, -4}} ),
		/* 2 */
		new Jnoise3( new int[][]
		{{-14,-14,-14,-14,-14,-10, -8, -6, -2,  2,  2,  2,  2,  8, 10, 10, 16},
		{-30,-30,-30,-30,-26,-22,-20,-14,-10, -6, -6, -6, -4, -4, -4, -2,  0},
		{-30,-30,-30,-30,-26,-22,-20,-14,-10,-10,-10,-10,-10,-10,-10, -8, -4}} ),
		/* 3 */
		new Jnoise3( new int[][]
		{{-14,-14,-14,-14,-14,-10, -8, -6, -2,  2,  2,  2,  2,  6,  8,  8, 14},
		{-30,-30,-30,-30,-26,-22,-20,-14,-10, -6, -6, -6, -4, -4, -4, -2,  0},
		{-30,-30,-30,-30,-26,-22,-20,-14,-10,-10,-10,-10,-10,-10,-10, -8, -4}} ),
		/* 4 */
		new Jnoise3( new int[][]
		{{-16,-16,-16,-16,-16,-12,-10, -6, -2,  0,  0,  0,  0,  4,  6,  6, 12},
		{-30,-30,-30,-30,-26,-22,-20,-14,-10, -6, -6, -6, -4, -4, -4, -2,  0},
		{-30,-30,-30,-30,-26,-22,-20,-14,-10,-10,-10,-10,-10,-10,-10, -8, -4}} ),
		/* 5 */
		new Jnoise3( new int[][]
		{{-20,-20,-20,-20,-20,-18,-14,-10, -4,  0,  0,  0,  0,  4,  4,  6, 11},
		{-32,-32,-32,-32,-28,-24,-22,-16,-10, -6, -8, -8, -6, -6, -6, -4, -2},
		{-34,-34,-34,-34,-30,-26,-24,-18,-14,-12,-12,-12,-12,-12,-10, -9, -5}} ),
		/* 6
		{{{-20,-20,-20,-20,-20,-18,-14,-10, -4,  0,  0,  0,  0,  4,  4,  6, 11},
		{-34,-34,-34,-34,-30,-30,-24,-20,-12,-12,-14,-14,-10, -9, -8, -6, -4},
		{-34,-34,-34,-34,-34,-30,-26,-20,-16,-15,-15,-15,-15,-15,-13,-12, -8}}},*/
		new Jnoise3( new int[][]
		{{-20,-20,-20,-20,-20,-18,-14,-10, -4,  0,  0,  0,  0,  4,  4,  6, 11},
		{-34,-34,-34,-34,-30,-30,-30,-24,-16,-16,-16,-16,-16,-16,-14,-14,-12},
		{-36,-36,-36,-36,-36,-34,-28,-24,-20,-20,-20,-20,-20,-20,-20,-18,-16}} ),
		/* 7 */
		new Jnoise3( new int[][]
		/*  {{{-22,-22,-22,-22,-22,-20,-14,-10, -6,  0,  0,  0,  0,  4,  4,  6, 11},
		{-34,-34,-34,-34,-30,-30,-24,-20,-14,-14,-16,-16,-14,-12,-10,-10,-10},
		{-34,-34,-34,-34,-32,-32,-30,-24,-20,-19,-19,-19,-19,-19,-17,-16,-12}}},*/
		{{-22,-22,-22,-22,-22,-20,-14,-10, -6,  0,  0,  0,  0,  4,  4,  6, 11},
		{-34,-34,-34,-34,-30,-30,-30,-30,-26,-26,-26,-26,-26,-26,-26,-24,-22},
		{-40,-40,-40,-40,-40,-40,-40,-32,-30,-30,-30,-30,-30,-30,-30,-30,-24}} ),
		/* 8 */
		new Jnoise3( new int[][]
		/*  {{{-24,-24,-24,-24,-24,-22,-14,-10, -6, -1, -1, -1, -1,  3,  3,  5, 10},
		{-34,-34,-34,-34,-30,-30,-30,-24,-20,-20,-20,-20,-20,-18,-16,-16,-14},
		{-36,-36,-36,-36,-36,-34,-28,-24,-24,-24,-24,-24,-24,-24,-24,-20,-16}}},*/
		{{-24,-24,-24,-24,-24,-22,-14,-10, -6, -1, -1, -1, -1,  3,  3,  5, 10},
		{-34,-34,-34,-34,-34,-32,-32,-30,-26,-26,-26,-26,-26,-26,-26,-26,-24},
		{-40,-40,-40,-40,-40,-40,-40,-32,-30,-30,-30,-30,-30,-30,-30,-30,-24}} ),
		/* 9 */
		new Jnoise3( new int[][]
		/*  {{{-28,-28,-28,-28,-28,-28,-28,-20,-14, -8, -4, -4, -4, -4, -4, -2,  2},
		{-36,-36,-36,-36,-34,-32,-32,-30,-26,-26,-26,-26,-26,-22,-20,-20,-18},
		{-40,-40,-40,-40,-40,-40,-40,-32,-30,-30,-30,-30,-30,-30,-30,-24,-20}}},*/
		{{-28,-28,-28,-28,-28,-28,-28,-20,-14, -8, -4, -4, -4, -4, -4, -2,  2},
		{-36,-36,-36,-36,-34,-32,-32,-30,-26,-26,-26,-26,-26,-26,-26,-26,-26},
		{-40,-40,-40,-40,-40,-40,-40,-32,-30,-30,-30,-30,-30,-30,-30,-24,-20}} ),
		/* 10 */
		new Jnoise3( new int[][]
		{{-30,-30,-30,-30,-30,-26,-24,-24,-24,-20,-16,-16,-16,-16,-16,-14,-12},
		{-40,-40,-40,-40,-40,-40,-40,-40,-35,-30,-30,-30,-30,-30,-30,-30,-26},
		{-40,-40,-40,-40,-40,-40,-40,-40,-40,-40,-40,-40,-40,-40,-40,-40,-40}} ),
	};

	/** noise bias (padding block) */
	protected static final Jnoise3 _psy_noisebias_padding[] = {// [12]
		/*  63     125     250     500      1k       2k      4k      8k     16k*/

		/* -1 */
		new Jnoise3( new int[][]
		{{-10,-10,-10,-10,-10, -4,  0,  0,  4,  8,  8,  8,  8, 10, 12, 14, 20},
		{-30,-30,-30,-30,-26,-20,-16, -8, -6, -6, -2,  2,  2,  3,  6,  6, 15},
		{-30,-30,-30,-30,-30,-24,-20,-14,-10, -6, -8, -8, -6, -6, -6, -4, -2}} ),

		/* 0 */
		new Jnoise3( new int[][]
		{{-10,-10,-10,-10,-10, -4,  0,  0,  4,  8,  8,  8,  8, 10, 12, 14, 20},
		{-30,-30,-30,-30,-26,-22,-20,-14,-10, -4, -2,  2,  3,  6,  6,  8, 10},
		{-30,-30,-30,-30,-26,-22,-20,-14,-10, -4, -4, -4, -4, -4, -2,  0,  2}} ),
		/* 1 */
		new Jnoise3( new int[][]
		{{-12,-12,-12,-12,-12, -8, -6, -4,  0,  4,  4,  4,  4, 10, 12, 14, 20},
		{-30,-30,-30,-30,-26,-22,-20,-14,-10, -4,  0,  0,  0,  2,  2,  4,  8},
		{-30,-30,-30,-30,-26,-22,-20,-14,-10, -6, -6, -6, -6, -6, -4, -2,  0}} ),
		/* 2 */
		new Jnoise3( new int[][]
		/*  {{{-14,-14,-14,-14,-14,-10, -8, -6, -2,  2,  2,  2,  2,  8, 10, 10, 16},
		    {-30,-30,-30,-30,-26,-22,-20,-14,-10, -4,  0,  0,  0,  2,  2,  4,  8},
		    {-30,-30,-30,-30,-26,-22,-20,-14,-10, -8, -8, -8, -8, -8, -6, -4, -2}}},*/
		{{-14,-14,-14,-14,-14,-10, -8, -6, -2,  2,  2,  2,  2,  8, 10, 10, 16},
		{-30,-30,-30,-30,-26,-22,-20,-14,-10, -6, -1, -1, -1,  0,  0,  2,  6},
		{-30,-30,-30,-30,-26,-22,-20,-14,-10, -8, -8, -8, -8, -8, -6, -4, -2}} ),
		/* 3 */
		new Jnoise3( new int[][]
		{{-14,-14,-14,-14,-14,-10, -8, -6, -2,  2,  2,  2,  2,  6,  8,  8, 14},
		{-30,-30,-30,-30,-26,-22,-20,-14,-10, -6, -1, -1, -1,  0,  0,  2,  6},
		{-30,-30,-30,-30,-26,-22,-20,-14,-10, -8, -8, -8, -8, -8, -6, -4, -2}} ),
		/* 4 */
		new Jnoise3( new int[][]
		{{-16,-16,-16,-16,-16,-12,-10, -6, -2,  0,  0,  0,  0,  4,  6,  6, 12},
		{-30,-30,-30,-30,-26,-22,-20,-14,-10, -6, -1, -1, -1, -1,  0,  2,  6},
		{-30,-30,-30,-30,-26,-22,-20,-14,-10, -8, -8, -8, -8, -8, -6, -4, -2}} ),
		/* 5 */
		new Jnoise3( new int[][]
		{{-20,-20,-20,-20,-20,-18,-14,-10, -4,  0,  0,  0,  0,  4,  6,  6, 12},
		{-32,-32,-32,-32,-28,-24,-22,-16,-12, -6, -3, -3, -3, -3, -2,  0,  4},
		{-34,-34,-34,-34,-30,-26,-24,-18,-14,-10,-10,-10,-10,-10, -8, -5, -3}} ),
		/* 6 */
		new Jnoise3( new int[][]
		{{-20,-20,-20,-20,-20,-18,-14,-10, -4,  0,  0,  0,  0,  4,  6,  6, 12},
		{-34,-34,-34,-34,-30,-30,-24,-20,-14, -8, -4, -4, -4, -4, -3, -1,  4},
		{-34,-34,-34,-34,-34,-30,-26,-20,-16,-13,-13,-13,-13,-13,-11, -8, -6}} ),
		/* 7 */
		new Jnoise3( new int[][]
		{{-20,-20,-20,-20,-20,-18,-14,-10, -4,  0,  0,  0,  0,  4,  6,  6, 12},
		{-34,-34,-34,-34,-30,-30,-30,-24,-16,-10, -8, -6, -6, -6, -5, -3,  1},
		{-34,-34,-34,-34,-32,-32,-28,-22,-18,-16,-16,-16,-16,-16,-14,-12,-10}} ),
		/* 8 */
		new Jnoise3( new int[][]
		{{-22,-22,-22,-22,-22,-20,-14,-10, -4,  0,  0,  0,  0,  3,  5,  5, 11},
		{-34,-34,-34,-34,-30,-30,-30,-24,-16,-12,-10, -8, -8, -8, -7, -5, -2},
		{-36,-36,-36,-36,-36,-34,-28,-22,-20,-20,-20,-20,-20,-20,-20,-16,-14}} ),
		/* 9 */
		new Jnoise3( new int[][]
		{{-28,-28,-28,-28,-28,-28,-28,-20,-14, -8, -2, -2, -2, -2,  0,  2,  6},
		{-36,-36,-36,-36,-34,-32,-32,-24,-16,-12,-12,-12,-12,-12,-10, -8, -5},
		{-40,-40,-40,-40,-40,-40,-40,-32,-26,-24,-24,-24,-24,-24,-24,-20,-18}} ),
		/* 10 */
		new Jnoise3( new int[][]
		{{-30,-30,-30,-30,-30,-26,-24,-24,-24,-20,-12,-12,-12,-12,-12,-10, -8},
		{-40,-40,-40,-40,-40,-40,-40,-40,-35,-30,-25,-25,-25,-25,-25,-25,-15},
		{-40,-40,-40,-40,-40,-40,-40,-40,-40,-40,-40,-40,-40,-40,-40,-40,-40}} ),
	};

	protected static final Jnoiseguard _psy_noiseguards_44[] = {// [4]
		new Jnoiseguard( 3, 3, 15 ),
		new Jnoiseguard( 3,3,15 ),
		new Jnoiseguard( 10,10,100 ),
		new Jnoiseguard( 10,10,100 ),
	};

	protected static final int _psy_tone_suppress[] = {// [12]
		-20,-20,-20,-20,-20,-24,-30,-40,-40,-45,-45,-45,
	};

	protected static final int _psy_tone_0dB[] = {// [12]
		90,90,95,95,95,95,105,105,105,105,105,105,
	};

	protected static final int _psy_noise_suppress[] = {// [12]
		-20,-20,-24,-24,-24,-24,-30,-40,-40,-45,-45,-45,
	};

	public static final Jvorbis_info_psy _psy_info_template = new Jvorbis_info_psy(
		/** blockflag */
		-1,
		/** ath_adjatt, ath_maxatt */
		-140.f,-140.f,
		/** tonemask att boost/decay,suppr,curves */
		new float[]{0.f,0.f,0.f},     0.f,0.f,    -40.f, new float[]{0.f},

		/**noisemaskp,supp, low/high window, low/hi guard, minimum */
		true,         -0.f,           .5f, .5f,         0,0,0,
		/** noiseoffset*3, noisecompand, max_curve_dB */
		new float[][]{{-1},{-1},{-1}},new float[]{-1},105.f,
		/** noise normalization - noise_p, start, partition, thresh. */
		false,-1,-1,0.
	);

	/** ath ****************/
	protected static final int _psy_ath_floater[] = {// [12]
		-100,-100,-100,-100,-100,-100,-105,-105,-105,-105,-110,-120,
	};

	protected static final int _psy_ath_abs[] = {// [12]
		-130,-130,-130,-130,-140,-140,-140,-140,-140,-140,-140,-150,
	};

	/* stereo setup.  These don't map directly to quality level, there's
	   an additional indirection as several of the below may be used in a
	   single bitmanaged stream

	****************/

	/* various stereo possibilities */

	/** stereo mode by base quality level */
	protected static final Jadj_stereo _psy_stereo_modes_44[] = {// [12]
					/* 0     1    2    3   4     5   6   7   8   9  10  11  12  13  14  -1  */
	new Jadj_stereo(
		new int[]   {  4,    4,  4,    4,  4,    4,  4,  3,  2,  2,  1,  0,  0,  0,  0},
		new int[]   {  8,    8,  8,    8,  8,    8,  8,  8,  8,  8,  8,  8,  5,  4,  3},
		new float[] {  1,   2f,  3,   4f,  4,    4,  4,  4,  4,  5,  6,  7,  8,  8,  8},
		new float[] { 12,12.5f, 13,13.5f, 14,14.5f, 15, 99, 99, 99, 99, 99, 99, 99, 99} ),
					/* 0     1    2    3   4     5   6   7   8      10  11  12  13  14   0  */
	new Jadj_stereo(
		new int[]   {  4,    4,  4,    4,  4,    4,  4,  3,  2,  1,  0,  0,  0,  0,  0},
		new int[]   {  8,    8,  8,    8,  6,    6,  5,  5,  5,  5,  5,  5,  5,  4,  3},
		new float[] {  1,   2f,  3,   4f,  4,   5f,  6,  6,  6,  6,  6,  8,  8,  8,  8},
		new float[] { 12,12.5f, 13,13.5f, 14,14.5f, 15, 99, 99, 99, 99, 99, 99, 99, 99} ),
					/* 0   1   2   3   4   5   6   7   8   9  10  11  12  13  14         1  */
	new Jadj_stereo(
		new int[]   {  3,  3,  3,  3,  3,  3,  3,  3,  2,  1,  0,  0,  0,  0,  0},
		new int[]   {  8,  8,  8,  8,  6,  6,  5,  5,  5,  5,  5,  5,  5,  4,  3},
		new float[] {  1,  2,  3,  4,  4,  5,  6,  6,  6,  6,  6,  8,  8,  8,  8},
		new float[] { 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99} ),
					/* 0   1   2   3   4   5   6   7   8   9  10  11  12  13  14         2  */
	new Jadj_stereo(
		new int[]   {  3,  3,  3,  3,  3,  3,  3,  2,  1,  1,  0,  0,  0,  0,  0},
		new int[]   {  8,  8,  6,  6,  5,  5,  4,  4,  4,  4,  4,  4,  3,  2,  1},
		new float[] {  3,  4,  4,  5,  5,  6,  6,  6,  6,  6,  6,  8,  8,  8,  8},
		new float[] { 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99} ),
					/* 0   1   2   3   4   5   6   7   8   9  10  11  12  13  14         3  */
	new Jadj_stereo(
		new int[]   {  2,  2,  2,  2,  2,  1,  1,  1,  1,  0,  0,  0,  0,  0,  0},
		new int[]   {  5,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  3,  2,  1},
		new float[] {  4,  4,  5,  6,  6,  6,  6,  6,  8,  8, 10, 10, 10, 10, 10},
		new float[] { 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99} ),
					/* 0   1   2   3   4   5   6   7   8   9  10  11  12  13  14         4  */
	new Jadj_stereo(
		new int[] {  2,  2,  2,  1,  1,  1,  0,  0,  0,  0,  0,  0,  0,  0,  0},
		new int[] {  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  3,  3,  2,  1,  0},
		new float[] {  6,  6,  6,  8,  8,  8,  8,  8,  8,  8, 10, 10, 10, 10, 10},
		new float[] { 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99} ),
					/* 0   1   2   3   4   5   6   7   8   9  10  11  12  13  14         5  */
	new Jadj_stereo(
		new int[]   {  2,  2,  2,  1,  1,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},
		new int[]   {  3,  3,  3,  3,  3,  2,  2,  2,  2,  2,  2,  0,  0,  0,  0},
		new float[] {  6,  7,  8,  8,  8, 10, 10, 12, 12, 12, 12, 12, 12, 12, 12},
		new float[] { 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99} ),
					/* 0   1   2   3   4   5   6   7   8   9  10  11  12  13  14         6  */
	new Jadj_stereo(
		new int[]   {  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},
		new int[]   {  3,  3,  3,  2,  2,  2,  0,  0,  0,  0,  0,  0,  0,  0,  0},
		new float[] {  8,  8,  8, 10, 10, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12},
		new float[] { 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99} ),
					/* 0   1   2   3   4   5   6   7   8   9  10  11  12  13  14         7  */
	new Jadj_stereo(
		new int[]   {  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},
		new int[]   {  3,  3,  3,  2,  2,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},
		new float[] {  8,  8, 10, 10, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12},
		new float[] { 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99} ),
					/* 0   1   2   3   4   5   6   7   8   9  10  11  12  13  14         8  */
	new Jadj_stereo(
		new int[]   {  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},
		new int[]   {  2,  2,  2,  2,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},
		new float[] {  8, 10, 10, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12},
		new float[] { 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99} ),
					/* 0   1   2   3   4   5   6   7   8   9  10  11  12  13  14         9  */
	new Jadj_stereo(
		new int[]   {  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},
		new int[]   {  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},
		new float[] {  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4},
		new float[] { 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99} ),
					/* 0   1   2   3   4   5   6   7   8   9  10  11  12  13  14        10  */
	new Jadj_stereo(
		new int[]   {  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},
		new int[]   {  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},
		new float[] {  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4},
		new float[] { 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99} ),
	};

	/** tone master attenuation by base quality mode and bitrate tweak */
	protected static final Jatt3 _psy_tone_masteratt_44[] = {// [12]
		new Jatt3( new int[] { 35,  21,   9},  0,    0 ), /* -1 */
		new Jatt3( new int[] { 30,  20,   8}, -2, 1.25f ), /* 0 */
		/*  {{ 25,  14,   4},  0,    0}, *//* 1 */
		new Jatt3( new int[] { 25,  12,   2},  0,    0 ), /* 1 */
		/*  {{ 20,  10,  -2},  0,    0}, *//* 2 */
		new Jatt3( new int[] { 20,   9,  -3},  0,    0 ), /* 2 */
		new Jatt3( new int[] { 20,   9,  -4},  0,    0 ), /* 3 */
		new Jatt3( new int[] { 20,   9,  -4},  0,    0 ), /* 4 */
		new Jatt3( new int[] { 20,   6,  -6},  0,    0 ), /* 5 */
		new Jatt3( new int[] { 20,   3, -10},  0,    0 ), /* 6 */
		new Jatt3( new int[] { 18,   1, -14},  0,    0 ), /* 7 */
		new Jatt3( new int[] { 18,   0, -16},  0,    0 ), /* 8 */
		new Jatt3( new int[] { 18,  -2, -16},  0,    0 ), /* 9 */
		new Jatt3( new int[] { 12,  -2, -20},  0,    0 ), /* 10 */
	};

	/** lowpass by mode **************/
	protected static final double _psy_lowpass_44[] = {// [12]
		/*  15.1,15.8,16.5,17.9,20.5,48.,999.,999.,999.,999.,999. */
		13.9,15.1,15.8,16.5,17.2,18.9,20.1,48.,999.,999.,999.,999.
	};

	/** noise normalization **********/

	protected static final int _noise_start_short_44[] = {// [11]
		/*  16,16,16,16,32,32,9999,9999,9999,9999 */
		32,16,16,16,32,9999,9999,9999,9999,9999,9999
	};

	protected static final int _noise_start_long_44[] = {// [11]
		/*  128,128,128,256,512,512,9999,9999,9999,9999 */
		256,128,128,256,512,9999,9999,9999,9999,9999,9999
	};

	protected static final int _noise_part_short_44[] = {// [11]
		8,8,8,8,8,8,8,8,8,8,8
	};

	protected static final int _noise_part_long_44[] = {// [11]
		32,32,32,32,32,32,32,32,32,32,32
	};

	protected static final double _noise_thresh_44[] = {// [11]
		/*  .2,.2,.3,.4,.5,.5,9999.,9999.,9999.,9999., */
		.2,.2,.2,.4,.6,9999.,9999.,9999.,9999.,9999.,9999.,
	};

	protected static final double _noise_thresh_5only[] = {// [2]
		.5,.5,
	};
}