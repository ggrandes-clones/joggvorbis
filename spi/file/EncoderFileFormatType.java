package spi.file;

import javax.sound.sampled.AudioFileFormat;

/**
 * Extension for AudioFileFormat.Type to set encoding parameters
 */
public final class EncoderFileFormatType extends AudioFileFormat.Type {
	public static final int UNKNOWN = 0;
	/** Variable bit rate */
	public static final int VBR = 1;
	/** Constant bit rate */
	public static final int CBR = 2;
	/** Average bit rate */
	public static final int ABR = 3;
	/** Stream type: vbr, cbr, abr */
	final int mStreamType;
	/** Stream type parameter. For vbr this is quality, for cbr and abr - bitrate */
	final float mStreamTypeParameter;
	/**
	 * Constructor
	 *
	 * @param name file type name.
	 * @param extension file type extension.
	 * @param type stream type: vbr, cbr or abr.
	 * @param parameter a parameter of the stream type.
	 */
	public EncoderFileFormatType(final String name, final String extension, final int streamType, final float parameter) {
		super( name, extension );
		mStreamType = streamType;
		mStreamTypeParameter = parameter;
	}
}
