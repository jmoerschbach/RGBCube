package de.jonas.rgbcubecontrol.ui

import android.app.Application
import jonas.de.weatherapp.ui.utils.DelegatesExt
import jonas.de.weatherapp.ui.utils.NotNullSingleValueVar

class App : Application() {

    companion object {
        var instance: App by DelegatesExt.notNullSingleValue()
        fun instance() = instance
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}