package de.acagamics.crushingrocks.logic;

import de.acagamics.crushingrocks.GameProperties;
import de.acagamics.framework.resources.ResourceManager;
import de.acagamics.framework.types.Vec2f;
import de.acagamics.framework.ui.interfaces.GameObject;

/**
 * A Class for the Base of a Player.
 * 
 * @author Manuel Liebchen
 */
public final class Base extends GameObject {
	private Player owner;
	private int hp;

	/**
	 * Constructor that takes the owner and the position.
	 * 
	 * @param owner
	 * @param position
	 */
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

	public float getRadius() {
		return ResourceManager.getInstance().loadProperties(GameProperties.class).getBaseRadius();
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
