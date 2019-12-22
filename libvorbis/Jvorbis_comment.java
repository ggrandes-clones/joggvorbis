package libvorbis;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import libogg.Jogg_packet;
import libogg.Joggpack_buffer;

/**
the comments are not part of vorbis_info so that vorbis_info can be
static storage
*/
public final class Jvorbis_comment {
	public static final String TEXT_ENCODING = "UTF-8";
	//private static final byte[] GENERAL_VENDOR_STRING;// FIXME GENERAL_VENDOR_STRING never used
	private static final byte[] ENCODE_VENDOR_STRING;
	static final byte[] vorbis;

	static {
		//byte[] str1 = null;
		byte[] str2 = null;
		byte[] str3 = null;
		try {
			//str1 = "Xiph.Org libVorbis 1.3.6".getBytes( ENCODING );
			str2 = "Xiph.Org libVorbis I 20180316 (Now 100% fewer shells)".getBytes( TEXT_ENCODING );
			str3 = "vorbis".getBytes( TEXT_ENCODING );
		} catch(final UnsupportedEncodingException e) {
		}
		//GENERAL_VENDOR_STRING = str1;
		ENCODE_VENDOR_STRING = str2;
		vorbis = str3;
	}

	/** unlimited user comment fields.  libvorbis writes 'libvorbis'
	  whatever vendor is set to in encode */
	public String[] user_comments = new String[0];
	//public byte[][] user_comments;// zero end do not need
	//public int[] comment_lengths;// dont need, use user_comments[i].length
	//public int comments;// dont need, use user_comments.length
	public String vendor = null;
	//
	private final void clear() {
		//user_comments = null;
		//comment_lengths = null;
		//comments = 0;
		user_comments = new String[0];
		vendor = null;
	}

	// info.c
	public final void vorbis_comment_init() {
		clear();
	}

	public final void vorbis_comment_add(final String comment) {
		/*vc->user_comments=_ogg_realloc(vc->user_comments,
				(vc->comments+2)*sizeof(*vc->user_comments));// FIXME using +2 is this a bug?
		vc->comment_lengths=_ogg_realloc(vc->comment_lengths,
				(vc->comments+2)*sizeof(*vc->comment_lengths));
		vc->comment_lengths[vc->comments]=strlen(comment);
		vc->user_comments[vc->comments]=_ogg_malloc(vc->comment_lengths[vc->comments]+1);
		strcpy(vc->user_comments[vc->comments], comment);
		vc->comments++;
		vc->user_comments[vc->comments]=NULL;*/// FIXME why need extra null? see pack and unpack
		this.user_comments = Arrays.copyOf( this.user_comments, this.user_comments.length + 1 );
		this.user_comments[this.user_comments.length - 1] = comment;
	}

	public final void vorbis_comment_add_tag(final String tag, final String contents) {
		final String comment = tag + '=' + contents;
		vorbis_comment_add( comment );
	}

	/* This is more or less the same as strncasecmp - but that doesn't exist
	 * everywhere, and this is a fairly trivial function, so we include it */
	/*private static int tagcompare(final byte[] s1, final byte[] s2, int n) {
		int c = 0;
		while( c < n ) {
			if( toupper( s1[c] ) != toupper(s2[c]) )//FIXME what about encoding and not latin symbols?
				return ! 0;
			c++;
		}
		return 0;
	}*/

	/*private static boolean tagcompare(final byte[] s1, final byte[] s2, int n) {
		try {
			String str1 = new String( s1, 0, s1.length < n ? s1.length : n, ENCODING );
			String str2 = new String( s2, 0, s2.length < n ? s2.length : n, ENCODING );
			return str1.compareToIgnoreCase( str2 ) == 0;
		} catch (UnsupportedEncodingException e) {
		}
		return false;
	}*/

	public final String vorbis_comment_query(final String tag, final int count) {
		int i;
		int found = 0;
		// TODO check for non-ascii symbols
		final String fulltag = tag.toUpperCase() + '=';

		for( i = 0; i < this.user_comments.length; i++ ) {
			if( this.user_comments[i].toUpperCase().startsWith( fulltag ) ) {
				if( count == found ) {
					return this.user_comments[i].substring( fulltag.length() );
				} else {
					found++;
				}
			}
		}
		return null; /* didn't find anything */
	}

	public final int vorbis_comment_query_count(final String tag) {
		int count = 0;
		// TODO check for non-ascii symbols
		final String fulltag = tag.toUpperCase() + '=';

		for( int i = 0; i < this.user_comments.length; i++ ) {
			if( this.user_comments[i].toUpperCase().startsWith( fulltag ) ) {
				count++;
			}
		}

		return count;
	}

	public final void vorbis_comment_clear() {
		//if( vc != null ) {
			/*int i;
			if( this.m_user_comments != null ) {
				for( i = 0; i < this.m_comments; i++ )
					if( this.m_user_comments[i] != null ) this.m_user_comments[i] = null;
				this.m_user_comments = null;
			}
			if( this.m_comment_lengths != null ) this.m_comment_lengths = null;
			if( this.m_vendor != null ) this.m_vendor = null;*/
			clear();
		//}
	}

	final int _vorbis_unpack_comment(final Joggpack_buffer opb) {
		final int vendorlen = opb.oggpack_read( 32 );
		if( (vendorlen < 0) || (vendorlen > opb.storage - 8) ) {// goto err_out;
			vorbis_comment_clear();
			return (Jcodec.OV_EBADHEADER);
		}
		final byte[] vendor_buf = new byte[vendorlen];
		Jvorbis_info._v_readstring( opb, vendor_buf, vendorlen );
		try { this.vendor = new String( vendor_buf, TEXT_ENCODING );
		} catch( final UnsupportedEncodingException e ) {}
		int i = opb.oggpack_read( 32 );
		if( (i < 0) ||
			(i > ((opb.storage - opb.oggpack_bytes()) >> 2)) ) {// goto err_out;
			vorbis_comment_clear();
			return (Jcodec.OV_EBADHEADER);
		}
		this.user_comments = new String[i];

		final int length = this.user_comments.length;
		for( i = 0; i < length; i++ ) {
			final int len = opb.oggpack_read( 32 );
			if( (len < 0) ||
				(len > opb.storage - opb.oggpack_bytes()) ) {//goto err_out;
				vorbis_comment_clear();
				return (Jcodec.OV_EBADHEADER);
			}
			if( len != 0 ) {
				Jvorbis_info._v_readstring( opb, this.user_comments, i, len );
			}
		}
		if( opb.oggpack_read( 1 ) != 1 ) {// goto err_out; /* EOP check */
//err_out:
			vorbis_comment_clear();
			return (Jcodec.OV_EBADHEADER);
		}

		return (0);
	}

	final int _vorbis_pack_comment(final Joggpack_buffer opb) {
		final int bytes = ENCODE_VENDOR_STRING.length;

		/* preamble */
		opb.oggpack_write( 0x03, 8 );
		Jvorbis_info._v_writestring( opb, vorbis, 6 );

		/* vendor */
		opb.oggpack_write( bytes, 32 );
		Jvorbis_info._v_writestring( opb, ENCODE_VENDOR_STRING, bytes );

		/* comments */
		final int length = this.user_comments.length;// java
		opb.oggpack_write( length, 32 );
		if( length != 0 ) {
			final String[] comments = this.user_comments;// java
			for( int i = 0; i < length; i++ ) {
				if( comments[i] != null ) {
					try {
						final byte[] comment = comments[i].getBytes( TEXT_ENCODING );
						opb.oggpack_write( comment.length, 32 );
						Jvorbis_info._v_writestring( opb, comment, comment.length );
					} catch (final UnsupportedEncodingException e) {
					}
				} else {// FIXME why using null? is this a bug? see unpack function
					opb.oggpack_write( 0, 32 );
				}
			}
		}
		opb.oggpack_write( 1, 1 );

		return (0);
	}

	public final int vorbis_commentheader_out(final Jogg_packet op) {

		final Joggpack_buffer opb = new Joggpack_buffer();

		opb.oggpack_writeinit();
		if( _vorbis_pack_comment( opb ) != 0 ) {
			opb.oggpack_writeclear();
			return Jcodec.OV_EIMPL;
		}

		op.packet_base = new byte[opb.oggpack_bytes()];
		op.packet = 0;
		System.arraycopy( opb.buffer, 0, op.packet_base, 0, op.packet_base.length );

		op.bytes = opb.oggpack_bytes();
		op.b_o_s = false;
		op.e_o_s = false;
		op.granulepos = 0;
		op.packetno = 1;

		opb.oggpack_writeclear();
		return 0;
	}
}
