package de.acagamics.client.gui.states;

import de.acagamics.client.gui.StateManager;
import de.acagamics.client.gui.elements.Button;
import de.acagamics.client.gui.elements.Button.BUTTON_TYPE;
import de.acagamics.client.gui.elements.TextBox;
import de.acagamics.client.gui.interfaces.MenuState;
import de.acagamics.constants.DesignConstants.ALINGMENT;
import de.acagamics.data.GameStatistic;
import de.acagamics.data.InGameSettings;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

public class StatisticState extends MenuState {

	public StatisticState(StateManager manager, GraphicsContext context, GameStatistic statistic, InGameSettings settings) {
		super(manager, context);

		clickable.add(new Button(new Point2D(-325, -125), BUTTON_TYPE.NORMAL, "Back", () -> manager.pop())
				.setVerticalAlignment(ALINGMENT.RIGHT).setHorizontalAlignment(ALINGMENT.BOTTOM)
				.setKeyCode(KeyCode.ESCAPE));
		clickable.add(new Button(new Point2D(-200, -125), BUTTON_TYPE.NORMAL, "Restart",
				() -> manager.switchCurrentState(new InGameState(manager, context, settings))).setVerticalAlignment(ALINGMENT.RIGHT)
						.setHorizontalAlignment(ALINGMENT.BOTTOM).setKeyCode(KeyCode.ENTER));

		String title = "Statistics";
		if (statistic.isDraw()) {
			title += " - Tie-Break";
		}
		drawables.add(new TextBox(new Point2D(300, 100), title).setVerticalAlignment(ALINGMENT.LEFT)
				.setHorizontalAlignment(ALINGMENT.TOP));

		drawables.add(new TextBox(new Point2D(-450, 200), statistic.getSitesString())
				.setVerticalAlignment(ALINGMENT.CENTER).setHorizontalAlignment(ALINGMENT.TOP));
		drawables.add(new TextBox(new Point2D(-250, 200), statistic.getNameString())
				.setVerticalAlignment(ALINGMENT.CENTER).setHorizontalAlignment(ALINGMENT.TOP));
		drawables.add(
				new TextBox(new Point2D(200, 200), statistic.getBaseHPString()).setVerticalAlignment(ALINGMENT.CENTER)
						.setHorizontalAlignment(ALINGMENT.TOP).setTextAlignment(ALINGMENT.RIGHT));
		drawables.add(
				new TextBox(new Point2D(450, 200), statistic.getScoreString()).setVerticalAlignment(ALINGMENT.CENTER)
						.setHorizontalAlignment(ALINGMENT.TOP).setTextAlignment(ALINGMENT.RIGHT));

		drawables.addAll(clickable);
	}
}
