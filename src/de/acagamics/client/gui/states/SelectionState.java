package de.acagamics.client.gui.states;

import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.List;

import de.acagamics.client.gui.StateManager;
import de.acagamics.client.gui.elements.Button;
import de.acagamics.client.gui.elements.Button.BUTTON_TYPE;
import de.acagamics.client.gui.elements.Selector;
import de.acagamics.client.gui.elements.TextBox;
import de.acagamics.client.gui.interfaces.MenuState;
import de.acagamics.client.utility.BotClassLoader;
import de.acagamics.constants.DesignConstants.ALINGMENT;
import de.acagamics.data.InGameSettings;
import de.acagamics.data.InGameSettings.GAMEMODE;
import de.acagamics.game.controller.builtIn.EvilSanta;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

public class SelectionState extends MenuState {

	private Selector[] botSelectors;
	private BotClassLoader playerLoader;
	private List<Class<?>> bots;

	private Selector modeSelector;

	private Selector speedSelectors;

	public SelectionState(StateManager manager, GraphicsContext context) {
		super(manager, context);

		playerLoader = new BotClassLoader();
		playerLoader.loadControllerFromDirectory(FileSystems.getDefault().getPath("").toAbsolutePath().toString());
		bots = playerLoader.getLoadedBots();

		drawables.add(new TextBox(new Point2D(200, 100), "Bot Selection").setVerticalAlignment(ALINGMENT.LEFT)
				.setHorizontalAlignment(ALINGMENT.TOP));

		modeSelector = new Selector(new Point2D(0, 200), 200, 0, GAMEMODE.values().length - 1,
				(i) -> GAMEMODE.values()[i].toString()).setVerticalAlignment(ALINGMENT.CENTER);
		clickable.add(modeSelector);

		Button startbutton = new Button(new Point2D(-175, -125), BUTTON_TYPE.NORMAL, "Start",
				() -> manager.push(new InGameState(manager, context, generateSettings())))
						.setVerticalAlignment(ALINGMENT.RIGHT).setHorizontalAlignment(ALINGMENT.BOTTOM)
						.setKeyCode(KeyCode.ENTER);
		clickable.add(startbutton);

		clickable.add(new Button(new Point2D(-300, -125), BUTTON_TYPE.NORMAL, "Back", () -> manager.pop())
				.setVerticalAlignment(ALINGMENT.RIGHT).setHorizontalAlignment(ALINGMENT.BOTTOM)
				.setKeyCode(KeyCode.ESCAPE));

		if (!bots.isEmpty()) {
			botSelectors = new Selector[2];
			for(int i = 0; i < botSelectors.length; ++i) {
				botSelectors[i] = new Selector(new Point2D(0, 400 + i * 100), 200, 0, bots.size() - 1,
						(i2) -> bots.get(i2).getSimpleName()).setVerticalAlignment(ALINGMENT.CENTER);
				clickable.add(botSelectors[i]);
			}
		} else {
			startbutton.setEnabled(false);
		}

		drawables.add(new TextBox(new Point2D(200, -200), "Speed Multiplier").setHorizontalAlignment(ALINGMENT.BOTTOM));
		speedSelectors = new Selector(new Point2D(200, -125), 100, 1, 16, (i) -> i + "x")
				.setHorizontalAlignment(ALINGMENT.BOTTOM);
		clickable.add(speedSelectors);

	}

	private InGameSettings generateSettings() {
		List<String> names = new ArrayList<>(2);
		if (GAMEMODE.values()[modeSelector.getValue()] == GAMEMODE.NORMAL) {
			for (int i = 0; i < 2; ++i) {
				names.add(bots.get(botSelectors[i].getValue()).getName());
			}
		} else if (GAMEMODE.values()[modeSelector.getValue()] == GAMEMODE.XMAS_CHALLENGE) {
			names.add(bots.get(botSelectors[0].getValue()).getName());
			names.add(EvilSanta.class.getName());
		}
		return new InGameSettings(GAMEMODE.values()[modeSelector.getValue()], playerLoader, names, speedSelectors.getValue());
	}
}
