package de.jonas.cubeModel.scheduling;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import de.jonas.cubeModel.Cube;
import de.jonas.cubeModel.animations.Animation;
import de.jonas.cubeModel.mediaPlayback.AudioPlayer;

/**
 * The AnimationScheduler schedules the execution of {@link Animation}s.
 * Animations need to be registered (that is, added) via
 * {@link #addAnimation(Animation)} to the scheduler in order to be played.
 * Animations are always executed (that is, animated) in the order of
 * registration. The AnimationScheduler also handles the playback of sound if an
 * Animation requires that.
 * <p>
 * This class is observable and notifies its observers whenever its
 * {@link SchedulingState} changes.
 * 
 * @author Jonas
 *
 */
public class AnimationScheduler extends Observable implements Runnable {
	private static final Logger log = Logger.getLogger(AnimationScheduler.class.getName());

	private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

	private List<Animation> allAnimations;
	private ScheduledFuture<?> future;

	private AudioPlayer player;
	private SchedulingState state;
	private Animation currentAnimation;

	private Cube cube;

	public AnimationScheduler(AudioPlayer player, Cube cube) {
		allAnimations = new ArrayList<Animation>();
		this.player = player;
		this.state = SchedulingState.STOPPED;
		this.cube = cube;
	}

	/**
	 * Adds an animation which is executed by the AnimationScheduler on the
	 * RGB-Cube
	 *
	 * @param animation
	 *            the animation to be added
	 */
	public void addAnimation(Animation animation) {
		animation.reset(); // make sure animation is in its initial state
		allAnimations.add(animation);

	}

	/**
	 * Starts the scheduler. Before, clients should have registered at least one
	 * {@link Animation}.
	 */
	public void start() {
		future = scheduler.scheduleAtFixedRate(this, 1, 1, TimeUnit.MILLISECONDS);
		state = SchedulingState.PLAYING;
		setChanged();
		notifyObservers(state);
	}

	private void resetScheduler() {
		cancelRunner();
		allAnimations.clear();
		currentAnimation = null;
		future = null;
		player.stop();
		state = SchedulingState.STOPPED;
	}

	/**
	 * Stops the scheduler. All formerly registered animations are deregistered.
	 */
	public void stop() {
		resetScheduler();
		setChanged();
		notifyObservers(state);
	}

	private void cancelRunner() {
		if (future != null && !future.isCancelled() && !future.isDone())
			future.cancel(true);
	}

	/**
	 * 
	 * @return the state the scheduler is currently in
	 * @see SchedulingState
	 */
	public SchedulingState getState() {
		return state;
	}

	/**
	 * @return the number of animations that are registered for execution on the
	 *         cube
	 */
	public int getNumberOfRegisteredAnimations() {
		return allAnimations.size();
	}

	private boolean isNewAnimation(Animation toAnimate) {
		if (currentAnimation == null || !currentAnimation.equals(toAnimate) || !currentAnimation.isRunning()) {
			currentAnimation = toAnimate;
			return true;
		}
		return false;

	}

	private Animation findCurrentAnimation() {
		for (Animation cur : allAnimations)
			if (cur.shouldRun())
				return cur;
		return null;
	}

	@Override
	public void run() {
		Animation toAnimate = findCurrentAnimation();
		if (toAnimate == null) {
			stop();
			return;
		}
		if (isNewAnimation(toAnimate)) {
			log.info("switching to " + toAnimate);
			player.stop();
			player.play(toAnimate.getAudioFileName());
			new Thread(toAnimate).start();
		}
		toAnimate.clockTick();
		cube.parse(toAnimate.getByteStream());
		toAnimate.sendToCube(1);
	}

}
