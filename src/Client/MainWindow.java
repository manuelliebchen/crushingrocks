package Client;



import Client.GUI.States.MainMenu;
import Client.GUI.States.StateManager;
import Client.GUI.States.Interfaces.GameState;
import Game.GameConstants;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TimelineBuilder;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * @author Max Klockmann (max@acagamics.de)
 *
 */
public class MainWindow extends Application {
	
	private StateManager manager;
	private Stage rootStage;

	/**
	 * Setting up new Window and load default GameState
	 */
	@Override
	public void start(Stage stage) throws Exception {	
		stage.setResizable(false);
		stage.getIcons().add(new Image(ClientConstants.SCREEN_ICON));
		stage.setTitle(ClientConstants.SCREEN_TITLE);
		
		this.manager = new StateManager();
		
		this.manager.push(MainMenu.create(this.manager));
		
		stage.setScene(new Scene(new GridPane(), 100, 100));
		stage.show();
		
		// GameApp.get().setWindow(this);
		
		this.rootStage = stage;
		
		KeyFrame frame = new KeyFrame(Duration.millis(GameConstants.MINIMUM_TIME_PER_FRAME_MS), new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Duration time = ((KeyFrame)event.getSource()).getTime();
				
				manager.update((float)time.toMillis());
				manager.draw((float)time.toMillis());
				
				rootStage.setScene(manager.peek());
			}
		});
		
		Timeline line = new Timeline(frame);
		line.setCycleCount(Animation.INDEFINITE);
		line.play();
	}
}