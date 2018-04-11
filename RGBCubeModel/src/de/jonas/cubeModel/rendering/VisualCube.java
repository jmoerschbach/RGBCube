package de.jonas.cubeModel.rendering;

import com.threed.jpct.World;

import de.jonas.cubeModel.Cube;

/**
 * This is a cube which can be visualized in 3D by adding it to a {@link com.threed.jpct.World}
 * @author jomo894
 *
 */
public interface VisualCube extends Cube {


	/** The world this cube shall be added to for visualization
	 * 
	 * @param world
	 */
	void addCubeToWorld(World world);

}