package Client.GUI.States;

import Client.ClientConstants;
import Client.GUI.States.Interfaces.GameState;
import Client.GUI.States.Interfaces.IDraw;
import Client.Web.News;
import Client.Web.Version;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * @author Max Klockmann (max@acagamics.de)
 *
 */
public class MainMenu extends GameState implements IDraw {	
	/**
	 * Creating new MainMenu.
	 * @param manager The StateManager of the current Window
	 * @param pane The Gui Layout pane of the current Window. Used for Buttons
	 */
	public MainMenu(StateManager manager) {
		super(manager);
	}

	@Override
	public void draw(GraphicsContext graphics, float elapsedTime) {
		if (!isTop)
			return;
		
		// draw background color
		graphics.setFill(Color.RED);
		graphics.fillRect(0, 0, ClientConstants.SCREEN_WIDTH, ClientConstants.SCREEN_HEIGHT);
		
		// draw oval in the middle
		graphics.setFill(Color.CORNFLOWERBLUE);
		graphics.fillOval((ClientConstants.SCREEN_WIDTH - 50) / 2, (ClientConstants.SCREEN_HEIGHT - 50) / 2, 50, 50);
		
		// write version
		if (Version.isChecked()) {
			String versionText = "version: " + ClientConstants.VERSION;
			if (Version.isUpToDate()) {
				versionText += " - up to date!";
				graphics.setFill(Color.WHITE);
			} else {
				versionText += " - out of date!";
				graphics.setFill(Color.BLUE);
			}
			graphics.fillText(versionText, 10, 10);
		}
		
		// write news
		if (News.hasNews()) {
			String versionText = "news: " + News.getNews();
			graphics.fillText(versionText, 10, 40);
		}
	}
}
