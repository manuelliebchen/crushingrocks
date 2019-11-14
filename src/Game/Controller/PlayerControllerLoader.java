package Game.Controller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import Client.Main;

/**
 * Class for loading of extern/intern PlayerController.
 * 
 * @author Andreas Reich (andreas@acagamics.de)
 *
 */
public final class PlayerControllerLoader {
	private static final Logger LOG = Logger.getLogger(Main.class);

	/**
	 * Class loader for player-controller class files.
	 */
	private FileClassLoader fileClassLoader;

	/**
	 * List of all extern controller class names.
	 */
	List<String> externControllerClassNames = new ArrayList<String>();
	/**
	 * List of all extern controller display names (obtained
	 * via @see{IPlayerController}.getName()).
	 */
	List<String> externControllerDisplayNames = new ArrayList<String>();

	/**
	 * Creates new player controller loader.
	 */
	public PlayerControllerLoader() {
		fileClassLoader = new FileClassLoader(new URL[] {}, ClassLoader.getSystemClassLoader());
	}

	/**
	 * Returns a list of all extern controllers that have been loaded (not
	 * necessarily instantiated!).
	 * 
	 * @return List of class names.
	 */
	public List<String> getLoadedExternControllerClassNames() {
		return externControllerClassNames;
	}

	/**
	 * Returns a list of the display names (@see{IPlayerController}.getName()) of
	 * all extern controllers that have been loaded (not necessarily instantiated!).
	 * 
	 * @return List of display names.
	 */
	public List<String> getLoadedExternControllerDisplayNames() {
		return externControllerDisplayNames;
	}

	/**
	 * Parse directory for extern player controllers. Attention: Does not parse the
	 * directory recursively!
	 * 
	 * @param directoryFilename Directory where to look for player controller class
	 *                          files.
	 */
	public void loadControllerFromDirectory(String directoryFilename) {
		File folder = new File(directoryFilename);
		File[] listOfFiles = folder.listFiles();
		if (listOfFiles != null) {
			for (int i = 0; i < listOfFiles.length; i++) {
				String filename = listOfFiles[i].getAbsolutePath();
				if (listOfFiles[i].isFile() && filename.endsWith(".class")) {
					File classFile = new File(filename);
					if (!classFile.exists()) {
						continue;
					}
					// add url
					try {
						URL url = new URL(
								"file:" + File.separator + FileSystems.getDefault().getPath("").toAbsolutePath());
						fileClassLoader.addURL(url);
					} catch (MalformedURLException e) {
						e.printStackTrace();
						continue;
					}
				}
			}
			for (int i = 0; i < listOfFiles.length; i++) {
				String filename = listOfFiles[i].getAbsolutePath();
				if (listOfFiles[i].isFile() && filename.endsWith(".class")) {
					File classFile = new File(filename);
					if (!classFile.exists()) {
						continue;
					}
					// load subclasses
					if (filename.contains("$")) {
						try {
							fileClassLoader.createClass(classFile);
						} catch (IOException e) {
							e.printStackTrace();
							continue;
						}
						// Load player controller
					} else {
						// Instantiate the controller
						IPlayerController controller = createControllerFromFile(filename);
						if (controller != null) {
							// output
							String name = controller.getName();
							if (name == null) {
								LOG.warn("The name (getName) for player controller " + controller.getClass().getName()
										+ " is null!");
								name = "null";
							}

							externControllerDisplayNames.add(name);
							externControllerClassNames.add(controller.getClass().getName());
						}
					}
				}
			}
		}
	}

	/**
	 * Tries to create a PlayerController from extern class file.
	 * 
	 * @return Working IPlayerController class or null if something went wrong.
	 */
	private IPlayerController createControllerFromFile(String filename) {
		File classFile = new File(filename);
		if (!classFile.exists()) {
			return null;
		}

		Class<?> controllerClass = null;
		try {
			controllerClass = fileClassLoader.createClass(classFile);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Load dependencies (if any).
		String className = controllerClass.getCanonicalName();
		int levels = className.replaceAll("[^.]*", "").length();
		File root = classFile.getParentFile();
		for (int i = 0; i < levels; i++) {
			root = root.getParentFile();
		}
		try {
			fileClassLoader.addURL(root.toURI().toURL());
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		}

		// Perform several validation checks on the supposed to be controller class.
		if (!IPlayerController.class.isAssignableFrom(controllerClass)) {
			LOG.error("Extern player controller " + controllerClass.getName() + " is not a IPlayerController!");
		} else if (controllerClass.isMemberClass()) {
			LOG.error("Extern player controller " + controllerClass.getName() + " is a member class!");
		} else if (controllerClass.isInterface()) {
			LOG.error("Extern player controller " + controllerClass.getName() + " is a interface!");
		} else if (controllerClass.isAnonymousClass()) {
			LOG.error("Extern player controller " + controllerClass.getName() + " is anonymous!");
		} else {
			return instantiateLoadedExternController(controllerClass.getName());
		}

		return null;
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
			controllerClass = fileClassLoader.loadClass(className);
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
			return null;
		}

		try {
			return (IPlayerController) controllerClass.getDeclaredConstructor().newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
