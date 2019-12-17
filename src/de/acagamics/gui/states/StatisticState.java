package de.acagamics.gui.states;

import de.acagamics.constants.DesignConstants;
import de.acagamics.data.GameStatistic;
import de.acagamics.data.InGameSettings;
import de.acagamics.game.types.Vec2f;
import de.acagamics.gui.StateManager;
import de.acagamics.gui.elements.Button;
import de.acagamics.gui.elements.TextBox;
import de.acagamics.gui.elements.Button.BUTTON_TYPE;
import de.acagamics.gui.interfaces.ALIGNMENT;
import de.acagamics.gui.interfaces.MenuState;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

public class StatisticState extends MenuState {

	public StatisticState(StateManager manager, GraphicsContext context, GameStatistic statistic,
			InGameSettings settings) {
		super(manager, context);

		clickable.add((Button) (new Button(new Vec2f(-350, -125), BUTTON_TYPE.NORMAL, "Back", () -> manager.pop())
				.setKeyCode(KeyCode.ESCAPE).setVerticalAlignment(ALIGNMENT.RIGHT)
				.setHorizontalAlignment(ALIGNMENT.BOTTOM)));
		clickable.add((Button) (new Button(new Vec2f(-200, -125), BUTTON_TYPE.NORMAL, "Restart",
				() -> manager.switchCurrentState(new InGameState(manager, context, settings))).setKeyCode(KeyCode.ENTER)
						.setVerticalAlignment(ALIGNMENT.RIGHT).setHorizontalAlignment(ALIGNMENT.BOTTOM)));

		String title = "Statistics";
		if (statistic.isDraw()) {
			title += " - Tie-Break";
		}
		drawables.add(new TextBox(new Vec2f(300, 100), title).setFont(DesignConstants.SECOND_TITLE_FONT).setVerticalAlignment(ALIGNMENT.LEFT)
				.setHorizontalAlignment(ALIGNMENT.TOP));
		
		drawables.add(new TextBox(new Vec2f(-500, 200), "Color").setTextAlignment(ALIGNMENT.LEFT)
				.setVerticalAlignment(ALIGNMENT.CENTER).setHorizontalAlignment(ALIGNMENT.TOP));
		drawables.add(new TextBox(new Vec2f(-250, 200), "Name").setTextAlignment(ALIGNMENT.LEFT)
				.setVerticalAlignment(ALIGNMENT.CENTER).setHorizontalAlignment(ALIGNMENT.TOP));
		drawables.add(new TextBox(new Vec2f(200, 200), "Base HP").setTextAlignment(ALIGNMENT.RIGHT)
				.setVerticalAlignment(ALIGNMENT.CENTER).setHorizontalAlignment(ALIGNMENT.TOP));
		drawables.add(new TextBox(new Vec2f(450, 200), "Score").setTextAlignment(ALIGNMENT.RIGHT)
				.setVerticalAlignment(ALIGNMENT.CENTER).setHorizontalAlignment(ALIGNMENT.TOP));
		
		for(int i = 0; i < statistic.getPlayerAmount(); ++i) {			
			drawables.add(new TextBox(new Vec2f(-500, 250 + i * 50), statistic.getSitesString(i)).setTextAlignment(ALIGNMENT.LEFT)
					.setVerticalAlignment(ALIGNMENT.CENTER).setHorizontalAlignment(ALIGNMENT.TOP));
			drawables.add(new TextBox(new Vec2f(-250, 250 + i * 50), statistic.getNameString(i)).setTextAlignment(ALIGNMENT.LEFT)
					.setVerticalAlignment(ALIGNMENT.CENTER).setHorizontalAlignment(ALIGNMENT.TOP));
			drawables.add(new TextBox(new Vec2f(200, 250 + i * 50), statistic.getBaseHPString(i)).setTextAlignment(ALIGNMENT.RIGHT)
					.setVerticalAlignment(ALIGNMENT.CENTER).setHorizontalAlignment(ALIGNMENT.TOP));
			drawables.add(new TextBox(new Vec2f(450, 250 + i * 50), statistic.getScoreString(i)).setTextAlignment(ALIGNMENT.RIGHT)
					.setVerticalAlignment(ALIGNMENT.CENTER).setHorizontalAlignment(ALIGNMENT.TOP));
		}
	}
}
