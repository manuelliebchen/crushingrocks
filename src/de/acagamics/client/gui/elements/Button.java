package de.acagamics.client.gui.elements;

import de.acagamics.client.gui.interfaces.IClickable;
import de.acagamics.client.rendering.assetmanagment.ImageManager;
import de.acagamics.constants.DesignConstants;
import de.acagamics.constants.DesignConstants.ALINGMENT;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * @author Gerd Schmidt (gerd.schmidt@acagamics.de) Image buttons with customs
 *         images and text. Registers if a user pressed on it.
 */
public final class Button implements IClickable {
	
	public static enum BUTTON_TYPE {NORMAL, WIDE, SQUARE};
	
	// Button status
	private boolean isEnabled = true;

	// Drawing status
	private String buttonText;
	private Point2D centeredPositioOffset;
	private Color textColor = DesignConstants.FOREGROUND_COLOR;
	private Point2D relativPosition;
	private Point2D position;
	private Point2D size;
	
//	private BUTTON_TYPE type;

	private Image imgUp = ImageManager.getInstance().loadImage("buttons/defaultButtonUp.png");
	private Image imgDown = ImageManager.getInstance().loadImage("buttons/defaultButtonDown.png");
	private Image imgInActive = ImageManager.getInstance().loadImage("buttons/defaultButtonInActive.png");
	private Font font = DesignConstants.STANDART_FONT;

	protected ALINGMENT verticalAlignment = ALINGMENT.LEFT;
	protected float verticalAlignmentFactor = 0;
	protected ALINGMENT horizontalAlignment = ALINGMENT.TOP;
	protected float horizontalAlignmentFactor = 0;
	
	private KeyCode keycode;

	private Runnable function;

	private boolean isOver;
	
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
	public Button(Point2D relativPosition, BUTTON_TYPE type, String buttonText, Runnable function) {
		this.buttonText = buttonText;
//		this.type = type;
		switch(type) {
		case WIDE:
			size = new Point2D(200, 50);
			break;
		case SQUARE:
			size = new Point2D(50, 50);
			break;
		default:
			size = new Point2D(100, 50);
			break;
		} 
		this.relativPosition = relativPosition.subtract(size.multiply(0.5));
		this.position = this.relativPosition;
		this.function = function;

		calcButtonTextProperties();
	}

	public Button setVerticalAlignment(ALINGMENT verticalAlignment) {
		this.verticalAlignment = verticalAlignment;
		recalculateAlignment();
		return this;
	}

	public Button setHorizontalAlignment(ALINGMENT horizontalAlignment) {
		this.horizontalAlignment = horizontalAlignment;
		recalculateAlignment();
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

	public Button setKeyCode(KeyCode keycode) {
		this.keycode = keycode;
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

	protected void recalculateAlignment() {
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
		return this.isEnabled && isOver && mousePressed;
	}

	/**
	 * Draws the button. First button image (imgUp,imgDown,imgInActive). Second:
	 * button text
	 * 
	 * @param graphics
	 */
	public void draw(GraphicsContext graphics) {

		Canvas canvas = graphics.getCanvas();

		double drawingPositionX = relativPosition.getX()
				+ canvas.getWidth() * verticalAlignmentFactor;
		double drawingPositionY = relativPosition.getY() - size.getY() * 0.5
				+ canvas.getHeight() * horizontalAlignmentFactor;

		position = new Point2D(drawingPositionX, drawingPositionY);

		if (!this.isEnabled) {
			graphics.drawImage(imgInActive, drawingPositionX, drawingPositionY, size.getX(), size.getY());
		} else if (isOver) {
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
	public void handle(InputEvent event) {
		if(!isEnabled) {
			return;
		}
		if (event instanceof MouseEvent) {
			MouseEvent mouseEvent = (MouseEvent) event;
			if (mouseEvent.getEventType() == MouseEvent.MOUSE_CLICKED) {
				if (mouseEvent.getButton() == MouseButton.PRIMARY && isOver) {
					function.run();
				}
			}
			isOver = mouseEvent.getSceneX() >= position.getX() && mouseEvent.getSceneX() <= position.getX() + size.getX()
			&& mouseEvent.getSceneY() >= position.getY() && mouseEvent.getSceneY() <= position.getY() + size.getY();
			
		}
		if(keycode != null) {
			if (event instanceof KeyEvent) {
				KeyEvent keyEvent = (KeyEvent) event;
	
				if (keyEvent.getEventType() == KeyEvent.KEY_PRESSED) {
					if (keyEvent.getCode() == keycode) {
						function.run();
					}
				}
			}
		}
	}
}
