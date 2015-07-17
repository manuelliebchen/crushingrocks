package Game.Controller.BuiltIn;

import Client.ClientConstants;
import Client.InputManager;
import Game.GameConstants;
import Game.Controller.IPlayerController;
import Game.Controller.MapInfo;
import Game.Controller.PlayerInfo;
import Game.Logic.Vector;

/**
 * Simplistic Human Mouse Bot
 * @author Max
 *
 */
public class HumanMouseBot implements IPlayerController {
	
	Vector getMousePosition() {
		Vector pos = new Vector((float)InputManager.get().getMousePositionX(), (float)InputManager.get().getMousePositionY());
		Vector relative = new Vector(pos.getX() - ClientConstants.SCREEN_WIDTH / 2, pos.getY() - ClientConstants.SCREEN_HEIGHT / 2);
		relative = relative.div(ClientConstants.SCREEN_HEIGHT / (GameConstants.MAP_RADIUS * 2));
		
		return relative;
	}
		
	@Override
	public String getName() {
		return "Human Mouse Bot";
	}

	@Override
	public String getAuthor() {
		return "Max Klockmann";
	}
	
	@Override
	public Vector think(MapInfo mapInfo, PlayerInfo ownPlayerInfo, PlayerInfo[] enemyPlayerInfo) {		
		return getMousePosition().sub(ownPlayerInfo.getPosition());
	}
}

