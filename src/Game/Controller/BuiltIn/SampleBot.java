package Game.Controller.BuiltIn;

import java.util.List;
import java.util.Random;

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
	
	private Random rnd = new Random();
		
	@Override
	public String getName() {
		return "Boty McBotface";
	}

	@Override
	public String getAuthor() {
		return "Manuel Liebchen";
	}
	
	@Override
	public UNIT_TYPE think(Map mapInfo, Player ownPlayer, Player enemyPlayerInfo) {
		List<Mine> mines = mapInfo.getMines();
		mines.removeIf( m -> m.getOwner() == ownPlayer);
		for(Unit unit: ownPlayer.getUnits()) {
			mines.sort((Mine m, Mine n) -> Math.round(m.getPosition().distanceSqr(unit.getPosition()) - n.getPosition().distanceSqr(unit.getPosition())));
			unit.setOrder(mines.get(0).getPosition().sub(unit.getPosition()));
		}
		return UNIT_TYPE.RED;
	}


}

