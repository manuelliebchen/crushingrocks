package Game.Logic;

import Game.Types.Vector;

/**
 * Coins can be collected by Players for increasing their score.
 * @author Manuel Liebchen
 *
 */
public class Mine {
	Vector position;
	Player currentOwner;
	
	public Mine(Mine copy){
		this.position = new Vector(copy.position);
		this.currentOwner = copy.currentOwner;
	}
	
	public Mine(Vector position){
		this.position = position;
	}

	/**
	 * Get the position of this coin.
	 * @return position
	 */
	public Vector getPosition() {
		return position.copy();
	}
}
