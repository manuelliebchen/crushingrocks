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
		context.setLineWidth(RenderingConstants.OVERLAY_LINE_WIDTH);
		for (Base base : bases) {
			context.setStroke(RenderingConstants.HEALTH_BACKGROUND);
			context.strokeOval(base.getPosition().getX() - GameConstants.BASE_RADIUS,
					base.getPosition().getY() - GameConstants.BASE_RADIUS, 2 * GameConstants.BASE_RADIUS,
					2 * GameConstants.BASE_RADIUS);
			context.setStroke(base.getOwner().getColor());
			context.strokeArc(base.getPosition().getX() - GameConstants.BASE_RADIUS,
					base.getPosition().getY() - GameConstants.BASE_RADIUS, 2 * GameConstants.BASE_RADIUS,
					2 * GameConstants.BASE_RADIUS,
					(float) (GameConstants.PLAYER_BASE_POSITION[base.getOwner().getPlayerID()].getAngle() * 360/(Math.PI * 2) - (180 * base.getHP() / GameConstants.INITIAL_BASE_HP)),
					(float) (360 * base.getHP() / GameConstants.INITIAL_BASE_HP), ArcType.OPEN);
		}

		for (Mine mine : mines) {
			float[] ownership = mine.getOwnership();

			for (int i = 0; i < ownership.length; ++i) {
				context.setStroke(players.get(i).getColor());
				context.strokeArc(mine.getPosition().getX() - GameConstants.MINE_RADIUS,
						mine.getPosition().getY() - GameConstants.MINE_RADIUS, 2 * GameConstants.MINE_RADIUS,
						2 * GameConstants.MINE_RADIUS,
						(float) (GameConstants.PLAYER_BASE_POSITION[i].getAngle() * 360/(Math.PI * 2) - ownership[i] * 180),
						(float) (ownership[i] * 360), ArcType.OPEN);
			}
		}

		for (Player player : players) {
			context.setStroke(player.getColor());
			for (Unit unit : player.getUnits()) {
				context.strokeOval(unit.getPosition().getX() - GameConstants.UNIT_RADIUS,
						unit.getPosition().getY() - GameConstants.UNIT_RADIUS, 2 * GameConstants.UNIT_RADIUS,
						2 * GameConstants.UNIT_RADIUS);
			}
		}
	}

}