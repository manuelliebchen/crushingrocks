package de.acagamics.crushingrocks;

import de.acagamics.crushingrocks.types.CliArguments;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

import com.beust.jcommander.JCommander;

import de.acagamics.framework.ui.MainWindow;

import de.acagamics.crushingrocks.states.MainMenuState;

/**
 * @author Max Klockmann (max@acagamics.de)
 */
public final class Main {
	private static final Logger LOG = LogManager.getLogger(Main.class.getName());

	public static void main(String... args) {
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

		if (cliArg.showGui()) {
			MainWindow.launch(MainMenuState.class);
		}
	}

}
