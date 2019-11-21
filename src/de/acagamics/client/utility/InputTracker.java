package de.acagamics.client.utility;

import java.util.HashMap;
import java.util.Map;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/**
 * Simple singleton input manager. It ensures that input messages are only sent
 * at a specific point in the gameloop (updateTables).
 * 
 * @author Gerd Schmidt (gerd.schmidt@acagamics.de)
 */
public class InputTracker implements EventHandler<InputEvent> {
	// Pressed-status of all keys.
	private Map<KeyCode, Boolean> keyDown = new HashMap<KeyCode, Boolean>();
	private Map<KeyCode, Boolean> keyPressed = new HashMap<KeyCode, Boolean>();
	private Map<KeyCode, Boolean> keyReleased = new HashMap<KeyCode, Boolean>();

	private Map<MouseButton, Boolean> mouseButtonDown = new HashMap<MouseButton, Boolean>();
	private Map<MouseButton, Boolean> mouseButtonPressed = new HashMap<MouseButton, Boolean>();
	private Map<MouseButton, Boolean> mouseButtonReleased = new HashMap<MouseButton, Boolean>();

	private Map<MouseButton, Boolean> mouseButtonClicked = new HashMap<MouseButton, Boolean>();
	private Map<MouseButton, Boolean> mouseDraggedButton = new HashMap<MouseButton, Boolean>();
	private boolean mouseEntered = false;
	private boolean mouseExited = false;

	private double mousePositionX = 0;
	private double mousePositionY = 0;
	
	/**
	 * Private constructor (singleton!).
	 */
	public InputTracker() {
		for (KeyCode code : KeyCode.values()) {
			keyDown.put(code, false);
			keyPressed.put(code, false);
			keyReleased.put(code, false);
		}
		for (MouseButton button : MouseButton.values()) {
			mouseButtonDown.put(button, false);
			mouseButtonPressed.put(button, false);
			mouseButtonReleased.put(button, false);
			mouseButtonClicked.put(button, false);
			mouseDraggedButton.put(button, false);
		}
	}

	@Override
	public void handle(InputEvent event) {
		if (event instanceof KeyEvent) {
			KeyEvent keyEvent = (KeyEvent) event;

			if (keyEvent.getEventType() == KeyEvent.KEY_PRESSED) {
				if (keyDown.get(keyEvent.getCode())) {
					keyPressed.put(keyEvent.getCode(), true);
					keyDown.put(keyEvent.getCode(), false);
				}
				if (!keyPressed.get(keyEvent.getCode())) {
					keyDown.put(keyEvent.getCode(), true);
					keyPressed.put(keyEvent.getCode(), true);
				}
			} else if (keyEvent.getEventType() == KeyEvent.KEY_RELEASED) {
				keyDown.put(keyEvent.getCode(), false);
				keyReleased.put(keyEvent.getCode(), true);
			}
		} else if (event instanceof MouseEvent) {
			MouseEvent mouseEvent = (MouseEvent) event;

			if (mouseEvent.getEventType() == MouseEvent.MOUSE_MOVED) {
				mousePositionX = mouseEvent.getSceneX();
				mousePositionY = mouseEvent.getSceneY();
			} else if (mouseEvent.getEventType() == MouseEvent.MOUSE_CLICKED) {
				mouseButtonClicked.put(mouseEvent.getButton(), true);
			} else if (mouseEvent.getEventType() == MouseEvent.MOUSE_DRAGGED) {
				mouseDraggedButton.put(mouseEvent.getButton(), true);
			} else if (mouseEvent.getEventType() == MouseEvent.MOUSE_PRESSED) {
				mouseButtonPressed.put(mouseEvent.getButton(), true);
				mouseButtonDown.put(mouseEvent.getButton(), true);
			} else if (mouseEvent.getEventType() == MouseEvent.MOUSE_RELEASED) {
				mouseButtonReleased.put(mouseEvent.getButton(), true);
				mouseButtonDown.put(mouseEvent.getButton(), false);
			} else if (mouseEvent.getEventType() == MouseEvent.MOUSE_EXITED) {
				mouseExited = true;
			} else if (mouseEvent.getEventType() == MouseEvent.MOUSE_ENTERED) {
				mouseEntered = true;
			}
		} else {
			System.out.println("blub");
		}
	}

	/**
	 * Retrieves the current position of the mouse cursor.
	 * 
	 * @return A mouse cursor position.
	 * @see getMousePositionX, getMousePositionY
	 */
	public Point2D getMousePosition() {
		return new Point2D(mousePositionX, mousePositionY);
	}

	/**
	 * Updates all internal tables. Should be called once per frame. Will call all
	 * registered input handler.
	 */
	public void updateTables() {
		for (KeyCode code : keyPressed.keySet()) {
			if (keyPressed.get(code)) {
				keyPressed.put(code, false);
			}
		}

		for (KeyCode code : keyReleased.keySet()) {
			if (keyReleased.get(code)) {
				keyReleased.put(code, false);
			}
		}

		for (MouseButton button : mouseButtonPressed.keySet()) {
			if (mouseButtonPressed.get(button)) {
				mouseButtonPressed.put(button, false);
			}
		}

		for (MouseButton button : mouseButtonReleased.keySet()) {
			if (mouseButtonReleased.get(button)) {
				mouseButtonReleased.put(button, false);
			}
		}

		for (MouseButton button : mouseButtonClicked.keySet()) {
			mouseButtonClicked.put(button, false);
		}

		for (MouseButton button : mouseDraggedButton.keySet()) {
			mouseDraggedButton.put(button, false);
		}

		if (mouseEntered) {
			mouseEntered = false;
		}

		if (mouseExited) {
			mouseExited = false;
		}
	}
}
