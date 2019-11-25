package de.acagamics.client.gui.states;

import de.acagamics.client.gui.StateManager;
import de.acagamics.client.gui.elements.Button;
import de.acagamics.client.gui.elements.Button.BUTTON_TYPE;
import de.acagamics.client.gui.elements.TextBox;
import de.acagamics.client.gui.interfaces.MenuState;
import de.acagamics.constants.DesignConstants.ALINGNMENT;
import de.acagamics.game.types.Vec2f;
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
		drawables.add(new TextBox(new Vec2f(200, 200), "Credits:\nManuel Liebchen\nAnja Kaminski\nMichl Steglich"));
		clickable.add((Button) (new Button(new Vec2f(200, 125), BUTTON_TYPE.NORMAL, "Back", () -> manager.pop())
				.setKeyCode(KeyCode.ESCAPE).setVerticalAlignment(ALINGNMENT.RIGHT)
				.setHorizontalAlignment(ALINGNMENT.BOTTOM)));
	}
}
