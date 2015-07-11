package Client;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

/**
 * @author Max Klockmann (max@acagamics.de)
 *
 */
public class CliArguments {
	private static final Logger LOG = Logger.getLogger(CliArguments.class);
	
	@Parameter(names = "--debug", description = "Debug mode")
	private boolean debug = false;
	@Parameter(names = "--help", description = "Show usage")
	private boolean help = false;
	
	@Parameter(names = "--nogui", description = "Don't show Gui")
	private boolean noGui = false;
	@Parameter(names = "--count", description = "Number of Games")
	private int count = 1;
	@Parameter(names = "--mode", description = "Selected gamemode")
	private String mode = "";
	@Parameter(names = "-bot", description = "Selected bot")
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
	
	public CliArguments(String... args) {
		JCommander commander = new JCommander(this, args);
		
		if (this.help) {
			commander.usage();
		}
		
		if (this.debug) {
			LOG.setLevel(Level.DEBUG);
		}
	}
}
