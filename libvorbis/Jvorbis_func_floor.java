package libvorbis;

import libogg.Joggpack_buffer;

/**
this would all be simpler/shorter with templates, but....<p>
Floor backend generic
*/
abstract class Jvorbis_func_floor {
	final boolean is_pack_supported;
	//
	Jvorbis_func_floor(boolean isPack) {
		is_pack_supported = isPack;
	}
	//
	abstract void pack(Jvorbis_info_floor i, Joggpack_buffer opg);
	abstract Jvorbis_info_floor unpack(Jvorbis_info vi, Joggpack_buffer opb);
	abstract Jvorbis_look_floor look(Jvorbis_dsp_state vd, Jvorbis_info_floor in);
	//abstract void free_info(Jvorbis_info_floor);// use Jvorbis_info_floor = null
	//abstract void free_look(Jvorbis_look_floor);// use Jvorbis_look_floor = null
	abstract Object inverse1(Jvorbis_block vb, Jvorbis_look_floor in);
	abstract boolean inverse2(Jvorbis_block vb, Jvorbis_look_floor in, Object buffer, float[] out);
}
