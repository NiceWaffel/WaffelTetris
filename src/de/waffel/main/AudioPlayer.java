package de.waffel.main;

import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineEvent.Type;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


public class AudioPlayer {
	
	private Clip clip;
	private Thread musicThread;
	private Runnable runnable;
	
	private boolean playing;
	
	public AudioPlayer(String clipPath) {
		runnable = new Runnable() {
			@Override
			public void run() {
				try {
					clip = AudioSystem.getClip();
					clip.open(AudioSystem.getAudioInputStream(AudioPlayer.class.getResource(clipPath)));
					clip.start();
				} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
					e.printStackTrace();
				}
				clip.addLineListener(new LineListener() {
					@Override
					public void update(LineEvent event) {
						if(event.getType() == Type.STOP) {
							clip.close();
							
							// Loop the file if we are still playing
							if(playing)
								playAudio();
						}
					}
				});
			}
		};
	}
	
	public void playAudio() {
		playing = true;
		musicThread = new Thread(runnable);
		musicThread.start();
	}
	
	public void stop() {
		playing = false;
		clip.stop();
	}
	
	public boolean isPlaying() {
		return this.playing;
	}
}
