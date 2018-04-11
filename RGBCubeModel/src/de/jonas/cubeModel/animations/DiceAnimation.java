package de.jonas.cubeModel.animations;

import de.jonas.cubeModel.Cube;

public class DiceAnimation extends Animation {

	private int state = 0;

	public DiceAnimation(Cube cubes) {
		super(cubes);
		turnLayerOn(7);
		milliSecondsToRun = 20000;
	}

	private void sixTop() {
		clearAllLeds();
		for (int i = 1; i < 7; i++) {
			setLedWhite(i, 1);
			setLedWhite(i, 2);
			setLedWhite(i, 5);
			setLedWhite(i, 6);
		}
	}

	private void oneTop() {
		clearAllLeds();
		setLedWhite(3, 3);
		setLedWhite(3, 4);
		setLedWhite(4, 3);
		setLedWhite(4, 4);
	}

	private void twoTop() {
		clearAllLeds();
		setLedWhite(2, 2);
		setLedWhite(2, 1);
		setLedWhite(1, 2);
		setLedWhite(1, 1);

		setLedWhite(5, 5);
		setLedWhite(5, 6);
		setLedWhite(6, 5);
		setLedWhite(6, 6);
	}

	private void threeTop() {
		clearAllLeds();
		setLedWhite(2, 2);
		setLedWhite(2, 1);
		setLedWhite(1, 2);
		setLedWhite(1, 1);

		setLedWhite(3, 3);
		setLedWhite(3, 4);
		setLedWhite(4, 3);
		setLedWhite(4, 4);

		setLedWhite(5, 5);
		setLedWhite(5, 6);
		setLedWhite(6, 5);
		setLedWhite(6, 6);
	}

	private void fourTop() {
		clearAllLeds();
		setLedWhite(2, 2);
		setLedWhite(2, 1);
		setLedWhite(1, 2);
		setLedWhite(1, 1);

		setLedWhite(5, 2);
		setLedWhite(5, 1);
		setLedWhite(6, 2);
		setLedWhite(6, 1);

		setLedWhite(2, 5);
		setLedWhite(2, 6);
		setLedWhite(1, 5);
		setLedWhite(1, 6);

		setLedWhite(5, 5);
		setLedWhite(5, 6);
		setLedWhite(6, 5);
		setLedWhite(6, 6);
	}

	private void fiveTop() {
		clearAllLeds();
		setLedWhite(2, 2);
		setLedWhite(2, 1);
		setLedWhite(1, 2);
		setLedWhite(1, 1);

		setLedWhite(5, 2);
		setLedWhite(5, 1);
		setLedWhite(6, 2);
		setLedWhite(6, 1);

		setLedWhite(3, 3);
		setLedWhite(3, 4);
		setLedWhite(4, 3);
		setLedWhite(4, 4);

		setLedWhite(2, 5);
		setLedWhite(2, 6);
		setLedWhite(1, 5);
		setLedWhite(1, 6);

		setLedWhite(5, 5);
		setLedWhite(5, 6);
		setLedWhite(6, 5);
		setLedWhite(6, 6);
	}

	@Override
	public void animate10ms() {
		switch (state) {
		case 0:
			sixTop();
			break;
		case 1:
			oneTop();
			break;
		case 2:
			twoTop();
			break;
		case 3:
			threeTop();
			break;
		case 4:
			fourTop();
			break;
		case 5:
			fiveTop();
			break;
		}

		// TODO Auto-generated method stub

	}

	@Override
	public void animate50ms() {
		// TODO Auto-generated method stub

	}

	@Override
	public void animate100ms() {
		// TODO Auto-generated method stub

	}

	@Override
	public void animate200ms() {
		// TODO Auto-generated method stub

	}

	@Override
	public void animate500ms() {
		// TODO Auto-generated method stub

	}

	@Override
	public void animate1000ms() {
		state++;
		if (state > 5)
			state = 0;

	}

	@Override
	public String getAnimationName() {
		return "Wuerfel";
	}

	@Override
	public void reset() {
		turnLayerOn(7);
		milliSecondsToRun = 20000;
		
	}

	@Override
	void loop() {
		// TODO Auto-generated method stub
		
	}

}
