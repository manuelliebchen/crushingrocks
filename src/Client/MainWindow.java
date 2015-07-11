package Client;



import Client.GUI.States.MainMenu;
import Client.GUI.States.StateManager;
import Client.GUI.States.Interfaces.GameState;
import Game.GameConstants;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * @author Max Klockmann (max@acagamics.de)
 *
 */
public class MainWindow extends Application {
	
	private StateManager manager;
	private Stage stage;

	/**
	 * Setting up new Window and load default GameState
	 */
	@Override
	public void start(Stage stage) throws Exception {	
		stage = new Stage();
		stage.setResizable(false);
		stage.getIcons().add(new Image(ClientConstants.SCREEN_ICON));
		stage.setTitle(ClientConstants.SCREEN_TITLE);
		
		this.manager = new StateManager();
		
		this.manager.push(MainMenu.create(this.manager));
		
		stage.setScene(manager.peek());
		stage.show();
		
		GameApp.get().setWindow(this);
		
		this.stage = stage;
	}
	
	public void update(float elapsedTime) {
		this.manager.update(elapsedTime);
	}
	
	public void draw(float elapsedTime) {
		this.manager.draw(elapsedTime);
	}
	
	/**
	 * Create new Window
	 * @param args Arguments passed to GameApp
	 */
	public static void createNewWindow() {
		MainWindow.launch(new String[0]);
	}
	
}