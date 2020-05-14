package de.acagamics.crushingrocks.controllers;

import de.acagamics.crushingrocks.logic.IPlayerController;
import de.acagamics.crushingrocks.logic.Map;
import de.acagamics.crushingrocks.logic.Player;
import de.acagamics.framework.interfaces.Student;

/**
 * Simplistic sample bot.
 * @author Manuel Liebchen
 *
 */
@Student(author = "Manuel Liebchen", matrikelnummer = -1, name = "Empty")
public final class EmptyBotyMcBotface implements IPlayerController {
	
	@Override
	public void think(Map mapInfo, Player ownPlayer, Player enemyPlayerInfo) {
		// Do nothing because EmptyBotyMcBotface does nothing.
	}
}

