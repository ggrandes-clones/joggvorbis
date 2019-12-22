package libvorbis;

import libogg.Joggpack_buffer;

/**
 * residue decodepart interface<p>
 * c-variant:<br>
 * <code>int (*decodepart)(codebook *, float *, Joggpack_buffer *, int )</code>
 */
interface Idecodepart {
	int decodepart(Jcodebook book, float[] a, int offset, Joggpack_buffer b, int n);
}
