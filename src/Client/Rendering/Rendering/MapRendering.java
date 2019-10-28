package Client.Rendering.Rendering;

import Client.Rendering.Drawing.AnimatedImage;
import Client.Rendering.Drawing.ImageManager;

import Client.Rendering.RenderingUtils;
import Game.Logic.Base;
import Game.Logic.Coin;
import Game.Logic.Map;
import Game.Logic.Mine;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;

/**
 * @author Claudius Grimm (claudius@acagamics.de)
 */
public final class MapRendering {

    Map gameMap;

    ArrayList<Base> bases;
    ArrayList<Mine> mines;

    /**
     * Used to render the game map.
     * @param inGameMap The map to render.
     */
    public MapRendering(Map inGameMap) {
        gameMap = inGameMap;
        mines = gameMap.getMines();
        bases = gameMap.getBases();
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
        for (int i = 0; i < mines.size(); i++) {
            context.drawImage(minetexture, RenderingUtils.toPixelCoordinates(mines.get(i).getPosition()).getX() - 40,
                    RenderingUtils.toPixelCoordinates(mines.get(i).getPosition()).getY() - 30, 80, 60);
        }

        Image baseTexture = ImageManager.getInstance().loadImage("base.png");
        for (int i = 0; i < bases.size(); i++) {
            context.drawImage(baseTexture, RenderingUtils.toPixelCoordinates(bases.get(i).getPosition()).getX() - 40,
                    RenderingUtils.toPixelCoordinates(bases.get(i).getPosition()).getY() - 30, 80, 60);
        }
    }

}
