package de.acagamics.crushingrocks.rendering;

import de.acagamics.crushingrocks.logic.Map;
import de.acagamics.framework.resources.ResourceManager;
import de.acagamics.framework.ui.interfaces.IDrawable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Background implements IDrawable {

	Image image;
	Color tophalf = Color.web("#8fe3ff");
	Color bottomhalf = Color.web("#84c750");
	
	int pixelAbove = 70;
	float height;

	MapRendering mapRendering;
	
	public Background(int pixelAbove, float height, Map map) {
		this.pixelAbove = pixelAbove;

		this.height = height;

		image = ResourceManager.getInstance().loadImage("Hintergrund.png");

		if(map != null) {
			mapRendering = new MapRendering(map);
		}
	}
	
	@Override
	public void draw(GraphicsContext context) {
		Canvas canvas = context.getCanvas();

		int pixelHight = (int) ( canvas.getHeight() * height);
		int pixelWidth = (int) (pixelHight * image.getWidth() / image.getHeight());
		
		context.setFill(bottomhalf);
		context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
		context.setFill(tophalf);
		context.fillRect(0, 0, canvas.getWidth(), (double) pixelAbove + (double) pixelHight / 2);

		context.drawImage(image, (canvas.getWidth() - pixelWidth)/ 2, pixelAbove, pixelWidth, pixelHight);
		context.drawImage(image, (canvas.getWidth() - pixelWidth)/ 2 - pixelWidth, pixelAbove, pixelWidth, pixelHight);
		context.drawImage(image, (canvas.getWidth()- pixelWidth)/ 2 + pixelWidth, pixelAbove, pixelWidth, pixelHight);

		if(mapRendering != null) {
			context.save();
			context.translate(0, (double) pixelAbove - 50);
			mapRendering.draw(context);
			context.restore();
		}
	}

	public MapRendering getMapRendering() {
		return mapRendering;
	}

}
