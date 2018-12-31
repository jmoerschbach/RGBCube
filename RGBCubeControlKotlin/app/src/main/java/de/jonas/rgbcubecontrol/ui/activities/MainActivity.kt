package de.jonas.rgbcubecontrol.ui.activities

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.Toast
import app.akexorcist.bluetotohspp.library.BluetoothSPP
import app.akexorcist.bluetotohspp.library.BluetoothState
import app.akexorcist.bluetotohspp.library.DeviceList
import de.jonas.rgbcubecontrol.R
import de.jonas.rgbcubecontrol.bluetooth.StreamingService
import de.jonas.rgbcubecontrol.domain.animations.Animation
import de.jonas.rgbcubecontrol.ui.AnimationItemProvider
import de.jonas.rgbcubecontrol.ui.App
import de.jonas.rgbcubecontrol.ui.App.Companion.bt
import de.jonas.rgbcubecontrol.ui.adapters.AvailableAnimationsListAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.properties.Delegates


class MainActivity() : AppCompatActivity() {
    private val TAG = "MainActivity"

    private lateinit var mService: StreamingService
    private var mBound: Boolean = false


    var connectedToCube by Delegates.observable(false) { _, wasConnected, isNowConnected ->
        if (wasConnected != isNowConnected)
            connectionStatus(isNowConnected)//
    }


    private val mConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
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
        val adapter = AvailableAnimationsListAdapter(AnimationItemProvider().getAllAnimationItems(), { startPlaying(it.animation) })
        availableAnimationsList.layoutManager = LinearLayoutManager(this)
        availableAnimationsList.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        Intent(this, StreamingService::class.java).also {
            bindService(it, mConnection, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onStop() {
        super.onStop()
        unbindService(mConnection)
    }


    override fun onResume() {
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
        Log.w(TAG, "connectionStatus=$isConnected")
        sendButton.isEnabled = isConnected
        stopButton.isEnabled = isConnected
        if (isConnected)
            connectionStatusIcon.setImageResource(R.drawable.connect)
        else {
            connectionStatusIcon.setImageResource(R.drawable.disconnect)
            stopPlaying()
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
        startPlaying()

    }

    private fun startPlaying() {
        Intent(this, StreamingService::class.java).also { startService(it) }
        if (mBound)
            mService.startPlaying()
    }

    private fun startPlaying(animation: Animation) {
        Intent(this, StreamingService::class.java).also { startService(it) }
        if (mBound)
            mService.startPlaying(animation)
    }

    fun stop(view: View) {
        Toast.makeText(this, "stop sending...", Toast.LENGTH_SHORT).show()
        stopPlaying()
    }

    private fun stopPlaying() {
        if (mBound)
            mService.stopPlaying()
    }

}
