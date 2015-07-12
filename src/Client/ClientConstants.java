package Client;

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
	public static final String VERSION = "1.0.0";
	/**
	 * The url to the list of news.
	 * <p>
	 * Value = {@value #NEWS_URL}
	 */
	// TODO change "localhost:12686" to real domain
	public static final String NEWS_URL = "http://localhost:12686/wp-admin/admin-ajax.php?action=news";
	/**
	 * The url to the current version.
	 * <p>
	 * Value = {@value #VERSION_URL}
	 */
	// TODO change "localhost:12686" to real domain
	public static final String VERSION_URL = "http://localhost:12686/wp-admin/admin-ajax.php?action=version";
	/**
	 * The title of the window.
	 * <p>
	 * Value = {@value #SCREEN_TITLE} 
	 */
	public static final String SCREEN_TITLE = "Ein Herz für Spiele";
	/**
	 * The icon of the window.
	 * <p>
	 * Value = {@value #SCREEN_ICON} 
	 */
	public static final String SCREEN_ICON = "/Assets/icon.png";
	/**
	 * The window width.
	 * <p>
	 * Value = {@value #SCREEN_WIDTH} 
	 */
	public static final int SCREEN_WIDTH = 800;
	/**
	 * The window height.
	 * <p>
	 * Value = {@value #SCREEN_HEIGHT} 
	 */
	public static final int SCREEN_HEIGHT = 600;
}
