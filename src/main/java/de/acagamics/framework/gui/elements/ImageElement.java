package de.acagamics.framework.gui.elements;

import de.acagamics.framework.gui.interfaces.Alignable;
import de.acagamics.framework.resourcemanagment.ResourceManager;
import de.acagamics.framework.types.Vec2f;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class ImageElement extends Alignable {

	Image image;
	float size;

	public ImageElement(Vec2f relativPosition, String image, float size) {
		super(relativPosition);
		this.image = ResourceManager.getInstance().loadImage(image);
		this.size = size;
	}

	@Override
	public void draw(GraphicsContext context) {
		Vec2f position = getAlignedPosition(context);
		float ration = (float) (image.getHeight() / image.getWidth());
		context.drawImage(image, position.getX() - size, position.getY() - size * ration, 2 * size, 2 * size * ration);
	}

}
