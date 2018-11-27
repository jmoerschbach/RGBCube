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
import android.support.v7.widget.Toolbar
import android.widget.ImageView
import app.akexorcist.bluetotohspp.library.DeviceList
import de.jonas.rgbcubecontrol.ui.App.Companion.bt
import org.jetbrains.anko.find


class MainActivity() : AppCompatActivity() {

    val toolbar by lazy { find<Toolbar>(R.id.toolbar) }
    val connectionStatusIcon by lazy { find<ImageView>(R.id.connectionStatus) }
    val stopButton by lazy { find<Button>(R.id.stopButton) }
    val sendButton by lazy { find<Button>(R.id.sendButton) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        connectionStatusIcon.setOnClickListener { setupBluetooth() }
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

    private fun setupConnection(data: Intent) {
        bt.setupService()
        bt.startService(BluetoothState.DEVICE_OTHER)
        bt.setBluetoothConnectionListener(object : BluetoothSPP.BluetoothConnectionListener {
            override fun onDeviceConnected(name: String, address: String) {
                Toast.makeText(App.instance, "enabling button", Toast.LENGTH_SHORT).show()
                Log.w("StreamingService", "succesfull connected to {$name}")
                sendButton.isEnabled = true
                stopButton.isEnabled = true
                connectionStatusIcon.setImageResource(R.drawable.connect)
            }

            override fun onDeviceDisconnected() {
                stopButton.isEnabled = false
                sendButton.isEnabled = false
                connectionStatusIcon.setImageResource(R.drawable.disconnect)
            }

            override fun onDeviceConnectionFailed() {
                stopButton.isEnabled = false
                sendButton.isEnabled = false
                connectionStatusIcon.setImageResource(R.drawable.disconnect)
            }
        })
        bt.connect(data)
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

//        val pendingIntent: PendingIntent =
//                Intent(this, StreamingService::class.java).let { notificationIntent ->
//                    PendingIntent.getActivity(this, 0, notificationIntent, 0)
//                }
//
//        val notification: Notification = Notification.Builder(this, Notification.CATEGORY_EVENT)
//                .setContentTitle("RGBCube")
//                .setContentText("streaming data to cube")
//                .setContentIntent(pendingIntent)
//                .setTicker("bla")
//                .build()
//
//        startForegroundService(1, notification)
        Intent(this, StreamingService::class.java).also { intent ->
            startForegroundService(intent)
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
