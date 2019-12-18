package de.acagamics.constants;

import de.acagamics.game.types.Vec2f;

/**
 * Class for various game relevant constants.
 * @author Manuel Liebchen
 */
public final class GameConstants {
	
	/**
	 * Initial health points of the base.
	 * <p>
	 * Value = {@value #INITIAL_BASE_HP}
	 */
	public static final int INITIAL_FRAME_AMOUNT = 5000;

	/**
	 * Initial health points of the base.
	 * <p>
	 * Value = {@value #INITIAL_BASE_HP}
	 */
	public static final int INITIAL_BASE_HP = 20;

	/**
	 * Attack radius of the Base.
	 * <p>
	 * Value = {@value #BASE_RADIUS}
	 */
	public static final float BASE_RADIUS = 0.2f;

	/**
	 * Radius a unit can attack.
	 * <p>
	 * Value = {@value #UNIT_RADIUS}
	 */
	public static final float UNIT_RADIUS = 0.1f;

	/**
	 * Maximal per step moving speed of a unit.
	 * <p>
	 * Value = {@value #MAX_UNIT_SPEED}
	 */
	public static final float MAX_UNIT_SPEED = 0.01f;

	/**
	 * Radius a unit body blocks.
	 * <p>
	 * Value = {@value #UNIT_BODY_RADIUS}
	 */
	public static final float UNIT_BODY_RADIUS = 0.01f;
	
	public static final float COST_EXPONENT = 0.725f;
	public static final int COST_MULTIPIER = 1000;

	/**
	 * Base attack damage of an Unit.
	 * <p>
	 * Value = {@value #UNIT_DAMAGE}
	 */
	public static final int UNIT_DAMAGE = 1;

	/**
	 * Damage the base does to each unit
	 */
	public static final int BASE_DAMAGE = 1;

	/**
	 * Number of mines on the map.
	 * <p>
	 * Value = {@value #NUMBER_OF_MINES}
	 */
	public static final int NUMBER_OF_MINES = 7;
	
	/**
	 * Maximum amount of units one can command.
	 * <p>
	 * Value = {@value #MAX_UNITS_PER_PLAYER}
	 */
	public static final int MAX_UNITS_PER_PLAYER = 8;

	/**
	 * Income per owned mine per tick.
	 * <p>
	 * Value = {@value #PER_MINE_INCOME}
	 */
	public static final int PER_MINE_INCOME = 10;

	/**
	 * Ticks it takes to capture a mine;
	 * <p>
	 * Value = {@value #MINE_CAPTURING_PER_TICK}
	 */

	public static final float MINE_CAPTURING_PER_TICK = 0.005f;

	/**
	 * Capture radius for mines.
	 * <p>
	 * Value = {@value #MINE_RADIUS}
	 */
	public static final float MINE_RADIUS = 0.15f;
	
	/**
	 * Initial creadit points a player has.
	 * <p>
	 * Value = {@value #INITIAL_CREDIT_POINTS}
	 */
	public static final int INITIAL_CREDIT_POINTS = (int) (COST_MULTIPIER * 2.5f);

	/**
	 * Radius of the in game map.
	 * <p>
	 * Value = {@value #MAP_RADIUS}
	 */
	public static final float MAP_RADIUS = 1f;


	/**
	 * Minimum distinct float difference.
	 * <p>
	 * Value = {@value #EPSILON}
	 */
	public static final float EPSILON = Float.MIN_VALUE * 100;
	
	
	public static final Vec2f[] PLAYER_BASE_POSITION = new Vec2f[]{new Vec2f(-1, 0), new Vec2f(1,0), new Vec2f(0,-1), new Vec2f(0,1)};

	public enum SITES{ YELLOW, BLUE, GREEN, PURPLE}; 
}
