package de.jonas.rgbcubecontrol.bluetooth

import android.app.IntentService
import android.content.Intent
import android.os.IBinder
import android.util.Log
import app.akexorcist.bluetotohspp.library.BluetoothSPP
import app.akexorcist.bluetotohspp.library.BluetoothState
import de.jonas.rgbcubecontrol.ui.App
import app.akexorcist.bluetotohspp.library.BluetoothSPP.BluetoothConnectionListener


class StreamingService() : IntentService("StreamingService") {

    private var shouldRun = true

    override fun onHandleIntent(p0: Intent?) {
        Log.w("StreamingService", "going to send because connection sucessful")
        shouldRun=true
        while(shouldRun)
            App.bt.send("Hallo", true)
    }

    override fun onDestroy() {
        super.onDestroy()
        shouldRun=false
        Log.w("StreamingService", "going to be destroyed")

    }

    override fun onBind(intent: Intent?): IBinder {
        return super.onBind(intent)
    }

}