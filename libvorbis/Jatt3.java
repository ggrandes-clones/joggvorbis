package libvorbis;

/**
high level configuration information for setting things up
step-by-step with the detailed vorbis_encode_ctl interface.
There's a fair amount of redundancy such that interactive setup
does not directly deal with any vorbis_info or codec_setup_info
initialization; it's all stored (until full init) in this highlevel
setup, then flushed out to the real codec setup structs later.
*/
public final class Jatt3 {
	final int[] att = new int [Jvorbis_look_psy.P_NOISECURVES];
	final float boost;
	final float decay;
	//
	public Jatt3(int[] iatt, float fboost, float fdecay) {
		System.arraycopy( iatt, 0, att, 0, iatt.length );
		boost = fboost;
		decay = fdecay;
	}
}
