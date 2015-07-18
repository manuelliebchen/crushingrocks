package Client.Simulation.Context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * An elimination tournament system (knockout system) is a quick way to determine an overall winner.
 * The results are not as representative as in a #CombinatoricsFactory but may be a good way for
 * some kind of preliminary decision.
 * 
 * TODO fuse the multi-stage tournament system with the statistics system!
 * 
 * @author Tim Benedict Jagla {@literal <tim@acagamics.de>}
 */
public final class EliminationFactory extends TournamentFactoryBase implements IMultiStageTournamentFactory {

	/** Number of controllers in a single tournament bracket. */
	final int sizeBracket;
	/** Number of controllers eliminated after a bracket. */
	final int eliminationsPerBracket;
	
	int stage = -1;
	
	List<String> stageBots;
	int currentBracket;
	int totalBrackets;
	
	public EliminationFactory(List<String> bots, String gameName, final int sizeBracket, final int eliminationsPerBracket) {
		super(bots, gameName);
		this.sizeBracket = sizeBracket;
		this.eliminationsPerBracket = eliminationsPerBracket;
		prepareStage(this.bots);
	}

	@Override
	public String tournamentSystem() {
		return "Elimination";
	}
	
	@Override
	public boolean hasNext() {
		return this.currentBracket < this.totalBrackets;
	}

	@Override
	public GameContext next() {
		final List<String> participants = this.stageBots.subList(this.currentBracket * this.sizeBracket, ++this.currentBracket * this.sizeBracket);
		return this.builder.bots(participants).build();
	}

	@Override
	public void prepareStage(List<String> statistics) {
		prepareBots(statistics);
		
		// reset bracket counters
		this.currentBracket = 0;
		this.totalBrackets = this.stageBots.size() / this.sizeBracket;
		
		// update stage
		++this.stage;
		this.builder.tournamentStage(this.stage);
	}
	
	private void prepareBots(List<String> bots) {
		this.stageBots = new ArrayList<String>(bots);
		
		// add some randomness to create fairer pairings
		Collections.shuffle(this.stageBots);
		
		// fill the list of stageBots with the required number of BYEs as far away from each other as possible
		final int remainder = this.stageBots.size() % this.sizeBracket;
		if (remainder != 0) {
			final int missing = this.sizeBracket - remainder;
			final double stride = (double)this.stageBots.size() / missing;
			for (int i = 0; i < missing; ++i) {
				int insertAt = (int)(Math.round((i+1) * stride) + i);
				this.stageBots.add(insertAt, "BYE");
			}
		}
	}
	
}
