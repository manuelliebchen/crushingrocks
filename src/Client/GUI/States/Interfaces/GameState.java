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
	protected boolean isTop;

	public void entered() {
		isTop = true;
	}
    public void leaving() {
    	isTop = false;
    }
    public void obscuring() {
    	isTop = false;
    }
    public void revealed() {
    	isTop = true;
    }
    
}
