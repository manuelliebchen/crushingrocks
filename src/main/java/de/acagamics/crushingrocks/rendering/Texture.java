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
        context.drawImage(ResourceManager.getInstance().loadImage(file), position.getX() - (flipFactor * radius),
                position.getY() - radius, 2 * flipFactor * radius,
                2 * radius);
    }

}
