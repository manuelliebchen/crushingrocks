package Game.Logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Constants.GameConstants;
import Game.Types.Vector;

/**
 * A map has a certain size and contains items like coins.
 * 
 * @author Jan-Cord Gerken (jancord@acagamics.de)
 *
 */
public class Map {

	private List<Base> bases;
	private List<Mine> mines;
	private List<Player> player;

	private Random random;

	Map(Random random, List<Player> player) {
		this.random = random;

		bases = new ArrayList<>(player.size());
		this.player = player;

		for (int i = 0; i < player.size(); i++) {
			bases.add(new Base(player.get(i), GameConstants.PLAYER_BASE_POSITION[i]));
			this.player.get(i).setBase(bases.get(i));
		}

		List<Vector> minePositions = new ArrayList<>(GameConstants.NUMBER_OF_MINES);;
		mines = new ArrayList<>(GameConstants.NUMBER_OF_MINES);
		boolean overlap = false;
		do {
			overlap = false;
			minePositions.clear();
			for (int i = 0; i < GameConstants.NUMBER_OF_MINES; ++i) {
				minePositions.add(new Vector(this.random.nextFloat() * 2 - 1, this.random.nextFloat() * 2 - 1));
			}
		
			// Procesdureal Positioning
			for (int i = 0; i < 5; ++i) {
				float distSum = 0;
				float[] dists = new float[player.size()];
				// Data aquisition
				for (int j = 0; j < GameConstants.NUMBER_OF_MINES; ++j) {
					for (int k = 0; k < bases.size(); ++k) {
						float dist = bases.get(k).getPosition().distance(minePositions.get(j));
						dists[k] += dist;
						distSum += dist;
					}
				}
	
				// Updating
				for (int j = 0; j < GameConstants.NUMBER_OF_MINES; ++j) {
					Vector aditor = new Vector();
					Vector baseVector = new Vector();
					for (int k = 0; k < bases.size(); ++k) {
						Vector singleBaseVector = bases.get(k).getPosition().sub(minePositions.get(j));
						aditor = aditor.add(singleBaseVector.mult(GameConstants.NUMBER_OF_MINES / dists[k]));
						baseVector = baseVector.add(singleBaseVector);
					}
					aditor = aditor.add(baseVector.mult(GameConstants.NUMBER_OF_MINES / distSum));
					//0.765169, der Durschnitswert der Entfernurng von allen Punkten im Einheitsquadrat zu einer Ecke
					minePositions.set(j, minePositions.get(j).add(aditor.mult(0.765169f)));
				}
			}
			for(Vector mineposition : minePositions) {
				if(mineposition.getX() > GameConstants.MAP_RADIUS || mineposition.getX() < -GameConstants.MAP_RADIUS || mineposition.getY() > GameConstants.MAP_RADIUS || mineposition.getY() < -GameConstants.MAP_RADIUS) {
					overlap = true;
					break;
				}
				for(Vector minepositiontwo : minePositions) {
					if(mineposition.distance(minepositiontwo) < GameConstants.EPSILON) {
						continue;
					}
					if(mineposition.distance(minepositiontwo) < 2 * GameConstants.MINE_RADIUS) {
						overlap = true;
						break;
					}
				}
				for(Base base : bases) {
					if(mineposition.distance(base.getPosition()) < GameConstants.MINE_RADIUS + GameConstants.BASE_RADIUS) {
						overlap = true;
						break;
					}
				}
				if(overlap) {
					break;
				}
			}
		} while(overlap);

		for (int i = 0; i < GameConstants.NUMBER_OF_MINES; ++i) {
			mines.add(new Mine(minePositions.get(i), player.size()));
		}
		mines.sort((m, n) -> (int) (1000 * (m.getPosition().getY() - n.getPosition().getY())));
	}

	/**
	 * Get all bases in this map.
	 * 
	 * @return bases in this map
	 */
	public List<Base> getBases() {
		return new ArrayList<>(bases);
	}

	/**
	 * Get all mines in this map.
	 * 
	 * @return mines in this map
	 */
	public List<Mine> getMines() {
		return new ArrayList<>(mines);
	}

	/**
	 * Get all players in this map.
	 * 
	 * @return players in this map
	 */
	public List<Player> getPlayers() {
		return new ArrayList<>(player);
	}
}
