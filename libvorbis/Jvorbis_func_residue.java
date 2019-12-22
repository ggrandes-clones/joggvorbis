package libvorbis;

import libogg.Joggpack_buffer;

/** Residue backend generic */
abstract class Jvorbis_func_residue {
	final boolean is_pack_supported;
	final boolean is_class_supported;
	final boolean is_forward_supported;
	//
	Jvorbis_func_residue(final boolean isPack, final boolean isClass, final boolean isForward) {
		is_pack_supported = isPack;
		is_class_supported = isClass;
		is_forward_supported = isForward;
	}
	//
	abstract void pack(Jvorbis_info_residue vr, Joggpack_buffer opb);
	abstract Jvorbis_info_residue unpack(Jvorbis_info vi, Joggpack_buffer opb);
	abstract Jvorbis_look_residue look(Jvorbis_dsp_state vd, Jvorbis_info_residue vr);
	//abstract void free_info(vorbis_info_residue *);// use vorbis_info_residue = null
	abstract void free_look(Jvorbis_look_residue i);// used only to debug output
	abstract int[][] fclass(Jvorbis_block vb, Jvorbis_look_residue vl, int[][] in, boolean[] nonzero, int ch);
	abstract int forward(Joggpack_buffer opb, Jvorbis_block vb, Jvorbis_look_residue vl,
					int[][] in, boolean[] nonzero, int ch, int[][] partword, int submap);
	abstract int inverse(Jvorbis_block vb, Jvorbis_look_residue vl, float[][] in, boolean[] nonzero, int ch);
	// general
	class Jvorbis_look_residue0 extends Jvorbis_look_residue {
		Jvorbis_info_residue0 info;

		int parts  = 0;
		int stages = 0;
		Jcodebook[]   fullbooks = null;
		Jcodebook     phrasebook = null;
		Jcodebook[][] partbooks = null;

		int      partvals  = 0;
		int[][]  decodemap = null;

		int postbits   = 0;
		int phrasebits = 0;
		int frames     = 0;
/*
#if defined(TRAIN_RES) || defined(TRAIN_RESAUX)
		int        train_seq;
		int      *training_data[8][64];
		float      training_max[8][64];
		float      training_min[8][64];
		float     tmin;
		float     tmax;
		int       submap;
#endif
*/
	}

	/** a truncated packet here just means 'stop working'; it's not an error
	 * <pre>
	 * static int _01inverse(Jvorbis_block *vb,vorbis_look_residue *vl,
	 *	float **in,int ch,
	 *	long (*decodepart)(codebook *, float *,
	 *	oggpack_buffer *,int))
	 * </pre>
	 */
	static int _01inverse(final Jvorbis_block vb, final Object vl,
					final float[][] in, final int ch,
					final Idecodepart decodepart ) {

		final Jvorbis_look_residue0 look = (Jvorbis_look_residue0)vl;
		final Jvorbis_info_residue0 info = look.info;

		/* move all this setup out later */
		final int samples_per_partition = info.grouping;
		final int partitions_per_word = look.phrasebook.dim;
		final int max = vb.pcmend >> 1;
		final int end = (info.end < max ? info.end : max);
		final int begin = info.begin;// java
		int n = end - begin;

		if( n > 0 ) {
			int partvals = n / samples_per_partition;
			final int partwords = (partvals + partitions_per_word - 1) / partitions_per_word;
			partvals *= samples_per_partition;// java
			partvals += begin;
			final int[][][] partword = new int[ch][partwords][];

			final int[][] decodemap = look.decodemap;// java
			final Jcodebook[][] partbooks = look.partbooks;// java
			final Jcodebook phrasebook = look.phrasebook;// java
			final int[] secondstages = info.secondstages;// java
			for( int s = 0, stages = look.stages; s < stages; s++ ) {
				final int s1 = (1 << s);// java
				/* each loop decodes on partition codeword containing
					partitions_per_word partitions */
				for( int i = begin, l = 0; i < partvals; l++ ) {
					if( s == 0 ) {
						/* fetch the partition word for each channel */
						for( int j = 0; j < ch; j++ ) {
							final int temp = phrasebook.vorbis_book_decode( vb.opb );

							if( temp == -1 || temp >= info.partvals ) {
								return 0;// goto eopbreak;
							}
							partword[j][l] = decodemap[temp];
							if( partword[j][l] == null ) {
								return 0;// goto errout;
							}
						}
					}

					/* now we decode residual values for the partitions */
					for( int k = 0; k < partitions_per_word && i < partvals; k++, i += samples_per_partition ) {
						for( int j = 0; j < ch; j++ ) {
							n = partword[j][l][k];
							if( (secondstages[n] & s1) != 0 ) {
								final Jcodebook stagebook = partbooks[n][s];
								if( stagebook != null ) {
									if( decodepart.decodepart( stagebook, in[j], i, vb.opb,
											samples_per_partition ) == -1 ) {
										return 0;// goto eopbreak;
									}
								}
							}
						}
					}
				}
			}
		}
//errout:
//eopbreak:
		return (0);
	}
}
