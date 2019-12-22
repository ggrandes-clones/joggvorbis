package libvorbis.modes;

import libvorbis.Jstatic_bookblock;
import libvorbis.Jstatic_codebook;
import libvorbis.Jvorbis_mapping_template;
import libvorbis.Jvorbis_residue_template;
import libvorbis.books.coupled.Jres_books_stereo1;
import libvorbis.books.uncoupled.Jres_books_uncoupled1;

/** toplevel residue templates 16/22kHz */

public final class Jresidue_16 {

	private static final Jstatic_bookblock _resbook_16s_0 = new Jstatic_bookblock(
		new Jstatic_codebook[][]{
		{null},
		{null,null,Jres_books_stereo1._16c0_s_p1_0},
		{null},
		{null,null,Jres_books_stereo1._16c0_s_p3_0},
		{null,null,Jres_books_stereo1._16c0_s_p4_0},
		{null,null,Jres_books_stereo1._16c0_s_p5_0},
		{null,null,Jres_books_stereo1._16c0_s_p6_0},
		{Jres_books_stereo1._16c0_s_p7_0,Jres_books_stereo1._16c0_s_p7_1},
		{Jres_books_stereo1._16c0_s_p8_0,Jres_books_stereo1._16c0_s_p8_1},
		{Jres_books_stereo1._16c0_s_p9_0,Jres_books_stereo1._16c0_s_p9_1,Jres_books_stereo1._16c0_s_p9_2}
		}
	);

	private static final Jstatic_bookblock _resbook_16s_1 = new Jstatic_bookblock(
		new Jstatic_codebook[][]{
		{null},
		{null,null,Jres_books_stereo1._16c1_s_p1_0},
		{null},
		{null,null,Jres_books_stereo1._16c1_s_p3_0},
		{null,null,Jres_books_stereo1._16c1_s_p4_0},
		{null,null,Jres_books_stereo1._16c1_s_p5_0},
		{null,null,Jres_books_stereo1._16c1_s_p6_0},
		{Jres_books_stereo1._16c1_s_p7_0,Jres_books_stereo1._16c1_s_p7_1},
		{Jres_books_stereo1._16c1_s_p8_0,Jres_books_stereo1._16c1_s_p8_1},
		{Jres_books_stereo1._16c1_s_p9_0,Jres_books_stereo1._16c1_s_p9_1,Jres_books_stereo1._16c1_s_p9_2}
		}
	);

	private static final Jstatic_bookblock _resbook_16s_2 = new Jstatic_bookblock(
		new Jstatic_codebook[][]{
		{null},
		{null,null,Jres_books_stereo1._16c2_s_p1_0},
		{null,null,Jres_books_stereo1._16c2_s_p2_0},
		{null,null,Jres_books_stereo1._16c2_s_p3_0},
		{null,null,Jres_books_stereo1._16c2_s_p4_0},
		{Jres_books_stereo1._16c2_s_p5_0,Jres_books_stereo1._16c2_s_p5_1},
		{Jres_books_stereo1._16c2_s_p6_0,Jres_books_stereo1._16c2_s_p6_1},
		{Jres_books_stereo1._16c2_s_p7_0,Jres_books_stereo1._16c2_s_p7_1},
		{Jres_books_stereo1._16c2_s_p8_0,Jres_books_stereo1._16c2_s_p8_1},
		{Jres_books_stereo1._16c2_s_p9_0,Jres_books_stereo1._16c2_s_p9_1,Jres_books_stereo1._16c2_s_p9_2}
		}
	);

	private static final Jvorbis_residue_template _res_16s_0[] = {
		new Jvorbis_residue_template(2,0,32,  Jresidue_44._residue_44_mid,
		Jres_books_stereo1._huff_book__16c0_s_single, Jres_books_stereo1._huff_book__16c0_s_single,
		_resbook_16s_0, _resbook_16s_0),
	};

	private static final Jvorbis_residue_template _res_16s_1[] = {
		new Jvorbis_residue_template(2,0,32,  Jresidue_44._residue_44_mid,
		Jres_books_stereo1._huff_book__16c1_s_short,Jres_books_stereo1._huff_book__16c1_s_short,
		_resbook_16s_1, _resbook_16s_1),

		new Jvorbis_residue_template(2,0,32,  Jresidue_44._residue_44_mid,
		Jres_books_stereo1._huff_book__16c1_s_long, Jres_books_stereo1._huff_book__16c1_s_long,
		_resbook_16s_1, _resbook_16s_1)
	};

	private static final Jvorbis_residue_template _res_16s_2[] = {
		new Jvorbis_residue_template(2,0,32,  Jresidue_44._residue_44_high,
		Jres_books_stereo1._huff_book__16c2_s_short, Jres_books_stereo1._huff_book__16c2_s_short,
		_resbook_16s_2, _resbook_16s_2),

		new Jvorbis_residue_template(2,0,32,  Jresidue_44._residue_44_high,
		Jres_books_stereo1._huff_book__16c2_s_long, Jres_books_stereo1._huff_book__16c2_s_long,
		_resbook_16s_2, _resbook_16s_2)
	};

	protected static final Jvorbis_mapping_template _mapres_template_16_stereo[] = {// [3]
		new Jvorbis_mapping_template( Jsetup._map_nominal, _res_16s_0 ), /* 0 */
		new Jvorbis_mapping_template( Jsetup._map_nominal, _res_16s_1 ), /* 1 */
		new Jvorbis_mapping_template( Jsetup._map_nominal, _res_16s_2 ), /* 2 */
	};

	private static final Jstatic_bookblock _resbook_16u_0 = new Jstatic_bookblock(
		new Jstatic_codebook[][]{
		{null},
		{null,null,Jres_books_uncoupled1._16u0__p1_0},
		{null,null,Jres_books_uncoupled1._16u0__p2_0},
		{null,null,Jres_books_uncoupled1._16u0__p3_0},
		{null,null,Jres_books_uncoupled1._16u0__p4_0},
		{null,null,Jres_books_uncoupled1._16u0__p5_0},
		{Jres_books_uncoupled1._16u0__p6_0,Jres_books_uncoupled1._16u0__p6_1},
		{Jres_books_uncoupled1._16u0__p7_0,Jres_books_uncoupled1._16u0__p7_1,Jres_books_uncoupled1._16u0__p7_2}
		}
	);

	private static final Jstatic_bookblock _resbook_16u_1 = new Jstatic_bookblock(
		new Jstatic_codebook[][]{
		{null},
		{null,null,Jres_books_uncoupled1._16u1__p1_0},
		{null,null,Jres_books_uncoupled1._16u1__p2_0},
		{null,null,Jres_books_uncoupled1._16u1__p3_0},
		{null,null,Jres_books_uncoupled1._16u1__p4_0},
		{null,null,Jres_books_uncoupled1._16u1__p5_0},
		{null,null,Jres_books_uncoupled1._16u1__p6_0},
		{Jres_books_uncoupled1._16u1__p7_0,Jres_books_uncoupled1._16u1__p7_1},
		{Jres_books_uncoupled1._16u1__p8_0,Jres_books_uncoupled1._16u1__p8_1},
		{Jres_books_uncoupled1._16u1__p9_0,Jres_books_uncoupled1._16u1__p9_1,Jres_books_uncoupled1._16u1__p9_2}
		}
	);
	private static final Jstatic_bookblock _resbook_16u_2 = new Jstatic_bookblock(
		new Jstatic_codebook[][]{
		{null},
		{null,null,Jres_books_uncoupled1._16u2_p1_0},
		{null,null,Jres_books_uncoupled1._16u2_p2_0},
		{null,null,Jres_books_uncoupled1._16u2_p3_0},
		{null,null,Jres_books_uncoupled1._16u2_p4_0},
		{Jres_books_uncoupled1._16u2_p5_0,Jres_books_uncoupled1._16u2_p5_1},
		{Jres_books_uncoupled1._16u2_p6_0,Jres_books_uncoupled1._16u2_p6_1},
		{Jres_books_uncoupled1._16u2_p7_0,Jres_books_uncoupled1._16u2_p7_1},
		{Jres_books_uncoupled1._16u2_p8_0,Jres_books_uncoupled1._16u2_p8_1},
		{Jres_books_uncoupled1._16u2_p9_0,Jres_books_uncoupled1._16u2_p9_1,Jres_books_uncoupled1._16u2_p9_2}
		}
	);

	private static final Jvorbis_residue_template _res_16u_0[] = {
		new Jvorbis_residue_template(1,0,32,  Jresidue_44u._residue_44_low_un,
		Jres_books_uncoupled1._huff_book__16u0__single,Jres_books_uncoupled1._huff_book__16u0__single,
		_resbook_16u_0, _resbook_16u_0),
	};

	private static final Jvorbis_residue_template _res_16u_1[] = {
		new Jvorbis_residue_template(1,0,32,  Jresidue_44u._residue_44_mid_un,
		Jres_books_uncoupled1._huff_book__16u1__short,Jres_books_uncoupled1._huff_book__16u1__short,
		_resbook_16u_1, _resbook_16u_1),

		new Jvorbis_residue_template(1,0,32,  Jresidue_44u._residue_44_mid_un,
		Jres_books_uncoupled1._huff_book__16u1__long,Jres_books_uncoupled1._huff_book__16u1__long,
		_resbook_16u_1, _resbook_16u_1)
	};

	private static final Jvorbis_residue_template _res_16u_2[] = {
		new Jvorbis_residue_template(1,0,32,  Jresidue_44u._residue_44_hi_un,
		Jres_books_uncoupled1._huff_book__16u2__short,Jres_books_uncoupled1._huff_book__16u2__short,
		_resbook_16u_2, _resbook_16u_2),

		new Jvorbis_residue_template(1,0,32,  Jresidue_44u._residue_44_hi_un,
		Jres_books_uncoupled1._huff_book__16u2__long,Jres_books_uncoupled1._huff_book__16u2__long,
		_resbook_16u_2, _resbook_16u_2)
	};

	protected static final Jvorbis_mapping_template _mapres_template_16_uncoupled[] = {// [3]
		new Jvorbis_mapping_template( Jsetup._map_nominal_u, _res_16u_0 ), /* 0 */
		new Jvorbis_mapping_template( Jsetup._map_nominal_u, _res_16u_1 ), /* 1 */
		new Jvorbis_mapping_template( Jsetup._map_nominal_u, _res_16u_2 ), /* 2 */
	};
}
