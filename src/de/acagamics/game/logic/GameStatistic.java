package de.acagamics.game.logic;

import java.util.List;

import de.acagamics.constants.GameConstants;

public class GameStatistic {
	
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
	
	public String getSitesString() {
		String construction = "Sites\n";
		for(Player player : players) {
			construction += String.format("%s\n", GameConstants.SITES.values()[player.getPlayerID()]);
		}
		return construction;
	}
	
	public String getNameString() {
		String construction = "Names\n";
		for(Player player : players) {
			construction += String.format("%s\n", player.getController().getClass().getCanonicalName());
		}
		return construction;
	}
	
	public String getScoreString() {
		String construction = "Score\n";
		for(Player player : players) {
			construction += String.format("%d\n", player.getScore());
		}
		return construction;
	}

	public String getBaseHPString() {
		String construction = "BaseHP\n";
		for(Player player : players) {
			construction += String.format("%d\n", player.getBase().getHP());
		}
		return construction;
	}
}
