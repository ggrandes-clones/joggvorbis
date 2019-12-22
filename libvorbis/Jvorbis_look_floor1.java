package libvorbis;

final class Jvorbis_look_floor1 extends Jvorbis_look_floor {
	final int[] sorted_index  = new int[Jvorbis_info_floor.VIF_POSIT + 2];
	final int[] forward_index = new int[Jvorbis_info_floor.VIF_POSIT + 2];
	final int[] reverse_index = new int[Jvorbis_info_floor.VIF_POSIT + 2];

	final int[] hineighbor = new int[Jvorbis_info_floor.VIF_POSIT];
	final int[] loneighbor = new int[Jvorbis_info_floor.VIF_POSIT];
	int posts   = 0;

	int n       = 0;
	int quant_q = 0;
	Jvorbis_info_floor1 vi = null;

	int phrasebits = 0;
	int postbits   = 0;
	int frames     = 0;
}
