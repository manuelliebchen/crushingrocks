package de.acagamics.crushingrocks.logic;

import de.acagamics.crushingrocks.GameProperties;
import de.acagamics.framework.types.Vec2f;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

public class Flavor {
    Vec2f position;
    String texture;
    float radius;

    Flavor(Vec2f position, String texture, float radius){
        this.position = position;
        this.texture = texture;
        this.radius = radius;
    }

    public Vec2f getPosition() {
        return position;
    }

    public String getTexture() {
        return texture;
    }

    public float getRadius() {
        return radius;
    }

    static List<Flavor> createFlavors(int n, Predicate<Vec2f> pred, float distRadius, String texture, float radius, Random random) {
        List<Flavor> flavors = new ArrayList<>(n);
        for(;n > 0; --n){
            Vec2f pos;
            do {
                pos = new Vec2f(2 * distRadius * (random.nextFloat() * 2 - 1),
                                2 * distRadius * (random.nextFloat() * 2 - 1));
            } while( !pred.test(pos));
            flavors.add(new Flavor(pos, texture, radius));
        }
        return flavors;
    }

}
