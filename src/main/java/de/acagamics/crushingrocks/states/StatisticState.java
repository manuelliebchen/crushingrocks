package de.acagamics.crushingrocks.states;

import de.acagamics.crushingrocks.GameStatistic;
import de.acagamics.crushingrocks.controller.IPlayerController;
import de.acagamics.framework.resources.DesignProperties;
import de.acagamics.framework.resources.ResourceManager;
import de.acagamics.framework.types.MatchSettings;
import de.acagamics.framework.types.Vec2f;
import de.acagamics.framework.ui.StateManager;
import de.acagamics.framework.ui.elements.Button;
import de.acagamics.framework.ui.elements.TextBox;
import de.acagamics.framework.ui.elements.Button.BUTTON_TYPE;
import de.acagamics.framework.ui.interfaces.ALIGNMENT;
import de.acagamics.framework.ui.interfaces.MenuState;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

public class StatisticState extends MenuState {

	public StatisticState(StateManager manager, GraphicsContext context, GameStatistic statistic,
			MatchSettings settings) {
		super(manager, context);

		clickable.add((Button) (new Button(new Vec2f(-350, -125), BUTTON_TYPE.NORMAL, "Back", manager::pop)
				.setKeyCode(KeyCode.ESCAPE).setVerticalAlignment(ALIGNMENT.RIGHT)
				.setHorizontalAlignment(ALIGNMENT.LOWER)));
		clickable.add((Button) (new Button(new Vec2f(-200, -125), BUTTON_TYPE.NORMAL, "Restart",
				() -> manager.switchCurrentState(new InGameState(manager, context, settings))).setKeyCode(KeyCode.ENTER)
						.setVerticalAlignment(ALIGNMENT.RIGHT).setHorizontalAlignment(ALIGNMENT.LOWER)));

		String title = "Statistics";
		if (statistic.isDraw()) {
			title += " - Tie-Break";
		}
		drawables.add(new TextBox(new Vec2f(200, 50), title).setFont(ResourceManager.getInstance().loadProperties(DesignProperties.class).getSubtitleFont()).setVerticalAlignment(ALIGNMENT.LEFT)
				.setHorizontalAlignment(ALIGNMENT.UPPER));
		
		drawables.add(new TextBox(new Vec2f(-500, 300), "Color").setTextAlignment(ALIGNMENT.LEFT)
				.setVerticalAlignment(ALIGNMENT.CENTER).setHorizontalAlignment(ALIGNMENT.UPPER));
		drawables.add(new TextBox(new Vec2f(-250, 300), "Name").setTextAlignment(ALIGNMENT.LEFT)
				.setVerticalAlignment(ALIGNMENT.CENTER).setHorizontalAlignment(ALIGNMENT.UPPER));
		drawables.add(new TextBox(new Vec2f(200, 300), "Base HP").setTextAlignment(ALIGNMENT.RIGHT)
				.setVerticalAlignment(ALIGNMENT.CENTER).setHorizontalAlignment(ALIGNMENT.UPPER));
		drawables.add(new TextBox(new Vec2f(450, 300), "Score").setTextAlignment(ALIGNMENT.RIGHT)
				.setVerticalAlignment(ALIGNMENT.CENTER).setHorizontalAlignment(ALIGNMENT.UPPER));
		
		for(int i = 0; i < statistic.getPlayerAmount(); ++i) {			
			drawables.add(new TextBox(new Vec2f(-500, 350.0f + i * 50), statistic.getSitesString(i)).setTextAlignment(ALIGNMENT.LEFT)
					.setVerticalAlignment(ALIGNMENT.CENTER).setHorizontalAlignment(ALIGNMENT.UPPER));
			drawables.add(new TextBox(new Vec2f(-250, 350.0f + i * 50), statistic.getNameString(i)).setTextAlignment(ALIGNMENT.LEFT)
					.setVerticalAlignment(ALIGNMENT.CENTER).setHorizontalAlignment(ALIGNMENT.UPPER));
			drawables.add(new TextBox(new Vec2f(200, 350.0f + i * 50), statistic.getBaseHPString(i)).setTextAlignment(ALIGNMENT.RIGHT)
					.setVerticalAlignment(ALIGNMENT.CENTER).setHorizontalAlignment(ALIGNMENT.UPPER));
			drawables.add(new TextBox(new Vec2f(450, 350.0f + i * 50), statistic.getScoreString(i)).setTextAlignment(ALIGNMENT.RIGHT)
					.setVerticalAlignment(ALIGNMENT.CENTER).setHorizontalAlignment(ALIGNMENT.UPPER));
		}
	}
}
