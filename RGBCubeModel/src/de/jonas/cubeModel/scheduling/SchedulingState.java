package de.jonas.cubeModel.scheduling;

/**
 * There are several states in which the {@link AnimationScheduler} can be in.
 * 
 * @author Jonas
 *
 */
public enum SchedulingState {

	/**
	 * Indicates that at least one Animation is registered and currently played
	 */
	PLAYING("playing"), /**
						 * Indicates that the scheduler is/was stopped, no
						 * animations are played or registered. This is the
						 * inital state.
						 */
	STOPPED("stopped"), /**
						 * Indicates that the scheduler was paused. There are
						 * Animations still registered and playing can be
						 * continued from where it has been paused
						 */
	PAUSED("paused");
	private String state;

	private SchedulingState(String state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return state;
	}
}
