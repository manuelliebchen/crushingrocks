package de.acagamics.crushingrocks.states;

import de.acagamics.crushingrocks.types.MatchSettings;
import de.acagamics.crushingrocks.controller.IPlayerController;
import de.acagamics.crushingrocks.logic.Game;
import de.acagamics.crushingrocks.logic.Player;
import de.acagamics.crushingrocks.rendering.Background;
import de.acagamics.framework.resources.ClientProperties;
import de.acagamics.framework.resources.DesignProperties;
import de.acagamics.framework.resources.ResourceManager;
import de.acagamics.framework.simulation.Simulator;
import de.acagamics.framework.types.SimulationSettings;
import de.acagamics.framework.types.SimulationStatistic;
import de.acagamics.framework.types.Student;
import de.acagamics.framework.types.Vec2f;
import de.acagamics.framework.ui.StateManager;
import de.acagamics.framework.ui.elements.*;
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

/**
 * @author Claudius Grimm (claudius@acagamics.de)
 * @author Manuel Liebchen
 */
public final class SimulationState extends MenuState implements ISelfUpdating {
	private static final Logger LOG = LogManager.getLogger(SimulationState.class.getName());

	private Timeline timeline;

	private Simulator<Game, IPlayerController> simulator;
	private MatchSettings matchSettings;

	public SimulationState(StateManager manager, GraphicsContext context, SimulationSettings settings, MatchSettings matchSettings) {
		super(manager, context);
		this.matchSettings = matchSettings;

		drawables.add(new Background(100, 0.2f, new de.acagamics.crushingrocks.logic.Map(new Random(), new ArrayList<Player>())));

		this.simulator = new Simulator<> (settings, matchSettings);

		clickable.add( new Button(new Vec2f(-175, -120), Button.BUTTON_TYPE.NORMAL, "Restart",
				() -> manager.switchCurrentState(new SimulationState(manager, context, settings, matchSettings))).setKeyCode(KeyCode.ENTER)
				.setVerticalAlignment(ALIGNMENT.RIGHT).setHorizontalAlignment(ALIGNMENT.LOWER));
		clickable.add(new Button(new Vec2f(-325, -120), Button.BUTTON_TYPE.NORMAL, "Back", manager::pop)
				.setKeyCode(KeyCode.ESCAPE).setVerticalAlignment(ALIGNMENT.RIGHT)
				.setHorizontalAlignment(ALIGNMENT.LOWER));


		clickable.add(new Button(new Vec2f(175, -120), Button.BUTTON_TYPE.NORMAL, "Save .csv",
				this::saveCSV ).setKeyCode(KeyCode.ENTER)
				.setVerticalAlignment(ALIGNMENT.LEFT).setHorizontalAlignment(ALIGNMENT.LOWER));


		drawables.add(new TextBox(new Vec2f(250, 50), "Simulation Statistics").setFont(ResourceManager.getInstance().loadProperties(DesignProperties.class).getSubtitleFont()).setVerticalAlignment(ALIGNMENT.LEFT)
				.setHorizontalAlignment(ALIGNMENT.UPPER));


		drawables.add( new DynamicTextBox(new Vec2f(-150, 50), () -> "Draws:" + simulator.getStatistics().getDraws()).setVerticalAlignment(ALIGNMENT.RIGHT).setHorizontalAlignment(ALIGNMENT.UPPER));
		drawables.add( new DynamicTextBox(new Vec2f(-300, 50), () -> String.format("Runs: %7d", simulator.getStatistics().getVictories().values().stream().reduce(0,Integer::sum))).setVerticalAlignment(ALIGNMENT.RIGHT).setHorizontalAlignment(ALIGNMENT.UPPER));
		drawables.add( new DynamicTextBox(new Vec2f(-500, 50), () -> String.format("Progress: %3.0f%%", simulator.getProgress() * 100)).setVerticalAlignment(ALIGNMENT.RIGHT).setHorizontalAlignment(ALIGNMENT.UPPER));
		drawables.add( new DynamicTextBox(new Vec2f(-700, 50), () -> String.format("Time: %7.2f", simulator.getTimeElapsed())).setVerticalAlignment(ALIGNMENT.RIGHT).setHorizontalAlignment(ALIGNMENT.UPPER));

		List<Class<?>> controllers = matchSettings.getControllersClasses();
		SimulationStatistic<IPlayerController> scroes = simulator.getStatistics();
		for(int i = 0; i < controllers.size(); ++i) {
			float y = 350.0f + i * 50;
			Class<?> controller = controllers.get(i);
			drawables.add(new TextBox(new Vec2f(-300, y), String.valueOf(i+1) + ".").setTextAlignment(ALIGNMENT.LEFT)
					.setVerticalAlignment(ALIGNMENT.CENTER).setHorizontalAlignment(ALIGNMENT.UPPER));
			drawables.add(new TextBox(new Vec2f(-250, y), controller.getAnnotation(Student.class).name()).setTextAlignment(ALIGNMENT.LEFT)
					.setVerticalAlignment(ALIGNMENT.CENTER).setHorizontalAlignment(ALIGNMENT.UPPER));
			drawables.add(new TextBox(new Vec2f(0, y), controller.getSimpleName()).setTextAlignment(ALIGNMENT.LEFT)
					.setVerticalAlignment(ALIGNMENT.CENTER).setHorizontalAlignment(ALIGNMENT.UPPER));
			drawables.add(new DynamicTextBox(new Vec2f(400, y),() ->
					String.format("%7d", scroes.getVictories(controller))).setVerticalAlignment(ALIGNMENT.CENTER).setHorizontalAlignment(ALIGNMENT.UPPER));

		}

		timeline = new Timeline();
		KeyFrame frame = new KeyFrame(
				Duration.millis((double) ResourceManager.getInstance().loadProperties(ClientProperties.class).getMilisPerFrame()), event ->
				frame()
		);

		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.getKeyFrames().add(frame);
		simulator.start();
	}

	public void saveCSV(){
		try(FileWriter myWriter = new FileWriter("simulation_data.csv", true)) {
			StringBuilder bld = new StringBuilder();
			bld.append(String.format("%8d, %8d, %8.3f, ", simulator.getSettings().getRuns(), simulator.getStatistics().getDraws(), simulator.getTimeElapsed()));
			for(Class<?> entry : matchSettings.getControllersClasses()){
				Student student = entry.getAnnotation(Student.class);
				bld.append(String.format("%16d, %16s, %64s, %6d,", student.matrikelnummer(), student.name(), entry.getName(), simulator.getStatistics().getVictories(entry)));
			}
			bld.append("\n");
			myWriter.write(bld.toString());
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
