package de.acagamics.gui.elements;

import java.util.function.Supplier;

import de.acagamics.game.types.Vec2f;
import de.acagamics.gui.interfaces.IDrawable;
import javafx.scene.canvas.GraphicsContext;

/**
 * Dynamic TextBox.
 * 
 * @author Manuel Liebchen
 */
public final class DynamicTextBox extends TextBox implements IDrawable {

	// Drawing status
	private Supplier<String> textSupplier;

	public DynamicTextBox(Vec2f relativPosition, Supplier<String> textSupplier) {
		super(relativPosition, textSupplier.get());
		this.text = textSupplier.get();
		this.textSupplier = textSupplier;
		
		calcFontTextSize();
	}

	/**
	 * Draws the button. First button image (imgUp,imgDown,imgInActive). Second:
	 * button text
	 * 
	 * @param context GraphicsContext for rendering the state when active.
	 */
	@Override
	public void draw(GraphicsContext context) {
		this.text = textSupplier.get();
		calcFontTextSize();
		
		super.draw(context);
	}
}
