package de.jonas.cubeModel.rendering.models;

import com.threed.jpct.World;

import de.jonas.cubeModel.Constants;

public class LayerModel {

	private AnodeModel anodes[];

	public LayerModel() {
		anodes = new AnodeModel[Constants.numberOfLedsInRow];
		createAnodes();
	}

	private void createAnodes() {
		for (int i = 0; i < Constants.numberOfLedsInRow; i++) {
			anodes[i] = new AnodeModel();
		}
	}

	void addLayerTo(World world, int level) {

		for (int j = 0; j < Constants.numberOfLedsInRow; j++) {
			anodes[j].addAnodeTo(world, j, level);

		}
	}

	void turnOn() {
		for (int i = 0; i < anodes.length; i++) {
			anodes[i].turnOn();
		}
	}

	void turnOff() {
		for (int i = 0; i < anodes.length; i++) {
			anodes[i].turnOff();
		}
	}

	void parse(byte[] byteStream) {
		int anodeCounter = 0;
		for (int i = 0; i < byteStream.length; i += 3) {
			byte blue = byteStream[i + 2];
			byte green = byteStream[i + 1];
			byte red = byteStream[i];

			anodes[anodeCounter].parse(blue, green, red);
			anodeCounter++;
		}
	}

}
