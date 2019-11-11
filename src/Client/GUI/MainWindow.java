package Client.GUI;

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
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
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

		// Create Canvas and Layout(Pane) for window
		Pane pane = new Pane();
		Canvas canvas = new Canvas(ClientConstants.INITIAL_SCREEN_WIDTH, ClientConstants.INITIAL_SCREEN_HEIGHT);
		pane.getChildren().add(canvas);
		pane.autosize();
		pane.setPrefSize(ClientConstants.INITIAL_SCREEN_WIDTH, ClientConstants.INITIAL_SCREEN_HEIGHT);

		// Add canvas to new scene and set scene as window content
		Scene scene = new Scene(pane, pane.getWidth(), pane.getHeight(), false, SceneAntialiasing.BALANCED);
		stage.setScene(scene);

		Timeline timeline = new Timeline();

		// Create StateManager and set MainMenu as start state
		StateManager manager = new StateManager(stage);
		manager.push(new MainMenu(manager));

		scene.addEventHandler(MouseEvent.ANY, manager);
		scene.addEventHandler(KeyEvent.ANY, manager);
		
		// Create one frame
		KeyFrame frame = new KeyFrame(Duration.millis(ClientConstants.MINIMUM_TIME_PER_FRAME_MS),
				(event) -> {
					// Get elapsed time
					Duration time = ((KeyFrame) event.getSource()).getTime();

					// Update InputManager

//					InputManager.get().updateTables();

					GraphicsContext context = canvas.getGraphicsContext2D();
					// Clear screen
					context.clearRect(0, 0, ClientConstants.INITIAL_SCREEN_WIDTH,
							ClientConstants.INITIAL_SCREEN_HEIGHT);

					// Update and draw states
					manager.update((float) time.toSeconds());
					manager.draw(context);
				}
		);

		ChangeListener<Number> stageSizeListener = (observable, oldValue, newValue) -> {
			canvas.setWidth(stage.getWidth());
			canvas.setHeight(stage.getHeight());
		};
		stage.widthProperty().addListener(stageSizeListener);
		stage.heightProperty().addListener(stageSizeListener);
		
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.getKeyFrames().add(frame);
		timeline.play();
		stage.show();
	}
}