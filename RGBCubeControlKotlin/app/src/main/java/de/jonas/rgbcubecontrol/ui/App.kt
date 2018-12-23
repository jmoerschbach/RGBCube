package de.jonas.rgbcubecontrol.ui

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import app.akexorcist.bluetotohspp.library.BluetoothSPP
import jonas.de.weatherapp.ui.utils.DelegatesExt
import jonas.de.weatherapp.ui.utils.NotNullSingleValueVar

class App : Application() {

    companion object {
        var instance: App by DelegatesExt.notNullSingleValue()
        var bt: BluetoothSPP by DelegatesExt.notNullSingleValue()
        const val channel_id : String = "RGBCubeControl Notfication Channel"
        fun instance() = instance
        fun bt() = bt
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        bt = BluetoothSPP(this)
        createNotificationChannel()
    }

    private fun createNotificationChannel() {

        val channel = NotificationChannel(channel_id,
                "RGBCubeControl Notfication Channel",
                NotificationManager.IMPORTANCE_LOW)
        channel.enableVibration(false)

        (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).createNotificationChannel(channel)
    }
}