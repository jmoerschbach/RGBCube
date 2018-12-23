package de.jonas.rgbcubecontrol.domain.animations

class SimpleMultiplexAnimation : Animation() {
    override val animationName: String
        get() = "SimpleMultiplex"

    private var state: Int = 0

    override  fun reset() {
        state = 0
    }

    override fun animate1ms() {
        when (state) {
            0 -> {
                byteStream[1] = 0xff.toByte()
                byteStream[2] = 0x00
                byteStream[3] = 0x00
                byteStream[4] = 0xff.toByte()
                byteStream[5] = 0x00
                byteStream[6] = 0x00
                byteStream[0] = 0x01
            }
            1 -> {
                byteStream[1] = 0x00
                byteStream[2] = 0xff.toByte()
                byteStream[3] = 0x00
                byteStream[4] = 0x00
                byteStream[5] = 0xff.toByte()
                byteStream[6] = 0x00
                byteStream[0] = 0x02
            }
            2 -> {
                byteStream[1] = 0x00
                byteStream[2] = 0x00
                byteStream[3] = 0xff.toByte()
                byteStream[4] = 0x00
                byteStream[5] = 0x00
                byteStream[6] = 0xff.toByte()
                byteStream[0] = 0x04
            }
            3 -> {
                byteStream[1] = 0xff.toByte()
                byteStream[2] = 0x00
                byteStream[3] = 0x00
                byteStream[4] = 0xff.toByte()
                byteStream[5] = 0x00
                byteStream[6] = 0x00
                byteStream[0] = 0x08
            }
            4 -> {
                byteStream[1] = 0x00
                byteStream[2] = 0xff.toByte()
                byteStream[5] = 0x00
                byteStream[4] = 0x00
                byteStream[3] = 0xff.toByte()
                byteStream[6] = 0x00
                byteStream[0] = 0x10
            }
            5 -> {
                byteStream[1] = 0x00
                byteStream[2] = 0x00
                byteStream[3] = 0xff.toByte()
                byteStream[4] = 0x00
                byteStream[5] = 0xff.toByte()
                byteStream[6] = 0xff.toByte()
                byteStream[0] = 0x20
            }
            6 -> {
                byteStream[1] = 0xff.toByte()
                byteStream[2] = 0x00
                byteStream[3] = 0x00
                byteStream[6] = 0xff.toByte()
                byteStream[5] = 0x00
                byteStream[4] = 0x00
                byteStream[0] = 0x40
            }
            7 -> {
                byteStream[1] = 0x00
                byteStream[2] = 0xff.toByte()
                byteStream[3] = 0x00
                byteStream[5] = 0x00
                byteStream[4] = 0xff.toByte()
                byteStream[6] = 0x00
                byteStream[0] = 0x80.toByte()
            }
        }
        state++
        if (state > 7) {
            state = 0
        }
    }
}