package de.acagamics.crushingrocks;

import java.util.List;

import javafx.scene.paint.Color;

public class RenderingProperties {

	private float base_rendering_multiplier;
	
	private float unit_rendering_multiplier;

	private String background_color;
	
	private List<Color> player_colors;

	private float overlay_line_width;

	private Color health_foreground;

	private Color health_background;

	public float getBaseRenderingMultiplier() {
		return base_rendering_multiplier;
	}

	public float getUnitRenderingMultiplier() {
		return unit_rendering_multiplier;
	}

	public String getBackgroundColor() {
		return background_color;
	}

	public List<Color> getPlayerColors() {
		return player_colors;
	}

	public float getOverlayLineWidth() {
		return overlay_line_width;
	}
	
	public Color getHealthForeground() {
		return health_foreground;
	}

	public Color getHealthBackground() {
		return health_background;
	}
}
