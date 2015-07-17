package Game.Logic;

import java.util.ArrayList;

import Game.GameConstants;
import Game.Controller.MapInfo;

/**
 * A map has a certain size and contains items like coins.
 * @author Jan-Cord Gerken (jancord@acagamics.de)
 *
 */
public class Map {
	
	float radius;
	ArrayList<Coin> coins;
	MapInfo playerControllerInfo = new MapInfo(this);
	
	public Map(){
		radius = GameConstants.MAP_RADIUS;
		coins = new ArrayList<>();
		float stepSize = 0.1F;
		for(float t = 0; t < Math.PI * 4; t += stepSize){
			Vector pos = new Vector((float)Math.sin(t), (float)Math.cos(t));
			pos = pos.mult(radius / 2F + t / (float)Math.PI / 4F * (radius / 2F));
			coins.add(new Coin(pos));
		}
	}
	
	/**
	 * Get all coins in this map.
	 * @return coins in this map
	 */
	public ArrayList<Coin> getCoins() {
		return coins;
	}

	/**
	 * Get the mapRadius of this Map.
	 * @return mapRadius
	 */
	public float getRadius(){
		return radius;
	}
	
	/**
	 * Gets the map-info object which provides informations to player-controller.
	 * @return map info object.
	 */
	public MapInfo getPlayerControllerInfo(){
		return playerControllerInfo;
	}
}
