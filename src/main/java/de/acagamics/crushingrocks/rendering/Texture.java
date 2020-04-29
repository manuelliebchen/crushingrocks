package de.acagamics.crushingrocks.rendering;

import de.acagamics.framework.resources.ResourceManager;
import de.acagamics.framework.types.Vec2f;
import javafx.scene.canvas.GraphicsContext;

public class Texture {
    Vec2f offset;
    String file;
    float radius;
    boolean flip;

    Texture(Vec2f offset, String file, float radius, boolean flip){
        this.offset = offset;
        this.file = file;
        this.radius = radius;
        this.flip = flip;
    }

    void draw(GraphicsContext context, Vec2f position) {
        float flipFactor = flip ? 1 : -1;
        float renderRadius = radius
                * ( 1 + position.getY() * 0.5f);
        context.drawImage(ResourceManager.getInstance().loadImage(file), position.getX() + offset.getX() * renderRadius - (flipFactor * renderRadius),
                position.getY() + offset.getY() * renderRadius - renderRadius, 2 * flipFactor * renderRadius,
                2 * renderRadius);
    }

}
