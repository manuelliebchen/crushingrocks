package de.acagamics.gui.rendering;

import java.util.List;

import de.acagamics.constants.DesignConstants;
import de.acagamics.constants.GameConstants;
import de.acagamics.game.logic.Base;
import de.acagamics.game.logic.Map;
import de.acagamics.game.logic.Mine;
import de.acagamics.game.logic.Player;
import de.acagamics.game.logic.Unit;
import de.acagamics.gui.assetmanagment.AssetManager;
import de.acagamics.gui.interfaces.IDrawable;
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
	 */
	public void update() {
	}

	/**
	 * Displays the map images etc.
	 * 
	 * @param context           Context to draw on.
	 */
	public void draw(GraphicsContext context) {
		for (Base base : bases) {
			Image baseTexture = AssetManager.getInstance().loadImage("Base" + base.getOwner().getPlayerID() + ".png");
			context.drawImage(baseTexture,
					base.getPosition().getX()
							- GameConstants.BASE_RADIUS * DesignConstants.BASE_RENDERING_SIZE_MULTIPLIER,
					base.getPosition().getY()
							- GameConstants.BASE_RADIUS * DesignConstants.BASE_RENDERING_SIZE_MULTIPLIER,
					2 * DesignConstants.BASE_RENDERING_SIZE_MULTIPLIER * GameConstants.BASE_RADIUS,
					2 * DesignConstants.BASE_RENDERING_SIZE_MULTIPLIER * GameConstants.BASE_RADIUS);
		}

		Image minetexture = AssetManager.getInstance().loadImage("mine.png");
		for (Mine mine : mines) {
			context.drawImage(minetexture, mine.getPosition().getX() - GameConstants.MINE_RADIUS,
					mine.getPosition().getY() - GameConstants.MINE_RADIUS, 2 * GameConstants.MINE_RADIUS,
					2 * GameConstants.MINE_RADIUS);
		}

		Image unitTexture1 = AssetManager.getInstance().loadImage("Unit1.png");
		Image unitTexture2 = AssetManager.getInstance().loadImage("Unit2.png");
		Image unitTexture3 = AssetManager.getInstance().loadImage("Unit3.png");
		Image untiTexture = unitTexture1;
		for (Player player : players) {
			for (Unit unit : player.getUnits()) {
				if (unit.getStrength() <= 1) {
					untiTexture = unitTexture1;
				} else if (unit.getStrength() <= 2) {
					untiTexture = unitTexture2;
				} else {
					untiTexture = unitTexture3;
				}
				context.drawImage(untiTexture,
						unit.getPosition().getX()
								- GameConstants.UNIT_RADIUS * DesignConstants.UNIT_RENDERING_SIZE_MULTIPLIER,
						unit.getPosition().getY()
								- GameConstants.UNIT_RADIUS * DesignConstants.UNIT_RENDERING_SIZE_MULTIPLIER,
						2 * DesignConstants.UNIT_RENDERING_SIZE_MULTIPLIER * GameConstants.UNIT_RADIUS,
						2 * DesignConstants.UNIT_RENDERING_SIZE_MULTIPLIER * GameConstants.UNIT_RADIUS);
			}
		}
	}

}