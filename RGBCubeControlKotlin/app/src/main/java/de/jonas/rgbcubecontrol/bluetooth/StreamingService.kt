package de.jonas.rgbcubecontrol.bluetooth

import android.app.IntentService
import android.content.Intent
import android.util.Log
import app.akexorcist.bluetotohspp.library.BluetoothSPP
import app.akexorcist.bluetotohspp.library.BluetoothState
import de.jonas.rgbcubecontrol.ui.App

class StreamingService() : IntentService("StreamingService") {
    private val bt: BluetoothSPP = BluetoothSPP(App.instance)
    override fun onHandleIntent(p0: Intent?) {

        Log.w("StreamingService", "onHandleIntent")
        for (i in 0..10)
            bt.send("Hallo", true)
    }

    override fun onCreate() {
        super.onCreate()
        Log.w("StreamingService", "onCreate")
        bt.setupService()
        bt.startService(BluetoothState.DEVICE_OTHER)
        bt.autoConnect("Jonas")
    }
}