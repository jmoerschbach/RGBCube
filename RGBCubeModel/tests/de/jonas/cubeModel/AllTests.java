package de.jonas.cubeModel;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import de.jonas.cubeModel.animations.AnimationTest;
import de.jonas.cubeModel.animations.DiceAnimationTest;
import de.jonas.cubeModel.rendering.models.LedModelTest;
import de.jonas.cubeModel.scheduling.AnimationSchedulerTest;

@RunWith(Suite.class)
@SuiteClasses({ AnimationTest.class, LedModelTest.class, AnimationSchedulerTest.class,
		DiceAnimationTest.class })
public class AllTests {

}
