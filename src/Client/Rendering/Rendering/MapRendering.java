package Client.Rendering.Rendering;

import Client.Rendering.Drawing.AnimatedImage;
import Client.Rendering.Drawing.ImageManager;

import Client.Rendering.RenderingUtils;
import Game.Logic.Coin;
import Game.Logic.Map;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;

/**
 * @author Claudius Grimm (claudius@acagamics.de)
 */
public final class MapRendering {

    Map gameMap;

    ArrayList<Coin> coins;
    ArrayList<AnimatedImage> coinImages =  new ArrayList<>();

    /**
     * Used to render the game map.
     * @param inGameMap The map to render.
     */
    public MapRendering(Map inGameMap) {
        gameMap = inGameMap;

        coins = gameMap.getCoins();
        for (int i = 0; i < coins.size(); i++) {
            AnimatedImage coinImage = ImageManager.getInstance().loadAnimatedImage(1.0f, "coin/", "coin0.png", "coin1.png",
                    "coin2.png", "coin3.png", "coin4.png", "coin5.png", "coin6.png");

            coinImages.add(i, coinImage);
        }
    }

    /**
     * Updates the map rendering. E.g. animated sprites.
     * @param timeSinceLastUpdate Time passed since last update in seconds.
     */
    public void update(float timeSinceLastUpdate) {
        for (AnimatedImage coin : coinImages) {
            coin.update(timeSinceLastUpdate);
        }
    }

    /**
     * Displays the map images etc.
     * @param context Context to draw on.
     * @param timeSinceLastDraw Time passed since last draw in seconds.
     */
    public void draw(GraphicsContext context, float timeSinceLastDraw) {

        Image img = ImageManager.getInstance().loadImage("bg.jpg");
        context.drawImage(img, 0, 0);

        for (int i = 0; i < coins.size(); i++) {
            Image coinImg = coinImages.get(i).draw();

            context.drawImage(coinImg, RenderingUtils.toPixelCoordinates(coins.get(i).getPosition()).getX() - 40,
                    RenderingUtils.toPixelCoordinates(coins.get(i).getPosition()).getY() - 30, 80, 60);
        }
    }

}
