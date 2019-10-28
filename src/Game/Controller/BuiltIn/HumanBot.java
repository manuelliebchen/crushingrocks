package Game.Controller.BuiltIn;

import java.util.List;

import Client.InputManager;
import Client.InputManager.InputKeyListener;
import Client.InputManager.KeyEventType;
import Game.Controller.IPlayerController;
import Game.Info.MapInfo;
import Game.Info.PlayerInfo;
import Game.Logic.Unit;
import Game.Logic.Vector;
import Game.Logic.GameConstants.UNIT_TYPE;
import javafx.scene.input.KeyCode;

/**
 * Simplistic Human Keyboard Bot
 * @author Max
 *
 */
public class HumanBot implements IPlayerController, InputKeyListener {
	
	private int x;
	private int y;
	
	public HumanBot() {
		InputManager.get().addKeyListener(this);
	}
	
	Vector getDirection() {		
		return new Vector(x, y);
	}
		
	@Override
	public String getName() {
		return "Human Bot";
	}

	@Override
	public String getAuthor() {
		return "Max Klockmann";
	}
	
	@Override
	public UNIT_TYPE think(MapInfo mapInfo, List<Unit> ownUnits, PlayerInfo enemyPlayerInfo) {
//		return getDirection();
		return UNIT_TYPE.RED;
	}

	@Override
	public void keyEvent(KeyEventType type, KeyCode code) {
		if (type.equals(KeyEventType.KEY_DOWN)) {
			if (code.equals(KeyCode.UP)) {
				y -= 1;
			}
			if (code.equals(KeyCode.DOWN)) {
				y += 1;
			}
			if (code.equals(KeyCode.LEFT)) {
				x -= 1;
			}
			if (code.equals(KeyCode.RIGHT)) {
				x += 1;
			}
			
			if (x != 0)
				x = (x > 0) ? 1 : -1;
			if (y != 0)
				y = (y > 0) ? 1 : -1;
		}
		if (type.equals(KeyEventType.KEY_RELEASED)) {
			if (code.equals(KeyCode.UP) || code.equals(KeyCode.DOWN))
				y = 0;
			if (code.equals(KeyCode.LEFT) || code.equals(KeyCode.RIGHT))
				x = 0;
		}
	}
}

