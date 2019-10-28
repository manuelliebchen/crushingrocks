package Client.GUI.States;

import java.util.ArrayList;

import Client.GUI.States.Interfaces.GameState;
import Client.GUI.States.Interfaces.IDraw;
import Client.GUI.States.Interfaces.IGameStateManager;
import Client.GUI.States.Interfaces.IUpdate;
import javafx.scene.canvas.GraphicsContext;

/**
 * @author Gerd Schmidt (gerd.schmidt@acagamics.de)
 * @author Max Klockmann (max@acagamics.de)
 * 
 */
public final class StateManager implements IGameStateManager {

	/**
	 * Stores all current game states from layer
	 */
	ArrayList<GameState> currentStates = new ArrayList<GameState>();
	/**
	 * Stores all drawables from current game states
	 */
	ArrayList<IDraw> drawables = new ArrayList<IDraw>();
	/**
	 * Stores all updatebles from current game states
	 */
	ArrayList<IUpdate> updateables = new ArrayList<IUpdate>();
	
	/**
	 * The first and initial state. It's necessary if there is no state left.
	 * @see pop() 
	 */
	GameState initialState;
	
	/**
	 * StateManager controlls state handling
	 * e.g. Add/remove/reverts states to a layer based state handling
	 * 
	 * @param state sets the initial game state
	 */
	public StateManager()
	{
		initialState = null;
	}
	
	
	/**
	 * Returns the current state on top and remove it.
	 * 
	 * If there is only one state left, the initial state will add to currentStates.
	 */
	@Override
	public GameState pop() {
		
		GameState top = peek();
		if (top instanceof IUpdate)
			updateables.remove(currentStates.get(currentStates.size()-1));
		if (top instanceof IDraw)
			drawables.remove(currentStates.get(currentStates.size()-1));
		
		currentStates.get(currentStates.size()-1).leaving();
		
		if(currentStates.size()==1)
		{
			GameState lastState = currentStates.get(0);
			currentStates.remove(currentStates.size()-1);
			
			push(initialState);
			
			return lastState;
		}	
		else{
			currentStates.get(currentStates.size()-2).revealed();
			return currentStates.remove(currentStates.size()-1);
		}
		

	}

	/**
	 * Returns the current state on top.
	 */
	@Override
	public GameState peek() {
		return currentStates.get(currentStates.size()-1);
	}

	
	/**
	 * Adds a new game state to current state list.
	 * @param state -> new game state
	 */
	@Override
	public void push(GameState state) {
		

		
		if(currentStates.size()>0)
			currentStates.get(currentStates.size()-1).obscuring();
		
		state.entered();
		
		currentStates.add(state);
		
		if(state instanceof IUpdate)
		{
			updateables.add((IUpdate)state);
		}
		if(state instanceof IDraw)
		{
			drawables.add((IDraw)state);
		}
		
	}

	/**
	 * Removes the last state and adds a new state
	 * @param state -> new game state
	 */
	@Override
	public void switchCurrentState(GameState state) {
		currentStates.get(currentStates.size()-1).leaving();
		pop();
		push(state);
		currentStates.get(currentStates.size()-1).entered();
		
	}
	/**
	 * Removes all active states and add a new one as base.
	 * @param state -> new game state
	 */
	@Override
	public void resetWith(GameState state) {
		
		for(int i = currentStates.size()-1; i>=0; i--)
		{
			currentStates.get(i).leaving();		
		}
		
		currentStates.clear();
		updateables.clear();
		drawables.clear();
		push(state);
		
	}

	/**
	 * Draws all drawables states. (Layer based)
	 * @param elapsedTime elapsed time since last call
	 */
	@Override
	public void draw(GraphicsContext graphics, float elapsedTime) {
		
		for(int i =0; i<drawables.size(); i++)
		{
			drawables.get(i).draw(graphics, elapsedTime);
		}
	}

	
	/**
	 * Updates all updatable states.
	 * @param elapsedTime elapsed time since last call
	 */
	@Override
	public void update(float elapsedTime) {

		for(int i =0; i<updateables.size(); i++)
		{
			updateables.get(i).update(elapsedTime);
		}
	}

}
