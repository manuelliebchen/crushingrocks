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

		List<Vec2f> minePositions = generateMinePositions();
		mines = new ArrayList<>(GameProperties.get().getNumberOfMines());
		for (int i = 0; i < GameProperties.get().getNumberOfMines(); ++i) {
			mines.add(new Mine(minePositions.get(i), i, player.size()));
		}
		mines.sort((m, n) -> (int) (1000 * (m.getPosition().getY() - n.getPosition().getY())));
	}

	private List<Vec2f> generateMinePositions() {
		List<Vec2f> minePositions = new ArrayList<>(GameProperties.get().getNumberOfMines());
		boolean overlap;
		do {
			overlap = false;
			minePositions.clear();
			for (int i = 0; i < GameProperties.get().getNumberOfMines(); ++i) {
				minePositions.add(new Vec2f(this.random.nextFloat() * 2 - 1, this.random.nextFloat() * 2 - 1));
			}

			// Procesdureal Positioning
			for (int i = 0; i < 5; ++i) {
				generationStep(minePositions);
			}

			for(Vec2f mineposition : minePositions) {
				overlap |= checkPosition(mineposition, minePositions);
			}
		} while(overlap);
		return minePositions;
	}

	private List<Vec2f> generationStep(List<Vec2f> minePositions){
		float distSum = 0;
		float[] dists = new float[players.size()];
		// Data aquisition
		for (int j = 0; j < GameProperties.get().getNumberOfMines(); ++j) {
			for (int k = 0; k < bases.size(); ++k) {
				float dist = bases.get(k).getPosition().distance(minePositions.get(j));
				dists[k] += dist;
				distSum += dist;
			}
		}
		if(Math.abs(distSum) < GameProperties.EPSILON){
			return minePositions;
		}

		// Updating
		for (int j = 0; j < GameProperties.get().getNumberOfMines(); ++j) {
			Vec2f aditor = new Vec2f();
			Vec2f baseVector = new Vec2f();
			for (int k = 0; k < bases.size(); ++k) {
				if(Math.abs(dists[k]) < GameProperties.EPSILON){
					return minePositions;
				}
				Vec2f singleBaseVector = bases.get(k).getPosition().sub(minePositions.get(j));
				aditor = aditor.add(singleBaseVector.mult(GameProperties.get().getNumberOfMines() / dists[k]));
				baseVector = baseVector.add(singleBaseVector);
			}

			aditor = aditor.add(baseVector.mult(GameProperties.get().getNumberOfMines() / distSum)); //NOSONAR Sonar doesn't get that distSum can't be zero.
			//0.765169, der Durschnitswert der Entfernurng von allen Punkten im Einheitsquadrat zu einer Ecke
			minePositions.set(j, minePositions.get(j).add(aditor.mult(0.765169f)));
		}
		return minePositions;
	}

	private boolean checkPosition(Vec2f mineposition, List<Vec2f> minePositions) {
		if(mineposition.getX() > GameProperties.get().getMapRadius() || mineposition.getX() < -GameProperties.get().getMapRadius() || mineposition.getY() > GameProperties.get().getMapRadius() || mineposition.getY() < -GameProperties.get().getMapRadius()) {
			return true;
		}
		for(Vec2f minepositiontwo : minePositions) {
			if(mineposition.distance(minepositiontwo) < GameProperties.EPSILON) {
				continue;
			}
			if(mineposition.distance(minepositiontwo) < 2 * GameProperties.get().getMineRadius()) {
				return true;
			}
		}
		for(Base base : bases) {
			if(mineposition.distance(base.getPosition()) < GameProperties.get().getMineRadius() + GameProperties.get().getBaseRadius()) {
				return true;
			}
		}
		return false;
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
