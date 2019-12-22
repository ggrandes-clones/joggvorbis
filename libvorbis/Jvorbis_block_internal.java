package libvorbis;

import libogg.Joggpack_buffer;

final class Jvorbis_block_internal {
	/** this is a pointer into local storage */
	float[][] pcmdelay = null;
	float    ampmax = 0;
	int   blocktype = 0;
	/** initialized, must be freed;<br>
	  blob [PACKETBLOBS/2] points to the oggpack_buffer in the
	  main vorbis_block */
	final Joggpack_buffer packetblob[] =
			new Joggpack_buffer[Jvorbis_info.PACKETBLOBS];
}
