package de.acagamics.game.controller.builtIn;

import java.util.List;
import java.util.Random;

import de.acagamics.constants.GameConstants;
import de.acagamics.game.controller.IPlayerController;
import de.acagamics.game.logic.Map;
import de.acagamics.game.logic.Mine;
import de.acagamics.game.logic.Player;
import de.acagamics.game.logic.Unit;

/**
 * Simplistic sample bot.
 * @author Manuel Liebchen
 *
 */
public class BotyMcBotface implements IPlayerController {
	Random random = new Random();
	
	int nextUnit = random.nextInt(3)+1;
	
	@Override
	public void think(Map mapInfo, Player ownPlayer, Player enemyPlayerInfo) {
		List<Mine> mines = mapInfo.getMines();
		List<Unit> units = ownPlayer.getUnits();
		mines.removeIf( m -> m.getOwnership(ownPlayer) >= 0.9f);
		if(mines.isEmpty() || ownPlayer.getUnits().size() == GameConstants.MAX_UNITS_PER_PLAYER) {
			for(Unit unit: units) {
				unit.setOrder(this, enemyPlayerInfo.getBase().getPosition().sub(unit.getPosition()));
			}
		} else {
			for(Unit unit: units) {
				mines.sort((Mine m, Mine n) -> Math.round((n.getPosition().distanceSqr(unit.getPosition()) - m.getPosition().distanceSqr(unit.getPosition())) * 100));
				unit.setOrder(this, mines.get(mines.size() -1).getPosition().sub(unit.getPosition()));
			}
		}
		ownPlayer.setUnitCreationOrder(this, nextUnit);
	}

	@Override
	public String getAuthor() {
		return "Manuel Liebchen";
	}

	@Override
	public int getMatrikelnummer() {
		return -1;
	}


}

