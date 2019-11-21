package de.acagamics.client.gui.states;

import de.acagamics.client.gui.elements.Button;
import de.acagamics.client.gui.elements.TextBox;
import de.acagamics.client.gui.states.interfaces.IDrawable;
import de.acagamics.client.gui.states.interfaces.MenuState;
import de.acagamics.constants.DesignConstants;
import de.acagamics.constants.DesignConstants.Alignment;
import de.acagamics.game.logic.GameStatistic;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

public class GameStatisticState extends MenuState {

	public GameStatisticState(StateManager manager, GraphicsContext context, GameStatistic statistic, InGameSettings settings) {
		super(manager, context);

		buttons.add(new Button(new Point2D(400, 125), new Point2D(150, 50), "Back", () -> manager.pop())
				.setVerticalAlignment(Alignment.RIGHT).setHorizontalAlignment(Alignment.BOTTOM)
				.setKeyCode(KeyCode.ESCAPE));
		buttons.add(new Button(new Point2D(200, 125), new Point2D(150, 50), "Restart",
				() -> manager.switchCurrentState(new InGame(manager, context, settings))).setVerticalAlignment(Alignment.RIGHT)
						.setHorizontalAlignment(Alignment.BOTTOM).setKeyCode(KeyCode.ENTER));

		String title = "GameStatistics";
		if (statistic.isDraw()) {
			title += " - Tie-Break";
		}
		drawables.add(new TextBox(new Point2D(100, 100), title).setVerticalAlignment(Alignment.LEFT)
				.setHorizontalAlignment(Alignment.TOP));

		drawables.add(new TextBox(new Point2D(-450, 200), statistic.getSitesString())
				.setVerticalAlignment(Alignment.CENTER).setHorizontalAlignment(Alignment.TOP));
		drawables.add(new TextBox(new Point2D(-250, 200), statistic.getNameString())
				.setVerticalAlignment(Alignment.CENTER).setHorizontalAlignment(Alignment.TOP));
		drawables.add(
				new TextBox(new Point2D(200, 200), statistic.getBaseHPString()).setVerticalAlignment(Alignment.CENTER)
						.setHorizontalAlignment(Alignment.TOP).setTextAlignment(Alignment.RIGHT));
		drawables.add(
				new TextBox(new Point2D(450, 200), statistic.getScoreString()).setVerticalAlignment(Alignment.CENTER)
						.setHorizontalAlignment(Alignment.TOP).setTextAlignment(Alignment.RIGHT));

		drawables.addAll(buttons);
	}

	@Override
	public void redraw() {

		Canvas canvas = context.getCanvas();

		context.setFill(DesignConstants.BACKGROUND_COLOR);
		context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

		for (IDrawable drawable : drawables) {
			drawable.draw(context);
		}
	}
}
