package de.acagamics.crushingrocks.controller.builtin;

import java.util.List;
import java.util.Random;

import de.acagamics.crushingrocks.GameProperties;
import de.acagamics.crushingrocks.controller.IPlayerController;
import de.acagamics.crushingrocks.logic.Map;
import de.acagamics.crushingrocks.logic.Mine;
import de.acagamics.crushingrocks.logic.Player;
import de.acagamics.crushingrocks.logic.Unit;
import de.acagamics.framework.types.Student;
import de.acagamics.framework.types.Vec2f;

/**
 * Simplistic sample bot.
 * @author Manuel Liebchen
 *
 */
@Student(name = "Manuel Liebchen", matrikelnummer = -1)
public final class BotyMcBotface implements IPlayerController {
	Random random = new Random();
	
//	int nextUnit = random.nextInt(3)+1;
	int nextUnit = 1;
	
	@Override
	public void think(Map mapInfo, Player ownPlayer, Player enemyPlayerInfo) {
		List<Mine> mines = mapInfo.getMines();
		List<Unit> units = ownPlayer.getUnits();
		mines.removeIf( m -> m.getOwnership(ownPlayer) >= 1 - GameProperties.EPSILON);
		if(mines.isEmpty() || ownPlayer.getUnits().size() == GameProperties.get().getMaxUnitsPerPlayer()) {
			ownPlayer.setAllUnitsOrder(this, enemyPlayerInfo.getBase().getPosition());
		} else {
			for(Unit unit: units) {
				mines.sort((Mine m, Mine n) -> Math.round((n.getPosition().distanceSqr(unit.getPosition()) - m.getPosition().distanceSqr(unit.getPosition())) * 100));
				unit.setOrder(this, mines.get(mines.size() -1).getPosition().sub(unit.getPosition()));
			}
		}
		if(ownPlayer.hasHero()){
			Unit hero = ownPlayer.getHero();
			Vec2f order = hero.getPosition().sub(enemyPlayerInfo.getBase().getPosition());
			hero.setOrder(this, order);
		}
		if(units.size() < 1){
			ownPlayer.setUnitCreationOrder(this, nextUnit);
		}
		ownPlayer.setHeroCreationOrder(this);
	}
}