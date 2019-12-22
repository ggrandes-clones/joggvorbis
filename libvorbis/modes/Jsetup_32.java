package libvorbis.modes;

import libvorbis.Jve_setup_data_template;

/** toplevel settings for 32kHz */

public final class Jsetup_32 {

	private static final double rate_mapping_32[] = {// [12]
		18000.,28000.,35000.,45000.,56000.,60000.,
		75000.,90000.,100000.,115000.,150000.,190000.,
	};

	private static final double rate_mapping_32_un[] = {// [12]
		30000.,42000.,52000.,64000.,72000.,78000.,
		86000.,92000.,110000.,120000.,140000.,190000.,
	};

	private static final double _psy_lowpass_32[] = {// [12]
		12.3,13.,13.,14.,15.,99.,99.,99.,99.,99.,99.,99.
	};

	public static final Jve_setup_data_template ve_setup_32_stereo = new Jve_setup_data_template(
		11,
		rate_mapping_32,
		Jsetup_44.quality_mapping_44,
		2,
		26000,
		40000,

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

		_psy_lowpass_32,

		Jpsych_44._psy_global_44,
		Jsetup_44._global_mapping_44,
		Jpsych_44._psy_stereo_modes_44,

		Jfloor_all._floor_books,
		Jfloor_all._floor,
		2,
		Jsetup_44._floor_mapping_44,

		Jresidue_44._mapres_template_44_stereo
	);

	public static final Jve_setup_data_template ve_setup_32_uncoupled = new Jve_setup_data_template(
		11,
		rate_mapping_32_un,
		Jsetup_44.quality_mapping_44,
		-1,
		26000,
		40000,

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

		_psy_lowpass_32,

		Jpsych_44._psy_global_44,
		Jsetup_44._global_mapping_44,
		null,

		Jfloor_all._floor_books,
		Jfloor_all._floor,
		2,
		Jsetup_44._floor_mapping_44,

		Jresidue_44u._mapres_template_44_uncoupled
	);
}
