package libvorbis;

/**
careful with this; it's using static array sizing to make managing
all the modes a little less annoying.  If we use a residue backend
with > 12 partition types, or a different division of iteration,
this needs to be updated.
*/
public final class Jstatic_bookblock {
	final Jstatic_codebook[][] books = new Jstatic_codebook[12][4];
	//
	public Jstatic_bookblock(final Jstatic_codebook[][] st_books) {
		for( int i = 0, length = st_books.length; i < length; i++ ) {
			System.arraycopy( st_books[i], 0, books[i], 0, st_books[i].length );
		}
	}
}
