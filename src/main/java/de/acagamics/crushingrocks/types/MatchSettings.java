package de.acagamics.crushingrocks.types;

import de.acagamics.crushingrocks.logic.Game;
import de.acagamics.crushingrocks.logic.GameMode;
import de.acagamics.crushingrocks.logic.IPlayerController;
import de.acagamics.framework.simulation.Simulatable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

public class MatchSettings implements Supplier<Simulatable> {
	private static final Logger LOG = LogManager.getLogger(MatchSettings.class.getName());

	private GameMode mode;
	private List<Class<?>> controllersClasses;
	private Random random;

	public MatchSettings(GameMode mode, long seed, List<Class<?>> controllersClasses) {
		this.mode = mode;
		this.controllersClasses = controllersClasses;
		this.random = new Random(seed);
	}

	public GameMode getMode() {
		return mode;
	}

	public long getSeed() {
		return random.nextLong();
	}

	public List<Class<?>> getControllersClasses() {
		return controllersClasses;
	}

	public List<IPlayerController> getControllers() {
		List<IPlayerController> controllers = new ArrayList<>(controllersClasses.size());
		for(Class<?> controllerClass : controllersClasses) {
			try {
				controllers.add((IPlayerController) controllerClass.getDeclaredConstructor().newInstance());
			} catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
				LOG.error("while constructing bot", e);
			}
		}
		return controllers;
	}

	@Override
	public Simulatable get() {
		return new Game(this);
	}
}
