package Client.GUI.States;

import java.util.ArrayList;
import java.util.List;

import Game.Controller.IPlayerController;
import Game.Controller.PlayerControllerLoader;

public class InGameSettings {

	private PlayerControllerLoader playerLoader;
	private List<String> names;
	private int speedMultiplier;

	InGameSettings(PlayerControllerLoader playerLoader, List<String> controllers, int speedMultiplier) {
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
			controller.add(playerLoader.instantiateInternController(name));
		}
		return controller;
	}

}
