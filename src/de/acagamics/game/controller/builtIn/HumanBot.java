package de.acagamics.game.controller.builtIn;

import de.acagamics.game.controller.IPlayerController;
import de.acagamics.game.logic.Map;
import de.acagamics.game.logic.Player;
import de.acagamics.game.logic.Unit;
import javafx.event.EventHandler;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyEvent;

/**
 * Simplistic Human Keyboard Bot
 * 
 * @author Max
 *
 */
public class HumanBot implements IPlayerController, EventHandler<InputEvent> {

	private int mineOrder;

	public HumanBot() {
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
	public void think(Map mapInfo, Player ownPlayer, Player enemyPlayerInfo) {
		if (mineOrder - 1 >= 0 && mineOrder - 1 < mapInfo.getMines().size()) {
			for (Unit unit : ownPlayer.getUnits()) {
				unit.setOrder(this, mapInfo.getMines().get(mineOrder - 1).getPosition().sub(unit.getPosition()));
			}
		} else if (mineOrder == 0) {
			for (Unit unit : ownPlayer.getUnits()) {
				unit.setOrder(this, enemyPlayerInfo.getBase().getPosition().sub(unit.getPosition()));
			}
		}
		ownPlayer.setUnitCreationOrder(this, 1);
	}

	@Override
	public void handle(InputEvent event) {
		if (event instanceof KeyEvent) {
			KeyEvent keyEvent = (KeyEvent) event;
			if (keyEvent.getEventType().equals(KeyEvent.KEY_TYPED)) {
				try {
					mineOrder = Integer.valueOf(keyEvent.getCharacter());
				} catch (Exception e) {
				}
			}
		}
	}
}
