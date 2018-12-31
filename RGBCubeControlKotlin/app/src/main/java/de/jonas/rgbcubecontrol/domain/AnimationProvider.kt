package de.jonas.rgbcubecontrol.domain

import de.jonas.rgbcubecontrol.domain.animations.AllLayersOnOffAnimation
import de.jonas.rgbcubecontrol.domain.animations.Animation
import de.jonas.rgbcubecontrol.domain.animations.SimpleMultiplexAnimation
import de.jonas.rgbcubecontrol.domain.animations.TestLedAnimation
import java.util.Arrays.asList

class AnimationProvider {

    fun getAvailableAnimations() : List<Animation> = asList(SimpleMultiplexAnimation(), AllLayersOnOffAnimation(), TestLedAnimation())
}