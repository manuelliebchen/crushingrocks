package Game.Logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Constants.GameConstants;
import Game.Types.Vector;

/**
 * A map has a certain size and contains items like coins.
 * @author Jan-Cord Gerken (jancord@acagamics.de)
 *
 */
public class Map {
	
	List<Base> bases;
	List<Mine> mines;
	List<Player> player;
	
	Random random;
	
	public Map(Random random, List<Player> player){
		this.random = random;
		
		bases = new ArrayList<>(2);
		this.player = player;
		
		bases.add(new Base(player.get(0), GameConstants.PLAYER_BASE_POSITION[0]));
		this.player.get(0).setBase(bases.get(0));
		
		bases.add(new Base(player.get(1), GameConstants.PLAYER_BASE_POSITION[1]));
		this.player.get(1).setBase(bases.get(1));
		
		mines = new ArrayList<>(GameConstants.NUMBER_OF_MINES);
		for( int i = 0; i *2< GameConstants.NUMBER_OF_MINES; ++i) {
			Vector pos;
			do {
				pos = new Vector(random.nextFloat(), 2 * random.nextFloat() -1);
			} while(pos.length() > 1);
			mines.add(new Mine(pos.copy()));
			mines.add(new Mine(pos.mult(-1).copy()));
		}
		
	}
	

	/**
	 * Get all bases in this map.
	 * @return bases in this map
	 */
	public List<Base> getBases() {
		return bases;
	}
	
	/**
	 * Get all mines in this map.
	 * @return mines in this map
	 */
	public List<Mine> getMines() {
		return mines;
	}
	
	/**
	 * Get all players in this map.
	 * @return players in this map
	 */
	public List<Player> getPlayers() {
		return player;
	}
}
