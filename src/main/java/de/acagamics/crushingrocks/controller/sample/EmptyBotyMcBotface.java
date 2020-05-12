package de.acagamics.crushingrocks.controller.sample;

import de.acagamics.crushingrocks.controller.IPlayerController;
import de.acagamics.framework.types.Student;
import de.acagamics.crushingrocks.logic.Map;
import de.acagamics.crushingrocks.logic.Player;

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

