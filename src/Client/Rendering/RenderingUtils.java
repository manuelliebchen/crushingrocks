package Client.Rendering;

import Client.ClientConstants;
import Game.Logic.Vector;

import javafx.geometry.Point2D;

/**
 * @author Claudius Grimm (claudius@acagamics.de)
 */
public final class RenderingUtils {

    /**
     * Calculates a rendering position from given Vector.
     * @return Position for rendering.
     */
    public static Point2D toPixelCoordinates(Vector position) {
        Point2D midPoint = new Point2D(ClientConstants.SCREEN_WIDTH / 2, ClientConstants.SCREEN_HEIGHT / 2);

        // normalize
        Vector pos = position.mult(1 / ClientConstants.MAP_SIZE, 1 / ClientConstants.MAP_SIZE);

        // scale to Window-Size
        pos = pos.mult(Math.min(ClientConstants.SCREEN_WIDTH, ClientConstants.SCREEN_HEIGHT) / 2,
                Math.min(ClientConstants.SCREEN_WIDTH, ClientConstants.SCREEN_HEIGHT) / 2);

        return new Point2D(midPoint.getX() + pos.getX(), midPoint.getY() + pos.getY());
    }

    /**
     * Calculates an game logic vector from given position.
     * @return Game logic vector.
     */
    public static Vector toIngameCoordinates(Point2D pos) {
        // set around Position (0,0)
        Vector ingamePos = new Vector((float) (pos.getX() - (ClientConstants.SCREEN_WIDTH / 2)),
                (float) (pos.getY() - (ClientConstants.SCREEN_HEIGHT / 2)));

        // scale to ingame-size
        ingamePos = ingamePos.mult((ClientConstants.MAP_SIZE * 2) / ClientConstants.SCREEN_WIDTH,
                (ClientConstants.MAP_SIZE * 2) / ClientConstants.SCREEN_HEIGHT);

        ingamePos.y = ingamePos.y * -1;
        return ingamePos;
    }

}
