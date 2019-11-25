package de.acagamics.client.gui.elements;

import de.acagamics.constants.DesignConstants;
import de.acagamics.constants.DesignConstants.ALINGNMENT;
import de.acagamics.game.types.Vec2f;
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
	protected String buttonText = "";
	protected Color textColor = DesignConstants.FOREGROUND_COLOR;
	protected Vec2f size = Vec2f.ZERO();
	protected Font font = DesignConstants.STANDART_FONT;
	protected ALINGNMENT textAlignment;

	/**
	 * Default button constructor with default buttons images and text color
	 * (black).
	 * 
	 * @param position   The position where the button will be drawn (top-left).
	 * @param buttonText The Text, which will be displayed on the button.
	 */
	public TextBox(Vec2f relativPosition, String buttonText) {
		super(relativPosition);
		this.buttonText = buttonText;
		calcFontTextSize();
	}

	public TextBox setFont(Font font) {
		this.font = font;
		calcFontTextSize();
		return this;
	}

	public TextBox setTextAlignment(ALINGNMENT textAlignment) {
		this.textAlignment = textAlignment;
		return this;
	}

	public TextBox setTextColor(Color textColor) {
		this.textColor = textColor;
		return this;
	}

	/**
	 * Change text of button. Updates text properties
	 * 
	 * @see calcButtonTextProperties()
	 * @param buttonText
	 */
	public TextBox setText(String buttonText) {
		this.buttonText = buttonText;
		return this;
	}

	/**
	 * @param context
	 */
	public void draw(GraphicsContext context) {
		Vec2f position = getAlignedPosition(context);
		position = position.add(size.mult(-0.5f));

		context.setFont(font);
		context.setFill(textColor);
		context.fillText(buttonText, position.getX(), position.getY());

	}

	/**
	 * Updates buttonTextSize and centeredPosition if font oder text has changed
	 * 
	 * @see changeFont(Font font)
	 * @see changeText(String buttonText)
	 */
	protected void calcFontTextSize() {
		Text text = new Text(buttonText);
		text.setFont(font);

		size = new Vec2f((float) text.getLayoutBounds().getWidth(), (float) text.getLayoutBounds().getHeight());
	}
}
