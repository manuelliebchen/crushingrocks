package Client.GUI.States;

import java.util.ArrayList;

import Client.GUI.States.Interfaces.GameState;
import Client.GUI.States.Interfaces.IDrawable;
import Client.GUI.States.Interfaces.IUpdateable;
import Client.Rendering.Rendering.HUDRenderer;
import Client.Rendering.Rendering.MapOverlayRendering;
import Client.Rendering.Rendering.MapRendering;
import Constants.DesignConstants;
import Constants.GameConstants;
import Game.Controller.IPlayerController;
import Game.Controller.PlayerControllerLoader;
import Game.Controller.BuiltIn.SampleBot;
import Game.Logic.Game;
import Game.Logic.GameStatistic;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.transform.Affine;

/**
 * @author Claudius Grimm (claudius@acagamics.de)
 * @author Manuel Liebchen
 */
public final class InGame extends GameState implements IDrawable, IUpdateable {

	private Game game;
	private MapRendering mapRenderer;
	private MapOverlayRendering mapOverlayRenderer;
	private HUDRenderer hudRenderer;

//    private float timeSinceLastGameUpdate = 0;

	public InGame(StateManager manager) {
		super(manager);

		PlayerControllerLoader playerLoader = new PlayerControllerLoader();

		// Only for testing purposes, you should use a special directory for external
		// bots.
		// Build in bots, can always be loaded via instantiateInternController.
		playerLoader.loadControllerFromDirectory("Game/Controller/BuiltIn/");

		ArrayList<IPlayerController> playerControllers = new ArrayList<>();
//        playerControllers.add(playerLoader.instantiateLoadedExternController(HumanBot.class.getName()));
		playerControllers.add(playerLoader.instantiateInternController(SampleBot.class.getName()));
		playerControllers.add(playerLoader.instantiateInternController(SampleBot.class.getName()));
//		playerControllers.add(playerLoader.instantiateInternController(SampleBot.class.getName()));

		game = new Game(playerControllers);

		mapRenderer = new MapRendering(game.getMap());
		mapOverlayRenderer = new MapOverlayRendering(game.getMap());
		hudRenderer = new HUDRenderer(game);
	}

	/**
	 * Updates all needed display objects, e.g. player, map.
	 * 
	 * @param elapsedTime Time passed since last update in seconds.
	 */
	@Override
	public void update(float elapsedTime) {

		GameStatistic statistic = game.tick();
		if (statistic != null) {
			manager.switchCurrentState(new GameStatisticState(manager, statistic));
		}

		mapRenderer.update(elapsedTime);
	}

	/**
	 * Draws all needed display objects, e.g. player, map.
	 * 
	 * @param context     The context to draw on.
	 * @param elapsedTime Time passed since last draw in seconds.
	 */
	@Override
	public void draw(GraphicsContext context) {
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
		
		context.setLineWidth(0.005);
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
			}
		}
		game.handle(event);
	}
}
