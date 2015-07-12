package Game.Logic;

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
	private Vector position;
	private float radius;
	private IPlayerController controller;
	private float score;
	private PlayerInfo controllerInfo = new PlayerInfo(this);
	
	public Player(IPlayerController controller){
		this.controller = controller;
		resetPosition();
		radius = 1F;
		score = 0F;
	}
	
	/**
	 * Places the Player back in its startPosition.
	 */
	public void resetPosition(){
		position = Vector.ZERO();
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
		
		// Move.
		position = position.add(direction.mult(GameConstants.MAX_PLAYER_SPEED));
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
	 * Get the radius of this player.
	 * @return radius
	 */
	public float getRadius() {
		return radius;
	}

	/**
	 * Get the position of this player.
	 * @return position
	 */
	public Vector getPosition(){
		return position;
	}
}
