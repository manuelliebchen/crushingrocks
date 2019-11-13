package Game.Controller.BuiltIn;

import Game.Controller.IPlayerController;
import Game.Logic.Map;
import Game.Logic.Player;

/**
 * Simplistic sample bot.
 * @author Manuel Liebchen
 *
 */
public class EmptyBot implements IPlayerController {
		
	@Override
	public String getName() {
		return "Boty McBotface";
	}

	@Override
	public String getAuthor() {
		return "Manuel Liebchen";
	}

	@Override
	public int getMatrikelnummer() {
		return -1;
	}
	
	@Override
	public void think(Map mapInfo, Player ownPlayer, Player enemyPlayerInfo) {
	}



}

