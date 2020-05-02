package de.acagamics.crushingrocks.states;

import de.acagamics.crushingrocks.Tournament;
import de.acagamics.crushingrocks.controller.IPlayerController;
import de.acagamics.crushingrocks.logic.Map;
import de.acagamics.crushingrocks.logic.Player;
import de.acagamics.crushingrocks.rendering.Background;
import de.acagamics.framework.resources.ClientProperties;
import de.acagamics.framework.resources.DesignProperties;
import de.acagamics.framework.resources.ResourceManager;
import de.acagamics.framework.types.Vec2f;
import de.acagamics.framework.ui.StateManager;
import de.acagamics.framework.ui.elements.Button;
import de.acagamics.framework.ui.elements.Button.BUTTON_TYPE;
import de.acagamics.framework.ui.elements.DynamicTextBox;
import de.acagamics.framework.ui.elements.Selector;
import de.acagamics.framework.ui.elements.TextBox;
import de.acagamics.framework.ui.interfaces.ALIGNMENT;
import de.acagamics.framework.ui.interfaces.ISelfUpdating;
import de.acagamics.framework.ui.interfaces.MenuState;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class TournamentSelectionState extends MenuState implements ISelfUpdating {
	private static final Logger LOG = LogManager.getLogger(TournamentSelectionState.class.getName());

	private Timeline timeline;

	private List<Class<?>> bots;

	private Selector threadSelector;
	private Selector runsSelector;

	private Button startbutton;

	private Tournament tournament;

	public TournamentSelectionState(StateManager manager, GraphicsContext context) {
		super(manager, context);


		drawables.add(new Background(100,  0.2f, new Map(new Random(), new ArrayList<Player>())));

		bots = ResourceManager.getInstance().loadContorller(IPlayerController.class).stream().filter(c->!c.getPackageName().equals("de.acagamics.crushingrocks.controller.sample")).collect(Collectors.toList());

		drawables.add(new TextBox(new Vec2f(200, 50), "Tournament Selection").setFont(ResourceManager.getInstance().loadProperties(DesignProperties.class).getSubtitleFont()).setVerticalAlignment(ALIGNMENT.LEFT)
				.setHorizontalAlignment(ALIGNMENT.UPPER));
		clickable.add(new Button(new Vec2f(-325, -120), BUTTON_TYPE.NORMAL, "Back", manager::pop)
				.setKeyCode(KeyCode.ESCAPE).setVerticalAlignment(ALIGNMENT.RIGHT)
				.setHorizontalAlignment(ALIGNMENT.LOWER));

		drawables.add(new TextBox(new Vec2f(-250, 300), "Threads:").setVerticalAlignment(ALIGNMENT.CENTER));
		threadSelector = new Selector(new Vec2f(0, 300), 150, 1, 16, i -> i + "x")
				.setVerticalAlignment(ALIGNMENT.CENTER);
		clickable.add(threadSelector);

		drawables.add(new TextBox(new Vec2f(-250, 400), "Runs:").setVerticalAlignment(ALIGNMENT.CENTER));
		runsSelector = new Selector(new Vec2f(0, 400), 150, 1, 6, i -> (int) Math.pow(10, i) + "x").setVerticalAlignment(ALIGNMENT.CENTER);
		clickable.add(runsSelector);

		startbutton = new Button(new Vec2f(-175, -120), BUTTON_TYPE.NORMAL, "Start",
				this::runTournament).setKeyCode(KeyCode.ENTER)
				.setVerticalAlignment(ALIGNMENT.RIGHT).setHorizontalAlignment(ALIGNMENT.LOWER);
		clickable.add(startbutton);

		clickable.add(new Button(new Vec2f(175, -120), Button.BUTTON_TYPE.NORMAL, "Save .csv",
				this::saveCSV ).setKeyCode(KeyCode.ENTER)
				.setVerticalAlignment(ALIGNMENT.LEFT).setHorizontalAlignment(ALIGNMENT.LOWER));

		timeline = new Timeline();
		KeyFrame frame = new KeyFrame(
				Duration.millis((double) ResourceManager.getInstance().loadProperties(ClientProperties.class).getMilisPerFrame()), event ->
				frame()
		);
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.getKeyFrames().add(frame);
	}

	private void runTournament() {
		tournament = new Tournament(bots, new Random().nextLong(),threadSelector.getValue(), (int) Math.pow(10, runsSelector.getValue()));

		drawables.add( new DynamicTextBox(new Vec2f(-150, 50), () -> String.format("Progress: %3.0f%%", tournament.getProgress() * 100)).setVerticalAlignment(ALIGNMENT.RIGHT).setHorizontalAlignment(ALIGNMENT.UPPER));
		drawables.add( new DynamicTextBox(new Vec2f(-450, 50), () -> String.format("Time: %7.2f", tournament.getTimeElapsed())).setVerticalAlignment(ALIGNMENT.RIGHT).setHorizontalAlignment(ALIGNMENT.UPPER));

		tournament.start();
	}

	private void saveCSV() {
		try(FileWriter myWriter = new FileWriter("tournament_data.csv")) {
			myWriter.write(tournament.getCSV());
		} catch (IOException e) {
			LOG.error(e);
		}
	}

	@Override
	public void entered() {
		timeline.play();
	}

	@Override
	public void leaving() {
		timeline.stop();
	}
}
