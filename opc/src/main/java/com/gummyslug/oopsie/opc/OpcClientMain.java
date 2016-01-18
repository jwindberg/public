package com.gummyslug.oopsie.opc;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class OpcClientMain {

	Timer timer = null;
	OPCTask opcTask = null;
	OpcClient opcClient = new OpcClient();

	List<Pixel> pixels = createPixels();

	public void run() {
		timer = new Timer("OPC");
		opcTask = new OPCTask();
		timer.scheduleAtFixedRate(opcTask, 2000, 1000 / 60);

		sleep(30);

	}

	private List<Pixel> createPixels() {
		List<Pixel> pixels = new ArrayList<Pixel>();
		for (int i = 0; i < 64; i++) {
			pixels.add(Pixels.RED);
		}
		return pixels;
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
		new OpcClientMain().run2();
	}

	private void run2() {
		opcClient.writePixels(pixels);

	}

	class OPCTask extends TimerTask {

		@Override
		public void run() {
			opcClient.writePixels(pixels);

		}

	}

}
