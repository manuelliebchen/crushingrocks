package de.acagamics.crushingrocks.logic;

import de.acagamics.crushingrocks.types.MatchSettings;
import de.acagamics.framework.simulation.GameStatistic;
import de.acagamics.framework.simulation.Simulatable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * MainClass for the GameSide. EntrancePoint for communication with ClientSide.
 */

public final class Game implements Simulatable {
	private List<Player> players;
	private Map map;
	private int framesLeft;

	private Random random;

	private GameStatistic gameStatistic;

	/**
	 * Creates a new map with the given player controller and a map.
	 * 
	 * @param settings Settings for the game.
	 */
	public Game(MatchSettings settings) {
		GameMode gamemode = settings.getMode();

		List<?> playerController = settings.getControllers();
		players = new ArrayList<>(playerController.size());
		for (int i = 0; i < playerController.size(); ++i) {
			players.add(new Player((IPlayerController) playerController.get(i),
					i));
		}

		this.random = new Random(settings.getSeed());
		map = new Map(this.random, players);
		if (gamemode == GameMode.XMAS_CHALLENGE) {
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

	public List<IPlayerController> getPlayerControllers() {
		return players.stream().map( Player::getController).collect(Collectors.toList());
	}

	public IPlayerController getPlayerControllers(int i) {
		Player p = getPlayer(i);
		if(p != null){
			return p.getController();
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
	@Override
	public GameStatistic tick() {
		if(gameStatistic != null){
			return gameStatistic;
		}
		framesLeft -= 1;
		if (framesLeft <= 0) {
			return generateStatistic();
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

		for (Base base : map.getBases()) {
			if (base.getHP() <= 0) {
				gameStatistic = generateStatistic();
				return gameStatistic;
			}
		}
		return null;
	}

	@Override
	public boolean isAlive() {
		return gameStatistic == null;
	}

	@Override
	public GameStatistic getStatistic() {
		return gameStatistic;
	}

	private GameStatistic generateStatistic(){
		boolean hasWon = false;
		for(Player player : players) {
			hasWon |= player.getBase().getHP() <= 0;
		}
		if(hasWon) {
			players.sort( (p1, p2) -> p2.getBase().getHP() - p1.getBase().getHP());
		} else {
			players.sort( (p1, p2) -> p2.getScore() - p1.getScore());
		}

		List<IPlayerController> controllers = players.stream().map(Player::getController).collect(Collectors.toList());
		return new GameStatistic(!hasWon && players.get(0).getScore() == players.get(1).getScore(), controllers);
	}
}
