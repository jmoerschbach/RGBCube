package de.jonas.cubeModel;

/**
 * A Cube interprets the bytes sent to him in some way.
 * 
 * @author Jonas
 *
 */
public interface Cube {

	/**
	 * parses the byteStream which holds the information about lightning the
	 * LEDs.
	 * 
	 * @param byteStream
	 *            exactly 31 bytes (first 3 bytes must be 's', 't', 'a' and last
	 *            three bytes must be 'e', 'n', 'd')
	 */
	void parse(byte[] byteStream);

}