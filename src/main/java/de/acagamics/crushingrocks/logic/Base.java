package de.acagamics.crushingrocks.logic;

import java.util.List;
import java.util.stream.Collectors;

import de.acagamics.crushingrocks.GameProperties;
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
		hp = GameProperties.get().getBaseHP();
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
		List<Unit> unitsInBaseRadius = allUnits.stream()
				.filter(unit -> position.distance(unit.getPosition()) < GameProperties.get().getBaseRadius()
						+ GameProperties.get().getUnitRadius() && unit.getOwner() != owner)
				.collect(Collectors.toList());
		int unitsInAttackRadius = unitsInBaseRadius.stream()
				.filter(unit -> position.distance(unit.getPosition()) < GameProperties.get().getBaseRadius()
						+ GameProperties.get().getUnitRadius())
				.mapToInt(Unit::getStrength).sum();

		for (int i = 0; i < unitsInBaseRadius.size(); i++) {
			unitsInBaseRadius.get(i).attackBy(1);
		}

		hp -= unitsInAttackRadius;
		hp = Math.max(hp, 0);
	}
}
