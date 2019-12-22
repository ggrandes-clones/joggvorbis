package libvorbis;

/** mode **/
final class Jvorbis_info_mode {
	int blockflag  = 0;
	int windowtype = 0;
	int transformtype = 0;
	int mapping = 0;
	//
	Jvorbis_info_mode() {
	}

	Jvorbis_info_mode(int i_blockflag, int i_windowtype,
				int i_transformtype, int i_mapping) {
		blockflag = i_blockflag;
		windowtype = i_windowtype;
		transformtype = i_transformtype;
		mapping = i_mapping;
	}

	Jvorbis_info_mode(Jvorbis_info_mode m) {
		blockflag = m.blockflag;
		windowtype = m.windowtype;
		transformtype = m.transformtype;
		mapping = m.mapping;
	}
}
