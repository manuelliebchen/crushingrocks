package de.acagamics.crushingrocks.controller.builtIn;

import java.util.List;
import java.util.Random;

import de.acagamics.crushingrocks.GameProperties;
import de.acagamics.crushingrocks.controller.IPlayerController;
import de.acagamics.crushingrocks.logic.Map;
import de.acagamics.crushingrocks.logic.Mine;
import de.acagamics.crushingrocks.logic.Player;
import de.acagamics.crushingrocks.logic.Unit;

/**
 * Simplistic sample bot.
 * @author Manuel Liebchen
 *
 */
public final class BotyMcBotface implements IPlayerController {
	Random random = new Random();
	
	int nextUnit = random.nextInt(3)+1;
	
	@Override
	public void think(Map mapInfo, Player ownPlayer, Player enemyPlayerInfo) {
		List<Mine> mines = mapInfo.getMines();
		List<Unit> units = ownPlayer.getUnits();
		mines.removeIf( m -> m.getOwnership(ownPlayer) >= 0.9f);
		if(mines.isEmpty() || ownPlayer.getUnits().size() == GameProperties.get().getMaxUnitsPerPlayer()) {
			ownPlayer.setAllUnitsOrder(this, enemyPlayerInfo.getBase().getPosition());
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

