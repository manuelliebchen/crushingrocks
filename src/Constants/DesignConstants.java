package Constants;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Class for all color const constants.
 * @author Max Klockmann (max@acagamics.de)
 *
 */
public final class DesignConstants {
	
	public static final Font STANDART_FONT = Font.font(Font.getDefault().getFamily(), FontWeight.BOLD, 24);
	
	public static final Font LARGE_FONT = Font.font(Font.getDefault().getFamily(), FontWeight.BOLD, 48);

	public static final Color HEALTH_BACKGROUND = Color.RED;

	public static final Color HEALTH_FOREGROUND = Color.GREENYELLOW;

	public static final Color FOREGROUND_COLOR = Color.color(0f, 0f,0f);
	
	public static final Color BACKGROUND_COLOR = Color.color(1f, 1f,1f);
	
	public static final Color[] PLAYER_COLORS = new Color[]{Color.color(0.925f, 0.741f, 0.192f), Color.color(0.18f, 0.314f, 0.62f), Color.color(0.467f, 0.824f, 0.173f), Color.color(0.60f, 0.125f, 0.60f)};

	public enum Alignment { CENTER, LEFT, RIGHT, TOP, BOTTOM}
}
