package de.acagamics.crushingrocks.states;

import de.acagamics.crushingrocks.logic.Map;
import de.acagamics.crushingrocks.logic.Player;
import de.acagamics.crushingrocks.rendering.Background;
import de.acagamics.framework.types.GameStatistic;
import de.acagamics.framework.resources.DesignProperties;
import de.acagamics.framework.resources.ResourceManager;
import de.acagamics.crushingrocks.types.MatchSettings;
import de.acagamics.framework.types.Vec2f;
import de.acagamics.framework.ui.StateManager;
import de.acagamics.framework.ui.elements.Button;
import de.acagamics.framework.ui.elements.TextBox;
import de.acagamics.framework.ui.elements.Button.BUTTON_TYPE;
import de.acagamics.framework.ui.interfaces.ALIGNMENT;
import de.acagamics.framework.ui.interfaces.MenuState;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameStatisticState extends MenuState {

	public GameStatisticState(StateManager manager, GraphicsContext context, GameStatistic statistic,
							  MatchSettings settings) {
		super(manager, context);

		drawables.add(new Background(100, 0.2f, new Map(new Random(), new ArrayList<Player>())));

		clickable.add(new Button(new Vec2f(-350, -125), BUTTON_TYPE.NORMAL, "Back", manager::pop)
				.setKeyCode(KeyCode.ESCAPE).setVerticalAlignment(ALIGNMENT.RIGHT)
				.setHorizontalAlignment(ALIGNMENT.LOWER));
		clickable.add(new Button(new Vec2f(-200, -125), BUTTON_TYPE.NORMAL, "Restart",
				() -> manager.switchCurrentState(new GameState(manager, context, settings, 1))).setKeyCode(KeyCode.ENTER)
						.setVerticalAlignment(ALIGNMENT.RIGHT).setHorizontalAlignment(ALIGNMENT.LOWER));

		drawables.add(new TextBox(new Vec2f(200, 50), "Statistics").setFont(ResourceManager.getInstance().loadProperties(DesignProperties.class).getSubtitleFont()).setVerticalAlignment(ALIGNMENT.LEFT)
				.setHorizontalAlignment(ALIGNMENT.UPPER));
		if (statistic.isDraw()) {
			drawables.add(new TextBox(new Vec2f(0, 50),  "It's a Draw!").setFont(ResourceManager.getInstance().loadProperties(DesignProperties.class).getSubtitleFont()).setVerticalAlignment(ALIGNMENT.LEFT)
					.setHorizontalAlignment(ALIGNMENT.UPPER));
		}

		List<Object> scores = statistic.getOrderedControllers();
		for(int i = 0; i < statistic.getPlayerAmount(); ++i) {
			drawables.add(new TextBox(new Vec2f(-300, 350.0f + i * 50), String.valueOf(i+1) + ".").setTextAlignment(ALIGNMENT.LEFT)
					.setVerticalAlignment(ALIGNMENT.CENTER).setHorizontalAlignment(ALIGNMENT.UPPER));
			drawables.add(new TextBox(new Vec2f(-100, 350.0f + i * 50), scores.get(i).getClass().getSimpleName()).setTextAlignment(ALIGNMENT.LEFT)
					.setVerticalAlignment(ALIGNMENT.CENTER).setHorizontalAlignment(ALIGNMENT.UPPER));
		}
	}
}
