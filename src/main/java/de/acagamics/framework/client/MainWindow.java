package de.acagamics.framework.client;

import de.acagamics.framework.client.web.News;
import de.acagamics.framework.client.web.Version;
import de.acagamics.framework.gui.StateManager;
import de.acagamics.framework.gui.interfaces.GameState;
import de.acagamics.framework.resourcemanagment.ClientProperties;
import de.acagamics.framework.resourcemanagment.ResourceManager;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
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
		Version.loadVersion();
		News.loadNews();

		// Set window settings and show window
		stage.getIcons().add(ResourceManager.getInstance().loadImage("icon.png"));
		stage.setTitle(ResourceManager.getInstance().loadProperties(ClientProperties.class).getTitle());
		stage.setWidth(ResourceManager.getInstance().loadProperties(ClientProperties.class).getScreenWidth());
		stage.setHeight(ResourceManager.getInstance().loadProperties(ClientProperties.class).getScreenHeight());
		stage.setMinWidth(ResourceManager.getInstance().loadProperties(ClientProperties.class).getScreenWidth());
		stage.setMinHeight(ResourceManager.getInstance().loadProperties(ClientProperties.class).getScreenHeight());

		// Create Canvas and Layout(Pane) for window
		Pane pane = new Pane();
		Canvas canvas = new Canvas(
				ResourceManager.getInstance().loadProperties(ClientProperties.class).getScreenWidth(),
				ResourceManager.getInstance().loadProperties(ClientProperties.class).getScreenHeight());
		pane.getChildren().add(canvas);
		pane.autosize();
		pane.setPrefSize(ResourceManager.getInstance().loadProperties(ClientProperties.class).getScreenWidth(),
				ResourceManager.getInstance().loadProperties(ClientProperties.class).getScreenHeight());

		// Add canvas to new scene and set scene as window content
		Scene scene = new Scene(pane, pane.getWidth(), pane.getHeight(), false, SceneAntialiasing.BALANCED);
		stage.setScene(scene);

		// Create StateManager and set MainMenu as start state
		StateManager manager = new StateManager(stage, canvas.getGraphicsContext2D());

		String main_menue = ResourceManager.getInstance().loadProperties(ClientProperties.class).getMainState();

		Class<?> controllerClass = Class.forName(main_menue);
		GameState mainState = (GameState) controllerClass
				.getDeclaredConstructor(StateManager.class, GraphicsContext.class)
				.newInstance(manager, canvas.getGraphicsContext2D());

		manager.push((GameState) mainState);

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