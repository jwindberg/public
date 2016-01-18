package com.gummyslug.oopsie.opc;

public abstract class Pixels {

	private Pixels() {

	}

	public static final Pixel BLACK = new Pixel((byte) 0, (byte) 0, (byte) 0);
	public static final Pixel RED = new Pixel((byte) 100, (byte) 0, (byte) 0);
	public static final Pixel GREEN = new Pixel((byte) 0, (byte) 100, (byte) 0);
	public static final Pixel BLUE = new Pixel((byte) 0, (byte) 0, (byte) 100);

}
