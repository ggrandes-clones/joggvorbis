package libvorbis;

/**
codec_setup_info contains all the setup information specific to the
specific compression/decompression mode in progress (eg,
psychoacoustic settings, channel setup, options, codebook
etc).
*/
final class Jcodec_setup_info {
	/** Vorbis supports only short and long blocks, but allows the
	encoder to choose the sizes */
	final int[] blocksizes = new int[2];

	/** modes are the primary means of supporting on-the-fly different
	blocksizes, different channel mappings (LR or M/A),
	different residue backends, etc.  Each mode consists of a
	blocksize flag and a mapping (along with the mapping setup */
	int modes    = 0;
	int maps     = 0;
	int floors   = 0;
	int residues = 0;
	int books    = 0;
	/** encode only */
	int psys     = 0;

	final Jvorbis_info_mode[] mode_param = new Jvorbis_info_mode[64];
	final int[]               map_type   = new int[64];
	final Jvorbis_info_mapping[] map_param = new Jvorbis_info_mapping[64];
	final int[]                 floor_type = new int[64];
	final Jvorbis_info_floor[] floor_param = new Jvorbis_info_floor[64];
	final int[]               residue_type = new int[64];
	final Jvorbis_info_residue[] residue_param = new Jvorbis_info_residue[64];
	final Jstatic_codebook[]        book_param = new Jstatic_codebook[256];
	Jcodebook[] fullbooks;

	/** encode only */
	final Jvorbis_info_psy[]        psy_param = new Jvorbis_info_psy[4];
	final Jvorbis_info_psy_global psy_g_param = new Jvorbis_info_psy_global();

	final Jbitrate_manager_info bi = new Jbitrate_manager_info();
	/** used only by vorbisenc.c.  It's a
 	 * highly redundant structure, but
 	 * improves clarity of program flow. */
	final Jhighlevel_encode_setup hi = new Jhighlevel_encode_setup();
	/** painless downsample for decode. 1 or 0 */
	int halfrate_flag = 0;// java: not boolean, because uses as int value
}
