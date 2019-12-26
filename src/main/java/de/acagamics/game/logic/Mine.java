package de.acagamics.game.logic;

import java.util.List;

import de.acagamics.constants.GameConstants;
import de.acagamics.game.types.Vec2f;

/**
 * Coins can be collected by Players for increasing their score.
 * @author Manuel Liebchen
 *
 */
public final class Mine {
	
	private final int mineid;
	
	private Vec2f position;
	private float[] ownership;
	
	Mine(Mine copy){
		this.mineid = copy.mineid;
		this.position = new Vec2f(copy.position);
		this.ownership = copy.ownership;
	}
	
	Mine(Vec2f position, int mineid, int numberOfPlayer){
		this.mineid = mineid;
		this.position = position;
		ownership = new float[numberOfPlayer];
		for(int i = 0; i < numberOfPlayer; ++i) {
			ownership[i] = 1 / (float) numberOfPlayer;
		}
	}
	
	
	/**
	 * Mine id getter.
	 * @return the mineid.
	 */
	public int getMineID() {
		return mineid;
	}

	/**
	 * Get the position of this coin.
	 * @return position
	 */
	public Vec2f getPosition() {
		return position.copy();
	}
	
	/**
	 * Owner of the Mine.
	 * @param player of which the ownership is returned
	 * @return Player that owns this mine.
	 */
	public float getOwnership(Player player) {
		return ownership[player.getPlayerID()];
	}

	/**
	 * Owner of the Mine.
	 * @return Player that owns this mine.
	 */
	public float[] getOwnership() {
		return ownership;
	}
	
	void setOwnership(float[] ownership) {
		this.ownership = ownership;
	}

	void update(List<Player> players, List<Unit> allUnits) {
		float[] count = new float[players.size()];
		for(Unit unit : allUnits) {
			if(position.distance(unit.getPosition()) < GameConstants.MINE_RADIUS) {
				count[unit.getOwner().getPlayerID()] += unit.getStrength();
			}
		}
		float sum = 0;
		for(int i = 0; i < count.length; ++i) {
			sum += count[i];
		}
		if(sum == 0) {
			return;
		}
		
		for(int i = 0; i < ownership.length; ++i) {
			float negativSum = 0;
			for(int j = 0; j < count.length; ++j) {
				if(i != j) {
					negativSum += count[j];
				}
			}
			ownership[i] += (count[i] - negativSum) * GameConstants.MINE_CAPTURING_PER_TICK;
		}
		
		sum = 0;
		for(float owner : ownership) {
			sum += owner;
		}
		for(int i = 0; i < count.length; ++i) {
			ownership[i] /= sum;
		}
		
		for(int i = 0; i < ownership.length; ++i) {
			ownership[i] = Math.min(1, Math.max(0, ownership[i]));
		}
	}
}
