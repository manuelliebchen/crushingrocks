package Client.GUI.States;

import Client.GUI.Elements.Button;
import Client.GUI.Elements.TextBox;
import Client.GUI.States.Interfaces.GameState;
import Client.GUI.States.Interfaces.IDraw;
import Client.GUI.States.Interfaces.IUpdate;
import Constants.ClientConstants.Alignment;
import Constants.ColorConstants;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * @author Max Klockmann (max@acagamics.de)
 *
 */
public class Credits extends GameState implements IDraw, IUpdate {

	Button backbutton;
	TextBox text;

	/**
	 * Creating new Credits State.
	 * 
	 * @param manager The StateManager of the current Window
	 */
	public Credits(StateManager manager) {
		super(manager);
		backbutton = new Button(new Point2D(200, 125), new Point2D(150, 50), "Back", Alignment.RIGHT, Alignment.DOWN);
		text = new TextBox(new Point2D(200, 125), "Manuel Liebchen");
	}

	@Override
	public void draw(GraphicsContext graphics, float elapsedTime) {

		Canvas canvas = graphics.getCanvas();
		
		graphics.setFill(ColorConstants.BACKGROUND_COLOR);
		graphics.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
		
		// draw background image
//		graphics.drawImage(ImageManager.getInstance().loadImage("backgrounds/credits.png"), 0, 0,
//				ClientConstants.INITIAL_SCREEN_WIDTH, ClientConstants.INITIAL_SCREEN_HEIGHT);

		// draw text
		graphics.setFill(ColorConstants.FOREGROUND_COLOR);
		graphics.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.BOLD, 36));
		graphics.fillText("Credits", 60, 30);

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
