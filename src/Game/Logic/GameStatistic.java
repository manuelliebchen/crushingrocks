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
			construction += String.format("%s %4d\n", player.getController().getName(), player.getBase().getHP());
		}
		return construction;
	}
}
