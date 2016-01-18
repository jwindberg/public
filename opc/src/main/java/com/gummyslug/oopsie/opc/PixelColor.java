package com.gummyslug.oopsie.opc;

public final class PixelColor {

	private byte red = 0;
	private byte green = 0;
	private byte blue = 0;
	private int color = 0;

	public PixelColor() {

	}

	public PixelColor(byte red, byte green, byte blue) {
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.color = (red << 16) + (green << 8) + blue;
	}

	public PixelColor(int color) {
		this.color = color;
		this.red = (byte) (color >> 16);
		this.green = (byte) (color >> 8);
		this.blue = (byte) color;
	}

	public byte getRed() {
		return red;
	}

	public byte getGreen() {
		return green;
	}

	public byte getBlue() {
		return blue;
	}

	public int getColor() {
		return color;
	}

}
