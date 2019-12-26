package de.acagamics.data;

import java.util.ArrayList;
import java.util.List;

import com.beust.jcommander.Parameter;

/**
 * Class for command-line parsing
 * @author Max Klockmann (max@acagamics.de)
 */
public final class CliArguments {
//	private static final Logger LOG = LogManager.getLogger(CliArguments.class.getName());
	
	/**
	 * automatic Parameter parsing.
	 */
	@Parameter(names = "--debug", description = "Debug mode")
	private boolean debug = false;
	@Parameter(names = "--help", description = "Show usage")
	private boolean help = false;
	
	@Parameter(names = "--nogui", description = "Don't show Gui")
	private boolean noGui = false;
	@Parameter(names = "--count", description = "Number of Games")
	private int count = 0;
	@Parameter(names = "--mode", description = "Selected gamemode")
	private String mode = "";
	@Parameter(names = "--bot", description = "Selected bot")
	private List<String> bots = new ArrayList<>();
	
	
	public boolean isDebug() {
		return this.debug;
	}
	
	public boolean showGui() {
		return !this.noGui;
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
