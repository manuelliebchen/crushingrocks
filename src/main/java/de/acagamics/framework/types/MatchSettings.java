package de.acagamics.framework.types;

import java.util.ArrayList;
import java.util.List;

import de.acagamics.framework.util.BotClassLoader;

public final class MatchSettings<T> {
	
	public static enum GAMEMODE { NORMAL, XMAS_CHALLENGE };

	private BotClassLoader<T> playerLoader;
	private List<String> names;
	private int speedMultiplier;
	
	private GAMEMODE mode;

	public MatchSettings(GAMEMODE mode, BotClassLoader<T> playerLoader, List<String> controllers, int speedMultiplier) {
		this.mode = mode;
		this.playerLoader = playerLoader;
		this.speedMultiplier = speedMultiplier;
		this.names = controllers;
	}
	
	public GAMEMODE getMode() {
		return mode;
	}

	public int getSpeedMultiplier() {
		return speedMultiplier;
	}

	public List<T> getControllers() {
		List<T> controller = new ArrayList<>(names.size());
		for(String name : names) {
			controller.add(playerLoader.instantiateLoadedExternController(name));
		}
		return controller;
	}

}
