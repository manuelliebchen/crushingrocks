package de.acagamics.crushingrocks.logic;

import de.acagamics.framework.geometry.Vec2f;
import de.acagamics.framework.interfaces.Student;
import de.acagamics.framework.resources.ClientProperties;
import de.acagamics.framework.resources.ResourceManager;
import de.acagamics.framework.simulation.GameStatistic;
import de.acagamics.framework.simulation.UnauthorizedAccessException;
import de.acagamics.framework.ui.CliArguments;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Player is the active entity in the game, controlled by PlayerControllers.
 * 
 * @author Jan-Cord Gerken (jancord@acagamics.de)
 *
 */
public final class Player {
	private static final Logger LOG = LogManager.getLogger(Player.class.getName());

	private IPlayerController controller;
	private Student student;
	private int score;
	private int creditPoints;

	private int playerID;

	private Base base;

	private List<Unit> units;
	private int unitCreationOrder;

	private Unit hero;
	private boolean heroCreationOrder;

	private boolean lock;

	private int thinkCounter;

	Player(IPlayerController controller, int playerID) {
		this.controller = controller;
		this.student = this.controller.getClass().getAnnotation(Student.class);
		this.playerID = playerID;
		units = new ArrayList<>(GameProperties.get().getMaxUnitsPerPlayer());
		creditPoints = GameProperties.get().getInitialResources();
		this.lock = true;
	}

	void setBase(Base base) {
		this.base = base;
	}

	void update(Map mapInfo, List<Player> players) {
		// Create a list with enemy player infos.
		Player enemyInfos;
		if (players.get(0) == this) {
			enemyInfos = players.get(1);
		} else {
			enemyInfos = players.get(0);
		}

		try {
			this.lock = false;
			controller.think(mapInfo, this, enemyInfos);
			this.lock = true;
		} catch (Exception e){
			if(!ResourceManager.getInstance().loadSingleton(CliArguments.class).isQuiet()) {
				LOG.error(String.format("%s threw unhandlet exception: ", this.toString()), e);
			}
		}
		thinkCounter += 1;
	}

	void executeOrders(Map mapInfo) {
		if(heroCreationOrder && hero == null){
			int cost = GameProperties.get().getUnitCost(GameProperties.get().getHeroStrength());
			if(creditPoints >= cost) {
				hero = new Unit(GameProperties.get().getHeroStrength(), this, base.getPosition(), true);
				mapInfo.addUnit(hero);
				creditPoints -= cost;
			}
		}
		heroCreationOrder = false;

		if (unitCreationOrder > 0
				&& units.size() <= GameProperties.get().getMaxUnitsPerPlayer()) {
			int cost = GameProperties.get().getUnitCost(unitCreationOrder);
			if(creditPoints >= cost){
				Unit unit = new Unit(unitCreationOrder, this, base.getPosition(),false);
				units.add(unit);
				mapInfo.addUnit(unit);
				creditPoints -= cost;
			}
		}
		unitCreationOrder = 0;

		int income = mapInfo.getMineIncome(this);
		creditPoints += income;
		score += income;
	}

	IPlayerController getController() {
		return controller;
	}

	Student getStudent() {return student;}

	void remove(Unit unit) {
		if(unit.isHero()){
			hero = null;
		} else {
			units.remove(unit);
		}
	}

	boolean isLocked() {
		return lock;
	}

	/**
	 * The quantity the think method has been called.
	 * @return The quantity the think method has been called.
	 */
	public int getThinkCounter() {
		return thinkCounter;
	}

	/**
	 * Sets the order of which unit should be createt next frame.
	 *
	 * @param strength   of the unit to be created
	 * @return the cost of the unit. Zero if none created.
	 */
	public int setUnitCreationOrder(int strength) {
		if(this.lock) {
			throw new UnauthorizedAccessException();
		}
		if(strength > 0 && strength <= GameProperties.get().getMaxUnitStrength()) {
			int cost = GameProperties.get().getUnitCost(strength);
			if (creditPoints >= cost) {
				unitCreationOrder = strength;
				return cost;
			}
		}
		return 0;
	}

	/**
	 * Sets the order to create a Hero
	 * @return the cost of the unit. Zero if none created.
	 */
	public int setHeroCreationOrder() {
		if(this.lock) {
			throw new UnauthorizedAccessException();
		}
		int cost = GameProperties.get().getUnitCost(GameProperties.get().getHeroStrength());
		if (creditPoints >= cost) {
			heroCreationOrder = true;
			return cost;
		}
		return 0;
	}

	/**
	 * Sets the order for all units of the player for in current frame.
	 *
	 * @param position   to which the unit should moves.
	 */
	public void setAllUnitsOrder(Vec2f position) {
		if(this.lock) {
			throw new UnauthorizedAccessException();
		}
		for (Unit unit : units) {
			unit.setOrder(position);
		}
		if(hero != null) {
			hero.setOrder(position);
		}
	}

	/**
	 * @return the identifier of the player.
	 */
	public int getPlayerID() {
		return playerID;
	}

	/**
	 * Get the creditPoints of this player.
	 * 
	 * @return the creditPoints
	 */
	public int getCreditPoints() {
		return creditPoints;
	}

	/**
	 * Returns the overall score.
	 * This is only relevant for draws and is calculated by the sum of all positive incomes.
	 * @return the overall score.
	 */
	public int getScore() {
		return score;
	}

	/**
	 * @return the base of the Player.
	 */
	public Base getBase() {
		return base;
	}

	/**
	 * @return a list of all units the player has under his control.
	 */
	public List<Unit> getUnits() {
		return new ArrayList<>(units);
	}

	/**
	 * @return Whether or not the player has an hero.
	 */
	public boolean hasHero() {
		return hero != null;
	}

	/**
	 * @return Gets the players hero unit.
	 */
	public Unit getHero() {
		return hero;
	}

	/**
	 * @return the name of the player.
	 */
	public String getName() {
		return GameStatistic.getName(controller.getClass());
	}

	@Override
	public String toString() {
		return String.valueOf(GameProperties.SITES.values()[playerID]) + "\t" +
				student.author() + ":" + student.matrikelnummer() + " - " +
				getName();
	}
}
