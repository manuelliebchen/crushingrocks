package Client.GUI.States;

import java.util.ArrayList;

import Client.GUI.States.Interfaces.IDraw;
import Client.GUI.States.Interfaces.IGameState;
import Client.GUI.States.Interfaces.IGameStateManager;
import Client.GUI.States.Interfaces.IUpdate;

/**
 * @author Gerd Schmidt (gerd.schmidt@acagamics.de)
 */
public final class StateManager implements IGameStateManager {

	/**
	 * Stores all current game states from layer
	 */
	ArrayList<IGameState> currentStates = new ArrayList<IGameState>();
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
	IGameState initialState;
	
	/**
	 * StateManager controlls state handling
	 * e.g. Add/remove/reverts states to a layer based state handling
	 * 
	 * @param state sets the initial game state
	 */
	public StateManager(IGameState state)
	{
		initialState = state;
		push(state);
	}
	
	
	/**
	 * Returns the current state on top and remove it.
	 * 
	 * If there is only one state left, the initial state will add to currentStates.
	 */
	@Override
	public IGameState pop() {
		
		updateables.remove(currentStates.get(currentStates.size()-1));
		drawables.remove(currentStates.get(currentStates.size()-1));
		
		currentStates.get(currentStates.size()-1).leaving();
		
		if(currentStates.size()==1)
		{
			IGameState lastState = currentStates.get(0);
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
	public IGameState peek() {
		return currentStates.get(currentStates.size()-1);
	}

	
	/**
	 * Adds a new game state to current state list.
	 * @param state -> new game state
	 */
	@Override
	public void push(IGameState state) {
		

		
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
	public void switchCurrentState(IGameState state) {
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
	public void resetWith(IGameState state) {
		
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
	public void draw(float elapsedTime) {
		
		for(int i =0; i<drawables.size(); i++)
		{
			drawables.get(i).draw(elapsedTime);
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
