package de.jonas.cubeModel.serial;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import gnu.io.NRSerialPort;

public class TwoWaySerialComm {

	private static TwoWaySerialComm instance;

	private DataInputStream ins;
	private DataOutputStream outs;
	private NRSerialPort serial;

	public static TwoWaySerialComm getInstance() {
		if (instance == null)
			instance = new TwoWaySerialComm();
		return instance;
	}

	private TwoWaySerialComm() {

	}

	public void connectTo(String port, int baudRate) {
		serial = new NRSerialPort(port, baudRate);
		serial.connect();
		ins = new DataInputStream(serial.getInputStream());
		outs = new DataOutputStream(serial.getOutputStream());
	}

	public void send(byte[] byteStream) {
		try {
			for (byte current : byteStream)
				outs.writeByte(current);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void disconnect() {
		serial.disconnect();
	}
}