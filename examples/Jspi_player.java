package examples;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.*;

public final class Jspi_player {
	public static void main(String[] args) {
		if( args.length != 1 ) {
			System.out.println("Usage:");
			System.out.println("java -jar Jspi_player.jar <Input File[.wav]>");
			System.exit( 0 );
			return;
		}
		AudioInputStream in = null;
		AudioInputStream din = null;
		SourceDataLine audio_out_line = null;
		try {
			in = AudioSystem.getAudioInputStream( new File( args[0] ) );
			if( in != null ) {
				final AudioFormat in_format = in.getFormat();
				final int channels = in_format.getChannels();
				final AudioFormat decoded_format = new AudioFormat(
						AudioFormat.Encoding.PCM_SIGNED,
						in_format.getSampleRate(),
						16, channels, channels * (16 / 8),
						in_format.getSampleRate(),
						false );
				din = AudioSystem.getAudioInputStream( decoded_format, in );
				//
				DataLine.Info info = new DataLine.Info( SourceDataLine.class, decoded_format );
				if( ! AudioSystem.isLineSupported( info ) ) {
					throw new LineUnavailableException("sorry, the sound format cannot be played");
				}
				audio_out_line = (SourceDataLine) AudioSystem.getLine( info );
				audio_out_line.open();
				audio_out_line.start();
				final byte[] buffer = new byte[4096];
				int readed;
				//
				while( (readed = din.read( buffer, 0, buffer.length )) >= 0 ) {
					audio_out_line.write( buffer, 0, readed );
				}
				//
				audio_out_line.drain();
			}
		} catch(Exception e) {
			System.err.println( e.getMessage() );
			e.printStackTrace();
		} finally {
			if( audio_out_line != null ) audio_out_line.close();
			if( din != null ) try { din.close(); } catch( IOException e ) {}
			if( in != null ) try { in.close(); } catch( IOException e ) {}
		}
	}
}
