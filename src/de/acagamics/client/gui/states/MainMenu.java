package de.acagamics.client.gui.states;

import de.acagamics.client.gui.elements.Button;
import de.acagamics.client.gui.elements.TextBox;
import de.acagamics.client.gui.states.interfaces.IDrawable;
import de.acagamics.client.gui.states.interfaces.MenuState;
import de.acagamics.client.web.News;
import de.acagamics.client.web.Version;
import de.acagamics.constants.ClientConstants;
import de.acagamics.constants.DesignConstants;
import de.acagamics.constants.DesignConstants.Alignment;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

/**
 * @author Max Klockmann (max@acagamics.de)
 * @author Gerd Schmidt (gerd.schmidt@acagamics.de)
 *
 */
public class MainMenu extends MenuState {

	TextBox title;

	/**
	 * Creating new MainMenu.
	 * 
	 * @param manager The StateManager of the current Window
	 * @param pane    The Gui Layout pane of the current Window. Used for Buttons
	 */
	public MainMenu(StateManager manager, GraphicsContext context) {
		super(manager, context);
		buttons.add(new Button(new Point2D(0, 100), new Point2D(200, 50), "Start Game",
				() -> manager.push(new BotSelectionState(manager, context))).setVerticalAlignment(Alignment.CENTER)
						.setHorizontalAlignment(Alignment.TOP).setKeyCode(KeyCode.ENTER));
		buttons.add(new Button(new Point2D(0, 200), new Point2D(200, 50), "Show Credits",
				() -> manager.push(new Credits(manager, context))).setVerticalAlignment(Alignment.CENTER)
						.setHorizontalAlignment(Alignment.TOP));
		buttons.add(new Button(new Point2D(0, 100), new Point2D(200, 50), "Exit Game", () -> manager.pop())
				.setVerticalAlignment(Alignment.CENTER).setHorizontalAlignment(Alignment.BOTTOM)
				.setKeyCode(KeyCode.ESCAPE));
		drawables.addAll(buttons);
	}

	@Override
	public void redraw() {

		Canvas canvas = context.getCanvas();

		context.setFill(DesignConstants.BACKGROUND_COLOR);
		context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

		// write version
		if (Version.isChecked()) {
			String versionText = "version: " + ClientConstants.VERSION;
			if (Version.isUpToDate()) {
				versionText += " - up to date!";
				context.setFill(Color.WHITE);
			} else {
				versionText += " - out of date!";
				context.setFill(Color.BLUE);
			}
			context.fillText(versionText, 10, 10);
		}

		// write news
		if (News.hasNews()) {
			String versionText = "news: " + News.getNews();
			context.fillText(versionText, 10, 40);
		}

		for (IDrawable drawable : drawables) {
			drawable.draw(context);
		}
	}
}
