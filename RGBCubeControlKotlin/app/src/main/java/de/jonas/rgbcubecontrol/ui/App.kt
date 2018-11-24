package de.jonas.rgbcubecontrol.ui

import android.app.Application
import app.akexorcist.bluetotohspp.library.BluetoothSPP
import jonas.de.weatherapp.ui.utils.DelegatesExt
import jonas.de.weatherapp.ui.utils.NotNullSingleValueVar

class App : Application() {

    companion object {
        var instance: App by DelegatesExt.notNullSingleValue()
        var bt: BluetoothSPP by DelegatesExt.notNullSingleValue()
        fun instance() = instance
        fun bt() = bt
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        bt = BluetoothSPP(this)
    }
}