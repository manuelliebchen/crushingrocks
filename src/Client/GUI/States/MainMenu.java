package Client.GUI.States;

import java.util.ArrayList;
import java.util.List;

import Client.GUI.Elements.Button;
import Client.GUI.Elements.TextBox;
import Client.GUI.States.Interfaces.GameState;
import Client.GUI.States.Interfaces.IDrawable;
import Client.Web.News;
import Client.Web.Version;
import Constants.ClientConstants;
import Constants.DesignConstants;
import Constants.DesignConstants.Alignment;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * @author Max Klockmann (max@acagamics.de)
 * @author Gerd Schmidt (gerd.schmidt@acagamics.de)
 *
 */
public class MainMenu extends GameState implements IDrawable {

	TextBox title;
	
	List<Button> buttons;
	List<IDrawable> drawables;

	/**
	 * Creating new MainMenu.
	 * 
	 * @param manager The StateManager of the current Window
	 * @param pane    The Gui Layout pane of the current Window. Used for Buttons
	 */
	public MainMenu(StateManager manager) {
		super(manager);
		buttons = new ArrayList<>(3);
		buttons.add(new Button(new Point2D(0, 100), new Point2D(200, 50), "Start Game", () -> manager.push(new InGame(manager))).setVerticalAlignment(Alignment.CENTER).setHorizontalAlignment(Alignment.TOP));
		buttons.add(new Button(new Point2D(0, 200), new Point2D(200, 50), "Show Credits", () -> manager.push(new Credits(manager))).setVerticalAlignment(Alignment.CENTER).setHorizontalAlignment(Alignment.TOP));
		buttons.add(new Button(new Point2D(0, 100), new Point2D(200, 50), "Exit Game", () -> manager.pop()).setVerticalAlignment(Alignment.CENTER).setHorizontalAlignment(Alignment.BOTTOM));
		drawables = new ArrayList<>(3);
		drawables.addAll(buttons);
	}

	@Override
	public void draw(GraphicsContext graphics) {

		Canvas canvas = graphics.getCanvas();

		graphics.setFill(DesignConstants.BACKGROUND_COLOR);
		graphics.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

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

		for(IDrawable drawable : drawables) {
			drawable.draw(graphics);
		}
	}
	
    /**
     * Method witch is called when state is leaved.
     */
    public void leaving() {
    	
    }

//	@Override
//	public void update(float elapsedTime) {
//		// Tests if buttons are pressed
//		if (startGame.isPressed()) {
//			manager.push(new InGame(manager));
//		} else if (showCredits.isPressed()) {
//			manager.push(new Credits(manager));
//		} else if (endGame.isPressed()) {
//			manager.pop();
//		}
//	}

}
