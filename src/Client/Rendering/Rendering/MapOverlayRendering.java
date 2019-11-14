package Client.Rendering.Rendering;

import java.util.List;

import Client.GUI.States.Interfaces.IDrawable;
import Constants.DesignConstants;
import Constants.GameConstants;
import Game.Logic.Base;
import Game.Logic.Map;
import Game.Logic.Mine;
import Game.Logic.Player;
import Game.Logic.Unit;
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
	 * @param timeSinceLastDraw Time passed since last draw in seconds.
	 */
	public void draw(GraphicsContext context) {
		for (Base base : bases) {
			context.setStroke(base.getOwner().getColor());
			context.strokeOval(base.getPosition().getX() - GameConstants.BASE_RADIUS,
					base.getPosition().getY() - GameConstants.BASE_RADIUS, 2 * GameConstants.BASE_RADIUS,
					2 * GameConstants.BASE_RADIUS);

			context.setFill(DesignConstants.HEALTH_BACKGROUND);
			context.fillRect(base.getPosition().getX() - GameConstants.BASE_RADIUS,
					base.getPosition().getY() - GameConstants.BASE_RADIUS - 0.04, GameConstants.BASE_RADIUS * 2, 0.02);
			context.setFill(DesignConstants.HEALTH_FOREGROUND);
			context.fillRect(base.getPosition().getX() - GameConstants.BASE_RADIUS,
					base.getPosition().getY() - GameConstants.BASE_RADIUS - 0.04,
					GameConstants.BASE_RADIUS * 2 * base.getHP() / GameConstants.INITIAL_BASE_HP, 0.02);
		}

		for (Mine mine : mines) {
			float[] ownership = mine.getOwnership();
//			context.setStroke(players.get(0).getColor().interpolate(players.get(1).getColor(), ownership[1]));
//			context.strokeOval(mine.getPosition().getX() - GameConstants.MINE_RADIUS,
//					mine.getPosition().getY() - GameConstants.MINE_RADIUS, 2 * GameConstants.MINE_RADIUS,
//					2 * GameConstants.MINE_RADIUS);

//			context.setFill(players.get(1).getColor());
//			context.fillRect(mine.getPosition().getX() - GameConstants.MINE_RADIUS,
//					mine.getPosition().getY() - GameConstants.MINE_RADIUS - 0.04, GameConstants.MINE_RADIUS * 2, 0.02);
//			context.setFill(players.get(0).getColor());
//			context.fillRect(mine.getPosition().getX() - GameConstants.MINE_RADIUS,
//					mine.getPosition().getY() - GameConstants.MINE_RADIUS - 0.04,
//					GameConstants.MINE_RADIUS * 2 * ownership[0], 0.02);

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
