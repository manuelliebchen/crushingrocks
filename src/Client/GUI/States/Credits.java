package Client.GUI.States;

import Client.GUI.Elements.Button;
import Client.GUI.Elements.TextBox;
import Client.GUI.States.Interfaces.IDrawable;
import Client.GUI.States.Interfaces.MenuState;
import Constants.DesignConstants;
import Constants.DesignConstants.Alignment;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

/**
 * @author Max Klockmann (max@acagamics.de)
 *
 */
public class Credits extends MenuState implements IDrawable {

	/**
	 * Creating new Credits State.
	 * 
	 * @param manager The StateManager of the current Window
	 */
	public Credits(StateManager manager) {
		super(manager);
		drawables.add(new TextBox(new Point2D(200, 125), "Credits:\nManuel Liebchen"));
		Button backbutton = new Button(new Point2D(200, 125), new Point2D(150, 50), "Back", () -> manager.pop())
				.setVerticalAlignment(Alignment.RIGHT).setHorizontalAlignment(Alignment.BOTTOM);
		drawables.add(backbutton);
		buttons.add(backbutton);
	}

	@Override
	public void draw(GraphicsContext graphics) {

		Canvas canvas = graphics.getCanvas();

		graphics.setFill(DesignConstants.BACKGROUND_COLOR);
		graphics.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

		for (IDrawable drawable : drawables) {
			drawable.draw(graphics);
		}
	}
}
