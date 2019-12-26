package de.acagamics.simulation.context;

import java.util.List;

import de.acagamics.client.utility.StaticCombinationIterator;

/**
 * Uses combinatorics (binomial) to create a very fair but computationally
 * expensive tournament. It produces n over k combinations. Under the
 * assumption that bots play differently under different conditions
 * provided by other bots the results of such a tournament are very truthful
 * to the real quality of a bot.
 * 
 * This generalized tournament system is equivalent of the round robin
 * tournament system.
 * 
 * @author Tim Benedict Jagla {@literal <tim@acagamics.de>}
 */
public final class CombinatoricsFactory extends TournamentFactoryBase {

	private static final int CRITICAL_CALCULATION_TIME_IN_DAYS = 7;
	private static final int PROCESS_TO_MINUTES = 2;
	private static final int MINUTES_TO_HOURS = 60;
	private static final int HOURS_TO_DAYS = 24;
	private static final int PARALLEL = 3;
	
	private final StaticCombinationIterator<String> combinationIterator;
	
	/**
	 * Constructor
	 * @param bots Bots to play.
	 * @param gameName Name of the Game.
	 * @param gameSize Number of controllers in a single game.
	 */
	public CombinatoricsFactory(List<String> bots, String gameName, int gameSize) {
		super(bots, gameName);
		this.combinationIterator = new StaticCombinationIterator<String>(this.bots, gameSize);
		// calculate time expectations as this factory is highly susceptible to complexity
		final double days = ((((this.combinationIterator.binomial().longValue() * PROCESS_TO_MINUTES) / MINUTES_TO_HOURS) / HOURS_TO_DAYS) / PARALLEL);
		final String message = String.format("Forecast calculation time of %.0f days, with an average of %d minutes per process on %d threads!", days, PROCESS_TO_MINUTES, PARALLEL);
		if (days > CRITICAL_CALCULATION_TIME_IN_DAYS) { throw new UnsupportedOperationException(message); }
	}
	
	@Override
	public String tournamentSystem() {
		return "Combinatorics";
	}
	
	/**
	 * Checks whether another unique combination with parameters according to
	 * {@link #CombinatoricsFactory(List, String, int)} exists.
	 * @see java.util.Iterator#hasNext()
	 */
	@Override
	public boolean hasNext() {
		return this.combinationIterator.hasNext();
	}

	/**
	 * Returns the next unique combination with parameters according to
	 * {@link #CombinatoricsFactory(List, String, int)}.
	 * @see java.util.Iterator#next()
	 */
	@Override
	public GameContext next() {
		return this.builder.bots(this.combinationIterator.next()).build();
	}

}
