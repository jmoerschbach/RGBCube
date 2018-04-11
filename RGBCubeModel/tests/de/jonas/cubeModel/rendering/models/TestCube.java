package de.jonas.cubeModel.rendering.models;

import java.util.ArrayList;
import java.util.List;

import de.jonas.cubeModel.Cube;

public class TestCube implements Cube {

	private List<byte[]> byteStreams;

	public TestCube() {
		this.byteStreams = new ArrayList<>(1000);
	}

	@Override
	public void parse(byte[] byteStream) {
		byteStreams.add(byteStream);

	}

	public byte[] getByteStream(int number) {
		if (byteStreams.isEmpty())
			return null;
		return byteStreams.get(number);
	}
}
