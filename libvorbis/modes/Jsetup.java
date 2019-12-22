package libvorbis.modes;

import libvorbis.Jvorbis_info_mapping0;

public final class Jsetup {
	/* a few static coder conventions */
	// FIXME unused _mode_template
	/*private static final Jvorbis_info_mode _mode_template[] = {// [2]
		new Jvorbis_info_mode( 0,0,0,0 ),
		new Jvorbis_info_mode( 1,0,0,1 )
	};*/

	/* mapping conventions:
	only one submap (this would change for efficient 5.1 support for example)*/
	/* Four psychoacoustic profiles are used, one for each blocktype */

	protected static final Jvorbis_info_mapping0 _map_nominal[] = {// [2]
		new Jvorbis_info_mapping0(
		1, new int[] {0,0}, new int[] {0}, new int[] {0}, 1,new int[] {0},new int[] {1} ),
		new Jvorbis_info_mapping0(
		1, new int[] {0,0}, new int[] {1}, new int[] {1}, 1,new int[] {0},new int[] {1} )
	};

	public static final Jvorbis_info_mapping0 _map_nominal_u[] = {// [2]
		new Jvorbis_info_mapping0(1, new int[]{0,0,0,0,0,0}, new int[]{0}, new int[]{0}, 0,new int[]{0},new int[]{0}),
		new Jvorbis_info_mapping0(1, new int[]{0,0,0,0,0,0}, new int[]{1}, new int[]{1}, 0,new int[]{0},new int[]{0})
	};
}
