package libvorbis.modes;

import libvorbis.Jve_setup_data_template;

/** 11kHz settings */

public final class Jsetup_11 {

	private static final int blocksize_11[] = {// [2]
		512,512
	};

	private static final int _floor_mapping_11a[] = {
		6,6
	};

	private static final int _floor_mapping_11[][] = {
		_floor_mapping_11a
	};

	private static final double rate_mapping_11[] = {// [3]
		8000.,13000.,44000.,
	};

	private static final double rate_mapping_11_uncoupled[] = {// [3]
		12000.,20000.,50000.,
	};

	private static final double quality_mapping_11[] = {// [3]
		-.1,.0,1.
	};

	public static final Jve_setup_data_template ve_setup_11_stereo = new Jve_setup_data_template(
		2,
		rate_mapping_11,
		quality_mapping_11,
		2,
		9000,
		15000,

		blocksize_11,
		blocksize_11,

		Jpsych_11._psy_tone_masteratt_11,
		Jpsych_44._psy_tone_0dB,
		Jpsych_44._psy_tone_suppress,

		Jpsych_11._vp_tonemask_adj_11,
		null,
		Jpsych_11._vp_tonemask_adj_11,

		Jpsych_8._psy_noiseguards_8,
		Jpsych_11._psy_noisebias_11,
		Jpsych_11._psy_noisebias_11,
		null,
		null,
		Jpsych_44._psy_noise_suppress,

		Jpsych_8._psy_compand_8,
		Jsetup_8._psy_compand_8_mapping,
		null,

		new int[][]{Jpsych_8._noise_start_8,Jpsych_8._noise_start_8},
		new int[][]{Jpsych_8._noise_part_8,Jpsych_8._noise_part_8},
		Jpsych_11._noise_thresh_11,

		Jpsych_8._psy_ath_floater_8,
		Jpsych_8._psy_ath_abs_8,

		Jpsych_11._psy_lowpass_11,

		Jpsych_44._psy_global_44,
		Jsetup_8._global_mapping_8,
		Jpsych_8._psy_stereo_modes_8,

		Jfloor_all._floor_books,
		Jfloor_all._floor,
		1,
		_floor_mapping_11,

		Jresidue_8._mapres_template_8_stereo
	);

	public static final Jve_setup_data_template ve_setup_11_uncoupled = new Jve_setup_data_template(
		2,
		rate_mapping_11_uncoupled,
		quality_mapping_11,
		-1,
		9000,
		15000,

		blocksize_11,
		blocksize_11,

		Jpsych_11._psy_tone_masteratt_11,
		Jpsych_44._psy_tone_0dB,
		Jpsych_44._psy_tone_suppress,

		Jpsych_11._vp_tonemask_adj_11,
		null,
		Jpsych_11._vp_tonemask_adj_11,

		Jpsych_8._psy_noiseguards_8,
		Jpsych_11._psy_noisebias_11,
		Jpsych_11._psy_noisebias_11,
		null,
		null,
		Jpsych_44._psy_noise_suppress,

		Jpsych_8._psy_compand_8,
		Jsetup_8._psy_compand_8_mapping,
		null,

		new int[][]{Jpsych_8._noise_start_8,Jpsych_8._noise_start_8},
		new int[][]{Jpsych_8._noise_part_8,Jpsych_8._noise_part_8},
		Jpsych_11._noise_thresh_11,

		Jpsych_8._psy_ath_floater_8,
		Jpsych_8._psy_ath_abs_8,

		Jpsych_11._psy_lowpass_11,

		Jpsych_44._psy_global_44,
		Jsetup_8._global_mapping_8,
		Jpsych_8._psy_stereo_modes_8,

		Jfloor_all._floor_books,
		Jfloor_all._floor,
		1,
		_floor_mapping_11,

		Jresidue_8._mapres_template_8_uncoupled
	);
}
