
package de.acagamics.client.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import de.acagamics.game.controller.IPlayerController;
import de.acagamics.game.controller.builtIn.BotyMcBotface;
import de.acagamics.game.controller.builtIn.EmptyBotyMcBotface;
import de.acagamics.game.controller.builtIn.EvilSanta;
import de.acagamics.game.controller.builtIn.HumanBot;

public final class BotClassLoader extends ClassLoader {

	private final static Class<?>[] buildIn = new Class<?>[] { BotyMcBotface.class, EmptyBotyMcBotface.class,
			HumanBot.class, EvilSanta.class };

	/**
	 * List of all extern controller class names.
	 */
	List<Class<?>> controllerClasses;

	public BotClassLoader() {
		super();
		controllerClasses = new ArrayList<>();
		for (Class<?> bot : buildIn) {
			controllerClasses.add(bot);
		}
	}

	/**
	 * Parse directory for extern player controllers. Attention: Does not parse the
	 * directory recursively!
	 * 
	 * @param directoryFilename Directory where to look for player controller class
	 *                          files.
	 */
	public void loadControllerFromDirectory(String directoryFilename) {
		Stack<File> stackOfFiles = new Stack<>();
		for (File file : new File(directoryFilename).listFiles()) {
			stackOfFiles.push(file);
		}
		while (!stackOfFiles.isEmpty()) {
			File currentFile = stackOfFiles.pop();
			if (currentFile.isFile() && currentFile.getAbsolutePath().endsWith(".class")) {

				// Instantiate the controller
				IPlayerController controller = null;

				Class<?> controllerClass = null;
				controllerClass = defineClassFromFile(currentFile);
				if (controllerClass == null) {
					continue;
				}

				// Perform several validation checks on the supposed to be controller class.
				if (IPlayerController.class.isAssignableFrom(controllerClass)) {
					try {
						controller = (IPlayerController) controllerClass.getDeclaredConstructor().newInstance();
					} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
							| InvocationTargetException | NoSuchMethodException | SecurityException e) {
						System.err.println("Unable to load Controller from file.");
					}
				}

				if (controller != null) {
					controllerClasses.add(controller.getClass());
				}
			} else if (currentFile.isDirectory()) {
				for (File file : currentFile.listFiles()) {
					if (!file.getAbsolutePath().contains("de" + File.separator + "acagamics")) {
						stackOfFiles.push(file);
					}
				}
			}
		}
	}

	/**
	 * Instantiates an intern PlayerController from class name.
	 * 
	 * @param name Full package name of the class.
	 * @return New instance of player controller or null if something went wrong.
	 */
	public IPlayerController instantiateInternController(String name) {
		assert (name != null);

		Class<?> controllerClass = null;
		try {
			controllerClass = Class.forName(name);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try {
			return (IPlayerController) controllerClass.getDeclaredConstructor().newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Instantiates an already loaded extern player controller.
	 * 
	 * @param className Name of the class of with instance should be loaded.
	 * @return New instance of player controller or null if something went wrong.
	 */
	public IPlayerController instantiateLoadedExternController(String className) {
		Class<?> controllerClass;
		try {
			controllerClass = loadClass(className);
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
			return null;
		}
		Class<?>[] interfaces = controllerClass.getInterfaces();
		for (Class<?> iface : interfaces) {
			if (iface == IPlayerController.class) {
				try {
					return (IPlayerController) controllerClass.getDeclaredConstructor().newInstance();
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException | NoSuchMethodException | SecurityException e) {
					e.printStackTrace();
					return null;
				}
			}
		}
		return null;
	}

	protected Class<?> defineClassFromFile(File file) {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			byte[] bytes = new byte[fis.available()];
			int read = fis.read(bytes);
			if (read != bytes.length) {
				return null;
			}
			return defineClass(null, bytes, 0, bytes.length);
		} catch (IOException e) {
		} catch (LinkageError e) {
		} finally {

			try {
				fis.close();
			} catch (IOException e) {
			}
		}
		return null;
	}

	/**
	 * Returns a list of all extern controllers that have been loaded (not
	 * necessarily instantiated!).
	 * 
	 * @return List of class names.
	 */
	public List<Class<?>> getLoadedBots() {
		return new ArrayList<>(controllerClasses);
	}
}