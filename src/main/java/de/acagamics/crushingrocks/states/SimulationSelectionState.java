package de.acagamics.crushingrocks.states;

import de.acagamics.crushingrocks.GameMode;
import de.acagamics.crushingrocks.controller.IPlayerController;
import de.acagamics.crushingrocks.controller.builtin.EvilSanta;
import de.acagamics.framework.resources.DesignProperties;
import de.acagamics.framework.resources.ResourceManager;
import de.acagamics.framework.types.SimulationSettings;
import de.acagamics.framework.types.Student;
import de.acagamics.framework.types.Vec2f;
import de.acagamics.framework.ui.StateManager;
import de.acagamics.framework.ui.elements.Button;
import de.acagamics.framework.ui.elements.Button.BUTTON_TYPE;
import de.acagamics.framework.ui.elements.Selector;
import de.acagamics.framework.ui.elements.TextBox;
import de.acagamics.framework.ui.interfaces.ALIGNMENT;
import de.acagamics.framework.ui.interfaces.IDrawable;
import de.acagamics.framework.ui.interfaces.MenuState;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.List;

public class SimulationSelectionState extends MenuState {

	private Selector[] botSelectors;
	private List<Class<?>> bots;

	private Selector modeSelector;

	private Selector threadSelector;
	private Selector runsSelector;

	public SimulationSelectionState(StateManager manager, GraphicsContext context) {
		super(manager, context);
		try (ScanResult scanResult = new ClassGraph().enableAnnotationInfo().blacklistPackages("java", "javafx", "org.apache")
				.scan()) {
			bots = scanResult.getClassesImplementing(IPlayerController.class.getName()).filter( classInfo -> classInfo.hasAnnotation(Student.class.getName())).loadClasses();
		}

		drawables.add((IDrawable) new TextBox(new Vec2f(200, 50), "Bot Selection").setFont(ResourceManager.getInstance().loadProperties(DesignProperties.class).getSubtitleFont()).setVerticalAlignment(ALIGNMENT.LEFT)
				.setHorizontalAlignment(ALIGNMENT.UPPER));

		drawables.add((IDrawable) new TextBox(new Vec2f(-350, 300), "Game Mode:").setVerticalAlignment(ALIGNMENT.CENTER));
		modeSelector = new Selector(new Vec2f(0, 300), 200, 0, GameMode.values().length - 1,
				i -> GameMode.values()[i].toString()).setVerticalAlignment(ALIGNMENT.CENTER);
		clickable.add(modeSelector);

		Button startbutton = new Button(new Vec2f(-175, -120), BUTTON_TYPE.NORMAL, "Start",
				() -> manager.push(new SimulationState(manager, context, generateSettings()))).setKeyCode(KeyCode.ENTER)
				.setVerticalAlignment(ALIGNMENT.RIGHT).setHorizontalAlignment(ALIGNMENT.LOWER);
		clickable.add(startbutton);

		clickable.add(new Button(new Vec2f(-325, -120), BUTTON_TYPE.NORMAL, "Back", manager::pop)
				.setKeyCode(KeyCode.ESCAPE).setVerticalAlignment(ALIGNMENT.RIGHT)
				.setHorizontalAlignment(ALIGNMENT.LOWER));


		drawables.add((IDrawable) new TextBox(new Vec2f(-350, 400), "Bots:").setVerticalAlignment(ALIGNMENT.CENTER));
		if (!bots.isEmpty()) {
			botSelectors = new Selector[2];
			for (int i = 0; i < botSelectors.length; ++i) {
				botSelectors[i] = new Selector(new Vec2f(0, 400.0f + i * 80), 200, 0, bots.size() - 1,
						i2 -> bots.get(i2).getSimpleName()).setVerticalAlignment(ALIGNMENT.CENTER);
				clickable.add(botSelectors[i]);
			}
		}

		drawables.add((IDrawable) new TextBox(new Vec2f(200, -180), "Threads:").setHorizontalAlignment(ALIGNMENT.LOWER));
		threadSelector = new Selector(new Vec2f(200, -120), 100, 1, 16, i -> i + "x")
				.setHorizontalAlignment(ALIGNMENT.LOWER);
		clickable.add(threadSelector);

		drawables.add((IDrawable) new TextBox(new Vec2f(0, -180), "Runs:").setVerticalAlignment(ALIGNMENT.CENTER).setHorizontalAlignment(ALIGNMENT.LOWER));
		runsSelector = new Selector(new Vec2f(0, -120), 200, 1, 6, i -> String.valueOf((int) Math.pow(10, i)) + "x").setVerticalAlignment(ALIGNMENT.CENTER)
				.setHorizontalAlignment(ALIGNMENT.LOWER);
		clickable.add(runsSelector);

	}

	private SimulationSettings<GameMode> generateSettings() {
		List<Class<?>> names = new ArrayList<>(2);
		if (GameMode.values()[modeSelector.getValue()] == GameMode.NORMAL) {
			for (int i = 0; i < 2; ++i) {
				names.add(bots.get(botSelectors[i].getValue()));
			}
		} else if (GameMode.values()[modeSelector.getValue()] == GameMode.XMAS_CHALLENGE) {
			names.add(bots.get(botSelectors[0].getValue()));
			names.add(EvilSanta.class);
		}
		return new SimulationSettings<>(GameMode.values()[modeSelector.getValue()], names, threadSelector.getValue(), (int) Math.pow(10, runsSelector.getValue()));
	}
}
