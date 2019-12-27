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
	 * @param context           Context to draw on.
	 */
	public void draw(GraphicsContext context) {
		
		RenderingProperties renderingProperties = ResourceManager.getInstance().loadProperties(RenderingProperties.class);
		
		
		context.setLineWidth(renderingProperties.getOverlayLineWidth());
		for (Base base : bases) {
			context.setStroke(renderingProperties.getHealthBackground());
			context.strokeOval(base.getPosition().getX() - GameProperties.BASE_RADIUS,
					base.getPosition().getY() - GameProperties.BASE_RADIUS, 2 * GameProperties.BASE_RADIUS,
					2 * GameProperties.BASE_RADIUS);
			context.setStroke(base.getOwner().getColor());
			context.strokeArc(base.getPosition().getX() - GameProperties.BASE_RADIUS,
					base.getPosition().getY() - GameProperties.BASE_RADIUS, 2 * GameProperties.BASE_RADIUS,
					2 * GameProperties.BASE_RADIUS,
					(float) (GameProperties.PLAYER_BASE_POSITION[base.getOwner().getPlayerID()].getAngle() * 360/(Math.PI * 2) - (180 * base.getHP() / GameProperties.INITIAL_BASE_HP)),
					(float) (360 * base.getHP() / GameProperties.INITIAL_BASE_HP), ArcType.OPEN);
		}

		for (Mine mine : mines) {
			float[] ownership = mine.getOwnership();

			for (int i = 0; i < ownership.length; ++i) {
				context.setStroke(players.get(i).getColor());
				context.strokeArc(mine.getPosition().getX() - GameProperties.MINE_RADIUS,
						mine.getPosition().getY() - GameProperties.MINE_RADIUS, 2 * GameProperties.MINE_RADIUS,
						2 * GameProperties.MINE_RADIUS,
						(float) (GameProperties.PLAYER_BASE_POSITION[i].getAngle() * 360/(Math.PI * 2) - ownership[i] * 180),
						(float) (ownership[i] * 360), ArcType.OPEN);
			}
		}

		for (Player player : players) {
			context.setStroke(player.getColor());
			for (Unit unit : player.getUnits()) {
				context.strokeOval(unit.getPosition().getX() - GameProperties.UNIT_RADIUS,
						unit.getPosition().getY() - GameProperties.UNIT_RADIUS, 2 * GameProperties.UNIT_RADIUS,
						2 * GameProperties.UNIT_RADIUS);
			}
		}
	}

}
