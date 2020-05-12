package de.acagamics.crushingrocks.controller.sample;

import java.util.Optional;

import de.acagamics.crushingrocks.controller.IPlayerController;
import de.acagamics.framework.types.Student;
import de.acagamics.crushingrocks.logic.Map;
import de.acagamics.crushingrocks.logic.Mine;
import de.acagamics.crushingrocks.logic.Player;
import javafx.event.EventHandler;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyEvent;

/**
 * Simplistic Human Keyboard Bot
 * 
 * @author Max
 *
 */
@Student(author="Max", matrikelnummer = -1, name = "Human")
public final class HumanBot implements IPlayerController, EventHandler<InputEvent> {

	private int mineOrder;

	@Override
	public void think(Map mapInfo, Player ownPlayer, Player enemyPlayerInfo) {
		if (mineOrder - 1 >= 0 && mineOrder - 1 < mapInfo.getMines().size()) {
			Optional<Mine> order = mapInfo.getMines().stream().filter(m-> m.getMineID() == mineOrder-1).findFirst();
			if(order.isPresent()) {
				ownPlayer.setAllUnitsOrder( order.get().getPosition());
			}
		} else if (mineOrder == 0) {
			ownPlayer.setAllUnitsOrder(enemyPlayerInfo.getBase().getPosition());
		}
		ownPlayer.setUnitCreationOrder(1);
	}

	@Override
	public void handle(InputEvent event) {
		if (event instanceof KeyEvent) {
			KeyEvent keyEvent = (KeyEvent) event;
			if (keyEvent.getEventType().equals(KeyEvent.KEY_TYPED) && Character.isDigit(keyEvent.getCharacter().charAt(0))) {
				mineOrder = Integer.valueOf(keyEvent.getCharacter());
			}
		}
	}
}
