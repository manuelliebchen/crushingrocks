package Game.Controller.BuiltIn;

import java.util.List;
import java.util.Random;

import Constants.GameConstants;
import Game.Controller.IPlayerController;
import Game.Logic.Map;
import Game.Logic.Mine;
import Game.Logic.Player;
import Game.Logic.Unit;

/**
 * Simplistic sample bot.
 * @author Manuel Liebchen
 *
 */
public class SampleBot implements IPlayerController {
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
	public String getName() {
		return "Boty McBotface - " + String.valueOf(nextUnit);
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

