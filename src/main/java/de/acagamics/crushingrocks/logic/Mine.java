package de.acagamics.crushingrocks.logic;

import java.util.List;

import de.acagamics.crushingrocks.GameProperties;
import de.acagamics.framework.resources.ResourceManager;
import de.acagamics.framework.types.Vec2f;
import de.acagamics.framework.ui.interfaces.GameObject;

/**
 * Coins can be collected by Players for increasing their score.
 * @author Manuel Liebchen
 *
 */
public final class Mine extends GameObject {
	
	private final int mineid;

	private int numberOfPlayer;
	private float[] ownership;
	
	Mine(Vec2f position, int mineid, int numberOfPlayer){
		super(position);
		this.mineid = mineid;
		this.numberOfPlayer = numberOfPlayer;
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

	public float getRadius() {
		return ResourceManager.getInstance().loadProperties(GameProperties.class).getMapRadius();
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

	void update(List<Unit> unitsInRange) {
		float[] count = new float[numberOfPlayer];

		for(Unit unit : unitsInRange) {
			count[unit.getOwner().getPlayerID()] += unit.getStrength();
		}

		float captureSpeed = GameProperties.get().getMineCapturingPerFrame();

		float avrage = 0;
		for(int i = 0; i < numberOfPlayer; ++i) {
			avrage += count[i];
			if(ownership[i] < 0.5f && count[i] > unitsInRange.size()/2){
				captureSpeed *= GameProperties.get().getMineRecapturingMultiplier();
			}
		}
		avrage /= numberOfPlayer;

		for(int i = 0; i < numberOfPlayer; ++i) {
			ownership[i] += (count[i] - avrage) * captureSpeed;
		}
		
		float sum = 0;
		for(float owner : ownership) {
			sum += owner;
		}
		if(Math.abs(sum - 1) > GameProperties.EPSILON && Math.abs(sum) > GameProperties.EPSILON){
			for(int i = 0; i < numberOfPlayer; ++i) {
				ownership[i] /= sum; //NOSONAR Cant be zero!
			}
		}
		
		for(int i = 0; i < numberOfPlayer; ++i) {
			ownership[i] = Math.min(1, Math.max(0, ownership[i]));
		}
	}
}
