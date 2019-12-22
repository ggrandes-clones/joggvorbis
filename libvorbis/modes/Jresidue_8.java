package libvorbis.modes;

import libvorbis.Jstatic_bookblock;
import libvorbis.Jstatic_codebook;
import libvorbis.Jvorbis_mapping_template;
import libvorbis.Jvorbis_residue_template;
import libvorbis.books.coupled.Jres_books_stereo1;
import libvorbis.books.uncoupled.Jres_books_uncoupled1;
import libvorbis.books.uncoupled.Jres_books_uncoupled2;

/** toplevel residue templates 8/11kHz */

public final class Jresidue_8 {

	private static final Jstatic_bookblock _resbook_8s_0 = new Jstatic_bookblock(
		new Jstatic_codebook[][]{
		{null},
		{null,null,Jres_books_stereo1._8c0_s_p1_0},
		{null},
		{null,null,Jres_books_stereo1._8c0_s_p3_0},
		{null,null,Jres_books_stereo1._8c0_s_p4_0},
		{null,null,Jres_books_stereo1._8c0_s_p5_0},
		{null,null,Jres_books_stereo1._8c0_s_p6_0},
		{Jres_books_stereo1._8c0_s_p7_0,Jres_books_stereo1._8c0_s_p7_1},
		{Jres_books_stereo1._8c0_s_p8_0,Jres_books_stereo1._8c0_s_p8_1},
		{Jres_books_stereo1._8c0_s_p9_0,Jres_books_stereo1._8c0_s_p9_1,Jres_books_stereo1._8c0_s_p9_2}
		}
	);

	private static final Jstatic_bookblock _resbook_8s_1 = new Jstatic_bookblock(
		new Jstatic_codebook[][]{
		{null},
		{null,null,Jres_books_stereo1._8c1_s_p1_0},
		{null},
		{null,null,Jres_books_stereo1._8c1_s_p3_0},
		{null,null,Jres_books_stereo1._8c1_s_p4_0},
		{null,null,Jres_books_stereo1._8c1_s_p5_0},
		{null,null,Jres_books_stereo1._8c1_s_p6_0},
		{Jres_books_stereo1._8c1_s_p7_0,Jres_books_stereo1._8c1_s_p7_1},
		{Jres_books_stereo1._8c1_s_p8_0,Jres_books_stereo1._8c1_s_p8_1},
		{Jres_books_stereo1._8c1_s_p9_0,Jres_books_stereo1._8c1_s_p9_1,Jres_books_stereo1._8c1_s_p9_2}
		}
	);

	private static final Jvorbis_residue_template _res_8s_0[] = {
		new Jvorbis_residue_template(2,0,32,  Jresidue_44._residue_44_mid,
		Jres_books_stereo1._huff_book__8c0_s_single,Jres_books_stereo1._huff_book__8c0_s_single,
		_resbook_8s_0,_resbook_8s_0),
	};

	private static final Jvorbis_residue_template _res_8s_1[] = {
		new Jvorbis_residue_template(2,0,32,  Jresidue_44._residue_44_mid,
		Jres_books_stereo1._huff_book__8c1_s_single,Jres_books_stereo1._huff_book__8c1_s_single,
		_resbook_8s_1,_resbook_8s_1),
	};

	public static final Jvorbis_mapping_template _mapres_template_8_stereo[] = {// [2]
		new Jvorbis_mapping_template( Jsetup._map_nominal, _res_8s_0 ), /* 0 */
		new Jvorbis_mapping_template( Jsetup._map_nominal, _res_8s_1 ), /* 1 */
	};

	private static final Jstatic_bookblock _resbook_8u_0 = new Jstatic_bookblock(
		new Jstatic_codebook[][]{
		{null},
		{null,null,Jres_books_uncoupled1._8u0__p1_0},
		{null,null,Jres_books_uncoupled1._8u0__p2_0},
		{null,null,Jres_books_uncoupled1._8u0__p3_0},
		{null,null,Jres_books_uncoupled2._8u0__p4_0},
		{null,null,Jres_books_uncoupled2._8u0__p5_0},
		{Jres_books_uncoupled2._8u0__p6_0,Jres_books_uncoupled2._8u0__p6_1},
		{Jres_books_uncoupled2._8u0__p7_0,Jres_books_uncoupled2._8u0__p7_1,Jres_books_uncoupled2._8u0__p7_2}
		}
	);

	private static final Jstatic_bookblock _resbook_8u_1 = new Jstatic_bookblock(
		new Jstatic_codebook[][]{
		{null},
		{null,null,Jres_books_uncoupled2._8u1__p1_0},
		{null,null,Jres_books_uncoupled2._8u1__p2_0},
		{null,null,Jres_books_uncoupled2._8u1__p3_0},
		{null,null,Jres_books_uncoupled2._8u1__p4_0},
		{null,null,Jres_books_uncoupled2._8u1__p5_0},
		{null,null,Jres_books_uncoupled2._8u1__p6_0},
		{Jres_books_uncoupled2._8u1__p7_0,Jres_books_uncoupled2._8u1__p7_1},
		{Jres_books_uncoupled2._8u1__p8_0,Jres_books_uncoupled2._8u1__p8_1},
		{Jres_books_uncoupled2._8u1__p9_0,Jres_books_uncoupled2._8u1__p9_1,Jres_books_uncoupled2._8u1__p9_2}
		}
	);

	private static final Jvorbis_residue_template _res_8u_0[] = {
		new Jvorbis_residue_template(1,0,32,  Jresidue_44u._residue_44_low_un,
		Jres_books_uncoupled2._huff_book__8u0__single,Jres_books_uncoupled2._huff_book__8u0__single,
		_resbook_8u_0,_resbook_8u_0),
	};

	private static final Jvorbis_residue_template _res_8u_1[] = {
		new Jvorbis_residue_template(1,0,32,  Jresidue_44u._residue_44_mid_un,
		Jres_books_uncoupled2._huff_book__8u1__single,Jres_books_uncoupled2._huff_book__8u1__single,
		_resbook_8u_1,_resbook_8u_1),
	};

	protected static final Jvorbis_mapping_template _mapres_template_8_uncoupled[] = {// [2]
		new Jvorbis_mapping_template( Jsetup._map_nominal_u, _res_8u_0 ), /* 0 */
		new Jvorbis_mapping_template( Jsetup._map_nominal_u, _res_8u_1 ), /* 1 */
	};
}
