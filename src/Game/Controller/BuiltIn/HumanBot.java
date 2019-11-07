package Game.Controller.BuiltIn;

import Client.GUI.InputTracker.KeyEventType;
import Constants.GameConstants.UNIT_TYPE;
import Game.Controller.IPlayerController;
import Game.Logic.Map;
import Game.Logic.Player;
import Game.Types.Vector;
import javafx.event.EventHandler;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Simplistic Human Keyboard Bot
 * 
 * @author Max
 *
 */
public class HumanBot implements IPlayerController, EventHandler<InputEvent> {

	private int x;
	private int y;

	public HumanBot() {
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
	public int getMatrikelnummer() {
		return -1;
	}

	@Override
	public UNIT_TYPE think(Map mapInfo, Player ownPlayer, Player enemyPlayerInfo) {
		return UNIT_TYPE.RED;
	}

	@Override
	public void handle(InputEvent event) {
		if (event instanceof KeyEvent) {
			KeyEvent keyEvent = (KeyEvent) event;
			if (keyEvent.getEventType().equals(KeyEventType.KEY_DOWN)) {
				if (keyEvent.getCode().equals(KeyCode.UP)) {
					y -= 1;
				}
				if (keyEvent.getCode().equals(KeyCode.DOWN)) {
					y += 1;
				}
				if (keyEvent.getCode().equals(KeyCode.LEFT)) {
					x -= 1;
				}
				if (keyEvent.getCode().equals(KeyCode.RIGHT)) {
					x += 1;
				}

				if (x != 0)
					x = (x > 0) ? 1 : -1;
				if (y != 0)
					y = (y > 0) ? 1 : -1;
			}
			if (keyEvent.getEventType().equals(KeyEventType.KEY_RELEASED)) {
				if (keyEvent.getCode().equals(KeyCode.UP) || keyEvent.getCode().equals(KeyCode.DOWN))
					y = 0;
				if (keyEvent.getCode().equals(KeyCode.LEFT) || keyEvent.getCode().equals(KeyCode.RIGHT))
					x = 0;
			}
		}
	}
}
