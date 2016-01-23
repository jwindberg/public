package com.gummyslug.opc.fx;

import java.util.Arrays;

public class PixelGrid {

	private int[] pixelLocations;
	private int height;
	private int width;

	public PixelGrid(int width, int height) {
		this.height = height;
		this.width = width;
	}

	public PixelGrid(int width, int height, int stripLength, int numStrips, boolean zigzag) {
		this(width, height);
		ledGrid(0, stripLength, numStrips, width / 2, height / 2, width / stripLength, height / numStrips, 0, zigzag);
	}

	public void ledGrid8x8(int index, float x, float y, float spacing, float angle, boolean zigzag) {
		ledGrid(index, 8, 8, x, y, spacing, spacing, angle, zigzag);
	}

	public void ledGrid(int index, int stripLength, int numStrips, float x, float y, float ledSpacing,
			float stripSpacing, float angle, boolean zigzag) {
		float s = (float) Math.sin(angle + Math.PI / 2.0);
		float c = (float) Math.cos(angle + Math.PI / 2.0);
		for (int i = 0; i < numStrips; i++) {
			ledStrip(index + stripLength * i, stripLength, (float) (x + (i - (numStrips - 1) / 2.0) * stripSpacing * c),
					(float) (y + (i - (numStrips - 1) / 2.0) * stripSpacing * s), ledSpacing, angle,
					zigzag && (i % 2) == 1);
		}
	}

	public void ledStrip(int index, int count, float x, float y, float spacing, float angle, boolean reversed) {
		float s = (float) Math.sin(angle);
		float c = (float) Math.cos(angle);
		for (int i = 0; i < count; i++) {
			led(reversed ? (index + count - 1 - i) : (index + i),
					(int) (x + (i - (count - 1) / 2.0) * spacing * c + 0.5),
					(int) (y + (i - (count - 1) / 2.0) * spacing * s + 0.5));
		}
	}

	public void led(int index, int x, int y) {
		// For convenience, automatically grow the pixelLocations array. We do
		// want this to be an array,
		// instead of a HashMap, to keep draw() as fast as it can be.
		if (pixelLocations == null) {
			pixelLocations = new int[index + 1];
		} else if (index >= pixelLocations.length) {
			pixelLocations = Arrays.copyOf(pixelLocations, index + 1);
		}
		pixelLocations[index] = x + getWidth() * y;
	}

	public int[] getPixelLocations() {
		return pixelLocations;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

}
