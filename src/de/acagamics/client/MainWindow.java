package de.acagamics.client;

import de.acagamics.constants.ClientConstants;
import de.acagamics.gui.StateManager;
import de.acagamics.gui.states.MainMenuState;
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
public final class MainWindow extends Application {

	/**
	 * Setting up new Window and load default GameState
	 */
	@Override
	public void start(Stage stage) throws Exception {
		// Check version and news
//		Version.loadVersion();
//		News.loadNews();

		// Set window settings and show window
//		stage.getIcons().add(ImageManager.getInstance().loadImage("icon.png"));
		stage.setTitle(ClientConstants.SCREEN_TITLE);
		stage.setWidth(ClientConstants.INITIAL_SCREEN_WIDTH);
		stage.setHeight(ClientConstants.INITIAL_SCREEN_HEIGHT);
		stage.setMinWidth(ClientConstants.INITIAL_SCREEN_WIDTH);
		stage.setMinHeight(ClientConstants.INITIAL_SCREEN_HEIGHT);

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
		manager.push(new MainMenuState(manager, canvas.getGraphicsContext2D()));

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