package de.acagamics.game.logic;

import java.util.List;
import java.util.stream.Collectors;

import de.acagamics.constants.GameConstants;
import de.acagamics.game.types.Vector;

/**
 * A Class for the Base of a Player.
 * 
 * @author Manuel Liebchen
 */
public final class Base {
	private Vector position;
	private Player owner;
	private int hp;

	/**
	 * Constructor that takes the owner and the position.
	 * 
	 * @param owner
	 * @param position
	 */
	Base(Player owner, Vector position) {
		this.owner = owner;
		this.position = position;
		hp = GameConstants.INITIAL_BASE_HP;
	}

	/**
	 * Get the Owner of the Base.
	 * 
	 * @return A Player objekt of the owner.
	 */
	public Player getOwner() {
		return owner;
	}

	/**
	 * Get the position of this coin.
	 * 
	 * @return position
	 */
	public Vector getPosition() {
		return position.copy();
	}

	/**
	 * @return HP of the Base
	 */
	public int getHP() {
		return hp;
	}

	void update(List<Unit> allUnits) {
		List<Unit> unitsInBaseRadius = allUnits.stream().filter(
				(u) -> position.distance(u.getPosition()) < GameConstants.BASE_RADIUS + GameConstants.UNIT_RADIUS && u.getOwner() != owner)
				.collect(Collectors.toList());
		int unitsInAttackRadius = unitsInBaseRadius.stream().filter(
				(u) -> position.distance(u.getPosition()) < GameConstants.BASE_RADIUS + GameConstants.UNIT_RADIUS)
				.toArray().length;

		for (int i = 0; i < unitsInBaseRadius.size(); i++) {
			unitsInBaseRadius.get(i).attackBy(GameConstants.BASE_DAMAGE);
		}

		hp -= unitsInAttackRadius * GameConstants.UNIT_DAMAGE;
		hp = Math.max(hp, 0);
	}
}
