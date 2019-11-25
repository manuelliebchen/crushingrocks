package de.acagamics.client.gui.elements;

import de.acagamics.client.gui.interfaces.IClickable;
import de.acagamics.client.rendering.assetmanagment.ImageManager;
import de.acagamics.constants.DesignConstants;
import de.acagamics.constants.DesignConstants.ALINGNMENT;
import de.acagamics.game.types.Vec2f;
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
public final class Button extends Alignable implements IClickable {
	
	public static enum BUTTON_TYPE {NORMAL, WIDE, SQUARE};
	
	// Button status
	private boolean isEnabled = true;

	// Drawing status
	private String buttonText;
	private Vec2f centeredPositioOffset;
	private Color textColor = DesignConstants.FOREGROUND_COLOR;
	private Vec2f relativPosition;
	private Vec2f position;
	private Vec2f size;
	
	private BUTTON_TYPE type;

	private Image imgUp = ImageManager.getInstance().loadImage("buttons/defaultButtonUp.png");
	private Image imgDown = ImageManager.getInstance().loadImage("buttons/defaultButtonDown.png");
	private Image imgInActive = ImageManager.getInstance().loadImage("buttons/defaultButtonInActive.png");
	
	private Font font = DesignConstants.STANDART_FONT;

	protected ALINGNMENT verticalAlignment = ALINGNMENT.LEFT;
	protected ALINGNMENT horizontalAlignment = ALINGNMENT.TOP;
	
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
	public Button(Vec2f relativPosition, BUTTON_TYPE type, String buttonText, Runnable function) {
		super(relativPosition);
		this.buttonText = buttonText;
		this.type = type;
		switch(type) {
		case WIDE:
			size = new Vec2f(200, 50);
			break;
		case SQUARE:
			size = new Vec2f(50, 50);
			break;
		default:
			size = new Vec2f(100, 50);
			break;
		} 
		this.relativPosition = relativPosition.sub(size.mult(0.5f));
		this.position = this.relativPosition;
		this.function = function;

		calcButtonTextProperties();
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

		Vec2f buttonTextSize = new Vec2f((float) text.getLayoutBounds().getWidth(), (float) text.getLayoutBounds().getHeight());
		centeredPositioOffset = new Vec2f((size.getX() - buttonTextSize.getX()) / 2,
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
		position = super.getAlignedPosition(graphics).add(size.mult(-0.5f));

		switch(type) {
		case SQUARE: 
			
			break;
		case WIDE:
			
			break;
		default:
			
		}
		if (!this.isEnabled) {
			graphics.drawImage(imgInActive, position.getX(), position.getY(), size.getX(), size.getY());
		} else if (isOver) {
			graphics.drawImage(imgDown, position.getX(), position.getY(), size.getX(), size.getY());
		} else {
			graphics.drawImage(imgUp, position.getX(), position.getY(), size.getX(), size.getY());
		}

		graphics.setFont(font);
		graphics.setFill(textColor);
		graphics.fillText(buttonText, position.getX() + centeredPositioOffset.getX(),
				position.getY() + centeredPositioOffset.getY());

	}

	/**
	 * Get the size of the button
	 * 
	 * @return size
	 */
	public Vec2f getSize() {
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
