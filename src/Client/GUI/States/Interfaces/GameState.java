package Client.GUI.States.Interfaces;

import Client.GUI.States.StateManager;
import javafx.event.EventHandler;
import javafx.scene.input.InputEvent;

/**
 * @author Gerd Schmidt (gerd.schmidt@acagamics.de)
 * @author Max Klockmann (max@acagamics.de)
 *
 */
public abstract class GameState implements EventHandler<InputEvent> {
	
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
}
