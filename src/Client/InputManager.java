package Client;

import java.awt.MouseInfo;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.HashSet;
import java.util.Set;

import com.sun.javafx.property.adapter.PropertyDescriptor.Listener;

/**
 * simple singleton input manager
 * @author Gerd Schmidt (gerd.schmidt@acagamics.de)
 */
public class InputManager implements KeyListener, MouseListener, MouseMotionListener {
	/**
	 * pressed-status of all keys
	 */
	private boolean[] keyDown = new boolean[KeyEvent.KEY_LAST];
	private boolean[] keyPressed = new boolean[KeyEvent.KEY_LAST];
	private boolean[] keyReleased = new boolean[KeyEvent.KEY_LAST];
	
	private boolean[] mouseButtonDown = new boolean[MouseInfo.getNumberOfButtons()];
	private boolean[] mouseButtonPressed = new boolean[MouseInfo.getNumberOfButtons()];
	private boolean[] mouseButtonReleased = new boolean[MouseInfo.getNumberOfButtons()];
	
	private boolean[] mouseButtonClicked = new boolean[MouseInfo.getNumberOfButtons()];
	private boolean[] mouseDraggedButton = new boolean[MouseInfo.getNumberOfButtons()];
	private boolean mouseEntered = false;
	private boolean mouseExited = false;
	
	private int mousePositionX = 0;
	private int mousePositionY = 0;
	
	
	/**
	 * different EventTypes for the InputKeyListener
	 * @author Gerd Schmidt (gerd.schmidt@acagamics.de)
	 *
	 */
	public enum KeyEventType {
		KEY_RELEASED,
		KEY_PRESSED,
		KEY_DOWN	/** key is currently down */
	};
	/**
	 * different EventTypes for the InputMouseKeyListener
	 * @author Gerd Schmidt (gerd.schmidt@acagamics.de)
	 *
	 */
	public enum MouseKeyEventType {
		MOUSE_RELEASED,
		MOUSE_PRESSED,
		MOUSE_DOWN,	/** key is currently down */
		MOUSE_CLICKED,
		MOUSE_DRAGGED
	};
	/**
	 * different EventTypes for the InputMouseKeyListener without keys
	 * @author Gerd Schmidt (gerd.schmidt@acagamics.de)
	 *
	 */
	public enum MouseEventType {
		MOUSE_ENTER,
		MOUSE_EXIT,
	}
	/**
	 * Interface for simple key listeners
	 * @author Gerd Schmidt (gerd.schmidt@acagamics.de)
	 *
	 */
	public interface InputKeyListener {
		void keyEvent(KeyEventType type, int KeyCode);
	}
	private Set<InputKeyListener> keyListeners = new HashSet<InputKeyListener>();
	/**
	 * Interface for simple mouse key listeners
	 * @author Gerd Schmidt (gerd.schmidt@acagamics.de)
	 *
	 */
	public interface InputMouseKeyListener {
		void mouseKeyEvent(MouseKeyEventType type, int KeyCode);
		void mouseEvent(MouseEventType type);
	}
	private Set<InputMouseKeyListener> mouseKeyListeners = new HashSet<InputMouseKeyListener>();
	
	/** 
	 * private one and only instance
	 */
	private static InputManager singleton = new InputManager();
	
	/**
	 * private constructor
	 */
	private InputManager() {
		for(int i=0; i<keyDown.length; ++i) {
			keyDown[i] = false;
		}
		updateTables();
	}
	
	/**
	 * getter to the one-and-only singleton-instance 
	 */
	public static InputManager get() {
		return singleton;
	}
	
	/**
	 * Adds a key listener. Its functions will be called every time updateTables is called. 
	 */
	public void addKeyListener(InputKeyListener listener) {
		keyListeners.add(listener);
	}
	/**
	 * Adds a key listener. Its functions will be called every time updateTables is called. 
	 */
	public void addMouseKeyListener(InputMouseKeyListener listener) {
		mouseKeyListeners.add(listener);
	}
	
	/**
	 * removes a key listener  
	 */
	public void removeKeyListener(InputKeyListener listener) {
		keyListeners.remove(listener);
	}
	/**
	 * removes a mouse key listener  
	 */
	public void removeMouseKeyListener(InputMouseKeyListener listener) {
		mouseKeyListeners.remove(listener);
	}
	
	public int getMouseX() {
		return mousePositionX;
	}
	public int getMouseY() {
		return mousePositionY;
	}
	
	public void updateTables() {
		for(int i=0; i<keyPressed.length; ++i) {
			if(keyPressed[i]) {
				for(InputKeyListener listener : keyListeners)
					listener.keyEvent(KeyEventType.KEY_PRESSED, i);
				keyPressed[i] = false;
			}
		}
		for(int i=0; i<keyReleased.length; ++i) {
			if(keyReleased[i]) {
				for(InputKeyListener listener : keyListeners)
					listener.keyEvent(KeyEventType.KEY_RELEASED, i);
				keyReleased[i] = false;
			}
		}
		for(int i=0; i<keyDown.length; ++i) {
			if(keyDown[i]) {
				for(InputKeyListener listener : keyListeners)
					listener.keyEvent(KeyEventType.KEY_DOWN, i);
			}
		}
		
		for(int i=0; i<mouseButtonPressed.length; ++i) {
			if(mouseButtonPressed[i]) {
				for(InputMouseKeyListener listener : mouseKeyListeners)
					listener.mouseKeyEvent(MouseKeyEventType.MOUSE_PRESSED, i);
				mouseButtonPressed[i] = false;
			}

		}
		for(int i=0; i<mouseButtonReleased.length; ++i) {
			if(mouseButtonReleased[i]) {
				for(InputMouseKeyListener listener : mouseKeyListeners)
					listener.mouseKeyEvent(MouseKeyEventType.MOUSE_RELEASED, i);
				mouseButtonReleased[i] = false;
			}
		}
		for(int i=0; i<mouseButtonDown.length; ++i) {
			if(mouseButtonDown[i]) {
				for(InputMouseKeyListener listener : mouseKeyListeners)
					listener.mouseKeyEvent(MouseKeyEventType.MOUSE_DOWN, i);
			}
		}
		
		for(int i=0; i<mouseButtonClicked.length; ++i) {
			if(mouseButtonClicked[i]) {
				for(InputMouseKeyListener listener : mouseKeyListeners)
					listener.mouseKeyEvent(MouseKeyEventType.MOUSE_CLICKED, i);
				mouseButtonClicked[i] = false;
			}
		}

		for(int i=0; i<mouseDraggedButton.length; ++i) {
			if(mouseDraggedButton[i]) {
				for(InputMouseKeyListener listener : mouseKeyListeners)
					listener.mouseKeyEvent(MouseKeyEventType.MOUSE_DRAGGED, i);
				mouseDraggedButton[i] = false;
			}
		}

		if(mouseEntered) {
			for(InputMouseKeyListener listener : mouseKeyListeners)
				listener.mouseEvent(MouseEventType.MOUSE_ENTER);
			mouseEntered = false;
		}
		
		if(mouseExited) {
			for(InputMouseKeyListener listener : mouseKeyListeners)
				listener.mouseEvent(MouseEventType.MOUSE_EXIT);
			mouseExited = false;
		}

	}
	
	@Override
	public void keyPressed(KeyEvent arg0) {
		if(arg0.getKeyCode() >= keyDown.length) {
			return;
		}
		keyDown[arg0.getKeyCode()] = true;
		keyPressed[arg0.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		if(arg0.getKeyCode() >= keyDown.length) {
			return;
		}
		keyDown[arg0.getKeyCode()] = false;
		keyReleased[arg0.getKeyCode()] = true;
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		mouseButtonClicked[arg0.getButton()] = true;
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		mouseEntered = true;
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		mouseExited = true;
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		mouseButtonPressed[arg0.getButton()] = true;
		mouseButtonDown[arg0.getButton()] = true;
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		mouseButtonReleased[arg0.getButton()] = true;
		mouseButtonDown[arg0.getButton()] = false;
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		mouseDraggedButton[arg0.getButton()] = true;
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		mousePositionX = arg0.getX();
		mousePositionY = arg0.getY();
	}	
}
