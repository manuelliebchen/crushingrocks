package de.acagamics.client.gui.interfaces;

import de.acagamics.client.gui.StateManager;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.InputEvent;

/**
 * @author Gerd Schmidt (gerd.schmidt@acagamics.de)
 * @author Max Klockmann (max@acagamics.de)
 *
 */
public abstract class GameState implements EventHandler<InputEvent> {
	
	protected GraphicsContext context;
	protected StateManager manager;
	
	public GameState(StateManager manager, GraphicsContext context) {		
		this.manager = manager;
		this.context = context;
	}
	
    
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
    
    public void frame() {
    	update();
    	redraw();
    }
    
    public void update() {
    }
    
    public abstract void redraw();
}
