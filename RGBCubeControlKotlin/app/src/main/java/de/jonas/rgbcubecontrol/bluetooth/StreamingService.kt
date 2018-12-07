package de.jonas.rgbcubecontrol.bluetooth

import android.app.*
import android.content.Intent
import android.os.IBinder
import android.util.Log
import app.akexorcist.bluetotohspp.library.BluetoothSPP
import app.akexorcist.bluetotohspp.library.BluetoothState
import de.jonas.rgbcubecontrol.ui.App
import app.akexorcist.bluetotohspp.library.BluetoothSPP.BluetoothConnectionListener
import android.support.v4.app.NotificationCompat
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Context
import android.os.Build
import android.support.v4.app.NotificationCompat.PRIORITY_MIN
import de.jonas.rgbcubecontrol.R
import de.jonas.rgbcubecontrol.ui.MainActivity


class StreamingService() : IntentService("StreamingService") {

    private var shouldRun = true
    private val TAG = "StreamingService"


    override fun onCreate() {
        super.onCreate()
        startServiceOreoCondition()
    }

    private fun startServiceOreoCondition() {
        if (Build.VERSION.SDK_INT >= 26) {


            val intent = Intent(this, MainActivity::class.java)
            val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)


            val notification = NotificationCompat.Builder(this, App.channel_id)
                    .setContentTitle("RGBCube Streaming")
                    .setContentText("Right now streaming...")
                    .setSmallIcon(R.drawable.connect)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true).build()
            startForeground(1, notification)
        }
    }



    override fun onHandleIntent(p0: Intent?) {
        Log.w(TAG, "going to send because connection successful")
        shouldRun = true
        while (shouldRun)
            App.bt.send("Hallo", true)
    }

    override fun onDestroy() {
        super.onDestroy()
        shouldRun = false
        Log.w(TAG, "going to be destroyed")

    }

    override fun onBind(intent: Intent?): IBinder {
        return super.onBind(intent)
    }

}