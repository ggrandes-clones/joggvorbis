package libvorbis;

/** there was no great place to put this.... */
final class Janalysis {
/* XXX #ifdef ANALYSIS
	private static final boolean analysis_noisy = true;
	//
	protected static void _analysis_output_always(String base, int i,
			float[] v, int offset, int n,
			boolean bark, boolean dB, long off)
	{
		int j;
		java.io.PrintStream of = null;
		try {
			of = new java.io.PrintStream( base + "_" + Integer.toString( i ) );

			for( j = 0; j < n; j++ ) {
				if( bark ) {
					float b = Jcodec.toBARK( (4000.f * j / n) + .25f );
					of.printf( "%f ", b );
				} else
					if( off != 0 )
						of.printf( "%f ", (double)(j + off) / 8000. );
					else
						of.printf( "%f ", (double)j );
	
				if( dB ) {
					float val;
					if( v[offset + j] == 0. )
						val = -140.f;
					else
						val = Jcodec.todB( v[offset + j] );// val = todB( v + j );
					of.printf( "%f\n", val );
				} else {
					of.printf( "%f\n", v[offset + j] );
				}
			}
		} catch(Exception e) {
			System.out.println("failed to open data dump file");
		} finally {
			if( of != null ) of.close();
		}
	}

	public static void _analysis_output(String base, int i,
			float[] v, int offset, int n,
			boolean bark, boolean dB, long off) {
		if( analysis_noisy ) _analysis_output_always( base, i, v, offset, n, bark, dB, off );
	}

/* #endif */

}
