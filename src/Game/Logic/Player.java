package Game.Logic;

import java.util.ArrayList;
import java.util.List;

import Constants.GameConstants;
import Constants.GameConstants.UNIT_TYPE;
import Game.Controller.IPlayerController;
import Game.Types.Color;

/**
 * Player is the active entity in the game, controlled by PlayerControllers.
 * @author Jan-Cord Gerken (jancord@acagamics.de)
 *
 */
public class Player {
	private IPlayerController controller;
	private int creditPoints;
	
	private Color color;
	
	private Base base;
	private List<Unit> units;
	
	Player(IPlayerController controller, Color color){
		this.controller = controller;
		this.color = color;
		units = new ArrayList<>(16);
		creditPoints = GameConstants.INITIAL_CREADIT_POINTS;
	}
	
	void setBase(Base base) {
		this.base = base;
	}
	
	void update(Map mapInfo, List<Player> players) {
		// Create a list with enemy player infos.
		Player enemyInfos;
		if(players.get(0) == this) {
			enemyInfos = players.get(1);
		} else {
			enemyInfos = players.get(0);
		}
		
		// Think.
		UNIT_TYPE ut = null;
		try {
			ut = controller.think(mapInfo, this, enemyInfos);
		} catch (Exception e) {
			System.err.println(controller.getName() + " throu an unhandelt exeption!");
		}
		
		if(ut != null && creditPoints >= GameConstants.UNIT_FEE && units.size() <= GameConstants.MAXIMUM_UNIT_AMOUNT) {
			units.add(new Unit(ut, this, base.getPosition()));
			creditPoints -= GameConstants.UNIT_FEE;
		}
		
		for(Mine mine : mapInfo.getMines()) {
			if(mine.getOwner() == this) {
				creditPoints += GameConstants.PER_MINE_INCOME;
			}
		}
	}
	
	/**
	 * Get the score of this player.
	 * @return score
	 */
	public float getCreditPoints() {
		return creditPoints;
	}
	
	/**
	 * Get the Base of the Player.
	 * @return Base of the Player
	 */
	public Base getBase() {
		return base;
	}
	
	public Color getColor() {
		return color.copy();
	}

	public List<Unit> getUnits() {
		return new ArrayList<>(units);
	}
	
	@Override
	public int hashCode() {
		return controller.getAuthor().hashCode() + controller.getName().hashCode() + controller.getMatrikelnummer();
	}
}
