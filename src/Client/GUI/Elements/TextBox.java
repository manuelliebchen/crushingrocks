package Client.GUI.Elements;

import Client.Rendering.Drawing.ImageManager;
import Constants.ClientConstants.Alignment;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * @author Gerd Schmidt (gerd.schmidt@acagamics.de) Image buttons with customs
 *         images and text. Registers if a user pressed on it.
 */
public final class TextBox {

	// Drawing status
	private String buttonText;
	private Color textColor;
	private Point2D relativPosition;
	private Point2D position;
	private Font font = Font.font(Font.getDefault().getFamily(), FontWeight.BOLD, 24);
	private Alignment verticalAlignment = Alignment.LEFT;
	private Alignment horizontalAlignment = Alignment.UP;

	/**
	 * Default button constructor with default buttons images and text color
	 * (black).
	 * 
	 * @param position   The position where the button will be drawn (top-left).
	 * @param size       The size of the button (e.g. image size).
	 * @param buttonText The Text, which will be displayed on the button.
	 */
	public TextBox(Point2D relativPosition, String buttonText) {
		this(relativPosition, buttonText, Alignment.LEFT, Alignment.UP);
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
		this(relativPosition, buttonText, ImageManager.getInstance().loadImage("buttons/defaultButtonUp.png"),
				ImageManager.getInstance().loadImage("buttons/defaultButtonDown.png"),
				ImageManager.getInstance().loadImage("buttons/defaultButtonInActive.png"), Color.BLACK, verticalAlignment, horizontalAlignment);
	}

	/**
	 * Complex Button constructor with default buttons and text color.
	 * 
	 * @param position        The position where the button will be drawn
	 *                        (top-left).
	 * @param size            The size of the button (e.g. image size).
	 * @param buttonText      The Text, which will be displayed on the button.
	 * @param imageButtonUp   The image of the button which will be shown if there
	 *                        is no hover (default view).
	 * @param imageButtonDown The image of the button which will be shown if there
	 *                        is a hover (mouse hover view).
	 * @param imageInActive   The image of the button which will be shown if the
	 *                        button is disabled.
	 * @param textColor       The text color of the button
	 */
	public TextBox(Point2D relativPosition, String buttonText, Image imageButtonUp, Image imageButtonDown,
			Image imageInActive, Color textColor, Alignment verticalAlignment, Alignment horizontalAlignment) {
		this.buttonText = buttonText;
		this.relativPosition = relativPosition;
		this.position = relativPosition;
		this.textColor = textColor;

		this.verticalAlignment = verticalAlignment;
		this.horizontalAlignment = horizontalAlignment;
	}

	/**
	 * Change font of button. Updates text properties
	 * 
	 * @see calcButtonTextProperties()
	 * @param font
	 */
	public void changeFont(Font font) {
		this.font = font;
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
			drawingPositionX = (relativPosition.getX() + canvas.getWidth()) / 2;
		} else if (verticalAlignment == Alignment.RIGHT) {
			drawingPositionX = (canvas.getWidth() - relativPosition.getX());
		}

		if (horizontalAlignment == Alignment.UP) {
			drawingPositionY = relativPosition.getY();
		} else if (horizontalAlignment == Alignment.CENTER) {
			drawingPositionY = (relativPosition.getY() + canvas.getHeight()) / 2;
		} else if (horizontalAlignment == Alignment.DOWN) {
			drawingPositionY = (canvas.getHeight() - relativPosition.getY());
		}

		position = new Point2D(drawingPositionX, drawingPositionY);

		graphics.setFont(font);
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
