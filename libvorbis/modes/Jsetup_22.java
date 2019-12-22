package libvorbis.modes;

import libvorbis.Jve_setup_data_template;

/** 22kHz settings */

public final class Jsetup_22 {

	private static final double rate_mapping_22[] = {// [4]
		15000.,20000.,44000.,86000.
	};

	private static final double rate_mapping_22_uncoupled[] = {// [4]
		16000.,28000.,50000.,90000.
	};

	private static final double _psy_lowpass_22[] = {9.5,11.,30.,99.};// [4]

	public static final Jve_setup_data_template ve_setup_22_stereo = new Jve_setup_data_template(
		3,
		rate_mapping_22,
		Jsetup_16.quality_mapping_16,
		2,
		19000,
		26000,

		Jsetup_16.blocksize_16_short,
		Jsetup_16.blocksize_16_long,

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
		Jsetup_16._psy_compand_16_mapping,
		Jsetup_16._psy_compand_16_mapping,

		new int[][]{Jpsych_16._noise_start_16,Jpsych_16._noise_start_16},
		new int[][]{Jpsych_16._noise_part_16,Jpsych_16._noise_part_16},
		Jpsych_16._noise_thresh_16,

		Jpsych_16._psy_ath_floater_16,
		Jpsych_16._psy_ath_abs_16,

		_psy_lowpass_22,

		Jpsych_44._psy_global_44,
		Jsetup_16._global_mapping_16,
		Jpsych_16._psy_stereo_modes_16,

		Jfloor_all._floor_books,
		Jfloor_all._floor,
		2,
		Jsetup_16._floor_mapping_16,

		Jresidue_16._mapres_template_16_stereo
	);

	public static final Jve_setup_data_template ve_setup_22_uncoupled = new Jve_setup_data_template(
		3,
		rate_mapping_22_uncoupled,
		Jsetup_16.quality_mapping_16,
		-1,
		19000,
		26000,

		Jsetup_16.blocksize_16_short,
		Jsetup_16.blocksize_16_long,

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
		Jsetup_16._psy_compand_16_mapping,
		Jsetup_16._psy_compand_16_mapping,

		new int[][]{Jpsych_16._noise_start_16,Jpsych_16._noise_start_16},
		new int[][]{Jpsych_16._noise_part_16,Jpsych_16._noise_part_16},
		Jpsych_16._noise_thresh_16,

		Jpsych_16._psy_ath_floater_16,
		Jpsych_16._psy_ath_abs_16,

		_psy_lowpass_22,

		Jpsych_44._psy_global_44,
		Jsetup_16._global_mapping_16,
		Jpsych_16._psy_stereo_modes_16,

		Jfloor_all._floor_books,
		Jfloor_all._floor,
		2,
		Jsetup_16._floor_mapping_16,

		Jresidue_16._mapres_template_16_uncoupled
	);
}
