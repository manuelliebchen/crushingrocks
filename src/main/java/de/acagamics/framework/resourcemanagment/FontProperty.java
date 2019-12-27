package de.acagamics.framework.resourcemanagment;

import javafx.scene.text.Font;

public class FontProperty {

	private String file;
	private int size;

	public Font getFont() {
		return ResourceManager.getInstance().loadFont(file, size);
	}
}
