package Client.GUI.Elements;

import Client.GUI.States.Interfaces.IDrawable;
import Constants.DesignConstants;
import Constants.DesignConstants.Alignment;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * @author Gerd Schmidt (gerd.schmidt@acagamics.de) Image buttons with customs
 *         images and text. Registers if a user pressed on it.
 */
public final class TextBox implements IDrawable {

	// Drawing status
	private String buttonText;
	private Color textColor;
	private Point2D relativPosition;
	private Point2D position;
	private Point2D size;
	private Alignment verticalAlignment = Alignment.LEFT;
	private Alignment horizontalAlignment = Alignment.TOP;

	/**
	 * Default button constructor with default buttons images and text color
	 * (black).
	 * 
	 * @param position   The position where the button will be drawn (top-left).
	 * @param size       The size of the button (e.g. image size).
	 * @param buttonText The Text, which will be displayed on the button.
	 */
	public TextBox(Point2D relativPosition, String buttonText) {
		this(relativPosition, buttonText, Alignment.LEFT, Alignment.TOP);
	}

	/**
	 * Default button constructor with default buttons images and text color
	 * (black).
	 * 
	 * @param position   The position where the button will be drawn (top-left).
	 * @param size       The size of the button (e.g. image size).
	 * @param buttonText The Text, which will be displayed on the button.
	 */
	public TextBox(Point2D relativPosition, String buttonText, Alignment verticalAlignment, Alignment horizontalAlignment) {
		this(relativPosition, buttonText, Color.BLACK, verticalAlignment, horizontalAlignment);
	}

	/**
	 * Complex Button constructor with default buttons and text color.
	 * 
	 * @param position        The position where the button will be drawn
	 *                        (top-left).
	 * @param size            The size of the button (e.g. image size).
	 * @param buttonText      The Text, which will be displayed on the button.
	 * @param textColor       The text color of the button
	 */
	public TextBox(Point2D relativPosition, String buttonText, Color textColor, Alignment verticalAlignment, Alignment horizontalAlignment) {
		this.buttonText = buttonText;
		this.relativPosition = relativPosition;
		this.position = relativPosition;
		this.textColor = textColor;
		
		this.verticalAlignment = verticalAlignment;
		this.horizontalAlignment = horizontalAlignment;
		
		calcButtonTextProperties();
	}

	/**
	 * Updates buttonTextSize and centeredPosition if font oder text has changed
	 * 
	 * @see changeFont(Font font)
	 * @see changeText(String buttonText)
	 */
	private void calcButtonTextProperties() {
		Text text = new Text(buttonText);
		text.setFont(DesignConstants.STANDART_FONT);

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

		position = new Point2D(drawingPositionX, drawingPositionY);

		graphics.setFont(DesignConstants.STANDART_FONT);
		graphics.setFill(textColor);
		graphics.fillText(buttonText, position.getX(), position.getY());

	}

	/**
	 * Get the position of the button
	 * 
	 * @return position
	 */
	public Point2D getPosition() {
		return position;
	}

}
