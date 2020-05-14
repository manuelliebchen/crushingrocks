package de.acagamics.crushingrocks.controllers;

import de.acagamics.crushingrocks.logic.IPlayerController;
import de.acagamics.crushingrocks.logic.Map;
import de.acagamics.crushingrocks.logic.Player;
import de.acagamics.framework.interfaces.Student;

/**
 * EvilSanta sample bot for the X-Mass challenge.
 * @author Manuel Liebchen
 *
 */
@Student(author="Manuel Liebchen", matrikelnummer = -1, name = "Santa")
public final class EvilSanta implements IPlayerController {
	
	@Override
	public void think(Map mapInfo, Player ownPlayer, Player enemyPlayerInfo) {
		// Do nothing because EvilSanta does nothing.
	}

}

