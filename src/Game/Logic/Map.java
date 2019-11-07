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
	
	private List<Base> bases;
	private List<Mine> mines;
	private List<Player> player;
	
	private Random random;
	
	Map(Random random, List<Player> player){
		this.random = random;

		int numberPlayers = GameConstants.PLAYER_BASE_POSITION.length;
		bases = new ArrayList<>(numberPlayers);
		this.player = player;

		for (int i = 0; i < numberPlayers; i++) {
			bases.add(new Base(player.get(i), GameConstants.PLAYER_BASE_POSITION[i]));
			this.player.get(i).setBase(bases.get(i));
		}
		
		mines = new ArrayList<>(GameConstants.NUMBER_OF_MINES);
		int minesPerPlayer = GameConstants.NUMBER_OF_MINES / numberPlayers;
		float baseDistance = GameConstants.PLAYER_BASE_POSITION[0].length();
		float maxDistancePerMine = baseDistance / 2f;
		float sumDistance = maxDistancePerMine * minesPerPlayer * 0.9f;
		if (baseDistance < maxDistancePerMine) {
			maxDistancePerMine = baseDistance;
		}
        // minimum * (mines - 1) == max
		float minDistancePerMine = maxDistancePerMine / (GameConstants.NUMBER_OF_MINES - 1);
		float remainingDistance = sumDistance;
		double halfPi = Math.PI/2;

		for (int j = 0; j < numberPlayers; j++) {
			Base base = bases.get(j);
			Vector startAngle = base.getPosition().rotate(halfPi + halfPi / 3);

			for (int i = 0; i < minesPerPlayer; ++i) {
				double angle = this.random.nextDouble() * Math.PI/1.5;
				float distance;
				if (i != minesPerPlayer - 1) {
					distance = this.random.nextFloat() * maxDistancePerMine;
					if (distance < minDistancePerMine) {
						distance = minDistancePerMine;
					}
					remainingDistance -= distance;
				} else {
					distance = remainingDistance;
				}
				Vector pos = base.getPosition().add(startAngle.mult(distance).rotate(angle));
				if (pos.getX() < -1 || pos.getX() > 1 || pos.getY() < -1 || pos.getY() > 1) {
					--i;
					continue;
				}
				mines.add(new Mine(pos, player.size()));
			}
		}
	}
	

	/**
	 * Get all bases in this map.
	 * @return bases in this map
	 */
	public List<Base> getBases() {
		return new ArrayList<>(bases);
	}
	
	/**
	 * Get all mines in this map.
	 * @return mines in this map
	 */
	public List<Mine> getMines() {
		return new ArrayList<>(mines);
	}
	
	/**
	 * Get all players in this map.
	 * @return players in this map
	 */
	public List<Player> getPlayers() {
		return new ArrayList<>(player);
	}
}
