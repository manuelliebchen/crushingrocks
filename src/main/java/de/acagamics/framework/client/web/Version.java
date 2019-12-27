package de.acagamics.framework.client.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.acagamics.framework.resourcemanagment.ClientProperties;
import de.acagamics.framework.resourcemanagment.ResourceManager;

/**
 * @author Max Klockmann (max@acagamics.de)
 *
 */
public class Version {
	private static final Logger LOG = LogManager.getLogger(Version.class.getName());
	
	/**
	 * The result of "current Version".compareTo(version).
	 */
	private static Optional<Integer> upToDate = Optional.empty();
	
	/**
	 * Have you called the loadVersion method and has it finished?
	 * @return If you called the checkVersion method and it finished.
	 */
	public static boolean isChecked() {
		return upToDate.isPresent();
	}
	/**
	 * Is your version the current version?
	 * @return Is your version up to date
	 */
	public static boolean isUpToDate() {
		return upToDate.get() == 0;
	}
	
	/**
	 * Run new thread to load the current version.
	 */
	public static void loadVersion() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				_loadVersion();
			}
		}).start();
	}
	
	/**
	 * Load the current version.
	 */
	private static void _loadVersion() {
		// create URL to version
		URL website;
		try {
			website = new URL(ResourceManager.getInstance().loadProperties(ClientProperties.class).getVersionURL());
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return;
		}
		
		// open connection
		URLConnection connection = null;
		try {
			connection = website.openConnection();
		} catch (IOException e) {
			LOG.warn("Can't connect to the mother ship (tried to check game version)");
			return;
		}
		
		// read server response
		BufferedReader in;
		try {
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		} catch (IOException e) {
			LOG.warn("Can't connect to the mother ship (tried to check game version)");
			return;
		}
		StringBuilder response = new StringBuilder();
		String inputLine;
		try {
			// delete first Line (garbage from wordpress)
			in.readLine();
			while ((inputLine = in.readLine()) != null) 
				response.append(inputLine);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		try {
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		// check version
		String version = response.toString();
		upToDate = Optional.of(version.compareTo(ResourceManager.getInstance().loadProperties(ClientProperties.class).getVersion()));
		if (upToDate.get() > 0) {
			LOG.warn("You're running an old version (" + ResourceManager.getInstance().loadProperties(ClientProperties.class).getVersion() + ")! The most recent version is " + version + ". Please Update!");
		} else if (upToDate.get() < 0) {
			LOG.warn("You're running a future version (" + ResourceManager.getInstance().loadProperties(ClientProperties.class).getVersion() + ")! The most recent version is " + version + ". Please report to mothership!");
		}
	}
}
