package de.jonas.rgbcubecontrol.bluetooth

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.support.v4.app.NotificationCompat
import android.support.v4.app.ServiceCompat
import android.util.Log
import de.jonas.rgbcubecontrol.R
import de.jonas.rgbcubecontrol.domain.animations.Animation
import de.jonas.rgbcubecontrol.domain.animations.SimpleMultiplexAnimation
import de.jonas.rgbcubecontrol.ui.App
import de.jonas.rgbcubecontrol.ui.activities.MainActivity
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit


private val NOTIFICATION_ID = 1

class StreamingService() : Service() {


    private val NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors()
    // Binder given to clients
    private val mBinder = LocalBinder()
    private val TAG = "StreamingService"
    private var scheduler = Executors.newScheduledThreadPool(NUMBER_OF_CORES)


    override fun onCreate() {
        super.onCreate()
        Log.w(TAG, "Number of cores: $NUMBER_OF_CORES")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.w(TAG, "onStartCommand")
        return super.onStartCommand(intent, flags, startId)
    }

    private fun startAsForegroundService(notification: Notification) {
        if (Build.VERSION.SDK_INT >= 26) {
            Log.w(TAG, "startAsForegroundService")
            startForeground(NOTIFICATION_ID, notification)
        }
    }

    private fun buildNotification(contentText: String = "", iconId: Int = R.drawable.connect): Notification {
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        return NotificationCompat.Builder(this, App.channel_id)
                .setContentTitle("RGBCube Control")
                .setContentText(contentText)
                .setSmallIcon(iconId)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true).build()
    }


    fun startPlaying(animationToRun: Animation = SimpleMultiplexAnimation()) {
        Log.w(TAG, "startPlaying")
        stopPlaying()
        startAsForegroundService(buildNotification("Currently playing ${animationToRun.animationName}..."))
        play(animationToRun)
    }

    private fun play(animationToRun: Animation) {
        val start = ByteArray(1).also { it[0] = 'a'.toByte() }
        val end = ByteArray(1).also { it[0] = 'e'.toByte() }

        scheduler.scheduleAtFixedRate({
            animationToRun.animate2ms()//
            App.bt.send(start, false)//
            App.bt.send(animationToRun.byteStream, false) //
            App.bt.send(end, false)
        }, 0, 2, TimeUnit.MILLISECONDS)


        scheduler.scheduleAtFixedRate({ animationToRun.animate100ms() }, 0, 100, TimeUnit.MILLISECONDS)
        scheduler.scheduleAtFixedRate({ animationToRun.animate200ms() }, 0, 200, TimeUnit.MILLISECONDS)
        scheduler.scheduleAtFixedRate({ animationToRun.animate500ms() }, 0, 500, TimeUnit.MILLISECONDS)
        scheduler.scheduleAtFixedRate({ animationToRun.animate1000ms() }, 0, 1000, TimeUnit.MILLISECONDS)
    }

    fun stopPlaying() {
        Log.w(TAG, "stopPlaying")
        ServiceCompat.stopForeground(this, STOP_FOREGROUND_REMOVE)
        scheduler.shutdownNow()
        scheduler = Executors.newScheduledThreadPool(NUMBER_OF_CORES)
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