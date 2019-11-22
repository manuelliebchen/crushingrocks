package de.acagamics.game.logic;

import java.util.List;

import de.acagamics.constants.GameConstants;
import de.acagamics.game.controller.IPlayerController;
import de.acagamics.game.types.Vector;

/**
 * Unit of the Player to be controlled.
 * @author Manuel Liebchen
 */
public final class Unit {

	private Vector position;
	private Player owner;
	private int strength;

	private Vector orderedDirection;

	Unit(int strength, Player owner, Vector position) {
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
	public Vector getPosition() {
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
	 * @param controller of the owner of the unit for verification.
	 * @param direction in witch the unit should moves.
	 */
	public void setOrder(IPlayerController controller, Vector direction) {
		if (controller == owner.getController()) {
			orderedDirection = direction;
		}
	}

	Vector updatePosition(List<Unit> allUnits) {
		if (orderedDirection != null) {
			if (orderedDirection.length() > GameConstants.MAX_UNIT_SPEED) {
				orderedDirection = orderedDirection.getNormalized().mult(GameConstants.MAX_UNIT_SPEED);
			}
			Vector targetPosition = position.add(orderedDirection);
			boolean hit = false;
			for (int i = 0; i < allUnits.size(); i++) {
//				if (allUnits.get(i) != this && allUnits.get(i).position.sub(targetPosition).length() < GameConstants.UNIT_BODY_RADIUS) {
//					hit = true;
//					break;
//				}
			}
			if (!hit) {
				position = targetPosition;
			}
			orderedDirection = null;
//			position = new Vector(Math.min(1, Math.max(-1, position.getX())),
//					Math.min(1, Math.max(-1, position.getY())));
		}
		return position;
	}

	void attackBy(int damage) {
		strength -= damage;
	}
	
	/**
	 * Calculates the cost of an Unit with given strength.
	 * @param strength of the unit in question
	 * @return cost of unit with given strength
	 */
	public static int getUnitCost(int strength) {
		return (int) (Math.pow(strength, GameConstants.COST_EXPONENT) *  GameConstants.COST_MULTIPIER);
	}
}
