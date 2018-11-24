package de.jonas.rgbcubecontrol.bluetooth

import android.app.IntentService
import android.content.Intent
import android.util.Log
import app.akexorcist.bluetotohspp.library.BluetoothSPP
import app.akexorcist.bluetotohspp.library.BluetoothState
import de.jonas.rgbcubecontrol.ui.App
import app.akexorcist.bluetotohspp.library.BluetoothSPP.BluetoothConnectionListener


class StreamingService() : IntentService("StreamingService") {

    override fun onHandleIntent(p0: Intent?) {
        Log.w("StreamingService", "going to send because connection sucessful")
        for (i in 0..10)
            App.bt.send("Hallo", true)
    }
}