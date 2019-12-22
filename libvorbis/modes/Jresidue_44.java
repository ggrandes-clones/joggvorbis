package libvorbis.modes;

import libvorbis.Jstatic_bookblock;
import libvorbis.Jstatic_codebook;
import libvorbis.Jvorbis_info_residue0;
import libvorbis.Jvorbis_mapping_template;
import libvorbis.Jvorbis_residue_template;
import libvorbis.books.coupled.Jres_books_stereo1;
import libvorbis.books.coupled.Jres_books_stereo2;
import libvorbis.books.coupled.Jres_books_stereo3;
import libvorbis.books.coupled.Jres_books_stereo4;

/** toplevel residue templates for 32/44.1/48kHz */

public final class Jresidue_44 {

	private static final Jvorbis_info_residue0 _residue_44_low = new Jvorbis_info_residue0(
		0,-1, -1, 9,-1,-1,
		/* 0   1   2   3   4   5   6   7  */
		new int[] {0},
		new int[] {-1},
		new int[] {  0,  1,  2,  2,  4,  8, 16, 32},
		new int[] {  0,  0,  0,999,  4,  8, 16, 32}
	);

	protected static final Jvorbis_info_residue0 _residue_44_mid = new Jvorbis_info_residue0(
		0,-1, -1, 10,-1,-1,
		/* 0   1   2   3   4   5   6   7   8  */
		new int[] {0},
		new int[] {-1},
		new int[] {  0,  1,  1,  2,  2,  4,  8, 16, 32},
		new int[] {  0,  0,999,  0,999,  4,  8, 16, 32}
	);

	protected static final Jvorbis_info_residue0 _residue_44_high = new Jvorbis_info_residue0(
		0,-1, -1, 10,-1,-1,
		/* 0   1   2   3   4   5   6   7   8  */
		new int[] {0},
		new int[] {-1},
		new int[] {  0,  1,  2,  4,  8, 16, 32, 71,157},
		new int[] {  0,  1,  2,  3,  4,  8, 16, 71,157}
	);

	private static final Jstatic_bookblock _resbook_44s_n1 = new Jstatic_bookblock(
		new Jstatic_codebook[][]{
		{null},{null,null,Jres_books_stereo4._44cn1_s_p1_0},{null,null,Jres_books_stereo4._44cn1_s_p2_0},
		{null,null,Jres_books_stereo4._44cn1_s_p3_0},{null,null,Jres_books_stereo4._44cn1_s_p4_0},{null,null,Jres_books_stereo4._44cn1_s_p5_0},
		{Jres_books_stereo4._44cn1_s_p6_0,Jres_books_stereo4._44cn1_s_p6_1},{Jres_books_stereo4._44cn1_s_p7_0,Jres_books_stereo4._44cn1_s_p7_1},
		{Jres_books_stereo4._44cn1_s_p8_0,Jres_books_stereo4._44cn1_s_p8_1,Jres_books_stereo4._44cn1_s_p8_2}
		}
	);

	private static final Jstatic_bookblock _resbook_44sm_n1 = new Jstatic_bookblock(
		new Jstatic_codebook[][]{
		{null},{null,null,Jres_books_stereo4._44cn1_sm_p1_0},{null,null,Jres_books_stereo4._44cn1_sm_p2_0},
		{null,null,Jres_books_stereo4._44cn1_sm_p3_0},{null,null,Jres_books_stereo4._44cn1_sm_p4_0},{null,null,Jres_books_stereo4._44cn1_sm_p5_0},
		{Jres_books_stereo4._44cn1_sm_p6_0,Jres_books_stereo4._44cn1_sm_p6_1},{Jres_books_stereo4._44cn1_sm_p7_0,Jres_books_stereo4._44cn1_sm_p7_1},
		{Jres_books_stereo4._44cn1_sm_p8_0,Jres_books_stereo4._44cn1_sm_p8_1,Jres_books_stereo4._44cn1_sm_p8_2}
		}
	);

	private static final Jstatic_bookblock _resbook_44s_0 = new Jstatic_bookblock(
		new Jstatic_codebook[][]{
		{null},{null,null,Jres_books_stereo3._44c0_s_p1_0},{null,null,Jres_books_stereo3._44c0_s_p2_0},
		{null,null,Jres_books_stereo3._44c0_s_p3_0},{null,null,Jres_books_stereo3._44c0_s_p4_0},{null,null,Jres_books_stereo3._44c0_s_p5_0},
		{Jres_books_stereo3._44c0_s_p6_0,Jres_books_stereo3._44c0_s_p6_1},{Jres_books_stereo3._44c0_s_p7_0,Jres_books_stereo3._44c0_s_p7_1},
		{Jres_books_stereo4._44c0_s_p8_0,Jres_books_stereo4._44c0_s_p8_1,Jres_books_stereo4._44c0_s_p8_2}
		}
	);

	private static final Jstatic_bookblock _resbook_44sm_0 = new Jstatic_bookblock(
		new Jstatic_codebook[][]{
		{null},{null,null,Jres_books_stereo4._44c0_sm_p1_0},{null,null,Jres_books_stereo4._44c0_sm_p2_0},
		{null,null,Jres_books_stereo4._44c0_sm_p3_0},{null,null,Jres_books_stereo4._44c0_sm_p4_0},{null,null,Jres_books_stereo4._44c0_sm_p5_0},
		{Jres_books_stereo4._44c0_sm_p6_0,Jres_books_stereo4._44c0_sm_p6_1},{Jres_books_stereo4._44c0_sm_p7_0,Jres_books_stereo4._44c0_sm_p7_1},
		{Jres_books_stereo4._44c0_sm_p8_0,Jres_books_stereo4._44c0_sm_p8_1,Jres_books_stereo4._44c0_sm_p8_2}
		}
	);

	private static final Jstatic_bookblock _resbook_44s_1 = new Jstatic_bookblock(
		new Jstatic_codebook[][]{
		{null},{null,null,Jres_books_stereo4._44c1_s_p1_0},{null,null,Jres_books_stereo4._44c1_s_p2_0},
		{null,null,Jres_books_stereo4._44c1_s_p3_0},{null,null,Jres_books_stereo4._44c1_s_p4_0},{null,null,Jres_books_stereo4._44c1_s_p5_0},
		{Jres_books_stereo4._44c1_s_p6_0,Jres_books_stereo4._44c1_s_p6_1},{Jres_books_stereo4._44c1_s_p7_0,Jres_books_stereo4._44c1_s_p7_1},
		{Jres_books_stereo4._44c1_s_p8_0,Jres_books_stereo4._44c1_s_p8_1,Jres_books_stereo4._44c1_s_p8_2}
		}
	);

	private static final Jstatic_bookblock _resbook_44sm_1 = new Jstatic_bookblock(
		new Jstatic_codebook[][]{
		{null},{null,null,Jres_books_stereo4._44c1_sm_p1_0},{null,null,Jres_books_stereo4._44c1_sm_p2_0},
		{null,null,Jres_books_stereo4._44c1_sm_p3_0},{null,null,Jres_books_stereo4._44c1_sm_p4_0},{null,null,Jres_books_stereo4._44c1_sm_p5_0},
		{Jres_books_stereo4._44c1_sm_p6_0,Jres_books_stereo4._44c1_sm_p6_1},{Jres_books_stereo4._44c1_sm_p7_0,Jres_books_stereo4._44c1_sm_p7_1},
		{Jres_books_stereo4._44c1_sm_p8_0,Jres_books_stereo4._44c1_sm_p8_1,Jres_books_stereo4._44c1_sm_p8_2}
		}
	);

	private static final Jstatic_bookblock _resbook_44s_2 = new Jstatic_bookblock(
		new Jstatic_codebook[][]{
		{null},{null,null,Jres_books_stereo1._44c2_s_p1_0},{null,null,Jres_books_stereo2._44c2_s_p2_0},{null,null,Jres_books_stereo2._44c2_s_p3_0},
		{null,null,Jres_books_stereo2._44c2_s_p4_0},{null,null,Jres_books_stereo2._44c2_s_p5_0},{null,null,Jres_books_stereo2._44c2_s_p6_0},
		{Jres_books_stereo2._44c2_s_p7_0,Jres_books_stereo2._44c2_s_p7_1},{Jres_books_stereo2._44c2_s_p8_0,Jres_books_stereo2._44c2_s_p8_1},
		{Jres_books_stereo2._44c2_s_p9_0,Jres_books_stereo2._44c2_s_p9_1,Jres_books_stereo2._44c2_s_p9_2}
		}
	);

	private static final Jstatic_bookblock _resbook_44s_3 = new Jstatic_bookblock(
		new Jstatic_codebook[][]{
		{null},{null,null,Jres_books_stereo2._44c3_s_p1_0},{null,null,Jres_books_stereo2._44c3_s_p2_0},{null,null,Jres_books_stereo2._44c3_s_p3_0},
		{null,null,Jres_books_stereo2._44c3_s_p4_0},{null,null,Jres_books_stereo2._44c3_s_p5_0},{null,null,Jres_books_stereo2._44c3_s_p6_0},
		{Jres_books_stereo2._44c3_s_p7_0,Jres_books_stereo2._44c3_s_p7_1},{Jres_books_stereo2._44c3_s_p8_0,Jres_books_stereo2._44c3_s_p8_1},
		{Jres_books_stereo2._44c3_s_p9_0,Jres_books_stereo2._44c3_s_p9_1,Jres_books_stereo2._44c3_s_p9_2}
		}
	);

	private static final Jstatic_bookblock _resbook_44s_4 = new Jstatic_bookblock(
		new Jstatic_codebook[][]{
		{null},{null,null,Jres_books_stereo2._44c4_s_p1_0},{null,null,Jres_books_stereo2._44c4_s_p2_0},{null,null,Jres_books_stereo2._44c4_s_p3_0},
		{null,null,Jres_books_stereo2._44c4_s_p4_0},{null,null,Jres_books_stereo2._44c4_s_p5_0},{null,null,Jres_books_stereo2._44c4_s_p6_0},
		{Jres_books_stereo2._44c4_s_p7_0,Jres_books_stereo2._44c4_s_p7_1},{Jres_books_stereo2._44c4_s_p8_0,Jres_books_stereo2._44c4_s_p8_1},
		{Jres_books_stereo2._44c4_s_p9_0,Jres_books_stereo2._44c4_s_p9_1,Jres_books_stereo2._44c4_s_p9_2}
		}
	);

	private static final Jstatic_bookblock _resbook_44s_5 = new Jstatic_bookblock(
		new Jstatic_codebook[][]{
		{null},{null,null,Jres_books_stereo2._44c5_s_p1_0},{null,null,Jres_books_stereo2._44c5_s_p2_0},{null,null,Jres_books_stereo2._44c5_s_p3_0},
		{null,null,Jres_books_stereo2._44c5_s_p4_0},{null,null,Jres_books_stereo2._44c5_s_p5_0},{null,null,Jres_books_stereo2._44c5_s_p6_0},
		{Jres_books_stereo2._44c5_s_p7_0,Jres_books_stereo2._44c5_s_p7_1},{Jres_books_stereo2._44c5_s_p8_0,Jres_books_stereo2._44c5_s_p8_1},
		{Jres_books_stereo2._44c5_s_p9_0,Jres_books_stereo2._44c5_s_p9_1,Jres_books_stereo2._44c5_s_p9_2}
		}
	);

	private static final Jstatic_bookblock _resbook_44s_6 = new Jstatic_bookblock(
		new Jstatic_codebook[][]{
		{null},{null,null,Jres_books_stereo2._44c6_s_p1_0},{null,null,Jres_books_stereo2._44c6_s_p2_0},{null,null,Jres_books_stereo2._44c6_s_p3_0},
		{null,null,Jres_books_stereo2._44c6_s_p4_0},
		{Jres_books_stereo2._44c6_s_p5_0,Jres_books_stereo2._44c6_s_p5_1},
		{Jres_books_stereo2._44c6_s_p6_0,Jres_books_stereo2._44c6_s_p6_1},
		{Jres_books_stereo2._44c6_s_p7_0,Jres_books_stereo2._44c6_s_p7_1},
		{Jres_books_stereo2._44c6_s_p8_0,Jres_books_stereo2._44c6_s_p8_1},
		{Jres_books_stereo2._44c6_s_p9_0,Jres_books_stereo2._44c6_s_p9_1,Jres_books_stereo3._44c6_s_p9_2}
		}
	);

	private static final Jstatic_bookblock _resbook_44s_7 = new Jstatic_bookblock(
		new Jstatic_codebook[][]{
		{null},{null,null,Jres_books_stereo3._44c7_s_p1_0},{null,null,Jres_books_stereo3._44c7_s_p2_0},{null,null,Jres_books_stereo3._44c7_s_p3_0},
		{null,null,Jres_books_stereo3._44c7_s_p4_0},
		{Jres_books_stereo3._44c7_s_p5_0,Jres_books_stereo3._44c7_s_p5_1},
		{Jres_books_stereo3._44c7_s_p6_0,Jres_books_stereo3._44c7_s_p6_1},
		{Jres_books_stereo3._44c7_s_p7_0,Jres_books_stereo3._44c7_s_p7_1},
		{Jres_books_stereo3._44c7_s_p8_0,Jres_books_stereo3._44c7_s_p8_1},
		{Jres_books_stereo3._44c7_s_p9_0,Jres_books_stereo3._44c7_s_p9_1,Jres_books_stereo3._44c7_s_p9_2}
		}
	);

	private static final Jstatic_bookblock _resbook_44s_8 = new Jstatic_bookblock(
		new Jstatic_codebook[][]{
		{null},{null,null,Jres_books_stereo3._44c8_s_p1_0},{null,null,Jres_books_stereo3._44c8_s_p2_0},{null,null,Jres_books_stereo3._44c8_s_p3_0},
		{null,null,Jres_books_stereo3._44c8_s_p4_0},
		{Jres_books_stereo3._44c8_s_p5_0,Jres_books_stereo3._44c8_s_p5_1},
		{Jres_books_stereo3._44c8_s_p6_0,Jres_books_stereo3._44c8_s_p6_1},
		{Jres_books_stereo3._44c8_s_p7_0,Jres_books_stereo3._44c8_s_p7_1},
		{Jres_books_stereo3._44c8_s_p8_0,Jres_books_stereo3._44c8_s_p8_1},
		{Jres_books_stereo3._44c8_s_p9_0,Jres_books_stereo3._44c8_s_p9_1,Jres_books_stereo3._44c8_s_p9_2}
		}
	);

	private static final Jstatic_bookblock _resbook_44s_9 = new Jstatic_bookblock(
		new Jstatic_codebook[][]{
		{null},{null,null,Jres_books_stereo3._44c9_s_p1_0},{null,null,Jres_books_stereo3._44c9_s_p2_0},{null,null,Jres_books_stereo3._44c9_s_p3_0},
		{null,null,Jres_books_stereo3._44c9_s_p4_0},
		{Jres_books_stereo3._44c9_s_p5_0,Jres_books_stereo3._44c9_s_p5_1},
		{Jres_books_stereo3._44c9_s_p6_0,Jres_books_stereo3._44c9_s_p6_1},
		{Jres_books_stereo3._44c9_s_p7_0,Jres_books_stereo3._44c9_s_p7_1},
		{Jres_books_stereo3._44c9_s_p8_0,Jres_books_stereo3._44c9_s_p8_1},
		{Jres_books_stereo3._44c9_s_p9_0,Jres_books_stereo3._44c9_s_p9_1,Jres_books_stereo3._44c9_s_p9_2}
		}
	);

	private static final Jvorbis_residue_template _res_44s_n1[] = {
		new Jvorbis_residue_template( 2,0,32,  _residue_44_low,
		Jres_books_stereo4._huff_book__44cn1_s_short, Jres_books_stereo4._huff_book__44cn1_sm_short,
		_resbook_44s_n1, _resbook_44sm_n1 ),

		new Jvorbis_residue_template( 2,0,32,  _residue_44_low,
		Jres_books_stereo4._huff_book__44cn1_s_long, Jres_books_stereo4._huff_book__44cn1_sm_long,
		_resbook_44s_n1, _resbook_44sm_n1 )
	};

	private static final Jvorbis_residue_template _res_44s_0[] = {
		new Jvorbis_residue_template( 2,0,16,  _residue_44_low,
		Jres_books_stereo4._huff_book__44c0_s_short, Jres_books_stereo4._huff_book__44c0_sm_short,
		_resbook_44s_0, _resbook_44sm_0 ),

		new Jvorbis_residue_template( 2,0,32,  _residue_44_low,
		Jres_books_stereo3._huff_book__44c0_s_long, Jres_books_stereo4._huff_book__44c0_sm_long,
		_resbook_44s_0, _resbook_44sm_0 )
	};

	private static final Jvorbis_residue_template _res_44s_1[] = {
		new Jvorbis_residue_template( 2,0,16,  _residue_44_low,
		Jres_books_stereo4._huff_book__44c1_s_short, Jres_books_stereo4._huff_book__44c1_sm_short,
		_resbook_44s_1, _resbook_44sm_1 ),

		new Jvorbis_residue_template( 2,0,32,  _residue_44_low,
		Jres_books_stereo4._huff_book__44c1_s_long, Jres_books_stereo4._huff_book__44c1_sm_long,
		_resbook_44s_1, _resbook_44sm_1 )
	};

	private static final Jvorbis_residue_template _res_44s_2[] = {
		new Jvorbis_residue_template( 2,0,16,  _residue_44_mid,
		Jres_books_stereo2._huff_book__44c2_s_short, Jres_books_stereo2._huff_book__44c2_s_short,
		_resbook_44s_2, _resbook_44s_2 ),

		new Jvorbis_residue_template( 2,0,32,  _residue_44_mid,
		Jres_books_stereo1._huff_book__44c2_s_long, Jres_books_stereo1._huff_book__44c2_s_long,
		_resbook_44s_2, _resbook_44s_2 )
	};

	private static final Jvorbis_residue_template _res_44s_3[] = {
		new Jvorbis_residue_template( 2,0,16,  _residue_44_mid,
		Jres_books_stereo2._huff_book__44c3_s_short, Jres_books_stereo2._huff_book__44c3_s_short,
		_resbook_44s_3, _resbook_44s_3 ),

		new Jvorbis_residue_template( 2,0,32,  _residue_44_mid,
		Jres_books_stereo2._huff_book__44c3_s_long, Jres_books_stereo2._huff_book__44c3_s_long,
		_resbook_44s_3, _resbook_44s_3 )
	};

	private static final Jvorbis_residue_template _res_44s_4[] = {
		new Jvorbis_residue_template( 2,0,16,  _residue_44_mid,
		Jres_books_stereo2._huff_book__44c4_s_short, Jres_books_stereo2._huff_book__44c4_s_short,
		_resbook_44s_4, _resbook_44s_4 ),

		new Jvorbis_residue_template( 2,0,32,  _residue_44_mid,
		Jres_books_stereo2._huff_book__44c4_s_long, Jres_books_stereo2._huff_book__44c4_s_long,
		_resbook_44s_4, _resbook_44s_4 )
	};

	private static final Jvorbis_residue_template _res_44s_5[] = {
		new Jvorbis_residue_template( 2,0,16,  _residue_44_mid,
		Jres_books_stereo2._huff_book__44c5_s_short, Jres_books_stereo2._huff_book__44c5_s_short,
		_resbook_44s_5, _resbook_44s_5 ),

		new Jvorbis_residue_template( 2,0,32,  _residue_44_mid,
		Jres_books_stereo2._huff_book__44c5_s_long, Jres_books_stereo2._huff_book__44c5_s_long,
		_resbook_44s_5, _resbook_44s_5 )
	};

	private static final Jvorbis_residue_template _res_44s_6[] = {
		new Jvorbis_residue_template( 2,0,16,  _residue_44_high,
		Jres_books_stereo3._huff_book__44c6_s_short, Jres_books_stereo3._huff_book__44c6_s_short,
		_resbook_44s_6, _resbook_44s_6 ),

		new Jvorbis_residue_template( 2,0,32,  _residue_44_high,
		Jres_books_stereo2._huff_book__44c6_s_long, Jres_books_stereo2._huff_book__44c6_s_long,
		_resbook_44s_6, _resbook_44s_6 )
	};

	private static final Jvorbis_residue_template _res_44s_7[] = {
		new Jvorbis_residue_template( 2,0,16,  _residue_44_high,
		Jres_books_stereo3._huff_book__44c7_s_short, Jres_books_stereo3._huff_book__44c7_s_short,
		_resbook_44s_7, _resbook_44s_7 ),

		new Jvorbis_residue_template( 2,0,32,  _residue_44_high,
		Jres_books_stereo3._huff_book__44c7_s_long, Jres_books_stereo3._huff_book__44c7_s_long,
		_resbook_44s_7, _resbook_44s_7 )
	};

	private static final Jvorbis_residue_template _res_44s_8[] = {
		new Jvorbis_residue_template( 2,0,16,  _residue_44_high,
		Jres_books_stereo3._huff_book__44c8_s_short, Jres_books_stereo3._huff_book__44c8_s_short,
		_resbook_44s_8, _resbook_44s_8 ),

		new Jvorbis_residue_template( 2,0,32,  _residue_44_high,
		Jres_books_stereo3._huff_book__44c8_s_long, Jres_books_stereo3._huff_book__44c8_s_long,
		_resbook_44s_8, _resbook_44s_8 )
	};

	private static final Jvorbis_residue_template _res_44s_9[] = {
		new Jvorbis_residue_template( 2,0,16,  _residue_44_high,
		Jres_books_stereo3._huff_book__44c9_s_short, Jres_books_stereo3._huff_book__44c9_s_short,
		_resbook_44s_9, _resbook_44s_9 ),

		new Jvorbis_residue_template( 2,0,32,  _residue_44_high,
		Jres_books_stereo3._huff_book__44c9_s_long, Jres_books_stereo3._huff_book__44c9_s_long,
		_resbook_44s_9, _resbook_44s_9 )
	};

	protected static final Jvorbis_mapping_template _mapres_template_44_stereo[] = {
		new Jvorbis_mapping_template( Jsetup._map_nominal, _res_44s_n1 ), /* -1 */
		new Jvorbis_mapping_template( Jsetup._map_nominal, _res_44s_0 ), /* 0 */
		new Jvorbis_mapping_template( Jsetup._map_nominal, _res_44s_1 ), /* 1 */
		new Jvorbis_mapping_template( Jsetup._map_nominal, _res_44s_2 ), /* 2 */
		new Jvorbis_mapping_template( Jsetup._map_nominal, _res_44s_3 ), /* 3 */
		new Jvorbis_mapping_template( Jsetup._map_nominal, _res_44s_4 ), /* 4 */
		new Jvorbis_mapping_template( Jsetup._map_nominal, _res_44s_5 ), /* 5 */
		new Jvorbis_mapping_template( Jsetup._map_nominal, _res_44s_6 ), /* 6 */
		new Jvorbis_mapping_template( Jsetup._map_nominal, _res_44s_7 ), /* 7 */
		new Jvorbis_mapping_template( Jsetup._map_nominal, _res_44s_8 ), /* 8 */
		new Jvorbis_mapping_template( Jsetup._map_nominal, _res_44s_9 ), /* 9 */
	};
}
