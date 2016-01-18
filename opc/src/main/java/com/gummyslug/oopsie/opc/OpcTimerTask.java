package com.gummyslug.oopsie.opc;

import java.util.TimerTask;

public class OpcTimerTask extends TimerTask {

	private OpcClient opcClient;

	public OpcTimerTask(OpcClient opcClient) {
		this.opcClient = opcClient;
	}

	@Override
	public void run() {
		opcClient.writePixels();
	}

}
