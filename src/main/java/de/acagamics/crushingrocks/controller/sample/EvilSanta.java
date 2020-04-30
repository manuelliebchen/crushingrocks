package de.acagamics.crushingrocks.controller.sample;

import de.acagamics.crushingrocks.controller.IPlayerController;
import de.acagamics.framework.types.Student;
import de.acagamics.crushingrocks.logic.Map;
import de.acagamics.crushingrocks.logic.Player;

/**
 * EvilSanta sample bot for the X-Mass challenge.
 * @author Manuel Liebchen
 *
 */
@Student(name="Manuel Liebchen", matrikelnummer = -1)
public final class EvilSanta implements IPlayerController {
	
	@Override
	public void think(Map mapInfo, Player ownPlayer, Player enemyPlayerInfo) {
		// Do nothing because EmptyBotyMcBotface does nothing.
	}

}

