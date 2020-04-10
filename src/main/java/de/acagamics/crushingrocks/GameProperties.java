package de.acagamics.crushingrocks;

import java.util.List;

import de.acagamics.framework.resources.ResourceManager;
import de.acagamics.framework.types.Vec2f;

/**
 * Class for various game relevant constants.
 * @author Manuel Liebchen
 */
public final class GameProperties {

	private GameProperties() {
	}

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

	public float getSpeedUp() {
		return speedUp;
	}

	public enum SITES{ YELLOW, BLUE}

	public int getMatchFrameQuantity() {
		return matchFrameQuantity;
	}

	public int getBaseHP() {
		return baseHP;
	}

	public float getBaseRadius() {
		return baseRadius;
	}

	public float getUnitRadius() {
		return unitRadius;
	}

	public float getMaxUnitSpeed() {
		return maxUnitSpeed;
	}

	public float getCostExponent() {
		return costExponent;
	}

	public int getConstMultipier() {
		return constMultipier;
	}

	public int getNumberOfMines() {
		return numberOfMines;
	}

	public int getMaxUnitsPerPlayer() {
		return maxUnitsPerPlayer;
	}

	public int getMaxUnitStrength() {
		return maxUnitStrength;
	}

	public int getHeroStrength() {
		return heroStrength;
	}

	public int getPerMineIncome() {
		return perMineIncome;
	}

	public float getMineCapturingPerFrame() {
		return mineCapturingPerFrame;
	}

	public float getMineRadius() {
		return mineRadius;
	}

	public int getInitialResources() {
		return initialResources;
	}

	public float getMapRadius() {
		return mapRadius;
	}

	public List<Vec2f> getPlayerBasePosition() {
		return playerBasePosition;
	}
}
