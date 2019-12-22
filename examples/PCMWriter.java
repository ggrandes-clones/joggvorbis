package examples;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Convert raw PCM 16 bit, signed data to PCM wav-file.
 */
public final class PCMWriter {
	private static final byte[] PCM_HEADER = {
		'R', 'I', 'F', 'F',// RIFF
		0, 0, 0, 0,// RIFF size = full size - 8
		'W', 'A', 'V', 'E', 'f', 'm', 't', ' ',// WAVEfmt
		0x10, 0, 0, 0,// Ext
		1, 0,// Format Tag
		2, 0,// number of channels
		0x11, 0x2B, 0x00, 0x00,// Samples per seconds
		0x11, 0x2B, 0x00, 0x00,// Avg Bytes per seconds
		4, 0,// BLock align
		16, 0,// Bits per sample
		'd', 'a', 't', 'a',// data
		0, 0, 0, 0// data size = full size - 44
	};
	//
	private byte[] mHeader = new byte[PCM_HEADER.length];
	private RandomAccessFile mFile = null;
	private int mDataLength = 0;
	private byte[] mCache = null;
	//
	private static void valueToBytes(int value, int byteCount, final byte[] data, int offset) {
		final int count = byteCount << 3;
		for( int i = 0; i < count; i += 8 ) {
			data[offset++] = (byte)(value >> i);
		}
	}
	private void setSoundFormat(int sampleRate, int bitsPerSample, int channels) {
		synchronized ( mHeader ) {
			valueToBytes( channels, 2, mHeader, 22 );// Channels
			valueToBytes( sampleRate, 4, mHeader, 24 );// Sample rate
			valueToBytes( sampleRate * channels * (bitsPerSample >> 3), 4, mHeader, 28 );// Avg Bytes per seconds
			valueToBytes( (bitsPerSample >> 3) * channels, 2, mHeader, 32 );// BLock align
			valueToBytes( bitsPerSample, 2, mHeader, 34 );// Bits per sample
		}
	}
	//
	public void startWrite(final String name, int sampleRate, int bitsPerSample, int channels) {
		mFile = null;
		try {
			System.out.println( "Start write, file: " + name );
			//
			mFile = new RandomAccessFile( name + ".wav", "rw" );
			mDataLength = 0;
			System.arraycopy( PCM_HEADER, 0, mHeader, 0, PCM_HEADER.length );
			setSoundFormat( sampleRate, bitsPerSample, channels );
			//
			mFile.write( mHeader );
		} catch (IOException e) {
			System.out.println( e.getMessage() );
			if( mFile != null ) try { mFile.close(); } catch(IOException ie){}
		}
	}
	public void write(final short[] buff, int count) {
		synchronized ( mHeader ) {
			if( mFile == null ) return;
			if( mCache == null ) mCache = new byte[buff.length << 1];
			ByteBuffer.wrap( mCache ).order( ByteOrder.LITTLE_ENDIAN ).asShortBuffer().put( buff, 0, count );
			try {
				mFile.write( mCache, 0, count << 1 );
				mDataLength += count;
			} catch (IOException e) {
				close();
			}
		}
	}
	public void write(final byte[] buff, int count) {
		synchronized ( mHeader ) {
			if( mFile == null ) return;
			try {
				mFile.write( buff, 0, count );
				mDataLength += count << 1;
			} catch (IOException e) {
				close();
			}
		}
	}
	public void close() {
		if( mFile != null ) {
			System.out.println( "Closing, data length = " + mDataLength );
			synchronized ( mHeader ) {// writing must be denied!!!
				try {
					// modify header
					int size = (mDataLength << 1);
					valueToBytes( size, 4, mHeader, 40 );
					size += 36;
					valueToBytes( size, 4, mHeader, 4 );
					mFile.seek( 0 );
					mFile.write( mHeader );
				} catch (IOException e) {
					System.out.println( e.getMessage() );
				} finally {
					try { mFile.close(); } catch (IOException e) {}
				}
			}
		}
		mFile = null;
	}
	//
	public static void main(String args[]) {
		if( args.length != 4 ) {
			System.out.println( "Usage:");
			System.out.println(
			"java -jar PCMWriter.jar <sample rate> <channels> <Input File[.raw]> <Output File>");
			System.exit( 0 );
			return;
		}
		final int sampleRate = Integer.parseInt( args[0] );
		final int ch = Integer.parseInt( args[1] );
		FileInputStream in = null;
		try{
			in = new FileInputStream( args[2] );
			final byte[] buff = new byte[in.available()];
			final int readed = in.read( buff );
			final PCMWriter t = new PCMWriter();
			t.startWrite( args[3], sampleRate, 16, ch );
			t.write( buff, readed );
			t.close();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if( in != null ) try{ in.close(); } catch(IOException e) {}
		}
	}
}
