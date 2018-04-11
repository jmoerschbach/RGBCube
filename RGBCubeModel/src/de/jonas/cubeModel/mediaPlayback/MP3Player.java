package de.jonas.cubeModel.mediaPlayback;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.logging.Logger;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

/**
 * AudioPlayer to play mp3 files. Uses an extra thread for playback in
 * background. Not quite sure about concurrent/parallel programming issues... :(
 * 
 * @author Jonas
 *
 */
public class MP3Player implements AudioPlayer {
	private static final Logger log = Logger.getLogger(MP3Player.class.getName());
	private Player player;
	private Thread runner;

	public void play(final String filename) {
		stop();

		runner = new Thread(new Runnable() {

			@Override
			public void run() {

				try {
					log.info("playing " + filename+".mp3");
					player = new Player(new FileInputStream("sounds" + File.separator + filename+".mp3"));
					player.play();
				} catch (FileNotFoundException | JavaLayerException e) {
					log.severe(
							"could not find audio file. May not be an error since it is not required for an Animation to play music.");
				}

			}
		});
		runner.start();
	}

	public void stop() {

		log.info("stopping...");
		if (runner != null)
			runner.interrupt();

		if (player != null)
			player.close();
	}

}
