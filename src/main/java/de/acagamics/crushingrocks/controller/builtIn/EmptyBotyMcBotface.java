package de.acagamics.crushingrocks.controller.builtIn;

import de.acagamics.crushingrocks.controller.IPlayerController;
import de.acagamics.crushingrocks.logic.Map;
import de.acagamics.crushingrocks.logic.Player;

/**
 * Simplistic sample bot.
 * @author Manuel Liebchen
 *
 */
public final class EmptyBotyMcBotface implements IPlayerController {

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

