package Client.GUI;

import Client.GUI.States.MainMenu;
import Client.GUI.States.StateManager;
import Client.Rendering.Drawing.ImageManager;
import Client.Web.News;
import Client.Web.Version;
import Constants.ClientConstants;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

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

		// Create StateManager and set MainMenu as start state
		StateManager manager = new StateManager(stage, canvas.getGraphicsContext2D());
		manager.push(new MainMenu(manager, canvas.getGraphicsContext2D()));

		EventHandler<Event> eventmanager = new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				// Get elapsed time
				if (event instanceof InputEvent) {
					if (event instanceof KeyEvent) {
						KeyEvent keyEvent = ((KeyEvent) event);
						if (keyEvent.getCode() == KeyCode.Q && keyEvent.isControlDown()) {
							manager.popAll();
						}
					}
					manager.handle((InputEvent) event);
				}
				// Update and draw states
				manager.frame();

			}
		};

		scene.addEventHandler(MouseEvent.ANY, eventmanager);
		scene.addEventHandler(KeyEvent.ANY, eventmanager);

		ChangeListener<Number> stageSizeListener = (observable, oldValue, newValue) -> {
			canvas.setWidth(stage.getWidth());
			canvas.setHeight(stage.getHeight());
			manager.redraw();
		};
		stage.widthProperty().addListener(stageSizeListener);
		stage.heightProperty().addListener(stageSizeListener);
		stage.show();

		manager.frame();
	}
}