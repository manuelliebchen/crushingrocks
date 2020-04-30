package de.acagamics.crushingrocks.logic;

import de.acagamics.crushingrocks.GameProperties;
import de.acagamics.crushingrocks.controller.IPlayerController;
import de.acagamics.framework.resources.ResourceManager;
import de.acagamics.framework.types.Vec2f;
import de.acagamics.framework.ui.interfaces.GameObject;

/**
 * Unit of the Player to be controlled.
 * 
 * @author Manuel Liebchen
 */
public final class Unit extends GameObject {

	private Player owner;
	private int strength;
	private boolean isHero;

	private int speedup;

	private Vec2f orderedDirection;

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
	 * 
	 * @param controller of the owner of the unit for verification.
	 * @param direction  in witch the unit should moves.
	 */
	public void setOrder(IPlayerController controller, Vec2f direction) {
		if (controller == owner.getController()) {
			orderedDirection = direction;
		}
	}

	public boolean isWalkingRight() {
		return walkingRight;
	}

	Vec2f updatePosition(Map mapinfo) {
		if (orderedDirection != null) {
			float currentMaxSpeed = GameProperties.get().getMaxUnitSpeed(speedup);
			if (orderedDirection.length() > currentMaxSpeed) {
				orderedDirection = orderedDirection.getNormalized().mult(currentMaxSpeed);
			}
			walkingRight = orderedDirection.getX() > 0;
			position = mapinfo.boundInMap(position.add(orderedDirection));

			orderedDirection = null;
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

	/**
	 * Speedup multiplier a unit has. Will be multiplied by to get actual speedup
	 * @return The speedup a unit gets.
	 */
	public int getSpeedup() {
		return speedup;
	}
}
