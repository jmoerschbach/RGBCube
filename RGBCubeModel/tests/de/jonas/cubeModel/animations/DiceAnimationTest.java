package de.jonas.cubeModel.animations;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import de.jonas.cubeModel.rendering.models.TestCube;

public class DiceAnimationTest extends TestBase {

	DiceAnimation toTest;

	@Before
	public void setUp() throws Exception {
		dummyCube = new TestCube();
		toTest = new DiceAnimation(dummyCube);
	}

	@Test
	public void test() {
		toTest.animate10ms();

		for (int i = 1; i < 7; i++) {
			assertTrue(isBlack(0, i, 0));
			assertTrue(isWhite(0, i, 1));
			assertTrue(isWhite(0, i, 2));
			assertTrue(isBlack(0, i, 3));
			assertTrue(isBlack(0, i, 4));
			assertTrue(isWhite(0, i, 5));
			assertTrue(isWhite(0, i, 6));
			assertTrue(isBlack(0, i, 7));
		}

		toTest.animate10ms();
		assertTrue(isWhite(1, 3, 3));
		assertTrue(isWhite(1, 3, 4));
		assertTrue(isWhite(1, 4, 3));
		assertTrue(isWhite(1, 4, 4));

	}

}
