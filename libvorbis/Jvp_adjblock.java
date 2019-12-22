package libvorbis;

public final class Jvp_adjblock {
	final int[] block = new int[Jvorbis_look_psy.P_BANDS];
	//
	public Jvp_adjblock(int[] i_block) {
		System.arraycopy( i_block, 0, block, 0, i_block.length );
	}
}
