package libvorbis;

public final class Jcompandblock {
	final int[] data = new int[Jvorbis_look_psy.NOISE_COMPAND_LEVELS];
	//
	public Jcompandblock(int[] i_data) {
		System.arraycopy( i_data, 0, data, 0, i_data.length );
	}
}
