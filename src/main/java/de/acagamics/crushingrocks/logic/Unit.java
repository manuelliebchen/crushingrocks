package de.acagamics.crushingrocks.logic;

import de.acagamics.crushingrocks.GameProperties;
import de.acagamics.crushingrocks.controller.IPlayerController;
import de.acagamics.framework.types.Vec2f;

/**
 * Unit of the Player to be controlled.
 * 
 * @author Manuel Liebchen
 */
public final class Unit {

	private Vec2f position;
	private Player owner;
	private int strength;
	private int speedup;
	private boolean isHero;

	private Vec2f orderedDirection;

	Unit(int strength, Player owner, Vec2f position, boolean isHero) {
		this.strength = strength;
		this.owner = owner;
		this.position = position;
		this.isHero = isHero;
	}

	/**
	 * @return the player object of the owner.
	 */
	public Player getOwner() {
		return owner;
	}

	/**
	 * @return Position of this unit on the map.
	 */
	public Vec2f getPosition() {
		return position.copy();
	}

	/**
	 * @return strenght of this unit.
	 */
	public int getStrength() {
		return strength;
	}

	/**
	 * Whether or not a unit is a hero unit.
	 */
	public boolean isHero() {
		return isHero;
	}

	/**
	 * Sets the order for this unit for in current frame.
	 * 
	 * @param controller of the owner of the unit for verification.
	 * @param direction  in witch the unit should moves.
	 */
	public void setOrder(IPlayerController controller, Vec2f direction) {
		if (controller == owner.getController()) {
			orderedDirection = direction;
		}
	}

	Vec2f updatePosition(Map mapinfo) {
		if (orderedDirection != null) {
			float currentMaxSpeed = GameProperties.get().getMaxUnitSpeed() + speedup * GameProperties.get().getSpeedUp();
			if (orderedDirection.length() > currentMaxSpeed) {
				orderedDirection = orderedDirection.getNormalized().mult(currentMaxSpeed);
			}
			position = position.add(orderedDirection);
			orderedDirection = null;

			position = mapinfo.boundInMap(position);
		}
		return position;
	}

	void attack(int damage) {
		strength -= damage;
	}

	/**
	 * Increases speedup by one.
	 * {@link GameProperties#getSpeedUp()}
	 */
	void addSpeedup() {
		speedup += 1;
	}

	boolean removeIfDeath() {
		if(strength <= 0) {
			owner.remove(this);
			return true;
		}
		return false;
	}

	/**
	 * Speedup multiplier a unit has. Will be multiplied by {@link GameProperties#getSpeedUp()} to get actual speedup
	 * @return The speedup a unit gets. speedup >= 0
	 */
	public int getSpeedup() {
		return speedup;
	}

	/**
	 * Calculates the cost of an Unit with given strength.
	 * 
	 * @param strength of the unit in question
	 * @return cost of unit with given strength
	 */
	public static int getUnitCost(int strength) {
		return (int) (Math.pow(strength, GameProperties.get().getCostExponent())
				* GameProperties.get().getConstMultipier());
	}
}
