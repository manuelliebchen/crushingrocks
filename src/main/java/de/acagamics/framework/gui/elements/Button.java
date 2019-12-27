package de.acagamics.framework.gui.elements;

import de.acagamics.framework.gui.interfaces.ALIGNMENT;
import de.acagamics.framework.gui.interfaces.Alignable;
import de.acagamics.framework.gui.interfaces.IClickable;
import de.acagamics.framework.resourcemanagment.ResourceManager;
import de.acagamics.framework.types.Vec2f;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * @author Gerd Schmidt (gerd.schmidt@acagamics.de) Image buttons with customs
 *         images and text. Registers if a user pressed on it.
 */
public final class Button extends Alignable implements IClickable {

	public static enum BUTTON_TYPE {
		NORMAL, WIDE, SQUARE
	};

	// Button status
	private boolean isEnabled = true;

	// Drawing status
	private String buttonText;
	private Vec2f centeredPositioOffset;
	private Color textColor = ResourceManager.getInstance().getDesignProperties().getButtonTextColor();
	private Vec2f relativPosition;
	private Vec2f position;
	private Vec2f size;

	private Image imgUp;
	private Image imgDown;
	private Image imgInActive;

	protected ALIGNMENT verticalAlignment = ALIGNMENT.LEFT;
	protected ALIGNMENT horizontalAlignment = ALIGNMENT.TOP;

	private KeyCode keycode;

	private Runnable function;

	private boolean isOver;

	// Mouse status
	private boolean mousePressed = false;

	/**
	 * Default button constructor with default buttons images and text color
	 * (black).
	 * 
	 * @param relativPosition   The relative position where the button will be drawn.
	 * @param type       The size of the button.
	 * @param buttonText The Text, which will be displayed on the button.
	 * @param function   The Function to be executed when pressed.
	 */
	public Button(Vec2f relativPosition, BUTTON_TYPE type, String buttonText, Runnable function) {
		super(relativPosition);
		this.buttonText = buttonText;
		String buttonTexture = "buttons/Button";
		int buttonHeight = ResourceManager.getInstance().getDesignProperties().getButtonHeight();
		switch (type) {
		case WIDE:
			size = new Vec2f(buttonHeight * 4, buttonHeight);
			buttonTexture += "4";
			break;
		case SQUARE:
			size = new Vec2f(buttonHeight, buttonHeight);
			buttonTexture += "1";
			break;
		default:
			size = new Vec2f(buttonHeight * 2, buttonHeight);
			buttonTexture += "2";
			break;
		}

		imgUp = ResourceManager.getInstance().loadImage(buttonTexture + ".png");
		imgDown = ResourceManager.getInstance().loadImage(buttonTexture + "dark.png");
		imgInActive = ResourceManager.getInstance().loadImage(buttonTexture + "Inactive.png");

		this.relativPosition = relativPosition.sub(size.mult(0.5f));
		this.position = this.relativPosition;
		this.function = function;

		calcButtonTextProperties();
	}

	public Button setTextColor(Color textColor) {
		this.textColor = textColor;
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
		text.setFont(ResourceManager.getInstance().getDesignProperties().getButtonFont());

		Vec2f buttonTextSize = new Vec2f((float) text.getLayoutBounds().getWidth(),
				(float) text.getLayoutBounds().getHeight());
		centeredPositioOffset = new Vec2f((size.getX() - buttonTextSize.getX()) / 2,
				size.getY() / 2 + buttonTextSize.getY() / 4);
	}

	/**
	 * Change text of button. Updates text properties
	 * 
	 * @see calcButtonTextProperties()
	 * @param buttonText the new Text to be displayed.
	 * @return this Button for further modifications.
	 */
	public Button setButtonText(String buttonText) {
		this.buttonText = buttonText;
		calcButtonTextProperties();
		return this;
	}

	/**
	 * Changes if the button is enabled or not. If not this button show button image:
	 * imageInActives.
	 * 
	 * @see isPressed()
	 * @param enabled wether this button is enabled
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
	 * @param context GraphicsContext for rendering the state when active.
	 */
	public void draw(GraphicsContext context) {
		position = super.getAlignedPosition(context).add(size.mult(-0.5f));

		
		if (!this.isEnabled) {
			context.drawImage(imgInActive, position.getX(), position.getY(), size.getX(), size.getY());
		} else if (isOver) {
			context.drawImage(imgDown, position.getX(), position.getY(), size.getX(), size.getY());
		} else {
			context.drawImage(imgUp, position.getX(), position.getY(), size.getX(), size.getY());
		}

		context.setFont(ResourceManager.getInstance().getDesignProperties().getButtonFont());
		context.setFill(textColor);
		context.fillText(buttonText, position.getX() + centeredPositioOffset.getX(),
				position.getY() + centeredPositioOffset.getY());

		context.drawImage(ResourceManager.getInstance().loadImage("Ressource.png"),
				position.getX() + ResourceManager.getInstance().getDesignProperties().getButtonHeight() * 1/2,
				position.getY() + size.getY() * 1/5, -ResourceManager.getInstance().getDesignProperties().getButtonHeight(),
				ResourceManager.getInstance().getDesignProperties().getButtonHeight());
		context.drawImage(ResourceManager.getInstance().loadImage("Ressource.png"),
				position.getX() + size.getX() - ResourceManager.getInstance().getDesignProperties().getButtonHeight() / 2,
				position.getY() + size.getY() * 1/5, ResourceManager.getInstance().getDesignProperties().getButtonHeight(),
				ResourceManager.getInstance().getDesignProperties().getButtonHeight());

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
		if (!isEnabled) {
			return;
		}
		if (event instanceof MouseEvent) {
			MouseEvent mouseEvent = (MouseEvent) event;
			if (mouseEvent.getEventType() == MouseEvent.MOUSE_CLICKED) {
				if (mouseEvent.getButton() == MouseButton.PRIMARY && isOver) {
					function.run();
				}
			}
			isOver = mouseEvent.getSceneX() >= position.getX()
					&& mouseEvent.getSceneX() <= position.getX() + size.getX()
					&& mouseEvent.getSceneY() >= position.getY()
					&& mouseEvent.getSceneY() <= position.getY() + size.getY();

		}
		if (keycode != null) {
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
