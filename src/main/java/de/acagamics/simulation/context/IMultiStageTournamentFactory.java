package de.acagamics.simulation.context;

import java.util.List;

/**
 * Common interface for a multi stage tournament.
 * 
 * TODO fuse the multi-stage tournament system with the statistics system!
 * 
 * @author Tim Benedict Jagla {@literal <tim@acagamics.de>}
 */
public interface IMultiStageTournamentFactory extends ITournamentFactory {

	/**
	 * Do not forget to keep track of the current stage and increment it.
	 * @param statistics the winner bots of the last stage.
	 */
	public void prepareStage(List<String> statistics);
	
}
