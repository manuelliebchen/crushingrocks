package de.acagamics.crushingrocks.states;

import de.acagamics.crushingrocks.logic.Player;
import de.acagamics.crushingrocks.rendering.Background;
import de.acagamics.crushingrocks.types.MatchSettings;
import de.acagamics.framework.geometry.Vec2f;
import de.acagamics.framework.interfaces.Student;
import de.acagamics.framework.resources.DesignProperties;
import de.acagamics.framework.resources.ResourceManager;
import de.acagamics.framework.simulation.GameStatistic;
import de.acagamics.framework.simulation.SimulationSettings;
import de.acagamics.framework.simulation.SimulationStatistic;
import de.acagamics.framework.simulation.Simulator;
import de.acagamics.framework.ui.StateManager;
import de.acagamics.framework.ui.elements.Button;
import de.acagamics.framework.ui.elements.DynamicTextBox;
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
import java.util.List;
import java.util.Random;

public final class SimulationState extends SelfUpdatingState {
	private static final Logger LOG = LogManager.getLogger(SimulationState.class.getName());

	private Simulator simulator;
	private MatchSettings matchSettings;

	public SimulationState(StateManager manager, GraphicsContext context, SimulationSettings settings, MatchSettings matchSettings) {
		super(manager, context);
		this.matchSettings = matchSettings;

		drawables.add(new Background(100, 0.2f, new de.acagamics.crushingrocks.logic.Map(new Random(), new ArrayList<Player>())));

		this.simulator = new Simulator(settings, matchSettings);

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
		SimulationStatistic scroes = simulator.getStatistics();
		for(int i = 0; i < controllers.size(); ++i) {
			float y = 350.0f + i * 50;
			Class<?> controller = controllers.get(i);
			drawables.add(new TextBox(new Vec2f(-300, y), String.valueOf(i+1) + ".").setTextAlignment(ALIGNMENT.LEFT)
					.setVerticalAlignment(ALIGNMENT.CENTER).setHorizontalAlignment(ALIGNMENT.UPPER));
			drawables.add(new TextBox(new Vec2f(-250, y), controller.getAnnotation(Student.class).author()).setTextAlignment(ALIGNMENT.LEFT)
					.setVerticalAlignment(ALIGNMENT.CENTER).setHorizontalAlignment(ALIGNMENT.UPPER));
			drawables.add(new TextBox(new Vec2f(0, y), GameStatistic.getName(controller)).setTextAlignment(ALIGNMENT.LEFT)
					.setVerticalAlignment(ALIGNMENT.CENTER).setHorizontalAlignment(ALIGNMENT.UPPER));
			drawables.add(new DynamicTextBox(new Vec2f(400, y),() ->
					String.format("%7d", scroes.getVictories(controller))).setVerticalAlignment(ALIGNMENT.CENTER).setHorizontalAlignment(ALIGNMENT.UPPER));

		}
	}

	public void saveCSV(){
		try(FileWriter myWriter = new FileWriter("simulation_data.csv", true)) {
			myWriter.write(simulator.getCSV(matchSettings.getControllersClasses()));
		} catch (IOException e) {
			LOG.error(e);
		}
	}

	@Override
	public void entered() {
		super.entered();
		Thread runner = new Thread(simulator);
		runner.start();
	}
}
