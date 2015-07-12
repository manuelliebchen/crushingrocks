package Game.Controller;

import Game.Logic.Coin;
import Game.Logic.Map;

/**
 * Map information for player controller.
 * @author Andreas Reich (andreas@acagamics.de)
 *
 */
public class MapInfo {
	private Map map;
	
	public MapInfo(Map map) {
		this.map = map;
	}
	
	/**
	 * Get the mapRadius.
	 * @return mapRadius
	 */
	public float getRadius() {
		return map.getRadius();
	}
	
	/**
	 * Get the number of coins that are currently on the map.
	 * @return Coin count.
	 * @see getCoin
	 */
	public int getNumCoins() {
		return map.getCoins().size();
	}
	
	/**
	 * Returns a coin with the given index.
	 * @param coinIndex Index of a coin, should be between zero and getNumCoins.
	 * @return A coin or null if the index is invalid.
	 * @see getNumCoins
	 */
	public Coin getCoin(int coinIndex) {
		if(coinIndex < map.getCoins().size()) {
			return new Coin(map.getCoins().get(coinIndex));
		} else {
			return null;
		}
	}
}
