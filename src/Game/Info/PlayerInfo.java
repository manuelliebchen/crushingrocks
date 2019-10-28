package Game.Info;

import java.util.ArrayList;
import java.util.List;

import Game.Logic.Player;

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
		return new BaseInfo(player.getBase());
	}
	
	public List<UnitInfo> getUnitInfos() {
		return new ArrayList<>();
	}
}
