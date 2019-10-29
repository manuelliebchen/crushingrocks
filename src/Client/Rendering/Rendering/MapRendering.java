package Client.Rendering.Rendering;

import java.util.List;

import Client.Rendering.RenderingUtils;
import Client.Rendering.Drawing.ImageManager;
import Game.Logic.Base;
import Game.Logic.Map;
import Game.Logic.Mine;
import Game.Logic.Player;
import Game.Logic.Unit;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * @author Claudius Grimm (claudius@acagamics.de)
 */
public final class MapRendering {

    Map gameMap;

    List<Base> bases;
    List<Mine> mines;
    List<Player> players;

    /**
     * Used to render the game map.
     * @param inGameMap The map to render.
     */
    public MapRendering(Map inGameMap) {
        gameMap = inGameMap;
        mines = gameMap.getMines();
        bases = gameMap.getBases();
        players = gameMap.getPlayers();
    }

    /**
     * Updates the map rendering. E.g. animated sprites.
     * @param timeSinceLastUpdate Time passed since last update in seconds.
     */
    public void update(float timeSinceLastUpdate) {
    }

    /**
     * Displays the map images etc.
     * @param context Context to draw on.
     * @param timeSinceLastDraw Time passed since last draw in seconds.
     */
    public void draw(GraphicsContext context, float timeSinceLastDraw) {

        Image img = ImageManager.getInstance().loadImage("bg.jpg");
        context.drawImage(img, 0, 0);

        Image minetexture = ImageManager.getInstance().loadImage("mine.png");
        for (Mine mine : mines) {
            context.drawImage(minetexture, RenderingUtils.toPixelCoordinates(mine.getPosition()).getX() - 25,
                    RenderingUtils.toPixelCoordinates(mine.getPosition()).getY() - 25, 50, 50);
        }

        Image baseTexture = ImageManager.getInstance().loadImage("base.png");
        for (Base base : bases) {
            context.drawImage(baseTexture, RenderingUtils.toPixelCoordinates(base.getPosition()).getX() - 25,
                    RenderingUtils.toPixelCoordinates(base.getPosition()).getY() - 25, 50, 50);
        }

        Image unitTexture = ImageManager.getInstance().loadImage("player.png");
        for (Player player : players) {
        	for(Unit unit : player.getUnits()) {
        		context.drawImage(unitTexture, RenderingUtils.toPixelCoordinates(unit.getPosition()).getX() - 12,
                    RenderingUtils.toPixelCoordinates(unit.getPosition()).getY() - 12, 25, 25);
            }
        }
    }

}
