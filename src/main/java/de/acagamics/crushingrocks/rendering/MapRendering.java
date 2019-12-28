package de.acagamics.crushingrocks.rendering;

import java.util.List;

import de.acagamics.crushingrocks.GameProperties;
import de.acagamics.crushingrocks.RenderingProperties;
import de.acagamics.crushingrocks.logic.Base;
import de.acagamics.crushingrocks.logic.Map;
import de.acagamics.crushingrocks.logic.Mine;
import de.acagamics.crushingrocks.logic.Player;
import de.acagamics.crushingrocks.logic.Unit;
import de.acagamics.framework.resources.ResourceManager;
import de.acagamics.framework.ui.interfaces.IDrawable;
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
	 * @param context Context to draw on.
	 */
	public void draw(GraphicsContext context) {

		RenderingProperties renderingProperties = ResourceManager.getInstance()
				.loadProperties(RenderingProperties.class);
		GameProperties gameProperties = ResourceManager.getInstance().loadProperties(GameProperties.class);

		for (Base base : bases) {
			Image baseTexture = ResourceManager.getInstance()
					.loadImage("Base" + base.getOwner().getPlayerID() + ".png");
			context.drawImage(baseTexture,
					base.getPosition().getX()
							- gameProperties.getBaseRadius() * renderingProperties.getBaseRenderingMultiplier(),
					base.getPosition().getY()
							- gameProperties.getBaseRadius() * renderingProperties.getBaseRenderingMultiplier(),
					2 * renderingProperties.getBaseRenderingMultiplier() * gameProperties.getBaseRadius(),
					2 * renderingProperties.getBaseRenderingMultiplier() * gameProperties.getBaseRadius());
		}

		Image minetexture = ResourceManager.getInstance().loadImage("mine.png");
		for (Mine mine : mines) {
			context.drawImage(minetexture, mine.getPosition().getX() - gameProperties.getMineRadius(),
					mine.getPosition().getY() - gameProperties.getMineRadius(), 2 * gameProperties.getMineRadius(),
					2 * gameProperties.getMineRadius());
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
								- gameProperties.getUnitRadius() * renderingProperties.getUnitRenderingMultiplier(),
						unit.getPosition().getY()
								- gameProperties.getUnitRadius() * renderingProperties.getUnitRenderingMultiplier(),
						2 * renderingProperties.getUnitRenderingMultiplier() * gameProperties.getUnitRadius(),
						2 * renderingProperties.getUnitRenderingMultiplier() * gameProperties.getUnitRadius());
			}
		}
	}

}
