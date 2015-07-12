package Client.GUI.States;

import Client.ClientConstants;
import Client.GUI.Elements.Button;
import Client.GUI.States.Interfaces.GameState;
import Client.GUI.States.Interfaces.IDraw;
import Client.GUI.States.Interfaces.IUpdate;
import Client.Rendering.Drawing.ImageManager;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * @author Max Klockmann (max@acagamics.de)
 *
 */
public class Credits extends GameState implements IDraw, IUpdate {
	
	Button backbutton;
	
	/**
	 * Creating new Credits State.
	 * @param manager The StateManager of the current Window
	 */
	public Credits(StateManager manager) {
		super(manager);
	}

	@Override
	public void draw(GraphicsContext graphics, float elapsedTime) {
		if (!isTop)
			return;
		
		// draw background image
		graphics.drawImage(ImageManager.getInstance().loadImage("backgrounds/credits.png"), 0, 0, ClientConstants.SCREEN_WIDTH, ClientConstants.SCREEN_HEIGHT);
		
		// draw text
		graphics.setFill(Color.WHITE);
		graphics.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.BOLD, 36));
		graphics.fillText("Credits", 60, 30);
		
		backbutton.draw(graphics);
	}

	@Override
	public void update(float elapsedTime) {
		// TODO Auto-generated method stub
		
		if(backbutton.isPressed())
			manager.pop();
		
	}
	@Override
	public void entered() {
		super.entered();
		
		
		//generate testbuttons
		backbutton = new Button(new Point2D((double)ClientConstants.SCREEN_WIDTH-200,(double)ClientConstants.SCREEN_HEIGHT-100),new Point2D(150,50),"Back");
		
	}
}
