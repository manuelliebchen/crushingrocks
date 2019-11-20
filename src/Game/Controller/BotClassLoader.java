
package Game.Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import Game.Controller.BuiltIn.BotyMcBotface;
import Game.Controller.BuiltIn.EmptyBotyMcBotface;
import Game.Controller.BuiltIn.HumanBot;

public class BotClassLoader extends ClassLoader {

	/**
	 * List of all extern controller class names.
	 */
	List<Class<?>> controllerClasses;

	public BotClassLoader() {
		super();
		controllerClasses = new ArrayList<>();
		controllerClasses.add(BotyMcBotface.class);
		controllerClasses.add(EmptyBotyMcBotface.class);
		controllerClasses.add(HumanBot.class);
	}

	/**
	 * Parse directory for extern player controllers. Attention: Does not parse the
	 * directory recursively!
	 * 
	 * @param directoryFilename Directory where to look for player controller class
	 *                          files.
	 */
	public void loadControllerFromDirectory(String directoryFilename) {
		System.out.println("Reading bot classes from: " + directoryFilename);
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
				if(controllerClass == null) {
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
//				for (File file : currentFile.listFiles()) {
//					stackOfFiles.push(file);
//				}
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
		}finally {

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
	public List<Class<?>> getLoadedExternControllerClassNames() {
		return controllerClasses;
	}
}
