package de.acagamics.crushingrocks.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.acagamics.crushingrocks.GameProperties;
import de.acagamics.framework.types.Vec2f;

/**
 * A map has a certain size and contains items like coins.
 * 
 * @author Jan-Cord Gerken (jancord@acagamics.de)
 *
 */
public final class Map {

	private List<Base> bases;
	private List<Mine> mines;
	private List<Player> players;

	private Random random;

	Map(Random random, List<Player> player) {
		this.random = random;

		bases = new ArrayList<>(player.size());
		this.players = player;

		for (int i = 0; i < player.size(); i++) {
			bases.add(new Base(player.get(i), GameProperties.get().getPlayerBasePosition().get(i)));
			this.players.get(i).setBase(bases.get(i));
		}

		List<Vec2f> minePositions = new ArrayList<>(GameProperties.get().getNumberOfMines());
		mines = new ArrayList<>(GameProperties.get().getNumberOfMines());
		boolean overlap = false;
		do {
			overlap = false;
			minePositions.clear();
			for (int i = 0; i < GameProperties.get().getNumberOfMines(); ++i) {
				minePositions.add(new Vec2f(this.random.nextFloat() * 2 - 1, this.random.nextFloat() * 2 - 1));
			}
		
			// Procesdureal Positioning
			for (int i = 0; i < 5; ++i) {
				float distSum = 0;
				float[] dists = new float[player.size()];
				// Data aquisition
				for (int j = 0; j < GameProperties.get().getNumberOfMines(); ++j) {
					for (int k = 0; k < bases.size(); ++k) {
						float dist = bases.get(k).getPosition().distance(minePositions.get(j));
						dists[k] += dist;
						distSum += dist;
					}
				}
	
				// Updating
				for (int j = 0; j < GameProperties.get().getNumberOfMines(); ++j) {
					Vec2f aditor = new Vec2f();
					Vec2f baseVector = new Vec2f();
					for (int k = 0; k < bases.size(); ++k) {
						Vec2f singleBaseVector = bases.get(k).getPosition().sub(minePositions.get(j));
						aditor = aditor.add(singleBaseVector.mult(GameProperties.get().getNumberOfMines() / dists[k]));
						baseVector = baseVector.add(singleBaseVector);
					}
					aditor = aditor.add(baseVector.mult(GameProperties.get().getNumberOfMines() / distSum));
					//0.765169, der Durschnitswert der Entfernurng von allen Punkten im Einheitsquadrat zu einer Ecke
					minePositions.set(j, minePositions.get(j).add(aditor.mult(0.765169f)));
				}
			}
			for(Vec2f mineposition : minePositions) {
				if(mineposition.getX() > GameProperties.get().getMapRadius() || mineposition.getX() < -GameProperties.get().getMapRadius() || mineposition.getY() > GameProperties.get().getMapRadius() || mineposition.getY() < -GameProperties.get().getMapRadius()) {
					overlap = true;
					break;
				}
				for(Vec2f minepositiontwo : minePositions) {
					if(mineposition.distance(minepositiontwo) < GameProperties.EPSILON) {
						continue;
					}
					if(mineposition.distance(minepositiontwo) < 2 * GameProperties.get().getMineRadius()) {
						overlap = true;
						break;
					}
				}
				for(Base base : bases) {
					if(mineposition.distance(base.getPosition()) < GameProperties.get().getMineRadius() + GameProperties.get().getBaseRadius()) {
						overlap = true;
						break;
					}
				}
				if(overlap) {
					break;
				}
			}
		} while(overlap);

		for (int i = 0; i < GameProperties.get().getNumberOfMines(); ++i) {
			mines.add(new Mine(minePositions.get(i), i, player.size()));
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
		return new ArrayList<>(players);
	}
	
	/**
	 * Get all Units on the map.
	 * @return List of Units.
	 */
	public List<Unit> getAllUnits() {
		List<Unit> allUnits = new ArrayList<>();
		for(Player player : players) {
			allUnits.addAll(player.getUnits());
		}
		return allUnits;
	}
}
