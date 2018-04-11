package de.jonas.cubeModel.rendering.models;

import com.threed.jpct.Object3D;
import com.threed.jpct.RGBColor;
import com.threed.jpct.SimpleVector;
import com.threed.jpct.World;
import com.threed.jpct.util.ExtendedPrimitives;

public class LedModel {

	private Object3D led;
	private RGBColor color;

	public LedModel() {
		// important to use ExtendedPrimitives, because setting number of quads
		// to < 10 increases performance drastically!
		led = ExtendedPrimitives.createEllipsoid(new SimpleVector(2, 2, 2), 8);
		led.setEnvmapped(Object3D.ENVMAP_ENABLED);
		turnOff();
		led.build();

	}

	void mixColors(boolean r, boolean g, boolean b) {
		color = new RGBColor(0, 0, 0);
		if (r)
			color = new RGBColor(255, 0, 0);
		if (g)
			color = new RGBColor(0, 255, 0);
		if (b)
			color = new RGBColor(0, 0, 255);
		if (r && g)
			color = new RGBColor(255, 255, 0);
		if (r && b)
			color = new RGBColor(255, 0, 255);
		if (g && b)
			color = new RGBColor(0, 255, 255);
		if (r && g && b)
			color = new RGBColor(255, 255, 255);
	}

	/**
	 * exists for unit-testing purposes only!
	 * 
	 * @return
	 */
	 RGBColor getCurrentColor() {
		return color;
	}

	void turnOn() {
		led.setAdditionalColor(color);
	}

	void turnOff() {
		color = new RGBColor(0, 0, 0);
		led.setAdditionalColor(color);
	}

	void addLedToWorld(World world) {
		world.addObject(led);
	}

	void translate(float x, float y, float z) {
		led.translate(x, y, z);
	}

}
