package Game.Logic;

import java.util.List;

import Constants.GameConstants;
import Game.Types.Vector;

/**
 * Coins can be collected by Players for increasing their score.
 * @author Manuel Liebchen
 *
 */
public class Mine {
	Vector position;
	Player currentOwner;
	
	Mine(Mine copy){
		this.position = new Vector(copy.position);
		this.currentOwner = copy.currentOwner;
	}
	
	Mine(Vector position){
		this.position = position;
	}

	/**
	 * Get the position of this coin.
	 * @return position
	 */
	public Vector getPosition() {
		return position.copy();
	}

	public Player getOwner() {
		return currentOwner;
	}

	public void update(List<Player> players) {
		//TODO:Implement transition function
		int count = 0;
		for(Unit unit : players.get(0).getUnits()) {
			if(position.distance(unit.getPosition()) < GameConstants.MINE_RADIUS) {
				++count;
			}
		}
		for(Unit unit : players.get(1).getUnits()) {
			if(position.distance(unit.getPosition()) < GameConstants.MINE_RADIUS) {
				--count;
			}
		}
		if(count > 0) {
			currentOwner = players.get(0);
		} else if (count < 0) {
			currentOwner = players.get(1);
		}
	}
}
