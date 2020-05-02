package de.acagamics.crushingrocks.types;

import java.util.ArrayList;
import java.util.List;

import com.beust.jcommander.Parameter;

/**
 * Class for command-line parsing
 * @author Max Klockmann (max@acagamics.de)
 */
public final class CliArguments {
	
	@Parameter(names = {"-d", "--debug"}, description = "Debug mode")
	private boolean debug = false;
	@Parameter(names = {"-h", "--help"}, description = "Show usage")
	private boolean help = false;
	
	@Parameter(names = {"-n", "--nogui"}, description = "Don't show Gui")
	private boolean noGui = false;

	@Parameter(names = {"-t", "--threads"}, description = "Number of Threads to be created")
	private int threads = 1;
	@Parameter(names = {"-c", "--count"}, description = "Number of Games")
	private int count = 1;
	@Parameter(names = {"-m", "--mode"}, description = "Selected gamemode")
	private String mode = "";
	@Parameter(names = {"-b", "--bot"}, description = "Selected bot")
	private List<String> bots = new ArrayList<>();
	
	
	public boolean isDebug() {
		return this.debug;
	}
	
	public boolean showHelp() {
		return help;
	}
	
	public boolean showGui() {
		return !this.noGui;
	}
	
	public int numberOfTreads() {
		return threads;
	}
	
	public int numberOfGames() {
		return this.count;
	}
	
	public String selectedGameMode() {
		return this.mode;
	}
	
	public List<String> selectedBots() {
		return this.bots;
	}
}
