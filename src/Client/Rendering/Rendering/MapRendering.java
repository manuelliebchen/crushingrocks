package Client.Rendering.Rendering;

import java.util.List;

import Client.GUI.States.Interfaces.IDrawable;
import Client.Rendering.Drawing.ImageManager;
import Constants.ClientConstants;
import Constants.GameConstants;
import Game.Logic.Base;
import Game.Logic.Map;
import Game.Logic.Mine;
import Game.Logic.Player;
import Game.Logic.Unit;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;

/**
 * @author Claudius Grimm (claudius@acagamics.de)
 * @author Manuel Liebchen
 */
public final class MapRendering implements IDrawable {

	private Map gameMap;

	private List<Base> bases;
	private List<Mine> mines;
	private List<Player> players;

	private Affine transformation;

	/**
	 * Used to render the game map.
	 * 
	 * @param inGameMap The map to render.
	 */
	public MapRendering(Map inGameMap) {
		gameMap = inGameMap;
		mines = gameMap.getMines();
		bases = gameMap.getBases();
		players = gameMap.getPlayers();

		transformation = new Affine();
		transformation.appendScale(ClientConstants.INITIAL_SCREEN_HEIGHT / (GameConstants.MAP_RADIUS * 2),
				ClientConstants.INITIAL_SCREEN_HEIGHT / (GameConstants.MAP_RADIUS * 2));
		float ratio = (float) ClientConstants.INITIAL_SCREEN_WIDTH / (float) ClientConstants.INITIAL_SCREEN_HEIGHT;
		transformation.appendTranslation(GameConstants.MAP_RADIUS * ratio, GameConstants.MAP_RADIUS);
	}

	/**
	 * Updates the map rendering. E.g. animated sprites.
	 * 
	 * @param timeSinceLastUpdate Time passed since last update in seconds.
	 */
	public void update(float timeSinceLastUpdate) {
	}

	/**
	 * Displays the map images etc.
	 * 
	 * @param context           Context to draw on.
	 * @param timeSinceLastDraw Time passed since last draw in seconds.
	 */
	public void draw(GraphicsContext context) {
		Canvas canvas = context.getCanvas();
		context.setFill(Color.WHITE);
		context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

//		context.setFont(font);
//		context.setTextAlign(TextAlignment.LEFT);
//		context.setFill(players.get(0).getColor());
//		context.fillText(String.valueOf(players.get(0).getCreditPoints()), 10, 40);
//		context.setTextAlign(TextAlignment.RIGHT);
//		context.setFill(players.get(1).getColor());
//		context.fillText(String.valueOf(players.get(1).getCreditPoints()), canvas.getWidth() - 10, 40);

		transformation = new Affine();
		transformation.appendTranslation(canvas.getWidth() / 2, canvas.getHeight() / 2);
		if (canvas.getHeight() > canvas.getWidth()) {
			transformation.appendScale(canvas.getWidth() / (GameConstants.MAP_RADIUS * 2.5),
					canvas.getWidth() / (GameConstants.MAP_RADIUS * 2.5));
		} else {
			transformation.appendScale(canvas.getHeight() / (GameConstants.MAP_RADIUS * 2.5),
					canvas.getHeight() / (GameConstants.MAP_RADIUS * 2.5));
		}

		context.setTransform(transformation);
		context.setLineWidth(0.01f);

		Image minetexture = ImageManager.getInstance().loadImage("mine.png");
		for (Mine mine : mines) {
			float[] ownership = mine.getOwnership();

			context.drawImage(minetexture, mine.getPosition().getX() - 0.1, mine.getPosition().getY() - 0.1, 0.2, 0.2);

			context.setStroke(players.get(0).getColor().interpolate(players.get(1).getColor(), ownership[1]));
			context.strokeOval(mine.getPosition().getX() - GameConstants.MINE_RADIUS,
					mine.getPosition().getY() - GameConstants.MINE_RADIUS, 2 * GameConstants.MINE_RADIUS,
					2 * GameConstants.MINE_RADIUS);

			context.setFill(players.get(1).getColor());
			context.fillRect(mine.getPosition().getX() - GameConstants.MINE_RADIUS,
					mine.getPosition().getY() - GameConstants.MINE_RADIUS - 0.04, GameConstants.MINE_RADIUS * 2, 0.02);
			context.setFill(players.get(0).getColor());
			context.fillRect(mine.getPosition().getX() - GameConstants.MINE_RADIUS,
					mine.getPosition().getY() - GameConstants.MINE_RADIUS - 0.04,
					GameConstants.MINE_RADIUS * 2 * ownership[0], 0.02);
		}

		Image baseTexture = ImageManager.getInstance().loadImage("base.png");
		for (Base base : bases) {
			context.setStroke(base.getOwner().getColor());
			context.drawImage(baseTexture, base.getPosition().getX() - 0.2, base.getPosition().getY() - 0.2, 0.4, 0.4);
			context.strokeOval(base.getPosition().getX() - GameConstants.BASE_RADIUS,
					base.getPosition().getY() - GameConstants.BASE_RADIUS, 2 * GameConstants.BASE_RADIUS,
					2 * GameConstants.BASE_RADIUS);

			context.setFill(Color.RED);
			context.fillRect(base.getPosition().getX() - GameConstants.BASE_RADIUS,
					base.getPosition().getY() - GameConstants.BASE_RADIUS - 0.04, GameConstants.BASE_RADIUS * 2, 0.02);
			context.setFill(Color.GREEN);
			context.fillRect(base.getPosition().getX() - GameConstants.BASE_RADIUS,
					base.getPosition().getY() - GameConstants.BASE_RADIUS - 0.04,
					GameConstants.BASE_RADIUS * 2 * base.getHP() / GameConstants.INITIAL_BASE_HP, 0.02);
		}

		Image unitTexture = ImageManager.getInstance().loadImage("player.png");
		for (Player player : players) {
			context.setStroke(player.getColor());
			for (Unit unit : player.getUnits()) {
				context.drawImage(unitTexture, unit.getPosition().getX() - 0.05, unit.getPosition().getY() - 0.05, 0.1,
						0.1);
				context.strokeOval(unit.getPosition().getX() - GameConstants.UNIT_RADIUS,
						unit.getPosition().getY() - GameConstants.UNIT_RADIUS, 2 * GameConstants.UNIT_RADIUS,
						2 * GameConstants.UNIT_RADIUS);

				context.setFill(Color.RED);
				context.fillRect(unit.getPosition().getX() - GameConstants.UNIT_RADIUS,
						unit.getPosition().getY() - GameConstants.UNIT_RADIUS - 0.04, GameConstants.UNIT_RADIUS * 2,
						0.02);
				context.setFill(Color.GREEN);
				context.fillRect(unit.getPosition().getX() - GameConstants.UNIT_RADIUS,
						unit.getPosition().getY() - GameConstants.UNIT_RADIUS - 0.04,
						GameConstants.UNIT_RADIUS * 2 * unit.getHP() / GameConstants.INITIAL_UNIT_HP, 0.02);
			}
		}
	}

}
