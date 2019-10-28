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
	 * Returns the BaseInfo of the player.
	 * @return This players BaseInfo.
	 */
	public BaseInfo getBaseInfo() {
		return new BaseInfo(player);
	}
}
