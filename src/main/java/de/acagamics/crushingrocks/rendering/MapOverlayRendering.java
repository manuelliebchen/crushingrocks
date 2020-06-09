package de.acagamics.crushingrocks.rendering;

import de.acagamics.crushingrocks.logic.*;
import de.acagamics.framework.geometry.Vec2f;
import de.acagamics.framework.resources.DesignProperties;
import de.acagamics.framework.resources.ResourceManager;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.ArcType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Affine;

/**
 * @author Claudius Grimm (claudius@acagamics.de)
 * @author Manuel Liebchen
 */
public final class MapOverlayRendering {

	private Map gameMap;

	private Font font;

	/**
	 * Used to render the game map.
	 * 
	 * @param inGameMap The map to render.
	 */
	public MapOverlayRendering(Map inGameMap) {
		gameMap = inGameMap;

		DesignProperties designProperties = ResourceManager.getInstance().loadProperties(DesignProperties.class);
		font = designProperties.getMediumSmallFont();
	}

	/**
	 * Displays the map images etc.
	 * @param transformation The affine transformation to map coordinates
	 * @param context Context to draw on.
	 */
	public void draw(GraphicsContext context, Affine transformation) {

		RenderingProperties renderingProperties = ResourceManager.getInstance()
				.loadProperties(RenderingProperties.class);
		GameProperties gameProperties = GameProperties.get();
		DesignProperties designProperties = ResourceManager.getInstance().loadProperties(DesignProperties.class);


		float scalation = (float) transformation.getMxx();

		context.setLineWidth(renderingProperties.getOverlayLineWidth() * scalation);
		context.setFont(font);

		for (Base base : gameMap.getBases()) {
			context.setStroke(renderingProperties.getHealthBackground());
			Point2D position = getPoint(base.getPosition());
			position = transformation.transform(position);
			float renderingRadius = gameProperties.getBaseRadius() * scalation;

			context.strokeOval(position.getX() - renderingRadius,
					position.getY() - renderingRadius, 2 * renderingRadius,
					2 * renderingRadius);
			context.setStroke(renderingProperties.getPlayerColors(base.getOwner()));
			context.strokeArc(position.getX() - renderingRadius,
					position.getY() - renderingRadius, 2 * renderingRadius,
					2 * renderingRadius,
					(float) (gameProperties.getPlayerBasePosition().get(base.getOwner().getPlayerID()).getAngle() * 360
							/ (Math.PI * 2) - ((double) 180 * base.getHP() / gameProperties.getBaseHP())),
					(float) ((double) 360 * base.getHP() / gameProperties.getBaseHP()), ArcType.OPEN);

			position = position.add(0, renderingRadius);
			String mineText = String.valueOf(base.getOwner().getName());
			Text text = new Text(mineText);
			text.setFont(font);
			Point2D textSize = new Point2D(text.getLayoutBounds().getWidth(), text.getLayoutBounds().getHeight());
			position = position.add(new Point2D(-0.5f * textSize.getX(), 1 * textSize.getY()));

			context.setFill(designProperties.getForegroundColor());
			context.fillText(mineText, position.getX(), position.getY());
		}

		for (Mine mine : gameMap.getMines()) {
			float[] ownership = mine.getOwnership();
			Point2D position = getPoint(mine.getPosition());
			position = transformation.transform(position);
			float renderingRadius = gameProperties.getMineRadius() * scalation;

			for (int i = 0; i < ownership.length; ++i) {
				context.setStroke(renderingProperties.getPlayerColors(i));
				context.strokeArc(position.getX() - renderingRadius,
						position.getY() - renderingRadius, 2 * renderingRadius,
						2 * renderingRadius,
						(float) (gameProperties.getPlayerBasePosition().get(i).getAngle() * 360 / (Math.PI * 2)
								- ownership[i] * 180),
						ownership[i] * 360, ArcType.OPEN);
			}

			position = position.add(0, renderingRadius);
			String mineText = String.valueOf(mine.getMineID());
			Text text = new Text(mineText);
			text.setFont(font);
			Point2D textSize = new Point2D(text.getLayoutBounds().getWidth(), text.getLayoutBounds().getHeight());
			position = position.add(new Point2D(-0.5f * textSize.getX(), 1 * textSize.getY()));

			context.setFill(designProperties.getForegroundColor());
			context.fillText(mineText, position.getX(), position.getY());
		}

		for (Unit unit : gameMap.getAllUnits()) {
			Point2D position = getPoint(unit.getPosition());
			position = transformation.transform(position);
			float renderingRadius = gameProperties.getUnitRadius() * scalation;

			context.setStroke(renderingProperties.getPlayerColors(unit.getOwner()));

			context.strokeOval(position.getX() - renderingRadius,
					position.getY() - renderingRadius, 2 * renderingRadius,
					2 * renderingRadius);


			position = position.add(new Point2D(0, renderingRadius));
			String unitText = getRoman(unit.getStrength());
			Text text = new Text(unitText);
			text.setFont(font);
			Point2D textSize = new Point2D(text.getLayoutBounds().getWidth(), text.getLayoutBounds().getHeight());
			position = position.add(new Point2D(-0.5f * textSize.getX(), 1 * textSize.getY()));

			context.setFill(designProperties.getForegroundColor());
			context.fillText(unitText, position.getX(), position.getY());
		}
	}

	Point2D getPoint(Vec2f vec){
		return new Point2D(vec.getX(), vec.getY());
	}

	String getRoman(int number ){
		switch(number) {
			case 1:
				return "I";
			case 2:
				return "II";
			case 3:
				return "III";
			case 4:
				return "IV";
			case 5:
				return "V";
			case 6:
				return "VI";
			case 7:
				return "VII";
			case 8:
				return "VIII";
			case 9:
				return "IX";
			case 10:
				return "X";
			default:
				return String.valueOf(number);
		}
	}

}
