package de.acagamics.crushingrocks.logic;

import java.util.ArrayList;
import java.util.List;

import de.acagamics.crushingrocks.GameProperties;
import de.acagamics.crushingrocks.controller.IPlayerController;
import de.acagamics.framework.types.Student;
import de.acagamics.framework.types.Vec2f;
import javafx.scene.paint.Color;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

	private Color color;
	private int playerID;

	private Base base;
	private List<Unit> units;

	private int unitCreationOrder;

	Player(IPlayerController controller, Student student, Color color, int playerID) {
		this.controller = controller;
		this.student = student;
		this.color = color;
		this.playerID = playerID;
		units = new ArrayList<>(GameProperties.get().getMaxUnitsPerPlayer());
		creditPoints = GameProperties.get().getInitialResources();
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

		// Think.
		try {
			controller.think(mapInfo, this, enemyInfos);
		} catch (Exception e) {
			LOG.error(String.format("%s through an unhandled exception!",controller.getClass().getSimpleName()));
			LOG.error(e.toString());
			LOG.error(e.getStackTrace()[0].toString());
		}

		int cost = Unit.getUnitCost(unitCreationOrder);
		if (unitCreationOrder > 0 && creditPoints >= cost
				&& units.size() <= GameProperties.get().getMaxUnitsPerPlayer()) {
			units.add(new Unit(unitCreationOrder, this, base.getPosition()));
			creditPoints -= cost;
		}
		unitCreationOrder = 0;

		for (Mine mine : mapInfo.getMines()) {
			creditPoints += mine.getOwnership()[playerID] * GameProperties.get().getPerMineIncome();
			score += mine.getOwnership()[playerID] * GameProperties.get().getPerMineIncome();
		}
	}

	IPlayerController getController() {
		return controller;
	}

	Student getStudent() {return student;}

	void removeDeath() {
		units.removeIf(u -> u.getStrength() <= 0);
	}

	/**
	 * Sets the order of which unit should be createt next frame.
	 * 
	 * @param controller of the owner of the player. This is mostly just 'this'.
	 * @param strength   of the unit to be created
	 * @return the cost of the unit. Zero if none created.
	 */
	public int setUnitCreationOrder(IPlayerController controller, int strength) {
		if (controller == this.controller && strength > 0 && strength <= 3) {
			int cost = Unit.getUnitCost(strength);
			if (creditPoints >= cost) {
				unitCreationOrder = strength;
				return cost;
			}
		}
		return 0;
	}

	/**
	 * Sets the order for all units of the player for in current frame.
	 * 
	 * @param controller of the owner of the unit for verification.
	 * @param position   to which the unit should moves.
	 */
	public void setAllUnitsOrder(IPlayerController controller, Vec2f position) {
		for (Unit unit : units) {
			unit.setOrder(controller, position.sub(unit.getPosition()));
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
	 * @return score
	 */
	public int getCreditPoints() {
		return creditPoints;
	}

	/**
	 * @return the score.
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
	 * @return the color of the player.
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * @return a list of all units the player has under his controll.
	 */
	public List<Unit> getUnits() {
		return new ArrayList<>(units);
	}

	/**
	 * @return the name of the player.s
	 */
	public String getName() {
		return controller.getClass().getSimpleName();
	}
}
