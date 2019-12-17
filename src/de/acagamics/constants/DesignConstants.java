package de.acagamics.constants;

import de.acagamics.gui.rendering.assetmanagment.AssetManager;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * Class for all color const constants.
 * @author Max Klockmann (max@acagamics.de)
 *
 */
public final class DesignConstants {
	
	// http://paletton.com/#uid=70X1q0kpntDfEF8kMvZtppdvJk0
	
	public static final Font STANDART_FONT = AssetManager.getInstance().loadFont("breathe_fire.otf", 32);
	
	public static final Font BUTTON_FONT = AssetManager.getInstance().loadFont("breathe_fire.otf", 32);

	public static final Color BUTTON_TEXT_COLOR = Color.web("#000000");
	
	public static final Font TITLE_FONT = AssetManager.getInstance().loadFont("breathe_fire.otf", 72);

	public static final Font SMALL_FONT = AssetManager.getInstance().loadFont("breathe_fire.otf", 16);
	
	public static final Font SECOND_TITLE_FONT = AssetManager.getInstance().loadFont("breathe_fire.otf", 42);

	public static final Color HEALTH_BACKGROUND = Color.color(0.741f, 0.153f, 0.486f);

	public static final Color HEALTH_FOREGROUND = Color.color(0.635f, 0.863f, 0.18f);

	public static final Color FOREGROUND_COLOR = Color.web("#BD277C");
	
	public static final Color BACKGROUND_COLOR = Color.web("#82BB0F");
	
	public static final Color GAME_BACKGROUND_COLOR = Color.web("#82BB0F");
	
	public static final Color[] PLAYER_COLORS = new Color[]{Color.color(0.925f, 0.741f, 0.192f), Color.color(0.18f, 0.314f, 0.62f), Color.color(0.467f, 0.824f, 0.173f), Color.color(0.60f, 0.125f, 0.60f)};

	public static final float OVERLAY_LINE_WIDTH = 0.005f;
	
	public static final int BUTTON_HEIGHT = 70;
	
	public static final float BASE_RENDERING_SIZE_MULTIPLIER = 2;
	
	public static final float UNIT_RENDERING_SIZE_MULTIPLIER = 2;
	
}
