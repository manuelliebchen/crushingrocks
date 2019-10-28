package Game.Info;

import Game.Logic.Unit;
import Game.Logic.Vector;

/**
 * Info object for Unit.
 * @author Manuel Liebchen 
 *
 */
public final class UnitInfo {
	private Unit unit;
	
	public UnitInfo(Unit unit) {
		this.unit = unit;
	}
	
	public PlayerInfo getOwner() {
		return unit.getOwner().getPlayerInfo();
	}
	
	/**
	 * Returns the Position of the player.
	 * @return This players BaseInfo.
	 */
	public Vector getPosition() {
		return new Vector(unit.getPosition());
	}
	
	public int getHP() {
		return unit.getHP();
	}
}
