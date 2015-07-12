package Client.Rendering.Drawing;

import javafx.scene.image.Image;

/**
 * Created by Claudius Grimm (claudius@acagamics.de)
 */
public final class AnimatedImage {

    private int frameCount = 0;
    private Image[] frames;

    /**
     * Creates an animated image, from a series of images.
     * Images should be handed as strings containing the frame names.
     * @param pathToImages Path to the directory where the images are located.
     * @param animImageNames Name of the frame images as string.
     */
    public AnimatedImage(String pathToImages, String... animImageNames ) {

        frameCount = animImageNames.length;
        frames = new Image[frameCount];

        for (int i = 0; i < frameCount; i++) {
            String framePath = pathToImages + animImageNames[i];
            frames[i] = new Image(framePath, false);
        }
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
