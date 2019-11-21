package de.acagamics.client.gui.states;

import java.util.ArrayList;
import java.util.List;

import de.acagamics.game.controller.BotClassLoader;
import de.acagamics.game.controller.IPlayerController;

public class InGameSettings {

	private BotClassLoader playerLoader;
	private List<String> names;
	private int speedMultiplier;

	InGameSettings(BotClassLoader playerLoader, List<String> controllers, int speedMultiplier) {
		this.playerLoader = playerLoader;
		this.speedMultiplier = speedMultiplier;
		this.names = controllers;
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
