package libvorbis.modes;

import libvorbis.Jve_setup_data_template;

/** catch-all toplevel settings for q modes only */

public final class Jsetup_X {

	private static final double rate_mapping_X[] = {// [12]
		-1.,-1.,-1.,-1.,-1.,-1.,
		-1.,-1.,-1.,-1.,-1.,-1.
	};

	public static final Jve_setup_data_template ve_setup_X_stereo = new Jve_setup_data_template(
		11,
		rate_mapping_X,
		Jsetup_44.quality_mapping_44,
		2,
		50000,
		200000,

		Jsetup_44.blocksize_short_44,
		Jsetup_44.blocksize_long_44,

		Jpsych_44._psy_tone_masteratt_44,
		Jpsych_44._psy_tone_0dB,
		Jpsych_44._psy_tone_suppress,

		Jpsych_44._vp_tonemask_adj_otherblock,
		Jpsych_44._vp_tonemask_adj_longblock,
		Jpsych_44._vp_tonemask_adj_otherblock,

		Jpsych_44._psy_noiseguards_44,
		Jpsych_44._psy_noisebias_impulse,
		Jpsych_44._psy_noisebias_padding,
		Jpsych_44._psy_noisebias_trans,
		Jpsych_44._psy_noisebias_long,
		Jpsych_44._psy_noise_suppress,

		Jpsych_44._psy_compand_44,
		Jsetup_44._psy_compand_short_mapping,
		Jsetup_44._psy_compand_long_mapping,

		new int[][]{Jpsych_44._noise_start_short_44, Jpsych_44._noise_start_long_44},
		new int[][]{Jpsych_44._noise_part_short_44, Jpsych_44._noise_part_long_44},
		Jpsych_44._noise_thresh_44,

		Jpsych_44._psy_ath_floater,
		Jpsych_44._psy_ath_abs,

		Jpsych_44._psy_lowpass_44,

		Jpsych_44._psy_global_44,
		Jsetup_44._global_mapping_44,
		Jpsych_44._psy_stereo_modes_44,

		Jfloor_all._floor_books,
		Jfloor_all._floor,
		2,
		Jsetup_44._floor_mapping_44,

		Jresidue_44._mapres_template_44_stereo
	);

	public static final Jve_setup_data_template ve_setup_X_uncoupled = new Jve_setup_data_template(
		11,
		rate_mapping_X,
		Jsetup_44.quality_mapping_44,
		-1,
		50000,
		200000,

		Jsetup_44.blocksize_short_44,
		Jsetup_44.blocksize_long_44,

		Jpsych_44._psy_tone_masteratt_44,
		Jpsych_44._psy_tone_0dB,
		Jpsych_44._psy_tone_suppress,

		Jpsych_44._vp_tonemask_adj_otherblock,
		Jpsych_44._vp_tonemask_adj_longblock,
		Jpsych_44._vp_tonemask_adj_otherblock,

		Jpsych_44._psy_noiseguards_44,
		Jpsych_44._psy_noisebias_impulse,
		Jpsych_44._psy_noisebias_padding,
		Jpsych_44._psy_noisebias_trans,
		Jpsych_44._psy_noisebias_long,
		Jpsych_44._psy_noise_suppress,

		Jpsych_44._psy_compand_44,
		Jsetup_44._psy_compand_short_mapping,
		Jsetup_44._psy_compand_long_mapping,

		new int[][]{Jpsych_44._noise_start_short_44,Jpsych_44._noise_start_long_44},
		new int[][]{Jpsych_44._noise_part_short_44,Jpsych_44._noise_part_long_44},
		Jpsych_44._noise_thresh_44,

		Jpsych_44._psy_ath_floater,
		Jpsych_44._psy_ath_abs,

		Jpsych_44._psy_lowpass_44,

		Jpsych_44._psy_global_44,
		Jsetup_44._global_mapping_44,
		null,

		Jfloor_all._floor_books,
		Jfloor_all._floor,
		2,
		Jsetup_44._floor_mapping_44,

		Jresidue_44u._mapres_template_44_uncoupled
	);

	public static final Jve_setup_data_template ve_setup_XX_stereo = new Jve_setup_data_template(
		2,
		rate_mapping_X,
		Jsetup_8.quality_mapping_8,
		2,
		0,
		8000,

		Jsetup_8.blocksize_8,
		Jsetup_8.blocksize_8,

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
		Jsetup_8._psy_compand_8_mapping,
		null,

		new int[][]{Jpsych_8._noise_start_8,Jpsych_8._noise_start_8},
		new int[][]{Jpsych_8._noise_part_8,Jpsych_8._noise_part_8},
		Jpsych_44._noise_thresh_5only,

		Jpsych_8._psy_ath_floater_8,
		Jpsych_8._psy_ath_abs_8,

		Jpsych_8._psy_lowpass_8,

		Jpsych_44._psy_global_44,
		Jsetup_8._global_mapping_8,
		Jpsych_8._psy_stereo_modes_8,

		Jfloor_all._floor_books,
		Jfloor_all._floor,
		1,
		Jsetup_8._floor_mapping_8,

		Jresidue_8._mapres_template_8_stereo
	);

	public static final Jve_setup_data_template ve_setup_XX_uncoupled = new Jve_setup_data_template(
		2,
		rate_mapping_X,
		Jsetup_8.quality_mapping_8,
		-1,
		0,
		8000,

		Jsetup_8.blocksize_8,
		Jsetup_8.blocksize_8,

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
		Jsetup_8._psy_compand_8_mapping,
		null,

		new int[][]{Jpsych_8._noise_start_8,Jpsych_8._noise_start_8},
		new int[][]{Jpsych_8._noise_part_8,Jpsych_8._noise_part_8},
		Jpsych_44._noise_thresh_5only,

		Jpsych_8._psy_ath_floater_8,
		Jpsych_8._psy_ath_abs_8,

		Jpsych_8._psy_lowpass_8,

		Jpsych_44._psy_global_44,
		Jsetup_8._global_mapping_8,
		Jpsych_8._psy_stereo_modes_8,

		Jfloor_all._floor_books,
		Jfloor_all._floor,
		1,
		Jsetup_8._floor_mapping_8,

		Jresidue_8._mapres_template_8_uncoupled
	);
}
