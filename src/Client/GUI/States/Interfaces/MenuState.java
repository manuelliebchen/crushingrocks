package Client.GUI.States.Interfaces;

import java.util.ArrayList;
import java.util.List;

import Client.GUI.Elements.Button;
import Client.GUI.States.StateManager;
import javafx.scene.input.InputEvent;

public class MenuState extends GameState {

	protected List<Button> buttons;
	protected List<IDrawable> drawables;
	
	public MenuState(StateManager manager) {
		super(manager);
		drawables = new ArrayList<>();
		buttons = new ArrayList<>();
	}

	@Override
	public void handle(InputEvent event) {
		for(Button button : buttons) {
			button.handle(event);
		}
	}

}
