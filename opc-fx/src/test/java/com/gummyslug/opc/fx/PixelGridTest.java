package com.gummyslug.opc.fx;

import org.junit.Assert;
import org.junit.Test;

public class PixelGridTest {

	@Test
	public void test() {
		PixelGrid pixelGrid = new PixelGrid(100, 100, 8, 8, false);
		int[] pixelLocations = pixelGrid.getPixelLocations();
		Assert.assertEquals(64, pixelLocations.length);
		for (int i : pixelLocations) {
			int x = i / 8;
			int y = i - x;
			System.out.println(x + "\t" + y);
		}
	}

}
