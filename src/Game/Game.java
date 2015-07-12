package Game;

import java.util.ArrayList;
import java.util.List;

import Game.Controller.IPlayerController;
import Game.Logic.Coin;
import Game.Logic.Map;
import Game.Logic.Player;

/**
 * MainClass for the GameSide. EntrancePoint for communication with ClientSide.
 * @author Jan-Cord Gerken (jancord@acagamics.de)
 * @author Andreas Reich (andreas@acagamics.de)
 * 
 */
public class Game {
	private Map map;
	private Player[] players;

	/** When a player moves out of the map, he gets penaltyPoints. */
	final float OUT_OF_WORLD_PENALTY = -100F;
	
	/**
	 * Creates a new map with the given player controller and a map.
	 * @param playerController A list of player controller.
	 */
	public Game(List<IPlayerController> playerController){
		assert(playerController != null);

		players = new Player[playerController.size()];
		for(int i=0; i<playerController.size(); ++i) {
			assert(playerController.get(i) != null);
			players[i] = new Player(playerController.get(i));
		}
		
		map = new Map();
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
		if(index < players.length)
			return players[index];
		else
			return null;
	}
	
	/**
	 * Returns the number of players.
	 * Note that the players may already be dead.
	 * @return Player count.
	 */
	public int getNumPlayers(int index) {
		return players.length;
	}
	
	/**
	 * Perform a single step in the gameLogic. Updates Players and Map.
	 */
	public void tick(){
		
		ArrayList<Coin> cachedCoinsToDelete = new ArrayList<Coin>();
		
		for(Player player : players){
			
			// update players
			player.update();
			
			// check if player falls of the map
			if (player.getPosition().lengthSqr() > map.getRadius() * map.getRadius())
			{
				// penalty-points
				player.addScore(OUT_OF_WORLD_PENALTY);
				
				// reset player
				player.resetPosition();
			}
			
			// collect coins. Btw. if two players collect the coin at the same time, both get the points
			// because coin is deleted later
			for(Coin coin : map.getCoins()){
				if(player.getPosition().distance(coin.getPosition()) < player.getRadius() + coin.getRadius()){
					player.addScore(coin.getValue());
					cachedCoinsToDelete.add(coin);
				}
			}
		}
		
		// remove collected coins
		for(Coin coin : cachedCoinsToDelete){
			map.getCoins().remove(coin);
		}
	}
}
