package de.jonas.cubeModel.mediaPlayback;

public interface AudioPlayer {

	/**
	 * play a song until its end or until player is being stopped
	 * 
	 * @param filename
	 */
	public void play(String filename);

	/**
	 * if audio is currently played back, stop it. If nothing is played, do
	 * nothing
	 */
	public void stop();

}
