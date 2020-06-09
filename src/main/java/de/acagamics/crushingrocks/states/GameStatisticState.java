package de.acagamics.crushingrocks.states;

import de.acagamics.crushingrocks.logic.Map;
import de.acagamics.crushingrocks.rendering.Background;
import de.acagamics.framework.geometry.Vec2f;
import de.acagamics.framework.resources.DesignProperties;
import de.acagamics.framework.resources.ResourceManager;
import de.acagamics.framework.simulation.GameStatistic;
import de.acagamics.framework.ui.StateManager;
import de.acagamics.framework.ui.elements.Button;
import de.acagamics.framework.ui.elements.Button.BUTTON_TYPE;
import de.acagamics.framework.ui.elements.TextBox;
import de.acagamics.framework.ui.interfaces.ALIGNMENT;
import de.acagamics.framework.ui.interfaces.UIState;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

import java.util.List;

public class GameStatisticState extends UIState {
	public GameStatisticState(StateManager manager, GraphicsContext context, GameStatistic statistic,
							  Map background) {
		super(manager, context);

		drawables.add(new Background(100, 0.2f, background));

		clickable.add(new Button(new Vec2f(-200, -125), BUTTON_TYPE.NORMAL, "Back",
				manager::pop).setKeyCode(KeyCode.ENTER)
						.setVerticalAlignment(ALIGNMENT.RIGHT).setHorizontalAlignment(ALIGNMENT.LOWER));

		drawables.add(new TextBox(new Vec2f(200, 50), "Statistics").setFont(ResourceManager.getInstance().loadProperties(DesignProperties.class).getSubtitleFont()).setVerticalAlignment(ALIGNMENT.LEFT)
				.setHorizontalAlignment(ALIGNMENT.UPPER));
		if (statistic.isDraw()) {
			drawables.add(new TextBox(new Vec2f(0, 50),  "It's a Draw!").setFont(ResourceManager.getInstance().loadProperties(DesignProperties.class).getSubtitleFont()).setVerticalAlignment(ALIGNMENT.LEFT)
					.setHorizontalAlignment(ALIGNMENT.UPPER));
		}

		List<Object> scores = statistic.getOrderedControllers();
		for(int i = 0; i < statistic.getPlayerAmount(); ++i) {
			drawables.add(new TextBox(new Vec2f(-300, 250.0f + i * 50), String.valueOf(i+1) + ".").setTextAlignment(ALIGNMENT.LEFT)
					.setVerticalAlignment(ALIGNMENT.CENTER).setHorizontalAlignment(ALIGNMENT.UPPER));
			drawables.add(new TextBox(new Vec2f(-100, 250.0f + i * 50), GameStatistic.getName(scores.get(i).getClass())).setTextAlignment(ALIGNMENT.LEFT)
					.setVerticalAlignment(ALIGNMENT.CENTER).setHorizontalAlignment(ALIGNMENT.UPPER));
		}
	}
}
