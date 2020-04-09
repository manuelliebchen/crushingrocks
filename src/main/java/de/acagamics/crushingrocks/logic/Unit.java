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

	private Vec2f orderedDirection;

	Unit(int strength, Player owner, Vec2f position) {
		this.strength = strength;
		this.owner = owner;
		this.position = position;
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

	Vec2f updatePosition() {
		if (orderedDirection != null) {
			if (orderedDirection.length() > GameProperties.get().getMaxUnitSpeed()) {
				orderedDirection = orderedDirection.getNormalized().mult(GameProperties.get().getMaxUnitSpeed());
			}
			position = position.add(orderedDirection);
			orderedDirection = null;
			float mapradius = GameProperties.get().getMapRadius();
			if(position.getX() > mapradius) {
				position = new Vec2f(mapradius, position.getY());
			} else if(position.getX() < - mapradius){
				position = new Vec2f(-mapradius, position.getY());
			}
			if(position.getY() > mapradius) {
				position = new Vec2f(position.getX(), mapradius);
			} else if(position.getY() < - mapradius){
				position = new Vec2f(position.getX(), -mapradius);
			}
		}
		return position;
	}

	void attackBy(int damage) {
		strength -= damage;
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
