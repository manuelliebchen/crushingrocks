package de.acagamics.crushingrocks.states;

import de.acagamics.crushingrocks.GameConstants;
import de.acagamics.crushingrocks.logic.Game;
import de.acagamics.crushingrocks.logic.Mine;
import de.acagamics.crushingrocks.logic.Unit;
import de.acagamics.crushingrocks.rendering.MapOverlayRendering;
import de.acagamics.crushingrocks.rendering.MapRendering;
import de.acagamics.framework.gui.StateManager;
import de.acagamics.framework.gui.elements.Background;
import de.acagamics.framework.gui.elements.DynamicTextBox;
import de.acagamics.framework.gui.elements.ImageElement;
import de.acagamics.framework.gui.elements.RenderingLayer;
import de.acagamics.framework.gui.interfaces.ALIGNMENT;
import de.acagamics.framework.gui.interfaces.GameState;
import de.acagamics.framework.gui.interfaces.ISelfUpdating;
import de.acagamics.framework.resourcemanagment.ResourceManager;
import de.acagamics.framework.types.GameStatistic;
import de.acagamics.framework.types.InGameSettings;
import de.acagamics.framework.types.Vec2f;
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
				Duration.millis(ResourceManager.getInstance().getClientProperties().getMilisPerFrame() / settings.getSpeedMultiplier()), (event) -> {
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
			text.setFont(ResourceManager.getInstance().getDesignProperties().getSmallFont());
			Point2D textSize = new Point2D(text.getLayoutBounds().getWidth(), text.getLayoutBounds().getHeight());
			position = position.add(new Point2D(-0.5f * textSize.getX(), 1 * textSize.getY()));

			context.setFill(ResourceManager.getInstance().getDesignProperties().getForegroundColor());
			context.setFont(ResourceManager.getInstance().getDesignProperties().getSmallFont());
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
			text.setFont(ResourceManager.getInstance().getDesignProperties().getSmallFont());
			Point2D textSize = new Point2D(text.getLayoutBounds().getWidth(), text.getLayoutBounds().getHeight());
			position = position.add(new Point2D(-0.5f * textSize.getX(), 1 * textSize.getY()));
	
			context.setFill(ResourceManager.getInstance().getDesignProperties().getForegroundColor());
			context.setFont(ResourceManager.getInstance().getDesignProperties().getSmallFont());
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