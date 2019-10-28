package Game.Controller;

import java.util.List;

import Game.Info.MapInfo;
import Game.Info.PlayerInfo;
import Game.Logic.Unit;
import Game.Logic.GameConstants.UNIT_TYPE;

/**
 * Player controller interface from which all bots and human player controllers implement. 
 * @author Andreas Reich (andreas@acagamics.de)
 *
 */
public interface IPlayerController {
	/**
	 * Returns the name of this player controller.
	 * This name is used for displaying and does not need to be globally unique (though it would be nice).
	 * @return The name of this controller.
	 */
	String getName();
	
	/**
	 * Returns the name of the author of this player controller.
	 * @return A name of a human being.
	 */
	String getAuthor();
	
	/**
	 * This method contains the actual AI/controlling.
	 * It is called in every update step of the game as long as the player is alive.
	 * @return The direction and speed in which the should move. A zero vector means no movement.
	 */
	UNIT_TYPE think(MapInfo mapInfo, List<Unit> ownUnits, PlayerInfo enemyPlayerInfo);
}
