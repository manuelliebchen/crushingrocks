package Client.Rendering.Rendering;

import java.util.ArrayList;
import java.util.List;

import Client.GUI.Elements.DynamicTextBox;
import Client.GUI.States.Interfaces.IDrawable;
import Constants.DesignConstants.Alignment;
import Game.Logic.Game;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

public class HUDRenderer implements IDrawable {

	List<IDrawable> drawables;

	public HUDRenderer(Game game) {
		drawables = new ArrayList<>(3);
		drawables.add(new DynamicTextBox(new Point2D(0, 0), () -> String.valueOf(game.getFramesLeft()))
				.setVerticalAlignment(Alignment.CENTER).setHorizontalAlignment(Alignment.TOP));
		drawables.add(new DynamicTextBox(new Point2D(10, 0), () -> String.valueOf(game.getPlayer(0).getCreditPoints()))
				.setTextColor(game.getPlayer(0).getColor()).setVerticalAlignment(Alignment.LEFT)
				.setHorizontalAlignment(Alignment.TOP));
		drawables.add(new DynamicTextBox(new Point2D(10, 0), () -> String.valueOf(game.getPlayer(1).getCreditPoints()))
				.setTextColor(game.getPlayer(1).getColor()).setVerticalAlignment(Alignment.RIGHT)
				.setHorizontalAlignment(Alignment.TOP));
	}

	@Override
	public void draw(GraphicsContext graphics) {
		for (IDrawable drawable : drawables) {
			drawable.draw(graphics);
		}
	}

}
