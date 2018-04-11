package de.jonas.bitOperations;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BitMask {

	private byte[] bitStream;
	private int test;

	@Before
	public void setUp() throws Exception {
		bitStream = new byte[24];
		test = 0xff;
	}

	@Test
	public void test() {
		assertTrue((test & (1 << 0)) == 1);
		//assertTrue((test & (1 << 1)) == 1);

		int a = (1 << 2);
		assertTrue((a & (1L<<2))!=0);
		assertEquals(4, a);

	}

}
