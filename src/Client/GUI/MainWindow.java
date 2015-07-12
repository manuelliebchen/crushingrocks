package Client.GUI;

import Client.ClientConstants;
import Client.InputManager;
import Client.GUI.States.MainMenu;
import Client.GUI.States.StateManager;
import Client.Rendering.ImageManager;
import Client.Web.News;
import Client.Web.Version;
import Game.GameConstants;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * @author Max Klockmann (max@acagamics.de)
 *
 */
public class MainWindow extends Application {	
	private StateManager manager;
	
	private Canvas canvas;
	private Pane pane;

	/**
	 * Setting up new Window and load default GameState
	 */
	@Override
	public void start(Stage stage) throws Exception {
		// Check version and news
		Version.loadVersion();
		News.loadNews();
		
		// Set window settings and show window
		stage.setResizable(false);
		stage.getIcons().add(ImageManager.getInstance().loadImage("icon.png"));
		stage.setTitle(ClientConstants.SCREEN_TITLE);
		stage.setScene(new Scene(new GridPane(), ClientConstants.SCREEN_WIDTH, ClientConstants.SCREEN_HEIGHT));
		stage.show();
		
		// Create Canvas and Layout(Pane) for window
		pane = new AnchorPane();
		canvas = new Canvas(ClientConstants.SCREEN_WIDTH, ClientConstants.SCREEN_HEIGHT);
		pane.getChildren().add(canvas);
		pane.autosize();
		
		// Add canvas to new scene and set scene as window content
		Scene scene = new Scene(pane, pane.getWidth(), pane.getHeight());
		stage.setScene(scene);
		
		// Create StateManager and set MainMenu as start state
		this.manager = new StateManager();		
		this.manager.push(new MainMenu(this.manager));
		
		// Setting up gameLoop
		
		// Create one frame
		KeyFrame frame = new KeyFrame(Duration.millis(GameConstants.MINIMUM_TIME_PER_FRAME_MS), new EventHandler<ActionEvent>() {

			/**
			 * This Method is called ones each frame
			 */
			@Override
			public void handle(ActionEvent event) {
				// Get elapsed time
				Duration time = ((KeyFrame)event.getSource()).getTime();
				
				// Update InputManager
				InputManager.get().updateTables();

				// Clear screen
				canvas.getGraphicsContext2D().clearRect(0, 0, ClientConstants.SCREEN_WIDTH, ClientConstants.SCREEN_HEIGHT);
				
				// Update and draw states
				manager.update((float)time.toMillis());
				manager.draw(canvas.getGraphicsContext2D(), (float)time.toMillis());
			}
		});
		
		// Create frame loop
		Timeline line = new Timeline(frame);
		line.setCycleCount(Animation.INDEFINITE);
		line.play();
	}
}