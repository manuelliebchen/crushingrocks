package Game.Logic;

import java.util.ArrayList;
import java.util.List;

import Constants.GameConstants;
import Game.Controller.IPlayerController;
import javafx.scene.paint.Color;

/**
 * Player is the active entity in the game, controlled by PlayerControllers.
 * @author Jan-Cord Gerken (jancord@acagamics.de)
 *
 */
public class Player {
	private IPlayerController controller;
	private int score;
	private int creditPoints;
	
	private Color color;
	private int playerID;
	
	private Base base;
	private List<Unit> units;
	
	private int unitCreationOrder;
	
	Player(IPlayerController controller, Color color, int playerID){
		this.controller = controller;
		this.color = color;
		this.playerID = playerID;
		units = new ArrayList<>(GameConstants.MAX_UNITS_PER_PLAYER);
		creditPoints = GameConstants.INITIAL_CREDIT_POINTS;
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
		try {
			controller.think(mapInfo, this, enemyInfos);
		} catch (Exception e) {
			System.err.println(controller.getName() + " through an unhandled exception!");
			System.err.println(e);
			for(StackTraceElement t : e.getStackTrace()) {
				System.err.println(t);
			}
		}

		int cost = Unit.getUnitCost(unitCreationOrder);
		if(unitCreationOrder > 0 && creditPoints >= cost && units.size() <= GameConstants.MAX_UNITS_PER_PLAYER) {
			units.add(new Unit(unitCreationOrder, this, base.getPosition()));
			creditPoints -= cost;
		}
		unitCreationOrder = 0;
		
		for(Mine mine : mapInfo.getMines()) {
			creditPoints += mine.getOwnership()[playerID] * GameConstants.PER_MINE_INCOME;
			score += mine.getOwnership()[playerID] * GameConstants.PER_MINE_INCOME;
		}
	}
	
	public int setUnitCreationOrder(IPlayerController controller, int strength) {
		if(controller == this.controller && strength > 0 && strength <= 3) {
			int cost = Unit.getUnitCost(strength);
			if( creditPoints >= cost) {
				unitCreationOrder = strength;
				return cost;
			}
		}
		return 0;
	}
	
	public int getPlayerID() {
		return playerID;
	}
	
	/**
	 * Get the creditPoints of this player.
	 * @return score
	 */
	public int getCreditPoints() {
		return creditPoints;
	}
	
	/**
	 * Get the score of this player.
	 * @return score
	 */
	public int getScore() {
		return score;
	}
	
	/**
	 * Get the Base of the Player.
	 * @return Base of the Player
	 */
	public Base getBase() {
		return base;
	}
	
	public Color getColor() {
		return color;
	}

	public List<Unit> getUnits() {
		return new ArrayList<>(units);
	}

	IPlayerController getController() {
		return controller;
	}

	void removeDeath() {
		units.removeIf( u -> u.getStrength() <= 0);
	}
	
	@Override
	public int hashCode() {
		return controller.getAuthor().hashCode() + controller.getName().hashCode() + controller.getMatrikelnummer();
	}
}
