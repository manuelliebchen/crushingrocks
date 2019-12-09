package de.acagamics.game.controller.builtIn;

import java.util.Optional;

import de.acagamics.game.controller.IPlayerController;
import de.acagamics.game.logic.Map;
import de.acagamics.game.logic.Mine;
import de.acagamics.game.logic.Player;
import javafx.event.EventHandler;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyEvent;

/**
 * Simplistic Human Keyboard Bot
 * 
 * @author Max
 *
 */
public final class HumanBot implements IPlayerController, EventHandler<InputEvent> {

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
			Optional<Mine> order = mapInfo.getMines().stream().filter((m)-> m.getMineID() == mineOrder-1).findFirst();
			if(order.isPresent()) {
				ownPlayer.setAllUnitsOrder(this, order.get().getPosition());
			}
		} else if (mineOrder == 0) {
			ownPlayer.setAllUnitsOrder(this, enemyPlayerInfo.getBase().getPosition());
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
