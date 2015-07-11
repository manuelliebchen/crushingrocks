package Client.Rendering;

import javafx.scene.image.Image;

/**
 * Created by Claudius Grimm (claudius@acagamics.de)
 */
public class AnimatedImage {

    private int frameCount = 0;

    private Image[] frames;

    /**
     * Creates an animated image, from a series of images.
     * All images should be named the same with appended numbers from 0 to frames - 1.
     * @param pathToImage Path to the directory where the images are located.
     * @param animImageName Name of the animation images without the frame number
     * @param numFrames Number of frames the animation consists of.
     */
    public AnimatedImage(String pathToImage, String animImageName, int numFrames) {

        frames = new Image[numFrames];
        String imgPath = pathToImage + "/" + animImageName;

        for (int i = 0; i < numFrames; i++) {
            String framePath = imgPath + i;
            frames[i] = new Image(framePath, false);
        }

        frameCount = numFrames;
    }

    /**
     * Returns the animation image at the given frame number.
     * @param frame The frame number.
     * @return The image at the given frame number.
     */
    public Image draw(int frame) {
        int currFrame = Math.max(0, Math.min(frameCount - 1, frame));
        return frames[currFrame];
    }
}
