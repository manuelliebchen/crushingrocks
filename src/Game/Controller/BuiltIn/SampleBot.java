package Game.Controller.BuiltIn;

import java.util.List;

import Constants.GameConstants;
import Constants.GameConstants.UNIT_TYPE;
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
		
	@Override
	public String getName() {
		return "Boty McBotface";
	}

	@Override
	public String getAuthor() {
		return "Manuel Liebchen";
	}

	@Override
	public int getMatrikelnummer() {
		return -1;
	}
	
	@Override
	public UNIT_TYPE think(Map mapInfo, Player ownPlayer, Player enemyPlayerInfo) {
		List<Mine> mines = mapInfo.getMines();
		List<Unit> units = ownPlayer.getUnits();
		if(ownPlayer.getUnits().size() == GameConstants.MAXIMUM_UNIT_AMOUNT) {
			for(Unit unit: units) {
				unit.setOrder(this, enemyPlayerInfo.getBase().getPosition().sub(unit.getPosition()));
			}
		} else {
			mines.removeIf( m -> m.getOwnership(ownPlayer) >= 0.9f);
			if(mines.isEmpty()) {
				return UNIT_TYPE.RED;
			}
			for(Unit unit: units) {
				mines.sort((Mine m, Mine n) -> Math.round(n.getPosition().distanceSqr(unit.getPosition()) - m.getPosition().distanceSqr(unit.getPosition())));
				unit.setOrder(this, mines.get(mines.size() -1).getPosition().sub(unit.getPosition()));
			}
		}
		
		return UNIT_TYPE.RED;
	}



}

