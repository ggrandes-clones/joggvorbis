package spi.convert;

import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioFormat.Encoding;
import javax.sound.sampled.AudioInputStream;

import libvorbis.JOggVorbis_File;
import libvorbis.Jcodec;
import libvorbis.Jvorbis_pcm;

public final class OggVorbisDecodedAudioInputStream extends AudioInputStream {
	private JOggVorbis_File mOVF = new JOggVorbis_File();
	private final boolean mIsBigEndian;
	private final boolean mIsSigned;
	//
	public OggVorbisDecodedAudioInputStream(final InputStream stream,
					final AudioFormat format, final long length) {
		super( stream, format, length );
		mIsBigEndian = format.isBigEndian();
		mIsSigned = format.getEncoding() == Encoding.PCM_SIGNED;
		if( mOVF.ov_open_callbacks( stream, null, 0, JOggVorbis_File.OV_CALLBACKS_STREAMONLY_NOCLOSE ) < 0 ) {
			mOVF.ov_clear();
		}
	}

	@Override
	public void close() throws IOException {
		if( mOVF != null ) {
			mOVF.ov_clear();
		}
		mOVF = null;
		super.close();
	}

	@Override
	public boolean markSupported() {
		return false;
	}

	@Override
	public int read() throws IOException {
		final byte[] data = new byte[1];
		if( read( data ) <= 0 ) {// we have a weird situation if read(byte[]) returns 0!
			return -1;
		}
		return ((int) data[0]) & 0xff;
	}

	/* @Override
	public int read(final byte[] b) throws IOException {
		return read( b, 0, b.length );
	} */

	@Override
	public int read(final byte[] b, final int off, final int len) throws IOException {
		final Jvorbis_pcm pcm_data = mOVF.ov_read( b, off, len, mIsBigEndian, 2, mIsSigned );
		if( pcm_data == null || pcm_data.samples < 0 ) {
			if( pcm_data == null || pcm_data.samples == Jcodec.OV_EBADLINK ) {
				close();
				throw new IOException("Corrupt bitstream section!");
			}
			return 0;
		} else if( pcm_data.samples == 0 ) {// EOF
			return -1;
		}
		return pcm_data.samples;
	}
}
