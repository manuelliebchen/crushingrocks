package de.acagamics.crushingrocks.states;

import de.acagamics.crushingrocks.logic.Map;
import de.acagamics.crushingrocks.logic.Player;
import de.acagamics.crushingrocks.rendering.Background;
import de.acagamics.framework.geometry.Vec2f;
import de.acagamics.framework.resources.DesignProperties;
import de.acagamics.framework.resources.ResourceManager;
import de.acagamics.framework.ui.StateManager;
import de.acagamics.framework.ui.elements.Button;
import de.acagamics.framework.ui.elements.Button.BUTTON_TYPE;
import de.acagamics.framework.ui.elements.TextBox;
import de.acagamics.framework.ui.interfaces.ALIGNMENT;
import de.acagamics.framework.ui.interfaces.MenuState;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author Max Klockmann (max@acagamics.de)
 *
 */
public class CreditsState extends MenuState {

	/**
	 * Creating new Credits State.
	 * 
	 * @param manager The StateManager of the current Window
	 * @param context GraphicsContext for rendering the state when active.
	 */
	public CreditsState(StateManager manager, GraphicsContext context) {
		super(manager, context);
		drawables.add(new Background(100, 0.2f,new Map(new Random(), new ArrayList<Player>()) ));

		drawables.add(new TextBox(new Vec2f(0, 50), "Credits:").setFont(ResourceManager.getInstance().loadProperties(DesignProperties.class).getSubtitleFont()).setVerticalAlignment(ALIGNMENT.CENTER));
		drawables.add(new TextBox(new Vec2f(0, 200), "\nManuel Liebchen\nAnja Kaminski\nMichl Steglich").setVerticalAlignment(ALIGNMENT.CENTER));

		clickable.add(new Button(new Vec2f(-325, -120), BUTTON_TYPE.NORMAL, "Back", manager::pop)
				.setKeyCode(KeyCode.ESCAPE).setVerticalAlignment(ALIGNMENT.RIGHT)
				.setHorizontalAlignment(ALIGNMENT.LOWER));
	}
}
