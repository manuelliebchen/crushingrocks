package Game.Controller;

import Game.Logic.Map;
import Game.Logic.Player;

/**
 * Player controller interface from which all bots and human player controllers implement. 
 * @author Andreas Reich (andreas@acagamics.de)
 *
 */
public interface IPlayerController {
	
	/**
	 * Returns the name of the author of this player controller.
	 * @return A name of a human being.
	 */
	String getAuthor();
	
	/**
	 * Returns the  of the author of this player controller.
	 * @return A name of a human being.
	 */
	int getMatrikelnummer();
	
	/**
	 * This method contains the actual AI/controlling.
	 * It is called in every update step of the game as long as the player is alive.
	 * @return The direction and speed in which the should move. A zero vector means no movement.
	 */
	void think(Map mapInfo, Player ownPlayer, Player enemyPlayerInfo);
}
