package de.jonas.rgbcubecontrol.domain.animations

class AllLayersOnOffAnimation : Animation() {
    override fun reset() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override val animationName: String
        get() = "AllLayersOnOff"

    private var on: Boolean = false

    override fun animate1000ms() {
        clearAllLeds()
        if (on) turnAllLayersOff()
        else turnAllLayersOn()
        on = !on

    }
}