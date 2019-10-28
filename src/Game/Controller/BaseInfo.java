package Game.Controller;

import Game.Logic.Player;
import Game.Logic.Vector;

/**
 * Info object for player controllers.
 * @author Manuel Liebchen 
 *
 */
public final class BaseInfo {
	private Player player;
	
	public BaseInfo(Player player) {
		this.player = player;
	}
	
	/**
	 * Returns the Position of the player.
	 * @return This players BaseInfo.
	 */
	public Vector getPosition() {
		return new Vector(player.getBase().getPosition());
	}
	
	public int getHP() {
		return player.getBase().getHP();
	}
}
