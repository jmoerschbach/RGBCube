package de.jonas.cubeModel.serial;

import de.jonas.cubeModel.Cube;

/**
 * This is a cube which sends the byteStream via a virtual com port instead of -
 * for example - visualizing it
 * 
 * @author jomo894
 * @see de.jonas.cubeModel.rendering.VisualCube VisualCube
 */
public class SerialCube implements Cube {

	private TwoWaySerialComm serialConsole;
	final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();

	public SerialCube() {
		serialConsole = TwoWaySerialComm.getInstance();
		serialConsole.connectTo("COM11", 115200);
	}

	@Override
	public void parse(byte[] byteStream) {
		System.out.println(bytesToHex(byteStream));
		byte[] start = { 's', 't', 'a' };
		byte[] end = { 'e', 'n', 'd' };
		serialConsole.send(start);
		serialConsole.send(byteStream);
		serialConsole.send(end);
	}

	private static String bytesToHex(byte[] bytes) {
		char[] hexChars = new char[bytes.length * 2];
		for (int j = 0; j < bytes.length; j++) {
			int v = bytes[j] & 0xFF;
			hexChars[j * 2] = hexArray[v >>> 4];
			hexChars[j * 2 + 1] = hexArray[v & 0x0F];
		}
		return new String(hexChars);
	}
}
