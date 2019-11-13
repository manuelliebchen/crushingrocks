package Client.GUI.States.Interfaces;

import java.util.ArrayList;
import java.util.List;

import Client.GUI.Elements.Button;
import Client.GUI.States.StateManager;
import Constants.DesignConstants;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.InputEvent;

public abstract class MenuState extends GameState {

	protected List<Button> buttons;
	protected List<IDrawable> drawables;
	
	public MenuState(StateManager manager, GraphicsContext context) {
		super(manager, context);
		drawables = new ArrayList<>();
		buttons = new ArrayList<>();
	}

	@Override
	public void handle(InputEvent event) {
		for(Button button : buttons) {
			button.handle(event);
		}
	}
	
	@Override
	public void frame() {
		Canvas canvas = context.getCanvas();

		context.setFill(DesignConstants.BACKGROUND_COLOR);
		context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

		for (IDrawable drawable : drawables) {
			drawable.draw(context);
		}
	}

}
