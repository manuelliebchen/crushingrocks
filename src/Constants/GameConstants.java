package Constants;

import Game.Types.Color;
import Game.Types.Vector;

/**
 * Class for various game relevant constants.
 * @author Manuel Liebchen
 */
public final class GameConstants {
	
	// Amounts!
	
	/**
	 * Number of mines on the map.
	 * <p>
	 * Value = {@value #NUMBER_OF_MINES}
	 */
	public static final int NUMBER_OF_MINES = 6;

	/**
	 * Maximum amount of units on can command.
	 * <p>
	 * Value = {@value #MAXIMUM_UNIT_AMOUNT}
	 */
	public static int MAXIMUM_UNIT_AMOUNT = 8;

	
	// Health Points
	
	/**
	 * Initial health points of the base.
	 * <p>
	 * Value = {@value #INITIAL_BASE_HP}
	 */
	public static final int INITIAL_BASE_HP = 1000;

	/**
	 * Initial health points of a unit.
	 * <p>
	 * Value = {@value #INITIAL_UNIT_HP}
	 */
	public static int INITIAL_UNIT_HP = 100;

	/**
	 * Base attack damage of an Unit.
	 * <p>
	 * Value = {@value #UNIT_BASE_ATTACK}
	 */
	public static int UNIT_BASE_ATTACK = 10;

	
	// Creadit Points!
	
	/**
	 * Initial creadit points a player has.
	 * <p>
	 * Value = {@value #INITIAL_CREADIT_POINTS}
	 */
	public static final int INITIAL_CREADIT_POINTS = 70;

	/**
	 * Price of a unit.
	 * <p>
	 * Value = {@value #UNIT_FEE}
	 */
	public static int UNIT_FEE = 20;

	/**
	 * Income per owned mine per tick.
	 * <p>
	 * Value = {@value #PER_MINE_INCOME}
	 */
	public static int PER_MINE_INCOME = 1;
	
	
	// Speed!
	
	/**
	 * Maximal per step moving speed of a unit.
	 * <p>
	 * Value = {@value #MAX_PLAYER_SPEED} 
	 */
	public static final float MAX_UNIT_SPEED = 0.01f;
	
	
	// Radii!
	
	/**
	 * Radius of the in game map.
	 * <p>
	 * Value = {@value #MAP_RADIUS}
	 */
	public static final float MAP_RADIUS = 1f;

	/**
	 * Attack radius of the Base.
	 * <p>
	 * Value = {@value #BASE_RADIUS}
	 */
	public static float BASE_RADIUS = 0.05f;

	/**
	 * Caput radius for mines.
	 * <p>
	 * Value = {@value #MINE_RADIUS}
	 */
	public static float MINE_RADIUS = 0.025f;

	/**
	 * Radius a unit can attack.
	 * <p>
	 * Value = {@value #UNIT_RADIUS}
	 */
	public static float UNIT_RADIUS = 0.0125f;


	/**
	 * Types of units.
	 * <p>
	 * Value = {@value #UNIT_TYPE}
	 */
	public enum UNIT_TYPE{ RED, GREEN, BLUE};
	
	public static Color[] PLAYER_COLORS = new Color[]{new Color(0.925f, 0.741f, 0.192f), new Color(0.18f, 0.314f, 0.62f), new Color(0.467f, 0.824f, 0.173f), new Color(0.60f, 0.125f, 0.60f)};
	public static Vector[] PLAYER_BASE_POSITION = new Vector[]{new Vector(-1, 0), new Vector(1,0), new Vector(0,-1), new Vector(0,1)};
}
