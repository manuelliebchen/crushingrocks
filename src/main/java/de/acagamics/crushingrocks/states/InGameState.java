package de.acagamics.crushingrocks.states;

import de.acagamics.crushingrocks.GameProperties;
import de.acagamics.crushingrocks.GameStatistic;
import de.acagamics.crushingrocks.controller.IPlayerController;
import de.acagamics.crushingrocks.logic.Game;
import de.acagamics.crushingrocks.logic.Mine;
import de.acagamics.crushingrocks.logic.Unit;
import de.acagamics.crushingrocks.rendering.MapOverlayRendering;
import de.acagamics.crushingrocks.rendering.MapRendering;
import de.acagamics.framework.resources.ClientProperties;
import de.acagamics.framework.resources.DesignProperties;
import de.acagamics.framework.resources.ResourceManager;
import de.acagamics.framework.types.MatchSettings;
import de.acagamics.framework.types.Vec2f;
import de.acagamics.framework.ui.StateManager;
import de.acagamics.framework.ui.elements.Background;
import de.acagamics.framework.ui.elements.DynamicTextBox;
import de.acagamics.framework.ui.elements.ImageElement;
import de.acagamics.framework.ui.elements.RenderingLayer;
import de.acagamics.framework.ui.interfaces.ALIGNMENT;
import de.acagamics.framework.ui.interfaces.GameState;
import de.acagamics.framework.ui.interfaces.ISelfUpdating;
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

	private MatchSettings<IPlayerController> settings;
	private Background background;

	public InGameState(StateManager manager, GraphicsContext context, MatchSettings<IPlayerController> settings) {
		super(manager, context);
		this.settings = settings;
		
		background = new Background(50);
		
		game = new Game(settings);

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
				Duration.millis((double) ResourceManager.getInstance().loadProperties(ClientProperties.class).getMilisPerFrame() / settings.getSpeedMultiplier()), event ->
					frame()
				);

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
		GameProperties gameProperties = ResourceManager.getInstance().loadProperties(GameProperties.class);
		transformation.appendTranslation(canvas.getWidth() / 2, canvas.getHeight() / 2);
		if (canvas.getHeight() > canvas.getWidth()) {
			transformation.appendScale(canvas.getWidth() / (gameProperties.getMapRadius() * 2.5),
					canvas.getWidth() / (gameProperties.getMapRadius() * 2.5));
		} else {
			transformation.appendScale(canvas.getHeight() / (gameProperties.getMapRadius() * 2.5),
					canvas.getHeight() / (gameProperties.getMapRadius() * 2.5));
		}

		context.setTransform(transformation);

		mapRenderer.draw(context);

		mapOverlayRenderer.draw(context);

		context.restore();

		DesignProperties designProperties = ResourceManager.getInstance().loadProperties(DesignProperties.class);
		
		for (Mine mine : game.getMap().getMines()) {
			Point2D position = mine.getPosition().add(0, gameProperties.getMineRadius()).getPoint2D();
			position = transformation.transform(position);
			String mineText = String.valueOf(mine.getMineID());
			Text text = new Text(mineText);
			text.setFont(designProperties.getSmallFont());
			Point2D textSize = new Point2D(text.getLayoutBounds().getWidth(), text.getLayoutBounds().getHeight());
			position = position.add(new Point2D(-0.5f * textSize.getX(), 1 * textSize.getY()));

			context.setFill(designProperties.getForegroundColor());
			context.setFont(designProperties.getSmallFont());
			context.fillText(mineText, position.getX(), position.getY());
		}
		
		for(Unit unit : game.getMap().getAllUnits()) {
			Point2D position = unit.getPosition().add(0, gameProperties.getUnitRadius()).getPoint2D();
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
			default:
				unitText = "";
			}
			Text text = new Text(unitText);
			text.setFont(designProperties.getSmallFont());
			Point2D textSize = new Point2D(text.getLayoutBounds().getWidth(), text.getLayoutBounds().getHeight());
			position = position.add(new Point2D(-0.5f * textSize.getX(), 1 * textSize.getY()));
	
			context.setFill(designProperties.getForegroundColor());
			context.setFont(designProperties.getSmallFont());
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
			} else if (keyEvent.getEventType().equals(KeyEvent.KEY_TYPED) && keyEvent.getCharacter().equals("p")) {
				if (timeline.getStatus() == Status.RUNNING) {
					timeline.stop();
				} else {
					timeline.play();
				}
			}
		}
		game.handle(event);
	}
}
