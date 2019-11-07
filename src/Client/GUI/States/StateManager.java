package Client.GUI.States;

import java.util.Stack;

import Client.GUI.States.Interfaces.GameState;
import Client.GUI.States.Interfaces.IDrawable;
import Client.GUI.States.Interfaces.IOverlay;
import Client.GUI.States.Interfaces.IUpdateable;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.InputEvent;
import javafx.stage.Stage;

/**
 * @author Gerd Schmidt (gerd.schmidt@acagamics.de)
 * @author Max Klockmann (max@acagamics.de)
 * 
 */
public final class StateManager implements IDrawable, IUpdateable, EventHandler<InputEvent> {

	Stage stage;
	
	/**
	 * Stores all current game states from layer
	 */
	Stack<GameState> currentStates = new Stack<GameState>();

	/**
	 * StateManager controlls state handling e.g. Add/remove/reverts states to a
	 * layer based state handling
	 * @param state sets the initial game state
	 */
	public StateManager(Stage stage) {
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

		currentStates.pop();
		if (currentStates.empty()) {
			stage.close();
		}
	}

	/**
	 * Returns the current state on top.
	 */
	public GameState peek() {
		return currentStates.peek();
	}

	/**
	 * Adds a new game state to current state list.
	 * 
	 * @param state -> new game state
	 */
	public void push(GameState state) {
		state.entered();
		currentStates.push(state);
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

	/**
	 * Draws all drawables states. (Layer based)
	 * 
	 * @param elapsedTime elapsed time since last call
	 */
	@Override
	public void draw(GraphicsContext graphics) {
		for (int i = currentStates.size() -1; i >= 0; i--) {
			if (currentStates.get(i) instanceof IDrawable) {
				((IDrawable) currentStates.get(i)).draw(graphics);
				if(!(currentStates.get(i) instanceof IOverlay)) {
					break;
				}
			}
		}
	}

	/**
	 * Updates all updatable states.
	 * 
	 * @param elapsedTime elapsed time since last call
	 */
	@Override
	public void update(float elapsedTime) {
		for (int i = currentStates.size() -1; i >= 0; i--) {
			if (currentStates.get(i) instanceof IUpdateable) {
				((IUpdateable) currentStates.get(i)).update(elapsedTime);
				if(!(currentStates.get(i) instanceof IOverlay)) {
					break;
				}
			}
		}
	}

	@Override
	public void handle(InputEvent event) {
		currentStates.peek().handle(event);
	}

}
