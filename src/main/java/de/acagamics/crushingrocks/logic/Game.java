package de.acagamics.crushingrocks.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import de.acagamics.crushingrocks.GameProperties;
import de.acagamics.crushingrocks.GameStatistic;
import de.acagamics.crushingrocks.RenderingProperties;
import de.acagamics.crushingrocks.controller.IPlayerController;
import de.acagamics.framework.types.MatchSettings;
import de.acagamics.framework.types.Student;
import de.acagamics.framework.resources.ResourceManager;
import de.acagamics.framework.types.MatchSettings.GAMEMODE;
import de.acagamics.framework.util.InputTracker;
import javafx.event.EventHandler;
import javafx.scene.input.InputEvent;

/**
 * MainClass for the GameSide. EntrancePoint for communication with ClientSide.
 * 
 * @author Jan-Cord Gerken (jancord@acagamics.de)
 * @author Andreas Reich (andreas@acagamics.de)
 * @author Manuel Liebchen
 * 
 */
public final class Game implements EventHandler<InputEvent> {
	private List<Player> players;
	private Map map;
	private int framesLeft;

	private InputTracker inputTracker;

	private Random random;

	/**
	 * Creates a new map with the given player controller and a map.
	 * 
	 * @param settings Settings for the game.
	 */
	public Game(MatchSettings<IPlayerController> settings) {
		List<IPlayerController> playerController = settings.getControllers();
		GAMEMODE gamemode = settings.getMode();

		inputTracker = new InputTracker();

		players = new ArrayList<>(playerController.size());
		for (int i = 0; i < playerController.size(); ++i) {
			players.add(new Player(playerController.get(i), playerController.getClass().getAnnotation(Student.class),
					ResourceManager.getInstance().loadProperties(RenderingProperties.class).getPlayerColors().get(i),
					i));
		}

		this.random = new Random();
		map = new Map(this.random, players);
		if (gamemode == GAMEMODE.XMAS_CHALLENGE) {
			List<Mine> mines = map.getMines();
			for (Mine mine : mines) {
				mine.setOwnership(new float[] { 0, 1 });
			}
		}

		framesLeft = GameProperties.get().getMatchFrameQuantity();
	}

	/**
	 * Retrieves game map.
	 * 
	 * @return A map. Surprise.
	 */
	public Map getMap() {
		return map;
	}

	/**
	 * Returns a player with a given index.
	 * 
	 * @param index Index of the player to be returned. Needs to be bigger than
	 *              zero.
	 * @return Player or null if the index is out of range
	 */
	public Player getPlayer(int index) {
		if(index >= 0) {
			for (Player player : players) {
				if (index == player.getPlayerID()) {
					return player;
				}
			}
		}
		return null;
	}

	/**
	 * Returns the number of players. Note that the players may already be dead.
	 * 
	 * @return Player count.
	 */
	public int getNumPlayers() {
		return players.size();
	}

	/**
	 * Returns the number of frames left.
	 * 
	 * @return Frame counter.
	 */
	public int getFramesLeft() {
		return framesLeft;
	}

	/**
	 * Perform a single step in the gameLogic. Updates Players and Map.
	 * 
	 * @return if the game is over it returns a gamestatistic, else null.
	 */
	public GameStatistic tick() {
		--framesLeft;
		if (framesLeft <= 0) {
			return new GameStatistic(new ArrayList<Player>(players));
		}

		// Update Player.
		for (Player player : players) {
			player.update(map, players);
		}

		// Summon all Units.
		List<Unit> allUnits = new ArrayList<>(GameProperties.get().getMaxUnitsPerPlayer() * 2);
		for (Player player : players) {
			allUnits.addAll(player.getUnits());
		}

		// Apply orders (move Units).
		Collections.shuffle(allUnits);
		for (Unit unit : allUnits) {
			unit.updatePosition();
		}

		// Update Mines (ownership)
		for (Mine mine : map.getMines()) {
			mine.update(players, allUnits);
		}

		// Update Bases (Attack by Units)
		for (Base base : map.getBases()) {
			base.update(allUnits);
		}

		GameStatistic statistic = null;
		for (Base base : map.getBases()) {
			if (base.getHP() <= 0 && statistic == null) {
				statistic = new GameStatistic(new ArrayList<Player>(players));
				break;
			}
		}

		// Update Unit hp by attack.
		java.util.Map<Unit, Integer> inflictedDamage = new HashMap<>();

		for (Unit unit : allUnits) {
			Optional<Unit> posibleAttacker = allUnits
					.stream().filter(
							e -> unit.getPosition().distance(e.getPosition()) < 2
									* GameProperties.get().getUnitRadius() && unit.getOwner() != e.getOwner())
					.sorted((e1, e2) -> e1.getPosition().distance(unit.getPosition())
							- e2.getPosition().distance(unit.getPosition()) > 0 ? 1 : -1)
					.findFirst();
			if (posibleAttacker.isPresent()) {
				if (inflictedDamage.containsKey(posibleAttacker.get())) {
					inflictedDamage.put(posibleAttacker.get(),
							inflictedDamage.get(posibleAttacker.get()) + unit.getStrength());
				} else {
					inflictedDamage.put(posibleAttacker.get(), unit.getStrength());
				}
			}
		}
		inflictedDamage.forEach((u, i) -> u.attackBy(i));

		// Remove Death Units
		for (Player player : players) {
			player.removeDeath();
		}

		inputTracker.updateTables();
		return statistic;
	}

	@Override
	public void handle(InputEvent event) {
		for (Player player : players) {
			IPlayerController controller = player.getController();
			if (controller instanceof EventHandler) {

				@SuppressWarnings("unchecked")
				EventHandler<InputEvent> handler = ((EventHandler<InputEvent>) controller);

				handler.handle(event);
			}
		}
		inputTracker.handle(event);
	}
}
