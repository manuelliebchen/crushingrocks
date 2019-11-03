package Client.GUI;

import Client.InputManager;
import Client.GUI.States.MainMenu;
import Client.GUI.States.StateManager;
import Client.Rendering.Drawing.ImageManager;
import Client.Web.News;
import Client.Web.Version;
import Constants.ClientConstants;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * @author Max Klockmann (max@acagamics.de)
 *
 */
public class MainWindow extends Application {

	/**
	 * Setting up new Window and load default GameState
	 */
	@Override
	public void start(Stage stage) throws Exception {
		// Check version and news
		Version.loadVersion();
		News.loadNews();

		// Set window settings and show window
		stage.getIcons().add(ImageManager.getInstance().loadImage("icon.png"));
		stage.setTitle(ClientConstants.SCREEN_TITLE);
		stage.setScene(
				new Scene(new GridPane(), ClientConstants.INITIAL_SCREEN_WIDTH, ClientConstants.INITIAL_SCREEN_HEIGHT));
		stage.show();

		// Create Canvas and Layout(Pane) for window
		Pane pane = new Pane();
		Canvas canvas = new Canvas(ClientConstants.INITIAL_SCREEN_WIDTH, ClientConstants.INITIAL_SCREEN_HEIGHT);
		pane.getChildren().add(canvas);
		pane.autosize();
		pane.setPrefSize(ClientConstants.INITIAL_SCREEN_WIDTH, ClientConstants.INITIAL_SCREEN_HEIGHT);

		// Add canvas to new scene and set scene as window content
		Scene scene = new Scene(pane, pane.getWidth(), pane.getHeight());
		stage.setScene(scene);

		InputManager.init(scene);

		// Create StateManager and set MainMenu as start state
		StateManager manager = new StateManager();
		manager.push(new MainMenu(manager));

		// Setting up gameLoop

		// Create one frame
		KeyFrame frame = new KeyFrame(Duration.millis(ClientConstants.MINIMUM_TIME_PER_FRAME_MS),
				new EventHandler<ActionEvent>() {

					/**
					 * This Method is called ones each frame
					 */
					@Override
					public void handle(ActionEvent event) {
						// Get elapsed time
						Duration time = ((KeyFrame) event.getSource()).getTime();

						// Update InputManager

						InputManager.get().updateTables();

						// Clear screen
						canvas.getGraphicsContext2D().clearRect(0, 0, ClientConstants.INITIAL_SCREEN_WIDTH,
								ClientConstants.INITIAL_SCREEN_HEIGHT);

						// Update and draw states
						manager.update((float) time.toSeconds());
						manager.draw(canvas.getGraphicsContext2D(), (float) time.toSeconds());
					}
				});

		ChangeListener<Number> stageSizeListener = (observable, oldValue, newValue) -> {
			canvas.setWidth(stage.getWidth());
			canvas.setHeight(stage.getHeight());
		};
		stage.widthProperty().addListener(stageSizeListener);
		stage.heightProperty().addListener(stageSizeListener);

		// Create frame loop
		Timeline line = new Timeline(frame);
		line.setCycleCount(Animation.INDEFINITE);
		line.play();
	}
}