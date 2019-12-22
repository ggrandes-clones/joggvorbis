package libvorbis;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.Arrays;

import libogg.Jogg_packet;
import libogg.Jogg_page;
import libogg.Jogg_stream_state;
import libogg.Jogg_sync_state;

/**
A 'chained bitstream' is a Vorbis bitstream that contains more than
one logical bitstream arranged end to end (the only form of Ogg
multiplexing allowed in a Vorbis bitstream; grouping [parallel
multiplexing] is not allowed in Vorbis)
<p>
A Vorbis file can be played beginning to end (streamed) without
worrying ahead of time about chaining (see decoder_example.c).  If
we have the whole file, however, and want random access
(seeking/scrubbing) or desire to know the total length/time of a
file, we need to account for the possibility of chaining.
<p>
We can handle things a number of ways; we can determine the entire
bitstream structure right off the bat, or find pieces on demand.
This example determines and caches structure for the entire
bitstream, but builds a virtual decoder on the fly when moving
between links in the chain.
<p>
There are also different ways to implement seeking.  Enough
information exists in an Ogg bitstream to seek to
sample-granularity positions in the output.  Or, one can seek by
picking some portion of the stream roughly in the desired area if
we only want coarse navigation through the stream.
*/
public final class JOggVorbis_File {
	//private static final int NOTOPEN   = 0;
	private static final int PARTOPEN  = 1;
	private static final int OPENED    = 2;
	private static final int STREAMSET = 3;
	private static final int INITSET   = 4;
	//
	private static final int SEEK_SET = 0;
	private static final int SEEK_CUR = 1;
	private static final int SEEK_END = 2;
	//
	/** The function prototypes for the callbacks are basically the same as for
	 * the stdio functions fread, fseek, fclose, ftell.
	 * The one difference is that the FILE * arguments have been replaced with
	 * a void * - this is to be used as a pointer to whatever internal data these
	 * functions might need. In the stdio case, it's just a FILE * cast to a void *
	 *
	 * If you use other functions, check the docs for these functions and return
	 * the right values. For seek_func(), you *MUST* return -1 if the stream is
	 * unseekable
	 */
	protected static final class Jov_callbacks {
		private final boolean is_read_supported;
		private final boolean is_seek_supported;
		private final boolean is_close_supported;
		private final boolean is_tell_supported;
		//
		private Jov_callbacks(final boolean isRead, final boolean isSeek, final boolean isClose, final boolean isTell) {
			is_read_supported = isRead;
			is_seek_supported = isSeek;
			is_close_supported = isClose;
			is_tell_supported = isTell;
		}
		//
		//size_t (*read_func)  (void *ptr, size_t size, size_t nmemb, void *datasource);
		//public abstract int read_func(Object ptr, int size, int nmemb, InputStream datasource);
		//int    (*seek_func)  (void *datasource, ogg_int64_t offset, int whence);
		//public abstract int seek_func(Object datasource, long offset, int whence);
		//int    (*close_func) (void *datasource);
		//public abstract int close_func(Object datasource);
		//long   (*tell_func)  (void *datasource);
		//public abstract int tell_func(Object datasource);
	}

	/** These structs below (OV_CALLBACKS_DEFAULT etc) are defined here as
	 * static data. That means that every file which includes this header
	 * will get its own copy of these structs whether it uses them or
	 * not unless it #defines OV_EXCLUDE_STATIC_CALLBACKS.
	 * These static symbols are essential on platforms such as Windows on
	 * which several different versions of stdio support may be linked to
	 * by different DLLs, and we need to be certain we know which one
	 * we're using (the same one as the main application).
	 */

	/**
	 * <pre>
	 * read_func
	 * seek_func
	 * close_func
	 * tell_func
	 * </pre>
	 */
	public static final Jov_callbacks OV_CALLBACKS_DEFAULT =
			new Jov_callbacks( true, true, true, true );

	/**
	 * <pre>
	 * read_func
	 * seek_func
	 * null
	 * tell_func
	 * </pre>
	 */
	public static final Jov_callbacks OV_CALLBACKS_NOCLOSE =
			new Jov_callbacks( true, true, false, true );

	/**
	 * <pre>
	 * read_func
	 * null
	 * close_func
	 * null
	 * </pre>
	 */
	public static final Jov_callbacks OV_CALLBACKS_STREAMONLY =
			new Jov_callbacks( true, false, true, false );

	/**
	 * <pre>
	 * read_func
	 * null
	 * null
	 * null
	 * </pre>
	 */
	public static final Jov_callbacks OV_CALLBACKS_STREAMONLY_NOCLOSE =
			new Jov_callbacks( true, false, false, false );
	//
	private static final class RandomAccessInputStream extends InputStream {

		private RandomAccessFile f;

		protected RandomAccessInputStream(final String path) throws FileNotFoundException {
			f = new RandomAccessFile( path, "r" );
		}

		/**
	     * Closes this random access file stream and releases any system
	     * resources associated with the stream. A closed random access
	     * file cannot perform input or output operations and cannot be
	     * reopened.
	     *
	     * <p> If this file has an associated channel then the channel is closed
	     * as well.
	     *
	     * @exception  IOException  if an I/O error occurs.
	     */
		@Override
		public void close() throws IOException {
			f.close();
			f = null;
		}

		@Override
		protected void finalize() throws Throwable {
			if( f != null ) {
				close();
			}
		}

		/**
	     * Returns the length of this file.
	     *
	     * @return     the length of this file, measured in bytes.
	     * @exception  IOException  if an I/O error occurs.
	     */
		private long length() throws IOException {
			return f.length();
		}

		/**
	     * Returns the current offset in this file.
	     *
	     * @return     the offset from the beginning of the file, in bytes,
	     *             at which the next read or write occurs.
	     * @exception  IOException  if an I/O error occurs.
	     */
		private long getFilePointer() throws IOException {
			return f.getFilePointer();
		}

		/**
	     * Reads a byte of data from this file. The byte is returned as an
	     * integer in the range 0 to 255 (<code>0x00-0x0ff</code>). This
	     * method blocks if no input is yet available.
	     * <p>
	     * @return     the next byte of data, or <code>-1</code> if the end of the
	     *             file has been reached.
	     * @exception  IOException  if an I/O error occurs. Not thrown if
	     *                          end-of-file has been reached.
	     */
		@Override
		public int read() throws IOException {
			return f.read();
		}

		/**
	     * Reads up to <code>len</code> bytes of data from this file into an
	     * array of bytes. This method blocks until at least one byte of input
	     * is available.
	     * <p>
	     *
	     * @param      b     the buffer into which the data is read.
	     * @param      off   the start offset in array <code>b</code>
	     *                   at which the data is written.
	     * @param      len   the maximum number of bytes read.
	     * @return     the total number of bytes read into the buffer, or
	     *             <code>-1</code> if there is no more data because the end of
	     *             the file has been reached.
	     * @exception  IOException If the first byte cannot be read for any reason
	     * other than end of file, or if the random access file has been closed, or if
	     * some other I/O error occurs.
	     * @exception  NullPointerException If <code>b</code> is <code>null</code>.
	     * @exception  IndexOutOfBoundsException If <code>off</code> is negative,
	     * <code>len</code> is negative, or <code>len</code> is greater than
	     * <code>b.length - off</code>
	     */
		@Override
		public int read(final byte[] b, final int off, final int len ) throws IOException {
			return f.read( b, off, len );
		}

		/**
	     * Reads up to <code>b.length</code> bytes of data from this file
	     * into an array of bytes. This method blocks until at least one byte
	     * of input is available.
	     * <p>
	     *
	     * @param      b   the buffer into which the data is read.
	     * @return     the total number of bytes read into the buffer, or
	     *             <code>-1</code> if there is no more data because the end of
	     *             this file has been reached.
	     * @exception  IOException If the first byte cannot be read for any reason
	     * other than end of file, or if the random access file has been closed, or if
	     * some other I/O error occurs.
	     * @exception  NullPointerException If <code>b</code> is <code>null</code>.
	     */
		@Override
		public int read(final byte[] b) throws IOException {
			return f.read( b );
		}

		/**
	     * Sets the file-pointer offset, measured from the beginning of this
	     * file, at which the next read or write occurs.  The offset may be
	     * set beyond the end of the file. Setting the offset beyond the end
	     * of the file does not change the file length.  The file length will
	     * change only by writing after the offset has been set beyond the end
	     * of the file.
	     *
	     * @param      pos   the offset position, measured in bytes from the
	     *                   beginning of the file, at which to set the file
	     *                   pointer.
	     * @exception  IOException  if <code>pos</code> is less than
	     *                          <code>0</code> or if an I/O error occurs.
	     */
		private void seek(final long pos) throws IOException {
			f.seek( pos );
		}

		// only for compatibility with InputStream
		@Override
		public int available() throws IOException {
			return (int)(f.length() - f.getFilePointer());
		}

		@Override
		public synchronized void mark(final int readlimit) {
		}

		@Override
		public boolean markSupported() {
			return false;
		}

		@Override
		public synchronized void reset() throws IOException {
		}

		@Override
		public long skip(final long n) throws IOException {
			return (long) f.skipBytes( (int)n );
		}
	}
	//
	private static final class Jinout_helper {
		private int serialno = 0;
		private long granpos = 0;
		private int[] serialno_list = null;
		private final void clear() {
			serialno = 0;
			granpos = 0;
			serialno_list = null;
		}
	}

	private final Jinout_helper inout = new Jinout_helper();

	/** Pointer to a InputStream */
	private InputStream  datasource = null;
	private boolean      seekable   = false;
	private long         offset     = 0;
	public long          end        = 0;
	private final Jogg_sync_state oy = new Jogg_sync_state();

	/* If the FILE handle isn't seekable (eg, a pipe), only the current
	 stream appears */
	private int   links       = 0;
	public long[] offsets     = null;
	public long[] dataoffsets = null;
	private int[] serialnos   = null;
	/** overloaded to maintain binary
	  compatibility; x2 size, stores both
	  beginning and end values */
	private long[]            pcmlengths = null;
	private Jvorbis_info[]    vi = null;
	private Jvorbis_comment[] vc = null;

	/* Decoding working state local storage */
	private long   pcm_offset       = 0;
	private int    ready_state      = 0;
	private int    current_serialno = 0;
	private int    current_link     = 0;

	private double bittrack  = 0.0;
	private double samptrack = 0.0;
	/** take physical pages, weld into a logical stream of packets */
	private final Jogg_stream_state os = new Jogg_stream_state();
	/** central working state for the packet->PCM decoder */
	private final Jvorbis_dsp_state vd = new Jvorbis_dsp_state();
	/** local working space for packet->PCM decode */
	private final Jvorbis_block     vb = new Jvorbis_block();

	private Jov_callbacks callbacks = null;
	//
	private final void clear() {
		inout.clear();
		datasource = null;
		seekable = false;
		offset = 0;
		end = 0;
		oy.clear();

		links = 0;
		offsets = null;
		dataoffsets = null;
		serialnos = null;

		pcmlengths = null;
		vi = null;
		vc = null;

		pcm_offset = 0;
		ready_state = 0;
		current_serialno = 0;
		current_link = 0;

		bittrack = 0.0;
		samptrack = 0.0;

		os.clear();
		vd.clear();
		vb.clear();

		callbacks = null;
	}
	//
	/*************************************************************************
	 * Many, many internal helpers.  The intention is not to be confusing;
	 * rampant duplication and monolithic function implementation would be
	 * harder to understand anyway.  The high level functions are last.  Begin
	 * grokking near the end of the file */

	/** greater-than-page-size granularity seeking */
	public static final int CHUNKSIZE = 65536;
	/** a smaller read size is needed for low-rate streaming. */
	private static final int READSIZE = 2048;

	/** read a little more data from the file/pipe into the ogg_sync framer */
	private final int _get_data() {
		if( ! this.callbacks.is_read_supported ) {
			return (-1);
		}
		if( this.datasource != null ) {
			final Jogg_sync_state oss = this.oy;// java
			final int buffer = oss.ogg_sync_buffer( READSIZE );
			try {
				final int bytes = this.datasource.read( oss.data, buffer, READSIZE );
				if( bytes > 0 ) {
					oss.ogg_sync_wrote( bytes );
				}
				if( bytes < 0 ) {
					return (-1);// fread returns 0 if eof or error
				}
				return (bytes);
			} catch(final IOException e) {
			}
			return -1;
		} else {
			return (0);
		}
	}

	private static long _tell_func(final InputStream is) throws IOException {
		if( is instanceof RandomAccessInputStream ) {
			return ((RandomAccessInputStream)is).getFilePointer();
		}
		throw new IOException("tell not supported");
		//return -1;
	}

	private static int _seek_func(final InputStream is, long offset, final int whence) {
		if( is instanceof RandomAccessInputStream ) {
			final RandomAccessInputStream r = (RandomAccessInputStream)is;
			try {
				switch( whence ) {
				case SEEK_CUR:
					offset += r.getFilePointer();
					break;
				case SEEK_END:
					offset += r.length();
					break;
				default:// SEEK_SET
					break;
				}
				r.seek( offset );
			} catch(final Exception e) {
				return -1;
			}
			return 0;
		}
		try {
			switch( whence ) {
			case SEEK_SET:
				is.reset();
				is.skip( offset );
				break;
			case SEEK_END:
				is.reset();
				is.skip( offset + is.available() );
				break;
			default:// SEEK_CUR
				throw new IOException("SEEK_CUR not supported");
				//break;
			}
		} catch(final Exception e){
			return -1;
		}
		return 0;
	}

	/** save a tiny smidge of verbosity to make the code more readable */
	private final int _seek_helper(final long off) {
		if( this.datasource != null ) {
			/* only seek if the file position isn't already there */
			if( this.offset != off ) {
				if( ! this.callbacks.is_seek_supported ||
					_seek_func( this.datasource, off, SEEK_SET ) == -1 ) {
					return Jcodec.OV_EREAD;
				}
				this.offset = off;
				this.oy.ogg_sync_reset();
			}
			return 0;
		}// else {
			/* shouldn't happen unless someone writes a broken callback */
			return Jcodec.OV_EFAULT;
		//}
		//return 0;
	}

	/* The read/seek functions track absolute position within the stream */

	/** from the head of the stream, get the next page.  boundary specifies
	   if the function is allowed to fetch more data from the stream (and
	   how much) or only use internally buffered data.

	   @param boundary -1) unbounded search<br>
	              0) read no additional data; use cached only<br>
	              n) search for a new page beginning for n bytes

	   @return   <0) did not find a page (OV_FALSE, OV_EOF, OV_EREAD)<br>
	              n) found a page at absolute offset n */
	private final long _get_next_page(final Jogg_page og, long boundary) {
		if( boundary > 0 ) {
			boundary += this.offset;
		}
		while( true ) {
			if( boundary > 0 && this.offset >= boundary ) {
				return (long)(Jcodec.OV_FALSE);
			}
			final int more = this.oy.ogg_sync_pageseek( og );

			if( more < 0 ) {
				/* skipped n bytes */
				this.offset -= more;
			} else {
				if( more == 0 ) {
					/* send more paramedics */
					if( boundary == 0 ) {
						return (long)(Jcodec.OV_FALSE);
					}
					{
						final int ret = _get_data();
						if( ret == 0 ) {
							return (long)(Jcodec.OV_EOF);
						}
						if( ret < 0 ) {
							return (long)(Jcodec.OV_EREAD);
						}
					}
				} else {
					/* got a page.  Return the offset at the page beginning,
					advance the internal offset past the page end */
					final long ret = this.offset;
					this.offset += more;
					return (ret);

				}
			}
		}
	}

	/** find the latest page beginning before the passed in position. Much
	  dirtier than the above as Ogg doesn't have any backward search
	  linkage.  no 'readp' as it will certainly have to read.<br>
	   returns offset or OV_EREAD, OV_FAULT */
	private final long _get_prev_page(long begin, final Jogg_page og) {
		final long finish = begin;
		long ret;
		long off = -1;

		while( off == -1 ) {
			begin -= CHUNKSIZE;
			if( begin < 0 ) {
				begin = 0;
			}

			ret = _seek_helper( begin );
			if( ret != 0 ) {
				return (ret);
			}

			while( this.offset < finish ) {
				og.clear();
				ret = _get_next_page( og, finish - this.offset );
				if( ret == Jcodec.OV_EREAD ) {
					return (Jcodec.OV_EREAD);
				}
				if( ret < 0 ) {
					break;
				} else {
					off = ret;
				}
			}
		}

		/* In a fully compliant, non-multiplexed stream, we'll still be
		   holding the last page.  In multiplexed (or noncompliant streams),
		   we will probably have to re-read the last page we saw */
		if( og.header_len == 0 ) {
			ret = _seek_helper( off );
			if( ret != 0 ) {
				return (ret);
			}

			ret = _get_next_page( og, CHUNKSIZE );
			if( ret < 0 ) {
				/* this shouldn't be possible */
				return (Jcodec.OV_EFAULT);
			}
		}

		return (off);
	}

	/* Java: extracted in place
	static void _add_serialno(ogg_page *og,long **serialno_list, int *n){
		long s = ogg_page_serialno(og);
		(*n)++;

		if(*serialno_list){
			*serialno_list = _ogg_realloc(*serialno_list, sizeof(**serialno_list)*(*n));
		}else{
			*serialno_list = _ogg_malloc(sizeof(**serialno_list));
		}

		(*serialno_list)[(*n)-1] = s;
	}
	*/

	/** returns nonzero if found */
	private static boolean _lookup_serialno(final int s, final int[] serialno_list, final int offset) {
		if( serialno_list != null ) {
			for( int i = offset, length = serialno_list.length; i < length; i++ ) {
				if( serialno_list[i] == s ) {
					return true;
				}
			}
		}
		return false;
	}

	private static boolean _lookup_page_serialno(final Jogg_page og, final int[] serialno_list, final int offset) {
		final int s = og.ogg_page_serialno();
		return _lookup_serialno( s, serialno_list, offset );
	}

	/** performs the same search as _get_prev_page, but prefers pages of
	   the specified serial number. If a page of the specified serialno is
	   spotted during the seek-back-and-read-forward, it will return the
	   info of last page of the matching serial number instead of the very
	   last page.  If no page of the specified serialno is seen, it will
	   return the info of last page and alter *serialno.  */
	private final long _get_prev_page_serial(long begin,
					final int[] serial_list, final int serial_offset,
					final Jinout_helper in_out) {
		final Jogg_page og = new Jogg_page();
		final long finish = begin;

		long prefoffset = -1;
		long off = -1;
		int ret_serialno = -1;// FIXME using ogg_int64_t
		long ret_gran = -1;

		while( off == -1 ) {
			begin -= CHUNKSIZE;
			if( begin < 0 ) {
				begin = 0;
			}

			final int r = _seek_helper( begin );
			if( r != 0 ) {
				return (r);
			}

			while( this.offset < finish ) {
				final long ret = _get_next_page( og, finish - this.offset );
				if( ret == Jcodec.OV_EREAD ) {
					return (Jcodec.OV_EREAD);
				}
				if( ret < 0 ) {
					break;
				} else {
					ret_serialno = og.ogg_page_serialno();
					ret_gran = og.ogg_page_granulepos();
					off = ret;

					if( ret_serialno == in_out.serialno ) {
						prefoffset = ret;
						in_out.granpos = ret_gran;
					}

					if( ! _lookup_serialno( ret_serialno, serial_list, serial_offset ) ) {
						/* we fell off the end of the link, which means we seeked
						back too far and shouldn't have been looking in that link
						to begin with.  If we found the preferred serial number,
						forget that we saw it. */
						prefoffset = -1;
					}
				}
			}
		}

		/* we're not interested in the page... just the serialno and granpos. */
		if( prefoffset >= 0 ) {
			return (prefoffset);
		}

		in_out.serialno = ret_serialno;
		in_out.granpos = ret_gran;
		return (off);

	}

	/** added instead goto goto bail_header; */
	private final void _bail_header(final Jvorbis_info vinfo, final Jvorbis_comment vcomment) {
		vinfo.vorbis_info_clear();
		vcomment.vorbis_comment_clear();
		this.ready_state = OPENED;
	}

	/** uses the local ogg_stream storage in vf; this is important for
	   non-streaming input sources */
	private final int _fetch_headers(final Jvorbis_info vinfo, final Jvorbis_comment vcomment,
					final Jinout_helper in_out, Jogg_page og_ptr) {

		if( og_ptr == null ) {
			final Jogg_page og = new Jogg_page();
			final long llret = _get_next_page( og, CHUNKSIZE );
			if( llret == Jcodec.OV_EREAD ) {
				return (Jcodec.OV_EREAD);
			}
			if( llret < 0 ) {
				return (Jcodec.OV_ENOTVORBIS);
			}
			og_ptr = og;
		}

		vinfo.vorbis_info_init();
		vcomment.vorbis_comment_init();
		this.ready_state = OPENED;

		/* extract the serialnos of all BOS pages + the first set of vorbis
		headers we see in the link */
		final Jogg_packet op = new Jogg_packet();
		final Jogg_stream_state oss = this.os;// java
		while( og_ptr.ogg_page_bos() ) {
			if( in_out != null ) {
				if( _lookup_page_serialno( og_ptr, in_out.serialno_list, 0 ) ) {
					/* a dupe serialnumber in an initial header packet set == invalid stream */
					in_out.serialno_list = null;
					_bail_header( vinfo, vcomment );//goto bail_header;
					return Jcodec.OV_EBADHEADER;
				}

				// inplace implementation _add_serialno( og_ptr, serialno_list, serialno_n );
				final int s = og_ptr.ogg_page_serialno();
				if( in_out.serialno_list != null ) {
					in_out.serialno_list = Arrays.copyOf( in_out.serialno_list, in_out.serialno_list.length + 1 );
				} else {
					in_out.serialno_list = new int[1];
				}
				in_out.serialno_list[in_out.serialno_list.length - 1] = s;
			}

			if( this.ready_state < STREAMSET ) {
				/* we don't have a vorbis stream in this link yet, so begin
				prospective stream setup. We need a stream to get packets */
				oss.ogg_stream_reset_serialno( og_ptr.ogg_page_serialno() );
				oss.ogg_stream_pagein( og_ptr );

				if( oss.ogg_stream_packetout( op ) > 0 &&
						Jvorbis_info.vorbis_synthesis_idheader( op ) ) {
					/* vorbis header; continue setup */
					this.ready_state = STREAMSET;

					if( Jvorbis_info.vorbis_synthesis_headerin( vinfo, vcomment, op ) != 0 ) {
						_bail_header( vinfo, vcomment );//goto bail_header;
						return Jcodec.OV_EBADHEADER;
					}
				}
			}

			/* get next page */
			{
				final long llret = _get_next_page( og_ptr, CHUNKSIZE );
				if( llret == Jcodec.OV_EREAD ) {
					_bail_header( vinfo, vcomment );//goto bail_header;
					return Jcodec.OV_EREAD;
				}
				if( llret < 0 ) {
					_bail_header( vinfo, vcomment );//goto bail_header;
					return Jcodec.OV_ENOTVORBIS;
				}

				/* if this page also belongs to our vorbis stream, submit it and break */
				if( this.ready_state == STREAMSET &&
					oss.serialno == og_ptr.ogg_page_serialno() ) {
					oss.ogg_stream_pagein( og_ptr );
					break;
				}
			}
		}

		if( this.ready_state != STREAMSET ) {
			_bail_header( vinfo, vcomment );//goto bail_header;
			return Jcodec.OV_ENOTVORBIS;
		}

		boolean allbos = false;
		while( true ) {

			int i = 0;
			while( i < 2 ) { /* get a page loop */

				while( i < 2 ) { /* get a packet loop */

					final int result = oss.ogg_stream_packetout( op );
					if( result == 0 ) {
						break;
					}
					if( result == -1 ) {
						_bail_header( vinfo, vcomment );//goto bail_header;
						return Jcodec.OV_EBADHEADER;
					}
					int ret;
					if( (ret = Jvorbis_info.vorbis_synthesis_headerin( vinfo, vcomment, op )) != 0 ) {
						_bail_header( vinfo, vcomment );//goto bail_header;
						return ret;
					}

					i++;
				}

				while( i < 2 ) {
					if( _get_next_page( og_ptr, CHUNKSIZE ) < 0 ) {
						_bail_header( vinfo, vcomment );//goto bail_header;
						return Jcodec.OV_EBADHEADER;
					}

					/* if this page belongs to the correct stream, go parse it */
					if( oss.serialno == og_ptr.ogg_page_serialno() ) {
						oss.ogg_stream_pagein( og_ptr );
						break;
					}

					/* if we never see the final vorbis headers before the link
					ends, abort */
					if( og_ptr.ogg_page_bos() ) {
						if( allbos ) {
							_bail_header( vinfo, vcomment );//goto bail_header;
							return Jcodec.OV_EBADHEADER;
						} else {
							allbos = true;
						}
					}

				/* otherwise, keep looking */
				}
			}

			return 0;
		}
/*
bail_header:
		Jvorbis_info.vorbis_info_clear( vi );
		Jvorbis_comment.vorbis_comment_clear( vc );
		vf.ready_state = OPENED;

		return ret;*/
	}

	/** Starting from current cursor position, get initial PCM offset of
	   next page.  Consumes the page in the process without decoding
	   audio, however this is only called during stream parsing upon
	   seekable open. */
	private final long _initial_pcmoffset(final Jvorbis_info vinfo) {
		final Jogg_page og = new Jogg_page();
		long accumulated = 0;
		int lastblock = -1;
		int result;
		final int serialno = this.os.serialno;
		final Jogg_packet op = new Jogg_packet();
		final Jogg_stream_state oss = this.os;// java
		while( true ) {

			if( _get_next_page( og, -1 ) < 0 ) {
				break; /* should not be possible unless the file is truncated/mangled */
			}

			if( og.ogg_page_bos() ) {
				break;
			}
			if( og.ogg_page_serialno() != serialno ) {
				continue;
			}

			/* count blocksizes of all frames in the page */
			oss.ogg_stream_pagein( og );
			while( (result = oss.ogg_stream_packetout( op )) != 0 ) {
				if( result > 0 ) { /* ignore holes */
					final int thisblock = vinfo.vorbis_packet_blocksize( op );
					if( thisblock >= 0 ) {
						if( lastblock != -1 ) {
							accumulated += (lastblock + thisblock) >> 2;
						}
						lastblock = thisblock;
					}
				}
			}

			if( og.ogg_page_granulepos() != -1 ) {
				/* pcm offset of last packet on the first audio page */
				accumulated = og.ogg_page_granulepos() - accumulated;
				break;
			}
		}

		/* less than zero?  Either a corrupt file or a stream with samples
		trimmed off the beginning, a normal occurrence; in both cases set
		the offset to zero */
		if( accumulated < 0 ) {
			accumulated = 0;
		}

		return accumulated;
	}

	/** finds each bitstream link one at a time using a bisection search
	   (has to begin by knowing the offset of the lb's initial page).
	   Recurses for each link so it can alloc the link storage after
	   finding them all, then unroll and fill the cache at the same time */
	private final int _bisect_forward_serialno(
					final long begin,
					long searched,
					final long finish,
					long endgran,
					int endserial,
					final int[] currentno_list,
					final int currentno_offset,
					int m) {
		// long dataoffset = searched;// FIXME why ?
		long searchgran = -1;
		// int ret;// FIXME using ogg_int64_t
		int serialno = this.os.serialno;
		/* invariants:
		we have the headers and serialnos for the link beginning at 'begin'
		we have the offset and granpos of the last page in the file (potentially
		not a page we care about)
		*/

		/* Is the last page in our list of current serialnumbers? */
		if( _lookup_serialno( endserial, currentno_list, currentno_offset ) ) {

			/* last page is in the starting serialno list, so we've bisected
			  down to (or just started with) a single link.  Now we need to
			  find the last vorbis page belonging to the first vorbis stream
			  for this link. */
			searched = finish;
			while( endserial != serialno ) {
				endserial = serialno;
				this.inout.serialno = endserial;
				this.inout.granpos = endgran;
				searched = _get_prev_page_serial( searched, currentno_list, currentno_offset, this.inout );
				endserial = this.inout.serialno;
				endgran = this.inout.granpos;
			}

			this.links = m + 1;
			//vf.offsets = null;
			//vf.serialnos = null;
			//vf.dataoffsets = null;

			this.offsets = new long[this.links + 1];
			this.vi = Arrays.copyOf( this.vi, this.links );
			this.vc = Arrays.copyOf( this.vc, this.links );
			this.serialnos = new int[this.links];
			this.dataoffsets = new long[this.links];
			this.pcmlengths = new long[this.links << 1];

			this.offsets[m + 1] = finish;
			this.offsets[m] = begin;
			this.pcmlengths[(m << 1) + 1] = (endgran < 0 ? 0 : endgran);

		} else {

			/* last page is not in the starting stream's serial number list,
			   so we have multiple links.  Find where the stream that begins
			   our bisection ends. */

			final int[] next_serialno_list;
			//int next_serialnos = 0;
			final Jvorbis_info vinfo = new Jvorbis_info();
			final Jvorbis_comment vcomment = new Jvorbis_comment();
			final Jogg_page og = new Jogg_page();
			int testserial = serialno + 1;

			/* the below guards against garbage seperating the last and
			   first pages of two links. */
			long endsearched = finish;
			long next = finish;
			final long[] pcm_lengths = this.pcmlengths;// java
			while( searched < endsearched ) {
				final long bisect;

				if( endsearched - searched < CHUNKSIZE ) {
					bisect = searched;
				} else {
					bisect = (searched + endsearched) >>> 1;
				}

				final int ret = _seek_helper(  bisect );
				if( ret != 0 ) {
					return (ret);
				}

				final long last = _get_next_page( og, -1 );
				if( last == Jcodec.OV_EREAD ) {
					return (Jcodec.OV_EREAD);
				}
				if( last < 0 || ! _lookup_page_serialno( og, currentno_list, currentno_offset ) ) {
					endsearched = bisect;
					if( last >= 0 ) {
						next = last;
					}
				} else {
					searched = this.offset;
				}
			}

			/* Bisection point found */
			/* for the time being, fetch end PCM offset the simple way */
			searched = next;
			while( testserial != serialno ) {
				testserial = serialno;
				this.inout.serialno = testserial;
				this.inout.granpos = searchgran;
				searched = _get_prev_page_serial( searched, currentno_list, currentno_offset, this.inout );
				testserial = this.inout.serialno;
				searchgran = this.inout.granpos;
			}

			int ret = _seek_helper(  next );
			if( ret != 0 ) {
				return (ret);
			}

			// ret = _fetch_headers( vf, &vi, &vc, &next_serialno_list, &next_serialnos, NULL );
			this.inout.serialno_list = null;
			ret = _fetch_headers( vinfo, vcomment, this.inout, null );
			if( ret != 0 ) {
				return (ret);
			}
			next_serialno_list = this.inout.serialno_list;
			serialno = this.os.serialno;
			final long dataoffset = this.offset;

			/* this will consume a page, however the next bisection always
			   starts with a raw seek */
			final long pcmoffset = _initial_pcmoffset( vinfo );

			ret = _bisect_forward_serialno( next, this.offset, finish, endgran, endserial,
									next_serialno_list, 0, m + 1 );
			if( ret != 0 ) {
				return (ret );
			}

			// next_serialno_list = null;
			this.inout.serialno_list = null;

			this.offsets[++m] = next;
			this.serialnos[m] = serialno;
			this.dataoffsets[m] = dataoffset;

			this.vi[m] = vinfo;
			this.vc[m--] = vcomment;

			m <<= 1;
			pcm_lengths[++m] = searchgran;
			pcm_lengths[++m] = pcmoffset;
			pcm_lengths[++m] -= pcmoffset;
			if( pcm_lengths[m] < 0 ) {
				pcm_lengths[m] = 0;
			}
		}
		return (0);
	}

	private final int _make_decode_ready() {
		if( this.ready_state > STREAMSET ) {
			return 0;
		}
		if( this.ready_state < STREAMSET ) {
			return Jcodec.OV_EFAULT;
		}
		if( this.seekable ) {
			if( this.vd.vorbis_synthesis_init( this.vi[this.current_link] ) ) {
				return Jcodec.OV_EBADLINK;
			}
		} else {
			if( this.vd.vorbis_synthesis_init( this.vi[0] ) ) {
				return Jcodec.OV_EBADLINK;
			}
		}
		this.vd.vorbis_block_init( this.vb );
		this.ready_state = INITSET;
		this.bittrack = 0.f;
		this.samptrack = 0.f;
		return 0;
	}

	private final int _open_seekable2() {
		final long dataoffset = this.dataoffsets[0];
		long endgran = -1;
		int endserial = this.os.serialno;
		final int serialno = this.os.serialno;

		/* we're partially open and have a first link header state in
		storage in vf */

		/* fetch initial PCM offset */
		final long pcmoffset = _initial_pcmoffset( this.vi[0] );

		/* we can seek, so set out learning all about this file */
		if( this.callbacks.is_seek_supported && this.callbacks.is_tell_supported ) {
			try {
				_seek_func( this.datasource, 0, SEEK_END );
				this.offset = this.end = _tell_func( this.datasource );
			} catch(final IOException e) {
				e.printStackTrace();
			}
		} else {
			this.offset = this.end = -1;
		}

		/* If seek_func is implemented, tell_func must also be implemented */
		if( this.end == -1 ) {
			return (Jcodec.OV_EINVAL);
		}

		/* Get the offset of the last page of the physical bitstream, or, if
		we're lucky the last vorbis page of this link as most OggVorbis
		files will contain a single logical bitstream */
		this.inout.serialno = endserial;
		this.inout.granpos = endgran;
		final long finish = _get_prev_page_serial( this.end, this.serialnos, 2, this.inout );
		if( finish < 0 ) {
			return (int)finish;
		}
		endserial = this.inout.serialno;
		endgran = this.inout.granpos;

		/* now determine bitstream structure recursively */
		if( _bisect_forward_serialno( 0, dataoffset, finish, endgran, endserial,
				this.serialnos, 2, 0 ) < 0 ) {
			return (Jcodec.OV_EREAD );
		}

		this.offsets[0] = 0;
		this.serialnos[0] = serialno;
		this.dataoffsets[0] = dataoffset;
		this.pcmlengths[0] = pcmoffset;
		long length1 = this.pcmlengths[1];// java
		length1 -= pcmoffset;
		if( length1 < 0 ) {
			length1 = 0;
		}
		this.pcmlengths[1] = length1;

		return ov_raw_seek( dataoffset );
	}

	/** clear out the current logical bitstream decoder */
	private final void _decode_clear() {
		this.vd.vorbis_dsp_clear();
		this.vb.vorbis_block_clear();
		this.ready_state = OPENED;
	}

	/** fetch and process a packet.  Handles the case where we're at a
	   bitstream boundary and dumps the decoding machine.  If the decoding
	   machine is unloaded, it loads it.  It also keeps pcm_offset up to
	   date (seek and read both use this.  seek uses a special hack with
	   readp).

	   @return <0) error, OV_HOLE (lost packet) or OV_EOF<br>
	            0) need more data (only if readp==0)<br>
	            1) got a packet
	*/
	private final int _fetch_and_process_packet(
					Jogg_packet op_in,
					final int readp,
					final int spanp) {
		final Jogg_page og = new Jogg_page();
		final Jogg_packet op = new Jogg_packet();
		/* handle one packet.  Try to fetch it from current stream state */
		/* extract packets from page */
		while( true ) {

			if( this.ready_state == STREAMSET ) {
				final int ret = _make_decode_ready();
				if( ret < 0 ) {
					return ret;
				}
			}

			/* process a packet if we can. */

			if( this.ready_state == INITSET ) {
				final int hs = this.vi[0].vorbis_synthesis_halfrate_p();

				while( true ) {
					final Jogg_packet op_ptr = (op_in != null ? op_in : op );
					final int result = this.os.ogg_stream_packetout( op_ptr );
					long granulepos;

					op_in = null;
					if( result == -1 ) {
						return (Jcodec.OV_HOLE); /* hole in the data. */
					}
					if( result > 0 ) {
						/* got a packet.  process it */
						granulepos = op_ptr.granulepos;
						if( this.vb.vorbis_synthesis( op_ptr ) == 0 ) { /* lazy check for lazy
																header handling.  The
																header packets aren't
																audio, so if/when we
																submit them,
																vorbis_synthesis will
																reject them */

							/* suck in the synthesis data and track bitrate */
							{
								final int oldsamples =
										this.vd.vorbis_synthesis_pcmout( false ).samples;
								/* for proper use of libvorbis within libvorbisfile,
								oldsamples will always be zero. */
								if( oldsamples != 0 ) {
									return (Jcodec.OV_EFAULT);
								}

								this.vd.vorbis_synthesis_blockin( this.vb );
								this.samptrack +=
									(this.vd.vorbis_synthesis_pcmout( false ).samples << hs);
								this.bittrack += op_ptr.bytes << 3;
							}

							/* update the pcm offset. */
							if( granulepos != -1 && ! op_ptr.e_o_s ) {
								final int link2 = (this.seekable ? this.current_link << 1 : 0);

								/* this packet has a pcm_offset on it (the last packet
								completed on a page carries the offset) After processing
								(above), we know the pcm position of the *last* sample
								ready to be returned. Find the offset of the *first*

								As an aside, this trick is inaccurate if we begin
								reading anew right at the last page; the end-of-stream
								granulepos declares the last frame in the stream, and the
								last packet of the last page may be a partial frame.
								So, we need a previous granulepos from an in-sequence page
								to have a reference point.  Thus the !op_ptr->e_o_s clause
								above */
								final long[] pcm_lengths = this.pcmlengths;// java
								if( this.seekable && link2 > 0 ) {
									granulepos -= pcm_lengths[link2];
								}
								if( granulepos < 0 ) {
									granulepos = 0; /* actually, this
																shouldn't be possible
																here unless the stream
																is very broken */
								}

								final int samples =
									(this.vd.vorbis_synthesis_pcmout( false ).samples << hs);

								granulepos -= samples;
								for( int i = 1; i < link2; i += 2 ) {
									granulepos += pcm_lengths[i];
								}
								this.pcm_offset = granulepos;
							}
							return (1);
						}
					} else {
						break;
					}
				}
			}

			if( this.ready_state >= OPENED ) {
				//long ret;

				while( true ) {
					/* the loop is not strictly necessary, but there's no sense in
					   doing the extra checks of the larger loop for the common
					   case in a multiplexed bistream where the page is simply
					   part of a different logical bitstream; keep reading until
					   we get one with the correct serialno */

					if( readp == 0 ) {
						return (0);
					}
						if( (/*ret = */_get_next_page( og, -1 )) < 0 ) {
							return (Jcodec.OV_EOF); /* eof. leave unitialized */
						}

					/* bitrate tracking; add the header's bytes here, the body bytes
					   are done by packet above */
					this.bittrack += og.header_len << 3;

					if( this.ready_state == INITSET ) {
						if( this.current_serialno != og.ogg_page_serialno() ) {

							/* two possibilities:
							   1) our decoding just traversed a bitstream boundary
							   2) another stream is multiplexed into this logical section */

							if( og.ogg_page_bos() ) {
								/* boundary case */
								if( spanp == 0 ) {
									return (Jcodec.OV_EOF);
								}

								_decode_clear();

								if( ! this.seekable ) {
									this.vi[0].vorbis_info_clear();
									this.vc[0].vorbis_comment_clear();
								}
								break;

							} else {
								continue; /* possibility #2 */
							}
						}
					}

					break;
				}
			}

			/* Do we need to load a new machine before submitting the page? */
			/* This is different in the seekable and non-seekable cases.

			   In the seekable case, we already have all the header
			   information loaded and cached; we just initialize the machine
			   with it and continue on our merry way.

			   In the non-seekable (streaming) case, we'll only be at a
			   boundary if we just left the previous logical bitstream and
			   we're now nominally at the header of the next bitstream
			*/

			if( this.ready_state != INITSET ) {
				// int link;

				if( this.ready_state < STREAMSET ) {
					if( this.seekable ) {
						final int serialno = og.ogg_page_serialno();

						/* match the serialno to bitstream section.  We use this rather than
						 offset positions to avoid problems near logical bitstream
						 boundaries */
						final int[] serial_nos = this.serialnos;// java
						int link = 0;
						final int count = this.links;
						for( ; link < count; link++ ) {
							if( serial_nos[link] == serialno ) {
								break;
							}
						}

						if( link == count ) {
							continue; /* not the desired Vorbis
															bitstream section; keep
															trying */
						}

						this.current_serialno = serialno;
						this.current_link = link;

						this.os.ogg_stream_reset_serialno( this.current_serialno );
						this.ready_state = STREAMSET;

					} else {
						/* we're streaming */
						/* fetch the three header packets, build the info struct */

						final int ret = _fetch_headers( this.vi[0], this.vc[0], null, og );
						if( ret != 0 ) {
							return (ret);
						}
						this.current_serialno = this.os.serialno;
						this.current_link++;
						// link = 0;// FIXME why?
					}
				}
			}

			/* the buffered page is the data we want, and we're ready for it;
			add it to the stream state */
			this.os.ogg_stream_pagein( og );

		}
	}

	/** if, eg, 64 bit stdio is configured by default, this will build with
	   fseek64 */
	/*private static int _fseek64_wrap(FILE *f, ogg_int64_t off, int whence)
		if( f == null ) return (-1);
		return _seek_func( f, off, whence );
	}*/

	private final int _ov_open1(final InputStream f, final byte[] initial,
					final int ibytes, final Jov_callbacks cb) {
		final int offsettest = ((f != null && cb.is_seek_supported) ?
				_seek_func( f, 0, SEEK_CUR ) : -1);
		//int[] serialno_list = null;// moved to inout.serialno_list
		//int serialno_list_size;// moved to inout.serialno_list.length
		final int ret;

		clear();
		this.datasource = f;
		this.callbacks = cb;

		/* init the framing state */
		final Jogg_sync_state oss = this.oy;// java
		oss.ogg_sync_init();

		/* perhaps some data was previously read into a buffer for testing
		 against other stream types.  Allow initialization from this
		 previously read data (especially as we may be reading from a
		 non-seekable stream) */
		if( initial != null ) {
			final int buffer = oss.ogg_sync_buffer( ibytes );
			System.arraycopy( initial, 0, oss.data, buffer, ibytes );
			oss.ogg_sync_wrote( ibytes );
		}

		/* can we seek? Stevens suggests the seek test was portable */
		if( offsettest != -1 ) {
			this.seekable = true;
		}

		/* No seeking yet; Set up a 'single' (current) logical bitstream
		 entry for partial open */
		this.links = 1;
		this.vi = new Jvorbis_info[this.links];
		this.vc = new Jvorbis_comment[this.links];
		for( int i = 0; i < this.links; i++ ) {
			this.vi[i] = new Jvorbis_info();
			this.vc[i] = new Jvorbis_comment();
		}
		this.os.ogg_stream_init( -1 ); /* fill in the serialno later */

		/* Fetch all BOS pages, store the vorbis header and all seen serial
		 numbers, load subsequent vorbis setup headers */
		this.inout.serialno_list = null;
		if( (ret = _fetch_headers( this.vi[0], this.vc[0], this.inout, null) ) < 0 ) {
			this.datasource = null;
			ov_clear();
		} else {
			/* serial number list for first link needs to be held somewhere
			   for second stage of seekable stream open; this saves having to
			   seek/reread first link's serialnumber data then. */
			this.serialnos = new int[this.inout.serialno_list.length + 2];
			this.serialnos[0] = this.current_serialno = this.os.serialno;
			this.serialnos[1] = this.inout.serialno_list.length;
			System.arraycopy( this.inout.serialno_list, 0, this.serialnos, 2, this.inout.serialno_list.length );

			this.offsets = new long[1];
			this.dataoffsets = new long[1];
			this.offsets[0] = 0;
			this.dataoffsets[0] = this.offset;

			this.ready_state = PARTOPEN;
		}
		this.inout.serialno_list = null;//serialno_list = null;
		return (ret);
	}

	private final int _ov_open2() {
		if( this.ready_state != PARTOPEN ) {
			return Jcodec.OV_EINVAL;
		}
		this.ready_state = OPENED;
		if( this.seekable ) {
			final int ret = _open_seekable2();
			if( ret != 0 ) {
				this.datasource = null;
				ov_clear();
			}
			return (ret);
		} else {
			this.ready_state = STREAMSET;
		}

		return 0;
	}

	/** clear out the OggVorbis_File struct */
	public final int ov_clear() {
		//if( vf != null ) {
			this.vb.vorbis_block_clear();
			this.vd.vorbis_dsp_clear();
			this.os.ogg_stream_clear();

			if( this.vi != null && this.links != 0 ) {
				for( int i = 0; i < this.links; i++ ) {
					this.vi[i].vorbis_info_clear();
					this.vc[i].vorbis_comment_clear();
				}
				this.vi = null;
				this.vc = null;
			}
			this.dataoffsets = null;
			this.pcmlengths = null;
			this.serialnos = null;
			this.offsets = null;
			this.oy.ogg_sync_clear();
			if( this.datasource != null && this.callbacks.is_close_supported ) {
				try { this.datasource.close(); } catch(final IOException e) {}
			}
			clear();
		//}
/*#ifdef DEBUG_LEAKS
		_VDBG_dump();
#endif*/
		return (0);
	}

	/** Java InputStream doesn't support seek. This function returns a seekable InputSream */
	public static final InputStream ov_open(final String path) throws FileNotFoundException {
		return new RandomAccessInputStream( path );
	}

	/** inspects the OggVorbis file and finds/documents all the logical
	   bitstreams contained in it.  Tries to be tolerant of logical
	   bitstream sections that are truncated/woogie.

	   @return -1) error<br>
				0) OK
	*/
	public final int ov_open_callbacks(final InputStream f,
				final byte[] initial, final int ibytes, final Jov_callbacks cb) {
		final int ret = _ov_open1( f, initial, ibytes, cb );
		if( ret != 0 ) {
			return ret;
		}
		return _ov_open2();
	}

	public final int ov_open(final InputStream f, final byte[] initial, final int ibytes) {
		return ov_open_callbacks( f, initial, ibytes, OV_CALLBACKS_DEFAULT );
	}

	/* FIXME: why need this function, if FILE* do not returns?
	public final int ov_fopen(final String path) {
		int ret;
		InputStream f = null;
		try {
			f = new RandomAccessInputStream( path );

			ret = ov_open( f, null, 0 );
			if( ret != 0 ) f.close();// java: what is correct, return ret or -1?
			return ret;
		} catch(IOException e) {
			return -1;
		}
	}
	*/

	/** cheap hack for game usage where downsampling is desirable; there's
	   no need for SRC as we can just do it cheaply in libvorbis. */
	public final int ov_halfrate(final boolean flag) {
		if( this.vi == null ) {
			return Jcodec.OV_EINVAL;
		}
		if( this.ready_state > STREAMSET ) {
			/* clear out stream state; dumping the decode machine is needed to
			   reinit the MDCT lookups. */
			this.vd.vorbis_dsp_clear();
			this.vb.vorbis_block_clear();
			this.ready_state = STREAMSET;
			if( this.pcm_offset >= 0 ) {
				final long pos = this.pcm_offset;
				this.pcm_offset = -1; /* make sure the pos is dumped if unseekable */
				ov_pcm_seek( pos );
			}
		}

		final Jvorbis_info[] vinfo = this.vi;// java
		for( int i = 0, ie = this.links; i < ie; i++ ) {
			if( vinfo[i].vorbis_synthesis_halfrate( flag ) != 0 ) {
				if( flag ) {
					ov_halfrate( false );
				}
				return Jcodec.OV_EINVAL;
			}
		}
		return 0;
	}

	/** @return 1 or 0 */
	public final int ov_halfrate_p() {
		if( this.vi == null ) {
			return Jcodec.OV_EINVAL;
		}
		return this.vi[0].vorbis_synthesis_halfrate_p();
	}

	/** Only partially open the vorbis file; test for Vorbisness, and load
	   the headers for the first chain.  Do not seek (although test for
	   seekability).  Use ov_test_open to finish opening the file, else
	      ov_clear to close/free it. Same return codes as open.

	   Note that vorbisfile does _not_ take ownership of the file if the
	   call fails; the calling applicaiton is responsible for closing the file
	   if this call returns an error. */

	public final int ov_test_callbacks(final InputStream f,
				final byte[] initial, final int ibytes, final Jov_callbacks cb)
	{
		return _ov_open1( f, initial, ibytes, cb );
	}

	public final int ov_test(final InputStream f, final byte[] initial, final int ibytes) {
		return ov_test_callbacks( f, initial, ibytes, OV_CALLBACKS_DEFAULT );
	}

	public final int ov_test_open() {
		if( this.ready_state != PARTOPEN ) {
			return (Jcodec.OV_EINVAL);
		}
		return _ov_open2();
	}

	/** How many logical bitstreams in this physical bitstream? */
	public final int ov_streams() {
		return this.links;
	}

	/** Is the FILE * associated with vf seekable? */
	public final boolean ov_seekable() {
		return this.seekable;
	}

	/** returns the bitrate for a given logical bitstream or the entire
	   physical bitstream.  If the file is open for random access, it will
	   find the *actual* average bitrate.  If the file is streaming, it
	   returns the nominal bitrate (if set) else the average of the
	   upper/lower bounds (if set) else -1 (unset).<p>

	   If you want the actual bitrate field settings, get them from the
	   vorbis_info structs */
	public final int ov_bitrate(int i) {
		if( this.ready_state < OPENED ) {
			return (Jcodec.OV_EINVAL);
		}
		if( i >= this.links ) {
			return (Jcodec.OV_EINVAL);
		}
		if( ! this.seekable && i != 0 ) {
			return (ov_bitrate( 0 ));
		}
		if( i < 0 ) {
			long bits = 0;
			for( i = 0; i < this.links; i++ ) {
				bits += (this.offsets[i + 1] - this.dataoffsets[i]) << 3;
			}
			/* This once read: return (rint(bits/ov_time_total(vf,-1)));
			* gcc 3.x on x86 miscompiled this at optimisation level 2 and above,
			* so this is slightly transformed to make it work.
			*/
			final double br = (double)bits / ov_time_total( -1 );
			return (int)Math.rint( br );
		} else {
			if( this.seekable ) {
				/* return the actual bitrate */
				return (int)Math.rint( ((this.offsets[i + 1] - this.dataoffsets[i]) << 3) / ov_time_total( i ) );
			} else {
				/* return nominal if set */
				final Jvorbis_info[] vinfo = this.vi;// java
				if( vinfo[i].bitrate_nominal > 0 ) {
					return vinfo[i].bitrate_nominal;
				} else {
					if( vinfo[i].bitrate_upper > 0 ) {
						if( vinfo[i].bitrate_lower > 0 ) {
							return (vinfo[i].bitrate_upper + vinfo[i].bitrate_lower) >>> 1;
						} else {
							return vinfo[i].bitrate_upper;
						}
					}
					return (Jcodec.OV_FALSE);
				}
			}
		}
	}

	/** @return the actual bitrate since last call. returns -1 if no
	   additional data to offer since last call (or at beginning of stream),
	   EINVAL if stream is only partially open
	 */
	public final int ov_bitrate_instant() {
		final int link = (this.seekable ? this.current_link : 0);
		if( this.ready_state < OPENED ) {
			return (Jcodec.OV_EINVAL);
		}
		if( this.samptrack == 0 ) {
			return (Jcodec.OV_FALSE);
		}
		final int ret = (int)(this.bittrack / this.samptrack * (double)this.vi[link].rate + .5);
		this.bittrack = 0.f;
		this.samptrack = 0.f;
		return (ret);
	}

	/** Guess */
	public final int ov_serialnumber(final int i) {
		if( i >= this.links ) {
			return (ov_serialnumber( this.links - 1 ) );
		}
		if( ! this.seekable && i >= 0 ) {
			return (ov_serialnumber( -1 ) );
		}
		if( i < 0 ) {
			return (this.current_serialno);
		} else {
			return (this.serialnos[i]);
		}
	}

	/** @return total raw (compressed) length of content<br>
			if i==-1 raw (compressed) length of that logical bitstream<br>
			for i==0 to n OV_EINVAL if the stream is not seekable
			(we can't know the length) or if stream is only partially open
	*/
	public final long ov_raw_total(int i) {
		if( this.ready_state < OPENED ) {
			return (Jcodec.OV_EINVAL);
		}
		final int count = this.links;// java
		if( ! this.seekable || i >= count ) {
			return (Jcodec.OV_EINVAL);
		}
		if( i < 0 ) {
			long acc = 0;
			for( i = 0; i < count; i++ ) {
				acc += ov_raw_total( i );
			}
			return (acc);
		} else {
			return (this.offsets[i + 1] - this.offsets[i] );
		}
	}

	/** @return total PCM length (samples) of content<br>
			if i==-1 PCM length (samples) of that logical bitstream<br>
			for i==0 to n OV_EINVAL if the stream is not seekable
			(we can't know the length) or only partially open
	*/
	public final long ov_pcm_total(int i) {
		if( this.ready_state < OPENED ) {
			return (Jcodec.OV_EINVAL);
		}
		final int count = this.links;// java
		if( ! this.seekable || i >= count ) {
			return (Jcodec.OV_EINVAL);
		}
		if( i < 0 ) {
			long acc = 0;
			for( i = 0; i < count; i++ ) {
				acc += ov_pcm_total( i );
			}
			return (acc);
		}// else {
			return (this.pcmlengths[(i << 1) + 1]);
		//}
	}

	/** @return: total seconds of content if i==-1
				seconds in that logical bitstream for i==0 to n
				OV_EINVAL if the stream is not seekable (we can't know the
				length) or only partially open
	*/
	public final double ov_time_total(int i) {
		if( this.ready_state < OPENED ) {
			return (Jcodec.OV_EINVAL);
		}
		final int count = this.links;// java
		if( ! this.seekable || i >= count ) {
			return (Jcodec.OV_EINVAL);
		}
		if( i < 0 ) {
			double acc = 0;
			for( i = 0; i < count; i++ ) {
				acc += ov_time_total( i );
			}
			return (acc);
		}// else {
			return ((double)(this.pcmlengths[(i << 1) + 1]) / this.vi[i].rate);
		//}
	}

	/** seek to an offset relative to the *compressed* data. This also
	   scans packets to update the PCM cursor. It will cross a logical
	   bitstream boundary, but only if it can't get any packets out of the
	   tail of the bitstream we seek to (so no surprises).

	   @return zero on success, nonzero on failure */
	public final int ov_raw_seek(final long pos) {
		if( this.ready_state < OPENED ) {
			return (Jcodec.OV_EINVAL);
		}
		if( ! this.seekable ) {
			return (Jcodec.OV_ENOSEEK); /* don't dump machine if we can't seek */
		}

		if( pos < 0 || pos > this.end ) {
			return (Jcodec.OV_EINVAL);
		}

		/* is the seek position outside our current link [if any]? */
		if( this.ready_state >= STREAMSET ) {
			if( pos < this.offsets[this.current_link] || pos >= this.offsets[this.current_link + 1] ) {
				_decode_clear(); /* clear out stream state */
			}
		}

		/* don't yet clear out decoding machine (if it's initialized), in
		 the case we're in the same link.  Restart the decode lapping, and
		 let _fetch_and_process_packet deal with a potential bitstream
		 boundary */
		this.pcm_offset = -1;
		this.os.ogg_stream_reset_serialno( this.current_serialno ); /* must set serialno */
		this.vd.vorbis_synthesis_restart();

		final int ret = _seek_helper( pos );
		if( ret != 0 ) {// goto seek_error;
			/* dump the machine so we're in a known state */
			this.pcm_offset = -1;
			//work_os.ogg_stream_clear();
			_decode_clear();
			return Jcodec.OV_EBADLINK;
		}

		/* we need to make sure the pcm_offset is set, but we don't want to
		 advance the raw cursor past good packets just to get to the first
		 with a granulepos.  That's not equivalent behavior to beginning
		 decoding as immediately after the seek position as possible.

		 So, a hack.  We use two stream states; a local scratch state and
		 the shared vf->os stream state.  We use the local state to
		 scan, and the shared state as a buffer for later decode.

		 Unfortuantely, on the last page we still advance to last packet
		 because the granulepos on the last page is not necessarily on a
		 packet boundary, and we need to make sure the granpos is
		 correct.
		*/
		final Jogg_stream_state work_os = new Jogg_stream_state();
		{
			final Jogg_page og = new Jogg_page();
			final Jogg_packet op = new Jogg_packet();
			int lastblock = 0;
			int accblock = 0;
			int thisblock = 0;
			boolean lastflag = false;
			boolean firstflag = false;
			long pagepos = -1;

			work_os.ogg_stream_init( this.current_serialno ); /* get the memory ready */
			work_os.ogg_stream_reset(); /* eliminate the spurious OV_HOLE
										return from not necessarily
										starting from the beginning */

			while( true ) {
				if( this.ready_state >= STREAMSET ) {
					/* snarf/scan a packet if we can */
					final int result = work_os.ogg_stream_packetout( op );

					if( result > 0 ) {

						if( this.vi[this.current_link].codec_setup != null ) {
							thisblock = this.vi[this.current_link].vorbis_packet_blocksize( op );
							if( thisblock < 0 ) {
								this.os.ogg_stream_packetout( null );
								thisblock = 0;
							} else {

								/* We can't get a guaranteed correct pcm position out of the
								 last page in a stream because it might have a 'short'
								 granpos, which can only be detected in the presence of a
								 preceding page.  However, if the last page is also the first
								 page, the granpos rules of a first page take precedence.  Not
								 only that, but for first==last, the EOS page must be treated
								 as if its a normal first page for the stream to open/play. */
								if( lastflag && ! firstflag ) {
									this.os.ogg_stream_packetout( null );
								} else
									if( lastblock != 0 ) {
										accblock += (lastblock + thisblock) >> 2;
									}
							}

							if( op.granulepos != -1 ) {
								final int link2 = this.current_link << 1;
								long granulepos = op.granulepos - this.pcmlengths[link2];
								if( granulepos < 0 ) {
									granulepos = 0;
								}

								for( int i = 1; i < link2; i += 2 ) {
									granulepos += this.pcmlengths[i];
								}
								this.pcm_offset = granulepos - accblock;
								if( this.pcm_offset < 0 ) {
									this.pcm_offset = 0;
								}
								break;
							}
							lastblock = thisblock;
							continue;
						} else {
							this.os.ogg_stream_packetout( null );
						}
					}
				}

				if( lastblock == 0 ) {
					pagepos = _get_next_page( og, -1 );
					if( pagepos < 0 ) {
						this.pcm_offset = ov_pcm_total( -1 );
						break;
					}
				} else {
					/* huh?  Bogus stream with packets but no granulepos */
					this.pcm_offset = -1;
					break;
				}

				/* has our decoding just traversed a bitstream boundary? */
				if( this.ready_state >= STREAMSET ) {
					if( this.current_serialno != og.ogg_page_serialno() ) {

						/* two possibilities:
						 1) our decoding just traversed a bitstream boundary
						 2) another stream is multiplexed into this logical section? */

						if( og.ogg_page_bos() ) {
							/* we traversed */
							_decode_clear(); /* clear out stream state */
							work_os.ogg_stream_clear();
						} /* else, do nothing; next loop will scoop another page */
					}
				}

				if( this.ready_state < STREAMSET ) {
					final int serialno = og.ogg_page_serialno();
					int link = 0;
					final int count = this.links;
					final int[] serial_nos = this.serialnos;// java
					for( ; link < count; link++ ) {
						if( serial_nos[link] == serialno ) {
							break;
						}
					}

					if( link == count ) {
						continue; /* not the desired Vorbis
														bitstream section; keep
														trying */
					}
					this.current_link = link;
					this.current_serialno = serialno;
					this.os.ogg_stream_reset_serialno( serialno );
					work_os.ogg_stream_reset_serialno( serialno );
					this.ready_state = STREAMSET;
					firstflag = (pagepos <= this.dataoffsets[link]);
				}

				this.os.ogg_stream_pagein( og );
				work_os.ogg_stream_pagein( og );
				lastflag = og.ogg_page_eos();

			}
		}

		//work_os.ogg_stream_clear();
		this.bittrack = 0.f;
		this.samptrack = 0.f;
		return (0);
/*
seek_error:
		// dump the machine so we're in a known state
		pcm_offset = -1;
		work_os.ogg_stream_clear();
		_decode_clear( vf );
		return Jcodec.OV_EBADLINK;*/
	}

	/** Page granularity seek (faster than sample granularity because we
	   don't do the last bit of decode to find a specific sample).<p>

	   Seek to the last [granule marked] page preceding the specified pos
	   location, such that decoding past the returned point will quickly
	   arrive at the requested position. */
	public final int ov_pcm_seek_page(final long pos) {
		if( this.ready_state < OPENED ) {
			return (Jcodec.OV_EINVAL);
		}
		if( ! this.seekable ) {
			return (Jcodec.OV_ENOSEEK);
		}

		long total = ov_pcm_total( -1 );
		if( pos < 0 || pos > total ) {
			return (Jcodec.OV_EINVAL);
		}

		long result = 0;
		/* which bitstream section does this pcm offset occur in? */
		int link;
		for( link = this.links - 1; link >= 0; link-- ) {
			total -= this.pcmlengths[(link << 1) + 1];
			if( pos >= total ) {
				break;
			}
		}

		/* Search within the logical bitstream for the page with the highest
		 pcm_pos preceding pos.  If we're looking for a position on the
		 first page, bisection will halt without finding our position as
		 it's before the first explicit granulepos fencepost. That case is
		 handled separately below.

		 There is a danger here; missing pages or incorrect frame number
		 information in the bitstream could make our task impossible.
		 Account for that (it would be an error condition) */

		/* new search algorithm originally by HB (Nicholas Vinen) */

		{
			long finish = this.offsets[link + 1];
			long begin = this.dataoffsets[link];
			long begintime = this.pcmlengths[link << 1];
			long endtime = this.pcmlengths[(link << 1) + 1] + begintime;
			final long target = pos - total + begintime;
			long best = -1;
			boolean got_page = false;

			final Jogg_page og = new Jogg_page();

			/* if we have only one page, there will be no bisection.  Grab the page here */
			if( begin == end ) {
				final int res = _seek_helper( begin );
				if( res != 0 ) {// goto seek_error;
					this.pcm_offset = -1;
					_decode_clear();
					return res;
				}

				result = _get_next_page( og, 1 );
				if( result < 0 ) {// goto seek_error;
					this.pcm_offset = -1;
					_decode_clear();
					return (int)result;
				}

				got_page = true;
			}

		    /* bisection loop */
			while( begin < finish ) {
				long bisect;

				if( finish - begin < CHUNKSIZE ) {
					bisect = begin;
				} else {
					/* take a (pretty decent) guess. */
					bisect = begin +
						(long)((double)(target - begintime) * (finish - begin) / (endtime - begintime))
						- CHUNKSIZE;
					if( bisect < begin + CHUNKSIZE ) {
						bisect = begin;
					}
				}

				int res = _seek_helper( bisect );
				if( res != 0 ) {// goto seek_error;
					this.pcm_offset = -1;
					_decode_clear();
					return res;
				}

				/* read loop within the bisection loop */
				while( begin < finish ) {
					result = _get_next_page( og, finish - this.offset );
					if( result == (long)Jcodec.OV_EREAD ) {// goto seek_error;
						this.pcm_offset = -1;
						_decode_clear();
						return (int)result;
					}
					if( result < 0 ) {
						/* there is no next page! */
						if( bisect <= begin + 1 ) {
							/* No bisection left to perform.  We've either found the
	 						  best candidate already or failed. Exit loop. */
							finish = begin;
						} else {
							/* We tried to load a fraction of the last page; back up a
							   bit and try to get the whole last page */
							if( bisect == 0 ) {// goto seek_error;
								this.pcm_offset = -1;
								_decode_clear();
								return (int)result;
							}
							bisect -= CHUNKSIZE;

							/* don't repeat/loop on a read we've already performed */
							if( bisect <= begin ) {
								bisect = begin + 1;
							}

							/* seek and cntinue bisection */
							res = _seek_helper( bisect );
							if( res != 0 ) {// goto seek_error;
								this.pcm_offset = -1;
								_decode_clear();
								return res;
							}
						}
					} else {
						got_page = true;

						/* got a page. analyze it */
						/* only consider pages from primary vorbis stream */
						if( og.ogg_page_serialno() != this.serialnos[link] ) {
							continue;
						}

						/* only consider pages with the granulepos set */
						final long granulepos = og.ogg_page_granulepos();
						if( granulepos == -1 ) {
							continue;
						}

						if( granulepos < target ) {
							/* this page is a successful candidate! Set state */

							best = result;  /* raw offset of packet with granulepos */
							begin = this.offset; /* raw offset of next page */
							begintime = granulepos;

							/* if we're before our target but within a short distance,
							   don't bisect; read forward */
							if( target - begintime > 44100 ) {
								break;
							}
							bisect = begin; /* *not* begin + 1 as above */
						} else {

							/* This is one of our pages, but the granpos is
							   post-target; it is not a bisection return
							   candidate. (The only way we'd use it is if it's the
							   first page in the stream; we handle that case later
							   outside the bisection) */
							if( bisect <= begin + 1 ) {
								/* No bisection left to perform.  We've either found the
								 best candidate already or failed. Exit loop. */
								finish = begin;
							} else {
								if( finish == this.offset ) {
									/* bisection read to the end; use the known page
									   boundary (result) to update bisection, back up a
									   little bit, and try again */
									finish = result;
									bisect -= CHUNKSIZE;
									if( bisect <= begin ) {
										bisect = begin + 1;
									}
									res = _seek_helper( bisect );
									if( res != 0 ) {// goto seek_error;
										this.pcm_offset = -1;
										_decode_clear();
										return res;
									}
								} else {
									/* Normal bisection */
									finish = bisect;
									endtime = granulepos;
									break;
								}
							}
						}
					}
				}
			}

			/* Out of bisection: did it 'fail?' */
			if( best == -1 ) {

				/* Check the 'looking for data in first page' special case;
				 bisection would 'fail' because our search target was before the
				 first PCM granule position fencepost. */

				if( got_page &&
					begin == this.dataoffsets[link] &&
					og.ogg_page_serialno() == this.serialnos[link] ) {

					/* Yes, this is the beginning-of-stream case. We already have
					  our page, right at the beginning of PCM data.  Set state
					  and return. */

					this.pcm_offset = total;

					if( link != this.current_link ) {
						/* Different link; dump entire decode machine */
						_decode_clear();

						this.current_link = link;
						this.current_serialno = this.serialnos[link];
						this.ready_state = STREAMSET;

					} else {
						this.vd.vorbis_synthesis_restart();
					}

					this.os.ogg_stream_reset_serialno( this.current_serialno );
					this.os.ogg_stream_pagein( og );

				} else {
					// goto seek_error;
					this.pcm_offset = -1;
					_decode_clear();
					return (int)result;
				}

		    } else {

		    	/* Bisection found our page. seek to it, update pcm offset. Easier case than
		    	 raw_seek, don't keep packets preceding granulepos. */

				og.clear();
				final Jogg_packet op = new Jogg_packet();

				/* seek */
				final int res = _seek_helper( best );
				this.pcm_offset = -1;
				if( res != 0 ) {// goto seek_error;
					this.pcm_offset = -1;
					_decode_clear();
					return res;
				}
				result = _get_next_page( og, -1 );
				if( result < 0 ) {// goto seek_error;
					this.pcm_offset = -1;
					_decode_clear();
					return (int)result;
				}

				if( link != this.current_link ) {
					/* Different link; dump entire decode machine */
					_decode_clear();

					this.current_link = link;
					this.current_serialno = this.serialnos[link];
					this.ready_state = STREAMSET;

				} else {
					this.vd.vorbis_synthesis_restart();
				}

				this.os.ogg_stream_reset_serialno( this.current_serialno );
				this.os.ogg_stream_pagein( og );

				/* pull out all but last packet; the one with granulepos */
				while( true ) {
					result = (long)this.os.ogg_stream_packetpeek( op );
					if( result == 0 ) {
						/* No packet returned; we exited the bisection with 'best'
						 pointing to a page with a granule position, so the packet
						 finishing this page ('best') originated on a preceding
						 page. Keep fetching previous pages until we get one with
						 a granulepos or without the 'continued' flag set.  Then
						 just use raw_seek for simplicity. */
						/* Do not rewind past the beginning of link data; if we do,
						 it's either a bug or a broken stream */
						result = best;
						while( result > this.dataoffsets[link] ) {
							result = _get_prev_page( result, og );
							if( result < 0 ) {// goto seek_error;
								this.pcm_offset = -1;
								_decode_clear();
								return (int)result;
							}
							if( og.ogg_page_serialno() == this.current_serialno &&
									(og.ogg_page_granulepos() > -1 ||
									! og.ogg_page_continued() ) ) {
								return ov_raw_seek( result );
							}
						}
					}
					if( result < 0 ) {
						// goto seek_error;
						this.pcm_offset = -1;
						_decode_clear();
						return Jcodec.OV_EBADPACKET;
					}
					if( op.granulepos != -1 ) {
						this.pcm_offset = op.granulepos - this.pcmlengths[this.current_link << 1];
						if( this.pcm_offset < 0 ) {
							this.pcm_offset = 0;
						}
						this.pcm_offset += total;
						break;
					} else {
						/*res = */this.os.ogg_stream_packetout( null );
					}
				}
			}
		}

		/* verify result */
		if( pcm_offset > pos || pos > ov_pcm_total( -1 ) ) {
			// goto seek_error;
			pcm_offset = -1;
			_decode_clear();
			return Jcodec.OV_EFAULT;
		}
		bittrack = 0.f;
		samptrack = 0.f;
		return (0);

//seek_error:
		/* dump machine so we're in a known state */
		/*pcm_offset = -1;
		_decode_clear( vf );
		return (int)result;*/
	}

	/** seek to a sample offset relative to the decompressed pcm stream
	   returns zero on success, nonzero on failure */
	public final int ov_pcm_seek(final long pos) {
		int thisblock, lastblock = 0;
		int ret = ov_pcm_seek_page( pos );
		if( ret < 0 ) {
			return (ret);
		}
		if( (ret = _make_decode_ready()) != 0 ) {
			return ret;
		}

		/* discard leading packets we don't need for the lapping of the
		 position we want; don't decode them */
		final Jogg_packet op = new Jogg_packet();
		final Jogg_page og = new Jogg_page();
		final Jvorbis_dsp_state d = this.vd;// java
		while( true ) {
			ret = this.os.ogg_stream_packetpeek( op );
			if( ret > 0 ) {
				thisblock = this.vi[this.current_link].vorbis_packet_blocksize( op );
				if( thisblock < 0 ) {
					this.os.ogg_stream_packetout( null );
					continue; /* non audio packet */
				}
				if( lastblock != 0 ) {
					this.pcm_offset += (lastblock + thisblock) >> 2;
				}

				if( this.pcm_offset + ((thisblock +
						this.vi[0].vorbis_info_blocksize( 1 )) >> 2) >= pos ) {
					break;
				}

				/* remove the packet from packet queue and track its granulepos */
				this.os.ogg_stream_packetout( null );
				this.vb.vorbis_synthesis_trackonly( op );  /* set up a vb with
														only tracking, no
														pcm_decode */
				d.vorbis_synthesis_blockin( this.vb );

				/* end of logical stream case is hard, especially with exact
				 length positioning. */

				if( op.granulepos > -1 ) {
					/* always believe the stream markers */
					final int link2 = this.current_link << 1;// java
					this.pcm_offset = op.granulepos - this.pcmlengths[link2];
					if( this.pcm_offset < 0 ) {
						this.pcm_offset = 0;
					}
					for( int i = 1; i < link2; i += 2 ) {
						this.pcm_offset += this.pcmlengths[i];
					}
				}

				lastblock = thisblock;

			} else {
				if( ret < 0 && ret != Jcodec.OV_HOLE ) {
					break;
				}

				/* suck in a new page */
				if( _get_next_page( og, -1 ) < 0 ) {
					break;
				}
				if( og.ogg_page_bos() ) {
					_decode_clear();
				}

				if( this.ready_state < STREAMSET ) {
					final int serialno = og.ogg_page_serialno();
					int link = 0;
					final int count = this.links;
					final int[] serial_nos = this.serialnos;// java
					for( ; link < count; link++ ) {
						if( serial_nos[link] == serialno ) {
							break;
						}
					}
					if( link == count ) {
						continue;
					}
					this.current_link = link;

					this.ready_state = STREAMSET;
					this.current_serialno = og.ogg_page_serialno();
					this.os.ogg_stream_reset_serialno( serialno );
					ret = _make_decode_ready();
					if( ret != 0 ) {
						return ret;
					}
					lastblock = 0;
				}

				this.os.ogg_stream_pagein( og );
			}
		}

		this.bittrack = 0.f;
		this.samptrack = 0.f;
		/* discard samples until we reach the desired position. Crossing a
		 logical bitstream boundary with abandon is OK. */
		{
			/* note that halfrate could be set differently in each link, but
			   vorbisfile encoforces all links are set or unset */
			final int hs = this.vi[0].vorbis_synthesis_halfrate_p();
			while( this.pcm_offset < ((pos >> hs) << hs) ) {
				final int target = (int)((pos - this.pcm_offset) >> hs);// FIXME ogg_int64_t to int
				int samples = d.vorbis_synthesis_pcmout( false ).samples;

				if( samples > target ) {
					samples = target;
				}
				d.vorbis_synthesis_read( samples );
				this.pcm_offset += samples << hs;

				if( samples < target ) {
					if( _fetch_and_process_packet( null, 1, 1 ) <= 0 ) {
						this.pcm_offset = ov_pcm_total( -1 ); /* eof */
					}
				}
			}
		}
		return 0;
	}

	/** seek to a playback time relative to the decompressed pcm stream
	   returns zero on success, nonzero on failure */
	public final int ov_time_seek(final double seconds) {
		/* translate time to PCM position and call ov_pcm_seek */

		// int link = -1;// FIXME why?
		if( this.ready_state < OPENED ) {
			return (Jcodec.OV_EINVAL);
		}
		if( ! this.seekable ) {
			return (Jcodec.OV_ENOSEEK);
		}
		if( seconds < 0 ) {
			return (Jcodec.OV_EINVAL);
		}

		/* which bitstream section does this time offset occur in? */
		final long[] lengths = this.pcmlengths;// java
		long pcm_total = 0;
		double time_total = 0.;
		int link = 0;
		final int count = this.links;
		for( ; link < count; link++ ) {
			final double addsec = ov_time_total( link );
			if( seconds < time_total + addsec ) {
				break;
			}
			time_total += addsec;
			pcm_total += lengths[(link << 1) + 1];
		}

		if( link == count ) {
			return (Jcodec.OV_EINVAL);
		}

		/* enough information to convert time offset to pcm offset */
		{
			final long target = (long)((double)pcm_total + (seconds - time_total) * (double)this.vi[link].rate);
			return (ov_pcm_seek( target ));
		}
	}

	/** page-granularity version of ov_time_seek
	   returns zero on success, nonzero on failure */
	public final int ov_time_seek_page(final double seconds) {
		/* translate time to PCM position and call ov_pcm_seek */

		// int link = -1;// FIXME why?

		if( this.ready_state < OPENED ) {
			return (Jcodec.OV_EINVAL);
		}
		if( ! this.seekable ) {
			return (Jcodec.OV_ENOSEEK);
		}
		if( seconds < 0 ) {
			return (Jcodec.OV_EINVAL);
		}

		/* which bitstream section does this time offset occur in? */
		long pcm_total = 0;
		double time_total = 0.;
		final long[] lengths = this.pcmlengths;// java
		int link = 0;
		final int count = this.links;
		for( ; link < count; link++ ) {
			final double addsec = ov_time_total( link );
			if( seconds < time_total + addsec ) {
				break;
			}
			time_total += addsec;
			pcm_total += lengths[(link << 1) + 1];
		}

		if( link == count ) {
			return (Jcodec.OV_EINVAL);
		}

		/* enough information to convert time offset to pcm offset */
		{
			final long target = (long)((double)pcm_total + (seconds - time_total) * (double)this.vi[link].rate);
			return (ov_pcm_seek_page( target ));
		}
	}

	/** tell the current stream offset cursor.  Note that seek followed by
	   tell will likely not give the set offset due to caching */
	public final long ov_raw_tell() {
		if( this.ready_state < OPENED ) {
			return (long)Jcodec.OV_EINVAL;
		}
		return (this.offset);
	}

	/** return PCM offset (sample) of next PCM sample to be read */
	public final long ov_pcm_tell() {
		if( this.ready_state < OPENED ) {
			return (long)Jcodec.OV_EINVAL;
		}
		return (this.pcm_offset);
	}

	/** return time offset (seconds) of next PCM sample to be read */
	public final double ov_time_tell() {
		int link = 0;
		long pcm_total = 0;
		double time_total = 0.f;

		if( this.ready_state < OPENED ) {
			return (Jcodec.OV_EINVAL);
		}
		if( this.seekable ) {
			pcm_total = ov_pcm_total( -1 );
			time_total = ov_time_total( -1 );

			/* which bitstream section does this time offset occur in? */
			final long[] lengths = this.pcmlengths;// java
			for( link = this.links - 1; link >= 0; link-- ) {
				pcm_total -= lengths[(link << 1) + 1];
				time_total -= ov_time_total( link );
				if( this.pcm_offset >= pcm_total ) {
					break;
				}
			}
		}

		return ((double)time_total + (double)(this.pcm_offset - pcm_total) / this.vi[link].rate);
	}

	/** @param link -1) return the vorbis_info struct for the bitstream section
					currently being decoded<br>
			   0-n) to request information for a specific bitstream section

		In the case of a non-seekable bitstream, any call returns the
		current bitstream.<br>
		NULL in the case that the machine is not initialized */
	public final Jvorbis_info ov_info(final int link) {
		if( this.seekable ) {
			if( link < 0 ) {
				if( this.ready_state >= STREAMSET ) {
					return this.vi[this.current_link];
				} else {
					return this.vi[0];
				}
			} else
				if( link >= this.links ) {
					return null;
				} else {
					return this.vi[link];
				}
		} else {
			return this.vi[0];
		}
	}

	/** grr, strong typing, grr, no templates/inheritence, grr */
	public final Jvorbis_comment ov_comment(final int link) {
		if( this.seekable ) {
			if( link < 0 ) {
				if( this.ready_state >= STREAMSET ) {
					return this.vc[this.current_link];
				} else {
					return this.vc[0];
				}
			} else
				if( link >= this.links ) {
					return null;
				} else {
					return this.vc[link];
				}
		} else {
			return this.vc[0];
		}
	}

	//private static boolean host_is_big_endian() {
		//ogg_int32_t pattern = 0xfeedface; /* deadbeef */
		//unsigned char *bytewise = (unsigned char *)&pattern;
		//if ( bytewise[0] == 0xfe ) return true;
		//return false;
	//}

	/** up to this point, everything could more or less hide the multiple
	   logical bitstream nature of chaining from the toplevel application
	   if the toplevel application didn't particularly care.  However, at
	   the point that we actually read audio back, the multiple-section
	   nature must surface: Multiple bitstream sections do not necessarily
	   have to have the same number of channels or sampling rate.<p>

	   ov_read returns the sequential logical bitstream number currently
	   being decoded along with the PCM data in order that the toplevel
	   application can take action on channel/sample rate changes.  This
	   number will be incremented even for streamed (non-seekable) streams
	   (for seekable streams, it represents the actual logical bitstream
	   index within the physical bitstream.  Note that the accessor
	   functions above are aware of this dichotomy).<p>

	   ov_read_filter is exactly the same as ov_read except that it processes
	   the decoded audio data through a filter before packing it into the
	   requested format. This gives greater accuracy than applying a filter
	   after the audio has been converted into integral PCM.<p>

	 * @param buffer a buffer to hold packed PCM data for return
	 * @param off the start offset in array <code>buffer</code>
	 * 			at which the data is written.
	 * @param length the byte length requested to be placed into buffer
	 * @param bigendianp should the data be packed LSB first (0) or MSB first (1)
	 * @param word word size for output. currently 1 (byte) or 2 (16 bit short)
	 * @param signed indicates whether the data is signed or unsigned
	 *
	 * @return <code>Jvorbis_pcm</code><br>
	 * NULL) stream not opened<br>
	 * <code>Jvorbis_pcm.samples</code>:<br>
	 * <0) error/hole in data (OV_HOLE), partial open (OV_EINVAL)<br>
	 * 0) EOF<br>
	 * n) number of bytes of PCM actually returned.
	 * The below works on a packet-by-packet basis, so the
	 * return length is not related to the 'length' passed in, just guaranteed to fit.
	 * <p>
	 * <code>Jvorbis_pcm.data</code>: set to the logical bitstream number
	 */
	@SuppressWarnings("null")// if samples != 0 , than pcm_data not null
	public final Jvorbis_pcm ov_read_filter(final byte[] buffer, final int off, final int length,
					final boolean bigendianp, final int word, final boolean signed,
					// int[] bitstream moved to Jvorbis_pcm.pcmret
					final Iov_read_filter filter, final Object filter_param) {
		// boolean host_endian = host_is_big_endian();
		Jvorbis_pcm pcm_data = null;

		if( this.ready_state < OPENED ) {
			return null;//(Jcodec.OV_EINVAL);
		}

		int samples;
		while( true ) {
			if( this.ready_state == INITSET ) {
				pcm_data = this.vd.vorbis_synthesis_pcmout( true );
				samples = pcm_data.samples;
				if( samples != 0 ) {
					break;
				}
			}

			/* suck in another packet */
			{
				final int ret = _fetch_and_process_packet( null, 1, 1 );
				if( ret == Jcodec.OV_EOF ) {
					//return (0);
					if( pcm_data == null ) {
						pcm_data = new Jvorbis_pcm();
					}
					pcm_data.samples = 0;
					return pcm_data;
				}
				if( ret <= 0 ) {
					//return (ret);
					if( pcm_data == null ) {
						pcm_data = new Jvorbis_pcm();
					}
					pcm_data.samples = ret;
					return pcm_data;
				}
			}

		}

		if( samples > 0 ) {

			/* yay! proceed to pack data into the byte buffer */

			final int channels = ov_info( -1 ).channels;
			final int bytespersample = word * channels;
			if( samples > length / bytespersample ) {
				samples = length / bytespersample;
			}

			if( samples <= 0 ) {
				//return Jcodec.OV_EINVAL;
				if( pcm_data == null ) {
					pcm_data = new Jvorbis_pcm();
				}
				pcm_data.samples = Jcodec.OV_EINVAL;
				return pcm_data;
			}

			/* Here. */
			if( filter != null ) {
				filter.filter( pcm_data.pcm, channels, samples, filter_param );
			}

			/* a tight loop to pack each size */
			{
				final float[][] pcm = pcm_data.pcm;
				final int pcmoffset = pcm_data.pcmret;
				final int finish = samples + pcmoffset;
				if( word == 1 ) {
					final int shift = (signed ? 0 : 128 );
					for( int i = 0; i < channels; i++ ) {
						final float[] src = pcm[i];
						int dest = off + i;
						for( int j = pcmoffset; j < finish; j++ ) {
							int val = (int)Math.floor( (double)(src[j] * 128.f + 0.5f) );
							if( val > 127 ) {
								val = 127;
							} else if( val < -128 ) {
								val = -128;
							}
							buffer[dest] = (byte)(val + shift);
							dest += channels;
						}
					}
				} else {// word == 2
					final int shift = (signed ? 0 : 32768);
					final int channels2 = (channels << 1) - 1;// 2 bytes per channel

					if( bigendianp ) {
						if( signed ) {// big endian and signed
							for( int i = 0; i < channels; i++ ) { /* It's faster in this order */
								final float[] src = pcm[i];
								int dest = off + (i << 1);
								for( int j = pcmoffset; j < finish; j++ ) {
									int val = (int)Math.floor( (double)(src[j] * 32768.f + 0.5f) );
									if( val > 32767 ) {
										val = 32767;
									} else if( val < -32768 ) {
										val = -32768;
									}
									buffer[dest++] = (byte)(val >> 8);
									buffer[dest]   = (byte)(val);
									dest += channels2;
								}
							}
						} else {// big endian and unsigned
							for( int i = 0; i < channels; i++ ) {
								final float[] src = pcm[i];
								int dest = off + (i << 1);
								for( int j = pcmoffset; j < finish; j++ ) {
									int val = (int)Math.floor( (double)(src[j] * 32768.f + 0.5f) );
									if( val > 32767 ) {
										val = 32767;
									} else if( val < -32768 ) {
										val = -32768;
									}
									val += shift;
									buffer[dest++] = (byte)(val >> 8);
									buffer[dest]   = (byte)(val);
									dest += channels2;
								}
							}
						}
					} else {
						if( signed ) {// little endian and signed
							for( int i = 0; i < channels; i++ ) { /* It's faster in this order */
								final float[] src = pcm[i];
								int dest = off + (i << 1);
								for( int j = pcmoffset; j < finish; j++ ) {
									int val = (int)Math.floor( (double)(src[j] * 32768.f + 0.5f) );
									if( val > 32767 ) {
										val = 32767;
									} else if( val < -32768 ) {
										val = -32768;
									}
									buffer[dest++] = (byte)(val);
									buffer[dest]   = (byte)(val >> 8);
									dest += channels2;
								}
							}
						} else {// little endian and unsigned
							for( int i = 0; i < channels; i++ ) {
								final float[] src = pcm[i];
								int dest = off + (i << 1);
								for( int j = pcmoffset; j < finish; j++ ) {
									int val = (int)Math.floor( (double)(src[j] * 32768.f + 0.5f) );
									if( val > 32767 ) {
										val = 32767;
									} else if( val < -32768 ) {
										val = -32768;
									}
									val += shift;
									buffer[dest++] = (byte)(val);
									buffer[dest]   = (byte)(val >> 8);
									dest += channels2;
								}
							}
						}
					}
				}
			}

			this.vd.vorbis_synthesis_read( samples );
			final int hs = this.vi[0].vorbis_synthesis_halfrate_p();
			this.pcm_offset += (samples << hs);
			// changed if( bitstream != null ) bitstream[0] = current_link;
			pcm_data.data = this.current_link;
			//return (samples * bytespersample);
			pcm_data.samples = samples * bytespersample;
		} else {
			//return (samples);
			if( pcm_data == null ) {
				pcm_data = new Jvorbis_pcm();
			}
			pcm_data.samples = samples;
		}
		return pcm_data;
	}

	/**
	 * @param buffer a buffer to hold packed PCM data for return
	 * @param off the start offset in array <code>buffer</code>
	 * 			at which the data is written.
	 * @param length the byte length requested to be placed into buffer
	 * @param bigendianp should the data be packed LSB first (0) or MSB first (1)
	 * @param word word size for output. currently 1 (byte) or 2 (16 bit short)
	 * @param signed indicates whether the data is signed or unsigned
	 *
	 * @return <code>Jvorbis_pcm</code><br>
	 * NULL) stream not opened<br>
	 * <code>Jvorbis_pcm.samples</code>:<br>
	 * <0) error/hole in data (OV_HOLE), partial open (OV_EINVAL)<br>
	 * 0) EOF<br>
	 * n) number of bytes of PCM actually returned.
	 * The below works on a packet-by-packet basis, so the
	 * return length is not related to the 'length' passed in, just guaranteed to fit.
	 * <p>
	 * <code>Jvorbis_pcm.data</code>: set to the logical bitstream number
	 *
	 * @see #ov_read_filter(byte[], int, int, boolean, int, boolean, Iov_read_filter, Object)
	 */
	public final Jvorbis_pcm ov_read(final byte[] buffer, final int off, final int length,
					// int[] bitstream moved to Jvorbis_pcm.pcmret
					final boolean bigendianp, final int word, final boolean signed) {
		return ov_read_filter( buffer, off, length, bigendianp, word, signed, null, null );
	}

	/**
		@param pcm_channels a float vector per channel of output
		@param length the sample length being read by the app

		@return Jvorbis_pcm<br>
			Jvorbis_pcm.samples:<br>
				<0) error/hole in data (OV_HOLE), partial open (OV_EINVAL)<br>
				0) EOF<br>
				n) number of samples of PCM actually returned.
				The below works on a packet-by-packet basis,
				so the return length is not related to the 'length' passed in,
				just guaranteed to fit.<p>

			Jvorbis_pcm.pcm: pcm_channels<br>
			Jvorbis_pcm.data: set to the logical bitstream number<br>
			NULL: Jcodec.OV_EINVAL
	*/
	public final Jvorbis_pcm ov_read_float(final int length) {
					// int[] bitstream moved to Jvorbis_pcm.data) {

		if( this.ready_state < OPENED ) {
			return null;//(Jcodec.OV_EINVAL);
		}

		while( true ) {
			if( this.ready_state == INITSET ) {
				final Jvorbis_pcm pcm_data = this.vd.vorbis_synthesis_pcmout( true );
				int samples = pcm_data.samples;
				if( samples != 0 ) {
					final int hs = this.vi[0].vorbis_synthesis_halfrate_p();
					//if( pcm_channels ) *pcm_channels = pcm;
					if( samples > length ) {
						samples = length;
					}
					this.vd.vorbis_synthesis_read( samples );
					this.pcm_offset += (long)(samples << hs);
					//if( bitstream ) *bitstream = current_link;
					//return samples;
					pcm_data.data = this.current_link;
					return pcm_data;

				}
			}

			/* suck in another packet */
			{
				final int ret = _fetch_and_process_packet( null, 1, 1 );
				if( ret == Jcodec.OV_EOF ) {
					//return (0);
					final Jvorbis_pcm pcm_ret = new Jvorbis_pcm();
					pcm_ret.samples = 0;
					return pcm_ret;
				}
				if( ret <= 0 ) {
					//return (ret);
					final Jvorbis_pcm pcm_ret = new Jvorbis_pcm();
					pcm_ret.samples = ret;
					return pcm_ret;
				}
			}

		}
	}

	private static void _ov_splice(final float[][] pcm, final float[][] lappcm,
					final int n1, final int n2,
					final int ch1, final int ch2,
					final float[] w1, final float[] w2) {
		float[] w = w1;
		int n = n1;

		if( n1 > n2 ) {
			n = n2;
			w = w2;
		}

		/* splice */
		int j = 0;
		for( ; j < ch1 && j < ch2; j++ ) {
			final float[] s = lappcm[j];
			final float[] d = pcm[j];

			for( int i = 0; i < n; i++ ) {
				float wd = w[i];
				wd *= wd;
				final float ws = 1.f - wd;
				d[i] = d[i] * wd + s[i] * ws;
			}
		}
		/* window from zero */
		for( ; j < ch2; j++ ) {
			final float[] d = pcm[j];
			for( int i = 0; i < n; i++ ) {
				float wd = w[i];
				wd *= wd;
				d[i] *= wd;
			}
		}

	}

	/** make sure vf is INITSET */
	private final int _ov_initset() {
		while( true ) {
			if( this.ready_state == INITSET ) {
				break;
			}
			/* suck in another packet */
			{
				final int ret = _fetch_and_process_packet( null, 1, 0 );
				if( ret < 0 && ret != Jcodec.OV_HOLE ) {
					return (ret);
				}
			}
		}
		return 0;
	}

	/** make sure vf is INITSET and that we have a primed buffer; if
	   we're crosslapping at a stream section boundary, this also makes
	   sure we're sanity checking against the right stream information */
	private final int _ov_initprime() {
		while( true ) {
			if( this.ready_state == INITSET ) {
				if( this.vd.vorbis_synthesis_pcmout( false ).samples != 0 ) {
					break;
				}
			}

			/* suck in another packet */
			{
				final int ret = _fetch_and_process_packet( null, 1, 0 );
				if( ret < 0 && ret != Jcodec.OV_HOLE ) {
					return (ret);
				}
			}
		}
		return 0;
	}

	/** grab enough data for lapping from vf; this may be in the form of
	   unreturned, already-decoded pcm, remaining PCM we will need to
	   decode, or synthetic postextrapolation from last packets. */
	private final void _ov_getlap(final Jvorbis_info vinfo, final Jvorbis_dsp_state vdsp,
					final float[][] lappcm, final int lapsize) {
		final int channels = vinfo.channels;// java
		int lapcount = 0;
		/* try first to decode the lapping data */
		while( lapcount < lapsize ) {
			final Jvorbis_pcm pcm_data = vdsp.vorbis_synthesis_pcmout( true );
			int samples = pcm_data.samples;
			if( samples != 0 ) {
				final int v = lapsize - lapcount;// java
				if( samples > v ) {
					samples = v;
				}
				final float[][] pcm = pcm_data.pcm;// java
				for( int i = 0; i < channels; i++ ) {
					System.arraycopy( pcm[i], 0, lappcm[i], lapcount, samples );
				}
				lapcount += samples;
				vdsp.vorbis_synthesis_read( samples );
			} else {
				/* suck in another packet */
				final int ret = _fetch_and_process_packet( null, 1, 0 ); /* do *not* span */
				if( ret == Jcodec.OV_EOF ) {
					break;
				}
			}
		}
		if( lapcount < lapsize ) {
			/* failed to get lapping data from normal decode; pry it from the
			   postextrapolation buffering, or the second half of the MDCT
			   from the last packet */
			final Jvorbis_pcm pcm_data = this.vd.vorbis_synthesis_lapout( true );
			int samples = pcm_data.samples;
			if( samples == 0 ) {
				for( int i = 0; i < channels; i++ ) {
					Arrays.fill( lappcm[i], lapcount, lapsize, 0.0f );
				}
				lapcount = lapsize;
			} else {
				if( samples > lapsize - lapcount ) {
					samples = lapsize - lapcount;
				}
				final float[][] pcm = pcm_data.pcm;// java
				for( int i = 0; i < channels; i++ ) {
					System.arraycopy( pcm[i], 0, lappcm[i], lapcount, samples );
				}
				lapcount += samples;
			}
		}
	}

	/** this sets up crosslapping of a sample by using trailing data from
	   sample 1 and lapping it into the windowing buffer of sample 2 */
	public static final int ov_crosslap(final JOggVorbis_File vf1, final JOggVorbis_File vf2) {
		if( vf1 == vf2 ) {
			return (0); /* degenerate case */
		}
		if( vf1.ready_state < OPENED ) {
			return (Jcodec.OV_EINVAL);
		}
		if( vf2.ready_state < OPENED ) {
			return (Jcodec.OV_EINVAL);
		}

		/* the relevant overlap buffers must be pre-checked and pre-primed
		 before looking at settings in the event that priming would cross
		 a bitstream boundary.  So, do it now */

		int ret = vf1._ov_initset();
		if( ret != 0 ) {
			return (ret);
		}
		ret = vf2._ov_initprime();
		if( ret != 0 ) {
			return (ret);
		}

		final Jvorbis_info vi1 = vf1.ov_info( -1 );
		final Jvorbis_info vi2 = vf2.ov_info( -1 );
		final int hs1 = vf1.ov_halfrate_p();
		final int hs2 = vf2.ov_halfrate_p();


		final int n1 = vi1.vorbis_info_blocksize( 0 ) >> (1 + hs1);
		final int n2 = vi2.vorbis_info_blocksize( 0 ) >> (1 + hs2);
		final float[] w1 = vf1.vd.vorbis_window( 0 );
		final float[] w2 = vf2.vd.vorbis_window( 0 );

		final float[][] lappcm = new float[vi1.channels][n1];

		vf1._ov_getlap( vi1, vf1.vd, lappcm, n1 );

		/* have a lapping buffer from vf1; now to splice it into the lapping
		 buffer of vf2 */
		/* consolidate and expose the buffer. */
		final Jvorbis_pcm pcm_data = vf2.vd.vorbis_synthesis_lapout( true );

/*#if 0
		Janalysis._analysis_output_always( "pcmL", 0, pcm_data.pcm[0], 0, n1 << 1, false, false, 0 );
		Janalysis._analysis_output_always( "pcmR", 0, pcm_data.pcm[1], 0, n1 << 1, false, false, 0 );
#endif*/

		/* splice */
		_ov_splice( pcm_data.pcm, lappcm, n1, n2, vi1.channels, vi2.channels, w1, w2 );

		/* done */
		return (0);
	}

	private static final int OV_RAW_SEEK = 0;
	private static final int OV_PCM_SEEK = 1;
	private static final int OV_PCM_SEEK_PAGE = 2;

	private final int _ov_64_seek_lap(final long pos, final int localseek) {
		if( this.ready_state < OPENED ) {
			return (Jcodec.OV_EINVAL);
		}
		int ret = _ov_initset();
		if( ret != 0 ) {
			return (ret);
		}
		Jvorbis_info vinfo = ov_info( -1 );
		final int hs = ov_halfrate_p();

		final int ch1 = vinfo.channels;
		final int n1 = vinfo.vorbis_info_blocksize( 0 ) >> (1 + hs);
		final float[] w1 = this.vd.vorbis_window( 0 );  /* window arrays from libvorbis are
										persistent; even if the decode state
										from this link gets dumped, this
										window array continues to exist */

		final float[][] lappcm = new float[ch1][n1];

		_ov_getlap( vinfo, this.vd, lappcm, n1 );

		/* have lapping data; seek and prime the buffer */
		//ret = localseek( vf, pos );
		switch( localseek ) {
		case OV_PCM_SEEK:
			ret = ov_pcm_seek( pos );
			break;
		case OV_PCM_SEEK_PAGE:
			ret = ov_pcm_seek_page( pos );
		default:// OV_RAW_SEEK
			ret = ov_raw_seek( pos );
			break;
		}
		if( ret != 0 ) {
			return ret;
		}
		ret = _ov_initprime();
		if( ret != 0 ) {
			return (ret);
		}

		/* Guard against cross-link changes; they're perfectly legal */
		vinfo = ov_info( -1 );
		final int ch2 = vinfo.channels;
		final int n2 = vinfo.vorbis_info_blocksize( 0 ) >> (1 + hs);
		final float[] w2 = this.vd.vorbis_window( 0 );

		/* consolidate and expose the buffer. */
		final Jvorbis_pcm pcm_data = this.vd.vorbis_synthesis_lapout( true );

		/* splice */
		_ov_splice( pcm_data.pcm, lappcm, n1, n2, ch1, ch2, w1, w2 );

		/* done */
		return (0);
	}

	public final int ov_raw_seek_lap(final long pos) {
		return _ov_64_seek_lap( pos, OV_RAW_SEEK );
	}

	public final int ov_pcm_seek_lap(final long pos) {
		return _ov_64_seek_lap( pos, OV_PCM_SEEK );
	}

	public final int ov_pcm_seek_page_lap(final long pos) {
		return _ov_64_seek_lap( pos, OV_PCM_SEEK_PAGE );
	}

	private static final int OV_TIME_SEEK = 0;
	private static final int OV_TIME_SEEK_PAGE = 1;

	private final int _ov_d_seek_lap(final double pos, final int localseek) {
		if( this.ready_state < OPENED ) {
			return (Jcodec.OV_EINVAL);
		}
		int ret = _ov_initset();
		if( ret != 0 ) {
			return (ret);
		}
		Jvorbis_info vinfo = ov_info( -1 );
		final int hs = ov_halfrate_p();

		final int ch1 = vinfo.channels;
		final int n1 = vinfo.vorbis_info_blocksize( 0 ) >> (1 + hs);
		final float[] w1 = this.vd.vorbis_window( 0 );  /* window arrays from libvorbis are
										persistent; even if the decode state
										from this link gets dumped, this
										window array continues to exist */

		final float[][] lappcm = new float[ch1][n1];

		_ov_getlap( vinfo, this.vd, lappcm, n1 );

		/* have lapping data; seek and prime the buffer */
		//ret = localseek( vf, pos );
		switch( localseek ) {
		case OV_TIME_SEEK_PAGE:
			ret = ov_time_seek_page( pos );
		default:// OV_TIME_SEEK
			ret = ov_time_seek( pos );
			break;
		}
		if( ret != 0 ) {
			return ret;
		}
		ret = _ov_initprime();
		if( ret != 0 ) {
			return (ret);
		}

		/* Guard against cross-link changes; they're perfectly legal */
		vinfo = ov_info( -1 );
		final int ch2 = vinfo.channels;
		final int n2 = vinfo.vorbis_info_blocksize( 0 ) >> (1 + hs);
		final float[] w2 = this.vd.vorbis_window( 0 );

		/* consolidate and expose the buffer. */
		final Jvorbis_pcm pcm_data = this.vd.vorbis_synthesis_lapout( true );

		/* splice */
		_ov_splice( pcm_data.pcm, lappcm, n1, n2, ch1, ch2, w1, w2 );

		/* done */
		return (0);
	}

	public final int ov_time_seek_lap(final double pos) {
		return _ov_d_seek_lap( pos, OV_TIME_SEEK );
	}

	public final int ov_time_seek_page_lap(final double pos) {
		return _ov_d_seek_lap( pos, OV_TIME_SEEK_PAGE );
	}
}
