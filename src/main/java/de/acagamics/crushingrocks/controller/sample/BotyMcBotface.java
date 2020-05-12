package de.acagamics.crushingrocks.controller.sample;

import java.util.List;
import java.util.Random;

import de.acagamics.crushingrocks.types.GameProperties;
import de.acagamics.crushingrocks.controller.IPlayerController;
import de.acagamics.crushingrocks.logic.Map;
import de.acagamics.crushingrocks.logic.Mine;
import de.acagamics.crushingrocks.logic.Player;
import de.acagamics.crushingrocks.logic.Unit;
import de.acagamics.framework.types.Student;

/**
 * Simplistic sample bot.
 * @author Manuel Liebchen
 *
 */
@Student(author = "Manuel Liebchen", matrikelnummer = -1, name = "BotyMcBotface")
public final class BotyMcBotface implements IPlayerController {
	Random random = new Random();
	int nextBot = random.nextInt(3) + 1;
	
	@Override
	public void think(Map mapInfo, Player ownPlayer, Player enemyPlayerInfo) {
		List<Mine> mines = mapInfo.getMines();
		List<Unit> units = ownPlayer.getUnits();
		ownPlayer.setAllUnitsOrder(enemyPlayerInfo.getBase().getPosition());
		mines.removeIf( m -> m.getOwnership(ownPlayer) >= 1 - GameProperties.EPSILON);
		int ownedMines = mines.size();
		if(mines.isEmpty()) {
			ownPlayer.setAllUnitsOrder(enemyPlayerInfo.getBase().getPosition());
		} else {
			for(Unit unit : units){
				if(!mines.isEmpty()) {
					mines.sort((Mine m, Mine n) -> (n.getPosition().distanceSqr(unit.getPosition()) - m.getPosition().distanceSqr(unit.getPosition()) < 0 ? 1 : -1));
					Mine target = mines.get(0);
					unit.setOrder(target.getPosition());
					mines.remove(target);
				} else {
					unit.setOrder(enemyPlayerInfo.getBase().getPosition());
				}
			}
		}
		if(ownPlayer.hasHero()){
			Unit hero = ownPlayer.getHero();
			hero.setOrder(enemyPlayerInfo.getBase().getPosition());
		}
		if(ownedMines > GameProperties.get().getNumberOfMines() / 2 && ownPlayer.setUnitCreationOrder(nextBot) > 0)  {
			nextBot = random.nextInt(3) + 1;
		}
		ownPlayer.setHeroCreationOrder();
	}
}