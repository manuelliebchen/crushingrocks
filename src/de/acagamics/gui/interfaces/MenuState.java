package de.acagamics.gui.interfaces;

import java.util.ArrayList;
import java.util.List;

import de.acagamics.gui.StateManager;
import de.acagamics.gui.rendering.assetmanagment.AssetManager;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.InputEvent;

public abstract class MenuState extends GameState {

	protected List<IClickable> clickable;
	protected List<IDrawable> drawables;

	public MenuState(StateManager manager, GraphicsContext context) {
		super(manager, context);
		drawables = new ArrayList<>();
		clickable = new ArrayList<>();
	}

	@Override
	public void handle(InputEvent event) {
		for (IClickable button : clickable) {
			button.handle(event);
		}
	}

	@Override
	public void frame() {
		redraw();
	}
	
	@Override
	public void redraw() {
		AssetManager.getInstance().getBackground().draw(context);

		for (IDrawable drawable : drawables) {
			drawable.draw(context);
		}
		for (IClickable clickable : clickable) {
			clickable.draw(context);
		}
	}

}
