package de.acagamics.framework.resourcemanagment;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * Class for all color const constants.
 * @author Manuel Liebchen
 *
 */
public final class DesignProperties {
	
	// http://paletton.com/#uid=70X1q0kpntDfEF8kMvZtppdvJk0
	
	public static final Font STANDART_FONT = ResourceManager.getInstance().loadFont("breathe_fire.otf", 32);
	
	public static final Font BUTTON_FONT = ResourceManager.getInstance().loadFont("breathe_fire.otf", 32);

	public static final Color BUTTON_TEXT_COLOR = Color.web("#000000");
	
	public static final Font TITLE_FONT = ResourceManager.getInstance().loadFont("breathe_fire.otf", 72);
	
	public static final Font SECOND_TITLE_FONT = ResourceManager.getInstance().loadFont("breathe_fire.otf", 42);
	
	public static final Font MEDIUM_SMALL_FONT = ResourceManager.getInstance().loadFont("breathe_fire.otf", 24);

	public static final Font SMALL_FONT = ResourceManager.getInstance().loadFont("breathe_fire.otf", 16);

	public static final Color FOREGROUND_COLOR = Color.web("#BD277C");
	
	public static final Color BACKGROUND_COLOR = Color.web("#82BB0F");
	
	public static final int BUTTON_HEIGHT = 70;
	
}
