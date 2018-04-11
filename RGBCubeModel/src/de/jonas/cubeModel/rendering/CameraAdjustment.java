package de.jonas.cubeModel.rendering;

import java.awt.event.KeyEvent;

import com.threed.jpct.Camera;
import com.threed.jpct.FrameBuffer;
import com.threed.jpct.Matrix;
import com.threed.jpct.SimpleVector;
import com.threed.jpct.World;
import com.threed.jpct.util.KeyMapper;
import com.threed.jpct.util.KeyState;

/**
 * This class encapsulates the logic for adjusting the camera. The camera may be
 * moved or rotated depending on user input via keyboard and mouse.
 * 
 * @author Jonas
 *
 */
public class CameraAdjustment {
	private boolean forward;
	private boolean backward;
	private boolean left;
	private boolean right;
	private boolean up;
	private boolean down;
	private KeyMapper keyMapper;
	private MouseMapper mouseMapper;
	private float xAngle = 0;
	private World world;
	private boolean escapePressed;

	public CameraAdjustment(World world, FrameBuffer buffer) {
		this.world = world;
		keyMapper = new KeyMapper();
		mouseMapper = new MouseMapper(buffer);
		mouseMapper.hide();
	}

	/**
	 * 
	 * @return true if escape button was pressed
	 */
	boolean escapePressed() {
		return escapePressed;
	}

	/**
	 * checks if the user hit some keys on the keyboard. Arrow buttons
	 * (up/down/left/right), page up/down and escape buttons are checked
	 */
	void pollControls() {

		KeyState ks = null;
		while ((ks = keyMapper.poll()) != KeyState.NONE) {
			escapePressed = ks.getKeyCode() == KeyEvent.VK_ESCAPE;

			if (ks.getKeyCode() == KeyEvent.VK_UP) {
				forward = ks.getState();
			}

			if (ks.getKeyCode() == KeyEvent.VK_DOWN) {
				backward = ks.getState();
			}

			if (ks.getKeyCode() == KeyEvent.VK_LEFT) {
				left = ks.getState();
			}

			if (ks.getKeyCode() == KeyEvent.VK_RIGHT) {
				right = ks.getState();
			}

			if (ks.getKeyCode() == KeyEvent.VK_PAGE_UP) {
				up = ks.getState();
			}

			if (ks.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
				down = ks.getState();
			}
		}
	}

	private void moveCamera(long ticks) {

		SimpleVector ellipsoid = new SimpleVector(5, 5, 5);

		if (forward) {
			world.checkCameraCollisionEllipsoid(Camera.CAMERA_MOVEIN,
					ellipsoid, ticks, 5);
		}

		if (backward) {
			world.checkCameraCollisionEllipsoid(Camera.CAMERA_MOVEOUT,
					ellipsoid, ticks, 5);
		}

		if (left) {
			world.checkCameraCollisionEllipsoid(Camera.CAMERA_MOVELEFT,
					ellipsoid, ticks, 5);
		}

		if (right) {
			world.checkCameraCollisionEllipsoid(Camera.CAMERA_MOVERIGHT,
					ellipsoid, ticks, 5);
		}

		if (up) {
			world.checkCameraCollisionEllipsoid(Camera.CAMERA_MOVEUP,
					ellipsoid, ticks, 5);
		}

		if (down) {
			world.checkCameraCollisionEllipsoid(Camera.CAMERA_MOVEDOWN,
					ellipsoid, ticks, 5);
		}

	}

	/**
	 * adjusts the camera meaning it may be rotated or moved
	 */
	void adjust() {

		moveCamera(5);
		rotateCamera(1);

	}

	private void rotateCamera(long ticks) {

		// if (!mouseMapper.buttonDown(0))
		// return;

		Matrix rot = world.getCamera().getBack();
		int dx = mouseMapper.getDeltaX();
		int dy = mouseMapper.getDeltaY();

		float ts = 0.2f * ticks;
		float tsy = ts;

		if (dx != 0) {
			ts = dx / 500f;
		}
		if (dy != 0) {
			tsy = dy / 500f;
		}

		if (dx != 0) {
			rot.rotateAxis(rot.getYAxis(), ts);
		}

		if ((dy > 0 && xAngle < Math.PI / 4.2)
				|| (dy < 0 && xAngle > -Math.PI / 4.2)) {
			rot.rotateX(tsy);
			xAngle += tsy;
		}
	}

}
