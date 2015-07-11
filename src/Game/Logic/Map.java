package Game.Logic;

import java.util.ArrayList;

/**
 * A map has a certain size and contains items like coins.
 * @author Jan-Cord Gerken (jancord@acagamics.de)
 *
 */
public class Map {
	
	float radius;
	ArrayList<Coin> coins;
	
	public Map(){
		radius = 100F;
		coins = new ArrayList<Coin>();
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
}
