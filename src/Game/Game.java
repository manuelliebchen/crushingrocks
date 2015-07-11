package Game;

import java.util.ArrayList;

import Game.Logic.Coin;
import Game.Logic.Map;
import Game.Logic.Player;

/**
 * MainClass for the GameSide. EntrancePoint for communication with ClientSide.
 * @author Jan-Cord Gerken (jancord@acagamics.de)
 * 
 */
public class Game {
	
	Map map;
	ArrayList<Player> players;

	/**When a player moves out of the map, he gets penaltyPoints*/
	final float OUT_OF_WORLD_PENALTY = -100F;
	
	public Game(){
		players = new ArrayList<Player>();
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
