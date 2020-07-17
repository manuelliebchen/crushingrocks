package de.acagamics.crushingrocks.logic;

import de.acagamics.framework.geometry.Circle2f;
import de.acagamics.framework.geometry.Vec2f;
import de.acagamics.framework.resources.ResourceManager;
import de.acagamics.framework.simulation.UnauthorizedAccessException;
import de.acagamics.framework.ui.interfaces.GameObject;

/**
 * Unit of the Player to be controlled.
 */

public final class Unit extends GameObject {

	private Player owner;
	private int strength;
	private boolean isHero;

	private int speedup;

	private Vec2f order;

	private boolean walkingRight;

	Unit(int strength, Player owner, Vec2f position, boolean isHero) {
		super(position);
		this.strength = strength;
		this.owner = owner;
		this.isHero = isHero;
	}

	/**
	 * @return the player object of the owner.
	 */
	public Player getOwner() {
		return owner;
	}

	public float getRadius() {
		return ResourceManager.getInstance().loadProperties(GameProperties.class).getUnitRadius();
	}

	public Circle2f getBoundary() {
		return new Circle2f(position, getRadius());
	}

	/**
	 * @return strenght of this unit.
	 */
	public int getStrength() {
		return strength;
	}

	/**
	 * Whether or not a unit is a hero unit.
	 * @return Whether or not a unit is a hero unit.
	 */
	public boolean isHero() {
		return isHero;
	}

	/**
	 * Sets the order for this unit for in current frame.
	 * @param target where the unit should move.
	 */
	public void setOrder(Vec2f target) {
		if(this.owner.isLocked()) {
			throw new UnauthorizedAccessException();
		}
		order = target;
	}

	/**
	 * Returns the order of the unit.
	 * @return the order of the unit.
	 */
	public Vec2f getOrder(){
		if(this.owner.isLocked()) {
			throw new UnauthorizedAccessException();
		}
		return order;
	}

	/**
	 * Speedup multiplier a unit has. Will be multiplied by to get actual speedup
	 * @return The speedup a unit gets.
	 */
	public int getSpeedup() {
		return speedup;
	}

	/**
	 * @param strength The strength of the unit.
	 * @return Cost of a unit with given strength
	 */
	public static int getUnitCost(int strength) {
		return GameProperties.get().getUnitCost(strength);
	}

	public boolean isWalkingRight() {
		return walkingRight;
	}

	Vec2f updatePosition(Map mapinfo) {
		if (order != null) {
			Vec2f orderDirection = order.sub(position).clipLenght(GameProperties.get().getMaxUnitSpeed(speedup));
			walkingRight = orderDirection.getX() > 0;
			position = mapinfo.getBoundary().clip(position.add(orderDirection));

			if(order.distance(position) <= GameProperties.EPSILON) {
				order = null;
			}
		}
		return position;
	}

	void attack(int damage) {
		strength -= damage;
	}

	/**
	 * Increases speedup by one.
	 */
	protected void addSpeedup() {
		speedup += 1;
	}

	boolean removeIfDeath() {
		if(strength <= 0) {
			owner.remove(this);
			return true;
		}
		return false;
	}
}
