package de.acagamics.crushingrocks;

import de.acagamics.crushingrocks.logic.Game;
import de.acagamics.framework.simulation.Factory;
import de.acagamics.framework.types.MatchSettings;

import java.util.Random;


public class GameFactory implements Factory<Game> {

    private MatchSettings<GameMode> settings;

    public GameFactory(MatchSettings<GameMode> settings){
        this.settings= settings;
    }

    @Override
    public Game create() {
        return new Game(settings, new Random());
    }
}
