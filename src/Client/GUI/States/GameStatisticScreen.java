package Client.GUI.States;

import java.util.ArrayList;
import java.util.List;

import Client.GUI.Elements.Button;
import Client.GUI.Elements.TextBox;
import Client.GUI.States.Interfaces.GameState;
import Client.GUI.States.Interfaces.IDrawable;
import Client.GUI.States.Interfaces.IUpdateable;
import Constants.DesignConstants;
import Constants.DesignConstants.Alignment;
import Game.Logic.GameStatistic;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class GameStatisticScreen extends GameState implements IDrawable, IUpdateable {

	Button backbutton;

	List<IDrawable> drawables;
	
	public GameStatisticScreen(StateManager manager, GameStatistic statistic) {
		super(manager);
		drawables = new ArrayList<>();
		backbutton = new Button(new Point2D(200, 125), new Point2D(150, 50), "Back").setVerticalAlignment(Alignment.RIGHT).setHorizontalAlignment(Alignment.BOTTOM);
		drawables.add(new TextBox(new Point2D(100, 100), "GameStatistics").setVerticalAlignment(Alignment.LEFT).setHorizontalAlignment(Alignment.TOP));
		drawables.add(new TextBox(new Point2D(0, 100), statistic.toString()).setVerticalAlignment(Alignment.CENTER).setHorizontalAlignment(Alignment.TOP));
		drawables.add(backbutton);
	}

	@Override
	public void update(float elapsedTime) {
		if (backbutton.isPressed()) {
			manager.pop();
		}
	}

	@Override
	public void draw(GraphicsContext graphics) {

		Canvas canvas = graphics.getCanvas();

		graphics.setFill(DesignConstants.BACKGROUND_COLOR);
		graphics.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

		for(IDrawable drawable : drawables) {
			drawable.draw(graphics);
		}
	}

}
