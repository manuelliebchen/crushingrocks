package Client.GUI.Elements;

import Client.InputManager;
import Client.InputManager.InputMouseListener;
import Client.InputManager.MouseEventType;
import Client.InputManager.MouseKeyEventType;
import Client.GUI.States.Interfaces.IDrawable;
import Client.Rendering.Drawing.ImageManager;
import Constants.DesignConstants;
import Constants.DesignConstants.Alignment;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * @author Gerd Schmidt (gerd.schmidt@acagamics.de) Image buttons with customs
 *         images and text. Registers if a user pressed on it.
 */
public final class Button implements InputMouseListener, IDrawable {

	// Button status
	boolean isEnabled = false;

	// Drawing status
	private String buttonText;
	private Point2D centeredPositioOffset;
	private Color textColor = DesignConstants.FOREGROUND_COLOR;
	private Point2D relativPosition;
	private Point2D position;
	private Point2D size;
	private Image imgUp = ImageManager.getInstance().loadImage("buttons/defaultButtonUp.png");
	private Image imgDown = ImageManager.getInstance().loadImage("buttons/defaultButtonDown.png");
	private Image imgInActive = ImageManager.getInstance().loadImage("buttons/defaultButtonInActive.png");
	private Font font = DesignConstants.STANDART_FONT;
	private Alignment verticalAlignment = Alignment.LEFT;
	private Alignment horizontalAlignment = Alignment.TOP;

	// Mouse status
	private boolean mousePressed = false;

	/**
	 * Default button constructor with default buttons images and text color
	 * (black).
	 * 
	 * @param position   The position where the button will be drawn (top-left).
	 * @param size       The size of the button (e.g. image size).
	 * @param buttonText The Text, which will be displayed on the button.
	 */
	public Button(Point2D relativPosition, Point2D size, String buttonText) {
		this.buttonText = buttonText;
		this.relativPosition = relativPosition;
		this.position = relativPosition;
		this.size = size;

		InputManager.get().addMouseKeyListener(this);
		calcButtonTextProperties();
	}

	public Button setVerticalAlignment(Alignment verticalAlignment) {
		this.verticalAlignment = verticalAlignment;
		return this;
	}

	public Button setHorizontalAlignment(Alignment horizontalAlignment) {
		this.horizontalAlignment = horizontalAlignment;
		return this;
	}

	public Button setTextColor(Color textColor) {
		this.textColor = textColor;
		return this;
	}

	public Button setFont(Font font) {
		this.font = font;
		calcButtonTextProperties();
		return this;
	}

	/**
	 * Updates buttonTextSize and centeredPosition if font oder text has changed
	 * 
	 * @see changeFont(Font font)
	 * @see changeText(String buttonText)
	 */
	private void calcButtonTextProperties() {
		Text text = new Text(buttonText);
		text.setFont(font);

		Point2D buttonTextSize = new Point2D(text.getLayoutBounds().getWidth(), text.getLayoutBounds().getHeight());
		centeredPositioOffset = new Point2D((size.getX() - buttonTextSize.getX()) / 2,
				size.getY() / 2 + buttonTextSize.getY() / 4);
	}

	/**
	 * Change font of button. Updates text properties
	 * 
	 * @see calcButtonTextProperties()
	 * @param font
	 */
	public void changeFont(Font font) {
		this.font = font;
		calcButtonTextProperties();

	}

	/**
	 * Change text of button. Updates text properties
	 * 
	 * @see calcButtonTextProperties()
	 * @param buttonText
	 */
	public void changeText(String buttonText) {
		this.buttonText = buttonText;
		calcButtonTextProperties();
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
	 * Changes if the button is enabled or not. If not -> show button image:
	 * imageInActive and isPressed() is false.
	 * 
	 * @see isPressed()
	 * @param enabled
	 */
	public void setEnabled(boolean enabled) {
		this.isEnabled = enabled;
	}

	/**
	 * Returns true if the button isEnable and the user pressed on it.
	 * 
	 * @return true or false.
	 */
	public boolean isPressed() {
		return this.isEnabled && isMouseOver() && mousePressed;
	}

	/**
	 * Checks if the mouse hovered the button
	 * 
	 * @return true or false
	 */
	private boolean isMouseOver() {
		Point2D pos = InputManager.get().getMousePosition();
		return pos.getX() >= position.getX() && pos.getX() <= position.getX() + size.getX()
				&& pos.getY() >= position.getY() && pos.getY() <= position.getY() + size.getY();
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
			drawingPositionY = relativPosition.getY();
		} else if (horizontalAlignment == Alignment.CENTER) {
			drawingPositionY = (relativPosition.getY() + canvas.getHeight() - size.getY()) / 2;
		} else if (horizontalAlignment == Alignment.BOTTOM) {
			drawingPositionY = (canvas.getHeight() - relativPosition.getY() - size.getY());
		}

		position = new Point2D(drawingPositionX, drawingPositionY);

		if (!this.isEnabled) {
			graphics.drawImage(imgInActive, drawingPositionX, drawingPositionY, size.getX(), size.getY());
		} else if (isMouseOver()) {
			graphics.drawImage(imgDown, drawingPositionX, drawingPositionY, size.getX(), size.getY());
		} else {
			graphics.drawImage(imgUp, drawingPositionX, drawingPositionY, size.getX(), size.getY());
		}

		graphics.setFont(font);
		graphics.setFill(textColor);
		graphics.fillText(buttonText, drawingPositionX + centeredPositioOffset.getX(),
				drawingPositionY + centeredPositioOffset.getY());

	}

	/**
	 * Get the size of the button
	 * 
	 * @return size
	 */
	public Point2D getSize() {
		return size;
	}

	/**
	 * Changes state of mousePressed (LeftButton pressed: mousePressed=true,
	 * LeftButton released: mousePressed=false)
	 */
	@Override
	public void mouseKeyEvent(MouseKeyEventType type, MouseButton code) {
		if (type == MouseKeyEventType.MOUSE_RELEASED) {
			if (code == MouseButton.PRIMARY) {
				this.isEnabled = true;
				mousePressed = false;
			}
		} else if (type == MouseKeyEventType.MOUSE_PRESSED) {
			if (code == MouseButton.PRIMARY) {
				mousePressed = true;
			}
		}

	}

	@Override
	public void mouseEvent(MouseEventType type) {
	}

}
