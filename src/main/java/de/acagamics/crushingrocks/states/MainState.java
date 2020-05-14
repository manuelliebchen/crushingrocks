package de.acagamics.crushingrocks.states;

import de.acagamics.crushingrocks.logic.Map;
import de.acagamics.crushingrocks.logic.Player;
import de.acagamics.crushingrocks.rendering.Background;
import de.acagamics.framework.geometry.Vec2f;
import de.acagamics.framework.resources.ClientProperties;
import de.acagamics.framework.resources.DesignProperties;
import de.acagamics.framework.resources.ResourceManager;
import de.acagamics.framework.ui.StateManager;
import de.acagamics.framework.ui.elements.Button;
import de.acagamics.framework.ui.elements.Button.BUTTON_TYPE;
import de.acagamics.framework.ui.elements.TextBox;
import de.acagamics.framework.ui.interfaces.ALIGNMENT;
import de.acagamics.framework.ui.interfaces.MenuState;
import de.acagamics.framework.web.Version;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author Max Klockmann (max@acagamics.de)
 * @author Gerd Schmidt (gerd.schmidt@acagamics.de)
 *
 */
public class MainState extends MenuState {

	/**
	 * Creating new MainMenu.
	 * 
	 * @param manager The StateManager of the current Window
	 * @param context GraphicsContext for rendering the state when active.
	 */
	public MainState(StateManager manager, GraphicsContext context) {
		super(manager, context);

		drawables.add(new Background(150, 0.2f, new Map(new Random(), new ArrayList<Player>())));
		
		drawables.add(new TextBox(new Vec2f(0, 60), "Crushing Rocks!")
				.setFont(ResourceManager.getInstance().loadProperties(DesignProperties.class).getTitleFont())
				.setVerticalAlignment(ALIGNMENT.CENTER));

		clickable.add(new Button(new Vec2f(0, 325), BUTTON_TYPE.WIDE, "Start Game",
				() -> manager.push(new GameSelectionState(manager, context))).setKeyCode(KeyCode.ENTER)
						.setVerticalAlignment(ALIGNMENT.CENTER).setHorizontalAlignment(ALIGNMENT.UPPER));
		clickable.add(new Button(new Vec2f(0, 400), BUTTON_TYPE.WIDE, "Simulation",
				() -> manager.push(new SimulationSelectionState(manager, context))).setVerticalAlignment(ALIGNMENT.CENTER)
						.setHorizontalAlignment(ALIGNMENT.UPPER));
		clickable.add(new Button(new Vec2f(0, 475), BUTTON_TYPE.WIDE, "Tournament",
				() -> manager.push(new TournamentState(manager, context))).setVerticalAlignment(ALIGNMENT.CENTER)
				.setHorizontalAlignment(ALIGNMENT.UPPER));
		clickable.add(new Button(new Vec2f(0, 550), BUTTON_TYPE.WIDE, "Show Credits",
				() -> manager.push(new CreditsState(manager, context))).setVerticalAlignment(ALIGNMENT.CENTER)
				.setHorizontalAlignment(ALIGNMENT.UPPER));
		clickable.add(new Button(new Vec2f(0, -75), BUTTON_TYPE.WIDE, "Exit Game", manager::pop)
				.setKeyCode(KeyCode.ESCAPE).setVerticalAlignment(ALIGNMENT.CENTER)
				.setHorizontalAlignment(ALIGNMENT.LOWER));
		drawables.addAll(clickable);

		if(Version.isChecked() && !Version.isUpToDate()) {
			drawables.add(new TextBox(new Vec2f(0,125), "You may update your game.")
					.setFont(ResourceManager.getInstance().loadProperties(DesignProperties.class).getMediumSmallFont())
					.setVerticalAlignment(ALIGNMENT.CENTER));
		}
		
		drawables.add(new TextBox(new Vec2f(-40,-20), "v" + ResourceManager.getInstance().loadProperties(ClientProperties.class).getVersion())
				.setFont(ResourceManager.getInstance().loadProperties(DesignProperties.class).getSmallFont())
				.setVerticalAlignment(ALIGNMENT.LOWER).setHorizontalAlignment(ALIGNMENT.RIGHT));
	}
}
