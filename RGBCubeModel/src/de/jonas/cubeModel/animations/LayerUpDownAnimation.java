package de.jonas.cubeModel.animations;

import de.jonas.cubeModel.Cube;

public class LayerUpDownAnimation extends Animation {

	private int layerToLight = 0;
	private int STATE;
	private final static int COUNT_UP = 1;
	private final static int COUNT_DOWN = 0;

	public LayerUpDownAnimation(Cube cubes) {
		super(cubes);

	}

	@Override
	public void animate500ms() {
		if (STATE == COUNT_UP) {
			layerToLight++;
		} else if (STATE == COUNT_DOWN) {
			layerToLight--;
		}
		if (STATE == COUNT_UP && layerToLight == 7) {
			STATE = COUNT_DOWN;
			changeColor();
		}
		if (STATE == COUNT_DOWN && layerToLight == 0) {
			STATE = COUNT_UP;
			changeColor();
		}

		turnAllLayersOff();
		turnLayerOn(layerToLight);
	}

	private void changeColor() {
		clearAllLeds();
		int color = rand.nextInt(7);
		switch (color) {
		case 0:
			setAllLedsBlue();
			break;
		case 1:
			setAllLedsRed();
			break;
		case 2:
			setAllLedsGreen();
			break;
		case 3:
			setAllLedsCyan();
			break;
		case 4:
			setAllLedsMagenta();
			break;
		case 5:
			setAllLedsYellow();
			break;
		case 6:
			setAllLedsWhite();
			break;
		}

	}

	@Override
	public String getAnimationName() {
		return "Ebenen auf und ab";
	}

	@Override
	public void reset() {

		setAllLedsWhite();
		STATE = COUNT_UP;
		milliSecondsToRun = 10000;
	}

	@Override
	void loop() {
		// TODO Auto-generated method stub
		
	}

}
