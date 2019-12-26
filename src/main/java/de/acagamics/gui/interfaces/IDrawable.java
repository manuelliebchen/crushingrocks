package de.acagamics.gui.interfaces;

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
	 * @param context The GraphicsContext is used for drawing
	 */
	public void draw(GraphicsContext context);
}
