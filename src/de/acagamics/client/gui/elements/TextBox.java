package de.acagamics.client.gui.elements;

import de.acagamics.client.gui.interfaces.IDrawable;
import de.acagamics.constants.DesignConstants;
import de.acagamics.constants.DesignConstants.ALINGMENT;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * @author Gerd Schmidt (gerd.schmidt@acagamics.de) Image buttons with customs
 *         images and text. Registers if a user pressed on it.
 */
public class TextBox implements IDrawable {

	// Drawing status
	protected String buttonText = "";
	protected Color textColor = DesignConstants.FOREGROUND_COLOR;
	protected Point2D relativPosition = Point2D.ZERO;
	protected Point2D size = Point2D.ZERO;
	protected Font font = DesignConstants.STANDART_FONT;
	
	protected ALINGMENT verticalAlignment = ALINGMENT.LEFT;
	protected float verticalAlignmentFactor = 0;
	protected ALINGMENT horizontalAlignment = ALINGMENT.TOP;
	protected float horizontalAlignmentFactor = 0;
	protected ALINGMENT textAlignment = ALINGMENT.LEFT;
	protected float textAlignmentFactor = 0.5f;
	
	
	/**
	 * Needet for DynamicTextBox!
	 * Because the superconsturctor can't run recalculateAlignment for DynamicTextbox
	 * TODO Find better solution
	 */
	protected TextBox() {}

	/**
	 * Default button constructor with default buttons images and text color
	 * (black).
	 * 
	 * @param position   The position where the button will be drawn (top-left).
	 * @param buttonText The Text, which will be displayed on the button.
	 */
	public TextBox(Point2D relativPosition, String buttonText) {
		this.buttonText = buttonText;
		this.relativPosition = relativPosition;
		calcFontTextSize();
	}

	public TextBox setVerticalAlignment(ALINGMENT verticalAlignment) {
		this.verticalAlignment = verticalAlignment;
		recalculateAlignment();
		return this;
	}

	public TextBox setHorizontalAlignment(ALINGMENT horizontalAlignment) {
		this.horizontalAlignment = horizontalAlignment;
		recalculateAlignment();
		return this;
	}

	public TextBox setFont(Font font) {
		this.font = font;
		recalculateAlignment();
		return this;
	}

	public TextBox setTextAlignment(ALINGMENT textAlignment) {
		this.textAlignment = textAlignment;
		recalculateAlignment();
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
	 * Updates buttonTextSize and centeredPosition if font oder text has changed
	 * 
	 * @see changeFont(Font font)
	 * @see changeText(String buttonText)
	 */
	protected void recalculateAlignment() {
		calcFontTextSize();

		if (verticalAlignment == ALINGMENT.LEFT) {
			verticalAlignmentFactor = 0;
		} else if (verticalAlignment == ALINGMENT.CENTER) {
			verticalAlignmentFactor = 0.5f;
		} else if (verticalAlignment == ALINGMENT.RIGHT) {
			verticalAlignmentFactor = 1;
		}

		if (horizontalAlignment == ALINGMENT.TOP) {
			horizontalAlignmentFactor = 0;
		} else if (horizontalAlignment == ALINGMENT.CENTER) {
			horizontalAlignmentFactor = 0.5f;
		} else if (horizontalAlignment == ALINGMENT.BOTTOM) {
			horizontalAlignmentFactor = 1;
		}

//		if (textAlignment == ALINGMENT.LEFT) {
//			textAlignmentFactor = 0;
//		} else if (textAlignment == ALINGMENT.CENTER) {
//			textAlignmentFactor = 0.5f;
//		} else if (textAlignment == ALINGMENT.RIGHT) {
//			textAlignmentFactor = 1;
//		}
	}

	/**
	 * @param context
	 */
	public void draw(GraphicsContext context) {

		Canvas canvas = context.getCanvas();

		double drawingPositionX = relativPosition.getX()
				- size.getX() * textAlignmentFactor
				+ canvas.getWidth() * verticalAlignmentFactor;
		double drawingPositionY = relativPosition.getY() - size.getY() * 0.5
				+ canvas.getHeight() * horizontalAlignmentFactor;

		context.setFont(font);
		context.setFill(textColor);
		context.fillText(buttonText, drawingPositionX, drawingPositionY);

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

		size = new Point2D(text.getLayoutBounds().getWidth(), text.getLayoutBounds().getHeight());
	}
}
