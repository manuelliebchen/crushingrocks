package de.acagamics.client.gui.elements;

import java.util.function.Supplier;

import de.acagamics.client.gui.interfaces.IDrawable;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

/**
 * Dynamic TextBox.
 * 
 * @author Manuel Liebchen
 */
public final class DynamicTextBox extends TextBox implements IDrawable {

	// Drawing status
	private Supplier<String> textSupplier;

	public DynamicTextBox(Point2D relativPosition, Supplier<String> textSupplier) {
		this.buttonText = textSupplier.get();
		this.relativPosition = relativPosition;
		this.textSupplier = textSupplier;
		
		calcFontTextSize();
	}

	/**
	 * Draws the button. First button image (imgUp,imgDown,imgInActive). Second:
	 * button text
	 * 
	 * @param graphics
	 */
	@Override
	public void draw(GraphicsContext context) {
		this.buttonText = textSupplier.get();
		calcFontTextSize();
		
		super.draw(context);
	}
}
