package libvorbis.modes;

import libvorbis.Jve_setup_data_template;

/** 8kHz settings */

public final class Jsetup_8 {

	protected static final int blocksize_8[] = {// [2]
		512,512
	};

	private static final int _floor_mapping_8a[] = {
		6,6
	};

	protected static final int _floor_mapping_8[][] = {
		_floor_mapping_8a
	};

	private static final double rate_mapping_8[] = {// [3]
		6000.,9000.,32000.,
	};

	private static final double rate_mapping_8_uncoupled[] = {// [3]
		8000.,14000.,42000.,
	};

	protected static final double quality_mapping_8[] = {// [3]
		-.1,.0,1.
	};

	protected static final double _psy_compand_8_mapping[] = { 0., 1., 1.};// [3]

	protected static final double _global_mapping_8[] = { 1., 2., 3. };// [3]

	public static final Jve_setup_data_template ve_setup_8_stereo = new Jve_setup_data_template(
		2,
		rate_mapping_8,
		quality_mapping_8,
		2,
		8000,
		9000,

		blocksize_8,
		blocksize_8,

		Jpsych_8._psy_tone_masteratt_8,
		Jpsych_44._psy_tone_0dB,
		Jpsych_44._psy_tone_suppress,

		Jpsych_8._vp_tonemask_adj_8,
		null,
		Jpsych_8._vp_tonemask_adj_8,

		Jpsych_8._psy_noiseguards_8,
		Jpsych_8._psy_noisebias_8,
		Jpsych_8._psy_noisebias_8,
		null,
		null,
		Jpsych_44._psy_noise_suppress,

		Jpsych_8._psy_compand_8,
		_psy_compand_8_mapping,
		null,

		new int[][]{Jpsych_8._noise_start_8,Jpsych_8._noise_start_8},
		new int[][]{Jpsych_8._noise_part_8,Jpsych_8._noise_part_8},
		Jpsych_44._noise_thresh_5only,

		Jpsych_8._psy_ath_floater_8,
		Jpsych_8._psy_ath_abs_8,

		Jpsych_8._psy_lowpass_8,

		Jpsych_44._psy_global_44,
		_global_mapping_8,
		Jpsych_8._psy_stereo_modes_8,

		Jfloor_all._floor_books,
		Jfloor_all._floor,
		1,
		_floor_mapping_8,

		Jresidue_8._mapres_template_8_stereo
	);

	public static final Jve_setup_data_template ve_setup_8_uncoupled = new Jve_setup_data_template(
		2,
		rate_mapping_8_uncoupled,
		quality_mapping_8,
		-1,
		8000,
		9000,

		blocksize_8,
		blocksize_8,

		Jpsych_8._psy_tone_masteratt_8,
		Jpsych_44._psy_tone_0dB,
		Jpsych_44._psy_tone_suppress,

		Jpsych_8._vp_tonemask_adj_8,
		null,
		Jpsych_8._vp_tonemask_adj_8,

		Jpsych_8._psy_noiseguards_8,
		Jpsych_8._psy_noisebias_8,
		Jpsych_8._psy_noisebias_8,
		null,
		null,
		Jpsych_44._psy_noise_suppress,

		Jpsych_8._psy_compand_8,
		_psy_compand_8_mapping,
		null,

		new int[][]{Jpsych_8._noise_start_8,Jpsych_8._noise_start_8},
		new int[][]{Jpsych_8._noise_part_8,Jpsych_8._noise_part_8},
		Jpsych_44._noise_thresh_5only,

		Jpsych_8._psy_ath_floater_8,
		Jpsych_8._psy_ath_abs_8,

		Jpsych_8._psy_lowpass_8,

		Jpsych_44._psy_global_44,
		_global_mapping_8,
		Jpsych_8._psy_stereo_modes_8,

		Jfloor_all._floor_books,
		Jfloor_all._floor,
		1,
		_floor_mapping_8,

		Jresidue_8._mapres_template_8_uncoupled
	);
}
