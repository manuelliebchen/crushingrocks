package de.acagamics.crushingrocks.rendering;

import de.acagamics.crushingrocks.logic.GameProperties;
import de.acagamics.framework.resources.ResourceManager;
import javafx.scene.paint.Color;

import java.util.List;

public class RenderingProperties {

	GameProperties gameProperties = ResourceManager.getInstance().loadProperties(GameProperties.class);

	private float baseRenderingMultiplier;
	
	private float unitRenderingMultiplier;

	private float unitSpeedupSize;

	private String backgroundColor;
	
	private List<Color> playerColors;

	private float overlayLineWidth;

	private Color healthForeground;

	private Color healthBackground;

	private float minVisibleMapRadius;

	public float getBaseRenderingRadius() {
		return gameProperties.getBaseRadius() * baseRenderingMultiplier;
	}

	public float getUnitRenderingRadius() {
		return gameProperties.getUnitRadius() *  unitRenderingMultiplier;
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

	public float getMinVisibleMapRadius() {
		return minVisibleMapRadius;
	}
}
