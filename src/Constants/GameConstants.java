package Constants;

import Game.Types.Color;

/**
 * Class for various game relevant constants.
 * @author Manuel Liebchen
 */
public final class GameConstants {
	/**
	 * Maximal per step moving speed of a unit.
	 * <p>
	 * Value = {@value #MAX_PLAYER_SPEED} 
	 */
	public static final float MAX_UNIT_SPEED = 0.01f;
	
	/**
	 * Radius of the in game map.
	 * <p>
	 * Value = {@value #MAP_RADIUS}
	 */
	public static final float MAP_RADIUS = 1f;
	
	/**
	 * Number of mines on the map.
	 * <p>
	 * Value = {@value #NUMBER_OF_MINES}
	 */
	public static final int NUMBER_OF_MINES = 6;

	/**
	 * Initial health points of the base.
	 * <p>
	 * Value = {@value #INITIAL_BASE_HP}
	 */
	public static final int INITIAL_BASE_HP = 1000;

	/**
	 * Types of units.
	 * <p>
	 * Value = {@value #UNIT_TYPE}
	 */
	public enum UNIT_TYPE{ RED, GREEN, BLUE};

	/**
	 * Price of a unit.
	 * <p>
	 * Value = {@value #UNIT_FEE}
	 */
	public static int UNIT_FEE = 10;

	/**
	 * Initial health points of a unit.
	 * <p>
	 * Value = {@value #INITIAL_UNIT_HP}
	 */
	public static int INITIAL_UNIT_HP = 100;
	
	public static Color[] PLAYER_COLORS = new Color[]{new Color(0.925f, 0.741f, 0.192f), new Color(0.153f, 0.404f, 0.596f)};
	
}
