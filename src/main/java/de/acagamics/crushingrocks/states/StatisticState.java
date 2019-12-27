package de.acagamics.crushingrocks.states;

import de.acagamics.framework.gui.StateManager;
import de.acagamics.framework.gui.elements.Button;
import de.acagamics.framework.gui.elements.Button.BUTTON_TYPE;
import de.acagamics.framework.gui.elements.TextBox;
import de.acagamics.framework.gui.interfaces.ALIGNMENT;
import de.acagamics.framework.gui.interfaces.MenuState;
import de.acagamics.framework.resourcemanagment.ResourceManager;
import de.acagamics.framework.types.GameStatistic;
import de.acagamics.framework.types.InGameSettings;
import de.acagamics.framework.types.Vec2f;
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
		drawables.add(new TextBox(new Vec2f(200, 50), title).setFont(ResourceManager.getInstance().getDesignProperties().getSubtitleFont()).setVerticalAlignment(ALIGNMENT.LEFT)
				.setHorizontalAlignment(ALIGNMENT.TOP));
		
		drawables.add(new TextBox(new Vec2f(-500, 300), "Color").setTextAlignment(ALIGNMENT.LEFT)
				.setVerticalAlignment(ALIGNMENT.CENTER).setHorizontalAlignment(ALIGNMENT.TOP));
		drawables.add(new TextBox(new Vec2f(-250, 300), "Name").setTextAlignment(ALIGNMENT.LEFT)
				.setVerticalAlignment(ALIGNMENT.CENTER).setHorizontalAlignment(ALIGNMENT.TOP));
		drawables.add(new TextBox(new Vec2f(200, 300), "Base HP").setTextAlignment(ALIGNMENT.RIGHT)
				.setVerticalAlignment(ALIGNMENT.CENTER).setHorizontalAlignment(ALIGNMENT.TOP));
		drawables.add(new TextBox(new Vec2f(450, 300), "Score").setTextAlignment(ALIGNMENT.RIGHT)
				.setVerticalAlignment(ALIGNMENT.CENTER).setHorizontalAlignment(ALIGNMENT.TOP));
		
		for(int i = 0; i < statistic.getPlayerAmount(); ++i) {			
			drawables.add(new TextBox(new Vec2f(-500, 350 + i * 50), statistic.getSitesString(i)).setTextAlignment(ALIGNMENT.LEFT)
					.setVerticalAlignment(ALIGNMENT.CENTER).setHorizontalAlignment(ALIGNMENT.TOP));
			drawables.add(new TextBox(new Vec2f(-250, 350 + i * 50), statistic.getNameString(i)).setTextAlignment(ALIGNMENT.LEFT)
					.setVerticalAlignment(ALIGNMENT.CENTER).setHorizontalAlignment(ALIGNMENT.TOP));
			drawables.add(new TextBox(new Vec2f(200, 350 + i * 50), statistic.getBaseHPString(i)).setTextAlignment(ALIGNMENT.RIGHT)
					.setVerticalAlignment(ALIGNMENT.CENTER).setHorizontalAlignment(ALIGNMENT.TOP));
			drawables.add(new TextBox(new Vec2f(450, 350 + i * 50), statistic.getScoreString(i)).setTextAlignment(ALIGNMENT.RIGHT)
					.setVerticalAlignment(ALIGNMENT.CENTER).setHorizontalAlignment(ALIGNMENT.TOP));
		}
	}
}