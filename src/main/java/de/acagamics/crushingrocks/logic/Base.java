package de.acagamics.crushingrocks.logic;

import de.acagamics.framework.geometry.Circle2f;
import de.acagamics.framework.geometry.Vec2f;
import de.acagamics.framework.geometry.Volume2f;
import de.acagamics.framework.ui.interfaces.GameObject;

/**
 * A Class for the Base of a Player.
 * 
 * @author Manuel Liebchen
 */
public final class Base extends GameObject {
	private Player owner;
	private int hp;

	Base(Player owner, Vec2f position) {
		super(position);
		this.owner = owner;
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
	@Override
	public Vec2f getPosition() {
		return position.copy();
	}


	/**
	 *
	 * @return the radius of the base
	 */
	public float getRadius() {
		return GameProperties.get().getBaseRadius();
	}

	/**
	 *
	 * @return the boundary of the base. It is a Circle2f.
	 */
	public Volume2f getBoundary() {
		return new Circle2f(position, getRadius());
	}

	/**
	 * @return HP of the Base
	 */
	public int getHP() {
		return hp;
	}

	void attack(int damage) {
		hp -= damage;
		hp = Math.max(0,hp);
	}
}
