package de.acagamics.crushingrocks;

import java.util.List;

import de.acagamics.framework.resources.ResourceManager;
import de.acagamics.framework.types.Vec2f;

/**
 * Class for various game relevant constants.
 * @author Manuel Liebchen
 */
public final class GameProperties {
	
	public static GameProperties get() {
		return ResourceManager.getInstance().loadProperties(GameProperties.class);
	}
	private GameProperties() {
	}
	
	/**
	 * Initial health points of the base.
	 */
	private int match_frame_quantity;

	/**
	 * Initial health points of the base.
	 */
	private int base_hp;

	/**
	 * Attack radius of the Base.
	 */
	private float base_radius;

	/**
	 * Radius a unit can attack.
	 */
	private float unit_radius;

	/**
	 * Maximal per step moving speed of a unit.
	 */
	private float max_unit_speed;
	
	private float cost_exponent;
	private int const_multipier;

	/**
	 * Number of mines on the map.
	 */
	private int number_of_mines = 7;
	
	/**
	 * Maximum amount of units one can command.
	 */
	private int max_units_per_player = 8;

	/**
	 * Income per owned mine per tick.
	 */
	private int per_mine_income = 10;

	/**
	 * Ticks it takes to capture a mine;
	 */

	private float mine_capturing_per_frame = 0.005f;

	/**
	 * Capture radius for mines.
	 */
	private float mine_radius = 0.15f;
	
	/**
	 * Initial creadit points a player has.
	 */
	private int initial_resources;

	/**
	 * Radius of the in game map.
	 */
	private float map_radius = 1f;


	/**
	 * Minimum distinct float difference.
	 */
	public final static float EPSILON = Float.MIN_VALUE * 100;
	
	
	private List<Vec2f> player_base_position;

	public static enum SITES{ YELLOW, BLUE}

	public int getMatchFrameQuantity() {
		return match_frame_quantity;
	}

	public int getBaseHP() {
		return base_hp;
	}

	public float getBaseRadius() {
		return base_radius;
	}

	public float getUnitRadius() {
		return unit_radius;
	}

	public float getMaxUnitSpeed() {
		return max_unit_speed;
	}

	public float getCostExponent() {
		return cost_exponent;
	}

	public int getConstMultipier() {
		return const_multipier;
	}

	public int getNumberOfMines() {
		return number_of_mines;
	}

	public int getMaxUnitsPerPlayer() {
		return max_units_per_player;
	}

	public int getPerMineIncome() {
		return per_mine_income;
	}

	public float getMineCapturingPerFrame() {
		return mine_capturing_per_frame;
	}

	public float getMineRadius() {
		return mine_radius;
	}

	public int getInitialResources() {
		return initial_resources;
	}

	public float getMapRadius() {
		return map_radius;
	}

	public List<Vec2f> getPlayerBasePosition() {
		return player_base_position;
	}; 
}
