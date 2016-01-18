package com.gummyslug.oopsie.opc;

import java.util.Timer;

public class TimerTaskOpcClientExample {

	private static final int NUM_LEDS = 64;

	Timer timer = null;
	OpcTimerTask opcTimerTask;
	OpcClient opcClient = new OpcClient(NUM_LEDS);

	public void run() {
		setColor(PixelColors.BLUE);
		timer = new Timer("OPC");
		opcTimerTask = new OpcTimerTask(opcClient);
		timer.scheduleAtFixedRate(opcTimerTask, 0, 1000 / 60);
		sleep(2);
		System.out.println("Red");
		setColor(PixelColors.RED);
		sleep(2);
		System.out.println("Green");
		setColor(PixelColors.GREEN);
		sleep(2);
		opcClient.clear();
		timer.cancel();
	}

	public void setColor(PixelColor pixelColor) {
		for (int i = 0; i < NUM_LEDS; i++) {
			opcClient.setPixel(i, pixelColor);
		}
	}

	private void sleep(int seconds) {
		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new TimerTaskOpcClientExample().run();
	}

}
