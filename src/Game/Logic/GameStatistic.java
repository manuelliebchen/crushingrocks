package Game.Logic;

import java.util.List;

public class GameStatistic {
	
	List<Player> players;
	
	public GameStatistic(List<Player> players) {
		this.players = players;
	}
	
	@Override
	public String toString() {
		players.sort( (p1, p2) -> p2.getBase().getHP() - p1.getBase().getHP());
		String construction = "";
		for(Player player : players) {
			construction += String.format("%-32s %16d %16d\n", player.getController().getName(), player.getBase().getHP(), player.getScore());
		}
		return construction;
	}
}
