package de.acagamics.client.gui.states;

import de.acagamics.client.gui.StateManager;
import de.acagamics.client.gui.elements.Button;
import de.acagamics.client.gui.elements.Button.BUTTON_TYPE;
import de.acagamics.client.gui.elements.TextBox;
import de.acagamics.client.gui.interfaces.ALIGNMENT;
import de.acagamics.client.gui.interfaces.MenuState;
import de.acagamics.constants.DesignConstants;
import de.acagamics.game.types.Vec2f;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

/**
 * @author Max Klockmann (max@acagamics.de)
 * @author Gerd Schmidt (gerd.schmidt@acagamics.de)
 *
 */
public class MainMenuState extends MenuState {

	TextBox title;

	/**
	 * Creating new MainMenu.
	 * 
	 * @param manager The StateManager of the current Window
	 * @param pane    The Gui Layout pane of the current Window. Used for Buttons
	 */
	public MainMenuState(StateManager manager, GraphicsContext context) {
		super(manager, context);
		drawables.add(new TextBox(new Vec2f(0, 100), "Crushing Rocks!")
				.setFont(DesignConstants.TITLE_FONT)
				.setVerticalAlignment(ALIGNMENT.CENTER));

		clickable.add((Button) (new Button(new Vec2f(0, 225), BUTTON_TYPE.WIDE, "Start Game",
				() -> manager.push(new SelectionState(manager, context))).setKeyCode(KeyCode.ENTER)
						.setVerticalAlignment(ALIGNMENT.CENTER).setHorizontalAlignment(ALIGNMENT.TOP)));
		clickable.add((Button) (new Button(new Vec2f(0, 300), BUTTON_TYPE.WIDE, "Show Credits",
				() -> manager.push(new CreditsState(manager, context))).setVerticalAlignment(ALIGNMENT.CENTER)
						.setHorizontalAlignment(ALIGNMENT.TOP)));
		clickable.add((Button) (new Button(new Vec2f(0, -100), BUTTON_TYPE.WIDE, "Exit Game", () -> manager.pop())
				.setKeyCode(KeyCode.ESCAPE).setVerticalAlignment(ALIGNMENT.CENTER)
				.setHorizontalAlignment(ALIGNMENT.BOTTOM)));
		drawables.addAll(clickable);
	}

//	private String checkVersion() {
//		String versionText = "version: " + ClientConstants.VERSION;
//		if (Version.isUpToDate()) {
//			versionText += " - up to date!";
//			context.setFill(Color.WHITE);
//		} else {
//			versionText += " - out of date!";
//			context.setFill(Color.BLUE);
//		}
//		return versionText;
//	}
//
//	private String checkNews() {
//		return "news: " + News.getNews();
//	}
}
