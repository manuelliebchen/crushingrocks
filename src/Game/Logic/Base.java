package Game.Logic;

import Constants.GameConstants;
import Game.Types.Vector;

/**
 * Coins can be collected by Players for increasing their score.
 * @author Manuel Liebchen
 *
 */
public class Base {
	private Vector position;
	private Player owner;
	private int hp;
	
	Base(Player owner, Vector position){
		this.owner = owner;
		this.position = position;
		hp = GameConstants.INITIAL_BASE_HP;
	}
	
	public Player getOwner() {
		return owner;
	}

	/**
	 * Get the position of this coin.
	 * @return position
	 */
	public Vector getPosition() {
		return position.copy();
	}
	
	/**
	 * @return HP of the Base
	 */
	public int getHP() {
		return hp;
	}
}
