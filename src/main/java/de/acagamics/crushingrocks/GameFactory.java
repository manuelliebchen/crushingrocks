package de.acagamics.crushingrocks;

import de.acagamics.crushingrocks.logic.Game;
import de.acagamics.framework.types.MatchSettings;

public class GameFactory implements de.acagamics.framework.simulation.GameFactory<Game> {

    private MatchSettings settings;

    public GameFactory(MatchSettings settings){
        this.settings= settings;
    }

    @Override
    public Game generateGame() {
        return new Game(settings);
    }
}
