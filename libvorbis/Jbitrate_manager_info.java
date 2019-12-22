package libvorbis;

/** encode side bitrate tracking */
final class Jbitrate_manager_info {
	int    avg_rate = 0;
	int    min_rate = 0;
	int    max_rate = 0;
	int    reservoir_bits = 0;
	double reservoir_bias = 0.0;
	double slew_damp = 0.0;
}
