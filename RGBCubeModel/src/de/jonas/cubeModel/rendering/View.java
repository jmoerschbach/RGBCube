package de.jonas.cubeModel.rendering;

import com.threed.jpct.Config;
import com.threed.jpct.FrameBuffer;
import com.threed.jpct.IRenderer;
import com.threed.jpct.Object3D;
import com.threed.jpct.Primitives;
import com.threed.jpct.World;

public class View {

	private World world;
	private FrameBuffer buffer;
	private CameraAdjustment camAdjustment;

	public View() {
		Config.useMultipleThreads = true;
		world = new World();

		world.setAmbientLight(30, 30, 30);

	}

	public void init(VisualCube cube) {
		buffer = new FrameBuffer(800, 600, FrameBuffer.SAMPLINGMODE_NORMAL);
		buffer.disableRenderer(IRenderer.RENDERER_SOFTWARE);
		buffer.enableRenderer(IRenderer.RENDERER_OPENGL);
		
		
		Object3D dummy = Primitives.getPyramide(0, 1);
		cube.addCubeToWorld(world);
		world.addObject(dummy);
		world.getCamera().setPosition(50, -50, 0);
		world.getCamera().lookAt(dummy.getTransformedCenter());

		camAdjustment = new CameraAdjustment(world, buffer);
	}

	public void gameLoop() throws InterruptedException {

		while (!org.lwjgl.opengl.Display.isCloseRequested()
				&& !camAdjustment.escapePressed()) {
			camAdjustment.pollControls();
			camAdjustment.adjust();
			buffer.clear();
			world.renderScene(buffer);
			world.draw(buffer);
			buffer.update();
			buffer.displayGLOnly();

		}

		buffer.disableRenderer(IRenderer.RENDERER_OPENGL);
		buffer.dispose();
	}

}
