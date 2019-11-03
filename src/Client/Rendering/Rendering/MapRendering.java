package Client.Rendering.Rendering;

import java.util.List;

import Client.ClientConstants;
import Client.Rendering.Drawing.ImageManager;
import Constants.GameConstants;
import Game.Logic.Base;
import Game.Logic.Map;
import Game.Logic.Mine;
import Game.Logic.Player;
import Game.Logic.Unit;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

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
    	
    	context.save();
    	context.scale(ClientConstants.SCREEN_HEIGHT / (GameConstants.MAP_RADIUS * 2), ClientConstants.SCREEN_HEIGHT / (GameConstants.MAP_RADIUS * 2));

    	float ratio = (float) ClientConstants.SCREEN_WIDTH/ (float) ClientConstants.SCREEN_HEIGHT;
    	context.translate(GameConstants.MAP_RADIUS * ratio, GameConstants.MAP_RADIUS);
    	
    	
        Image img = ImageManager.getInstance().loadImage("bg.jpg");
        context.drawImage(img, -GameConstants.MAP_RADIUS * (ratio), -GameConstants.MAP_RADIUS, 2 * ratio, 2);
    	context.setLineWidth(0.01f);

        Image minetexture = ImageManager.getInstance().loadImage("mine.png");
    	context.setStroke(Color.BLACK);
        for (Mine mine : mines) {
//            context.drawImage(minetexture, RenderingUtils.toPixelCoordinates(mine.getPosition()).getX() - 25,
//                    RenderingUtils.toPixelCoordinates(mine.getPosition()).getY() - 25, 50, 50);
            context.drawImage(minetexture, mine.getPosition().getX() - 0.1,
                    mine.getPosition().getY() - 0.1, 0.2, 0.2);
    		context.strokeOval(mine.getPosition().getX() - GameConstants.MINE_RADIUS,
    				mine.getPosition().getY() - GameConstants.MINE_RADIUS, 2 * GameConstants.MINE_RADIUS, 2 * GameConstants.MINE_RADIUS);
        }

        Image baseTexture = ImageManager.getInstance().loadImage("base.png");
        for (Base base : bases) {
        	context.setStroke(base.getOwner().getColor());
            context.drawImage(baseTexture, base.getPosition().getX() - 0.1,
                    base.getPosition().getY() - 0.1, 0.2, 0.2);
    		context.strokeOval(base.getPosition().getX() - GameConstants.BASE_RADIUS,
    				base.getPosition().getY() - GameConstants.BASE_RADIUS, 2 * GameConstants.BASE_RADIUS, 2 * GameConstants.BASE_RADIUS);
        }

        Image unitTexture = ImageManager.getInstance().loadImage("player.png");
        for (Player player : players) {
        	context.setStroke(player.getColor());
        	for(Unit unit : player.getUnits()) {
        		context.drawImage(unitTexture, unit.getPosition().getX() - 0.1,
                    unit.getPosition().getY() - 0.1, 0.2, 0.2);
        		context.strokeOval(unit.getPosition().getX() - GameConstants.UNIT_RADIUS,
                        unit.getPosition().getY() - GameConstants.UNIT_RADIUS, 2 * GameConstants.UNIT_RADIUS, 2 * GameConstants.UNIT_RADIUS);
            }
        }
        
        context.restore();
    }

}
