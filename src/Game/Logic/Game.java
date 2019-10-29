package Game.Logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
	private Map map;
	private List<Player> players;
	private Random random;

	/** When a player moves out of the map, he gets penaltyPoints. */
	final float OUT_OF_WORLD_PENALTY = -100F;
	
	/**
	 * Creates a new map with the given player controller and a map.
	 * @param playerController A list of player controller.
	 */
	public Game(List<IPlayerController> playerController){
		assert(playerController != null);

		players = new ArrayList<>(playerController.size());
		for(int i=0; i<playerController.size(); ++i) {
			assert(playerController.get(i) != null);
			players.add(new Player(playerController.get(i), GameConstants.PLAYER_COLORS[i]));
		}
		
		this.random = new Random();
		map = new Map(this.random, players);
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
		if(index < players.size()) {
			return players.get(index);
		} else { 
			return null; 
		}
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
	 * Perform a single step in the gameLogic. Updates Players and Map.
	 */
	public void tick(){
		for(Player player : players){
			player.update(map, players);
		}
		for(Mine mine : map.getMines()) {
			mine.update(players);
		}
	}
}
