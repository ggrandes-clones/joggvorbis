package libvorbis;

public final class Jvorbis_residue_template {
	final int res_type;
	/** 0 lowpass limited, 1 point stereo limited */
	final int limit_type;
	final int grouping;
	final Jvorbis_info_residue0 res;
	final Jstatic_codebook  book_aux;
	final Jstatic_codebook  book_aux_managed;
	final Jstatic_bookblock books_base;
	final Jstatic_bookblock books_base_managed;
	//
	public Jvorbis_residue_template(
			int i_res_type,
			int i_limit_type,
			int i_grouping,
			Jvorbis_info_residue0 vir_res,
			Jstatic_codebook  sc_book_aux,
			Jstatic_codebook  sc_book_aux_managed,
			Jstatic_bookblock sb_books_base,
			Jstatic_bookblock sb_books_base_managed
		)
	{
		res_type = i_res_type;
		limit_type = i_limit_type;
		grouping = i_grouping;
		res = vir_res;
		book_aux = sc_book_aux;
		book_aux_managed = sc_book_aux_managed;
		books_base = sb_books_base;
		books_base_managed = sb_books_base_managed;
	}
}
