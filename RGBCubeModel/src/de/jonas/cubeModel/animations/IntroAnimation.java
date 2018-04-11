package de.jonas.cubeModel.animations;

import de.jonas.cubeModel.Cube;

public class IntroAnimation extends Animation {

	private int previousColor = 0;
	private int seconds = 0;
	private boolean heartbeat;

	public IntroAnimation(Cube cubes) {
		super(cubes);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void animate200ms() {
		turnAllLayersOff();
	};

	@Override
	public void animate1000ms() {
		seconds++;
		if (seconds < 2)
			return;
		randomizeColor();
		clearAllLeds();
		lightCube();
		turnAllLayersOn();
		seconds = 0;
		heartbeat = true;
	}

	private void lightCube() {
		switch (previousColor) {
		case 0:
			setAllLedsRed();
			break;
		case 1:
			setAllLedsGreen();
			break;
		case 2:
			setAllLedsBlue();
			break;
		case 3:
			setAllLedsWhite();
			break;
		case 4:
			setAllLedsCyan();
			break;
		case 5:
			setAllLedsMagenta();
			break;
		case 6:
			setAllLedsYellow();
			break;
		default:
			break;
		}
	}
	
	public void animate500ms() {
		if(heartbeat) {
		lightCube();
		turnAllLayersOn();
		heartbeat = false;
		}
	}

	private void randomizeColor() {
		int r = rand.nextInt(7);
		while (r == previousColor)
			r = rand.nextInt(7);
		previousColor = r;
		
	};

	@Override
	public String getAnimationName() {
		return "Intro";
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub

	}

	@Override
	void loop() {
		// TODO Auto-generated method stub
		
	}

}
