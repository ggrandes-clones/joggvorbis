package libvorbis;

import libogg.Joggpack_buffer;

/**
 * residue encodepart interface<p>
 * c-variant:<br>
 * <code>int encode(oggpack_buffer *opb,int *vec, int n, codebook *book,long *acc)</code>
 */
interface Iencodepart {
//#ifdef TRAIN_RES
//	static int _encodepart(oggpack_buffer *opb,int *vec, int n, codebook *book,long *acc)
//#else
//	static int _encodepart(oggpack_buffer *opb,int *vec, int n, codebook *book)
//#endif
	int encodepart(Joggpack_buffer opb, int[] vec, int offset, int n, Jcodebook book);//, int[] acc);
}
