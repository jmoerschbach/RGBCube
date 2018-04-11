package de.jonas.cubeModel.rendering.models;

import com.threed.jpct.World;

import de.jonas.cubeModel.Constants;

public class AnodeModel {

	private LedModel[] leds;

	public AnodeModel() {
		leds = new LedModel[Constants.numberOfLedsInRow];
		createLeds();
	}

	private void createLeds() {
		for (int i = 0; i < leds.length; i++) {
			leds[i] = new LedModel();
		}
	}

	void addAnodeTo(World world, int column, int level) {
		for (int j = 0; j < Constants.numberOfLedsInRow; j++) {
			leds[j].addLedToWorld(world);
			leds[j].translate(-column * Constants.distanceBetweenLeds, level * Constants.distanceBetweenLeds,
					j * Constants.distanceBetweenLeds);
		}
	}

	void turnOn() {
		for (int i = 0; i < leds.length; i++) {
			leds[i].turnOn();
		}
	}

	void turnOff() {
		for (int i = 0; i < leds.length; i++) {
			leds[i].turnOff();
		}
	}

	public void parse(byte blue, byte green, byte red) {
		for (int i = 0; i < 8; i++) {
			boolean b = (blue & (1 << i)) != 0;
			boolean g = (green & (1 << i)) != 0;
			boolean r = (red & (1 << i)) != 0;
			leds[i].mixColors(r, g, b);
		}

	}

}
