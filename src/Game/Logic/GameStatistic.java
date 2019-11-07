package Game.Logic;

import java.util.List;

import Constants.GameConstants;

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
			construction += String.format("%32s\n", player.getController().getName());
		}
		return construction;
	}
	
	public String getScoreString() {
		String construction = "Score\n";
		for(Player player : players) {
			construction += String.format("%16d\n", player.getScore());
		}
		return construction;
	}

	public String getBaseHPString() {
		String construction = "BaseHP\n";
		for(Player player : players) {
			construction += String.format("%16d\n", player.getBase().getHP());
		}
		return construction;
	}
	
	@Override
	public String toString() {
		String construction = "";
		for(Player player : players) {
			construction += String.format("%-32s %16d %16d\n", player.getController().getName(), player.getBase().getHP(), player.getScore());
		}
		return construction;
	}
}
