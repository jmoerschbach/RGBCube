package de.jonas.cubeModel.animations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import de.jonas.cubeModel.Constants;
import de.jonas.cubeModel.rendering.models.TestCube;

public class AnimationTest extends TestBase {

	private Animation toTest;

	@Before
	public void setUp() throws Exception {
		toTest = new DummyAnimation(new TestCube());
	}

	@Test
	public void testTurnLayerOn() {
		boolean expected;
		toTest.turnAllLayersOff();
		for (int i = 0; i < Constants.numberOfLedsInColumn; i++) {
			toTest.turnLayerOn(i);
			expected = testIfBitIsSet(toTest.byteStream, 0, i);
			assertTrue(expected);
		}
	}

	@Test
	public void testTurnLayerOff() {
		boolean expected;
		toTest.turnAllLayersOn();
		for (int i = 0; i < Constants.numberOfLedsInColumn; i++) {
			toTest.turnLayerOff(i);
			expected = testIfBitIsCleared(toTest.byteStream, 0, i);
			assertTrue(expected);
		}
	}

	@Test
	public void testTurnAllLayersOn() {
		toTest.turnAllLayersOn();
		byte expected = (byte) 0xff;
		byte actual = toTest.byteStream[0];
		assertEquals(expected, actual);

		toTest.turnAllLayersOff();
		expected = (byte) 0x00;
		actual = toTest.byteStream[0];
		assertEquals(expected, actual);
	}

	@Test
	public void testTurnAllLayersOff() {
		toTest.turnAllLayersOff();
		byte expected = (byte) 0x00;
		byte actual = toTest.byteStream[0];
		assertEquals(expected, actual);

		toTest.turnAllLayersOn();
		expected = (byte) 0xff;
		actual = toTest.byteStream[0];
		assertEquals(expected, actual);
	}

	@Test
	public void testSetLedWhite() {
		testSetFirstFirstLedWhite();

		testSetFirstLastLedWhite();

		testSetSecondLastLedWhite();
	}

	private void testSetSecondLastLedWhite() {
		toTest.setLedWhite(1, 7);
		boolean red = testIfBitIsSet(toTest.byteStream, 4, 7);
		boolean green = testIfBitIsSet(toTest.byteStream, 5, 7);
		boolean blue = testIfBitIsSet(toTest.byteStream, 6, 7);

		assertTrue(red);
		assertTrue(green);
		assertTrue(blue);

	}

	private void testSetFirstLastLedWhite() {
		toTest.setLedWhite(0, 7);
		boolean red = testIfBitIsSet(toTest.byteStream, 1, 7);
		boolean green = testIfBitIsSet(toTest.byteStream, 2, 7);
		boolean blue = testIfBitIsSet(toTest.byteStream, 3, 7);
		assertTrue(red);
		assertTrue(green);
		assertTrue(blue);
	}

	private void testSetFirstFirstLedWhite() {
		toTest.setLedWhite(0, 0);
		boolean red = testIfBitIsSet(toTest.byteStream, 1, 0);
		boolean green = testIfBitIsSet(toTest.byteStream, 2, 0);
		boolean blue = testIfBitIsSet(toTest.byteStream, 3, 0);
		assertTrue(red);
		assertTrue(green);
		assertTrue(blue);

	}

	@Test
	public void testSetLedBlack() {
		testSetSecondLastLedBlack();
		testSetFirstFirstLedBlack();
		testSetFirstLastLedBlack();
	}

	private void testSetFirstFirstLedBlack() {
		toTest.clearLed(0, 0);
		boolean red = testIfBitIsCleared(toTest.byteStream, 1, 0);
		boolean green = testIfBitIsCleared(toTest.byteStream, 2, 0);
		boolean blue = testIfBitIsCleared(toTest.byteStream, 3, 0);
		assertTrue(red);
		assertTrue(green);
		assertTrue(blue);

	}

	private void testSetSecondLastLedBlack() {
		toTest.clearLed(1, 7);
		boolean red = testIfBitIsCleared(toTest.byteStream, 4, 7);
		boolean green = testIfBitIsCleared(toTest.byteStream, 5, 7);
		boolean blue = testIfBitIsCleared(toTest.byteStream, 6, 7);

		assertTrue(red);
		assertTrue(green);
		assertTrue(blue);

	}

	private void testSetFirstLastLedBlack() {
		toTest.clearLed(0, 7);
		boolean red = testIfBitIsCleared(toTest.byteStream, 1, 7);
		boolean green = testIfBitIsCleared(toTest.byteStream, 2, 7);
		boolean blue = testIfBitIsCleared(toTest.byteStream, 3, 7);
		assertTrue(red);
		assertTrue(green);
		assertTrue(blue);
	}

}
