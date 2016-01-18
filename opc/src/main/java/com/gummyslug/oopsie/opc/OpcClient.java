package com.gummyslug.oopsie.opc;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.Socket;

public class OpcClient {

	private Socket socket;
	private OutputStream output;
	private String host = "localhost";
	private int port = 7890;
	private String colorCorrection;
	private byte firmwareConfig;
	private byte[] packetData;

	public OpcClient(int size) {
		setPixelCount(size);
	}

	public void setPixelCount(int numPixels) {
		int numBytes = 3 * numPixels;
		int packetLen = 4 + numBytes;
		if (packetData == null || packetData.length != packetLen) {
			// Set up our packet buffer
			packetData = new byte[packetLen];
			packetData[0] = 0; // Channel
			packetData[1] = 0; // Command (Set pixel colors)
			packetData[2] = (byte) (numBytes >> 8);
			packetData[3] = (byte) (numBytes & 0xFF);
		}
	}

	public void setPixel(int number, PixelColor pixelColor) {
		int offset = 4 + number * 3;
		if (packetData == null || packetData.length < offset + 3) {
			setPixelCount(number);
		}
		packetData[offset] = pixelColor.getRed();
		packetData[offset + 1] = pixelColor.getGreen();
		packetData[offset + 2] = pixelColor.getBlue();
	}

	public void setPixel(int number, int color) {
		int offset = 4 + number * 3;
		if (packetData == null || packetData.length < offset + 3) {
			setPixelCount(number);
		}

		packetData[offset] = (byte) (color >> 16);
		packetData[offset + 1] = (byte) (color >> 8);
		packetData[offset + 2] = (byte) color;
	}

	public void writePixels() {
		if (packetData == null || packetData.length == 0) {
			// No pixel buffer
			return;
		}
		if (output == null) {
			// Try to (re)connect
			connect();
		}
		if (output == null) {
			return;
		}

		try {
			output.write(packetData);
		} catch (Exception e) {
			close();
		}
	}

	// Enable or disable dithering. Dithering avoids the "stair-stepping"
	// artifact and increases color
	// resolution by quickly jittering between adjacent 8-bit brightness levels
	// about 400 times a second.
	// Dithering is on by default.
	public void setDithering(boolean enabled) {
		if (enabled)
			firmwareConfig &= ~0x01;
		else
			firmwareConfig |= 0x01;
		sendFirmwareConfigPacket();
	}

	// Enable or disable frame interpolation. Interpolation automatically blends
	// between consecutive frames
	// in hardware, and it does so with 16-bit per channel resolution. Combined
	// with dithering, this helps make
	// fades very smooth. Interpolation is on by default.
	public void setInterpolation(boolean enabled) {
		if (enabled)
			firmwareConfig &= ~0x02;
		else
			firmwareConfig |= 0x02;
		sendFirmwareConfigPacket();
	}

	// Put the Fadecandy onboard LED under automatic control. It blinks any time
	// the firmware processes a packet.
	// This is the default configuration for the LED.
	public void statusLedAuto() {
		firmwareConfig &= 0x0C;
		sendFirmwareConfigPacket();
	}

	// Manually turn the Fadecandy onboard LED on or off. This disables
	// automatic LED control.
	public void setStatusLed(boolean on) {
		firmwareConfig |= 0x04; // Manual LED control
		if (on)
			firmwareConfig |= 0x08;
		else
			firmwareConfig &= ~0x08;
		sendFirmwareConfigPacket();
	}

	// Set the color correction parameters
	public void setColorCorrection(float gamma, float red, float green, float blue) {
		colorCorrection = "{ \"gamma\": " + gamma + ", \"whitepoint\": [" + red + "," + green + "," + blue + "]}";
		sendColorCorrectionPacket();
	}

	// Set custom color correction parameters from a string
	public void setColorCorrection(String s) {
		colorCorrection = s;
		sendColorCorrectionPacket();
	}

	private void connect() {
		// Try to connect to the OPC server. This normally happens automatically
		// in draw()
		try {
			socket = new Socket(host, port);
			socket.setTcpNoDelay(true);
			output = socket.getOutputStream();
			System.out.println("Connected to OPC server");
		} catch (ConnectException e) {
			close();
		} catch (IOException e) {
			close();
		}

		sendColorCorrectionPacket();
		sendFirmwareConfigPacket();
	}

	public void close() {
		// Destroy the socket. Called internally when we've disconnected.
		if (output != null) {
			try {
				output.close();
			} catch (IOException e) {
				// silence is golden
			}
			System.out.println("Disconnected from OPC server");
		}
		if (socket != null) {
			try {
				socket.close();
			} catch (IOException e) {
				// silence is golden
			}
		}

		socket = null;
		output = null;
	}

	// Send a packet with the current color correction settings
	private void sendColorCorrectionPacket() {
		if (colorCorrection == null) {
			// No color correction defined
			return;
		}
		if (output == null) {
			// We'll do this when we reconnect
			return;
		}

		byte[] content = colorCorrection.getBytes();
		int packetLen = content.length + 4;
		byte[] header = new byte[8];
		header[0] = 0; // Channel (reserved)
		header[1] = (byte) 0xFF; // Command (System Exclusive)
		header[2] = (byte) (packetLen >> 8);
		header[3] = (byte) (packetLen & 0xFF);
		header[4] = 0x00; // System ID high byte
		header[5] = 0x01; // System ID low byte
		header[6] = 0x00; // Command ID high byte
		header[7] = 0x01; // Command ID low byte

		try {
			output.write(header);
			output.write(content);
		} catch (Exception e) {
			close();
		}
	}

	// Send a packet with the current firmware configuration settings
	private void sendFirmwareConfigPacket() {
		if (output == null) {
			// We'll do this when we reconnect
			return;
		}

		byte[] packet = new byte[9];
		packet[0] = 0; // Channel (reserved)
		packet[1] = (byte) 0xFF; // Command (System Exclusive)
		packet[2] = 0; // Length high byte
		packet[3] = 5; // Length low byte
		packet[4] = 0x00; // System ID high byte
		packet[5] = 0x01; // System ID low byte
		packet[6] = 0x00; // Command ID high byte
		packet[7] = 0x02; // Command ID low byte
		packet[8] = firmwareConfig;

		try {
			output.write(packet);
		} catch (Exception e) {
			close();
		}
	}

	protected void finalize() throws Throwable {
		clear();
		close();
	};

	public void clear() {
		if (packetData != null) {
			for (int i = 4; i < packetData.length; i++) {
				packetData[i] = 0;
			}
			writePixels();
		}
	}

}
