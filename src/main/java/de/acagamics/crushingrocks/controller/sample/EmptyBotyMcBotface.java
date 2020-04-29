package de.acagamics.crushingrocks.controller.sample;

import de.acagamics.crushingrocks.controller.IPlayerController;
import de.acagamics.crushingrocks.logic.Map;
import de.acagamics.crushingrocks.logic.Player;
import de.acagamics.framework.types.Student;

/**
 * Simplistic sample bot.
 * @author Manuel Liebchen
 *
 */
@Student(name = "Manuel Liebchen", matrikelnummer = -1)
public final class EmptyBotyMcBotface implements IPlayerController {
	
	@Override
	public void think(Map mapInfo, Player ownPlayer, Player enemyPlayerInfo) {
		// It's EmptyBotyMcBotface! It does empty!
	}



}

