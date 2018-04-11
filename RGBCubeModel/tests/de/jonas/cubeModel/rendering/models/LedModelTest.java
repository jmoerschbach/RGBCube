package de.jonas.cubeModel.rendering.models;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import de.jonas.cubeModel.rendering.models.LedModel;

public class LedModelTest {

	private LedModel toTest;

	@Before
	public void setUp() throws Exception {
		toTest = new LedModel();
	}

	@Test
	public void testMixColors() {
		testRed();
		testGreen();
		testBlue();
		testYellow();
		testCyan();
		testMagenta();
		testWhite();
		testBlack();
	}

	private void testRed() {
		toTest.mixColors(true, false, false);
		checkRGBColor(255, 0, 0);
	}

	private void testGreen() {
		toTest.mixColors(false, true, false);
		checkRGBColor(0, 255, 0);
	}

	private void testBlue() {
		toTest.mixColors(false, false, true);
		checkRGBColor(0, 0, 255);
	}

	private void testYellow() {
		toTest.mixColors(true, true, false);
		checkRGBColor(255, 255, 0);
	}

	private void testCyan() {
		toTest.mixColors(false, true, true);
		checkRGBColor(0, 255, 255);
	}

	private void testMagenta() {
		toTest.mixColors(true, false, true);
		checkRGBColor(255, 0, 255);
	}

	private void testWhite() {
		toTest.mixColors(true, true, true);
		checkRGBColor(255, 255, 255);
	}

	private void testBlack() {
		toTest.mixColors(false, false, false);
		checkRGBColor(0, 0, 0);

	}

	private void checkRGBColor(int r, int g, int b) {
		assertTrue(toTest.getCurrentColor().getRed() == r);
		assertTrue(toTest.getCurrentColor().getGreen() == g);
		assertTrue(toTest.getCurrentColor().getBlue() == b);
	}

}
