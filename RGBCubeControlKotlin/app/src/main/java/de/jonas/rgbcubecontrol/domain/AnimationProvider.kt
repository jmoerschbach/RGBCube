package de.jonas.rgbcubecontrol.domain

import de.jonas.rgbcubecontrol.domain.animations.*
import java.util.Arrays.asList

class AnimationProvider {

    fun getAvailableAnimations(): List<Animation> {
        return listOf(SimpleMultiplexAnimation(), AllLayersOnOffAnimation(), TestLedAnimation(), RainfallAnimation()).sortedBy { it.animationName }
    }
}