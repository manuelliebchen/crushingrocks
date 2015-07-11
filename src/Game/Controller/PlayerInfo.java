package Game.Controller;

import Game.Logic.Player;
import Game.Logic.Vector;

/**
 * Info object for player controllers.
 * @author Andreas Reich (andreas@acagamics.de)
 *
 */
public final class PlayerInfo {
	private Player player;
	
	public PlayerInfo(Player player) {
		this.player = player;
	}
	
	/**
	 * Returns the current position of the player.
	 * @return A player position.
	 */
	public Vector getPosition() {
		return new Vector(player.getPosition());
	}
	
	/**
	 * Get the radius of the player.
	 * @return radius
	 */
	public float getRadius() {
		return player.getRadius();
	}
}
