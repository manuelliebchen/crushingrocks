package Client.Rendering.Rendering;

import java.util.List;

import Client.GUI.States.Interfaces.IDrawable;
import Client.Rendering.Drawing.ImageManager;
import Constants.GameConstants;
import Game.Logic.Base;
import Game.Logic.Map;
import Game.Logic.Mine;
import Game.Logic.Player;
import Game.Logic.Unit;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * @author Claudius Grimm (claudius@acagamics.de)
 * @author Manuel Liebchen
 */
public final class MapRendering implements IDrawable {

	private Map gameMap;

	private List<Base> bases;
	private List<Mine> mines;
	private List<Player> players;

	/**
	 * Used to render the game map.
	 * 
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
	 * 
	 * @param timeSinceLastUpdate Time passed since last update in seconds.
	 */
	public void update() {
	}

	/**
	 * Displays the map images etc.
	 * 
	 * @param context           Context to draw on.
	 * @param timeSinceLastDraw Time passed since last draw in seconds.
	 */
	public void draw(GraphicsContext context) {

		Image baseTexture = ImageManager.getInstance().loadImage("base.png");
		for (Base base : bases) {
			context.drawImage(baseTexture, base.getPosition().getX() - GameConstants.BASE_RADIUS,
					base.getPosition().getY() - GameConstants.BASE_RADIUS, 2 * GameConstants.BASE_RADIUS,
					2 * GameConstants.BASE_RADIUS);
		}

		Image minetexture = ImageManager.getInstance().loadImage("mine.png");
		for (Mine mine : mines) {
			context.drawImage(minetexture, mine.getPosition().getX() - GameConstants.MINE_RADIUS,
					mine.getPosition().getY() - GameConstants.MINE_RADIUS, 2 * GameConstants.MINE_RADIUS,
					2 * GameConstants.MINE_RADIUS);
		}

		Image unitTexture1 = ImageManager.getInstance().loadImage("Unit1.png");
		Image unitTexture2 = ImageManager.getInstance().loadImage("Unit2.png");
		Image unitTexture3 = ImageManager.getInstance().loadImage("Unit3.png");
		Image untiTexture = unitTexture1;
		for (Player player : players) {
			for (Unit unit : player.getUnits()) {
				if(unit.getStrength() <= 1) {
					untiTexture = unitTexture1;
				} else if(unit.getStrength() <= 2) {
					untiTexture = unitTexture2;
				} else {
					untiTexture = unitTexture3;
				}
				context.drawImage(untiTexture, unit.getPosition().getX() - GameConstants.UNIT_RADIUS,
						unit.getPosition().getY() - GameConstants.UNIT_RADIUS, 2 * GameConstants.UNIT_RADIUS,
						2 * GameConstants.UNIT_RADIUS);
			}
		}
	}

}
