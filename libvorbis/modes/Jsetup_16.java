package libvorbis.modes;

import libvorbis.Jve_setup_data_template;

/** 16kHz settings */

public final class Jsetup_16 {

	protected static final int blocksize_16_short[] = {// [3]
		1024,512,512
	};

	protected static final int blocksize_16_long[] = {// [3]
		1024,1024,1024
	};

	private static final int _floor_mapping_16a[] = {
		9,3,3
	};

	private static final int _floor_mapping_16b[] = {
		9,9,9
	};

	protected static final int _floor_mapping_16[][] = {
		_floor_mapping_16a,
		_floor_mapping_16b
	};

	private static final double rate_mapping_16[] = {// [4]
		12000.,20000.,44000.,86000.
	};

	private static final double rate_mapping_16_uncoupled[] = {// [4]
		16000.,28000.,64000.,100000.
	};

	protected static final double _global_mapping_16[] = { 1., 2., 3., 4. };// [4]

	protected static final double quality_mapping_16[] = { -.1,.05,.5,1. };// [4]

	protected static final double _psy_compand_16_mapping[] = { 0., .8, 1., 1.};// [4]

	public static final Jve_setup_data_template ve_setup_16_stereo = new Jve_setup_data_template(
		3,
		rate_mapping_16,
		quality_mapping_16,
		2,
		15000,
		19000,

		blocksize_16_short,
		blocksize_16_long,

		Jpsych_16._psy_tone_masteratt_16,
		Jpsych_44._psy_tone_0dB,
		Jpsych_44._psy_tone_suppress,

		Jpsych_16._vp_tonemask_adj_16,
		Jpsych_16._vp_tonemask_adj_16,
		Jpsych_16._vp_tonemask_adj_16,

		Jpsych_16._psy_noiseguards_16,
		Jpsych_16._psy_noisebias_16_impulse,
		Jpsych_16._psy_noisebias_16_short,
		Jpsych_16._psy_noisebias_16_short,
		Jpsych_16._psy_noisebias_16,
		Jpsych_44._psy_noise_suppress,

		Jpsych_8._psy_compand_8,
		_psy_compand_16_mapping,
		_psy_compand_16_mapping,

		new int[][]{Jpsych_16._noise_start_16,Jpsych_16._noise_start_16},
		new int[][]{Jpsych_16._noise_part_16,Jpsych_16._noise_part_16},
		Jpsych_16._noise_thresh_16,

		Jpsych_16._psy_ath_floater_16,
		Jpsych_16._psy_ath_abs_16,

		Jpsych_16._psy_lowpass_16,

		Jpsych_44._psy_global_44,
		_global_mapping_16,
		Jpsych_16._psy_stereo_modes_16,

		Jfloor_all._floor_books,
		Jfloor_all._floor,
		2,
		_floor_mapping_16,

		Jresidue_16._mapres_template_16_stereo
	);

	public static final Jve_setup_data_template ve_setup_16_uncoupled = new Jve_setup_data_template(
		3,
		rate_mapping_16_uncoupled,
		quality_mapping_16,
		-1,
		15000,
		19000,

		blocksize_16_short,
		blocksize_16_long,

		Jpsych_16._psy_tone_masteratt_16,
		Jpsych_44._psy_tone_0dB,
		Jpsych_44._psy_tone_suppress,

		Jpsych_16._vp_tonemask_adj_16,
		Jpsych_16._vp_tonemask_adj_16,
		Jpsych_16._vp_tonemask_adj_16,

		Jpsych_16._psy_noiseguards_16,
		Jpsych_16._psy_noisebias_16_impulse,
		Jpsych_16._psy_noisebias_16_short,
		Jpsych_16._psy_noisebias_16_short,
		Jpsych_16._psy_noisebias_16,
		Jpsych_44._psy_noise_suppress,

		Jpsych_8._psy_compand_8,
		_psy_compand_16_mapping,
		_psy_compand_16_mapping,

		new int[][]{Jpsych_16._noise_start_16,Jpsych_16._noise_start_16},
		new int[][]{Jpsych_16._noise_part_16,Jpsych_16._noise_part_16},
		Jpsych_16._noise_thresh_16,

		Jpsych_16._psy_ath_floater_16,
		Jpsych_16._psy_ath_abs_16,

		Jpsych_16._psy_lowpass_16,

		Jpsych_44._psy_global_44,
		_global_mapping_16,
		Jpsych_16._psy_stereo_modes_16,

		Jfloor_all._floor_books,
		Jfloor_all._floor,
		2,
		_floor_mapping_16,

		Jresidue_16._mapres_template_16_uncoupled
	);
}
