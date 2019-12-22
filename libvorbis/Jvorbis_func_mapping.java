package libvorbis;

import libogg.Joggpack_buffer;

/** Mapping backend generic */
abstract class Jvorbis_func_mapping {
	abstract void pack(Jvorbis_info vi, Jvorbis_info_mapping vm, Joggpack_buffer opb);
	abstract Jvorbis_info_mapping unpack(Jvorbis_info vi, Joggpack_buffer opb);
	//abstract void free_info(Jvorbis_info_mapping vm);// use Jvorbis_info_mapping = null
	abstract int forward(Jvorbis_block vb);
	abstract int inverse(Jvorbis_block vb, Jvorbis_info_mapping vm);
}
