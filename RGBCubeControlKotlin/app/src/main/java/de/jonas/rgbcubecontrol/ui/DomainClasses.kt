package de.jonas.rgbcubecontrol.ui

import de.jonas.rgbcubecontrol.domain.animations.Animation

data class AnimationList(val animationItems: List<AnimationItem>) {
    val size: Int
        get() = animationItems.size

    operator fun get(position: Int) = animationItems[position]
}

data class AnimationItem(val animation: Animation, val isSelected: Boolean)