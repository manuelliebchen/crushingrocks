package Client.GUI.Elements;

import Client.GUI.States.Interfaces.IDrawable;
import Constants.DesignConstants;
import Constants.DesignConstants.Alignment;
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
public final class TextBox implements IDrawable {

	// Drawing status
	private String buttonText;
	private Color textColor = DesignConstants.FOREGROUND_COLOR;
	private Point2D relativPosition;
	private Point2D size;
	private Font font = DesignConstants.STANDART_FONT;
	private Alignment verticalAlignment = Alignment.LEFT;
	private Alignment horizontalAlignment = Alignment.TOP;

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
	
	public TextBox setVerticalAlignment(Alignment verticalAlignment) {
		this.verticalAlignment = verticalAlignment;
		return this;
	}
	
	public TextBox setHorizontalAlignment(Alignment horizontalAlignment) {
		this.horizontalAlignment = horizontalAlignment;
		return this;
	}
	
	public TextBox setTextColor(Color textColor) {
		this.textColor = textColor;
		return this;
	}
	
	public TextBox setFont(Font font) {
		this.font = font;
		calcFontTextSize();
		return this;
	}

	/**
	 * Updates buttonTextSize and centeredPosition if font oder text has changed
	 * 
	 * @see changeFont(Font font)
	 * @see changeText(String buttonText)
	 */
	private void calcFontTextSize() {
		Text text = new Text(buttonText);
		text.setFont(font);

		size = new Point2D(text.getLayoutBounds().getWidth(), text.getLayoutBounds().getHeight());
	}

	/**
	 * Change text of button. Updates text properties
	 * 
	 * @see calcButtonTextProperties()
	 * @param buttonText
	 */
	public void changeText(String buttonText) {
		this.buttonText = buttonText;
	}

	/**
	 * Change color of button text.
	 * 
	 * @param color
	 */
	public void changeTextColor(Color color) {
		this.textColor = color;
	}

	/**
	 * Draws the button. First button image (imgUp,imgDown,imgInActive). Second:
	 * button text
	 * 
	 * @param graphics
	 */
	public void draw(GraphicsContext graphics) {

		Canvas canvas = graphics.getCanvas();

		double drawingPositionX = 0;
		double drawingPositionY = 0;

		if (verticalAlignment == Alignment.LEFT) {
			drawingPositionX = relativPosition.getX();
		} else if (verticalAlignment == Alignment.CENTER) {
			drawingPositionX = (relativPosition.getX() + canvas.getWidth() - size.getX()) / 2;
		} else if (verticalAlignment == Alignment.RIGHT) {
			drawingPositionX = (canvas.getWidth() - relativPosition.getX() - size.getX());
		}

		if (horizontalAlignment == Alignment.TOP) {
			drawingPositionY = relativPosition.getY() + size.getY();
		} else if (horizontalAlignment == Alignment.CENTER) {
			drawingPositionY = (relativPosition.getY() + canvas.getHeight() - size.getY()) / 2;
		} else if (horizontalAlignment == Alignment.BOTTOM) {
			drawingPositionY = (canvas.getHeight() - relativPosition.getY() - size.getY());
		}

		graphics.setFont(font);
		graphics.setFill(textColor);
		graphics.fillText(buttonText, drawingPositionX, drawingPositionY);

	}
}
