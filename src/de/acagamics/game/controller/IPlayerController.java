package de.acagamics.game.controller;

import de.acagamics.game.logic.Map;
import de.acagamics.game.logic.Player;

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
	 * Returns the Matrikelnummer of the author of this controller.
	 * @return A valid matrikelnummer for the evaluation of the programming competition.
	 */
	int getMatrikelnummer();
	
	/**
	 * This method contains the actual AI/controlling.
	 * It is called in every update step of the game as long as the player is alive.
	 * @param mapInfo Map on witch is played.
	 * @param ownPlayer object of own player to get Units and set orders.
	 * @param enemyPlayerInfo object for informations of the other player.
	 */
	void think(Map mapInfo, Player ownPlayer, Player enemyPlayerInfo);
}
