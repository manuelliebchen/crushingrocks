package de.acagamics.client.gui.states;

import de.acagamics.client.gui.elements.Button;
import de.acagamics.client.gui.elements.TextBox;
import de.acagamics.client.gui.states.interfaces.IDrawable;
import de.acagamics.client.gui.states.interfaces.MenuState;
import de.acagamics.constants.DesignConstants;
import de.acagamics.constants.DesignConstants.Alignment;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

/**
 * @author Max Klockmann (max@acagamics.de)
 *
 */
public class Credits extends MenuState {

	/**
	 * Creating new Credits State.
	 * 
	 * @param manager The StateManager of the current Window
	 */
	public Credits(StateManager manager, GraphicsContext context) {
		super(manager, context);
		drawables.add(new TextBox(new Point2D(100, 100), "Credits:\nManuel Liebchen\nAnja Kaminski\nMichl Steglich"));
		Button backbutton = new Button(new Point2D(200, 125), new Point2D(150, 50), "Back", () -> manager.pop())
				.setVerticalAlignment(Alignment.RIGHT).setHorizontalAlignment(Alignment.BOTTOM).setKeyCode(KeyCode.ESCAPE);
		drawables.add(backbutton);
		buttons.add(backbutton);
	}

	@Override
	public void redraw() {

		Canvas canvas = context.getCanvas();

		context.setFill(DesignConstants.BACKGROUND_COLOR);
		context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

		for (IDrawable drawable : drawables) {
			drawable.draw(context);
		}
	}
}
