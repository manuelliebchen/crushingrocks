package de.acagamics.crushingrocks;

import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

import com.beust.jcommander.JCommander;

import de.acagamics.crushingrocks.controller.IPlayerController;
import de.acagamics.crushingrocks.controller.builtIn.BotyMcBotface;
import de.acagamics.crushingrocks.controller.builtIn.EmptyBotyMcBotface;
import de.acagamics.crushingrocks.controller.builtIn.EvilSanta;
import de.acagamics.crushingrocks.controller.builtIn.HumanBot;
import de.acagamics.crushingrocks.logic.Game;
import de.acagamics.framework.types.MatchSettings.GAMEMODE;
import de.acagamics.framework.ui.MainWindow;
import de.acagamics.framework.util.BotClassLoader;
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
		} else {

			LOG.info("Running Simulation");

			List<Class<?>> buildin = Arrays.asList(
					new Class<?>[] { BotyMcBotface.class, EmptyBotyMcBotface.class, HumanBot.class, EvilSanta.class });
			BotClassLoader<IPlayerController> playerLoader = new BotClassLoader<IPlayerController>(
					IPlayerController.class, buildin);
			playerLoader.loadControllerFromDirectory(FileSystems.getDefault().getPath("").toAbsolutePath().toString());
			List<Class<?>> bots = playerLoader.getLoadedBots();

			List<String> names = new ArrayList<>(2);
			for (int i = 0; i < 2; ++i) {
				for (Class<?> b : bots) {
					if (b.getSimpleName().contains(cliArg.selectedBots().get(i))) {
						names.add(b.getName());
						break;
					}
				}
			}
			
			List<IPlayerController> controller = new ArrayList<>(names.size());
			for (String name : names) {
				controller.add(playerLoader.instantiateLoadedExternController(name));
			}

			Game game = new Game(controller, GAMEMODE.NORMAL);
			long startTime = System.currentTimeMillis();
			GameStatistic gs = null;
			while (gs == null) {
				gs = game.tick();
			}
			System.out.println("Took: " + (System.currentTimeMillis() - startTime) + "ms");
			System.out.println("Statistic:\n" + gs.toString());
		}
	}

}
