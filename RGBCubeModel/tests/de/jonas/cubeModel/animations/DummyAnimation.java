package de.jonas.cubeModel.animations;

import de.jonas.cubeModel.Cube;

public class DummyAnimation extends Animation {

	public DummyAnimation(Cube cube) {
		super(cube);
	}

	private String description;
	public int animated1ms, animated2ms, animated5ms, animated10ms, animated25ms, animated50ms, animated100ms,
			animated200ms, animated500ms, animated1000ms;

	@Override
	public void animate1ms() {
		description = "1";
		animated1ms++;

	}

	@Override
	public void animate2ms() {
		description = "2";
		animated2ms++;
	}

	@Override
	public void animate5ms() {
		description = "5";
		animated5ms++;
	}

	@Override
	public void animate10ms() {
		description = "10";
		animated10ms++;
	}

	@Override
	public void animate25ms() {
		description = "25";
		animated25ms++;
	}

	@Override
	public void animate50ms() {
		description = "50";
		animated50ms++;
	}

	@Override
	public void animate100ms() {
		description = "100";
		animated100ms++;
	}

	@Override
	public void animate200ms() {
		description = "200";
		animated200ms++;
	}

	@Override
	public void animate500ms() {
		description = "500";
		animated500ms++;
	}

	@Override
	public void animate1000ms() {
		description = "1000";
		animated1000ms++;
	}

	public String toString() {
		return this.description;
	}

	@Override
	public String getAnimationName() {
		return "DummyAnimation";
	}

	@Override
	public void reset() {
		animated1ms = 0;
		animated2ms = 0;
		animated5ms = 0;
		animated10ms = 0;
		animated25ms = 0;
		animated50ms = 0;
		animated100ms = 0;
		animated200ms = 0;
		animated1000ms = 0;

	}

	@Override
	void loop() {
		// TODO Auto-generated method stub
		
	}

}
