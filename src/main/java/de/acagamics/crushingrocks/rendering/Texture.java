package de.acagamics.crushingrocks.rendering;

import de.acagamics.framework.geometry.Vec2f;
import de.acagamics.framework.resources.ResourceManager;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Texture {
    private Vec2f offset;
    private Image image;
    private float ratio;
    private float radius;
    private boolean flip;

    /**
     * Texture with offset and size.
     * @param offset The offset the upper left corner has to the center.
     * @param file The file in der resources directory to be used.
     * @param radius The size of the texture in the half of the width.
     * @param flip Whether or not to filp the texture.
     */
     public Texture(Vec2f offset, String file, float radius, boolean flip){
        this.offset = offset;
        this.radius = radius;
        this.flip = flip;
        image = ResourceManager.getInstance().loadImage(file);
        ratio = (float) (image.getWidth() / image.getHeight() + 1) / 2;
    }

    public void draw(GraphicsContext context, Vec2f position) {
        float flipFactor = flip ? 1 : -1;
        float renderWidth = radius
                * ( 1 + position.getY() * 0.5f) * ratio;
        float renderHeight = radius
                * ( 1 + position.getY() * 0.5f) / ratio;
        context.drawImage(image, position.getX() + offset.getX() * renderWidth - (flipFactor * renderWidth),
                position.getY() + offset.getY() * renderHeight - renderHeight, 2 * flipFactor * renderWidth,
                2 * renderHeight);
    }

}
