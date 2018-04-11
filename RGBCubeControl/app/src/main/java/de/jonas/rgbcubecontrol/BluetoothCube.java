package de.jonas.rgbcubecontrol;


import android.util.Log;


import de.jonas.cubeModel.Cube;
import de.jonas.rgbcubecontrol.bluetooth.BluetoothSPP;
import de.jonas.rgbcubecontrol.bluetooth.BluetoothState;

/**
 * Created by jomo894 on 19.02.2016.
 */
public class BluetoothCube implements Cube {

    private String TAG = "BluetoothCube";
    private final BluetoothSPP bt;
    private final static String btModuleName = "RGB-Cube";
    private int sentPackages=0;

    public BluetoothCube(BluetoothSPP bt) {
        this.bt = bt;
    }

    void setupBluetooth() {
        Log.w(TAG, "starting Bluetooth...");
        bt.setBluetoothConnectionListener(new BluetoothSPP.BluetoothConnectionListener() {
            @Override
            public void onDeviceConnected(String name, String address) {
                Log.i(TAG, "Connected to " + name);
            }

            @Override
            public void onDeviceDisconnected() {
                Log.w(TAG, "Disconnected");
            }

            @Override
            public void onDeviceConnectionFailed() {
                Log.w(TAG, "Connection failed");
            }
        });

        bt.setAutoConnectionListener(new BluetoothSPP.AutoConnectionListener() {
            @Override
            public void onAutoConnectionStarted() {
                Log.i(TAG, "AutoConnection started...");
            }

            @Override
            public void onNewConnection(String name, String address) {
                if (bt.getConnectedDeviceName() != null) {
                    Log.i(TAG, "AutoConnected to " + bt.getConnectedDeviceName());
                }
            }
        });
        bt.setOnDataReceivedListener(new BluetoothSPP.OnDataReceivedListener() {
            @Override
            public void onDataReceived(byte[] data, String message) {
                Log.w(TAG, message);
                Log.w(TAG, "sentPackages: "+sentPackages);
                sentPackages = 0;
            }
        });
        bt.setupService();
        bt.startService(BluetoothState.DEVICE_OTHER);
        bt.autoConnect(btModuleName);
    }

    void stopBluetooth() {
        bt.stopService();
    }

    @Override
    public void parse(byte[] bytes) {
        if (!isConnected()) {
            Log.i(TAG, "Not connected...");
            return;
        }
        sentPackages++;
        //Log.i(TAG, "sending: " + bytesToHex(bytes));
        bt.send(new byte[]{'a'}, false);
        bt.send(bytes, false);
        bt.send(new byte[]{'e'}, false);
    }

    public boolean isConnected() {
        return bt.getConnectedDeviceName() != null && bt.getConnectedDeviceName().equals(btModuleName);
    }

    final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    public void resetAutoconnectionRetries(){
        bt.resetRetries();
        bt.autoConnect(btModuleName);
    }
}
