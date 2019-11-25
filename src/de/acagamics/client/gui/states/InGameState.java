package de.acagamics.client.gui.states;

import de.acagamics.client.gui.StateManager;
import de.acagamics.client.gui.interfaces.GameState;
import de.acagamics.client.gui.interfaces.ISelfUpdating;
import de.acagamics.client.rendering.renderer.HUDRenderer;
import de.acagamics.client.rendering.renderer.MapOverlayRendering;
import de.acagamics.client.rendering.renderer.MapRendering;
import de.acagamics.constants.ClientConstants;
import de.acagamics.constants.DesignConstants;
import de.acagamics.constants.GameConstants;
import de.acagamics.data.GameStatistic;
import de.acagamics.data.InGameSettings;
import de.acagamics.game.logic.Game;
import javafx.animation.Animation;
import javafx.animation.Animation.Status;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.transform.Affine;
import javafx.util.Duration;

/**
 * @author Claudius Grimm (claudius@acagamics.de)
 * @author Manuel Liebchen
 */
public final class InGameState extends GameState implements ISelfUpdating {

	private Game game;
	private MapRendering mapRenderer;
	private MapOverlayRendering mapOverlayRenderer;
	private HUDRenderer hudRenderer;

	Timeline timeline;
	
	private InGameSettings settings;

	public InGameState(StateManager manager, GraphicsContext context, InGameSettings settings) {
		super(manager, context);
		this.settings = settings;

		game = new Game(settings.getControllers(), settings.getMode());

		mapRenderer = new MapRendering(game.getMap());
		mapOverlayRenderer = new MapOverlayRendering(game.getMap());
		hudRenderer = new HUDRenderer(game);

		timeline = new Timeline();
		KeyFrame frame = new KeyFrame(Duration.millis(ClientConstants.MINIMUM_TIME_PER_FRAME_MS/settings.getSpeedMultiplier()), (event) -> {
			frame();
		});

		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.getKeyFrames().add(frame);
	}

	@Override
	public void entered() {
		timeline.play();
	}

	@Override
	public void leaving() {
		timeline.stop();
	}

	/**
	 * Updates all needed display objects, e.g. player, map.
	 * 
	 * @param elapsedTime Time passed since last update in seconds.
	 */
	@Override
	public void update() {
		GameStatistic statistic = game.tick();
		if (statistic != null) {
			manager.switchCurrentState(new StatisticState(manager, context, statistic, settings));
		}
		mapRenderer.update();
	}

	/**
	 * Draws all needed display objects, e.g. player, map.
	 * 
	 * @param context     The context to draw on.
	 * @param elapsedTime Time passed since last draw in seconds.
	 */
	@Override
	public void redraw() {
		if(timeline.getStatus() != Status.RUNNING) {
			return;
		}
		Canvas canvas = context.getCanvas();
		context.setFill(DesignConstants.GAME_BACKGROUND_COLOR);
		context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

		context.save();

		Affine transformation = new Affine();
		transformation.appendTranslation(canvas.getWidth() / 2, canvas.getHeight() / 2);
		if (canvas.getHeight() > canvas.getWidth()) {
			transformation.appendScale(canvas.getWidth() / (GameConstants.MAP_RADIUS * 2.5),
					canvas.getWidth() / (GameConstants.MAP_RADIUS * 2.5));
		} else {
			transformation.appendScale(canvas.getHeight() / (GameConstants.MAP_RADIUS * 2.5),
					canvas.getHeight() / (GameConstants.MAP_RADIUS * 2.5));
		}

		context.setTransform(transformation);

		mapRenderer.draw(context);

		context.setLineWidth(DesignConstants.OVERLAY_LINE_WIDTH);
		mapOverlayRenderer.draw(context);

		context.restore();

		context.save();
		hudRenderer.draw(context);
		context.restore();
	}

	@Override
	public void handle(InputEvent event) {
		if (event instanceof KeyEvent) {
			KeyEvent keyEvent = (KeyEvent) event;
			if (keyEvent.getCode() == KeyCode.ESCAPE) {
				manager.pop();
			} else if (keyEvent.getEventType().equals(KeyEvent.KEY_TYPED)) {
				if (keyEvent.getCharacter().equals("p")) {
					if (timeline.getStatus() == Status.RUNNING) {
						timeline.stop();
					} else {
						timeline.play();
					}
				}
			}
		}
		game.handle(event);
	}
}
