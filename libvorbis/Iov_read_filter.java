package libvorbis;

/**
 * Interface for JOggVorbis_File.ov_read_filter<p>
 * c-variant:<br>
 * <code>void (*filter)(float **pcm, long channels, long samples, void *filter_param)</code>
 * <p>
 * ov_read_filter is exactly the same as ov_read except that it processes
 * the decoded audio data through a filter before packing it into the
 * requested format. This gives greater accuracy than applying a filter
 * after the audio has been converted into integral PCM.
 */
public interface Iov_read_filter {
	public void filter(float[][] pcm, int channels, int samples, Object filter_param);
}
