package de.acagamics.framework.types;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** 
 * Plain old data type that identifies or defines a simulation. It is a
 * container for specific information like the name of the game played,
 * tournament mode or number of game in a series of games.
 * 
 * Furthermore it provides a command line invoked process with the required
 * arguments to run the game in the mode specified within this object.
 * 
 * Also it defines the corresponding name convention of the output file of 
 * a simulation process.
 * 
 * @author Tim Benedict Jagla {@literal <tim@acagamics.de>}
 */
public final class GameContext {

	/** Identifier of the game to be played, possibly with a game mode extension. */
	private String gameName;
	/** Identifier of the tournament system.*/
	private String tournamentSystem;
	/** Stage within the tournament, e.g. finals or semi-finals as a number. */
	private int tournamentStage = 0;
	/** Number of the game in a series of games, e.g. the third game of a best of five series. */
	private int series = 0;
	/** Number of the repetition. Optional but a possible way to make a simulation run fairer. */
	private int repeat = 0;
	/** A list of the names of the bots participating in the specific simulation. Keep it in order. */
	private List<String> bots;
	
	/**
	 * Constructor
	 */
	private GameContext() {}
	
	/**
	 * Copy Constructor
	 * @param context the {@link GameContext} to copy
	 */
	private GameContext(final GameContext context) {
		this.gameName = context.gameName;
		this.tournamentSystem = context.tournamentSystem;
		this.tournamentStage = context.tournamentStage;
		this.series = context.series;
		this.repeat = context.repeat;
		this.bots = context.bots;
	}
	
	/**
	 * A Builder to mitigate the need for a huge constructor for a {@link GameContext} object.
	 * Additionally the builder provides parsing capabilities of the output filename of a
	 * process, defined in {@link #toFilename()}.
	 */
    public static class Builder {
    	
    	/** A prototypical instance of a {@link #GameContext} to build identical clones from. */
        private GameContext prototype;
        
        public Builder() {
        	prototype = new GameContext();
        }
        
        public Builder gameName(final String gameName) {
             prototype.gameName = gameName;
             return this;
        }
        
        public Builder tournamentSystem(final String tournamentSystem) {
            prototype.tournamentSystem = tournamentSystem;
            return this;
        }

        public Builder tournamentStage(final int tournamentStage) {
            prototype.tournamentStage = tournamentStage;
            return this;
        }
        
        public Builder series(final int series) {
            prototype.series = series;
            return this;
        }

        public Builder repeat(final int repeat) {
            prototype.repeat = repeat;
            return this;
        }

        public Builder bots(final List<String> bots) {
            prototype.bots = bots;
            return this;
        }
        
        public Builder bots(final String... bots) {
        	this.bots(Arrays.asList(bots));
        	return this;
        }
        
        /**
         * Parses an {@link GameContext#toFilename()} into an instance of {@link GameContext}.
         * @param filename fully qualifying filename of a game process instance.
         * @return a builder setup to build {@link GameContext}s according to the input parameter.
         */
        public Builder fromFilename(final String filename) {
    		String[] split = filename.split("-");
    		this.gameName(split[0])
    			.tournamentSystem(split[1])
    			.tournamentStage(Integer.parseInt(split[2]))
    			.series(Integer.parseInt(split[3]))
    			.repeat(Integer.parseInt(split[4]))
    			.bots(Arrays.asList(split[5].split("+")));
    		return this;
        }
        
        /**
         * @param context Instance of the {@link GameContext} to clone.
         * @return The builder to create clones of the given {@link GameContext}.
         */
        public Builder fromSimulationContext(GameContext context) {
        	this.gameName(context.gameName)
        		.tournamentSystem(context.tournamentSystem)
        		.tournamentStage(context.tournamentStage)
        		.series(context.series)
        		.repeat(context.repeat)
        		.bots(context.bots);
        	return this;
        }
        
        /**
         * Last action after a chained build statement to return a {@link GameContext}.
         * @return A copy constructed instance of {@link GameContext} from the {@link #prototype}.
         */
        public GameContext build() {
           if (prototype.gameName == null || prototype.tournamentSystem == null || prototype.bots == null) {
        	   throw new IllegalStateException("Required Parameters: gameName, tournamentSystem, bots");
           }
           return new GameContext(prototype);
        }
        
    }
    
    /**
     * Generate command line arguments to execute this specific simulation.
     * @return command line arguments of this specific simulation.
     */
	public List<String> arguments() {
		List<String> list = new ArrayList<String>();
		list.add("--bots");
		list.addAll(bots);
		return list;
	}
	
	/**
	 * Converts a {@link #GameContext} to a fully qualifying filename.
	 * @see Builder#fromFilename(String)
	 * @return fully qualifying filename of the expected statistics file returned from the game.
	 */
	public String toFilename() {
		return String.join("-", gameName, tournamentSystem, String.valueOf(tournamentStage), String.valueOf(series), String.valueOf(repeat), String.join("+", bots));
	}
	
	@Override
	public String toString() {
		return toFilename();
	}
	
}
