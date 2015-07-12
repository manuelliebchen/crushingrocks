package Client.Web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;

import Client.ClientConstants;

/**
 * @author Max Klockmann (max@acagamics.de)
 *
 */
public class News {
	private static final Logger LOG = Logger.getLogger(Version.class);
	
	private static final Random rnd = new Random();
	
	private static List<String> news;
	private static int index;
	
	/**
	 * Have you called the loadNews method and do you have a result?
	 * @return If you called the loadNews method and do you have a result.
	 */
	public static boolean hasNews() {
		return (news != null && news.size() > 0);
	}
	
	/**
	 * Set selected news to a new random entry.
	 */
	public static void changeNews() {
		index = rnd.nextInt(news.size());
	}
	/**
	 * Get the current selected entry.
	 * @return current selected news
	 */
	public static String getNews() {
		return getNews(index);
	}
	/**
	 * Get one entry.
	 * @param index index of news entry
	 * @return selected news at index
	 */
	public static String getNews(int index) {
		return news.get(index);
	}
	
	/**
	 * Run new thread to load the current news.
	 */
	public static void loadNews() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				_loadNews();
			}
		}).start();
	}
	
	/**
	 * Load the current news;
	 */
	private static void _loadNews() {
		// create URL to version
		URL website;
		try {
			website = new URL(ClientConstants.NEWS_URL);
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
		
		String inputLine;
		try {
			news = new ArrayList<>();
			while ((inputLine = in.readLine()) != null) {
				news.add(inputLine);
			}
				
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
		
		// select random entry
		changeNews();
	}
}