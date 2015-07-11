package Client;

import org.apache.log4j.Logger;

/**
 * @author Max Klockmann (max@acagamics.de)
 *
 */
public class Main {
	private static final Logger LOG = Logger.getLogger(Main.class);
	
	public static void main(String... args) {		
		CliArguments cli = new CliArguments(args);
		
		if (cli.isDebug()) {
			LOG.info("Debug mode enabled!");
		}
		
		// TODO start game
	}

}
