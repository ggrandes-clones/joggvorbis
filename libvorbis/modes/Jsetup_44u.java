package libvorbis.modes;

import libvorbis.Jve_setup_data_template;

/** toplevel settings for 44.1/48kHz uncoupled modes */

public final class Jsetup_44u {

	private static final double rate_mapping_44_un[] = {// [12]
		32000.,48000.,60000.,70000.,80000.,86000.,
		96000.,110000.,120000.,140000.,160000.,240001.
	};

	public static final Jve_setup_data_template ve_setup_44_uncoupled = new Jve_setup_data_template(
		11,
		rate_mapping_44_un,
		Jsetup_44.quality_mapping_44,
		-1,
		40000,
		50000,

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
		Jpsych_44._psy_stereo_modes_44,

		Jfloor_all._floor_books,
		Jfloor_all._floor,
		2,
		Jsetup_44._floor_mapping_44,

		Jresidue_44u._mapres_template_44_uncoupled
	);
}