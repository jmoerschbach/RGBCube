package de.jonas.rgbcubecontrol.ui

import de.jonas.rgbcubecontrol.domain.animations.Animation

data class AnimationList(val animationItems: List<AnimationItem>) {
    val size: Int
        get() = animationItems.size

    operator fun get(position: Int) = animationItems[position]

    fun deselectAll() {
        animationItems.forEach { it.isSelected = false }
    }
}

data class AnimationItem(val animation: Animation, var isSelected: Boolean = false, var isPlaying: Boolean = false)