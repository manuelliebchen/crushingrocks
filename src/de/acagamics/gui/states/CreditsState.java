package de.acagamics.gui.states;

import de.acagamics.constants.DesignConstants;
import de.acagamics.game.types.Vec2f;
import de.acagamics.gui.StateManager;
import de.acagamics.gui.elements.Button;
import de.acagamics.gui.elements.TextBox;
import de.acagamics.gui.elements.Button.BUTTON_TYPE;
import de.acagamics.gui.interfaces.ALIGNMENT;
import de.acagamics.gui.interfaces.MenuState;
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
	 */
	public CreditsState(StateManager manager, GraphicsContext context) {
		super(manager, context);
		drawables.add(new TextBox(new Vec2f(0, 100), "Credits:").setFont(DesignConstants.SECOND_TITLE_FONT).setVerticalAlignment(ALIGNMENT.CENTER));
		drawables.add(new TextBox(new Vec2f(0, 100), "\nManuel Liebchen\nAnja Kaminski\nMichl Steglich").setVerticalAlignment(ALIGNMENT.CENTER));
		clickable.add((Button) (new Button(new Vec2f(200, 125), BUTTON_TYPE.NORMAL, "Back", () -> manager.pop())
				.setKeyCode(KeyCode.ESCAPE).setVerticalAlignment(ALIGNMENT.RIGHT)
				.setHorizontalAlignment(ALIGNMENT.BOTTOM)));
	}
}
