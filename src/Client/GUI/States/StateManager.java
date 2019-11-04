package Client.GUI.States;

import java.util.Stack;

import Client.GUI.States.Interfaces.GameState;
import Client.GUI.States.Interfaces.IDraw;
import Client.GUI.States.Interfaces.IUpdate;
import javafx.animation.Timeline;
import javafx.scene.canvas.GraphicsContext;

/**
 * @author Gerd Schmidt (gerd.schmidt@acagamics.de)
 * @author Max Klockmann (max@acagamics.de)
 * 
 */
public final class StateManager implements IDraw, IUpdate {

	Timeline timeline;
	
	/**
	 * Stores all current game states from layer
	 */
	Stack<GameState> currentStates = new Stack<GameState>();

	/**
	 * The first and initial state. It's necessary if there is no state left.
	 * 
	 * @see pop()
	 */
	GameState initialState;

	/**
	 * StateManager controlls state handling e.g. Add/remove/reverts states to a
	 * layer based state handling
	 * 
	 * @param state sets the initial game state
	 */
	public StateManager(Timeline line) {
		initialState = null;
		this.timeline = line;
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
			timeline.stop();
		} else {
			currentStates.peek().revealed();
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
	 * Removes all active states and add a new one as base.
	 * 
	 * @param state -> new game state
	 */
	public void resetWith(GameState state) {
		while(!currentStates.empty()) {
			pop();
		}
		push(state);
	}

	/**
	 * Draws all drawables states. (Layer based)
	 * 
	 * @param elapsedTime elapsed time since last call
	 */
	@Override
	public void draw(GraphicsContext graphics, float elapsedTime) {
		for (int i = currentStates.size() -1; i >= 0; i--) {
			if (currentStates.get(i) instanceof IDraw) {
				((IDraw) currentStates.get(i)).draw(graphics, elapsedTime);
				break;
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
			if (currentStates.get(i) instanceof IUpdate) {
				((IUpdate) currentStates.get(i)).update(elapsedTime);
				break;
			}
		}
	}

}
