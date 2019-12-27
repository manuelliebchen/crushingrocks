package de.acagamics.crushingrocks.rendering;

import java.util.List;

import de.acagamics.crushingrocks.GameProperties;
import de.acagamics.crushingrocks.RenderingProperties;
import de.acagamics.crushingrocks.logic.Base;
import de.acagamics.crushingrocks.logic.Map;
import de.acagamics.crushingrocks.logic.Mine;
import de.acagamics.crushingrocks.logic.Player;
import de.acagamics.crushingrocks.logic.Unit;
import de.acagamics.framework.gui.interfaces.IDrawable;
import de.acagamics.framework.resourcemanagment.ResourceManager;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.ArcType;

/**
 * @author Claudius Grimm (claudius@acagamics.de)
 * @author Manuel Liebchen
 */
public final class MapOverlayRendering implements IDrawable {

	private Map gameMap;

	private List<Base> bases;
	private List<Mine> mines;
	private List<Player> players;

	/**
	 * Used to render the game map.
	 * 
	 * @param inGameMap The map to render.
	 */
	public MapOverlayRendering(Map inGameMap) {
		gameMap = inGameMap;
		mines = gameMap.getMines();
		bases = gameMap.getBases();
		players = gameMap.getPlayers();
	}

	/**
	 * Displays the map images etc.
	 * 
	 * @param context Context to draw on.
	 */
	public void draw(GraphicsContext context) {

		RenderingProperties renderingProperties = ResourceManager.getInstance()
				.loadProperties(RenderingProperties.class);
		GameProperties gameProperties = GameProperties.get();

		context.setLineWidth(renderingProperties.getOverlayLineWidth());
		for (Base base : bases) {
			context.setStroke(renderingProperties.getHealthBackground());
			context.strokeOval(base.getPosition().getX() - gameProperties.getBaseRadius(),
					base.getPosition().getY() - gameProperties.getBaseRadius(), 2 * gameProperties.getBaseRadius(),
					2 * gameProperties.getBaseRadius());
			context.setStroke(base.getOwner().getColor());
			context.strokeArc(base.getPosition().getX() - gameProperties.getBaseRadius(),
					base.getPosition().getY() - gameProperties.getBaseRadius(), 2 * gameProperties.getBaseRadius(),
					2 * gameProperties.getBaseRadius(),
					(float) (gameProperties.getPlayerBasePosition().get(base.getOwner().getPlayerID()).getAngle() * 360
							/ (Math.PI * 2) - (180 * base.getHP() / gameProperties.getBaseHP())),
					(float) (360 * base.getHP() / gameProperties.getBaseHP()), ArcType.OPEN);
		}

		for (Mine mine : mines) {
			float[] ownership = mine.getOwnership();

			for (int i = 0; i < ownership.length; ++i) {
				context.setStroke(players.get(i).getColor());
				context.strokeArc(mine.getPosition().getX() - gameProperties.getMineRadius(),
						mine.getPosition().getY() - gameProperties.getMineRadius(), 2 * gameProperties.getMineRadius(),
						2 * gameProperties.getMineRadius(),
						(float) (gameProperties.getPlayerBasePosition().get(i).getAngle() * 360 / (Math.PI * 2)
								- ownership[i] * 180),
						(float) (ownership[i] * 360), ArcType.OPEN);
			}
		}

		for (Player player : players) {
			context.setStroke(player.getColor());
			for (Unit unit : player.getUnits()) {
				context.strokeOval(unit.getPosition().getX() - gameProperties.getUnitRadius(),
						unit.getPosition().getY() - gameProperties.getUnitRadius(), 2 * gameProperties.getUnitRadius(),
						2 * gameProperties.getUnitRadius());
			}
		}
	}

}
