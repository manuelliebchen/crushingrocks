package Client.GUI.States.Interfaces;

import javafx.scene.canvas.GraphicsContext;

/**
 * @author Gerd Schmidt (gerd.schmidt@acagamics.de)
 * @author Max Klockmann (max@acagamics.de)
 * 
 * Simple drawing interface.
 */
public interface IDrawable {

	/**
	 * This method is invoked in each step and draws the content on the window
	 * @param graphics The GraphicsContext is used for drawing
	 * @param elapsedTime The Elapsed Time since last draw call
	 */
	public void draw(GraphicsContext context);
}
