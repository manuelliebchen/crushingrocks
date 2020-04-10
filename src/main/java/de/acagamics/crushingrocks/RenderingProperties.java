package de.acagamics.crushingrocks;

import java.util.List;

import javafx.scene.paint.Color;

public class RenderingProperties {

	private float baseRenderingMultiplier;
	
	private float unitRenderingMultiplier;

	private float unitSpeedupSize;

	private String backgroundColor;
	
	private List<Color> playerColors;

	private float overlayLineWidth;

	private Color healthForeground;

	private Color healthBackground;

	public float getBaseRenderingMultiplier() {
		return baseRenderingMultiplier;
	}

	public float getUnitRenderingMultiplier() {
		return unitRenderingMultiplier;
	}

	public float getUnitSpeedupSize() {
		return unitSpeedupSize;
	}

	public String getBackgroundColor() {
		return backgroundColor;
	}

	public List<Color> getPlayerColors() {
		return playerColors;
	}

	public float getOverlayLineWidth() {
		return overlayLineWidth;
	}
	
	public Color getHealthForeground() {
		return healthForeground;
	}

	public Color getHealthBackground() {
		return healthBackground;
	}
}
