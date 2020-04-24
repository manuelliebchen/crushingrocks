package de.acagamics.crushingrocks.states;

import java.util.ArrayList;
import java.util.List;

import de.acagamics.crushingrocks.GAMEMODE;
import io.github.classgraph.*;

import de.acagamics.crushingrocks.controller.IPlayerController;
import de.acagamics.crushingrocks.controller.builtin.EvilSanta;
import de.acagamics.framework.resources.DesignProperties;
import de.acagamics.framework.resources.ResourceManager;
import de.acagamics.framework.types.MatchSettings;
import de.acagamics.framework.types.Student;
import de.acagamics.framework.ui.StateManager;
import de.acagamics.framework.ui.elements.Button;
import de.acagamics.framework.ui.elements.Selector;
import de.acagamics.framework.ui.elements.TextBox;
import de.acagamics.framework.ui.elements.Button.BUTTON_TYPE;
import de.acagamics.framework.ui.interfaces.ALIGNMENT;
import de.acagamics.framework.ui.interfaces.IDrawable;
import de.acagamics.framework.ui.interfaces.MenuState;
import de.acagamics.framework.types.Vec2f;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

public class SelectionState extends MenuState {

	private Selector[] botSelectors;
	private List<Class<?>> bots;

	private Selector modeSelector;

	private Selector speedSelectors;

	public SelectionState(StateManager manager, GraphicsContext context) {
		super(manager, context);
		try (ScanResult scanResult = new ClassGraph().enableAnnotationInfo().blacklistPackages("java", "javafx", "org.apache")
				.scan()) {
			bots = scanResult.getClassesImplementing(IPlayerController.class.getName()).filter( classInfo -> classInfo.hasAnnotation(Student.class.getName())).loadClasses();
		}

		drawables.add((IDrawable) new TextBox(new Vec2f(200, 50), "Bot Selection").setFont(ResourceManager.getInstance().loadProperties(DesignProperties.class).getSubtitleFont()).setVerticalAlignment(ALIGNMENT.LEFT)
				.setHorizontalAlignment(ALIGNMENT.UPPER));

		drawables.add((IDrawable) new TextBox(new Vec2f(-350, 300), "Game Mode:").setVerticalAlignment(ALIGNMENT.CENTER));
		modeSelector = new Selector(new Vec2f(0, 300), 200, 0, GAMEMODE.values().length - 1,
				i -> GAMEMODE.values()[i].toString()).setVerticalAlignment(ALIGNMENT.CENTER);
		clickable.add(modeSelector);

		Button startbutton = (Button) (new Button(new Vec2f(-175, -120), BUTTON_TYPE.NORMAL, "Start",
				() -> manager.push(new InGameState(manager, context, generateSettings(), speedSelectors.getValue()))).setKeyCode(KeyCode.ENTER)
						.setVerticalAlignment(ALIGNMENT.RIGHT).setHorizontalAlignment(ALIGNMENT.LOWER));
		clickable.add(startbutton);

		clickable.add((Button) (new Button(new Vec2f(-325, -120), BUTTON_TYPE.NORMAL, "Back", manager::pop)
				.setKeyCode(KeyCode.ESCAPE).setVerticalAlignment(ALIGNMENT.RIGHT)
				.setHorizontalAlignment(ALIGNMENT.LOWER)));


		drawables.add((IDrawable) new TextBox(new Vec2f(-350, 400), "Bots:").setVerticalAlignment(ALIGNMENT.CENTER));
		if (!bots.isEmpty()) {
			botSelectors = new Selector[2];
			for (int i = 0; i < botSelectors.length; ++i) {
				botSelectors[i] = new Selector(new Vec2f(0, 400.0f + i * 100), 200, 0, bots.size() - 1,
						i2 -> bots.get(i2).getSimpleName()).setVerticalAlignment(ALIGNMENT.CENTER);
				clickable.add(botSelectors[i]);
			}
		} else {
			startbutton.setEnabled(false);
		}

		drawables.add((IDrawable) new TextBox(new Vec2f(200, -200), "Speed Multiplier").setHorizontalAlignment(ALIGNMENT.LOWER));
		speedSelectors = new Selector(new Vec2f(200, -120), 100, 1, 16, i -> i + "x")
				.setHorizontalAlignment(ALIGNMENT.LOWER);
		clickable.add(speedSelectors);

	}

	private MatchSettings generateSettings() {
		List<Class<?>> names = new ArrayList<>(2);
		if (GAMEMODE.values()[modeSelector.getValue()] == GAMEMODE.NORMAL) {
			for (int i = 0; i < 2; ++i) {
				names.add(bots.get(botSelectors[i].getValue()));
			}
		} else if (GAMEMODE.values()[modeSelector.getValue()] == GAMEMODE.XMAS_CHALLENGE) {
			names.add(bots.get(botSelectors[0].getValue()));
			names.add(EvilSanta.class);
		}
		return new MatchSettings(GAMEMODE.values()[modeSelector.getValue()], names);
	}
}
