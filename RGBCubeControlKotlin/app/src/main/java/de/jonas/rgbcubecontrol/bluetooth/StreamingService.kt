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
import android.os.Binder
import android.os.Build
import android.support.v4.app.NotificationCompat.PRIORITY_MIN
import de.jonas.rgbcubecontrol.R
import de.jonas.rgbcubecontrol.domain.animations.Animation
import de.jonas.rgbcubecontrol.domain.animations.SimpleMultiplexAnimation
import de.jonas.rgbcubecontrol.ui.MainActivity
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit


class StreamingService() : Service() {


    // Binder given to clients
    private val mBinder = LocalBinder()
    private val TAG = "StreamingService"
    private var scheduler = Executors.newScheduledThreadPool(1)


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





    fun startPlaying() {
        Log.w(TAG, "startPlaying")
        val start = ByteArray(1).also { it[0] = 'a'.toByte() }
        val end = ByteArray(1).also { it[0] = 'e'.toByte() }

        val animationToRun = SimpleMultiplexAnimation()

        scheduler.scheduleAtFixedRate({
            animationToRun.animate1ms()//
            App.bt.send(start, false)//
            App.bt.send(animationToRun.byteStream, false) //
            App.bt.send(end, false)
        }, 0, 1, TimeUnit.MICROSECONDS)

    }

    fun stopPlaying() {
        Log.w(TAG, "stopPlaying")
        scheduler.shutdownNow()
        scheduler=Executors.newScheduledThreadPool(1)
    }

    inner class LocalBinder : Binder() {
        // Return this instance of LocalService so clients can call public methods
        fun getService(): StreamingService = this@StreamingService
    }


    override fun onBind(intent: Intent?): IBinder {
        Log.w(TAG, "onBind")
        return mBinder
    }
}