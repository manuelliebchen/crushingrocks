package de.acagamics.crushingrocks.logic;

import de.acagamics.framework.geometry.Vec2f;
import de.acagamics.framework.resources.ResourceManager;

import java.util.List;

/**
 * Class for various game relevant constants.
 * @author Manuel Liebchen
 */
public final class GameProperties {


	private GameProperties() {
	}

	/**
	 * @return singelton instance of GameProperties
	 */
	public static GameProperties get() {
		return ResourceManager.getInstance().loadProperties(GameProperties.class);
	}
	
	/**
	 * Initial health points of the base.
	 */
	private int matchFrameQuantity;

	/**
	 * Initial health points of the base.
	 */
	private int baseHP;

	/**
	 * Attack damage a base inflicts on all enemy units in range.
	 */
	private int baseAttack;

	/**
	 * Attack radius of the Base.
	 */
	private float baseRadius;

	/**
	 * Radius a unit can attack.
	 */
	private float unitRadius;

	/**
	 * Maximal per step moving speed of a unit.
	 */
	private float maxUnitSpeed;

	/**
	 * Speedup a unit gets after a fight
	 */
	private float speedUp;
	
	private float costExponent;
	private int constMultipier;

	/**
	 * Number of mines on the map.
	 */
	private int numberOfMines = 7;
	
	/**
	 * Maximum amount of units one can command.
	 */
	private int maxUnitsPerPlayer = 8;

	private int maxUnitStrength = 10;

	private int heroStrength = 10;

	/**
	 * Income per owned mine per tick.
	 */
	private int perMineIncome = 10;

	/**
	 * Ticks it takes to capture a mine;
	 */
	private float mineCapturingPerFrame = 0.005f;

	/**
	 * Multiplier a capturing unit gets when recapturing.
	 */
	private float mineRecapturingMultiplier = 2;

	/**
	 * Capture radius for mines.
	 */
	private float mineRadius = 0.15f;
	
	/**
	 * Initial creadit points a player has.
	 */
	private int initialResources;

	/**
	 * Radius of the in game map.
	 */
	private float mapRadius = 1f;


	/**
	 * Minimum distinct float difference.
	 */
	public static final float EPSILON = Float.MIN_VALUE * 100;
	
	
	private List<Vec2f> playerBasePosition;

	/**
	 *
	 * @return attackdamage of the base
	 */
	public int getBaseAttack() {
		return baseAttack;
	}

	/**
	 *
	 * @return the mine recaptuirng mulitiplyier.
	 */
	public float getMineRecapturingMultiplier() {
		return mineRecapturingMultiplier;
	}

	public enum SITES{ YELLOW, BLUE}

	/**
	 *
	 * @return number of frames per match
	 */
	public int getMatchFrameQuantity() {
		return matchFrameQuantity;
	}

	/**
	 *
	 * @return health points of the base
	 */
	public int getBaseHP() {
		return baseHP;
	}

	/**
	 *
	 * @return the base radius
	 */
	public float getBaseRadius() {
		return baseRadius;
	}

	/**
	 *
	 * @return the unit radius
	 */
	public float getUnitRadius() {
		return unitRadius;
	}

	/**
	 *
	 * @param speedup speedup the unit has
	 * @return the maximum distance a unit can move in one frame.
	 */
	public float getMaxUnitSpeed(int speedup) {
		return maxUnitSpeed + speedup * this.speedUp;
	}

	/**
	 * Calculates the cost of an Unit with given strength.
	 *
	 * @param strength of the unit in question
	 * @return cost of unit with given strength
	 */
	public int getUnitCost(int strength) {
		if(strength == getHeroStrength()){
			strength -= 3;
		}
		return (int) ((strength + costExponent)
				* constMultipier);
	}

	/**
	 *
	 * @return number of mines on the map
	 */
	public int getNumberOfMines() {
		return numberOfMines;
	}

	/**
	 *
	 * @return the maximum amount of units one player can controll
	 */
	public int getMaxUnitsPerPlayer() {
		return maxUnitsPerPlayer;
	}

	/**
	 *
	 * @return the maximum strength a unit might have.
	 */
	public int getMaxUnitStrength() {
		return maxUnitStrength;
	}

	/**
	 *
	 * @return the strength the hero has.
	 */
	public int getHeroStrength() {
		return heroStrength;
	}

	/**
	 *
	 * @return the amount of credits one player get's when in full control of a mine.
	 */
	public int getPerMineIncome() {
		return perMineIncome;
	}

	/**
	 *
	 * @return the amount of change in ownership a unit makes per tick.
	 */
	public float getMineCapturingPerFrame() {
		return mineCapturingPerFrame;
	}

	/**
	 *
	 * @return the mine radius
	 */
	public float getMineRadius() {
		return mineRadius;
	}

	/**
	 *
	 * @return the amount of credits one has at the beginning of a game.
	 */
	public int getInitialResources() {
		return initialResources;
	}

	/**
	 *
	 * @return the size of the map.
	 */
	public float getMapRadius() {
		return mapRadius;
	}

	/**
	 *
	 * @return the position of the bases of the players.
	 */
	public List<Vec2f> getPlayerBasePosition() {
		return playerBasePosition;
	}
}
