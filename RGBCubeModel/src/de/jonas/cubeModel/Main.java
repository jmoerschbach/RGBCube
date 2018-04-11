package de.jonas.cubeModel;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import de.jonas.cubeModel.animations.Animation;
import de.jonas.cubeModel.animations.CompositeAnimation;
import de.jonas.cubeModel.animations.RainfallAnimation;
import de.jonas.cubeModel.animations.RandomLedAnimation;
import de.jonas.cubeModel.animations.SimpleMultiplexAnimation;
import de.jonas.cubeModel.mediaPlayback.MP3Player;
import de.jonas.cubeModel.rendering.View;
import de.jonas.cubeModel.rendering.VisualCube;
import de.jonas.cubeModel.rendering.models.CubeModel;
import de.jonas.cubeModel.scheduling.AnimationScheduler;

public class Main {
	private static final Logger log = Logger.getLogger(MP3Player.class.getName());

	public static void main(String[] args) throws InterruptedException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		VisualCube cube = new CubeModel();
		View v = new View();
		v.init(cube);
		AnimationScheduler scheduler = new AnimationScheduler(new MP3Player(), cube);
		// findAndAddAllAnimations(scheduler, cube);

		// Cube c = new SerialCube();

		// scheduler.addAnimation(new DummyAnimation(cube));

		// scheduler.addAnimation(new RainfallAnimation(cube));
		// scheduler.addAnimation(new RainfallAnimation(cube));
		// scheduler.addAnimation(new RandomLedAnimation(cube));
		// scheduler.addAnimation(new LayerUpDownAnimation(cube));

		scheduler.addAnimation(new SimpleMultiplexAnimation(cube));
		scheduler.start();
		//

		v.gameLoop();

		System.exit(0); // to dispose timer task...
	}

	private static void findAndAddAllAnimations(AnimationScheduler scheduler, VisualCube cube)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		List<ClassLoader> classLoadersList = new LinkedList<ClassLoader>();
		classLoadersList.add(ClasspathHelper.contextClassLoader());
		classLoadersList.add(ClasspathHelper.staticClassLoader());

		Reflections reflections = new Reflections(new ConfigurationBuilder()
				.setScanners(new SubTypesScanner(
						false /* don't exclude Object.class */), new ResourcesScanner())
				.setUrls(ClasspathHelper.forClassLoader(classLoadersList.toArray(new ClassLoader[0])))
				.filterInputsBy(new FilterBuilder().include(FilterBuilder.prefix("de.jonas.cubeModel.animations"))));

		Set<Class<? extends Animation>> classes = reflections.getSubTypesOf(Animation.class);
		log.info("found " + classes.size() + " animations.");
		Iterator<Class<? extends Animation>> it = classes.iterator();
		while (it.hasNext()) {
			Class<? extends Animation> current = it.next();
			Constructor<? extends Animation> cons = current.getDeclaredConstructor(Cube.class);
			Animation currentAnimation = cons.newInstance(new Object[] { cube });
			if (!currentAnimation.getAnimationName().equalsIgnoreCase("DummyAnimation")) {
				scheduler.addAnimation(currentAnimation);
				log.info("Added " + currentAnimation);
			}

		}

	}

}