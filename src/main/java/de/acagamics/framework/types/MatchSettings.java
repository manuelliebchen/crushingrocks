package de.acagamics.framework.types;

import java.util.ArrayList;
import java.util.List;

import de.acagamics.crushingrocks.controller.IPlayerController;
import de.acagamics.framework.client.utility.BotClassLoader;

public final class MatchSettings {
	
	public static enum GAMEMODE { NORMAL, XMAS_CHALLENGE };

	private BotClassLoader playerLoader;
	private List<String> names;
	private int speedMultiplier;
	
	private GAMEMODE mode;

	public MatchSettings(GAMEMODE mode, BotClassLoader playerLoader, List<String> controllers, int speedMultiplier) {
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

	public List<IPlayerController> getControllers() {
		List<IPlayerController> controller = new ArrayList<>(names.size());
		for(String name : names) {
			controller.add(playerLoader.instantiateLoadedExternController(name));
		}
		return controller;
	}

}
