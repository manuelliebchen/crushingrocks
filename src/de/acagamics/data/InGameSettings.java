package de.acagamics.data;

import java.util.ArrayList;
import java.util.List;

import de.acagamics.client.utility.BotClassLoader;
import de.acagamics.game.controller.IPlayerController;

public final class InGameSettings {
	
	public static enum GAMEMODE { NORMAL, XMAS_CHALLENGE };

	private BotClassLoader playerLoader;
	private List<String> names;
	private int speedMultiplier;
	
	private GAMEMODE mode;

	public InGameSettings(GAMEMODE mode, BotClassLoader playerLoader, List<String> controllers, int speedMultiplier) {
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
