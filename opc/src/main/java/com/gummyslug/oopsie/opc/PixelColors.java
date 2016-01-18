package com.gummyslug.oopsie.opc;

public abstract class PixelColors {

	private PixelColors() {

	}

	public static final PixelColor BLACK = new PixelColor((byte) 0, (byte) 0, (byte) 0);
	public static final PixelColor RED = new PixelColor((byte) 255, (byte) 0, (byte) 0);
	public static final PixelColor GREEN = new PixelColor((byte) 0, (byte) 255, (byte) 0);
	public static final PixelColor BLUE = new PixelColor((byte) 0, (byte) 0, (byte) 255);

}
