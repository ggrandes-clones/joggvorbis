package libvorbis;

/**
high level configuration information for setting things up
step-by-step with the detailed vorbis_encode_ctl interface.
There's a fair amount of redundancy such that interactive setup
does not directly deal with any vorbis_info or codec_setup_info
initialization; it's all stored (until full init) in this highlevel
setup, then flushed out to the real codec setup structs later.
*/
public final class Jadj_stereo {
	final int[]   pre  = new int[Jvorbis_info.PACKETBLOBS];
	final int[]   post = new int[Jvorbis_info.PACKETBLOBS];
	final float[] kHz  = new float[Jvorbis_info.PACKETBLOBS];
	final float[] lowpasskHz = new float[Jvorbis_info.PACKETBLOBS];
	//
	public Jadj_stereo(int[] ipre, int[] ipost, float[] fkHz, float[] flowpasskHz) {
		System.arraycopy( ipre, 0, pre, 0, ipre.length );
		System.arraycopy( ipost, 0, post, 0, ipost.length );
		System.arraycopy( fkHz, 0, kHz, 0, fkHz.length );
		System.arraycopy( flowpasskHz, 0, lowpasskHz, 0, flowpasskHz.length );
	}
}
