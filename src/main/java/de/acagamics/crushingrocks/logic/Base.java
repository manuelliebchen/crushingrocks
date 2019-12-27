package de.acagamics.crushingrocks.logic;

import java.util.List;
import java.util.stream.Collectors;

import de.acagamics.crushingrocks.GameConstants;
import de.acagamics.framework.types.Vec2f;

/**
 * A Class for the Base of a Player.
 * 
 * @author Manuel Liebchen
 */
public final class Base {
	private Vec2f position;
	private Player owner;
	private int hp;

	/**
	 * Constructor that takes the owner and the position.
	 * 
	 * @param owner
	 * @param position
	 */
	Base(Player owner, Vec2f position) {
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
	public Vec2f getPosition() {
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
