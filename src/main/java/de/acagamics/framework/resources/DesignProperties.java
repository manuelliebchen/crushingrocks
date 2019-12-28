package de.acagamics.framework.resources;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * Class for all color const constants.
 * @author Manuel Liebchen
 *
 */
public final class DesignProperties {
	
	private FontProperty standart_font;
	
	public Font getStandartFont() {
		return standart_font.getFont();
	}
	
	private FontProperty title_font;
	
	public Font getTitleFont() {
		return title_font.getFont();
	}
	
	private FontProperty subtitle_font;
	
	public Font getSubtitleFont() {
		return subtitle_font.getFont();
	}
	
	private FontProperty medium_small_font;

	public Font getMediumSmallFont() {
		return medium_small_font.getFont();
	}

	private FontProperty small_font;
	
	public Font getSmallFont() {
		return small_font.getFont();
	}
	
	private String foreground_color;
	
	public Color getForegroundColor() {
		return Color.web(foreground_color);
	}
	
	private String background_color;
	
	public Color getBackgroundColor() {
		return Color.web(background_color);
	}

	private FontProperty button_font;
	
	public Font getButtonFont() {
		return button_font.getFont();
	}

	
	private String button_text_color;
	
	public Color getButtonTextColor() {
		return Color.web(button_text_color);
	}

	private int button_height;
	
	public int getButtonHeight() {
		return button_height;
	}
	
}
