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
	private Vector position;
	private Player currentOwner;
	
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

	/**
	 * Owner of the Mine.
	 * @return Player that owns this mine.
	 */
	public Player getOwner() {
		return currentOwner;
	}

	void update(List<Player> players, List<Unit> allUnits) {
		//TODO:Implement transition function
		int[] count = new int[players.size()];
		for(Unit unit : allUnits) {
			if(position.distance(unit.getPosition()) < GameConstants.MINE_RADIUS) {
				count[players.indexOf(unit.getOwner())]++;
			}
		}
		int maxIndex = 0;
		for( int i = 0; i < count.length; ++i) {
			if(count[i] > count[maxIndex]) {
				maxIndex = i;
			}
		}
		if(count[maxIndex] > 0) {
			currentOwner = players.get(maxIndex);
		}
	}
}
