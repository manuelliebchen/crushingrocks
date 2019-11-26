package de.acagamics.data;

import java.util.ArrayList;
import java.util.List;

import de.acagamics.constants.GameConstants;
import de.acagamics.game.logic.Player;

public final class GameStatistic {
	
	List<Player> players;
	boolean isDraw = false;
	
	public GameStatistic(List<Player> players) {
		boolean hasWon = false;
		for(Player player : players) {
			hasWon |= player.getBase().getHP() <= 0;
		}
		if(hasWon) {
			players.sort( (p1, p2) -> p2.getBase().getHP() - p1.getBase().getHP());
		} else {
			players.sort( (p1, p2) -> p2.getScore() - p1.getScore());
		}
		this.players = players;
		isDraw = !hasWon;
	}
	
	public boolean isDraw() {
		return isDraw;
	}
	
	public String getSitesString(int playerID) {
		return String.valueOf(GameConstants.SITES.values()[playerID]);
	}
	
	public String getNameString(int playerID) {
		return String.valueOf(players.get(playerID).getName());
	}
	
	public String getScoreString(int playerID) {
		return String.valueOf(players.get(playerID).getScore());
	}

	public String getBaseHPString(int playerID) {
		return String.valueOf(players.get(playerID).getBase().getHP());
	}

	public List<Integer> getPlayerIDs() {
		List<Integer> playerIDs = new ArrayList<>(players.size());
		for(Player player : players) {
			playerIDs.add(player.getPlayerID());
		}
		return playerIDs;
	}
}
