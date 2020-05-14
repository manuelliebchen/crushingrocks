package de.acagamics.crushingrocks.rendering;

import de.acagamics.framework.geometry.Vec2f;
import de.acagamics.framework.resources.ResourceManager;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Texture {
    Vec2f offset;
    Image image;
    float ratio;
    float radius;
    boolean flip;

    Texture(Vec2f offset, String file, float radius, boolean flip){
        this.offset = offset;
        this.radius = radius;
        this.flip = flip;
        image = ResourceManager.getInstance().loadImage(file);
        ratio = (float) (image.getWidth() / image.getHeight() + 1) / 2;
    }

    void draw(GraphicsContext context, Vec2f position) {
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
