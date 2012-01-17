package be.hehehe.supersonic;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Executors;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import org.apache.commons.io.IOUtils;

public class Player {

	private boolean interrupted;

	public void start(final InputStream inputStream) {
		interrupted = false;

		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				play(inputStream);
			}
		};
		Executors.newCachedThreadPool().execute(runnable);
	}

	public void stop() {
		interrupted = true;
	}

	private void play(InputStream inputStream) {
		AudioInputStream in = null;
		AudioInputStream din = null;
		try {
			in = AudioSystem.getAudioInputStream(inputStream);
			AudioFormat baseFormat = in.getFormat();
			AudioFormat decodedFormat = new AudioFormat(
					AudioFormat.Encoding.PCM_SIGNED,
					baseFormat.getSampleRate(), 16, baseFormat.getChannels(),
					baseFormat.getChannels() * 2, baseFormat.getSampleRate(),
					false);
			din = AudioSystem.getAudioInputStream(decodedFormat, in);
			rawplay(decodedFormat, din);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(inputStream);
			IOUtils.closeQuietly(in);
			IOUtils.closeQuietly(din);
		}

	}

	private void rawplay(AudioFormat targetFormat, AudioInputStream din)
			throws IOException, LineUnavailableException {
		byte[] data = new byte[4096];
		SourceDataLine line = getLine(targetFormat);
		if (line != null) {
			// Start
			line.start();
			int nBytesRead = 0;
			int nBytesWritten = 0;
			while (nBytesRead != -1 && !interrupted) {
				nBytesRead = din.read(data, 0, data.length);
				if (nBytesRead != -1)
					nBytesWritten = line.write(data, 0, nBytesRead);
			}
			// Stop
			line.drain();
			line.stop();
			line.close();
			IOUtils.closeQuietly(din);
		}
	}

	private SourceDataLine getLine(AudioFormat audioFormat)
			throws LineUnavailableException {
		SourceDataLine res = null;
		DataLine.Info info = new DataLine.Info(SourceDataLine.class,
				audioFormat);
		res = (SourceDataLine) AudioSystem.getLine(info);
		res.open(audioFormat);
		return res;
	}
}
