package de.jonas.cubeModel.animations;

import de.jonas.cubeModel.Constants;
import de.jonas.cubeModel.Cube;

public class RainfallAnimation extends Animation {

	private int layerToLight = 7;
	private boolean multiplex;
	private int row, column;

	public RainfallAnimation(Cube cubes) {
		super(cubes);
		turnAllLayersOff();

		turnLayerOn(7);
		setAllLedsWhite();
	}

	@Override
	public void animate1ms() {
		if (multiplex) {
			clearAllLeds();
			setLedWhite(row, column);
			turnLayerOff(layerToLight + 1);
			turnLayerOn(layerToLight);
		} else {
			setAllLedsWhite();
			turnLayerOff(layerToLight);
			turnLayerOn(7);
		}
		multiplex = !multiplex;

	}

	@Override
	public void animate50ms() {

	}

	@Override
	public void animate100ms() {
		if (layerToLight == 0) {
			turnLayerOff(layerToLight + 1);
			layerToLight = rand.nextInt(15) + 10;
			row = rand.nextInt(Constants.numberOfLedsInRow);
			column = rand.nextInt(Constants.numberOfLedsInColumn);
		}
		layerToLight--;

	}

	@Override
	public void animate200ms() {

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
	public String getAnimationName() {
		return "Regenschauer";
	}

	@Override
	public String getAudioFileName() {
		return "bla.mp3";
	}

	@Override
	public void reset() {
		milliSecondsToRun = INFINITY;
		turnAllLayersOff();

		turnLayerOn(7);
		setAllLedsWhite();
		
	}

	@Override
	void loop() {
		// TODO Auto-generated method stub
		
	}

}
