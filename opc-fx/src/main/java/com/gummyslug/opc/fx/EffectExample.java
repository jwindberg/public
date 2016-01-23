package com.gummyslug.opc.fx;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.gummyslug.oopsie.opc.OpcClient;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.image.WritablePixelFormat;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class EffectExample extends Application {
	private static final Random RANDOM = new Random();
	private static final List<Color> colors = getColors();

	private static final int WIDTH = 500;
	private static final int HEIGHT = 500;

	private PixelGrid pixelGrid = new PixelGrid(WIDTH, HEIGHT, 8, 8, false);
	private OpcClient opcClient = new OpcClient(64);

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Hello World");
		Group root = new Group();
		Scene scene = new Scene(root, WIDTH, HEIGHT);
		Canvas canvas = new Canvas(WIDTH, HEIGHT);
		Button button = new Button();
		button.setLayoutX(100);
		button.setLayoutY(80);
		button.setText("Hello World!");

		button.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				drawShapes(canvas);
				writePixels(canvas);
				WritableImage snapshot = scene.snapshot(null);
				PixelReader pixelReader = snapshot.getPixelReader();
				WritablePixelFormat<IntBuffer> writablePixelFormat = WritablePixelFormat.getIntArgbInstance();
				int[] pixels = new int[WIDTH * HEIGHT];
				pixelReader.getPixels(0, 0, WIDTH, HEIGHT, writablePixelFormat, pixels, 0, WIDTH);

				int pixel = 0;
				for (int pixelLocation : pixelGrid.getPixelLocations()) {
					int color = pixels[pixelLocation];
					int alpha = (color >>> 24);
					int red = (color >>> 16 & 0xFF);
					int green = (color >>> 8 & 0xFF);
					int blue = (color & 0xFF);
					System.out.println(red + "\t" + green + "\t" + blue + "\t" + alpha);
					opcClient.setPixel(pixel, color);
					pixel++;
				}
				opcClient.writePixels();

			}

		});

		root.getChildren().add(canvas);
		root.getChildren().add(button);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void writePixels(Canvas canvas) {

	}

	private void drawShapes(Canvas canvas) {

		int x = RANDOM.nextInt((int) canvas.getWidth());
		int y = RANDOM.nextInt((int) canvas.getHeight());
		int width = RANDOM.nextInt((int) canvas.getWidth());
		int height = RANDOM.nextInt((int) canvas.getHeight());

		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setFill(selectRandomColor());
		// gc.setStroke(Color.BLUE);
		gc.setLineWidth(5);

		gc.fillOval(x, y, height, width);
	}

	private Color selectRandomColor() {
		return colors.get(RANDOM.nextInt(colors.size() - 1));
	}

	private static List<Color> getColors() {
		List<Color> colors = new ArrayList<>();
		colors.add(Color.ALICEBLUE);
		colors.add(Color.ANTIQUEWHITE);
		colors.add(Color.AQUA);
		colors.add(Color.AQUAMARINE);
		colors.add(Color.AZURE);
		colors.add(Color.BEIGE);
		colors.add(Color.BISQUE);
		colors.add(Color.BLACK);
		colors.add(Color.BLANCHEDALMOND);
		colors.add(Color.BLUE);
		colors.add(Color.BLUEVIOLET);
		colors.add(Color.BROWN);
		colors.add(Color.BURLYWOOD);
		colors.add(Color.CADETBLUE);
		colors.add(Color.CHARTREUSE);
		colors.add(Color.CHOCOLATE);
		colors.add(Color.CORAL);
		colors.add(Color.CORNFLOWERBLUE);
		colors.add(Color.CORNSILK);
		colors.add(Color.CRIMSON);
		colors.add(Color.CYAN);
		colors.add(Color.DARKBLUE);
		colors.add(Color.DARKCYAN);
		colors.add(Color.DARKGOLDENROD);
		colors.add(Color.DARKGRAY);
		colors.add(Color.DARKGREEN);
		colors.add(Color.DARKGREY);
		colors.add(Color.DARKKHAKI);
		colors.add(Color.DARKMAGENTA);
		colors.add(Color.DARKOLIVEGREEN);
		colors.add(Color.DARKORANGE);
		colors.add(Color.DARKORCHID);
		colors.add(Color.DARKRED);
		colors.add(Color.DARKSALMON);
		colors.add(Color.DARKSEAGREEN);
		colors.add(Color.DARKSLATEBLUE);
		colors.add(Color.DARKSLATEGRAY);
		colors.add(Color.DARKSLATEGREY);
		colors.add(Color.DARKTURQUOISE);
		colors.add(Color.DARKVIOLET);
		colors.add(Color.DEEPPINK);
		colors.add(Color.DEEPSKYBLUE);
		colors.add(Color.DIMGRAY);
		colors.add(Color.DIMGREY);
		colors.add(Color.DODGERBLUE);
		colors.add(Color.FIREBRICK);
		colors.add(Color.FLORALWHITE);
		colors.add(Color.FORESTGREEN);
		colors.add(Color.FUCHSIA);
		colors.add(Color.GAINSBORO);
		colors.add(Color.GHOSTWHITE);
		colors.add(Color.GOLD);
		colors.add(Color.GOLDENROD);
		colors.add(Color.GRAY);
		colors.add(Color.GREEN);
		colors.add(Color.GREENYELLOW);
		colors.add(Color.GREY);
		colors.add(Color.HONEYDEW);
		colors.add(Color.HOTPINK);
		colors.add(Color.INDIANRED);
		colors.add(Color.INDIGO);
		colors.add(Color.IVORY);
		colors.add(Color.KHAKI);
		colors.add(Color.LAVENDER);
		colors.add(Color.LAVENDERBLUSH);
		colors.add(Color.LAWNGREEN);
		colors.add(Color.LEMONCHIFFON);
		colors.add(Color.LIGHTBLUE);
		colors.add(Color.LIGHTCORAL);
		colors.add(Color.LIGHTCYAN);
		colors.add(Color.LIGHTGOLDENRODYELLOW);
		colors.add(Color.LIGHTGRAY);
		colors.add(Color.LIGHTGREEN);
		colors.add(Color.LIGHTGREY);
		colors.add(Color.LIGHTPINK);
		colors.add(Color.LIGHTSALMON);
		colors.add(Color.LIGHTSEAGREEN);
		colors.add(Color.LIGHTSKYBLUE);
		colors.add(Color.LIGHTSLATEGRAY);
		colors.add(Color.LIGHTSLATEGREY);
		colors.add(Color.LIGHTSTEELBLUE);
		colors.add(Color.LIGHTYELLOW);
		colors.add(Color.LIME);
		colors.add(Color.LIMEGREEN);
		colors.add(Color.LINEN);
		colors.add(Color.MAGENTA);
		colors.add(Color.MAROON);
		colors.add(Color.MEDIUMAQUAMARINE);
		colors.add(Color.MEDIUMBLUE);
		colors.add(Color.MEDIUMORCHID);
		colors.add(Color.MEDIUMPURPLE);
		colors.add(Color.MEDIUMSEAGREEN);
		colors.add(Color.MEDIUMSLATEBLUE);
		colors.add(Color.MEDIUMSPRINGGREEN);
		colors.add(Color.MEDIUMTURQUOISE);
		colors.add(Color.MEDIUMVIOLETRED);
		colors.add(Color.MIDNIGHTBLUE);
		colors.add(Color.MINTCREAM);
		colors.add(Color.MISTYROSE);
		colors.add(Color.MOCCASIN);
		colors.add(Color.NAVAJOWHITE);
		colors.add(Color.NAVY);
		colors.add(Color.OLDLACE);
		colors.add(Color.OLIVE);
		colors.add(Color.OLIVEDRAB);
		colors.add(Color.ORANGE);
		colors.add(Color.ORANGERED);
		colors.add(Color.ORCHID);
		colors.add(Color.PALEGOLDENROD);
		colors.add(Color.PALEGREEN);
		colors.add(Color.PALETURQUOISE);
		colors.add(Color.PALEVIOLETRED);
		colors.add(Color.PAPAYAWHIP);
		colors.add(Color.PEACHPUFF);
		colors.add(Color.PERU);
		colors.add(Color.PINK);
		colors.add(Color.PLUM);
		colors.add(Color.POWDERBLUE);
		colors.add(Color.PURPLE);
		colors.add(Color.RED);
		colors.add(Color.ROSYBROWN);
		colors.add(Color.ROYALBLUE);
		colors.add(Color.SADDLEBROWN);
		colors.add(Color.SALMON);
		colors.add(Color.SANDYBROWN);
		colors.add(Color.SEAGREEN);
		colors.add(Color.SEASHELL);
		colors.add(Color.SIENNA);
		colors.add(Color.SILVER);
		colors.add(Color.SKYBLUE);
		colors.add(Color.SLATEBLUE);
		colors.add(Color.SLATEGRAY);
		colors.add(Color.SLATEGREY);
		colors.add(Color.SNOW);
		colors.add(Color.SPRINGGREEN);
		colors.add(Color.STEELBLUE);
		colors.add(Color.TAN);
		colors.add(Color.TEAL);
		colors.add(Color.THISTLE);
		colors.add(Color.TOMATO);
		colors.add(Color.TRANSPARENT);
		colors.add(Color.TURQUOISE);
		colors.add(Color.VIOLET);
		colors.add(Color.WHEAT);
		colors.add(Color.WHITE);
		colors.add(Color.WHITESMOKE);
		colors.add(Color.YELLOW);
		colors.add(Color.YELLOWGREEN);
		return colors;
	}

}
