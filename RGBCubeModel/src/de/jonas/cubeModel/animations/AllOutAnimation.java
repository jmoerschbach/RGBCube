package de.jonas.cubeModel.animations;

import de.jonas.cubeModel.Cube;

public class AllOutAnimation extends Animation {

	private boolean multiplex;

	public AllOutAnimation(Cube cubes) {
		super(cubes);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getAnimationName() {
		return "allOn";
	}
	
	@Override
	public void animate1ms() {
//		if(multiplex){
//			turnAllLayersOff();
//		}
//		else
//			turnAllLayersOn();
//
//		multiplex =!multiplex;
	}

	@Override
	public void reset() {
		clearAllLeds();
		turnAllLayersOn();

	}

	@Override
	void loop() {
		// TODO Auto-generated method stub
		
	}

}
