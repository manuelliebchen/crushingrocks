package Client.Simulation.Context;

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
	 * @return A list of #SimulationContext to describe each of the game of a stage of the tournament.
	 */
	public void prepareStage(List<String> statistics);
	
}
