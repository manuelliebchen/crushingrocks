package Client.GUI.States;

import Client.GUI.Elements.Button;
import Client.GUI.States.Interfaces.GameState;
import Client.GUI.States.Interfaces.IDraw;
import Client.GUI.States.Interfaces.IUpdate;
import Client.Web.News;
import Client.Web.Version;
import Constants.ClientConstants;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * @author Max Klockmann (max@acagamics.de)
 * @author Gerd Schmidt (gerd.schmidt@acagamics.de)
 *
 */
public class MainMenu extends GameState implements IDraw, IUpdate {
	
	Button startGame;
	Button showCredits;
	Button sample;
	Button endGame;
	
	/**
	 * Creating new MainMenu.
	 * @param manager The StateManager of the current Window
	 * @param pane The Gui Layout pane of the current Window. Used for Buttons
	 */
	public MainMenu(StateManager manager) {
		super(manager);
	}


	@Override
	public void draw(GraphicsContext graphics, float elapsedTime) {		
		// draw background color
		graphics.setFill(Color.RED);
		graphics.fillRect(0, 0, ClientConstants.INITIAL_SCREEN_WIDTH, ClientConstants.INITIAL_SCREEN_HEIGHT);
		
		// draw oval in the middle
		graphics.setFill(Color.CORNFLOWERBLUE);
		graphics.fillOval((ClientConstants.INITIAL_SCREEN_WIDTH - 50) / 2, (ClientConstants.INITIAL_SCREEN_HEIGHT - 50) / 2, 50, 50);
		
		// write version
		if (Version.isChecked()) {
			String versionText = "version: " + ClientConstants.VERSION;
			if (Version.isUpToDate()) {
				versionText += " - up to date!";
				graphics.setFill(Color.WHITE);
			} else {
				versionText += " - out of date!";
				graphics.setFill(Color.BLUE);
			}
			graphics.fillText(versionText, 10, 10);
		}
		
		// write news
		if (News.hasNews()) {
			String versionText = "news: " + News.getNews();
			graphics.fillText(versionText, 10, 40);
		}
		
		//draw buttons
		startGame.draw(graphics);
		showCredits.draw(graphics);
		sample.draw(graphics);
		endGame.draw(graphics);
	}
	
	@Override
	public void entered() {
		super.entered();
		
		
		//generate testbuttons
		startGame = new Button(new Point2D(100,100),new Point2D(150,50),"Start Game");
		showCredits = new Button(new Point2D(100,200),new Point2D(150,50),"Show Credits");
		sample = new Button(new Point2D(100,300),new Point2D(150,50),"sample");
		sample.setEnabled(false);
		endGame = new Button(new Point2D(100,400),new Point2D(150,50),"Exit Game");
		
	}
	
	//Optional
	@Override
    public void leaving() {
    	super.leaving();
    }
	//Optional
	@Override
    public void obscuring() {
    	super.obscuring();
    }
	//Optional
	@Override
    public void revealed() {
    	super.revealed();
    }


	@Override
	public void update(float elapsedTime) {
//		if (!isTop) {
//			return;
//		}
		
		//Tests if buttons are pressed
		if(startGame.isPressed()){
			manager.push(new InGame(manager));
		}
		else if(showCredits.isPressed()){
			manager.push(new Credits(manager));
		}
		else if(sample.isPressed()){
		// Do some crazy stuff if the button is enabled.
		}
		else if(endGame.isPressed()){
			System.exit(0);
		}
	}
	
}
