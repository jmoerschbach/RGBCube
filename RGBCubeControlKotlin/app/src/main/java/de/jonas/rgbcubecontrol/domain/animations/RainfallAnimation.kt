package de.jonas.rgbcubecontrol.domain.animations

import de.jonas.rgbcubecontrol.domain.Constants

class RainfallAnimation : Animation() {

    private var layerToLight = 7
    private var multiplex: Boolean = false
    private var row: Int = 0
    private var column: Int = 0

    override val animationName: String
        get() = "Rainfall"

    init {
        turnAllLayersOff()

        turnLayerOn(7)
        setAllLedsWhite()
    }

    override fun animate2ms() {
        if (multiplex) {
            clearAllLeds()
            setLedWhite(row, column)
            turnLayerOff(layerToLight + 1)
            turnLayerOn(layerToLight)
        } else {
            setAllLedsWhite()
            turnLayerOff(layerToLight)
            turnLayerOn(7)
        }
        multiplex = !multiplex

    }



   override fun animate100ms() {
        if (layerToLight == 0) {
            turnLayerOff(layerToLight + 1)
            layerToLight = rand.nextInt(15) + 10
            row = rand.nextInt(Constants.numberOfLedsInRow)
            column = rand.nextInt(Constants.numberOfLedsInColumn)
        }
        layerToLight--

    }



    override fun reset() {
        turnAllLayersOff()

        turnLayerOn(7)
        setAllLedsWhite()

    }
}