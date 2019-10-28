package Game.Logic;

import java.util.ArrayList;
import java.util.Random;

import Game.Info.MapInfo;

/**
 * A map has a certain size and contains items like coins.
 * @author Jan-Cord Gerken (jancord@acagamics.de)
 *
 */
public class Map {
	
	ArrayList<Base> bases;
	ArrayList<Mine> mines;
	MapInfo playerControllerInfo = new MapInfo(this);
	
	Random random;
	
	public Map(Random random, Player[] player){
		this.random = random;
		
		bases = new ArrayList<>(2);
		bases.add(new Base(player[0], new Vector(-1, 0)));
		bases.add(new Base(player[1], new Vector(1, 0)));
		
		mines = new ArrayList<>(GameConstants.NUMBER_OF_MINES);
		for( int i = 0; i *2< GameConstants.NUMBER_OF_MINES; ++i) {
			Vector pos = new Vector(random.nextFloat(), 2 * random.nextFloat() -1);
			mines.add(new Mine(pos.copy()));
			mines.add(new Mine(pos.mult(-1).copy()));
		}
		
	}
	

	/**
	 * Get all bases in this map.
	 * @return bases in this map
	 */
	public ArrayList<Base> getBases() {
		return bases;
	}
	
	/**
	 * Get all mines in this map.
	 * @return mines in this map
	 */
	public ArrayList<Mine> getMines() {
		return mines;
	}
	
	/**
	 * Gets the map-info object which provides informations to player-controller.
	 * @return map info object.
	 */
	public MapInfo getPlayerControllerInfo(){
		return playerControllerInfo;
	}
}
