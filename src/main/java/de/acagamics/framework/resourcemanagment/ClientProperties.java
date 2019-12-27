package de.acagamics.framework.resourcemanagment;

/**
 * Class for various client relevant constants.
 * @author Max Klockmann (max@acagamics.de)
 *
 */
public class ClientProperties {
	/**
	 * The title of the window. 
	 */
	private String title;
	
	public String getTitle() {
		return title;
	}
	
	/**
	 * The version of this game.
	 */
	private String version;
	
	public String getVersion() {
		return version;
	}
	
	/**
	 * The url to the list of news.
	 */
	private String news_url;
	
	public String getNewsURL() {
		return news_url;
	}
	
	/**
	 * The url to the current version.
	 */
	private	String version_url;
	
	public String getVersionURL() {
		return version_url;
	}
	
//	/**
//	 * The icon of the window.
//	 * <p>
//	 * Value = {@value #SCREEN_ICON} 
//	 */
//	public static final String SCREEN_ICON = "icon.png";
//	/**
//	 * The asset manager root folder, all assets are relative to this path.
//	 * <p>
//	 * Value = {@value #ASSET_ROOT}
//	 */
//	public static final String ASSET_ROOT = "";
	/**
	 * The window width.
	 */
	private int screen_width;
	
	public int getScreenWidth() {
		return screen_width;
	}
	
	/**
	 * The window height.
	 */
	private int screen_height;
	
	public int getScreenHeight() {
		return screen_height;
	}
	
	/**
	 * Targeted frame rate
	 */
	private int framerate;
	
	public int getMilisPerFrame() {
		return 1000/framerate;
	}
}
