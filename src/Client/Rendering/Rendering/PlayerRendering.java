package Client.Rendering.Rendering;

import Client.Rendering.Drawing.ImageManager;
import Client.Rendering.RenderingUtils;
import Game.Logic.Player;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * @author Claudius Grimm (claudius@acagamics.de)
 */
public final class PlayerRendering {

    Player player;
    Image playerImg;

    /**
     * Used for rendering each player.
     * @param gamePlayer The player to render.
     */
    public PlayerRendering(Player gamePlayer) {
        player = gamePlayer;

        playerImg = ImageManager.getInstance().loadImage("player.png");
    }

    /**
     * Updates the player rendering. E.g. animated sprites.
     * @param timeSinceLastUpdate Time passed since last update in seconds.
     */
    public void update(float timeSinceLastUpdate) {
        
    }

    /**
     * Displays the player images etc.
     * @param context Context to draw on.
     * @param timeSinceLastDraw Time passed since last draw in seconds.
     */
    public void draw(GraphicsContext context, float timeSinceLastDraw) {
//        context.drawImage(playerImg, RenderingUtils.toPixelCoordinates(player.getPosition()).getX() - 10,
//                RenderingUtils.toPixelCoordinates(player.getPosition()).getY() - 10, 20, 20);
    }

}
