package de.jonas.rgbcubecontrol.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import app.akexorcist.bluetotohspp.library.BluetoothSPP
import app.akexorcist.bluetotohspp.library.BluetoothState
import de.jonas.rgbcubecontrol.R
import app.akexorcist.bluetotohspp.library.DeviceList
import android.content.Intent
import android.app.Activity
import android.widget.Button
import de.jonas.rgbcubecontrol.bluetooth.StreamingService


class MainActivity() : AppCompatActivity() {
    private val bt: BluetoothSPP = BluetoothSPP(App.instance)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (!bt.isBluetoothEnabled()) {
            //findViewById<R.id.>()
        } else {
            bt.setupService()
            bt.startService(BluetoothState.DEVICE_OTHER)
            bt.autoConnect("Jonas")
            bt.setBluetoothConnectionListener(object : BluetoothSPP.BluetoothConnectionListener {
                override fun onDeviceConnected(name: String, address: String) {
                    Toast.makeText(App.instance, "enabling button", Toast.LENGTH_SHORT).show()
                    Log.w("StreamingService", "succesfull connected to {$name}")
                    findViewById<Button>(R.id.sendButton).isEnabled = true
                }

                override fun onDeviceDisconnected() {
                    findViewById<Button>(R.id.sendButton).isEnabled = false
                }

                override fun onDeviceConnectionFailed() {
                    findViewById<Button>(R.id.sendButton).isEnabled = false
                }
            })
//            val intent = Intent(applicationContext, DeviceList::class.java)
//            startActivityForResult(intent, BluetoothState.REQUEST_CONNECT_DEVICE)
        }
    }

    fun send(view: View) {
//        bt.send("Hallo", true)
        Intent(this, StreamingService::class.java).also { intent ->

            startService(intent)
        }
        Toast.makeText(this, "button was clicked", Toast.LENGTH_SHORT).show()

    }

}
