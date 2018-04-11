package de.jonas.cubeModel.animations;

import de.jonas.cubeModel.Cube;

public class SimpleMultiplexAnimation extends Animation {
	private int state = 0;

	public SimpleMultiplexAnimation(Cube cubes) {
		super(cubes);

	}

	@Override
	public void animate1ms() {

	}

	@Override
	public String getAnimationName() {
		return "einfaches Multiplexing";
	}

	@Override
	public void reset() {
		// milliSecondsToRun=1000;
	}

	@Override
	public String getAudioFileName() {
		return "randomled";
	}

	@Override
	void loop() {
		switch (state) {
		case 0:
			byteStream[1] = (byte) 0xff;
			byteStream[2] = 0x00;
			byteStream[3] = 0x00;
			byteStream[4] = (byte) 0xff;
			byteStream[5] = 0x00;
			byteStream[6] = 0x00;
			byteStream[0] = 0x01;
			break;
		case 1:
			byteStream[1] = 0x00;
			byteStream[2] = (byte) 0xff;
			byteStream[3] = 0x00;
			byteStream[4] = 0x00;
			byteStream[5] = (byte) 0xff;
			byteStream[6] = 0x00;
			byteStream[0] = 0x02;
			break;
		case 2:
			byteStream[1] = 0x00;
			byteStream[2] = 0x00;
			byteStream[3] = (byte) 0xff;
			byteStream[4] = 0x00;
			byteStream[5] = 0x00;
			byteStream[6] = (byte) 0xff;
			byteStream[0] = 0x04;
			break;
		case 3:
			byteStream[1] = (byte) 0xff;
			byteStream[2] = 0x00;
			byteStream[3] = 0x00;
			byteStream[4] = (byte) 0xff;
			byteStream[5] = 0x00;
			byteStream[6] = 0x00;
			byteStream[0] = 0x08;
			break;
		case 4:
			byteStream[1] = 0x00;
			byteStream[2] = (byte) 0xff;
			byteStream[5] = 0x00;
			byteStream[4] = 0x00;
			byteStream[3] = (byte) 0xff;
			byteStream[6] = 0x00;
			byteStream[0] = 0x10;
			break;
		case 5:
			byteStream[1] = 0x00;
			byteStream[2] = 0x00;
			byteStream[3] = (byte) 0xff;
			byteStream[4] = 0x00;
			byteStream[5] = (byte) 0xff;
			byteStream[6] = (byte) 0xff;
			byteStream[0] = 0x20;
			break;
		case 6:
			byteStream[1] = (byte) 0xff;
			byteStream[2] = 0x00;
			byteStream[3] = 0x00;
			byteStream[6] = (byte) 0xff;
			byteStream[5] = 0x00;
			byteStream[4] = 0x00;
			byteStream[0] = 0x40;
			break;
		case 7:
			byteStream[1] = 0x00;
			byteStream[2] = (byte) 0xff;
			byteStream[3] = 0x00;
			byteStream[5] = 0x00;
			byteStream[4] = (byte) 0xff;
			byteStream[6] = 0x00;
			byteStream[0] = (byte) 0x80;
			break;
		}
		//delay(1000);
		state++;
		if (state > 7)
			state = 0;

	}

}
