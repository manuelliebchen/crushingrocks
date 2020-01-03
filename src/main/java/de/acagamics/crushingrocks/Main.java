package de.acagamics.crushingrocks;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

import com.beust.jcommander.JCommander;

import de.acagamics.framework.ui.MainWindow;
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
		JCommander jcom = JCommander.newBuilder().addObject(cliArg).build();
		jcom.parse(args);

		if (cliArg.showHelp()) {
			jcom.usage();
			return;
		}

		if (cliArg.isDebug()) {
			Configurator.setRootLevel(Level.ALL);
			LOG.info("Debug mode enabled!");
		}

		LOG.info("Starting with state: " + cliArg.getStartupState());
		
		if (cliArg.showGui()) {
			Application.launch(MainWindow.class, cliArg.getStartupState());
		}
	}

}
