package com.gummyslug.oopsie.opc;

public class Pixel {

	private byte red;
	private byte green;
	private byte blue;

	public Pixel() {

	}

	public Pixel(byte red, byte green, byte blue) {
		this.red = red;
		this.green = green;
		this.blue = blue;
	}

	public byte getRed() {
		return red;
	}

	public void setRed(byte red) {
		this.red = red;
	}

	public byte getGreen() {
		return green;
	}

	public void setGreen(byte green) {
		this.green = green;
	}

	public byte getBlue() {
		return blue;
	}

	public void setBlue(byte blue) {
		this.blue = blue;
	}

}
