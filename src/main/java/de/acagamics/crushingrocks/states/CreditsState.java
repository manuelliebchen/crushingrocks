package de.acagamics.crushingrocks.states;

import de.acagamics.constants.DesignConstants;
import de.acagamics.framework.gui.StateManager;
import de.acagamics.framework.gui.elements.Button;
import de.acagamics.framework.gui.elements.TextBox;
import de.acagamics.framework.gui.elements.Button.BUTTON_TYPE;
import de.acagamics.framework.gui.interfaces.ALIGNMENT;
import de.acagamics.framework.gui.interfaces.MenuState;
import de.acagamics.framework.types.Vec2f;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

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
		drawables.add(new TextBox(new Vec2f(0, 50), "Credits:").setFont(DesignConstants.SECOND_TITLE_FONT).setVerticalAlignment(ALIGNMENT.CENTER));
		drawables.add(new TextBox(new Vec2f(0, 200), "\nManuel Liebchen\nAnja Kaminski\nMichl Steglich").setVerticalAlignment(ALIGNMENT.CENTER));
		clickable.add((Button) (new Button(new Vec2f(200, 125), BUTTON_TYPE.NORMAL, "Back", () -> manager.pop())
				.setKeyCode(KeyCode.ESCAPE).setVerticalAlignment(ALIGNMENT.RIGHT)
				.setHorizontalAlignment(ALIGNMENT.BOTTOM)));

		clickable.add((Button) (new Button(new Vec2f(-325, -120), BUTTON_TYPE.NORMAL, "Back", () -> manager.pop())
				.setKeyCode(KeyCode.ESCAPE).setVerticalAlignment(ALIGNMENT.RIGHT)
				.setHorizontalAlignment(ALIGNMENT.BOTTOM)));
	}
}
