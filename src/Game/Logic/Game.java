package Game.Logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import Constants.DesignConstants;
import Constants.GameConstants;
import Game.Controller.IPlayerController;

/**
 * MainClass for the GameSide. EntrancePoint for communication with ClientSide.
 * @author Jan-Cord Gerken (jancord@acagamics.de)
 * @author Andreas Reich (andreas@acagamics.de)
 * @author Manuel Liebchen
 * 
 */
public class Game {
	private List<Player> players;
	private Map map;
	private int frames_left;

	private Random random;
	
	/**
	 * Creates a new map with the given player controller and a map.
	 * @param playerController A list of player controller.
	 */
	public Game(List<IPlayerController> playerController){
		assert(playerController != null);

		players = new ArrayList<>(playerController.size());
		for(int i=0; i<playerController.size(); ++i) {
			assert(playerController.get(i) != null);
			players.add(new Player(playerController.get(i), DesignConstants.PLAYER_COLORS[i], i));
		}
		
		this.random = new Random();
		map = new Map(this.random, players);
		
		frames_left = GameConstants.INITIAL_FRAME_AMOUNT;
	}
	
	/**
	 * Retrieves game map.
	 * @return A map. Surprise.
	 */
	public Map getMap() {
		return map;
	}
	
	/**
	 * Returns a player with a given index.
	 * @param index Index of the player to be returned. Needs to be bigger than zero.
	 * @return Player or null if the index is out of range
	 */
	public Player getPlayer(int index) {
		assert(index >= 0);
		for(Player player : players) {
			if(index == player.getPlayerID()) {
				return player;
			}
		}
		return null;
	}
	
	/**
	 * Returns the number of players.
	 * Note that the players may already be dead.
	 * @return Player count.
	 */
	public int getNumPlayers() {
		return players.size();
	}
	
	/**
	 * Returns the number of frames left.
	 * @return Frame counter.
	 */
	public int getFramesLeft() {
		return frames_left;
	}
	
	/**
	 * Perform a single step in the gameLogic. Updates Players and Map.
	 * @return 
	 */
	public GameStatistic tick(){
		--frames_left;
		if(frames_left <= 0) {
			return new GameStatistic(new ArrayList<Player>(players));
		}
		
		// Update Player.
		for(Player player : players){
			player.update(map, players);
		}

		// Summon all Units.
		List<Unit> allUnits = new ArrayList<>(GameConstants.MAX_UNITS_PER_PLAYER * 2);
		for(Player player : players) {
			allUnits.addAll(player.getUnits());	
		}
		
		// Apply orders (move Units).
		Collections.shuffle(allUnits);
		for(Unit unit : allUnits) {
			unit.updatePosition(allUnits);
		}

		// Update Mines (ownership)
		for(Mine mine : map.getMines()) {
			mine.update(players, allUnits);
		}

		// Update Bases (Attack by Units)
		for(Base base : map.getBases()) {
			base.update(allUnits);
		}

		GameStatistic statistic = null;
		for(Base base : map.getBases()) {
			if( base.getHP() <= 0) {
				if(statistic == null) {
					statistic = new GameStatistic(new ArrayList<Player>(players));
				}
			}
		}

		// Update Unit hp by attack.
		for(Unit unit : allUnits) {
			for(Unit enemyUnit : allUnits) {
				if(unit.getPosition().distance(enemyUnit.getPosition()) < GameConstants.UNIT_RADIUS && unit.getOwner() != enemyUnit.getOwner()) {
					enemyUnit.attackBy(GameConstants.UNIT_DAMAGE);
					break;
				}
			}
		}

		//Remove Death Units
		for(Player player : players) {
			player.removeDeath();	
		}
		
		return statistic;
	}
}
