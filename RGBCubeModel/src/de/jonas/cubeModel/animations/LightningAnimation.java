package de.jonas.cubeModel.animations;

import de.jonas.cubeModel.Constants;
import de.jonas.cubeModel.Cube;

public class LightningAnimation extends Animation {

	private int toLight = 0;
	boolean forward = true;

	public LightningAnimation(Cube cubes) {
		super(cubes);
	}

	public void animate50ms() {

		
	}

	@Override
	public String getAnimationName() {
		return "Lightning";
	}

	@Override
	public void reset() {
		turnLayerOn(0);
		turnLayerOn(1);

	}

	@Override
	void loop() {
		if (forward)
			toLight++;
		else
			toLight--;
		setLedBlue(7, toLight);
		delay(50);
		if (forward)
			clearLed(7, toLight - 3);
		else
			clearLed(7, toLight + 3);
		delay(50);
		if (toLight > Constants.numberOfLedsInColumn + 3)
			forward = false;
		if (toLight < -3)
			forward = true;
		
	}

}
