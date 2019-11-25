package de.acagamics.client.gui.elements;

import de.acagamics.constants.DesignConstants.ALINGNMENT;
import de.acagamics.game.types.Vec2f;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public abstract class Alignable extends GUIElement {

	private final Vec2f relativPosition;
	protected ALINGNMENT verticalAlignment = ALINGNMENT.LEFT;
	protected ALINGNMENT horizontalAlignment = ALINGNMENT.TOP;

	protected Alignable(Vec2f relativPosition) {
		this.relativPosition = relativPosition;
	}

	public Alignable setVerticalAlignment(ALINGNMENT verticalAlignment) {
		this.verticalAlignment = verticalAlignment;
		return this;
	}

	public Alignable setHorizontalAlignment(ALINGNMENT horizontalAlignment) {
		this.horizontalAlignment = horizontalAlignment;
		return this;
	}

	protected Vec2f getAlignedPosition(GraphicsContext graphics) {
		Canvas canvas = graphics.getCanvas();
		return new Vec2f((float) (relativPosition.getX() + canvas.getWidth() * verticalAlignment.getValue()),
				(float) (relativPosition.getY() + canvas.getHeight() * horizontalAlignment.getValue()));
	}
}
