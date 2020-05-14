package de.acagamics.crushingrocks.rendering;

import de.acagamics.framework.geometry.Vec2f;
import de.acagamics.framework.ui.interfaces.GameObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

public class Flavor extends GameObject {
    String type;

    public Flavor(String type, Vec2f position) {
        super(position);
        this.type = type;
    }

    public String getType() {
        return type;
    }

    static List<GameObject> createFlavors(List<String> texture, int n, Predicate<Vec2f> pred, float distRadius, Random random) {
        List<GameObject> textures = new ArrayList<>(n);
        for(;n > 0; --n){
            Vec2f pos;
            do {
                pos = new Vec2f(2 * distRadius * (random.nextFloat() * 2 - 1),
                        (2 * distRadius * ((float) Math.pow(random.nextFloat(), 2) * 2 - 1)));
            } while( !pred.test(pos));
            textures.add(new Flavor(texture.get(random.nextInt(texture.size())), pos));
        }
        return textures;
    }
}
