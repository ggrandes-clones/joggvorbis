package libvorbis;

/** bark scale utility */
final class Jbarkmel {
	/* Comment to reduce lib size
	@SuppressWarnings("boxing")
	public static void main(String[] args) {
		float rate;
		for( int i = 64; i < 32000; i *= 2 ) {
			int i2 = i / 2;
			rate = 48000.f;
			float rate2 = rate / 2.f;
			System.err.printf("rate=%gHz, block=%d, f(1)=%.2gHz bark(1)=%.2g (of %.2g)\n",
				rate, i, rate2 / (i2), Jcodec.toBARK(rate2 / (i2)), Jcodec.toBARK(rate2) );

			rate = 44100.f;
			rate2 = rate / 2.f;
			System.err.printf("rate=%gHz, block=%d, f(1)=%.2gHz bark(1)=%.2g (of %.2g)\n",
				rate, i, rate2 / (i2), Jcodec.toBARK(rate2 /(i2)), Jcodec.toBARK(rate2) );

			rate = 32000.f;
			rate2 = rate / 2.f;
			System.err.printf("rate=%gHz, block=%d, f(1)=%.2gHz bark(1)=%.2g (of %.2g)\n",
				rate, i, rate2 / (i2), Jcodec.toBARK(rate2 /(i2)), Jcodec.toBARK(rate2) );

			rate = 22050.f;
			rate2 = rate / 2.f;
			System.err.printf("rate=%gHz, block=%d, f(1)=%.2gHz bark(1)=%.2g (of %.2g)\n",
				rate, i, rate2 / (i2), Jcodec.toBARK(rate2 /(i2)), Jcodec.toBARK(rate2) );

			rate = 16000.f;
			rate2 = rate / 2.f;
			System.err.printf("rate=%gHz, block=%d, f(1)=%.2gHz bark(1)=%.2g (of %.2g)\n",
				rate, i, rate2 / (i2), Jcodec.toBARK(rate2 /(i2)), Jcodec.toBARK(rate2) );

			rate = 11025.f;
			rate2 = rate / 2.f;
			System.err.printf("rate=%gHz, block=%d, f(1)=%.2gHz bark(1)=%.2g (of %.2g)\n",
				rate, i, rate2 / (i2), Jcodec.toBARK(rate2 /(i2)), Jcodec.toBARK(rate2) );

			rate = 8000.f;
			rate2 = rate / 2.f;
			System.err.printf("rate=%gHz, block=%d, f(1)=%.2gHz bark(1)=%.2g (of %.2g)\n\n",
				rate, i, rate2 / (i2), Jcodec.toBARK(rate2 /(i2)), Jcodec.toBARK(rate2) );

		}
		{
			float i;
			int j;
			for( i = 0.f, j = 0; i < 28; i += 1, j++ ) {
				System.err.printf("(%d) bark=%f %gHz (%d of 128)\n",
					j, i, Jcodec.fromBARK( i ), (int)(Jcodec.fromBARK( i ) / 22050. * 128.));
			}
		}
		return;
	}
	*/
}

