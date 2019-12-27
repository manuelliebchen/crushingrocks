package de.acagamics.framework.resourcemanagment;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.introspector.BeanAccess;

import javafx.scene.image.Image;
import javafx.scene.text.Font;

/**
 * @author Claudius Grimm (claudius@acagamics.de)
 * @author Manuel Liebchen
 */
public final class ResourceManager {
	private static final Logger LOG = LogManager.getLogger(ResourceManager.class.getName());

	private static final ResourceManager INSTANCE = new ResourceManager();

	private ResourceManager() {
	}

	/**
	 * Get an instance of the ImageManager. Singleton pattern.
	 * 
	 * @return instance of the ImageManager
	 */
	public static ResourceManager getInstance() {
		return INSTANCE;
	}

	private Map<String, Object> porpertiesCache = new HashMap<>();

	@SuppressWarnings("unchecked")
	public <T> T loadProperties(Class<T> type) {
		String file_name = type.getSimpleName() + ".yaml";
		if (porpertiesCache.containsKey(file_name)) {
			return (T) porpertiesCache.get(file_name);
		}
		LOG.debug("Loading Property file: " + file_name);
		Yaml yaml = new Yaml();
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(file_name);
		yaml.setBeanAccess(BeanAccess.FIELD);
		T properties = yaml.loadAs(inputStream, type);
		porpertiesCache.put(file_name, properties);
		return properties;
	}

// IMAGES

	private Map<String, Image> imageCache = new HashMap<>();

	/**
	 * Loads an image from the given path. Also caches multiple loads of the same
	 * image.
	 * 
	 * @param imagePath Path to the image, as seen from the Asset package.
	 * @return Image at the given path.
	 */
	public Image loadImage(String imagePath) {
		assert imagePath != null;

		String assetPath = imagePath;

		if (imageCache.containsKey(imagePath)) {
			return imageCache.get(imagePath);
		} else {
			InputStream instream = this.getClass().getClassLoader().getResourceAsStream(assetPath);
			Image img = new Image(instream);
			imageCache.put(imagePath, img);
			return img;
		}
	}

//ANIMATIONS
	private Map<String, AnimatedImage> animImageCache = new HashMap<>();

	/**
	 * Loads an animated image from the given path. Images should be handed as
	 * strings containing the frame names. E.g. loadAnimatedImage("path/to/",
	 * "jump1.png", "jump2.png", ..., "jumpn.png");
	 * 
	 * @param pathToImages Path to the directory where the images are located, as
	 *                     seen from the de.acagamics.assets package.
	 * @param imgNames     Name of the frame images as string.
	 * @return Animated image.
	 */
	public AnimatedImage loadAnimatedImage(String pathToImages, String... imgNames) {
		assert pathToImages != null;
		assert imgNames != null;

		String assetPath = pathToImages;

		if (animImageCache.containsKey(imgNames[0])) {
			return animImageCache.get(imgNames[0]);
		} else {
			AnimatedImage animg = new AnimatedImage(assetPath, imgNames);
			animImageCache.put(imgNames[0], animg);
			return animg;
		}
	}

	/**
	 * Loads an animated image from the given path. Images should be handed as
	 * strings containing the frame names. E.g. loadAnimatedImage("path/to/",
	 * "jump1.png", "jump2.png", ..., "jumpn.png");
	 * 
	 * @param animationDuration duration of the animation.
	 * @param pathToImages      Path to the directory where the images are located,
	 *                          as seen from the de.acagamics.assets package.
	 * @param imgNames          Name of the frame images as string.
	 * @return Animated image.
	 */
	public AnimatedImage loadAnimatedImage(float animationDuration, String pathToImages, String... imgNames) {
		assert pathToImages != null;
		assert imgNames != null;
		assert animationDuration > 0.0f;

		String assetPath = pathToImages;

		if (animImageCache.containsKey(imgNames[0])) {
			return animImageCache.get(imgNames[0]);
		} else {
			AnimatedImage animg = new AnimatedImage(animationDuration, assetPath, imgNames);
			animImageCache.put(imgNames[0], animg);
			return animg;
		}
	}

//FONTS

	private Map<String, Font> fontCache = new HashMap<>();

	/**
	 * Loads an font from the given path. Also caches multiple loads of the same
	 * font.
	 * 
	 * @param fontPath Path to the font file
	 * @param size     Size of the font.
	 * @return Image at the given path.
	 */
	public Font loadFont(String fontPath, double size) {
		assert fontPath != null;

		String assetPath = fontPath;

		fontPath += String.valueOf(size);

		if (fontCache.containsKey(fontPath)) {
			return fontCache.get(fontPath);
		} else {
			Font font = null;
			font = Font.loadFont(this.getClass().getClassLoader().getResourceAsStream(assetPath), size);
			fontCache.put(fontPath, font);
			return font;
		}
	}

}
