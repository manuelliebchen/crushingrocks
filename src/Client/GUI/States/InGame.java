package Client.GUI.States;

import java.util.ArrayList;
import java.util.List;

import Client.GUI.States.Interfaces.GameState;
import Client.GUI.States.Interfaces.ISelfUpdating;
import Client.Rendering.Rendering.HUDRenderer;
import Client.Rendering.Rendering.MapOverlayRendering;
import Client.Rendering.Rendering.MapRendering;
import Constants.ClientConstants;
import Constants.DesignConstants;
import Constants.GameConstants;
import Game.Controller.IPlayerController;
import Game.Controller.PlayerControllerLoader;
import Game.Controller.BuiltIn.SampleBot;
import Game.Logic.Game;
import Game.Logic.GameStatistic;
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
public final class InGame extends GameState implements ISelfUpdating {

	private Game game;
	private MapRendering mapRenderer;
	private MapOverlayRendering mapOverlayRenderer;
	private HUDRenderer hudRenderer;

	Timeline timeline;
//    private float timeSinceLastGameUpdate = 0;

	public InGame(StateManager manager, GraphicsContext context, int speed) {
		super(manager, context);

		PlayerControllerLoader playerLoader = new PlayerControllerLoader();

		// Only for testing purposes, you should use a special directory for external
		// bots.
		// Build in bots, can always be loaded via instantiateInternController.
//		playerLoader.loadControllerFromDirectory("Game/Controller/BuiltIn/");

		List<IPlayerController> playerControllers = new ArrayList<>();
//        playerControllers.add(playerLoader.instantiateLoadedExternController(HumanBot.class.getName()));
		playerControllers.add(playerLoader.instantiateInternController(SampleBot.class.getName()));
		playerControllers.add(playerLoader.instantiateInternController(SampleBot.class.getName()));

		game = new Game(playerControllers);

		mapRenderer = new MapRendering(game.getMap());
		mapOverlayRenderer = new MapOverlayRendering(game.getMap());
		hudRenderer = new HUDRenderer(game);

		timeline = new Timeline();
		KeyFrame frame = new KeyFrame(Duration.millis(ClientConstants.MINIMUM_TIME_PER_FRAME_MS/speed), (event) -> {
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
			manager.switchCurrentState(new GameStatisticState(manager, context, statistic));
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
		context.setFill(DesignConstants.BACKGROUND_COLOR);
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
