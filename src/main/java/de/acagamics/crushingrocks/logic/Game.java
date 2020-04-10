package de.acagamics.crushingrocks.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.acagamics.crushingrocks.GameProperties;
import de.acagamics.crushingrocks.GameStatistic;
import de.acagamics.crushingrocks.RenderingProperties;
import de.acagamics.crushingrocks.controller.IPlayerController;
import de.acagamics.framework.types.MatchSettings;
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

		RenderingProperties renderingProperties = ResourceManager.getInstance().loadProperties(RenderingProperties.class);
		players = new ArrayList<>(playerController.size());
		for (int i = 0; i < playerController.size(); ++i) {
			players.add(new Player(playerController.get(i),
					renderingProperties.getPlayerColors().get(i),
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
	 * Perform a single step in the game. Updates Players and Map.
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

		// Update Player.
		for (Player player : players) {
			player.executeOrders(map);
		}

		map.update();

		GameStatistic statistic = null;
		for (Base base : map.getBases()) {
			if (base.getHP() <= 0) {
				statistic = new GameStatistic(new ArrayList<Player>(players));
				break;
			}
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
