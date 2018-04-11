package de.jonas.cubeModel.animations;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import de.jonas.cubeModel.Constants;
import de.jonas.cubeModel.Cube;

/**
 * This is the base class for all animations that shall run on the RGB-Cube.
 * Every animation needs to manipulate the {@link #byteStream}, which is shifted
 * out to the Cube. This class provides many helper functions to manipulate the
 * byteStream very comfortably on a high abstraction layer.
 * <p>
 * To create a new animation, simply extend this class and implement your
 * animating algorithm. An Animation runs infinitely per default. However, you
 * can (and you should) restrict the runtime of your animation by setting
 * {@link #milliSecondsToRun} to an appropriate value in order to allow
 * subsequent animations to start.
 * <p>
 * <strong>Do not forget to add your animation's name to {@link #ANIMATIONS}
 * !</strong>
 * 
 * @author Jonas
 *
 */
public abstract class Animation implements Observer, Runnable {

	protected final static int INFINITY = -1;

	/**
	 * Register your animation by adding the class name to this array. Only then
	 * it will be found via reflection.
	 */
	public final static String[] ANIMATIONS = new String[] { "CornerRaceAnimation", //
			"DiceAnimation", //
			"IntroAnimation", //
			"LayerUpDownAnimation", //
			"LightningAnimation", //
			"RainfallAnimation", //
			"RandomLedAnimation", //
			"SimpleMultiplexAnimation", //
			"AllOutAnimation",//
			"CornerRaceAnimation"};

	protected volatile byte[] byteStream;

	protected Random rand;
	private List<Cube> allCubes;
	protected long milliSecondsToRun;

	private boolean isRunning;

	private int clock;

	public boolean shouldRun() {
		if (milliSecondsToRun == INFINITY)
			return true;
		return milliSecondsToRun > 0;
	}

	public Animation(Cube cubes) {
		milliSecondsToRun = INFINITY;
		allCubes = new ArrayList<>();
		// for (Cube cur : cubes)
		allCubes.add(cubes);

		this.byteStream = new byte[25];
		this.rand = new Random();
		clearAllLeds();
		turnAllLayersOff();
	}

	/**
	 * turns a layers on (while leaving all other layers as it is), meaning the
	 * anodes are set to high. If the number exceeds the limits, nothing is done
	 * 
	 * @param layerNr
	 *            the layer number in range from 0 to
	 *            {@link Constants#numberOfLedsInColumn}.
	 */
	final void turnLayerOn(int layerNr) {
		if (layerNr >= 0 && layerNr < Constants.numberOfLedsInColumn)
			// byteStream[0] |= (1 << layerNr);
			setBit(0, layerNr);
	}

	@Override
	public void run() {
		isRunning = true;
		while (shouldRun()) {
			loop();
			delay(1);
		}
		isRunning = false;
	}

	public boolean isRunning() {
		return isRunning;
	}

	abstract void loop();

	final void delay(int ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	final void turnAllLayersOn() {
		for (int i = 0; i < Constants.numberOfLedsInColumn; i++)
			turnLayerOn(i);
	}

	/**
	 * turns a layers off, meaning the anodes are set to low. If the number
	 * exceeds the limits, nothing is done
	 * 
	 * @param layerNr
	 *            the layer number in range from 0 to
	 *            {@link Constants#numberOfLedsInColumn}.
	 */
	final void turnLayerOff(int layerNr) {
		if (layerNr >= 0 && layerNr < Constants.numberOfLedsInColumn)
			// byteStream[0] &= ~(1 << layerNr);
			clearBit(0, layerNr);
	}

	final void turnAllLayersOff() {
		for (int i = 0; i < Constants.numberOfLedsInColumn; i++)
			turnLayerOff(i);
	}

	/**
	 * clears all bits meaning all LEDs (RGB) are turned off
	 */
	final void clearAllLeds() {
		for (int row = 0; row < Constants.numberOfLedsInRow; row++) {
			for (int column = 0; column < Constants.numberOfLedsInColumn; column++)
				clearLed(row, column);
		}
	}

	/**
	 * sets all LEDs to white
	 */
	final void setAllLedsWhite() {
		for (int row = 0; row < Constants.numberOfLedsInRow; row++) {
			for (int column = 0; column < Constants.numberOfLedsInColumn; column++)
				setLedWhite(row, column);
		}
	}

	/**
	 * sets all LEDs to blue
	 */
	final void setAllLedsBlue() {
		for (int row = 0; row < Constants.numberOfLedsInRow; row++) {
			for (int column = 0; column < Constants.numberOfLedsInColumn; column++)
				setLedBlue(row, column);
		}
	}

	/**
	 * sets all LEDs to red
	 */
	final void setAllLedsRed() {
		for (int row = 0; row < Constants.numberOfLedsInRow; row++) {
			for (int column = 0; column < Constants.numberOfLedsInColumn; column++)
				setLedRed(row, column);
		}
	}

	/**
	 * sets all LEDs to green
	 */
	final void setAllLedsGreen() {
		for (int row = 0; row < Constants.numberOfLedsInRow; row++) {
			for (int column = 0; column < Constants.numberOfLedsInColumn; column++)
				setLedGreen(row, column);
		}
	}

	/**
	 * sets all LEDs to magenta
	 */
	final void setAllLedsMagenta() {
		for (int row = 0; row < Constants.numberOfLedsInRow; row++) {
			for (int column = 0; column < Constants.numberOfLedsInColumn; column++)
				setLedMagenta(row, column);
		}
	}

	/**
	 * sets all LEDs to cyan
	 */
	final void setAllLedsCyan() {
		for (int row = 0; row < Constants.numberOfLedsInRow; row++) {
			for (int column = 0; column < Constants.numberOfLedsInColumn; column++)
				setLedCyan(row, column);
		}
	}

	/**
	 * sets all LEDs to yellow
	 */
	final void setAllLedsYellow() {
		for (int row = 0; row < Constants.numberOfLedsInRow; row++) {
			for (int column = 0; column < Constants.numberOfLedsInColumn; column++)
				setLedYellow(row, column);
		}
	}

	/**
	 * turns all 3 Leds (R, G, B) of RGB-Led with row- and column-index on
	 * 
	 * @param row
	 *            the row in which this LED is placed (0 to
	 *            {@link Constants#numberOfLedsInRow}
	 * @param column
	 *            the colum in which this LED is placed (0 to
	 *            {@link Constants#numberOfLedsInColumn}
	 */
	final void setLedWhite(int row, int column) {
		if (!areIndicesValid(row, column))
			return;
		setLedBlue(row, column);
		setLedGreen(row, column);
		setLedRed(row, column);
	}

	private boolean areIndicesValid(int row, int column) {
		return row >= 0 && row < Constants.numberOfLedsInRow && column >= 0 && column < Constants.numberOfLedsInColumn;
	}

	final void setLedYellow(int row, int column) {
		if (!areIndicesValid(row, column))
			return;
		setLedGreen(row, column);
		setLedRed(row, column);
	}

	final void setLedMagenta(int row, int column) {
		if (!areIndicesValid(row, column))
			return;
		setLedBlue(row, column);
		setLedRed(row, column);
	}

	final void setLedCyan(int row, int column) {
		if (!areIndicesValid(row, column))
			return;
		setLedBlue(row, column);
		setLedGreen(row, column);
	}

	/**
	 * turns all 3 Leds (R, G, B) of RGB-Led with row- and column-index off
	 * 
	 * @param row
	 *            the row in which this LED is placed (0 to
	 *            {@link Constants#numberOfLedsInRow}
	 * @param column
	 *            the colum in which this LED is placed (0 to
	 *            {@link Constants#numberOfLedsInColumn}
	 */
	final void clearLed(int row, int column) {
		if (!areIndicesValid(row, column))
			return;
		clearLedBlue(row, column);
		clearLedGreen(row, column);
		clearLedRed(row, column);
	}

	final void setLedBlue(int row, int column) {
		if (!areIndicesValid(row, column))
			return;
		int base = row * 3 + 1;
		setBit(base + 2, column);
	}

	final void setLedGreen(int row, int column) {
		if (!areIndicesValid(row, column))
			return;
		int base = row * 3 + 1;
		setBit(base + 1, column);
	}

	final void setLedRed(int row, int column) {
		if (!areIndicesValid(row, column))
			return;
		int base = row * 3 + 1;
		setBit(base, column);
	}

	final void clearLedBlue(int row, int column) {
		if (!areIndicesValid(row, column))
			return;
		int base = row * 3 + 1;
		clearBit(base + 2, column);
	}

	final void clearLedGreen(int row, int column) {
		if (!areIndicesValid(row, column))
			return;
		int base = row * 3 + 1;
		clearBit(base + 1, column);
	}

	final void clearLedRed(int row, int column) {
		if (!areIndicesValid(row, column))
			return;
		int base = row * 3 + 1;
		clearBit(base, column);
	}

	private void setBit(int byteNr, int bitNr) {
		byteStream[byteNr] |= (1 << bitNr);
	}

	private void clearBit(int byteNr, int bitNr) {
		byteStream[byteNr] &= ~(1 << bitNr);
	}

	public final synchronized void sendToCube(int interval) {
		// for (Cube cur : allCubes)
		// cur.parse(byteStream);
		if (milliSecondsToRun != INFINITY)
			milliSecondsToRun -= interval;
	}

	/**
	 * gets executed every 1ms. Use this for multiplexing!
	 */

	public void animate1ms() {
	}

	/**
	 * gets executed every 2ms. Use this for multiplexing!
	 */

	public void animate2ms() {
	}

	/**
	 * gets executed every 5ms.
	 */
	public void animate5ms() {
	}

	/**
	 * gets executed every 10ms
	 */
	public void animate10ms() {
	}

	/**
	 * gets executed every 25ms
	 */
	public void animate25ms() {
	}

	/**
	 * gets executed every 50ms
	 */
	public void animate50ms() {
	}

	/**
	 * gets executed every 100ms
	 */
	public void animate100ms() {
	}

	/**
	 * gets executed every 200ms
	 */
	public void animate200ms() {
	}

	/**
	 * gets executed every 500ms
	 */
	public void animate500ms() {
	}

	/**
	 * gets executed every 1000ms
	 */
	public void animate1000ms() {
	}

	/**
	 * 
	 * @return the name of this animation
	 */
	public abstract String getAnimationName();

	public String toString() {
		return getAnimationName();
	}

	/**
	 * 
	 * @return the name of the audio file that is to be played.<br>
	 *         <strong>Note: No file type ending permitted (instead of "bla.mp3"
	 *         return "bla")
	 */
	public String getAudioFileName() {
		return "";
	}

	/**
	 * resets this animation to its initial state. Most often,
	 * {@link #milliSecondsToRun} is reset to its initial value to allow this
	 * animation to be played again.
	 */
	public abstract void reset();

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Animation other = (Animation) obj;
		if (!getAnimationName().equalsIgnoreCase(other.getAnimationName()))
			return false;
		return true;
	}

	@Override
	public void update(Observable o, Object arg) {

	}

	public synchronized byte[] getByteStream() {
		return byteStream;
	}

	public void clockTick() {
		this.clock -= 1;

	}

}
