package de.acagamics.crushingrocks.rendering;

import java.util.List;

import de.acagamics.crushingrocks.GameConstants;
import de.acagamics.crushingrocks.RenderingConstants;
import de.acagamics.crushingrocks.logic.Base;
import de.acagamics.crushingrocks.logic.Map;
import de.acagamics.crushingrocks.logic.Mine;
import de.acagamics.crushingrocks.logic.Player;
import de.acagamics.crushingrocks.logic.Unit;
import de.acagamics.framework.gui.interfaces.IDrawable;
import de.acagamics.framework.resourcemanagment.ResourceManager;
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
			Image baseTexture = ResourceManager.getInstance().loadImage("Base" + base.getOwner().getPlayerID() + ".png");
			context.drawImage(baseTexture,
					base.getPosition().getX()
							- GameConstants.BASE_RADIUS * RenderingConstants.BASE_RENDERING_SIZE_MULTIPLIER,
					base.getPosition().getY()
							- GameConstants.BASE_RADIUS * RenderingConstants.BASE_RENDERING_SIZE_MULTIPLIER,
					2 * RenderingConstants.BASE_RENDERING_SIZE_MULTIPLIER * GameConstants.BASE_RADIUS,
					2 * RenderingConstants.BASE_RENDERING_SIZE_MULTIPLIER * GameConstants.BASE_RADIUS);
		}

		Image minetexture = ResourceManager.getInstance().loadImage("mine.png");
		for (Mine mine : mines) {
			context.drawImage(minetexture, mine.getPosition().getX() - GameConstants.MINE_RADIUS,
					mine.getPosition().getY() - GameConstants.MINE_RADIUS, 2 * GameConstants.MINE_RADIUS,
					2 * GameConstants.MINE_RADIUS);
		}

		Image unitTexture1 = ResourceManager.getInstance().loadImage("Unit1.png");
		Image unitTexture2 = ResourceManager.getInstance().loadImage("Unit2.png");
		Image unitTexture3 = ResourceManager.getInstance().loadImage("Unit3.png");
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
								- GameConstants.UNIT_RADIUS * RenderingConstants.UNIT_RENDERING_SIZE_MULTIPLIER,
						unit.getPosition().getY()
								- GameConstants.UNIT_RADIUS * RenderingConstants.UNIT_RENDERING_SIZE_MULTIPLIER,
						2 * RenderingConstants.UNIT_RENDERING_SIZE_MULTIPLIER * GameConstants.UNIT_RADIUS,
						2 * RenderingConstants.UNIT_RENDERING_SIZE_MULTIPLIER * GameConstants.UNIT_RADIUS);
			}
		}
	}

}
