package libvorbis;

/** encode side bitrate tracking */
final class Jbitrate_manager_state {
	int    managed = 0;

	int    avg_reservoir = 0;
	int    minmax_reservoir = 0;
	int    avg_bitsper = 0;
	int    min_bitsper = 0;
	int    max_bitsper = 0;

	int    short_per_long = 0;
	double avgfloat = 0.0;

	Jvorbis_block vb = null;
	int    choice = 0;

	final void clear() {
		managed          = 0;
		avg_reservoir    = 0;
		minmax_reservoir = 0;
		avg_bitsper = 0;
		min_bitsper = 0;
		max_bitsper = 0;

		short_per_long = 0;
		avgfloat = 0.0;

		vb = null;
		choice = 0;
	}

	// bitrate.c
	final void vorbis_bitrate_clear() {
		clear();
	}
}
