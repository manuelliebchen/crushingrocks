package Client.GUI.States.Interfaces;

import Client.GUI.States.StateManager;

/**
 * @author Gerd Schmidt (gerd.schmidt@acagamics.de)
 * @author Max Klockmann (max@acagamics.de)
 *
 */
public abstract class GameState {
	
	public GameState(StateManager manager) {		
		this.manager = manager;
	}
	
	protected StateManager manager;

    
    /**
     * Method witch is called when state is entered.
     */
	public void entered() {
	}
    
    /**
     * Method witch is called when state is leaved.
     */
    public void leaving() {
    }

    /**
     * Method witch is called when state is obscured.
     */
    public void obscuring() {
    }
    
    /**
     * Method witch is called when state is revealed.
     */
    public void revealed() {
    }
    
}
