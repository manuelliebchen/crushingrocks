package Client.GUI.States;

import Client.GUI.Elements.Button;
import Client.GUI.Elements.Button.ButtonAlignment;
import Client.GUI.States.Interfaces.GameState;
import Client.GUI.States.Interfaces.IDraw;
import Client.GUI.States.Interfaces.IUpdate;
import Constants.ClientConstants;
import Game.Logic.GameStatistic;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class GameStatisticScreen extends GameState implements IDraw, IUpdate {

	Button backbutton;
	
	public GameStatisticScreen(StateManager manager, GameStatistic statistic) {
		super(manager);
		backbutton = new Button(new Point2D((double)ClientConstants.INITIAL_SCREEN_WIDTH-200,(double)ClientConstants.INITIAL_SCREEN_HEIGHT-100),new Point2D(150,50),"Back", ButtonAlignment.RIGHT, ButtonAlignment.DOWN);
	}

	@Override
	public void update(float elapsedTime) {
		if(backbutton.isPressed()) {
			manager.pop();
		}
	}

	@Override
	public void draw(GraphicsContext graphics, float elapsedTime) {
		// draw text
		graphics.setFill(Color.WHITE);
		graphics.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.BOLD, 36));
		graphics.fillText("GameStatistics", 60, 30);
		
		backbutton.draw(graphics);
	}

}
