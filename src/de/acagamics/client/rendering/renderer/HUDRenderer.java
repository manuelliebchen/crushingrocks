package de.acagamics.client.rendering.renderer;

import java.util.ArrayList;
import java.util.List;

import de.acagamics.client.gui.elements.DynamicTextBox;
import de.acagamics.client.gui.interfaces.IDrawable;
import de.acagamics.constants.DesignConstants.ALINGMENT;
import de.acagamics.game.logic.Game;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

public class HUDRenderer implements IDrawable {

	List<IDrawable> drawables;

	public HUDRenderer(Game game) {
		drawables = new ArrayList<>(3);
		drawables.add(new DynamicTextBox(new Point2D(0, 50), () -> String.valueOf(game.getFramesLeft()))
				.setVerticalAlignment(ALINGMENT.CENTER).setHorizontalAlignment(ALINGMENT.TOP));
		drawables.add(new DynamicTextBox(new Point2D(100, 50), () -> String.valueOf(game.getPlayer(0).getCreditPoints()))
				.setTextColor(game.getPlayer(0).getColor()).setVerticalAlignment(ALINGMENT.LEFT)
				.setHorizontalAlignment(ALINGMENT.TOP).setTextAlignment(ALINGMENT.LEFT));
		drawables.add(new DynamicTextBox(new Point2D(-100, 50), () -> String.valueOf(game.getPlayer(1).getCreditPoints()))
				.setTextColor(game.getPlayer(1).getColor()).setVerticalAlignment(ALINGMENT.RIGHT)
				.setHorizontalAlignment(ALINGMENT.TOP).setTextAlignment(ALINGMENT.RIGHT));
	}

	@Override
	public void draw(GraphicsContext graphics) {
		for (IDrawable drawable : drawables) {
			drawable.draw(graphics);
		}
	}

}
