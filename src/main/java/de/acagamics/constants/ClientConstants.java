package de.acagamics.constants;

/**
 * Class for various client relevant constants.
 * @author Max Klockmann (max@acagamics.de)
 *
 */
public final class ClientConstants {
	/**
	 * The version of this game.
	 * <p>
	 * Value = {@value #VERSION}
	 */
	public static final String VERSION = "1.2.2";
	/**
	 * The url to the list of news.
	 * <p>
	 * Value = {@value #NEWS_URL}
	 */
	// TODO change "localhost:12686" to real domain
	public static final String NEWS_URL = "https://wettbewerb.acagamics.de/downloads/news/";
	/**
	 * The url to the current version.
	 * <p>
	 * Value = {@value #VERSION_URL}
	 */
	// TODO change "localhost:12686" to real domain
	public static final String VERSION_URL = "https://wettbewerb.acagamics.de/downloads/changelog/";
	/**
	 * The title of the window.
	 * <p>
	 * Value = {@value #SCREEN_TITLE} 
	 */
	public static final String SCREEN_TITLE = "Crushing Rocks!";
	/**
	 * The icon of the window.
	 * <p>
	 * Value = {@value #SCREEN_ICON} 
	 */
	public static final String SCREEN_ICON = "icon.png";
	/**
	 * The asset manager root folder, all assets are relative to this path.
	 * <p>
	 * Value = {@value #ASSET_ROOT}
	 */
	public static final String ASSET_ROOT = "";
	/**
	 * The window width.
	 * <p>
	 * Value = {@value #INITIAL_SCREEN_WIDTH} 
	 */
	public static final int INITIAL_SCREEN_WIDTH = 1280;
	/**
	 * The window height.
	 * <p>
	 * Value = {@value #INITIAL_SCREEN_HEIGHT} 
	 */
	public static final int INITIAL_SCREEN_HEIGHT = 720;
	/**
	 * The time per frame in milliseconds
	 * <p>
	 * Value = {@value #MINIMUM_TIME_PER_FRAME_MS}
	 */
	public static final long MINIMUM_TIME_PER_FRAME_MS = 16;
}
