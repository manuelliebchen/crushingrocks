package Game.Controller.BuiltIn;

import javafx.scene.input.KeyCode;
import Client.ClientConstants;
import Client.InputManager;
import Client.InputManager.InputKeyListener;
import Client.InputManager.KeyEventType;
import Game.GameConstants;
import Game.Controller.IPlayerController;
import Game.Controller.MapInfo;
import Game.Controller.PlayerInfo;
import Game.Logic.Vector;

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
	public Vector think(MapInfo mapInfo, PlayerInfo ownPlayerInfo, PlayerInfo[] enemyPlayerInfo) {
		return getDirection();
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

