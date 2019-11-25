package de.acagamics.client.gui.states;

import de.acagamics.client.gui.StateManager;
import de.acagamics.client.gui.elements.Button;
import de.acagamics.client.gui.elements.Button.BUTTON_TYPE;
import de.acagamics.client.gui.elements.TextBox;
import de.acagamics.client.gui.interfaces.MenuState;
import de.acagamics.constants.DesignConstants.ALINGNMENT;
import de.acagamics.data.GameStatistic;
import de.acagamics.data.InGameSettings;
import de.acagamics.game.types.Vec2f;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

public class StatisticState extends MenuState {

	public StatisticState(StateManager manager, GraphicsContext context, GameStatistic statistic,
			InGameSettings settings) {
		super(manager, context);

		clickable.add((Button) (new Button(new Vec2f(-325, -125), BUTTON_TYPE.NORMAL, "Back", () -> manager.pop())
				.setKeyCode(KeyCode.ESCAPE).setVerticalAlignment(ALINGNMENT.RIGHT)
				.setHorizontalAlignment(ALINGNMENT.BOTTOM)));
		clickable.add((Button) (new Button(new Vec2f(-200, -125), BUTTON_TYPE.NORMAL, "Restart",
				() -> manager.switchCurrentState(new InGameState(manager, context, settings))).setKeyCode(KeyCode.ENTER)
						.setVerticalAlignment(ALINGNMENT.RIGHT).setHorizontalAlignment(ALINGNMENT.BOTTOM)));

		String title = "Statistics";
		if (statistic.isDraw()) {
			title += " - Tie-Break";
		}
		drawables.add(new TextBox(new Vec2f(300, 100), title).setVerticalAlignment(ALINGNMENT.LEFT)
				.setHorizontalAlignment(ALINGNMENT.TOP));

		drawables.add(new TextBox(new Vec2f(-450, 200), statistic.getSitesString())
				.setVerticalAlignment(ALINGNMENT.CENTER).setHorizontalAlignment(ALINGNMENT.TOP));
		drawables.add(new TextBox(new Vec2f(-250, 200), statistic.getNameString())
				.setVerticalAlignment(ALINGNMENT.CENTER).setHorizontalAlignment(ALINGNMENT.TOP));
		drawables.add(new TextBox(new Vec2f(200, 200), statistic.getBaseHPString())
				.setVerticalAlignment(ALINGNMENT.CENTER).setHorizontalAlignment(ALINGNMENT.TOP));
		drawables.add(new TextBox(new Vec2f(450, 200), statistic.getScoreString())
				.setVerticalAlignment(ALINGNMENT.CENTER).setHorizontalAlignment(ALINGNMENT.TOP));

		drawables.addAll(clickable);
	}
}
