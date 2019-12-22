package libvorbis;

/** psychoacoustic setup */
final class Jvorbis_look_psy_global {
	float ampmax   = 0;
	int   channels = 0;

	Jvorbis_info_psy_global gi = null;
	final int[][] coupling_pointlimit = new int[2][Jvorbis_look_psy.P_NOISECURVES];

	// psy.c
	// void _vp_global_free(vorbis_look_psy_global *look) // use Jvorbis_look_psy_global = null
	/*void _vp_global_free(Jvorbis_look_psy_global look) {
		if( look != null ) {
			memset(look,0,sizeof(*look));
			_ogg_free(look);
		}
	}*/
}
