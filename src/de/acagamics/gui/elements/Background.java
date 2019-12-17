package de.acagamics.gui.elements;

import de.acagamics.gui.interfaces.IDrawable;
import de.acagamics.gui.rendering.assetmanagment.AssetManager;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Background implements IDrawable {

	Image image = AssetManager.getInstance().loadImage("Hintergrund.png");
	Color tophalf = Color.web("#8fe3ff");
	Color bottomhalf = Color.web("#84c750");
	
	float position = 0.1f;
	
	@Override
	public void draw(GraphicsContext context) {
		Canvas canvas = context.getCanvas();
		
		int pixel_position = (int) (canvas.getHeight() * position);
		int pixel_hight = (int) (canvas.getWidth() *  image.getHeight() / image.getWidth());
		
		context.setFill(bottomhalf);
		context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
		context.setFill(tophalf);
		context.fillRect(0, 0, canvas.getWidth(), pixel_position);
		
		
		context.drawImage(image, 0, canvas.getHeight() * position - pixel_hight / 2, canvas.getWidth(), pixel_hight);
	}

}
