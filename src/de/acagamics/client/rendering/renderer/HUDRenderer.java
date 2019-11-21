package de.acagamics.client.rendering.renderer;

import java.util.ArrayList;
import java.util.List;

import de.acagamics.client.gui.elements.DynamicTextBox;
import de.acagamics.client.gui.states.interfaces.IDrawable;
import de.acagamics.constants.DesignConstants.Alignment;
import de.acagamics.game.logic.Game;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

public class HUDRenderer implements IDrawable {

	List<IDrawable> drawables;

	public HUDRenderer(Game game) {
		drawables = new ArrayList<>(3);
		drawables.add(new DynamicTextBox(new Point2D(0, 50), () -> String.valueOf(game.getFramesLeft()))
				.setVerticalAlignment(Alignment.CENTER).setHorizontalAlignment(Alignment.TOP));
		drawables.add(new DynamicTextBox(new Point2D(100, 50), () -> String.valueOf(game.getPlayer(0).getCreditPoints()))
				.setTextColor(game.getPlayer(0).getColor()).setVerticalAlignment(Alignment.LEFT)
				.setHorizontalAlignment(Alignment.TOP).setTextAlignment(Alignment.LEFT));
		drawables.add(new DynamicTextBox(new Point2D(-100, 50), () -> String.valueOf(game.getPlayer(1).getCreditPoints()))
				.setTextColor(game.getPlayer(1).getColor()).setVerticalAlignment(Alignment.RIGHT)
				.setHorizontalAlignment(Alignment.TOP).setTextAlignment(Alignment.RIGHT));
	}

	@Override
	public void draw(GraphicsContext graphics) {
		for (IDrawable drawable : drawables) {
			drawable.draw(graphics);
		}
	}

}
