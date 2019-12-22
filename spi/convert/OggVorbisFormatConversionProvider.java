package spi.convert;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioFormat.Encoding;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.spi.FormatConversionProvider;

// TODO what must return getSourceEncodings(), getTargetEncodings ?
public final class OggVorbisFormatConversionProvider extends FormatConversionProvider {
	public static final AudioFormat.Encoding ENCODING = new AudioFormat.Encoding("OggVorbis");

	@Override
	public Encoding[] getSourceEncodings() {
		System.err.println("OggVorbisFormatConversionProvider.getSourceEncodings");
		return null;
	}

	@Override
	public Encoding[] getTargetEncodings() {
		System.err.println("OggVorbisFormatConversionProvider.getTargetEncodings");
		return null;
	}

	@Override
	public Encoding[] getTargetEncodings(final AudioFormat sourceFormat) {
		if( sourceFormat.getEncoding() == Encoding.PCM_SIGNED &&
			sourceFormat.getSampleSizeInBits() == 16 ) {
			final int sample_rate = (int)sourceFormat.getSampleRate();
			if( sample_rate >= 8000 && sample_rate < 200000 ) {
				final Encoding enc[] = new Encoding[] { ENCODING };
				return enc;
			}
		}
		return new Encoding[0];
	}

	@Override
	public AudioFormat[] getTargetFormats(final Encoding targetEncoding, final AudioFormat sourceFormat) {

		if( sourceFormat.getEncoding().equals( ENCODING ) ) {
			if( targetEncoding == Encoding.PCM_SIGNED ) {
				final AudioFormat af[] = {
					new AudioFormat( sourceFormat.getSampleRate(), 16, sourceFormat.getChannels(), true, false ),
					new AudioFormat( sourceFormat.getSampleRate(), 16, sourceFormat.getChannels(), true, true ),
					new AudioFormat( sourceFormat.getSampleRate(), 8, sourceFormat.getChannels(), true, false )
				};
				return af;
			}
			if( targetEncoding == Encoding.PCM_UNSIGNED ) {
				final AudioFormat af[] = {
					new AudioFormat( sourceFormat.getSampleRate(), 16, sourceFormat.getChannels(), false, false ),
					new AudioFormat( sourceFormat.getSampleRate(), 16, sourceFormat.getChannels(), false, true ),
					new AudioFormat( sourceFormat.getSampleRate(), 8, sourceFormat.getChannels(), false, false )
				};
				return af;
			}
		}
		return new AudioFormat[0];
	}

	@Override
	public AudioInputStream getAudioInputStream(final Encoding targetEncoding, final AudioInputStream sourceStream) {

		final AudioFormat saf = sourceStream.getFormat();
		final AudioFormat taf = new AudioFormat( targetEncoding,
						saf.getSampleRate(), 16, saf.getChannels(),
						AudioSystem.NOT_SPECIFIED, -1.0f, saf.isBigEndian() );

		return getAudioInputStream( taf, sourceStream );
	}

	@Override
	public AudioInputStream getAudioInputStream(final AudioFormat targetFormat,
					final AudioInputStream sourceStream) {

		return new OggVorbisDecodedAudioInputStream( sourceStream, targetFormat, AudioSystem.NOT_SPECIFIED );
	}
}
