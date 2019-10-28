package Game.Info;

import Game.Logic.Base;
import Game.Logic.Vector;

/**
 * Info object for Base.
 * @author Manuel Liebchen 
 *
 */
public final class BaseInfo {
	private Base base;
	
	public BaseInfo(Base base) {
		this.base = base;
	}
	
	public PlayerInfo getOwner() {
		return base.getOwner().getPlayerInfo();
	}
	
	/**
	 * Returns the Position of the player.
	 * @return This players BaseInfo.
	 */
	public Vector getPosition() {
		return new Vector(base.getPosition());
	}
	
	public int getHP() {
		return base.getHP();
	}
}
