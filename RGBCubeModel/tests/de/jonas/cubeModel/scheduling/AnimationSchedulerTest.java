package de.jonas.cubeModel.scheduling;

import static org.junit.Assert.*;

import java.util.Observable;
import java.util.Observer;

import org.junit.Before;
import org.junit.Test;

import de.jonas.cubeModel.animations.Animation;
import de.jonas.cubeModel.animations.DummyAnimation;
import de.jonas.cubeModel.mediaPlayback.MP3Player;

public class AnimationSchedulerTest implements Observer {

	private AnimationScheduler toTest;

	@Before
	public void setUp() throws Exception {

		toTest = new AnimationScheduler(new MP3Player(), null);
	}

	@Test
	public void testAddAnimation() {
		Animation dummy = new DummyAnimation(new DummyCube());
		toTest.addAnimation(dummy);

		int expected = 1;
		int actual = toTest.getNumberOfRegisteredAnimations();
		assertEquals(expected, actual);
	}

	@Test
	public void testIntervalAnimation() {
//		DummyAnimation dummy = new DummyAnimation(new DummyCube());
//		toTest.addAnimation(dummy);
//
//		toTest.animate(1);
//		String expected = "1";
//		String actual = dummy.toString();
//		assertEquals(expected, actual);
//
//		toTest.animate(2);
//		expected = "2";
//		actual = dummy.toString();
//		assertEquals(expected, actual);
//
//		toTest.animate(5);
//		expected = "5";
//		actual = dummy.toString();
//		assertEquals(expected, actual);
//
//		toTest.animate(10);
//		expected = "10";
//		actual = dummy.toString();
//		assertEquals(expected, actual);
//
//		toTest.animate(50);
//		expected = "50";
//		actual = dummy.toString();
//		assertEquals(expected, actual);
//
//		toTest.animate(100);
//		expected = "100";
//		actual = dummy.toString();
//		assertEquals(expected, actual);
//
//		toTest.animate(200);
//		expected = "200";
//		actual = dummy.toString();
//		assertEquals(expected, actual);
//
//		toTest.animate(500);
//		expected = "500";
//		actual = dummy.toString();
//		assertEquals(expected, actual);
//
//		toTest.animate(1000);
//		expected = "1000";
//		actual = dummy.toString();
//		assertEquals(expected, actual);

	}

	@Test
	public void testStartStop() {
		toTest.addObserver(this);
		DummyAnimation dummy = new DummyAnimation(new DummyCube());
		toTest.addAnimation(dummy);
		toTest.start();
		assertEquals(toTest.getState(), SchedulingState.PLAYING);
		try {
			Thread.sleep(1001);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		toTest.stop();
		assertEquals(toTest.getState(), SchedulingState.STOPPED);

		assertTrue(dummy.animated1000ms >= 1);
		assertTrue(dummy.animated500ms >= 2);
		assertTrue(dummy.animated200ms >= 5);
		assertTrue(dummy.animated100ms >= 10);
		assertTrue(dummy.animated50ms >= 20);
		assertTrue(dummy.animated25ms >= 40);
		assertTrue(dummy.animated10ms >= 100);
		assertTrue(dummy.animated5ms >= 200);
		assertTrue(dummy.animated2ms >= 500);
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub

	}

}
