package de.acagamics.crushingrocks.states;

import de.acagamics.framework.client.web.Version;
import de.acagamics.framework.gui.StateManager;
import de.acagamics.framework.gui.elements.Background;
import de.acagamics.framework.gui.elements.Button;
import de.acagamics.framework.gui.elements.Button.BUTTON_TYPE;
import de.acagamics.framework.gui.elements.TextBox;
import de.acagamics.framework.gui.interfaces.ALIGNMENT;
import de.acagamics.framework.gui.interfaces.MenuState;
import de.acagamics.framework.resourcemanagment.ClientProperties;
import de.acagamics.framework.resourcemanagment.DesignProperties;
import de.acagamics.framework.resourcemanagment.ResourceManager;
import de.acagamics.framework.types.Vec2f;
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
	 * @param context GraphicsContext for rendering the state when active.
	 */
	public MainMenuState(StateManager manager, GraphicsContext context) {
		super(manager, context);
		
		background = new Background(150);
		
		drawables.add(new TextBox(new Vec2f(0, 60), "Crushing Rocks!")
				.setFont(ResourceManager.getInstance().loadProperties(DesignProperties.class).getTitleFont())
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
					.setFont(ResourceManager.getInstance().loadProperties(DesignProperties.class).getMediumSmallFont())
					.setVerticalAlignment(ALIGNMENT.CENTER));
		}
		
		drawables.add(new TextBox(new Vec2f(-40,-20), "v" + ResourceManager.getInstance().loadProperties(ClientProperties.class).getVersion())
				.setFont(ResourceManager.getInstance().loadProperties(DesignProperties.class).getSmallFont())
				.setVerticalAlignment(ALIGNMENT.BOTTOM).setHorizontalAlignment(ALIGNMENT.RIGHT));
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
