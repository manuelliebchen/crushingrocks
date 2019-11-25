package de.acagamics;

import javafx.application.Application;

import org.apache.log4j.Logger;

import de.acagamics.client.MainWindow;
import de.acagamics.data.CliArguments;

/**
 * @author Max Klockmann (max@acagamics.de)
 *
 */
public final class Main {
	private static final Logger LOG = Logger.getLogger(Main.class);
	
	public static void main(String... args) throws Exception {
		// Parse command line arguments
		CliArguments cli = new CliArguments(args);
		
		if (cli.isDebug()) {
			LOG.info("Debug mode enabled!");
		}
		
		// Create new MainWindow
		Application.launch(MainWindow.class);
	}

}