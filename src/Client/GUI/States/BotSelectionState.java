package Client.GUI.States;

import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.List;

import Client.GUI.Elements.Button;
import Client.GUI.Elements.DynamicTextBox;
import Client.GUI.Elements.TextBox;
import Client.GUI.States.Interfaces.IDrawable;
import Client.GUI.States.Interfaces.MenuState;
import Constants.DesignConstants.Alignment;
import Game.Controller.BotClassLoader;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

public class BotSelectionState extends MenuState {

	private int[] selectedBot;
	private BotClassLoader playerLoader;
	private List<Class<?>> bots;

	private int speedMultiplier;

	public BotSelectionState(StateManager manager, GraphicsContext context) {
		super(manager, context);

		speedMultiplier = 1;

		playerLoader = new BotClassLoader();
		playerLoader.loadControllerFromDirectory(FileSystems.getDefault().getPath("bin/").toAbsolutePath().toString());
		bots = playerLoader.getLoadedExternControllerClassNames();

		drawables.add(new TextBox(new Point2D(100, 100), "Bot Selection").setVerticalAlignment(Alignment.LEFT)
				.setHorizontalAlignment(Alignment.TOP));

		Button startbutton = new Button(new Point2D(200, 125), new Point2D(150, 50), "Start",
				() -> manager.switchCurrentState(new InGame(manager, context, generateSettings())))
						.setVerticalAlignment(Alignment.RIGHT).setHorizontalAlignment(Alignment.BOTTOM)
						.setKeyCode(KeyCode.ENTER);
		buttons.add(startbutton);

		if (!bots.isEmpty()) {
			selectedBot = new int[2];

			buttons.add(new Button(new Point2D(-300, 150), new Point2D(50, 50), "<", () -> updateSelection(0, -1))
					.setVerticalAlignment(Alignment.CENTER));
			drawables.add(new DynamicTextBox(new Point2D(-100, 200), () -> bots.get(selectedBot[0]).getSimpleName())
					.setVerticalAlignment(Alignment.CENTER).setHorizontalAlignment(Alignment.TOP));
			buttons.add(new Button(new Point2D(300, 150), new Point2D(50, 50), ">", () -> updateSelection(0, 1))
					.setVerticalAlignment(Alignment.CENTER));

			buttons.add(new Button(new Point2D(-300, 250), new Point2D(50, 50), "<", () -> updateSelection(1, -1))
					.setVerticalAlignment(Alignment.CENTER));
			drawables.add(new DynamicTextBox(new Point2D(-100, 300), () -> bots.get(selectedBot[1]).getSimpleName())
					.setVerticalAlignment(Alignment.CENTER).setHorizontalAlignment(Alignment.TOP));
			buttons.add(new Button(new Point2D(300, 250), new Point2D(50, 50), ">", () -> updateSelection(1, 1))
					.setVerticalAlignment(Alignment.CENTER));
		} else {
			startbutton.setEnabled(false);
		}

		drawables.add(new TextBox(new Point2D(100, -200), "Speed Multiplier")
				.setHorizontalAlignment(Alignment.BOTTOM));
		buttons.add(new Button(new Point2D(100, 125), new Point2D(50, 50), "<",
				() -> speedMultiplier += speedMultiplier > 1 ? -1 : 0).setHorizontalAlignment(Alignment.BOTTOM));
		drawables.add(new DynamicTextBox(new Point2D(200, -125), () -> String.valueOf(speedMultiplier) + "x")
				.setHorizontalAlignment(Alignment.BOTTOM));
		buttons.add(new Button(new Point2D(300, 125), new Point2D(50, 50), ">", () -> speedMultiplier += 1)
				.setHorizontalAlignment(Alignment.BOTTOM));
		
		buttons.add(new Button(new Point2D(400, 125), new Point2D(150, 50), "Back", () -> manager.pop())
				.setVerticalAlignment(Alignment.RIGHT).setHorizontalAlignment(Alignment.BOTTOM)
				.setKeyCode(KeyCode.ESCAPE));
		drawables.addAll(buttons);

	}

	private void updateSelection(int index, int delta) {
		selectedBot[index] += delta;
		while (selectedBot[index] < 0) {
			selectedBot[index] += bots.size();
		}
		selectedBot[index] %= bots.size();
	}

	private InGameSettings generateSettings() {
		List<String> names = new ArrayList<>(2);
		for (int i = 0; i < 2; ++i) {
			names.add(bots.get(selectedBot[i]).getName());
		}
		return new InGameSettings(playerLoader, names, speedMultiplier);
	}

	@Override
	public void redraw() {

		for (IDrawable drawable : drawables) {
			drawable.draw(context);
		}
	}

}
