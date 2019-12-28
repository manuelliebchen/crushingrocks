package de.acagamics.framework.ui.interfaces;

import de.acagamics.framework.types.Vec2f;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public abstract class Alignable extends GUIElement {

	private final Vec2f relativPosition;
	protected ALIGNMENT verticalAlignment = ALIGNMENT.LEFT;
	protected ALIGNMENT horizontalAlignment = ALIGNMENT.TOP;

	protected Alignable(Vec2f relativPosition) {
		this.relativPosition = relativPosition;
	}

	public Alignable setVerticalAlignment(ALIGNMENT verticalAlignment) {
		this.verticalAlignment = verticalAlignment;
		return this;
	}

	public Alignable setHorizontalAlignment(ALIGNMENT horizontalAlignment) {
		this.horizontalAlignment = horizontalAlignment;
		return this;
	}

	protected Vec2f getAlignedPosition(GraphicsContext graphics) {
		Canvas canvas = graphics.getCanvas();
		return new Vec2f((float) (relativPosition.getX() + canvas.getWidth() * verticalAlignment.getValue()),
				(float) (relativPosition.getY() + canvas.getHeight() * horizontalAlignment.getValue()));
	}
}
