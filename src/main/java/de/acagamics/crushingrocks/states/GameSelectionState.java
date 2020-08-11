package de.acagamics.crushingrocks.states;

import de.acagamics.crushingrocks.controllers.EvilSanta;
import de.acagamics.crushingrocks.logic.GameMode;
import de.acagamics.crushingrocks.logic.IPlayerController;
import de.acagamics.crushingrocks.logic.Map;
import de.acagamics.crushingrocks.logic.Player;
import de.acagamics.crushingrocks.rendering.Background;
import de.acagamics.crushingrocks.types.MatchSettings;
import de.acagamics.framework.geometry.Vec2f;
import de.acagamics.framework.resources.DesignProperties;
import de.acagamics.framework.resources.ResourceManager;
import de.acagamics.framework.simulation.GameStatistic;
import de.acagamics.framework.ui.StateManager;
import de.acagamics.framework.ui.elements.Button;
import de.acagamics.framework.ui.elements.Button.BUTTON_TYPE;
import de.acagamics.framework.ui.elements.Selector;
import de.acagamics.framework.ui.elements.TextBox;
import de.acagamics.framework.ui.interfaces.ALIGNMENT;
import de.acagamics.framework.ui.interfaces.IDrawable;
import de.acagamics.framework.ui.interfaces.UIState;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GameSelectionState extends UIState {

	Random random;

	private Selector[] botSelectors;
	private List<Class<?>> bots;

	private Selector modeSelector;

	private Selector speedSelectors;

	public GameSelectionState(StateManager manager, GraphicsContext context) {
		super(manager, context);

		this.random = new Random();

		drawables.add(new Background(100, 0.2f, new Map(new Random(), new ArrayList<Player>())));

		bots = ResourceManager.getInstance().loadContorller(IPlayerController.class);

		drawables.add((IDrawable) new TextBox(new Vec2f(200, 50), "Bot Selection").setFont(ResourceManager.getInstance().loadProperties(DesignProperties.class).getSubtitleFont()).setVerticalAlignment(ALIGNMENT.LEFT)
				.setHorizontalAlignment(ALIGNMENT.UPPER));

		drawables.add((IDrawable) new TextBox(new Vec2f(-350, 300), "Game Mode:").setVerticalAlignment(ALIGNMENT.CENTER));
		modeSelector = new Selector(new Vec2f(0, 300), 200, Arrays.asList(GameMode.values()),
				i -> i.toString(),true).setVerticalAlignment(ALIGNMENT.CENTER);
		clickable.add(modeSelector);

		Button startbutton = new Button(new Vec2f(-175, -120), BUTTON_TYPE.NORMAL, "Start",
				() -> manager.push(new GameState(manager, context, generateSettings(), speedSelectors.getValue()))).setKeyCode(KeyCode.ENTER)
						.setVerticalAlignment(ALIGNMENT.RIGHT).setHorizontalAlignment(ALIGNMENT.LOWER);
		clickable.add(startbutton);

		clickable.add(new Button(new Vec2f(-325, -120), BUTTON_TYPE.NORMAL, "Back", manager::pop)
				.setKeyCode(KeyCode.ESCAPE).setVerticalAlignment(ALIGNMENT.RIGHT)
				.setHorizontalAlignment(ALIGNMENT.LOWER));


		drawables.add((IDrawable) new TextBox(new Vec2f(-350, 400), "Bots:").setVerticalAlignment(ALIGNMENT.CENTER));
		if (!bots.isEmpty()) {
			botSelectors = new Selector[2];
			for (int i = 0; i < botSelectors.length; ++i) {
				botSelectors[i] = new Selector(new Vec2f(0, 400.0f + i * 100), 200, bots,
						i2 -> GameStatistic.getName((Class<IPlayerController>)i2), true).setVerticalAlignment(ALIGNMENT.CENTER);
				clickable.add(botSelectors[i]);
			}
		} else {
			startbutton.setEnabled(false);
		}

		drawables.add((IDrawable) new TextBox(new Vec2f(200, -200), "Speed Multiplier").setHorizontalAlignment(ALIGNMENT.LOWER));
		speedSelectors = new Selector(new Vec2f(200, -120), 100, Arrays.asList(1,2,4,8,16), i -> i + "x",false)
				.setHorizontalAlignment(ALIGNMENT.LOWER);
		clickable.add(speedSelectors);

	}

	private MatchSettings generateSettings() {
		List<Class<?>> names = new ArrayList<>(2);
		if (GameMode.values()[modeSelector.getValue()] == GameMode.NORMAL) {
			for (int i = 0; i < 2; ++i) {
				names.add(bots.get(botSelectors[i].getValue()));
			}
		} else if (GameMode.values()[modeSelector.getValue()] == GameMode.XMAS_CHALLENGE) {
			names.add(bots.get(botSelectors[0].getValue()));
			names.add((Class<IPlayerController>) EvilSanta.class.asSubclass(IPlayerController.class));
		}
		return new MatchSettings(GameMode.values()[modeSelector.getValue()], random.nextLong(), names);
	}
}
