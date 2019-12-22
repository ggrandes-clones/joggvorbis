package libvorbis.modes;

import libvorbis.Jve_setup_data_template;

/** toplevel settings for 44.1/48kHz 5.1 surround modes */

public final class Jsetup_44p51 {

	private static final double rate_mapping_44p51[] = {// [12]
		14000.,20000.,28000.,38000.,46000.,54000.,
		75000.,96000.,120000.,140000.,180000.,240001.
	};

	public static final Jve_setup_data_template ve_setup_44_51 = new Jve_setup_data_template(
		11,
		rate_mapping_44p51,
		Jsetup_44.quality_mapping_44,
		6,
		40000,
		70000,

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
		3,
		Jsetup_44._floor_mapping_44,

		Jresidue_44p51._mapres_template_44_51
	);
}