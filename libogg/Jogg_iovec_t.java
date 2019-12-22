package libogg;

/**
 * Changes from C to Java:<p>
 * <code>byte data = iov_packet[iov_base]</code><br>
 * <code>int data = ((int)iov_packet[iov_base]) & 0xff</code>
 */
final class Jogg_iovec_t {
	int iov_base;
	byte[] iov_packet;
	int iov_len;
}
