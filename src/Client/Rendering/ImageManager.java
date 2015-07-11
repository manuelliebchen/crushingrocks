package Client.Rendering;

import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Claudius Grimm (claudius@acagamics.de)
 */
public final class ImageManager {

    private static final ImageManager INSTANCE = new ImageManager();

    private ImageManager() {
        if (INSTANCE != null) {
            throw new IllegalStateException("Already instantiated");
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

        String assetPath = "Assets/" + imagePath;

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
     * Animations are stored as images with the same name and appended numbers from 0 to frame count - 1.
     * E.g. Jump0.png, Jump1.png, ..., Jump5.png for a frame count of 5.
     * @param pathToImage Path to the directory where the images are located, as seen from the Assets package.
     * @param imgName Name of the animation images without the frame number
     * @param numFrames Number of frames the animation consists of.
     * @return Animated image.
     */
    public AnimatedImage loadAnimatedImage(String pathToImage, String imgName, int numFrames) {
        assert pathToImage != null;
        assert imgName != null;
        assert numFrames > 1;

        String assetPath = "Assets/" + pathToImage;

        if (animImageCache.containsKey(imgName)) {
            return animImageCache.get(imgName);
        } else {
            AnimatedImage animg = new AnimatedImage(assetPath, imgName, numFrames);
            animImageCache.put(imgName, animg);
            return animg;
        }
    }

}
