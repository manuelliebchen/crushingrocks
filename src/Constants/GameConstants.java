package Constants;

import Game.Types.Vector;

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
	public static final int INITIAL_BASE_HP = 4000;

	/**
	 * Damage the base does to each unit
	 */
	public static final int BASE_DAMAGE = 1;

	/**
	 * Attack radius of the Base.
	 * <p>
	 * Value = {@value #BASE_RADIUS}
	 */
	public static final float BASE_RADIUS = 0.25f;

	/**
	 * Radius a unit can attack.
	 * <p>
	 * Value = {@value #UNIT_RADIUS}
	 */
	public static final float UNIT_RADIUS = 0.06f;

	/**
	 * Initial health points of a unit.
	 * <p>
	 * Value = {@value #INITIAL_UNIT_HP}
	 */
	public static final int INITIAL_UNIT_HP = 200;

	/**
	 * Base attack damage of an Unit.
	 * <p>
	 * Value = {@value #UNIT_DAMAGE}
	 */
	public static final int UNIT_DAMAGE = 5;

	/**
	 * Maximal per step moving speed of a unit.
	 * <p>
	 * Value = {@value #MAX_PLAYER_SPEED}
	 */
	public static final float MAX_UNIT_SPEED = 0.009f;

	/**
	 * Radius a unit can attack.
	 * <p>
	 * Value = {@value #UNIT_RADIUS}
	 */
	public static final float UNIT_SIZE = 0.05f;

	/**
	 * Price of a unit.
	 * <p>
	 * Value = {@value #UNIT_FEE}
	 */
	public static final int UNIT_FEE = 5000;

	/**
	 * Maximum amount of units one can command.
	 * <p>
	 * Value = {@value #MAX_UNITS_PER_PLAYER}
	 */
	public static final int MAX_UNITS_PER_PLAYER = 8;
	
	/**
	 * Number of mines on the map.
	 * <p>
	 * Value = {@value #NUMBER_OF_MINES}
	 */
	public static final int NUMBER_OF_MINES = 8;

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

	public static final float MINE_CAPTURING_PER_TICK = 0.01f;

	/**
	 * Capture radius for mines.
	 * <p>
	 * Value = {@value #MINE_RADIUS}
	 */
	public static final float MINE_RADIUS = 0.1f;
	
	/**
	 * Initial creadit points a player has.
	 * <p>
	 * Value = {@value #INITIAL_CREDIT_POINTS}
	 */
	public static final int INITIAL_CREDIT_POINTS = 5000;

	/**
	 * Radius of the in game map.
	 * <p>
	 * Value = {@value #MAP_RADIUS}
	 */
	public static float MAP_RADIUS = 1f;


	/**
	 * Types of units.
	 * <p>
	 * Value = {@value #UNIT_TYPE}
	 */
	public enum UNIT_TYPE{ RED, GREEN, BLUE};
	
	public static final Vector[] PLAYER_BASE_POSITION = new Vector[]{new Vector(-1, 0), new Vector(1,0)};//, new Vector(0,-1), new Vector(0,1)};

	
	public enum SITES{ YELLOW, BLUE}; 
}
