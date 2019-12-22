package libvorbis.modes;

import libvorbis.Jstatic_bookblock;
import libvorbis.Jstatic_codebook;
import libvorbis.Jvorbis_info_residue0;
import libvorbis.Jvorbis_mapping_template;
import libvorbis.Jvorbis_residue_template;
import libvorbis.books.uncoupled.Jres_books_uncoupled2;
import libvorbis.books.uncoupled.Jres_books_uncoupled3;
import libvorbis.books.uncoupled.Jres_books_uncoupled4;
import libvorbis.books.uncoupled.Jres_books_uncoupled5;

/** toplevel residue templates for 32/44.1/48kHz uncoupled */

public final class Jresidue_44u{

	protected static final Jvorbis_info_residue0 _residue_44_low_un = new Jvorbis_info_residue0(
		0,-1, -1, 8,-1,-1,
		new int[]{0},
		new int[]{-1},
		new int[]{  0,  1,  1,  2,  2,  4, 28},
		new int[]{ -1, 25, -1, 45, -1, -1, -1}
	);

	protected static final Jvorbis_info_residue0 _residue_44_mid_un = new Jvorbis_info_residue0(
		0,-1, -1, 10,-1,-1,
		/* 0   1   2   3   4   5   6   7   8   9 */
		new int[]{0},
		new int[]{-1},
		new int[]{  0,  1,  1,  2,  2,  4,  4, 16, 60},
		new int[]{ -1, 30, -1, 50, -1, 80, -1, -1, -1}
	);

	protected static final Jvorbis_info_residue0 _residue_44_hi_un = new Jvorbis_info_residue0(
		0,-1, -1, 10,-1,-1,
		/* 0   1   2   3   4   5   6   7   8   9 */
		new int[]{0},
		new int[]{-1},
		new int[]{  0,  1,  2,  4,  8, 16, 32, 71,157},
		new int[]{ -1, -1, -1, -1, -1, -1, -1, -1, -1}
	);

	private static final Jstatic_bookblock _resbook_44u_n1 = new Jstatic_bookblock(
		new Jstatic_codebook[][]{
		{null},
		{null,null, Jres_books_uncoupled5._44un1__p1_0},
		{null,null, Jres_books_uncoupled5._44un1__p2_0},
		{null,null, Jres_books_uncoupled5._44un1__p3_0},
		{null,null, Jres_books_uncoupled5._44un1__p4_0},
		{null,null, Jres_books_uncoupled5._44un1__p5_0},
		{Jres_books_uncoupled5._44un1__p6_0, Jres_books_uncoupled5._44un1__p6_1},
		{Jres_books_uncoupled5._44un1__p7_0, Jres_books_uncoupled5._44un1__p7_1, Jres_books_uncoupled5._44un1__p7_2}
		}
	);

	private static final Jstatic_bookblock _resbook_44u_0 = new Jstatic_bookblock(
		new Jstatic_codebook[][]{
		{null},
		{null,null, Jres_books_uncoupled2._44u0__p1_0},
		{null,null, Jres_books_uncoupled2._44u0__p2_0},
		{null,null, Jres_books_uncoupled2._44u0__p3_0},
		{null,null, Jres_books_uncoupled2._44u0__p4_0},
		{null,null, Jres_books_uncoupled2._44u0__p5_0},
		{Jres_books_uncoupled2._44u0__p6_0, Jres_books_uncoupled2._44u0__p6_1},
		{Jres_books_uncoupled2._44u0__p7_0, Jres_books_uncoupled2._44u0__p7_1, Jres_books_uncoupled2._44u0__p7_2}
		}
	);

	private static final Jstatic_bookblock _resbook_44u_1 = new Jstatic_bookblock(
		new Jstatic_codebook[][]{
		{null},
		{null,null, Jres_books_uncoupled2._44u1__p1_0},
		{null,null, Jres_books_uncoupled2._44u1__p2_0},
		{null,null, Jres_books_uncoupled2._44u1__p3_0},
		{null,null, Jres_books_uncoupled2._44u1__p4_0},
		{null,null, Jres_books_uncoupled2._44u1__p5_0},
		{Jres_books_uncoupled2._44u1__p6_0, Jres_books_uncoupled2._44u1__p6_1},
		{Jres_books_uncoupled2._44u1__p7_0, Jres_books_uncoupled2._44u1__p7_1, Jres_books_uncoupled2._44u1__p7_2}
		}
	);

	private static final Jstatic_bookblock _resbook_44u_2 = new Jstatic_bookblock(
		new Jstatic_codebook[][]{
		{null},
		{null,null, Jres_books_uncoupled2._44u2__p1_0},
		{null,null, Jres_books_uncoupled3._44u2__p2_0},
		{null,null, Jres_books_uncoupled3._44u2__p3_0},
		{null,null, Jres_books_uncoupled3._44u2__p4_0},
		{null,null, Jres_books_uncoupled3._44u2__p5_0},
		{Jres_books_uncoupled3._44u2__p6_0, Jres_books_uncoupled3._44u2__p6_1},
		{Jres_books_uncoupled3._44u2__p7_0, Jres_books_uncoupled3._44u2__p7_1, Jres_books_uncoupled3._44u2__p7_2}
		}
	);

	private static final Jstatic_bookblock _resbook_44u_3 = new Jstatic_bookblock(
		new Jstatic_codebook[][]{
		{null},
		{null,null, Jres_books_uncoupled3._44u3__p1_0},
		{null,null, Jres_books_uncoupled3._44u3__p2_0},
		{null,null, Jres_books_uncoupled3._44u3__p3_0},
		{null,null, Jres_books_uncoupled3._44u3__p4_0},
		{null,null, Jres_books_uncoupled3._44u3__p5_0},
		{Jres_books_uncoupled3._44u3__p6_0, Jres_books_uncoupled3._44u3__p6_1},
		{Jres_books_uncoupled3._44u3__p7_0, Jres_books_uncoupled3._44u3__p7_1, Jres_books_uncoupled3._44u3__p7_2}
		}
	);

	private static final Jstatic_bookblock _resbook_44u_4 = new Jstatic_bookblock(
		new Jstatic_codebook[][]{
		{null},
		{null,null, Jres_books_uncoupled3._44u4__p1_0},
		{null,null, Jres_books_uncoupled3._44u4__p2_0},
		{null,null, Jres_books_uncoupled3._44u4__p3_0},
		{null,null, Jres_books_uncoupled3._44u4__p4_0},
		{null,null, Jres_books_uncoupled3._44u4__p5_0},
		{Jres_books_uncoupled3._44u4__p6_0, Jres_books_uncoupled3._44u4__p6_1},
		{Jres_books_uncoupled3._44u4__p7_0, Jres_books_uncoupled3._44u4__p7_1, Jres_books_uncoupled3._44u4__p7_2}
		}
	);

	private static final Jstatic_bookblock _resbook_44u_5 = new Jstatic_bookblock(
		new Jstatic_codebook[][]{
		{null},
		{null,null, Jres_books_uncoupled3._44u5__p1_0},
		{null,null, Jres_books_uncoupled3._44u5__p2_0},
		{null,null, Jres_books_uncoupled3._44u5__p3_0},
		{null,null, Jres_books_uncoupled3._44u5__p4_0},
		{null,null, Jres_books_uncoupled3._44u5__p5_0},
		{null,null, Jres_books_uncoupled3._44u5__p6_0},
		{Jres_books_uncoupled3._44u5__p7_0, Jres_books_uncoupled3._44u5__p7_1},
		{Jres_books_uncoupled3._44u5__p8_0, Jres_books_uncoupled3._44u5__p8_1},
		{Jres_books_uncoupled3._44u5__p9_0, Jres_books_uncoupled3._44u5__p9_1, Jres_books_uncoupled4._44u5__p9_2}
		}
	);

	private static final Jstatic_bookblock _resbook_44u_6 = new Jstatic_bookblock(
		new Jstatic_codebook[][]{
		{null},
		{null,null, Jres_books_uncoupled4._44u6__p1_0},
		{null,null, Jres_books_uncoupled4._44u6__p2_0},
		{null,null, Jres_books_uncoupled4._44u6__p3_0},
		{null,null, Jres_books_uncoupled4._44u6__p4_0},
		{null,null, Jres_books_uncoupled4._44u6__p5_0},
		{null,null, Jres_books_uncoupled4._44u6__p6_0},
		{Jres_books_uncoupled4._44u6__p7_0, Jres_books_uncoupled4._44u6__p7_1},
		{Jres_books_uncoupled4._44u6__p8_0, Jres_books_uncoupled4._44u6__p8_1},
		{Jres_books_uncoupled4._44u6__p9_0, Jres_books_uncoupled4._44u6__p9_1, Jres_books_uncoupled4._44u6__p9_2}
		}
	);

	private static final Jstatic_bookblock _resbook_44u_7 = new Jstatic_bookblock(
		new Jstatic_codebook[][]{
		{null},
		{null,null, Jres_books_uncoupled4._44u7__p1_0},
		{null,null, Jres_books_uncoupled4._44u7__p2_0},
		{null,null, Jres_books_uncoupled4._44u7__p3_0},
		{null,null, Jres_books_uncoupled4._44u7__p4_0},
		{null,null, Jres_books_uncoupled4._44u7__p5_0},
		{null,null, Jres_books_uncoupled4._44u7__p6_0},
		{Jres_books_uncoupled4._44u7__p7_0, Jres_books_uncoupled4._44u7__p7_1},
		{Jres_books_uncoupled4._44u7__p8_0, Jres_books_uncoupled4._44u7__p8_1},
		{Jres_books_uncoupled4._44u7__p9_0, Jres_books_uncoupled4._44u7__p9_1, Jres_books_uncoupled4._44u7__p9_2}
		}
	);

	private static final Jstatic_bookblock _resbook_44u_8 = new Jstatic_bookblock(
		new Jstatic_codebook[][]{
		{null},
		{null,null, Jres_books_uncoupled4._44u8_p1_0},
		{null,null, Jres_books_uncoupled4._44u8_p2_0},
		{null,null, Jres_books_uncoupled4._44u8_p3_0},
		{null,null, Jres_books_uncoupled4._44u8_p4_0},
		{Jres_books_uncoupled4._44u8_p5_0, Jres_books_uncoupled4._44u8_p5_1},
		{Jres_books_uncoupled4._44u8_p6_0, Jres_books_uncoupled4._44u8_p6_1},
		{Jres_books_uncoupled4._44u8_p7_0, Jres_books_uncoupled4._44u8_p7_1},
		{Jres_books_uncoupled4._44u8_p8_0, Jres_books_uncoupled4._44u8_p8_1},
		{Jres_books_uncoupled4._44u8_p9_0, Jres_books_uncoupled4._44u8_p9_1, Jres_books_uncoupled4._44u8_p9_2}
		}
	);

	private static final Jstatic_bookblock _resbook_44u_9 = new Jstatic_bookblock(
		new Jstatic_codebook[][]{
		{null},
		{null,null, Jres_books_uncoupled4._44u9_p1_0},
		{null,null, Jres_books_uncoupled5._44u9_p2_0},
		{null,null, Jres_books_uncoupled5._44u9_p3_0},
		{null,null, Jres_books_uncoupled5._44u9_p4_0},
		{Jres_books_uncoupled5._44u9_p5_0, Jres_books_uncoupled5._44u9_p5_1},
		{Jres_books_uncoupled5._44u9_p6_0, Jres_books_uncoupled5._44u9_p6_1},
		{Jres_books_uncoupled5._44u9_p7_0, Jres_books_uncoupled5._44u9_p7_1},
		{Jres_books_uncoupled5._44u9_p8_0, Jres_books_uncoupled5._44u9_p8_1},
		{Jres_books_uncoupled5._44u9_p9_0, Jres_books_uncoupled5._44u9_p9_1, Jres_books_uncoupled5._44u9_p9_2}
		}
	);

	private static final Jvorbis_residue_template _res_44u_n1[] = {
		new Jvorbis_residue_template(1,0,32,  _residue_44_low_un,
		Jres_books_uncoupled5._huff_book__44un1__short, Jres_books_uncoupled5._huff_book__44un1__short,
		_resbook_44u_n1, _resbook_44u_n1),

		new Jvorbis_residue_template(1,0,32,  _residue_44_low_un,
		Jres_books_uncoupled5._huff_book__44un1__long, Jres_books_uncoupled5._huff_book__44un1__long,
		_resbook_44u_n1, _resbook_44u_n1)
	};

	private static final Jvorbis_residue_template _res_44u_0[] = {
		new Jvorbis_residue_template(1,0,16,  _residue_44_low_un,
		Jres_books_uncoupled2._huff_book__44u0__short, Jres_books_uncoupled2._huff_book__44u0__short,
		_resbook_44u_0, _resbook_44u_0),

		new Jvorbis_residue_template(1,0,32,  _residue_44_low_un,
		Jres_books_uncoupled2._huff_book__44u0__long, Jres_books_uncoupled2._huff_book__44u0__long,
		_resbook_44u_0, _resbook_44u_0)
	};

	private static final Jvorbis_residue_template _res_44u_1[] = {
		new Jvorbis_residue_template(1,0,16,  _residue_44_low_un,
		Jres_books_uncoupled2._huff_book__44u1__short, Jres_books_uncoupled2._huff_book__44u1__short,
		_resbook_44u_1, _resbook_44u_1),

		new Jvorbis_residue_template(1,0,32,  _residue_44_low_un,
		Jres_books_uncoupled2._huff_book__44u1__long, Jres_books_uncoupled2._huff_book__44u1__long,
		_resbook_44u_1, _resbook_44u_1)
	};

	private static final Jvorbis_residue_template _res_44u_2[] = {
		new Jvorbis_residue_template(1,0,16,  _residue_44_low_un,
		Jres_books_uncoupled3._huff_book__44u2__short, Jres_books_uncoupled3._huff_book__44u2__short,
		_resbook_44u_2, _resbook_44u_2),

		new Jvorbis_residue_template(1,0,32,  _residue_44_low_un,
		Jres_books_uncoupled2._huff_book__44u2__long, Jres_books_uncoupled2._huff_book__44u2__long,
		_resbook_44u_2, _resbook_44u_2)
	};

	private static final Jvorbis_residue_template _res_44u_3[] = {
		new Jvorbis_residue_template(1,0,16,  _residue_44_low_un,
		Jres_books_uncoupled3._huff_book__44u3__short, Jres_books_uncoupled3._huff_book__44u3__short,
		_resbook_44u_3, _resbook_44u_3),

		new Jvorbis_residue_template(1,0,32,  _residue_44_low_un,
		Jres_books_uncoupled3._huff_book__44u3__long, Jres_books_uncoupled3._huff_book__44u3__long,
		_resbook_44u_3, _resbook_44u_3)
	};

	private static final Jvorbis_residue_template _res_44u_4[] = {
		new Jvorbis_residue_template(1,0,16,  _residue_44_low_un,
		Jres_books_uncoupled3._huff_book__44u4__short, Jres_books_uncoupled3._huff_book__44u4__short,
		_resbook_44u_4, _resbook_44u_4),

		new Jvorbis_residue_template(1,0,32,  _residue_44_low_un,
		Jres_books_uncoupled3._huff_book__44u4__long, Jres_books_uncoupled3._huff_book__44u4__long,
		_resbook_44u_4, _resbook_44u_4)
	};

	private static final Jvorbis_residue_template _res_44u_5[] = {
		new Jvorbis_residue_template(1,0,16,  _residue_44_mid_un,
		Jres_books_uncoupled4._huff_book__44u5__short, Jres_books_uncoupled4._huff_book__44u5__short,
		_resbook_44u_5, _resbook_44u_5),

		new Jvorbis_residue_template(1,0,32,  _residue_44_mid_un,
		Jres_books_uncoupled3._huff_book__44u5__long, Jres_books_uncoupled3._huff_book__44u5__long,
		_resbook_44u_5, _resbook_44u_5)
	};

	private static final Jvorbis_residue_template _res_44u_6[] = {
		new Jvorbis_residue_template(1,0,16,  _residue_44_mid_un,
		Jres_books_uncoupled4._huff_book__44u6__short, Jres_books_uncoupled4._huff_book__44u6__short,
		_resbook_44u_6, _resbook_44u_6),

		new Jvorbis_residue_template(1,0,32,  _residue_44_mid_un,
		Jres_books_uncoupled4._huff_book__44u6__long, Jres_books_uncoupled4._huff_book__44u6__long,
		_resbook_44u_6, _resbook_44u_6)
	};

	private static final Jvorbis_residue_template _res_44u_7[] = {
		new Jvorbis_residue_template(1,0,16,  _residue_44_mid_un,
		Jres_books_uncoupled4._huff_book__44u7__short, Jres_books_uncoupled4._huff_book__44u7__short,
		_resbook_44u_7, _resbook_44u_7),

		new Jvorbis_residue_template(1,0,32,  _residue_44_mid_un,
		Jres_books_uncoupled4._huff_book__44u7__long, Jres_books_uncoupled4._huff_book__44u7__long,
		_resbook_44u_7, _resbook_44u_7)
	};

	private static final Jvorbis_residue_template _res_44u_8[] = {
		new Jvorbis_residue_template(1,0,16,  _residue_44_hi_un,
		Jres_books_uncoupled4._huff_book__44u8__short, Jres_books_uncoupled4._huff_book__44u8__short,
		_resbook_44u_8, _resbook_44u_8),

		new Jvorbis_residue_template(1,0,32,  _residue_44_hi_un,
		Jres_books_uncoupled4._huff_book__44u8__long, Jres_books_uncoupled4._huff_book__44u8__long,
		_resbook_44u_8, _resbook_44u_8)
	};

	private static final Jvorbis_residue_template _res_44u_9[] = {
		new Jvorbis_residue_template(1,0,16,  _residue_44_hi_un,
		Jres_books_uncoupled4._huff_book__44u9__short, Jres_books_uncoupled4._huff_book__44u9__short,
		_resbook_44u_9, _resbook_44u_9),

		new Jvorbis_residue_template(1,0,32,  _residue_44_hi_un,
		Jres_books_uncoupled4._huff_book__44u9__long, Jres_books_uncoupled4._huff_book__44u9__long,
		_resbook_44u_9, _resbook_44u_9)
	};

	protected static final Jvorbis_mapping_template _mapres_template_44_uncoupled[] = {
		new Jvorbis_mapping_template( Jsetup._map_nominal_u, _res_44u_n1 ), /* -1 */
		new Jvorbis_mapping_template( Jsetup._map_nominal_u, _res_44u_0 ), /* 0 */
		new Jvorbis_mapping_template( Jsetup._map_nominal_u, _res_44u_1 ), /* 1 */
		new Jvorbis_mapping_template( Jsetup._map_nominal_u, _res_44u_2 ), /* 2 */
		new Jvorbis_mapping_template( Jsetup._map_nominal_u, _res_44u_3 ), /* 3 */
		new Jvorbis_mapping_template( Jsetup._map_nominal_u, _res_44u_4 ), /* 4 */
		new Jvorbis_mapping_template( Jsetup._map_nominal_u, _res_44u_5 ), /* 5 */
		new Jvorbis_mapping_template( Jsetup._map_nominal_u, _res_44u_6 ), /* 6 */
		new Jvorbis_mapping_template( Jsetup._map_nominal_u, _res_44u_7 ), /* 7 */
		new Jvorbis_mapping_template( Jsetup._map_nominal_u, _res_44u_8 ), /* 8 */
		new Jvorbis_mapping_template( Jsetup._map_nominal_u, _res_44u_9 ), /* 9 */
	};
}
