package Game.Logic;

import Game.Controller.IPlayerController;

/**
 * Player is the active entity in the game, controlled by PlayerControllers.
 * @author Jan-Cord Gerken (jancord@acagamics.de)
 *
 */
public class Player {
	
	Vector position;
	float radius;
	IPlayerController controller;
	float score;
	
	public Player(IPlayerController controller){
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
	public void update(){
		//TODO: get instructions from playerController
		//TODO: perform those actions
	}

	/**
	 * Change the score of the player.
	 */
	public void addScore(float scoreBonus){
		score += scoreBonus;
	}
	
	/**
	 * Get the score of the player.
	 * @return score
	 */
	public float getScore() {
		return score;
	}
	
	/**
	 * Get the radius of the player.
	 * @return radius
	 */
	public float getRadius() {
		return radius;
	}

	/**
	 * Get the radius of the player.
	 * @return radius
	 */
	public Vector getPosition(){
		return position;
	}
}
