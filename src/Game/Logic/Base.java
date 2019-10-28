package Game.Logic;

import Game.GameConstants;

/**
 * Coins can be collected by Players for increasing their score.
 * @author Manuel Liebchen
 *
 */
public class Base {
	Vector position;
	Player owner;
	int hp;
	
	public Base(Base copy){
		this.position = new Vector(copy.position);
		this.owner = copy.owner;
		hp = GameConstants.INITAL_BASE_HP;
	}
	
	public Base(Player owner, Vector position){
		this.owner = owner;
		this.position = position;
		hp = GameConstants.INITAL_BASE_HP;
	}

	/**
	 * Get the position of this coin.
	 * @return position
	 */
	public Vector getPosition() {
		return position;
	}
	
	/**
	 * @return HP of the Base
	 */
	public int getHP() {
		return hp;
	}
}
