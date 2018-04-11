package de.jonas.cubeModel.animations;

import de.jonas.cubeModel.rendering.models.TestCube;

public class TestBase {

	TestCube dummyCube;
	byte[] byteStream;

	boolean testIfBitIsSet(int byteNr, int bitNr) {
		return (byteStream[byteNr] & (1 << bitNr)) != 0;
	}

	boolean testIfBitIsCleared(int byteNr, int bitNr) {
		return (byteStream[byteNr] & (1 << bitNr)) == 0;
	}

	boolean testIfBitIsSet(byte[] byteStream, int byteNr, int bitNr) {
		return (byteStream[byteNr] & (1 << bitNr)) != 0;
	}

	boolean testIfBitIsCleared(byte[] byteStream, int byteNr, int bitNr) {
		return (byteStream[byteNr] & (1 << bitNr)) == 0;
	}

	boolean isGreen(int iteration, int row, int column) {
		getCurrentIteration(iteration);
		int offset = row * 3 + 1;
		return testIfBitIsSet(offset + 1, column);
	}

	boolean isRed(int iteration, int row, int column) {
		getCurrentIteration(iteration);
		int offset = row * 3 + 1;
		return testIfBitIsSet(offset, column);
	}

	boolean isBlue(int iteration, int row, int column) {
		getCurrentIteration(iteration);
		int offset = row * 3 + 1;
		return testIfBitIsSet(offset + 2, column);
	}

	boolean isWhite(int iteration, int row, int column) {
		getCurrentIteration(iteration);
		return isRed(iteration, row, column) && isGreen(iteration, row, column) && isBlue(iteration, row, column);
	}

	boolean isBlack(int iteration, int row, int column) {
		getCurrentIteration(iteration);
		return !isRed(iteration, row, column) && !isGreen(iteration, row, column) && !isBlue(iteration, row, column);
	}

	private void getCurrentIteration(int iteration) {
		byteStream = dummyCube.getByteStream(iteration);
	}

}
