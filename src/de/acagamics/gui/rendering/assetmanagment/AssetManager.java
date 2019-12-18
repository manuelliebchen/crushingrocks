package de.acagamics.gui.rendering.assetmanagment;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import de.acagamics.constants.ClientConstants;
import de.acagamics.gui.elements.Background;
import javafx.scene.image.Image;
import javafx.scene.text.Font;

/**
 * @author Claudius Grimm (claudius@acagamics.de)
 */
public final class AssetManager {

    private static final AssetManager INSTANCE = new AssetManager();

    private AssetManager() {
    }

    /**
     * Get an instance of the ImageManager.
     * Singleton pattern.
     * @return instance of the ImageManager
     */
    public static AssetManager getInstance() {
        return INSTANCE;
    }
    
    
// IMAGES
    
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
            InputStream instream = this.getClass().getClassLoader().getResourceAsStream(assetPath);
            Image img = new Image(instream);
            imageCache.put(imagePath, img);
            return img;
        }
    }
    
    private Background background;
    
    /**
     * Background object for background of the game.
     * @return backgound.
     */
    public Background getBackground() {
    	if(background == null) {
    		background = new Background(100);
    	}
    	return background;
    }

    
//ANIMATIONS
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
    
//FONTS
    
    private Map<String, Font> fontCache = new HashMap<>();

    /**
     * Loads an font from the given path. Also caches multiple loads of the same font.
     * @param imagePath Path to the image, as seen from the Asset package.
     * @return Image at the given path.
     */
    public Font loadFont(String fontPath, double size) {
        assert fontPath != null;

        String assetPath = ClientConstants.ASSET_ROOT + fontPath;

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
