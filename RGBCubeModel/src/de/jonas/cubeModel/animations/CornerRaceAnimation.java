package de.jonas.cubeModel.animations;

import java.util.Observable;

import de.jonas.cubeModel.Cube;

public class CornerRaceAnimation extends Animation {
	private int state;

	public CornerRaceAnimation(Cube cubes) {
		super(cubes);
		turnLayerOn(0);
	}

	@Override
	public String getAnimationName() {
		return "CornerRace";
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub

	}

	@Override
	void loop() {
		setLedWhite(0, 3);
		setLedWhite(0, 4);
		delay(100);
		clearLed(0, 3);
		clearLed(0, 4);
		setLedWhite(0, 2);
		setLedWhite(0, 5);
		delay(100);
		clearLed(0, 2);
		clearLed(0, 5);
		setLedWhite(0, 1);
		setLedWhite(0, 6);
		delay(100);
		clearLed(0, 1);
		clearLed(0, 6);
		setLedWhite(0, 0);
		setLedWhite(0, 7);
		delay(100);
		clearLed(0, 0);
		clearLed(0, 7);

		delay(1000);

	}

}
