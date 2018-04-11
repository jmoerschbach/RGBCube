package de.jonas.cubeModel.animations;

import de.jonas.cubeModel.Constants;
import de.jonas.cubeModel.Cube;

public class RandomLedAnimation extends Animation {

	private int minimumLedsToLight = 20;
	private final int timeToRun = 1000;

	public RandomLedAnimation(Cube cubes) {
		super(cubes);
	}

	@Override
	public void animate10ms() {

	}

	@Override
	public void animate50ms() {
		// TODO Auto-generated method stub

	}

	@Override
	public void animate100ms() {

	}

	@Override
	public void animate200ms() {
		turnAllLayersOff();
		clearAllLeds();
		int ledsOn = 0;
		while (ledsOn < minimumLedsToLight) {
			if (rand.nextBoolean()) {
				setLedBlue(rand.nextInt(Constants.numberOfLedsInRow), rand.nextInt(Constants.numberOfLedsInColumn));
				ledsOn++;
			}
			if (rand.nextBoolean()) {
				setLedRed(rand.nextInt(Constants.numberOfLedsInRow), rand.nextInt(Constants.numberOfLedsInColumn));
				ledsOn++;
			}
			if (rand.nextBoolean()) {
				setLedGreen(rand.nextInt(Constants.numberOfLedsInRow), rand.nextInt(Constants.numberOfLedsInColumn));
				ledsOn++;
			}

		}
		int layerToSet = rand.nextInt(Constants.numberOfLedsInHeight);
		int layerToReset = rand.nextInt(Constants.numberOfLedsInHeight);
		turnLayerOn(layerToSet);
		if (layerToReset != layerToSet)
			turnLayerOff(layerToReset);

	}

	@Override
	public void animate500ms() {
		// TODO Auto-generated method stub

	}

	@Override
	public void animate1000ms() {
		// TODO Auto-generated method stub

	}

	@Override
	public String getAudioFileName() {
		return "randomled";
	}

	@Override
	public String getAnimationName() {
		return "zufaellige LED";
	}

	@Override
	public void reset() {
		//milliSecondsToRun = timeToRun;
		milliSecondsToRun = INFINITY;

	}

	@Override
	void loop() {
		// TODO Auto-generated method stub
		
	}

}
