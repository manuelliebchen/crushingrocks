package de.acagamics.framework.ui.interfaces;

public enum ALIGNMENT {
	CENTER(0.5f), LEFT(0), RIGHT(1), TOP(0), BOTTOM(1);
	
	private float value;
	
	private ALIGNMENT(float value) {
		this.value = value;
	}
	
	public float getValue() {
		return value;
	}
}
