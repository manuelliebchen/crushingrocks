package de.acagamics.crushingrocks;

import java.util.ArrayList;
import java.util.List;

import de.acagamics.crushingrocks.logic.Player;

public final class GameStatistic {
	
	List<Player> players;
	boolean isDraw = false;
	
	public GameStatistic(List<Player> playersOriginal) {
		boolean hasWon = false;
		this.players = new ArrayList<>(playersOriginal);
		for(Player player : players) {
			hasWon |= player.getBase().getHP() <= 0;
		}
		if(hasWon) {
			players.sort( (p1, p2) -> p2.getBase().getHP() - p1.getBase().getHP());
		} else {
			players.sort( (p1, p2) -> p2.getScore() - p1.getScore());
		}
		isDraw = !hasWon;
	}
	
	public int getPlayerAmount() {
		return players.size();
	}
	
	public boolean isDraw() {
		return isDraw;
	}
	
	public String getSitesString(int playerID) {
		return String.valueOf(GameProperties.SITES.values()[players.get(playerID).getPlayerID()]);
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
}