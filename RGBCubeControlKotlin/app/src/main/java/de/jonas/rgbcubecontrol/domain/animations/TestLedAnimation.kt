package de.jonas.rgbcubecontrol.domain.animations

import de.jonas.rgbcubecontrol.domain.Constants

class TestLedAnimation : Animation() {
    private var row: Int = 0
    private var column: Int = 0
    private var color: Int = 0
    override fun reset() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override val animationName: String
        get() = "TestLedAnimation"

    override fun animate500ms() {
        clearLed(row, column)
        if (column < Constants.numberOfLedsInColumn)
            column++
        else {
            column = 0
            row++
        }
        if (row >= Constants.numberOfLedsInRow) {
            row = 0;
            color++
        }

        turnAllLayersOn()
        if (color == 0)
            setLedBlue(row, column)
        else if (color == 1)
            setLedRed(row, column)
        else if (color ==2)
            setLedGreen(row, column)
        else color = 0
    }
}