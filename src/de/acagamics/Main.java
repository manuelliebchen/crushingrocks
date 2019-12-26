package de.acagamics;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

import com.beust.jcommander.JCommander;

import de.acagamics.client.MainWindow;
import de.acagamics.data.CliArguments;
import javafx.application.Application;

/**
 * @author Max Klockmann (max@acagamics.de)
 *
 */
public final class Main {
	private static final Logger LOG = LogManager.getLogger(Main.class.getName());
	
	public static void main(String... args) throws Exception {
		// Parse command line arguments
		CliArguments cliArg = new CliArguments();
		JCommander.newBuilder()
		  .addObject(cliArg)
		  .build()
		  .parse(args);
		
		System.out.println(JCommander.newBuilder().addObject(cliArg).toString());
		
		// enable debug mode
		if (cliArg.isDebug()) {
			Configurator.setRootLevel(Level.ALL);
			LOG.info("Debug mode enabled!");
		}
		
		// Create new MainWindow
		Application.launch(MainWindow.class);
	}

}
