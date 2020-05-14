package de.acagamics.crushingrocks.controllers;

import de.acagamics.crushingrocks.logic.*;
import de.acagamics.framework.geometry.Geometry2f;
import de.acagamics.framework.geometry.Illustrator;
import de.acagamics.framework.geometry.Line2f;
import de.acagamics.framework.interfaces.IIllustrating;
import de.acagamics.framework.interfaces.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Simplistic sample bot.
 * @author Manuel Liebchen
 *
 */
@Student(author = "Manuel Liebchen", matrikelnummer = -1, name = "BotyMcBotfaceName")
public final class BotyMcBotface implements IPlayerController, IIllustrating {
	Random random = new Random();

	List<Geometry2f> draws;

	public BotyMcBotface() {
		draws = new ArrayList<>();
	}
	
	@Override
	public void think(Map mapInfo, Player ownPlayer, Player enemyPlayerInfo) {
		List<Mine> mines = mapInfo.getMines();
		ownPlayer.setAllUnitsOrder(enemyPlayerInfo.getBase().getPosition());
		mines.removeIf( m -> m.getOwnership(ownPlayer) >= 1 - GameProperties.EPSILON);
		int notOwnedMines = mines.size();
		if(mines.isEmpty()) {
			ownPlayer.setAllUnitsOrder(enemyPlayerInfo.getBase().getPosition());
		} else {
			for(Unit unit : ownPlayer.getUnits()){
				if(!mines.isEmpty()) {
					mines.sort(unit.getPosition().sortDistanceTo());
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
		int nextBot = 3 * (GameProperties.get().getNumberOfMines() - notOwnedMines) / GameProperties.get().getNumberOfMines() + 1;
		if(notOwnedMines > 0)  {
			ownPlayer.setUnitCreationOrder(nextBot);
		} else {
			ownPlayer.setHeroCreationOrder();
		}
	}

	@Override
	public void draw(Illustrator illustrator) {
		for(Geometry2f l : draws){
			illustrator.draw(l);
		}
		draws.clear();
	}
}