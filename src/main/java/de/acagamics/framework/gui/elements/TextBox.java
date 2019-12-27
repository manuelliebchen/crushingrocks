package de.acagamics.framework.gui.elements;

import de.acagamics.constants.DesignConstants;
import de.acagamics.framework.gui.interfaces.ALIGNMENT;
import de.acagamics.framework.gui.interfaces.Alignable;
import de.acagamics.framework.types.Vec2f;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * @author Gerd Schmidt (gerd.schmidt@acagamics.de) Image buttons with customs
 *         images and text. Registers if a user pressed on it.
 */
public class TextBox extends Alignable {

	// Drawing status
	protected String text = "";
	protected Color textColor = DesignConstants.FOREGROUND_COLOR;
	protected Vec2f size = Vec2f.ZERO();
	protected Font font = DesignConstants.STANDART_FONT;
	protected ALIGNMENT textAlignment = ALIGNMENT.CENTER;

	/**
	 * Default button constructor with default buttons images and text color
	 * (black).
	 * 
	 * @param relativPosition   The relativ position where the button will be drawn.
	 * @param text The Text, which will be displayed on the button.
	 */
	public TextBox(Vec2f relativPosition, String text) {
		super(relativPosition);
		this.text = text;
		calcFontTextSize();
	}

	public TextBox setFont(Font font) {
		this.font = font;
		calcFontTextSize();
		return this;
	}

	public TextBox setTextAlignment(ALIGNMENT textAlignment) {
		this.textAlignment = textAlignment;
		return this;
	}

	public TextBox setTextColor(Color textColor) {
		this.textColor = textColor;
		return this;
	}

	/**
	 * Change text of this TextBox.
	 * 
	 * @see #calcFontTextSize
	 * @param text The new Text of this TextBox
	 * @return this {@link TextBox} for further modifications.
	 */
	public TextBox setText(String text) {
		this.text = text;
		return this;
	}

	/**
	 * @param context GraphicsContext for rendering the state when active.
	 */
	public void draw(GraphicsContext context) {
		Vec2f position = getAlignedPosition(context);
		position = position.add(size.mult(-(textAlignment.getValue()), 0.5f));

		context.setFont(font);
		context.setFill(textColor);
		context.fillText(text, position.getX(), position.getY());

	}

	/**
	 * Updates buttonTextSize and centeredPosition if font oder text has changed
	 * 
	 * @see #setFont
	 * @see #setText
	 */
	protected void calcFontTextSize() {
		Text jfxtext = new Text(text);
		jfxtext.setFont(font);

		size = new Vec2f((float) jfxtext.getLayoutBounds().getWidth(), (float) jfxtext.getLayoutBounds().getHeight());
	}
}
