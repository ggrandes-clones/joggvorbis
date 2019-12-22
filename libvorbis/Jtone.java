package libvorbis;

final class Jtone {
	/* Comment to reduce lib size
	private static void usage(){
		System.err.printf("tone <frequency_Hz>,[<amplitude>] [<frequency_Hz>,[<amplitude>]...]\n");
		System.exit( 1 );
	}

	@SuppressWarnings("boxing")
	public static void main(String args[]) {
		int i, j;
		final double[] f;
		final double[] amp;

		if( args.length < 1 ) usage();

		f = new double[args.length];
		amp = new double[args.length];

		i = 0;
		while( i < args.length ) {
			int pos = args[i].indexOf(',');
			if( pos < 0 ) {
				f[i] = Double.parseDouble( args[i] );
				amp[i] = 32767.f;
			} else {
				f[i] = Double.parseDouble( args[i].substring( 0, pos ) );
				amp[i] = Double.parseDouble( args[i].substring( ++pos ) ) * 32767.0;
			}

			System.err.printf("%g Hz, %g amp\n", f[i], amp[i] );

			i++;
		}

		for( i = 0; i < 44100 * 10; i++ ) {
			double val = 0;
			int ival;
			for( j = 0; j < args.length; j++ )
				val += amp[j] * Math.sin( i / 44100.0 * f[j] * 2.0 * Math.PI );
			ival = (int)Math.rint( val + 0.5 );

			if( ival > 32767 ) ival = 32767;
			if( ival < -32768 ) ival = -32768;

			System.out.printf("%c%c%c%c",
					(byte)(ival & 0xff),
					(byte)((ival >> 8) & 0xff),
					(byte)(ival & 0xff),
					(byte)((ival >> 8) & 0xff));
		}
		System.exit( 0 );
	}*/
}

