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
import de.jonas.rgbcubecontrol.bluetooth.StreamingService
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.ComponentName
import android.content.Context
import android.content.ServiceConnection
import android.os.IBinder
import app.akexorcist.bluetotohspp.library.DeviceList
import de.jonas.rgbcubecontrol.ui.App.Companion.bt
import kotlin.properties.Delegates
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity() : AppCompatActivity() {
    private val TAG = "MainActivity"

    private lateinit var mService: StreamingService
    private var mBound: Boolean = false


    var connectedToCube by Delegates.observable(false) { _, _, isConnected -> connectionStatus(isConnected) }


    /** Defines callbacks for service binding, passed to bindService()  */
    private val mConnection = object : ServiceConnection {

        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            val binder = service as StreamingService.LocalBinder
            mService = binder.getService()
            mBound = true
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            mBound = false
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        connectionStatusIcon.setOnClickListener { setupBluetooth() }
        connectedToCube = bt.connectedDeviceName != null
//android:configChanges="orientation|screenSize"
        Log.w(TAG, "onCreate: connected to cube: $connectedToCube")
    }

    override fun onStart() {
        super.onStart()
        // Bind to LocalService

    }


    override fun onResume() {
        Log.w(TAG, "onResume")
        super.onResume()
        connectedToCube = bt.connectedDeviceName != null;
        Log.w(TAG, "onResume: connected to cube: $connectedToCube")
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
                Log.w(TAG, "successful connected to $name")
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
        Log.w(TAG, "connectionStatus= $isConnected")
        sendButton.isEnabled = isConnected
        stopButton.isEnabled = isConnected
        if (isConnected)
            connectionStatusIcon.setImageResource(R.drawable.connect)
        else {
            connectionStatusIcon.setImageResource(R.drawable.disconnect)
            stopStreamingService()
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
        Intent(this, StreamingService::class.java).also { intent ->
            bindService(intent, mConnection, Context.BIND_AUTO_CREATE)
        }
        if(mBound)
            mService.startPlaying()
        //Intent(this, StreamingService::class.java).also { startForegroundService(it) }
    }

    fun stop(view: View) {
        Toast.makeText(this, "stop sending...", Toast.LENGTH_SHORT).show()
        stopStreamingService()

    }

    private fun stopStreamingService() {
        if (mBound)
            mService.stopPlaying()
        //  Intent(this, StreamingService::class.java).also { stopService(it) }
    }

}
