package de.jonas.rgbcubecontrol.ui

import de.jonas.rgbcubecontrol.domain.AnimationProvider
import java.util.stream.Collectors.toList

class AnimationItemProvider(private val animationProvider: AnimationProvider = AnimationProvider()) {

    fun getAllAnimationItems(): AnimationList {
        val animationItems = animationProvider.getAvailableAnimations().stream().map { AnimationItem(it) }.collect(toList())
        return AnimationList(animationItems)
    }
}

