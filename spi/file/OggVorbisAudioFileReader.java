package spi.file;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.spi.AudioFileReader;

import spi.convert.OggVorbisFormatConversionProvider;

import libvorbis.JOggVorbis_File;
import libvorbis.Jvorbis_info;

public final class OggVorbisAudioFileReader extends AudioFileReader {
	// there is a real problem: a decoder must process all metadata block. this block can have a huge size.
	private static final int MAX_BUFFER = 1 << 19;// FIXME a metadata block can be 1 << 24.

	@Override
	public AudioFileFormat getAudioFileFormat(final InputStream stream)
			throws UnsupportedAudioFileException, IOException {

		final JOggVorbis_File ovf = new JOggVorbis_File();
		if( ovf.ov_open_callbacks( stream, null, 0, JOggVorbis_File.OV_CALLBACKS_STREAMONLY_NOCLOSE ) < 0 ) {
			ovf.ov_clear();
			throw new UnsupportedAudioFileException();
		}
		final Jvorbis_info vi = ovf.ov_info( -1 );
		// can be added properties with additional information
		final AudioFormat af = new AudioFormat( OggVorbisFormatConversionProvider.ENCODING,
				vi.rate, AudioSystem.NOT_SPECIFIED, vi.channels, 1, vi.rate, false );
		final AudioFileFormat aff = new AudioFileFormat(
				new AudioFileFormat.Type("OggVorbis", ""), af, AudioSystem.NOT_SPECIFIED );
		ovf.ov_clear();
		return aff;
	}

	@Override
	public AudioFileFormat getAudioFileFormat(final URL url)
			throws UnsupportedAudioFileException, IOException {

		InputStream is = null;
		try {
			is = url.openStream();
			return getAudioFileFormat( is );
		} catch(final UnsupportedAudioFileException e) {
			throw e;
		} catch(final IOException e) {
			throw e;
		} finally {
			if( is != null ) {
				try{ is.close(); } catch(final IOException e) {}
			}
		}
	}

	@Override
	public AudioFileFormat getAudioFileFormat(final File file)
			throws UnsupportedAudioFileException, IOException {

		InputStream is = null;
		try {
			is = new BufferedInputStream( new FileInputStream( file ) );
			return getAudioFileFormat( is );
		} catch(final UnsupportedAudioFileException e) {
			throw e;
		} catch(final IOException e) {
			throw e;
		} finally {
			if( is != null ) {
				try{ is.close(); } catch(final IOException e) {}
			}
		}
	}

	@Override
	public AudioInputStream getAudioInputStream(final InputStream stream)
			throws UnsupportedAudioFileException, IOException {
		// doc says: If the input stream does not support this, this method may fail with an IOException.
		// if( ! stream.markSupported() ) stream = new BufferedInputStream( stream, JOggVorbis_File.CHUNKSIZE * 2 );// possible resources leak
		try {
			stream.mark( MAX_BUFFER );
			final AudioFileFormat af = getAudioFileFormat( stream );
			stream.reset();// to start read header again
			return new AudioInputStream( stream, af.getFormat(), af.getFrameLength() );
		} catch(final UnsupportedAudioFileException e) {
			stream.reset();
			throw e;
		} catch(final IOException e) {
			stream.reset();
			throw e;
		}
	}

	@Override
	public AudioInputStream getAudioInputStream(final URL url)
			throws UnsupportedAudioFileException, IOException {

		InputStream is = null;
		try {
			is = url.openStream();
			return getAudioInputStream( is );
		} catch(final UnsupportedAudioFileException e) {
			if( is != null ) {
				try{ is.close(); } catch(final IOException ie) {}
			}
			throw e;
		} catch(final IOException e) {
			if( is != null ) {
				try{ is.close(); } catch(final IOException ie) {}
			}
			throw e;
		}
	}

	@Override
	public AudioInputStream getAudioInputStream(final File file)
			throws UnsupportedAudioFileException, IOException {

		BufferedInputStream is = null;
		try {
			is = new BufferedInputStream( new FileInputStream( file ), MAX_BUFFER );
			return getAudioInputStream( is );
		} catch(final UnsupportedAudioFileException e) {
			if( is != null ) {
				try{ is.close(); } catch(final IOException ie) {}
			}
			throw e;
		} catch(final IOException e) {
			if( is != null ) {
				try{ is.close(); } catch(final IOException ie) {}
			}
			throw e;
		}
	}
}
