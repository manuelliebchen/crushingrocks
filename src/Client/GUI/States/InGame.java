package Client.GUI.States;

import java.util.ArrayList;

import Client.GUI.States.Interfaces.GameState;
import Client.GUI.States.Interfaces.IDraw;
import Client.GUI.States.Interfaces.IUpdate;
import Client.Rendering.Rendering.MapRendering;
import Client.Rendering.Rendering.PlayerRendering;
import Game.Controller.IPlayerController;
import Game.Controller.BuiltIn.SampleBot;
import Game.Controller.Loader.PlayerControllerLoader;
import Game.Logic.Game;
import javafx.scene.canvas.GraphicsContext;

/**
 * @author Claudius Grimm (claudius@acagamics.de)
 */
public final class InGame extends GameState implements IDraw, IUpdate {

    private Game game;
    private MapRendering mapRenderer;
    private PlayerRendering[] playerRenderers;

    private float timeSinceLastGameUpdate = 0;

    public InGame(StateManager manager) {
        super(manager);

        PlayerControllerLoader playerLoader = new PlayerControllerLoader();

        // Only for testing purposes, you should use a special directory for external bots.
        // Build in bots, can always be loaded via instantiateInternController.
        playerLoader.loadControllerFromDirectory("Game/Controller/BuiltIn/");

        ArrayList<IPlayerController> playerControllers = new ArrayList<>();
        playerControllers.add(playerLoader.instantiateLoadedExternController(SampleBot.class.getName()));
        playerControllers.add(playerLoader.instantiateInternController(SampleBot.class.getName()));

        game = new Game(playerControllers);

        mapRenderer = new MapRendering(game.getMap());

        playerRenderers = new PlayerRendering[game.getNumPlayers()];
        for (int i = 0; i < playerRenderers.length; i++) {
            playerRenderers[i] = new PlayerRendering(game.getPlayer(i));
        }
    }

    /**
     * Updates all needed display objects, e.g. player, map.
     * @param elapsedTime Time passed since last update in seconds.
     */
    @Override
    public void update(float elapsedTime) {
        timeSinceLastGameUpdate += elapsedTime;

        // TODO: The loop freezes the game, possible endless loop.
        // This is needed to be sure that each game tick duration is always the same amount of time.
        //while (timeSinceLastGameUpdate > ClientConstants.SIMULATION_STEP_INTERVAL) {
            game.tick();
        //    timeSinceLastGameUpdate -= ClientConstants.SIMULATION_STEP_INTERVAL;
        //}

        mapRenderer.update(elapsedTime);

        for (PlayerRendering playerRend : playerRenderers) {
            playerRend.update(elapsedTime);
        }

    }

    /**
     * Draws all needed display objects, e.g. player, map.
     * @param context The context to draw on.
     * @param elapsedTime Time passed since last draw in seconds.
     */
    @Override
    public void draw(GraphicsContext context, float elapsedTime) {
        mapRenderer.draw(context, elapsedTime);

        for (PlayerRendering playerRend : playerRenderers) {
            playerRend.draw(context, elapsedTime);
        }
    }
}
