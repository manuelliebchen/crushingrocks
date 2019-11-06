package Client;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.KeyCode;

/**
 * Simple singleton input manager. It ensures that input messages are only sent
 * at a specific point in the gameloop (updateTables).
 * 
 * @author Gerd Schmidt (gerd.schmidt@acagamics.de)
 */
public class InputManager implements EventHandler<InputEvent> {
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
	 * Private one and only instance.
	 */
	private static InputManager instance;

	/**
	 * Private constructor (singleton!).
	 */
	private InputManager() {
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

	/**
	 * getter to the one-and-only singleton-instance
	 */
	public static InputManager get() {
		if (instance != null) {
			return instance;
		} else {
			throw new RuntimeException("InputManager must be initialized first.");
		}
	}

	/**
	 * Initializes the input-manager. You need to call this function before any
	 * other action!
	 * 
	 * @param scene The javafx-scene which is watched for input events.
	 */
	public static void init(Scene scene) {
		if (instance == null) {
			instance = new InputManager();

			scene.addEventHandler(MouseEvent.ANY, instance);
			scene.addEventHandler(KeyEvent.ANY, instance);
		} else {
			throw new RuntimeException("InputManager already initialized");
		}
	}

	@Override
	public void handle(InputEvent event) {
		if (event instanceof KeyEvent) {
			KeyEvent keyEvent = (KeyEvent) event;

			if (keyEvent.getEventType() == KeyEvent.KEY_PRESSED) {
				keyPressed(keyEvent);
			} else if (keyEvent.getEventType() == KeyEvent.KEY_RELEASED) {
				keyReleased(keyEvent);
			}
		} else if (event instanceof MouseEvent) {
			MouseEvent mouseEvent = (MouseEvent) event;

			if (mouseEvent.getEventType() == MouseEvent.MOUSE_MOVED) {
				mouseMoved(mouseEvent);
			} else if (mouseEvent.getEventType() == MouseEvent.MOUSE_CLICKED) {
				mouseClicked(mouseEvent);
			} else if (mouseEvent.getEventType() == MouseEvent.MOUSE_DRAGGED) {
				mouseDragged(mouseEvent);
			} else if (mouseEvent.getEventType() == MouseEvent.MOUSE_PRESSED) {
				mousePressed(mouseEvent);
			} else if (mouseEvent.getEventType() == MouseEvent.MOUSE_RELEASED) {
				mouseReleased(mouseEvent);
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
	 * Different EventTypes for the InputKeyListener.
	 * 
	 * @author Gerd Schmidt (gerd.schmidt@acagamics.de)
	 *
	 */
	public enum KeyEventType {
		KEY_RELEASED,
		/** key was released since the last updateTables call */
		KEY_PRESSED,
		/** key was pressed since the last updateTables call */
		KEY_DOWN /** key is currently down */
	};

	/**
	 * Different EventTypes for the InputMouseKeyListener.
	 * 
	 * @author Gerd Schmidt (gerd.schmidt@acagamics.de)
	 *
	 */
	public enum MouseKeyEventType {
		MOUSE_RELEASED,
		/** mouse button was released since the last updateTables call */
		MOUSE_PRESSED,
		/** mouse button was released since the last updateTables call */
		MOUSE_DOWN,
		/** mouse button is currently down */
		MOUSE_CLICKED, MOUSE_DRAGGED,
	};

	/**
	 * Different EventTypes for the InputMouseKeyListener without keys.
	 * 
	 * @author Gerd Schmidt (gerd.schmidt@acagamics.de)
	 *
	 */
	public enum MouseEventType {
		MOUSE_ENTER, MOUSE_EXIT,
	}

	/**
	 * Interface for simple key listeners.
	 * 
	 * @author Gerd Schmidt (gerd.schmidt@acagamics.de)
	 *
	 */
	public interface InputKeyListener {
		void keyEvent(KeyEventType type, KeyCode code);
	}

	private Set<InputKeyListener> keyListeners = new HashSet<InputKeyListener>();

	/**
	 * Interface for simple mouse key listeners
	 * 
	 * @author Gerd Schmidt (gerd.schmidt@acagamics.de)
	 *
	 */
	public interface InputMouseListener {
		void mouseKeyEvent(MouseKeyEventType type, MouseButton code);

		void mouseEvent(MouseEventType type);
	}

	private Set<InputMouseListener> mouseKeyListeners = new HashSet<InputMouseListener>();

	/**
	 * Adds a key listener. Its functions will be called every time updateTables is
	 * called.
	 */
	public void addKeyListener(InputKeyListener listener) {
		keyListeners.add(listener);
	}

	/**
	 * Adds a key listener. Its functions will be called every time updateTables is
	 * called.
	 */
	public void addMouseKeyListener(InputMouseListener listener) {
		mouseKeyListeners.add(listener);
	}

	/**
	 * Removes a key listener.
	 */
	public void removeKeyListener(InputKeyListener listener) {
		keyListeners.remove(listener);
	}

	/**
	 * Removes a mouse key listener.
	 */
	public void removeMouseKeyListener(InputMouseListener listener) {
		mouseKeyListeners.remove(listener);
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
	 * Retrieves the current x position of the mouse cursor.
	 * 
	 * @return A mouse cursor x position.
	 * @see getMousePositionY, getMousePosition
	 */
	public double getMousePositionX() {
		return mousePositionX;
	}

	/**
	 * Retrieves the current y position of the mouse cursor.
	 * 
	 * @return A mouse cursor y position.
	 * @see getMousePositionX, getMousePosition
	 */
	public double getMousePositionY() {
		return mousePositionY;
	}

	/**
	 * Updates all internal tables. Should be called once per frame. Will call all
	 * registered input handler.
	 */
	public void updateTables() {
		for (KeyCode code : keyPressed.keySet()) {
			if (keyPressed.get(code)) {
				for (InputKeyListener listener : keyListeners) {
					listener.keyEvent(KeyEventType.KEY_PRESSED, code);
				}
				keyPressed.put(code, false);
			}
		}

		for (KeyCode code : keyReleased.keySet()) {
			if (keyReleased.get(code)) {
				for (InputKeyListener listener : keyListeners) {
					listener.keyEvent(KeyEventType.KEY_RELEASED, code);
				}
				keyReleased.put(code, false);
			}
		}

		for (KeyCode code : keyDown.keySet()) {
			if (keyDown.get(code)) {
				for (InputKeyListener listener : keyListeners) {
					listener.keyEvent(KeyEventType.KEY_DOWN, code);
				}
			}
		}

		for (MouseButton button : mouseButtonPressed.keySet()) {
			if (mouseButtonPressed.get(button)) {
				for (InputMouseListener listener : mouseKeyListeners) {
					listener.mouseKeyEvent(MouseKeyEventType.MOUSE_PRESSED, button);
				}
				mouseButtonPressed.put(button, false);
			}
		}

		for (MouseButton button : mouseButtonReleased.keySet()) {
			if (mouseButtonReleased.get(button)) {
				for (InputMouseListener listener : mouseKeyListeners) {
					listener.mouseKeyEvent(MouseKeyEventType.MOUSE_RELEASED, button);
				}
				mouseButtonReleased.put(button, false);
			}
		}

		for (MouseButton button : mouseButtonDown.keySet()) {
			if (mouseButtonDown.get(button)) {
				for (InputMouseListener listener : mouseKeyListeners) {
					listener.mouseKeyEvent(MouseKeyEventType.MOUSE_DOWN, button);
				}
			}
		}

		for (MouseButton button : mouseButtonClicked.keySet()) {
			if (mouseButtonClicked.get(button)) {
				for (InputMouseListener listener : mouseKeyListeners) {
					listener.mouseKeyEvent(MouseKeyEventType.MOUSE_CLICKED, button);
				}
			}
			mouseButtonClicked.put(button, false);
		}

		for (MouseButton button : mouseDraggedButton.keySet()) {
			if (mouseDraggedButton.get(button)) {
				for (InputMouseListener listener : mouseKeyListeners) {
					listener.mouseKeyEvent(MouseKeyEventType.MOUSE_DRAGGED, button);
				}
			}
			mouseDraggedButton.put(button, false);
		}

		if (mouseEntered) {
			for (InputMouseListener listener : mouseKeyListeners) {
				listener.mouseEvent(MouseEventType.MOUSE_ENTER);
			}
			mouseEntered = false;
		}

		if (mouseExited) {
			for (InputMouseListener listener : mouseKeyListeners) {
				listener.mouseEvent(MouseEventType.MOUSE_EXIT);
			}
			mouseExited = false;
		}
	}

	private void keyPressed(KeyEvent arg0) {
		if (keyDown.get(arg0.getCode())) {
			keyPressed.put(arg0.getCode(), true);
			keyDown.put(arg0.getCode(), false);
		}
		if (!keyPressed.get(arg0.getCode())) {
			keyDown.put(arg0.getCode(), true);
			keyPressed.put(arg0.getCode(), true);
		}
	}

	private void keyReleased(KeyEvent arg0) {
		keyDown.put(arg0.getCode(), false);
		keyReleased.put(arg0.getCode(), true);
	}

	public void mouseClicked(MouseEvent event) {
		mouseButtonClicked.put(event.getButton(), true);
	}

	public void mousePressed(MouseEvent event) {
		mouseButtonPressed.put(event.getButton(), true);
		mouseButtonDown.put(event.getButton(), true);
	}

	public void mouseReleased(MouseEvent event) {
		mouseButtonReleased.put(event.getButton(), true);
		mouseButtonDown.put(event.getButton(), false);
	}

	public void mouseDragged(MouseEvent event) {
		mouseDraggedButton.put(event.getButton(), true);
	}

	public void mouseMoved(MouseEvent event) {
		mousePositionX = event.getSceneX();
		mousePositionY = event.getSceneY();
	}
}
