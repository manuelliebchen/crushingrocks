package Client.GUI.States;

import java.util.Stack;

import Client.GUI.States.Interfaces.GameState;
import Client.GUI.States.Interfaces.IOverlay;
import Client.GUI.States.Interfaces.ISelfUpdating;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.InputEvent;
import javafx.stage.Stage;

/**
 * @author Gerd Schmidt (gerd.schmidt@acagamics.de)
 * @author Max Klockmann (max@acagamics.de)
 * 
 */
public final class StateManager implements EventHandler<InputEvent> {

	/**
	 * The Stage for everything.
	 */
	private Stage stage;

	/**
	 * Stores all current game states from layer
	 */
	private Stack<GameState> stateStack = new Stack<GameState>();

	/**
	 * StateManager controlls state handling e.g. Add/remove/reverts states to a
	 * layer based state handling
	 * 
	 * @param state sets the initial game state
	 */
	public StateManager(Stage stage, GraphicsContext context) {
		this.stage = stage;
	}

	/**
	 * Returns the current state on top and remove it.
	 * 
	 * If there is only one state left, the initial state will add to currentStates.
	 */
	public void pop() {
		GameState top = peek();
		top.leaving();

		stateStack.pop();
		if (stateStack.empty()) {
			stage.close();
		}
	}

	/**
	 * Returns the current state on top.
	 */
	public GameState peek() {
		return stateStack.peek();
	}

	/**
	 * Adds a new game state to current state list.
	 * 
	 * @param state -> new game state
	 */
	public void push(GameState state) {
		state.entered();
		stateStack.push(state);
	}

	/**
	 * Removes the last state and adds a new state
	 * 
	 * @param state -> new game state
	 */
	public void switchCurrentState(GameState state) {
		pop();
		push(state);
	}
	
	public void frame() {
		for (int i = stateStack.size() - 1; i >= 0; i--) {
			if (stateStack.get(i) instanceof ISelfUpdating) {
				break;
			}
			stateStack.get(i).frame();
			if (!(stateStack.get(i) instanceof IOverlay)) {
				break;
			}
		}
	}

	/**
	 * Draws top IDrawable state.
	 */
	public void redraw() {
		for (int i = stateStack.size() - 1; i >= 0; i--) {
			if (stateStack.get(i) instanceof ISelfUpdating) {
				break;
			}
			stateStack.get(i).redraw();
			if (!(stateStack.get(i) instanceof IOverlay)) {
				break;
			}

		}
	}

	/**
	 * Updates all updatable states.
	 * 
	 * @param elapsedTime elapsed time since last call
	 */
	public void update() {
		for (int i = stateStack.size() - 1; i >= 0; i--) {
			if (stateStack.get(i) instanceof ISelfUpdating) {
				break;
			}
			stateStack.get(i).update();
			if (!(stateStack.get(i) instanceof IOverlay)) {
				break;
			}
		}
	}

	@Override
	public void handle(InputEvent event) {
		if (!stateStack.empty()) {
			stateStack.peek().handle(event);
		}
	}

}
