package Game.Controller.BuiltIn;

import java.util.List;

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
		if(units.size() >= 1) {
			for( Unit unit : units.subList(0, 1)) {
				unit.setOrder(this, ownPlayer.getBase().getPosition().sub(unit.getPosition()));
			}
		}
		if(units.size() >= 2) {
			for( Unit unit : units.subList(1, 2)) {
				unit.setOrder(this, enemyPlayerInfo.getBase().getPosition().sub(unit.getPosition()));
			}
		}
		if(units.size() >= 3) {
			mines.removeIf( m -> m.getOwner()[ownPlayer.getPlayerID()] > 0.5f);
			if(mines.isEmpty()) {
				return null;
			}
			for(Unit unit: units.subList(2,units.size())) {
				mines.sort((Mine m, Mine n) -> Math.round(m.getPosition().distanceSqr(unit.getPosition()) - n.getPosition().distanceSqr(unit.getPosition())));
				unit.setOrder(this, mines.get(0).getPosition().sub(unit.getPosition()));
			}
		}
		return UNIT_TYPE.RED;
	}



}

