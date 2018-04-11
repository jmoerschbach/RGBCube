package de.jonas.cubeModel.animations;

import de.jonas.cubeModel.Cube;

public abstract class CompositeAnimation extends Animation {

	protected Animation currentAnimation;

	public CompositeAnimation(Cube cubes) {
		super(cubes);
	}

	public void animate10ms() {
		currentAnimation.animate10ms();
	}

	public void animate50ms() {
		currentAnimation.animate50ms();
	}

	public void animate100ms() {
		currentAnimation.animate100ms();
	}

	public void animate200ms() {
		currentAnimation.animate200ms();
	}

	public void animate500ms() {
		currentAnimation.animate500ms();
	}

	public void animate1000ms() {
		currentAnimation.animate1000ms();
	}

	protected final void switchAnimationTo(Animation newAnimation) {
		currentAnimation = newAnimation;
		byteStream = currentAnimation.byteStream;
		currentAnimation.reset();
	}

}
