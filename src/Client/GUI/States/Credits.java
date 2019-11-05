package Client.GUI.States;

import Client.GUI.Elements.Button;
import Client.GUI.Elements.TextBox;
import Client.GUI.States.Interfaces.GameState;
import Client.GUI.States.Interfaces.IDrawable;
import Client.GUI.States.Interfaces.IUpdateable;
import Constants.DesignConstants;
import Constants.DesignConstants.Alignment;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

/**
 * @author Max Klockmann (max@acagamics.de)
 *
 */
public class Credits extends GameState implements IDrawable, IUpdateable {

	Button backbutton;
	TextBox text;

	/**
	 * Creating new Credits State.
	 * 
	 * @param manager The StateManager of the current Window
	 */
	public Credits(StateManager manager) {
		super(manager);
		text = new TextBox(new Point2D(200, 125), "Credits:\nManuel Liebchen");
		backbutton = new Button(new Point2D(200, 125), new Point2D(150, 50), "Back").setVerticalAlignment(Alignment.RIGHT).setHorizontalAlignment(Alignment.BOTTOM);
	}

	@Override
	public void draw(GraphicsContext graphics) {

		Canvas canvas = graphics.getCanvas();
		
		graphics.setFill(DesignConstants.BACKGROUND_COLOR);
		graphics.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

		backbutton.draw(graphics);
		text.draw(graphics);
	}

	@Override
	public void update(float elapsedTime) {
		if (backbutton.isPressed()) {
			manager.pop();
		}
	}
}
