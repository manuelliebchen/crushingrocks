package de.acagamics.gui.elements;

import de.acagamics.gui.assetmanagment.AssetManager;
import de.acagamics.gui.interfaces.IDrawable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Background implements IDrawable {

	Image image;
	Color tophalf = Color.web("#8fe3ff");
	Color bottomhalf = Color.web("#84c750");
	
	int pixel_above = 70;
	
	public Background(int pixel_above) {
		this.pixel_above = pixel_above;
		
		image = AssetManager.getInstance().loadImage("Hintergrund.png");
	}
	
	@Override
	public void draw(GraphicsContext context) {
		Canvas canvas = context.getCanvas();
		
		int pixel_hight = (int) (canvas.getWidth() *  image.getHeight() / image.getWidth());
		
		context.setFill(bottomhalf);
		context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
		context.setFill(tophalf);
		context.fillRect(0, 0, canvas.getWidth(), pixel_above + pixel_hight / 2);
		
		
		context.drawImage(image, 0, pixel_above, canvas.getWidth(), pixel_hight);
	}

}
