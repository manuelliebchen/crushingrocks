package de.acagamics.crushingrocks.controller.sample;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.acagamics.crushingrocks.types.GameProperties;
import de.acagamics.crushingrocks.controller.IPlayerController;
import de.acagamics.crushingrocks.logic.Map;
import de.acagamics.crushingrocks.logic.Mine;
import de.acagamics.crushingrocks.logic.Player;
import de.acagamics.crushingrocks.logic.Unit;
import de.acagamics.crushingrocks.types.RenderingProperties;
import de.acagamics.framework.resources.ResourceManager;
import de.acagamics.framework.types.Geometry2f;
import de.acagamics.framework.types.Line2f;
import de.acagamics.framework.types.Student;
import de.acagamics.framework.types.Vec2f;
import de.acagamics.framework.ui.IIllustrating;
import de.acagamics.framework.ui.Illustrator;
import de.acagamics.framework.ui.interfaces.IDrawable;
import javafx.scene.canvas.GraphicsContext;

/**
 * Simplistic sample bot.
 * @author Manuel Liebchen
 *
 */
@Student(author = "Manuel Liebchen", matrikelnummer = -1, name = "BotyMcBotface")
public final class BotyMcBotface implements IPlayerController, IIllustrating {
	Random random = new Random();
	int nextBot = random.nextInt(3) + 1;

	List<Geometry2f> draws;

	public BotyMcBotface() {
		draws = new ArrayList<>();
	}
	
	@Override
	public void think(Map mapInfo, Player ownPlayer, Player enemyPlayerInfo) {
		List<Mine> mines = mapInfo.getMines();
		List<Unit> units = ownPlayer.getUnits();
		ownPlayer.setAllUnitsOrder(enemyPlayerInfo.getBase().getPosition());
		mines.removeIf( m -> m.getOwnership(ownPlayer) >= 1 - GameProperties.EPSILON);
		int ownedMines = mines.size();
		if(mines.isEmpty()) {
			ownPlayer.setAllUnitsOrder(enemyPlayerInfo.getBase().getPosition());
		} else {
			for(Unit unit : units){
				if(!mines.isEmpty()) {
					mines.sort((Mine m, Mine n) -> (n.getPosition().distanceSqr(unit.getPosition()) - m.getPosition().distanceSqr(unit.getPosition()) < 0 ? 1 : -1));
					Mine target = mines.get(0);
					unit.setOrder(target.getPosition());
					draws.add(new Line2f(unit.getPosition(), target.getPosition()));
					draws.add(unit.getPosition());
					mines.remove(target);
				} else {
					unit.setOrder(enemyPlayerInfo.getBase().getPosition());
					draws.add(new Line2f(unit.getPosition(), enemyPlayerInfo.getBase().getPosition()));
					draws.add(unit.getPosition());
				}
			}
		}
		if(ownPlayer.hasHero()){
			Unit hero = ownPlayer.getHero();
			hero.setOrder(enemyPlayerInfo.getBase().getPosition());
			draws.add(new Line2f(hero.getPosition(), enemyPlayerInfo.getBase().getPosition()));
		}
		if(ownedMines > GameProperties.get().getNumberOfMines() / 2 && ownPlayer.setUnitCreationOrder(nextBot) > 0)  {
			nextBot = random.nextInt(3) + 1;
		}
		ownPlayer.setHeroCreationOrder();
	}

	@Override
	public void draw(Illustrator illustrator) {
		for(Geometry2f l : draws){
			illustrator.draw(l);
		}
		draws.clear();
	}
}