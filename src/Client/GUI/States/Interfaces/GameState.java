package Client.GUI.States.Interfaces;

import javafx.scene.Parent;
import javafx.scene.Scene;
import Client.GUI.States.StateManager;

/**
 * @author Gerd Schmidt (gerd.schmidt@acagamics.de)
 */
public abstract class GameState extends Scene {
	
	public GameState(Parent root, double width, double height, StateManager manager) {
		super(root, width, height);
		
		this.manager = manager;
	}
	
	protected StateManager manager;

	public abstract void entered();
    public abstract void leaving();
    public abstract void obscuring();
    public abstract void revealed();
    
}
