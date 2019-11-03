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
	private float[] ownership;
	
	Mine(Mine copy){
		this.position = new Vector(copy.position);
		this.ownership = copy.ownership;
	}
	
	Mine(Vector position, int numberOfPlayer){
		this.position = position;
		ownership = new float[numberOfPlayer]; 
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
	public float[] getOwner() {
		return ownership;
	}

	void update(List<Player> players, List<Unit> allUnits) {
		//TODO:Implement transition function
		float[] count = new float[players.size()];
		for(Unit unit : allUnits) {
			if(position.distance(unit.getPosition()) < GameConstants.MINE_RADIUS) {
				count[unit.getOwner().getPlayerID()]++;
			}
		}
		float sum = 0;
		for(int i = 0; i < count.length; ++i) {
			sum += count[i];
		}
		for(int i = 0; i < count.length; ++i) {
			ownership[i] += ((count[i] / sum) - ownership[i]) / GameConstants.MINE_CAPTURING_TICKES;
		}
	}
}
