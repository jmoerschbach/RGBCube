package de.jonas.rgbcubecontrol.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import app.akexorcist.bluetotohspp.library.BluetoothSPP
import app.akexorcist.bluetotohspp.library.BluetoothState
import de.jonas.rgbcubecontrol.R
import android.content.Intent
import android.widget.Button
import de.jonas.rgbcubecontrol.bluetooth.StreamingService
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import app.akexorcist.bluetotohspp.library.DeviceList
import de.jonas.rgbcubecontrol.ui.App.Companion.bt


class MainActivity() : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        setupBluetooth()
    }

    private fun setupBluetooth() {
        if (bt.isBluetoothEnabled) {
            chooseBluetoothDevice()
        } else {
            enableBluetooth()
        }
    }

    private fun enableBluetooth() {
        val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
        startActivityForResult(enableBtIntent, BluetoothState.REQUEST_ENABLE_BT)
    }

    private fun chooseBluetoothDevice() {
        val intent = Intent(applicationContext, DeviceList::class.java)
        startActivityForResult(intent, BluetoothState.REQUEST_CONNECT_DEVICE)
    }

    private fun setupConnection() {
        bt.setupService()
        bt.startService(BluetoothState.DEVICE_OTHER)
        bt.setBluetoothConnectionListener(object : BluetoothSPP.BluetoothConnectionListener {
            override fun onDeviceConnected(name: String, address: String) {
                Toast.makeText(App.instance, "enabling button", Toast.LENGTH_SHORT).show()
                Log.w("StreamingService", "succesfull connected to {$name}")
                findViewById<Button>(R.id.sendButton).isEnabled = true
                findViewById<Button>(R.id.stopButton).isEnabled = true
            }

            override fun onDeviceDisconnected() {
                findViewById<Button>(R.id.stopButton).isEnabled = false
                findViewById<Button>(R.id.sendButton).isEnabled = false
            }

            override fun onDeviceConnectionFailed() {
                findViewById<Button>(R.id.stopButton).isEnabled = false
                findViewById<Button>(R.id.sendButton).isEnabled = false
            }
        })
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == BluetoothState.REQUEST_CONNECT_DEVICE) {
            if (resultCode == Activity.RESULT_OK) {
                setupConnection()
                bt.connect(data!!)
            }
            else {
                Toast.makeText(this, "you need to choose a RGB-Cube", Toast.LENGTH_SHORT).show()
            }
        } else if (requestCode == BluetoothState.REQUEST_ENABLE_BT) {
            if (resultCode == Activity.RESULT_OK) {
                chooseBluetoothDevice()
            } else {
                Toast.makeText(this, "you need to enable Bluetooth", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun send(view: View) {

        Intent(this, StreamingService::class.java).also { intent ->

            startService(intent)
        }
        Toast.makeText(this, "start sending...", Toast.LENGTH_SHORT).show()

    }

    fun stop(view: View) {

        Intent(this, StreamingService::class.java).also { intent ->

            stopService(intent)
        }
        Toast.makeText(this, "stop sending...", Toast.LENGTH_SHORT).show()

    }

}
