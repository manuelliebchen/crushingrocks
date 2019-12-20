package de.acagamics.gui.states;

import de.acagamics.client.web.Version;
import de.acagamics.constants.DesignConstants;
import de.acagamics.game.types.Vec2f;
import de.acagamics.gui.StateManager;
import de.acagamics.gui.elements.Background;
import de.acagamics.gui.elements.Button;
import de.acagamics.gui.elements.Button.BUTTON_TYPE;
import de.acagamics.gui.elements.TextBox;
import de.acagamics.gui.interfaces.ALIGNMENT;
import de.acagamics.gui.interfaces.MenuState;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

/**
 * @author Max Klockmann (max@acagamics.de)
 * @author Gerd Schmidt (gerd.schmidt@acagamics.de)
 *
 */
public class MainMenuState extends MenuState {

	/**
	 * Creating new MainMenu.
	 * 
	 * @param manager The StateManager of the current Window
	 * @param pane    The Gui Layout pane of the current Window. Used for Buttons
	 */
	public MainMenuState(StateManager manager, GraphicsContext context) {
		super(manager, context);
		
		background = new Background(150);
		
		drawables.add(new TextBox(new Vec2f(0, 60), "Crushing Rocks!")
				.setFont(DesignConstants.TITLE_FONT)
				.setVerticalAlignment(ALIGNMENT.CENTER));

		clickable.add((Button) (new Button(new Vec2f(0, 350), BUTTON_TYPE.WIDE, "Start Game",
				() -> manager.push(new SelectionState(manager, context))).setKeyCode(KeyCode.ENTER)
						.setVerticalAlignment(ALIGNMENT.CENTER).setHorizontalAlignment(ALIGNMENT.TOP)));
		clickable.add((Button) (new Button(new Vec2f(0, 425), BUTTON_TYPE.WIDE, "Show Credits",
				() -> manager.push(new CreditsState(manager, context))).setVerticalAlignment(ALIGNMENT.CENTER)
						.setHorizontalAlignment(ALIGNMENT.TOP)));
		clickable.add((Button) (new Button(new Vec2f(0, -100), BUTTON_TYPE.WIDE, "Exit Game", () -> manager.pop())
				.setKeyCode(KeyCode.ESCAPE).setVerticalAlignment(ALIGNMENT.CENTER)
				.setHorizontalAlignment(ALIGNMENT.BOTTOM)));
		drawables.addAll(clickable);

		if(Version.isChecked() && !Version.isUpToDate()) {
			drawables.add(new TextBox(new Vec2f(0,125), "You may update your game.")
					.setFont(DesignConstants.MEDIUM_SMALL_FONT)
					.setVerticalAlignment(ALIGNMENT.CENTER));
		}
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
