package de.acagamics.constants;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Class for all color const constants.
 * @author Max Klockmann (max@acagamics.de)
 *
 */
public final class DesignConstants {
	
	// http://paletton.com/#uid=70X1q0kpntDfEF8kMvZtppdvJk0
	
	public static final Font STANDART_FONT = Font.font(Font.getDefault().getFamily(), FontWeight.BOLD, 22);
	
	public static final Font LARGE_FONT = Font.font(Font.getDefault().getFamily(), FontWeight.BOLD, 48);

	public static final Color HEALTH_BACKGROUND = Color.color(0.741f, 0.153f, 0.486f);

	public static final Color HEALTH_FOREGROUND = Color.color(0.635f, 0.863f, 0.18f);

	public static final Color FOREGROUND_COLOR = Color.color(0f, 0f,0f);
	
	public static final Color BACKGROUND_COLOR = Color.color(0.392f, 0.584f, 0.04f);
	
	public static final Color[] PLAYER_COLORS = new Color[]{Color.color(0.925f, 0.741f, 0.192f), Color.color(0.18f, 0.314f, 0.62f), Color.color(0.467f, 0.824f, 0.173f), Color.color(0.60f, 0.125f, 0.60f)};

	public static final float OVERLAY_LINE_WIDTH = 0.005f;
	
	public enum ALINGNMENT { 
		CENTER(0.5f), LEFT(0), RIGHT(1), TOP(0), BOTTOM(1);
		
		private float value;
		
		private ALINGNMENT(float value) {
			this.value = value;
		}
		
		public float getValue() {
			return value;
		}
	}
}
