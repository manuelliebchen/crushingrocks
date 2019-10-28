package Game.Logic;

import java.util.ArrayList;
import java.util.List;

import Game.GameConstants;
import Game.Controller.IPlayerController;
import Game.Controller.MapInfo;
import Game.Controller.PlayerInfo;

/**
 * Player is the active entity in the game, controlled by PlayerControllers.
 * @author Jan-Cord Gerken (jancord@acagamics.de)
 *
 */
public class Player {
	private IPlayerController controller;
	private int score;
	private PlayerInfo controllerInfo = new PlayerInfo(this);
	
	private Color color;
	
	private Base base;
	private List<Unit> units = new ArrayList<>(16);
	
	public Player(IPlayerController controller){
		this.controller = controller;
		reset();
	}
	
	/**
	 * Reset the Player.
	 */
	public void reset(){
		units = new ArrayList<>(16);
		score = 0;
	}
	
	/**
	 * TODO: add documentation (depends on PlayerController, which does not yet exist)
	 */
	public void update(MapInfo mapInfo, Player[] allPlayers) {
		// Create a list with enemy player infos.
		PlayerInfo[] enemyInfos = new PlayerInfo[allPlayers.length-1];
		for(int playerIndex=0, infoIndex=0; playerIndex<enemyInfos.length; ++playerIndex) {
			if(allPlayers[playerIndex] != this) {
				enemyInfos[infoIndex] = allPlayers[playerIndex].controllerInfo;
				++infoIndex;
			}
		}
		
		// Think.
		// TODO: Add timer!
		Vector direction = controller.think(mapInfo, controllerInfo, enemyInfos);
		

		
		// Limit maximal speed.
		float speed = direction.length();
		if(speed > 1.0f)
			direction = direction.div(speed);

	}

	/**
	 * Change the score of this player.
	 */
	public void addScore(float scoreBonus){
		score += scoreBonus;
	}
	
	/**
	 * Get the score of this player.
	 * @return score
	 */
	public float getScore() {
		return score;
	}
	
	/**
	 * Get the Base of the Player.
	 * @return Base of the Player
	 */
	public Base getBase() {
		return base;
	}
}
