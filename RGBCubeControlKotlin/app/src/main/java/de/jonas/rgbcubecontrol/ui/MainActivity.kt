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
import android.app.Notification
import android.app.PendingIntent
import android.bluetooth.BluetoothAdapter
import android.support.v7.widget.Toolbar
import android.widget.ImageView
import app.akexorcist.bluetotohspp.library.DeviceList
import de.jonas.rgbcubecontrol.ui.App.Companion.bt
import org.jetbrains.anko.find
import kotlin.properties.Delegates


class MainActivity() : AppCompatActivity() {
    private val TAG = "MainActivity"
    var connectedToCube by Delegates.observable(false) { _, _, isConnected -> connectionStatus(isConnected) }

    private val toolbar by lazy { find<Toolbar>(R.id.toolbar) }
    private val connectionStatusIcon by lazy { find<ImageView>(R.id.connectionStatus) }
    private val stopButton by lazy { find<Button>(R.id.stopButton) }
    private val sendButton by lazy { find<Button>(R.id.sendButton) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        connectionStatusIcon.setOnClickListener { setupBluetooth() }
        connectedToCube = bt.connectedDeviceName != null

        Log.w(TAG, "onCreate: connected to cube: $connectedToCube")
    }

    private fun setupBluetooth() {
        if (bt.isBluetoothEnabled) {
            if (!connectedToCube) chooseBluetoothDevice() else disconnect()
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

    private fun disconnect() = bt.disconnect()

    private fun setupConnection(data: Intent) {
        bt.setupService()
        bt.startService(BluetoothState.DEVICE_OTHER)
        bt.setBluetoothConnectionListener(object : BluetoothSPP.BluetoothConnectionListener {
            override fun onDeviceConnected(name: String, address: String) {
                Toast.makeText(App.instance, "successful connected to $name", Toast.LENGTH_SHORT).show()
                Log.w("StreamingService", "successful connected to $name")
                connectedToCube = true
            }

            override fun onDeviceDisconnected() {
                Toast.makeText(App.instance, "disconnected", Toast.LENGTH_SHORT).show()
                connectedToCube = false
            }

            override fun onDeviceConnectionFailed() {
                Toast.makeText(App.instance, "connection attempt failed", Toast.LENGTH_SHORT).show()
                connectedToCube = false
            }
        })
        bt.connect(data)
    }

    private fun connectionStatus(isConnected: Boolean) {
        sendButton.isEnabled = isConnected
        stopButton.isEnabled = isConnected
        if (isConnected)
            connectionStatusIcon.setImageResource(R.drawable.connect)
        else {
            stopStreamingService()
            connectionStatusIcon.setImageResource(R.drawable.disconnect)
        }
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == BluetoothState.REQUEST_CONNECT_DEVICE) {
            if (resultCode == Activity.RESULT_OK) {
                setupConnection(data!!)
            } else {
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
        Toast.makeText(this, "start sending...", Toast.LENGTH_SHORT).show()
        startStreamingService()

    }

    private fun startStreamingService() {
        Intent(this, StreamingService::class.java).also { startForegroundService(it) }
    }

    fun stop(view: View) {
        Toast.makeText(this, "stop sending...", Toast.LENGTH_SHORT).show()
        stopStreamingService()

    }

    private fun stopStreamingService() {
        Intent(this, StreamingService::class.java).also { stopService(it) }
    }

}
