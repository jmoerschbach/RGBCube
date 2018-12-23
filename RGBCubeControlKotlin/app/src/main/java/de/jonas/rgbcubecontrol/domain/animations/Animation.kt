package de.jonas.rgbcubecontrol.domain.animations


import java.util.ArrayList
import java.util.Observable
import java.util.Observer
import java.util.Random


import de.jonas.rgbcubecontrol.domain.Constants
import kotlin.experimental.and
import kotlin.experimental.or

/**
 * This is the base class for all animations that shall run on the RGB-Cube.
 * Every animation needs to manipulate the [byteStream], which is shifted
 * out to the Cube. This class provides many helper functions to manipulate the
 * byteStream very comfortably on a high abstraction layer.
 *
 *
 * To create a new animation, simply extend this class and implement your
 * animating algorithm. An Animation runs infinitely per default. However, you
 * can (and you should) restrict the runtime of your animation by setting
 * [milliSecondsToRun] to an appropriate value in order to allow
 * subsequent animations to start.
 *
 *
 * **Do not forget to add your animation's name to [.ANIMATIONS]
 * !**
 *
 * @author Jonas
 */
abstract class Animation() : Observer {


    var byteStream: ByteArray
        protected set

    protected var rand: Random
    //    private val allCubes: MutableList<Cube>
    protected var milliSecondsToRun: Long = 0

    var isRunning: Boolean = false
        private set

    private var clock: Int = 0

    /**
     *
     * @return the name of this animation
     */
    abstract val animationName: String

    /**
     *
     * @return the name of the audio file that is to be played.<br></br>
     * **Note: No file type ending permitted (instead of "bla.mp3"
     * return "bla")
     ** */
    val audioFileName: String
        get() = ""

    fun shouldRun(): Boolean {
        return if (milliSecondsToRun == INFINITY.toLong()) true else milliSecondsToRun > 0
    }

    init {
        milliSecondsToRun = INFINITY.toLong()
//        allCubes = ArrayList<Cube>()
//        // for (Cube cur : cubes)
//        allCubes.add(cubes)

        this.byteStream = ByteArray(25)
        this.rand = Random()
        clearAllLeds()
        turnAllLayersOff()
    }

    /**
     * turns a layers on (while leaving all other layers as it is), meaning the
     * anodes are set to high. If the number exceeds the limits, nothing is done
     *
     * @param layerNr
     * the layer number in range from 0 to
     * [Constants.numberOfLedsInColumn].
     */
    internal fun turnLayerOn(layerNr: Int) {
        if (layerNr >= 0 && layerNr < Constants.numberOfLedsInColumn)
        // byteStream[0] |= (1 << layerNr);
            setBit(0, layerNr)
    }

    /*
        override fun run() {
            isRunning = true
            while (shouldRun()) {
                loop()
                delay(1)
            }
            isRunning = false
        }

        internal abstract fun loop()

        internal fun delay(ms: Int) {
            try {
                Thread.sleep(ms.toLong())
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

        }
    */
    internal fun turnAllLayersOn() {
        for (i in 0 until Constants.numberOfLedsInColumn)
            turnLayerOn(i)
    }

    /**
     * turns a layers off, meaning the anodes are set to low. If the number
     * exceeds the limits, nothing is done
     *
     * @param layerNr
     * the layer number in range from 0 to
     * [Constants.numberOfLedsInColumn].
     */
    internal fun turnLayerOff(layerNr: Int) {
        if (layerNr >= 0 && layerNr < Constants.numberOfLedsInColumn)
        // byteStream[0] &= ~(1 << layerNr);
            clearBit(0, layerNr)
    }

    internal fun turnAllLayersOff() {
        for (i in 0 until Constants.numberOfLedsInColumn)
            turnLayerOff(i)
    }

    /**
     * clears all bits meaning all LEDs (RGB) are turned off
     */
    internal fun clearAllLeds() {
        for (row in 0 until Constants.numberOfLedsInRow) {
            for (column in 0 until Constants.numberOfLedsInColumn)
                clearLed(row, column)
        }
    }

    /**
     * sets all LEDs to white
     */
    internal fun setAllLedsWhite() {
        for (row in 0 until Constants.numberOfLedsInRow) {
            for (column in 0 until Constants.numberOfLedsInColumn)
                setLedWhite(row, column)
        }
    }

    /**
     * sets all LEDs to blue
     */
    internal fun setAllLedsBlue() {
        for (row in 0 until Constants.numberOfLedsInRow) {
            for (column in 0 until Constants.numberOfLedsInColumn)
                setLedBlue(row, column)
        }
    }

    /**
     * sets all LEDs to red
     */
    internal fun setAllLedsRed() {
        for (row in 0 until Constants.numberOfLedsInRow) {
            for (column in 0 until Constants.numberOfLedsInColumn)
                setLedRed(row, column)
        }
    }

    /**
     * sets all LEDs to green
     */
    internal fun setAllLedsGreen() {
        for (row in 0 until Constants.numberOfLedsInRow) {
            for (column in 0 until Constants.numberOfLedsInColumn)
                setLedGreen(row, column)
        }
    }

    /**
     * sets all LEDs to magenta
     */
    internal fun setAllLedsMagenta() {
        for (row in 0 until Constants.numberOfLedsInRow) {
            for (column in 0 until Constants.numberOfLedsInColumn)
                setLedMagenta(row, column)
        }
    }

    /**
     * sets all LEDs to cyan
     */
    internal fun setAllLedsCyan() {
        for (row in 0 until Constants.numberOfLedsInRow) {
            for (column in 0 until Constants.numberOfLedsInColumn)
                setLedCyan(row, column)
        }
    }

    /**
     * sets all LEDs to yellow
     */
    internal fun setAllLedsYellow() {
        for (row in 0 until Constants.numberOfLedsInRow) {
            for (column in 0 until Constants.numberOfLedsInColumn)
                setLedYellow(row, column)
        }
    }

    /**
     * turns all 3 Leds (R, G, B) of RGB-Led with row- and column-index on
     *
     * @param row
     * the row in which this LED is placed (0 to
     * [Constants.numberOfLedsInRow]
     * @param column
     * the colum in which this LED is placed (0 to
     * [Constants.numberOfLedsInColumn]
     */
    internal fun setLedWhite(row: Int, column: Int) {
        if (!areIndicesValid(row, column))
            return
        setLedBlue(row, column)
        setLedGreen(row, column)
        setLedRed(row, column)
    }

    private fun areIndicesValid(row: Int, column: Int): Boolean {
        return row >= 0 && row < Constants.numberOfLedsInRow && column >= 0 && column < Constants.numberOfLedsInColumn
    }

    internal fun setLedYellow(row: Int, column: Int) {
        if (!areIndicesValid(row, column))
            return
        setLedGreen(row, column)
        setLedRed(row, column)
    }

    internal fun setLedMagenta(row: Int, column: Int) {
        if (!areIndicesValid(row, column))
            return
        setLedBlue(row, column)
        setLedRed(row, column)
    }

    internal fun setLedCyan(row: Int, column: Int) {
        if (!areIndicesValid(row, column))
            return
        setLedBlue(row, column)
        setLedGreen(row, column)
    }

    /**
     * turns all 3 Leds (R, G, B) of RGB-Led with row- and column-index off
     *
     * @param row
     * the row in which this LED is placed (0 to
     * [Constants.numberOfLedsInRow]
     * @param column
     * the colum in which this LED is placed (0 to
     * [Constants.numberOfLedsInColumn]
     */
    internal fun clearLed(row: Int, column: Int) {
        if (!areIndicesValid(row, column))
            return
        clearLedBlue(row, column)
        clearLedGreen(row, column)
        clearLedRed(row, column)
    }

    internal fun setLedBlue(row: Int, column: Int) {
        if (!areIndicesValid(row, column))
            return
        val base = row * 3 + 1
        setBit(base + 2, column)
    }

    internal fun setLedGreen(row: Int, column: Int) {
        if (!areIndicesValid(row, column))
            return
        val base = row * 3 + 1
        setBit(base + 1, column)
    }

    internal fun setLedRed(row: Int, column: Int) {
        if (!areIndicesValid(row, column))
            return
        val base = row * 3 + 1
        setBit(base, column)
    }

    internal fun clearLedBlue(row: Int, column: Int) {
        if (!areIndicesValid(row, column))
            return
        val base = row * 3 + 1
        clearBit(base + 2, column)
    }

    internal fun clearLedGreen(row: Int, column: Int) {
        if (!areIndicesValid(row, column))
            return
        val base = row * 3 + 1
        clearBit(base + 1, column)
    }

    internal fun clearLedRed(row: Int, column: Int) {
        if (!areIndicesValid(row, column))
            return
        val base = row * 3 + 1
        clearBit(base, column)
    }

    private fun setBit(byteNr: Int, bitNr: Int) {
        byteStream[byteNr] = byteStream[byteNr] or (1 shl bitNr).toByte()
    }

    private fun clearBit(byteNr: Int, bitNr: Int) {
        byteStream[byteNr] = byteStream[byteNr] and (1 shl bitNr).inv().toByte()
    }

    @Synchronized
    fun sendToCube(interval: Int) {
        // for (Cube cur : allCubes)
        // cur.parse(byteStream);
        if (milliSecondsToRun != INFINITY.toLong())
            milliSecondsToRun -= interval.toLong()
    }

    /**
     * gets executed every 1ms. Use this for multiplexing!
     */

    open fun animate1ms() {}

    /**
     * gets executed every 2ms. Use this for multiplexing!
     */

    open fun animate2ms() {}

    /**
     * gets executed every 5ms.
     */
    open fun animate5ms() {}

    /**
     * gets executed every 10ms
     */
    open fun animate10ms() {}

    /**
     * gets executed every 25ms
     */
    open fun animate25ms() {}

    /**
     * gets executed every 50ms
     */
    open fun animate50ms() {}

    /**
     * gets executed every 100ms
     */
    open fun animate100ms() {}

    /**
     * gets executed every 200ms
     */
    open fun animate200ms() {}

    /**
     * gets executed every 500ms
     */
    open fun animate500ms() {}

    /**
     * gets executed every 1000ms
     */
    open fun animate1000ms() {}

    override fun toString(): String {
        return animationName
    }

    /**
     * resets this animation to its initial state. Most often,
     * [.milliSecondsToRun] is reset to its initial value to allow this
     * animation to be played again.
     */
    abstract fun reset()

    override fun equals(obj: Any?): Boolean {
        if (this === obj)
            return true
        if (obj == null)
            return false
        if (javaClass != obj.javaClass)
            return false
        val other = obj as Animation?
        return if (!animationName.equals(other!!.animationName, ignoreCase = true)) false else true
    }

    override fun update(o: Observable, arg: Any) {

    }

    fun clockTick() {
        this.clock -= 1

    }

    companion object {

        protected val INFINITY = -1

        /**
         * Register your animation by adding the class name to this array. Only then
         * it will be found via reflection.
         */
        val ANIMATIONS = arrayOf("CornerRaceAnimation", //
                "DiceAnimation", //
                "IntroAnimation", //
                "LayerUpDownAnimation", //
                "LightningAnimation", //
                "RainfallAnimation", //
                "RandomLedAnimation", //
                "SimpleMultiplexAnimation", //
                "AllOutAnimation", //
                "CornerRaceAnimation")
    }

}
