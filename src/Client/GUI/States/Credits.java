package Client.GUI.States;

import Client.ClientConstants;
import Client.GUI.States.Interfaces.GameState;
import Client.GUI.States.Interfaces.IDraw;
import Client.Rendering.Drawing.ImageManager;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * @author Max Klockmann (max@acagamics.de)
 *
 */
public class Credits extends GameState implements IDraw {
	/**
	 * Creating new Credits State.
	 * @param manager The StateManager of the current Window
	 */
	public Credits(StateManager manager) {
		super(manager);
	}

	@Override
	public void draw(GraphicsContext graphics, float elapsedTime) {
		if (!isTop)
			return;
		
		// draw background image
		graphics.drawImage(ImageManager.getInstance().loadImage("backgrounds/credits.png"), 0, 0, ClientConstants.SCREEN_WIDTH, ClientConstants.SCREEN_HEIGHT);
		
		// draw text
		graphics.setFill(Color.WHITE);
		graphics.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.BOLD, 36));
		graphics.fillText("Credits", 60, 30);
	}
}
