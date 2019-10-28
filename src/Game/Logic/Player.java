package Game.Logic;

import java.util.ArrayList;
import java.util.List;

import Game.Controller.IPlayerController;
import Game.Info.MapInfo;
import Game.Info.PlayerInfo;
import Game.Logic.GameConstants.UNIT_TYPE;

/**
 * Player is the active entity in the game, controlled by PlayerControllers.
 * @author Jan-Cord Gerken (jancord@acagamics.de)
 *
 */
public class Player {
	private IPlayerController controller;
	private int creditPoints;
	private PlayerInfo controllerInfo = new PlayerInfo(this);
	
	private Color color;
	
	private Base base;
	private List<Unit> units = new ArrayList<>(16);
	
	Player(IPlayerController controller, Color color){
		this.controller = controller;
		this.color = color;
		reset();
	}
	
	/**
	 * Reset the Player.
	 */
	void reset(){
		units = new ArrayList<>(16);
		creditPoints = 0;
	}
	
	/**
	 * TODO: add documentation (depends on PlayerController, which does not yet exist)
	 */
	void update(MapInfo mapInfo, Player[] allPlayers) {
		// Create a list with enemy player infos.
		PlayerInfo enemyInfos;
		if(allPlayers[0] == this) {
			enemyInfos = new PlayerInfo(allPlayers[1]);
		} else {
			enemyInfos = new PlayerInfo(allPlayers[0]);
		}
		
		// Think.
		// TODO: Add timer!
		UNIT_TYPE ut = controller.think(mapInfo, units, enemyInfos);
		
		for(Unit unit : units) {
			unit.updatePosition();
		}
		
		if(ut != null && creditPoints >= GameConstants.UNIT_FEE) {
			units.add(new Unit(ut, this, base.getPosition()));
		}
	}

	public PlayerInfo getPlayerInfo() {
		return controllerInfo;
	}

	/**
	 * Change the score of this player.
	 */
	void addScore(float scoreBonus){
		creditPoints += scoreBonus;
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
		return color;
	}
}
