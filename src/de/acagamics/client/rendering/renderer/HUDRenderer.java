package de.acagamics.client.rendering.renderer;

import java.util.ArrayList;
import java.util.List;

import de.acagamics.client.gui.elements.ALIGNMENT;
import de.acagamics.client.gui.elements.DynamicTextBox;
import de.acagamics.client.gui.interfaces.IDrawable;
import de.acagamics.game.logic.Game;
import de.acagamics.game.types.Vec2f;
import javafx.scene.canvas.GraphicsContext;

public class HUDRenderer implements IDrawable {

	List<IDrawable> drawables;

	public HUDRenderer(Game game) {
		drawables = new ArrayList<>(3);
		drawables.add(new DynamicTextBox(new Vec2f(0, 50), () -> String.valueOf(game.getFramesLeft()))
				.setVerticalAlignment(ALIGNMENT.CENTER).setHorizontalAlignment(ALIGNMENT.TOP));
		drawables.add(new DynamicTextBox(new Vec2f(100, 50), () -> String.valueOf(game.getPlayer(0).getCreditPoints()))
				.setTextAlignment(ALIGNMENT.LEFT).setTextColor(game.getPlayer(0).getColor())
				.setVerticalAlignment(ALIGNMENT.LEFT).setHorizontalAlignment(ALIGNMENT.TOP));
		drawables.add(new DynamicTextBox(new Vec2f(-100, 50), () -> String.valueOf(game.getPlayer(1).getCreditPoints()))
				.setTextAlignment(ALIGNMENT.RIGHT).setTextColor(game.getPlayer(1).getColor())
				.setVerticalAlignment(ALIGNMENT.RIGHT).setHorizontalAlignment(ALIGNMENT.TOP));
	}

	@Override
	public void draw(GraphicsContext graphics) {
		for (IDrawable drawable : drawables) {
			drawable.draw(graphics);
		}
	}

}
