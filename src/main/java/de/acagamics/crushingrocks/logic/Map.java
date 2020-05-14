package de.acagamics.crushingrocks.logic;

import de.acagamics.framework.geometry.Box2f;
import de.acagamics.framework.geometry.Vec2f;
import de.acagamics.framework.geometry.Volume2f;

import java.util.*;
import java.util.stream.Collectors;

/**
 * A map has a certain size and contains items like coins.
 * 
 * @author Jan-Cord Gerken (jancord@acagamics.de)
 *
 */
public final class Map {

	private int playerSize;

	private List<Base> bases;
	private List<Mine> mines;
	private List<Unit> allUnits;

	private Random random;


	public Map(Random random, List<Player> players) {
		this.random = random;


		bases = new ArrayList<>(players.size());
		allUnits = new ArrayList<>();
		playerSize = players.size();

		for (int i = 0; i < playerSize; i++) {
			Base base = new Base(players.get(i), GameProperties.get().getPlayerBasePosition().get(i));
			bases.add(base);
			players.get(i).setBase(base);
			allUnits.addAll(players.get(i).getUnits());
			if(players.get(i).hasHero()){
				allUnits.add(players.get(i).getHero());
			}
		}

		List<Vec2f> minePositions = generateMinePositions();
		mines = new ArrayList<>(GameProperties.get().getNumberOfMines());
		for (int i = 0; i < GameProperties.get().getNumberOfMines(); ++i) {
			mines.add(new Mine(minePositions.get(i), i, players.size()));
		}
		mines.sort((m, n) -> m.getPosition().getY() - n.getPosition().getY()> 0 ? 1 : -1);
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
				overlap |= checkMinePosition(mineposition, minePositions);
			}
		} while(overlap);
		return minePositions;
	}

	private List<Vec2f> generationStep(List<Vec2f> minePositions){
		float distSum = 0;
		float[] dists = new float[playerSize];
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

	private boolean checkMinePosition(Vec2f mineposition, List<Vec2f> minePositions) {
		if(!getBoundary().isInside(mineposition)) {
			return true;
		}
		for(Vec2f minepositiontwo : minePositions) {
			if(mineposition.equals(minepositiontwo)) {
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
	 * Returns the income of the player.
	 * @param player of which income will be returned
	 * @return the income of the player
	 */
	public int getMineIncome(Player player){
		float income = 0;
		for (Mine mine : mines) {
			income += mine.getOwnership(player) * GameProperties.get().getPerMineIncome();
		}
		return Math.round(income);
	}
	
	/**
	 * Get all Units on the map.
	 * @return List of Units.
	 */
	public List<Unit> getAllUnits() {
		List<Unit> units = new ArrayList<>(this.allUnits);
		Collections.shuffle(units);
		return units;
	}

	/**
	 * The boundary of the map.
	 * @return the boundary of the map.
	 */
	public Volume2f getBoundary() {
		float mapRadius = GameProperties.get().getMapRadius();
		return new Box2f(new Vec2f(-mapRadius, -mapRadius), mapRadius * 2, mapRadius * 2);
	}

	void update() {
		// Apply orders (move Units).
		for (Unit unit : allUnits) {
			unit.updatePosition(this);
		}

		// Update Mines (ownership)
		for (Mine mine : this.getMines()) {
			Volume2f boudery = mine.getBoundary();
			List<Unit> units = allUnits.stream().filter(u -> boudery.isInside(u.getPosition())).collect(Collectors.toList());
			mine.update(units);
		}

		attackUnits();
		attackBases();
		allUnits.removeIf(Unit::removeIfDeath);
	}


	private void attackUnits() {
		java.util.Map<Unit, Integer> attackUnits = new HashMap<>();
		java.util.Map<Base, Integer> attackBases = new HashMap<>();

		for (Unit unit : allUnits) {
			Optional<Base> posibleBase = bases
					.stream().filter(
							b -> b.getBoundary().isInside(unit.getPosition()) && unit.getOwner() != b.getOwner())
					.findFirst();
			if(posibleBase.isPresent()){
				Base attacked = posibleBase.get();
				int newValue = unit.getStrength();
				if (attackBases.containsKey(attacked)) {
					newValue += attackBases.get(attacked);
				}
				attackBases.put(attacked, newValue);
				unit.addSpeedup();
			} else {
				Optional<Unit> posibleUnit = allUnits
						.stream().filter(
								e -> unit.getBoundary().isInside(e.getPosition()) && unit.getOwner() != e.getOwner())
						.sorted(unit.getPosition().sortDistanceTo())
						.findFirst();
				if (posibleUnit.isPresent()) {
					Unit attacked = posibleUnit.get();
					int newValue = unit.getStrength();
					if (attackUnits.containsKey(attacked)) {
						newValue += attackUnits.get(attacked);
					}
					attackUnits.put(attacked, newValue);
					unit.addSpeedup();
				}
			}
		}
		attackUnits.forEach((u, i) -> u.attack(i));
		attackBases.forEach((b, i) -> b.attack(i));
	}

	private void attackBases() {
		java.util.Map<Unit, Integer> attackUnits = new HashMap<>();
		for (Base base : bases) {
			List<Unit> unitsInRange = allUnits
					.stream().filter(
							e -> base.getBoundary().isInside(e.getPosition()) && base.getOwner() != e.getOwner())
					.collect(Collectors.toList());
			for(Unit attacked:unitsInRange) {
				int newValue = GameProperties.get().getBaseAttack();
				if (attackUnits.containsKey(attacked)) {
					newValue += attackUnits.get(attacked);
				}
				attackUnits.put(attacked, newValue);
			}
		}
		attackUnits.forEach((u, i) -> u.attack(i));
	}

	void addUnit(Unit unit) {
		allUnits.add(unit);
	}
}
