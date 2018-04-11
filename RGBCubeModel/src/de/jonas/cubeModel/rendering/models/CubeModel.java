package de.jonas.cubeModel.rendering.models;

import com.threed.jpct.Object3D;
import com.threed.jpct.RGBColor;
import com.threed.jpct.World;
import com.threed.jpct.util.ExtendedPrimitives;

import de.jonas.cubeModel.Constants;
import de.jonas.cubeModel.rendering.VisualCube;

public class CubeModel implements VisualCube {

	private LayerModel[] layers;
	private Object3D plane;

	public CubeModel() {
		layers = new LayerModel[Constants.numberOfLedsInRow];
		createPlane();
		createLayers();
	}

	private void createLayers() {
		for (int i = 0; i < layers.length; i++) {
			layers[i] = new LayerModel();
		}
	}

	private void createPlane() {
		plane = ExtendedPrimitives.createPlane(8, 10);
		plane.setAdditionalColor(new RGBColor(100, 100, 100));
		plane.setCollisionMode(Object3D.COLLISION_CHECK_OTHERS);
		plane.translate(-25, 60, 30);
	}

	/* (non-Javadoc)
	 * @see de.jonas.cubeModel.models.Cube#addCubeToWorld(com.threed.jpct.World)
	 */
	@Override
	public void addCubeToWorld(World world) {

		addLayers(world);
		addPlane(world);
	}

	private void addPlane(World world) {
		world.addObject(plane);

	}

	private void addLayers(World world) {
		for (int i = 0; i < layers.length; i++) {
			layers[i].addLayerTo(world, i);
		}
	}

	void turnLayerOn(int layerNumber) {
		if (layerNumber >= 0 && layerNumber < layers.length) {
			layers[layerNumber].turnOn();
		}
	}

	void turnLayerOff(int layerNumber) {
		if (layerNumber >= 0 && layerNumber < layers.length) {
			layers[layerNumber].turnOff();
		}
	}

	void turnAllLayersOn() {
		for (int i = 0; i < layers.length; i++)
			turnLayerOn(i);
	}

	void turnAllLayersOff() {
		for (int i = 0; i < layers.length; i++)
			turnLayerOff(i);
	}

	/* (non-Javadoc)
	 * @see de.jonas.cubeModel.models.Cube#parse(byte[])
	 */
	@Override
	public void parse(byte[] byteStream) {
		byte anodes = byteStream[0];
		byte[] cathodes = new byte[24];
		System.arraycopy(byteStream, 1, cathodes, 0, 24);

		for (int i = 0; i < layers.length; i++) {
			layers[i].parse(cathodes);
		}
		turnLayerOnOff(anodes);
	}

	private void turnLayerOnOff(byte anodes) {
		for (int i = 0; i < 8; i++) {
			if ((anodes & (1 << i)) != 0) {
				turnLayerOn(7-i);
			} else {
				turnLayerOff(7-i);
			}
		}

	}

}
