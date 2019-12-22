package libvorbis;

public final class Jvorbis_mapping_template {
	final Jvorbis_info_mapping0[]    map;
	final Jvorbis_residue_template[] res;
	//
	public Jvorbis_mapping_template(Jvorbis_info_mapping0[] pvim_map,
						Jvorbis_residue_template[] pvrt_res) {
		map = pvim_map;
		res = pvrt_res;
	}
}
