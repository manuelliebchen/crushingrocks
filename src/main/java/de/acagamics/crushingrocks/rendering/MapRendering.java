package de.acagamics.crushingrocks.rendering;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import de.acagamics.crushingrocks.GameProperties;
import de.acagamics.crushingrocks.RenderingProperties;
import de.acagamics.crushingrocks.logic.*;
import de.acagamics.framework.resources.ResourceManager;
import de.acagamics.framework.types.Box2f;
import de.acagamics.framework.types.Vec2f;
import de.acagamics.framework.ui.interfaces.IDrawable;
import de.acagamics.framework.ui.interfaces.GameObject;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.transform.Affine;

/**
 * @author Claudius Grimm (claudius@acagamics.de)
 * @author Manuel Liebchen
 */
public final class MapRendering implements IDrawable {

	private Map gameMap;

	private List<GameObject> textures;

	private MapOverlayRendering mapOverlayRenderer;

	private java.util.Map<String, Texture> textureMap;

	/**
	 * Used to render the game map.
	 * 
	 * @param inGameMap The map to render.
	 */
	public MapRendering(Map inGameMap) {
		Random random = new Random();


		RenderingProperties renderingProperties = ResourceManager.getInstance().loadProperties(RenderingProperties.class);
		GameProperties gamePropertiese = ResourceManager.getInstance().loadProperties(GameProperties.class);

		textureMap = new java.util.HashMap<>();
		textureMap.put("Log", new Texture(new Vec2f(0, -0.2f), "Log.png", 0.05f, false));
		textureMap.put("Blumengras", new Texture(new Vec2f(0 , 0.1f), "Blumengras.png", 0.075f, false));
		textureMap.put("!Blumengras", new Texture(new Vec2f(0 , 0.1f), "Blumengras.png", 0.075f, true));
		textureMap.put("Blumengras2", new Texture(new Vec2f(0 , 0.1f), "Blumengras2.png", 0.075f, false));
		textureMap.put("!Blumengras2", new Texture(new Vec2f(0 , 0.1f), "Blumengras2.png", 0.075f, true));
		textureMap.put("Ressource", new Texture(new Vec2f(0, -0.1f), "Ressource.png", 0.025f, true));
		textureMap.put("Stein", new Texture(new Vec2f(0, -0.1f), "Stein.png", 0.075f, true));
		textureMap.put("Baum", new Texture(new Vec2f(0, -0.8f), "Baum.png", 0.1f, true));

		textureMap.put("Base0", new Texture(new Vec2f(0, -0.25f), "Base0.png", renderingProperties.getBaseRenderingRadius(), true));
		textureMap.put("Base1", new Texture(new Vec2f(0, -0.25f), "Base1.png", renderingProperties.getBaseRenderingRadius(), true));
		textureMap.put("Mine", new Texture(new Vec2f(0, -0.5f), "Mine.png", gamePropertiese.getMineRadius() * 0.75f, true));
		textureMap.put("Unit1", new Texture(new Vec2f(), "Unit1.png", renderingProperties.getUnitRenderingRadius(), true));
		textureMap.put("Unit2", new Texture(new Vec2f(), "Unit2.png", renderingProperties.getUnitRenderingRadius(), true));
		textureMap.put("Unit3", new Texture(new Vec2f(), "Unit3.png", renderingProperties.getUnitRenderingRadius(), true));

		gameMap = inGameMap;
		mapOverlayRenderer = new MapOverlayRendering(gameMap);

		float mapRadius = GameProperties.get().getMapRadius();
		textures = new ArrayList<>();
		textures.addAll(Flavor.createFlavors(Arrays.asList("Blumengras","Blumengras2","!Blumengras","!Blumengras2"), 1000, v -> v.getY() > -mapRadius * 0.7, 1.5f * mapRadius, random));
		textures.addAll(Flavor.createFlavors(Arrays.asList("Ressource"), 100, v -> v.getY() > -mapRadius * 0.7, 1.5f * mapRadius, random));
		textures.addAll(Flavor.createFlavors(Arrays.asList("Log"), 100, v -> v.getY() > -mapRadius * 0.7, 1.5f * mapRadius, random));
		textures.addAll(Flavor.createFlavors(Arrays.asList("Stein"), 100, v -> v.getY() > -mapRadius * 0.7, 1.5f *mapRadius, random));
		textures.addAll(Flavor.createFlavors(Arrays.asList("Baum"), 2000, v -> v.getY() > -mapRadius * 0.7 && v.length() > mapRadius * 1.2, 3 * mapRadius, random));

		textures.addAll(gameMap.getBases());
		textures.addAll(gameMap.getMines());
	}

	/**
	 * Displays the map images etc.
	 * 
	 * @param context Context to draw on.
	 */
	public void draw(GraphicsContext context) {
		context.save();

		Affine transformation = calcultateTransformation(context.getCanvas());
		context.setTransform(transformation);

		List<GameObject> gameObjects = new ArrayList<>();
		gameObjects.addAll(gameMap.getAllUnits());
		gameObjects.addAll(textures);

		clip(gameObjects, context.getCanvas());

		gameObjects.sort((f1,f2)-> {float diff = f1.getPosition().getY() - f2.getPosition().getY();
			if(diff == 0){
				return 0;
			}
			return diff > 0 ? 1 : -1;
		});

		for(GameObject object : gameObjects){
			textureMap.get(getTexture(object)).draw(context, object.getPosition());
		}

		context.restore();


		mapOverlayRenderer.draw(context, transformation);
	}

	Affine calcultateTransformation(Canvas canvas) {
		GameProperties gameProperties = ResourceManager.getInstance().loadProperties(GameProperties.class);
		RenderingProperties renderingProperties = ResourceManager.getInstance().loadProperties(RenderingProperties.class);
		Affine transformation = new Affine();

		transformation.appendTranslation(canvas.getWidth() / 2, canvas.getHeight() / 2);
		float scalation = gameProperties.getMapRadius() * renderingProperties.getMinVisibleMapRadius();
		if (canvas.getHeight() > canvas.getWidth()) {
			scalation = (float) canvas.getWidth() / scalation;
		} else {
			scalation = (float) canvas.getHeight() / scalation;
		}
		transformation.appendScale(scalation,
				scalation);
		return transformation;
	}

	void clip(List<GameObject> gameObjects, Canvas canvas){
		RenderingProperties renderingProperties = ResourceManager.getInstance().loadProperties(RenderingProperties.class);
		Box2f clipping;
		if (canvas.getHeight() > canvas.getWidth()) {
			float factor = (float) (canvas.getHeight() / canvas.getWidth());
			Vec2f position = new Vec2f(- renderingProperties.getMinVisibleMapRadius() / 2, - renderingProperties.getMinVisibleMapRadius() * factor / 2 );
			clipping = new Box2f(position, renderingProperties.getMinVisibleMapRadius(), renderingProperties.getMinVisibleMapRadius()* factor);
		} else {
			float factor = (float) (canvas.getWidth() / canvas.getHeight());
			Vec2f position = new Vec2f(- renderingProperties.getMinVisibleMapRadius() * factor / 2, - renderingProperties.getMinVisibleMapRadius() / 2 );
			clipping = new Box2f(position, renderingProperties.getMinVisibleMapRadius() * factor, renderingProperties.getMinVisibleMapRadius());
		}

		gameObjects.removeIf(g -> !clipping.isInside(g.getPosition()));
	}

	String getTexture(GameObject object) {
		if(object instanceof Unit){
			Unit unit = (Unit) object;
			if (unit.getStrength() >= 3) {
				return "Unit3";
			} else if (unit.getStrength() >= 2) {
				return "Unit2";
			} else {
				return "Unit1";
			}
		} else if(object instanceof Base) {
			return "Base" + ((Base) object).getOwner().getPlayerID();
		} else if(object instanceof Flavor) {
			return ((Flavor) object).getType();
		}
		return object.getClass().getSimpleName();
	}
}
