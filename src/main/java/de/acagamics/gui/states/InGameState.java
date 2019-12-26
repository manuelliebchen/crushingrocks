package de.acagamics.gui.states;

import de.acagamics.constants.ClientConstants;
import de.acagamics.constants.DesignConstants;
import de.acagamics.constants.GameConstants;
import de.acagamics.data.GameStatistic;
import de.acagamics.data.InGameSettings;
import de.acagamics.game.logic.Game;
import de.acagamics.game.logic.Mine;
import de.acagamics.game.logic.Unit;
import de.acagamics.game.types.Vec2f;
import de.acagamics.gui.StateManager;
import de.acagamics.gui.elements.Background;
import de.acagamics.gui.elements.DynamicTextBox;
import de.acagamics.gui.elements.ImageElement;
import de.acagamics.gui.elements.RenderingLayer;
import de.acagamics.gui.interfaces.ALIGNMENT;
import de.acagamics.gui.interfaces.GameState;
import de.acagamics.gui.interfaces.ISelfUpdating;
import de.acagamics.gui.rendering.renderer.MapOverlayRendering;
import de.acagamics.gui.rendering.renderer.MapRendering;
import javafx.animation.Animation;
import javafx.animation.Animation.Status;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.scene.transform.Affine;
import javafx.util.Duration;

/**
 * @author Claudius Grimm (claudius@acagamics.de)
 * @author Manuel Liebchen
 */
public final class InGameState extends GameState implements ISelfUpdating {

	private Game game;
	private MapRendering mapRenderer;
	private MapOverlayRendering mapOverlayRenderer;
	private RenderingLayer drawables;
	
	Timeline timeline;

	private InGameSettings settings;
	private Background background;

	public InGameState(StateManager manager, GraphicsContext context, InGameSettings settings) {
		super(manager, context);
		this.settings = settings;
		
		background = new Background(50);
		
		game = new Game(settings.getControllers(), settings.getMode());

		mapRenderer = new MapRendering(game.getMap());
		mapOverlayRenderer = new MapOverlayRendering(game.getMap());	

		drawables = new RenderingLayer();
		drawables.add(new DynamicTextBox(new Vec2f(0, 30), () -> String.valueOf(game.getFramesLeft()))
				.setVerticalAlignment(ALIGNMENT.CENTER).setHorizontalAlignment(ALIGNMENT.TOP));
		drawables.add(new DynamicTextBox(new Vec2f(100, 30), () -> String.valueOf(game.getPlayer(0).getCreditPoints()))
				.setTextAlignment(ALIGNMENT.LEFT).setTextColor(game.getPlayer(0).getColor())
				.setVerticalAlignment(ALIGNMENT.LEFT).setHorizontalAlignment(ALIGNMENT.TOP));
		drawables.add(new ImageElement(new Vec2f(70, 30), "Ressource.png", 25).setVerticalAlignment(ALIGNMENT.LEFT)
				.setHorizontalAlignment(ALIGNMENT.TOP));
		drawables.add(new DynamicTextBox(new Vec2f(-100, 30), () -> String.valueOf(game.getPlayer(1).getCreditPoints()))
				.setTextAlignment(ALIGNMENT.RIGHT).setTextColor(game.getPlayer(1).getColor())
				.setVerticalAlignment(ALIGNMENT.RIGHT).setHorizontalAlignment(ALIGNMENT.TOP));
		drawables.add(new ImageElement(new Vec2f(-70, 30), "Ressource.png", 25).setVerticalAlignment(ALIGNMENT.RIGHT)
				.setHorizontalAlignment(ALIGNMENT.TOP));
		

		timeline = new Timeline();
		KeyFrame frame = new KeyFrame(
				Duration.millis(ClientConstants.MINIMUM_TIME_PER_FRAME_MS / settings.getSpeedMultiplier()), (event) -> {
					frame();
				});

		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.getKeyFrames().add(frame);
	}

	@Override
	public void entered() {
		timeline.play();
	}

	@Override
	public void leaving() {
		timeline.stop();
	}

	/**
	 * Updates all needed display objects, e.g. player, map.
	 * 
	 * @param elapsedTime Time passed since last update in seconds.
	 */
	@Override
	public void update() {
		GameStatistic statistic = game.tick();
		if (statistic != null) {
			manager.switchCurrentState(new StatisticState(manager, context, statistic, settings));
		}
		mapRenderer.update();
	}

	/**
	 * Draws all needed display objects, e.g. player, map.
	 * 
	 * @param context     The context to draw on.
	 * @param elapsedTime Time passed since last draw in seconds.
	 */
	@Override
	public void redraw() {
		if (timeline.getStatus() != Status.RUNNING) {
			return;
		}
		background.draw(context);
		Canvas canvas = context.getCanvas();
		
		context.save();

		Affine transformation = new Affine();
		transformation.appendTranslation(canvas.getWidth() / 2, canvas.getHeight() / 2);
		if (canvas.getHeight() > canvas.getWidth()) {
			transformation.appendScale(canvas.getWidth() / (GameConstants.MAP_RADIUS * 2.5),
					canvas.getWidth() / (GameConstants.MAP_RADIUS * 2.5));
		} else {
			transformation.appendScale(canvas.getHeight() / (GameConstants.MAP_RADIUS * 2.5),
					canvas.getHeight() / (GameConstants.MAP_RADIUS * 2.5));
		}

		context.setTransform(transformation);

		mapRenderer.draw(context);

		mapOverlayRenderer.draw(context);

		context.restore();

		for (Mine mine : game.getMap().getMines()) {
			Point2D position = mine.getPosition().add(0, GameConstants.MINE_RADIUS).getPoint2D();
			position = transformation.transform(position);
			String mineText = String.valueOf(mine.getMineID());
			Text text = new Text(mineText);
			text.setFont(DesignConstants.SMALL_FONT);
			Point2D textSize = new Point2D(text.getLayoutBounds().getWidth(), text.getLayoutBounds().getHeight());
			position = position.add(new Point2D(-0.5f * textSize.getX(), 1 * textSize.getY()));

			context.setFill(DesignConstants.FOREGROUND_COLOR);
			context.setFont(DesignConstants.SMALL_FONT);
			context.fillText(mineText, position.getX(), position.getY());
		}
		
		for(Unit unit : game.getMap().getAllUnits()) {
			Point2D position = unit.getPosition().add(0, GameConstants.UNIT_RADIUS).getPoint2D();
			position = transformation.transform(position);
			String unitText = "";
			switch(unit.getStrength()) {
			case 1: 
				unitText = "I";
				break;
			case 2: 
				unitText = "II";
				break;
			case 3: 
				unitText = "III";
				break;
			
			}
			Text text = new Text(unitText);
			text.setFont(DesignConstants.SMALL_FONT);
			Point2D textSize = new Point2D(text.getLayoutBounds().getWidth(), text.getLayoutBounds().getHeight());
			position = position.add(new Point2D(-0.5f * textSize.getX(), 1 * textSize.getY()));
	
			context.setFill(DesignConstants.FOREGROUND_COLOR);
			context.setFont(DesignConstants.SMALL_FONT);
			context.fillText(unitText, position.getX(), position.getY());
		}
		
		drawables.draw(context);
	}

	@Override
	public void handle(InputEvent event) {
		if (event instanceof KeyEvent) {
			KeyEvent keyEvent = (KeyEvent) event;
			if (keyEvent.getCode() == KeyCode.ESCAPE) {
				manager.pop();
			} else if (keyEvent.getEventType().equals(KeyEvent.KEY_TYPED)) {
				if (keyEvent.getCharacter().equals("p")) {
					if (timeline.getStatus() == Status.RUNNING) {
						timeline.stop();
					} else {
						timeline.play();
					}
				}
			}
		}
		game.handle(event);
	}
}
