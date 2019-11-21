package de.acagamics.client.rendering.assetmanagment;

import javafx.scene.image.Image;
import org.apache.log4j.Logger;

import de.acagamics.constants.ClientConstants;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Claudius Grimm (claudius@acagamics.de)
 */
public final class ImageManager {

    private static final Logger LOG = Logger.getLogger(ImageManager.class);

    private static final ImageManager INSTANCE = new ImageManager();

    private ImageManager() {
        if (INSTANCE != null) {
            LOG.error("Already instantiated");
        }
    }

    /**
     * Get an instance of the ImageManager.
     * Singleton pattern.
     * @return instance of the ImageManager
     */
    public static ImageManager getInstance() {
        return INSTANCE;
    }

    private Map<String, Image> imageCache = new HashMap<>();

    /**
     * Loads an image from the given path. Also caches multiple loads of the same image.
     * @param imagePath Path to the image, as seen from the Asset package.
     * @return Image at the given path.
     */
    public Image loadImage(String imagePath) {
        assert imagePath != null;

        String assetPath = ClientConstants.ASSET_ROOT + imagePath;

        if (imageCache.containsKey(imagePath)) {
            return imageCache.get(imagePath);
        } else {
            Image img = new Image(assetPath, false);
            imageCache.put(imagePath, img);
            return img;
        }
    }

    private Map<String, AnimatedImage> animImageCache = new HashMap<>();

    /**
     * Loads an animated image from the given path.
     * Images should be handed as strings containing the frame names.
     * E.g. loadAnimatedImage("path/to/", "jump1.png", "jump2.png", ..., "jumpn.png");
     * @param pathToImages Path to the directory where the images are located, as seen from the de.acagamics.assets package.
     * @param imgNames Name of the frame images as string.
     * @return Animated image.
     */
    public AnimatedImage loadAnimatedImage(String pathToImages, String... imgNames) {
        assert pathToImages != null;
        assert imgNames != null;

        String assetPath = ClientConstants.ASSET_ROOT + pathToImages;

        if (animImageCache.containsKey(imgNames[0])) {
            return animImageCache.get(imgNames[0]);
        } else {
            AnimatedImage animg = new AnimatedImage(assetPath, imgNames);
            animImageCache.put(imgNames[0], animg);
            return animg;
        }
    }

    /**
     * Loads an animated image from the given path.
     * Images should be handed as strings containing the frame names.
     * E.g. loadAnimatedImage("path/to/", "jump1.png", "jump2.png", ..., "jumpn.png");
     * @param pathToImages Path to the directory where the images are located, as seen from the de.acagamics.assets package.
     * @param imgNames Name of the frame images as string.
     * @return Animated image.
     */
    public AnimatedImage loadAnimatedImage(float animationDuration, String pathToImages, String... imgNames) {
        assert pathToImages != null;
        assert imgNames != null;
        assert animationDuration > 0.0f;

        String assetPath = ClientConstants.ASSET_ROOT + pathToImages;

        if (animImageCache.containsKey(imgNames[0])) {
            return animImageCache.get(imgNames[0]);
        } else {
            AnimatedImage animg = new AnimatedImage(animationDuration, assetPath, imgNames);
            animImageCache.put(imgNames[0], animg);
            return animg;
        }
    }
}
