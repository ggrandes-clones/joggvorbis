package libvorbis;

final class Jprivate_state {
	/* local lookup storage */
	/** envelope lookup */
	Jenvelope_lookup        ve = null;
	final int[]             window = new int[2];
	/** block, type */
	final Jmdct_lookup[][]  transform = new Jmdct_lookup[2][];
	final Jdrft_lookup[]    fft_look =
			new Jdrft_lookup[] { new Jdrft_lookup(), new Jdrft_lookup() };

	int                     modebits = 0;
	Jvorbis_look_floor[]    flr      = null;
	Jvorbis_look_residue[]  residue  = null;
	Jvorbis_look_psy[]      psy      = null;
	Jvorbis_look_psy_global psy_g_look = null;

	/* local storage, only used on the encoding side.  This way the
	 application does not need to worry about freeing some packets'
	 memory and not others'; packet storage is always tracked.
	 Cleared next call to a _dsp_ function */
	byte[] header  = null;
	byte[] header1 = null;
	byte[] header2 = null;

	final Jbitrate_manager_state bms = new Jbitrate_manager_state();

	long sample_count = 0;
}
