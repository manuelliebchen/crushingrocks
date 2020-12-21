package de.acagamics.crushingrocks.states;

import de.acagamics.crushingrocks.controllers.BotyMcBotface;
import de.acagamics.crushingrocks.logic.GameMode;
import de.acagamics.crushingrocks.logic.IPlayerController;
import de.acagamics.crushingrocks.logic.Map;
import de.acagamics.crushingrocks.logic.Player;
import de.acagamics.crushingrocks.rendering.Background;
import de.acagamics.crushingrocks.types.MatchSettings;
import de.acagamics.framework.geometry.Vec2f;
import de.acagamics.framework.resources.DesignProperties;
import de.acagamics.framework.resources.ResourceManager;
import de.acagamics.framework.simulation.Tournament;
import de.acagamics.framework.ui.StateManager;
import de.acagamics.framework.ui.elements.Button;
import de.acagamics.framework.ui.elements.Button.BUTTON_TYPE;
import de.acagamics.framework.ui.elements.DynamicTextBox;
import de.acagamics.framework.ui.elements.Selector;
import de.acagamics.framework.ui.elements.TextBox;
import de.acagamics.framework.ui.interfaces.ALIGNMENT;
import de.acagamics.framework.ui.interfaces.SelfUpdatingState;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class TournamentState extends SelfUpdatingState {
	private static final Logger LOG = LogManager.getLogger(TournamentState.class.getName());

	private List<Class<?>> bots;

	private Selector<Integer> threadSelector;
	private Selector<Integer> runsSelector;

	private Button startbutton;

	private Tournament tournament;

	public TournamentState(StateManager manager, GraphicsContext context) {
		super(manager, context);


		drawables.add(new Background(100,  0.2f, new Map(new Random(), new ArrayList<Player>())));

		bots = ResourceManager.getInstance().loadContorller(IPlayerController.class).stream().filter(c->!c.getPackageName().equals(BotyMcBotface.class.getPackageName())).collect(Collectors.toList());

		drawables.add(new TextBox(new Vec2f(200, 50), "Tournament Selection").setFont(ResourceManager.getInstance().loadProperties(DesignProperties.class).getSubtitleFont()).setVerticalAlignment(ALIGNMENT.LEFT)
				.setHorizontalAlignment(ALIGNMENT.UPPER));
		clickable.add(new Button(new Vec2f(-325, -120), BUTTON_TYPE.NORMAL, "Back", manager::pop)
				.setKeyCode(KeyCode.ESCAPE).setVerticalAlignment(ALIGNMENT.RIGHT)
				.setHorizontalAlignment(ALIGNMENT.LOWER));

		drawables.add(new TextBox(new Vec2f(-250, 300), "Threads:").setVerticalAlignment(ALIGNMENT.CENTER));
		threadSelector = new Selector(new Vec2f(0, 300), 150, Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16), i -> i + "x", false)
				.setVerticalAlignment(ALIGNMENT.CENTER);
		clickable.add(threadSelector);

		drawables.add(new TextBox(new Vec2f(-250, 400), "Runs:").setVerticalAlignment(ALIGNMENT.CENTER));
		runsSelector = new Selector(new Vec2f(0, 400), 150, Arrays.asList(1,10,100,1000,10000), i -> i + "x", false).setVerticalAlignment(ALIGNMENT.CENTER);
		clickable.add(runsSelector);

		startbutton = new Button(new Vec2f(-175, -120), BUTTON_TYPE.NORMAL, "Start",
				this::runTournament).setKeyCode(KeyCode.ENTER)
				.setVerticalAlignment(ALIGNMENT.RIGHT).setHorizontalAlignment(ALIGNMENT.LOWER);
		clickable.add(startbutton);

		Button save = new Button(new Vec2f(175, -120), Button.BUTTON_TYPE.NORMAL, "Save .csv",
				this::saveCSV ).setKeyCode(KeyCode.ENTER)
				.setVerticalAlignment(ALIGNMENT.LEFT).setHorizontalAlignment(ALIGNMENT.LOWER);
		clickable.add(save);

		if(bots.isEmpty()){
			startbutton.setEnabled(false);
			save.setEnabled(false);
		}
	}

	private void runTournament() {
		tournament = new Tournament(bots, ( s, c1,  c2) -> new MatchSettings(GameMode.NORMAL, s, Arrays.asList(c1, c2)), new Random().nextLong(), threadSelector.getValue(), runsSelector.getValue());

		drawables.add( new DynamicTextBox(new Vec2f(-150, 50), () -> String.format("Progress: %3d%%", tournament.getProgress())).setVerticalAlignment(ALIGNMENT.RIGHT).setHorizontalAlignment(ALIGNMENT.UPPER));
		drawables.add( new DynamicTextBox(new Vec2f(-450, 50), () -> String.format("Time: %7.2f", tournament.getTimeElapsed())).setVerticalAlignment(ALIGNMENT.RIGHT).setHorizontalAlignment(ALIGNMENT.UPPER));

		Thread runner = new Thread(tournament);
		runner.start();
	}

	private void saveCSV() {
		try(FileWriter myWriter = new FileWriter("tournament_data.csv")) {
			myWriter.write(tournament.getCSV());
		} catch (IOException e) {
			LOG.error(e);
		}
	}

}
