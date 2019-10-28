package Game.Info;

import Game.Logic.Map;
import Game.Logic.Mine;

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
	 * Get the number of mines that are currently on the map.
	 * @return Mine count.
	 * @see getMine
	 */
	public int getNumMines() {
		return map.getMines().size();
	}
	
	/**
	 * Returns a mine with the given index.
	 * @param mineIndex Index of a mine, should be between zero and getNumCoins.
	 * @return A mine or null if the index is invalid.
	 * @see getNumMines
	 */
	public Mine getMine(int mineIndex) {
		if(mineIndex < map.getMines().size()) {
			return new Mine(map.getMines().get(mineIndex));
		} else {
			return null;
		}
	}
}
