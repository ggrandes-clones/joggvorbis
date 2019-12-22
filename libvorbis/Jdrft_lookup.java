package libvorbis;

/** for fft transform */
final class Jdrft_lookup {
	int n = 0;
	float[] trigcache = null;
	int[] splitcache = null;
	//
	final void drft_clear() {
		//if( l != null ) {
			trigcache = null;
			splitcache = null;
			n = 0;
		//}
	}
}
