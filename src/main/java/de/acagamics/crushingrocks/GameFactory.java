package de.acagamics.crushingrocks;

import de.acagamics.crushingrocks.logic.Game;
import de.acagamics.framework.simulation.Factory;
import de.acagamics.framework.types.MatchSettings;


public class GameFactory implements Factory<Game> {

    private MatchSettings settings;

    public GameFactory(MatchSettings settings){
        this.settings= settings;
    }

    @Override
    public Game create() {
        return new Game(settings);
    }
}
