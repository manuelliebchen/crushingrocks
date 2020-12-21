package de.acagamics.crushingrocks;

import de.acagamics.crushingrocks.controllers.BotyMcBotface;
import de.acagamics.crushingrocks.logic.GameMode;
import de.acagamics.crushingrocks.logic.GameProperties;
import de.acagamics.crushingrocks.logic.IPlayerController;
import de.acagamics.crushingrocks.states.MainState;
import de.acagamics.crushingrocks.types.MatchSettings;
import de.acagamics.framework.resources.ClientProperties;
import de.acagamics.framework.resources.ResourceManager;
import de.acagamics.framework.simulation.*;
import de.acagamics.framework.ui.CliArguments;
import de.acagamics.framework.ui.MainWindow;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public final class Main {
	private static final Logger LOG = LogManager.getLogger(Main.class.getName());

	public static void main(String... args) {
		CliArguments cliArg = new CliArguments(args);

		if (cliArg.showHelp()) {
			cliArg.usage();
			return;
		}

		ClientProperties cliProp = ResourceManager.getInstance().loadProperties(ClientProperties.class);
		if(cliArg.isVersion()) {
			System.out.println(cliProp.getTitle());
			System.out.println(cliProp.getVersion());
			return;
		}

		ResourceManager.getInstance().loadProperties(GameProperties.class);
		if(cliArg.isTournament()) {
			List<Class<?>> loaded = ResourceManager.getInstance().loadContorller(IPlayerController.class);
			List<Class<?>> selected = loaded.stream().filter(c->!c.getPackageName().equals(BotyMcBotface.class.getPackageName())).collect(Collectors.toList());
			Tournament tournament = new Tournament(selected, (s, c1, c2) -> new MatchSettings(GameMode.NORMAL, s, Arrays.asList(c1,c2)), new Random().nextLong(), cliArg.numberOfTreads(), cliArg.numberOfGames());
			tournament.run();
			try(FileWriter myWriter = new FileWriter(cliArg.getOutputFile())) {
				myWriter.write(tournament.getCSV());
			} catch (IOException e) {
				LOG.error("While writing .csv", e);
			}
			return;
		}
		if(cliArg.isSimulation()) {
			List<Class<?>> loaded = ResourceManager.getInstance().loadContorller(IPlayerController.class);
			List<String> names = cliArg.selectedBots();
			List<Class<?>> selected = new ArrayList<>(names.size());
			for(String name : names) {
				Optional<Class<?>> opt =  loaded.stream().filter(c-> c.getSimpleName().equals(name)).findAny();
				if(opt.isPresent()) {
					selected.add((Class<IPlayerController>) opt.get());
				}
			}
			Simulator simulator = new Simulator(new SimulationSettings(cliArg.numberOfTreads(),cliArg.numberOfGames()),new MatchSettings(GameMode.NORMAL, new Random().nextLong(), selected));
			simulator.run();
			try(FileWriter myWriter = new FileWriter(cliArg.getOutputFile())) {
				myWriter.write(simulator.getCSV(selected));
			} catch (IOException e) {
				LOG.error("While writing .csv", e);
			}
			return;
		}
		if(cliArg.isExam()) {
			List<Class<?>> loaded = ResourceManager.getInstance().loadContorller(IPlayerController.class);
			Class<?> bot = null;
			Optional<Class<?>> opt =  loaded.stream().filter(c-> c.getName().equals(cliArg.selectedBots().get(0))).findAny();
			if(opt.isPresent()) {
				bot = opt.get();
			} else {
				if(cliArg.selectedBots().isEmpty()){
					LOG.error("No Bot specified, use -b NAME");
				} else {
					LOG.error("Bot not found.");
				}
				return;
			}
			List<Class<?>> selected = Arrays.asList(BotyMcBotface.class, bot);
			Simulator simulator = new Simulator(new SimulationSettings(cliArg.numberOfTreads(),cliArg.numberOfGames()),new MatchSettings(GameMode.NORMAL, new Random().nextLong(), selected));
			simulator.run();
			SimulationStatistic statistic = simulator.getStatistics();
			if(statistic.getVictories(bot) < (cliArg.numberOfGames() * 0.8f)){
				LOG.info("Der Bot: {}, hat die Pruefungszulassung nicht bestanden.", bot.getName());
				System.exit(1);
			}
			LOG.info("Der Bot: {}, hat die Pruefungszulassung bestanden.", bot.getName());
			return;
		}


		MainWindow.launch(MainState.class);
	}

}
