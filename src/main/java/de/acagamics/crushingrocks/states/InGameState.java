package de.acagamics.crushingrocks.states;

import de.acagamics.crushingrocks.GameMode;
import de.acagamics.crushingrocks.logic.Map;
import de.acagamics.crushingrocks.logic.Player;
import de.acagamics.crushingrocks.rendering.MapOverlayRendering;
import de.acagamics.framework.types.GameStatistic;
import de.acagamics.crushingrocks.logic.Game;
import de.acagamics.crushingrocks.rendering.MapRendering;
import de.acagamics.framework.resources.ClientProperties;
import de.acagamics.framework.resources.ResourceManager;
import de.acagamics.framework.types.MatchSettings;
import de.acagamics.framework.types.Vec2f;
import de.acagamics.framework.ui.StateManager;
import de.acagamics.crushingrocks.rendering.Background;
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
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @author Claudius Grimm (claudius@acagamics.de)
 * @author Manuel Liebchen
 */
public final class InGameState extends GameState implements ISelfUpdating {
	private Game game;
	private MapOverlayRendering mapOverlayRenderer;
	private RenderingLayer drawables;
	
	Timeline timeline;

	private MatchSettings<GameMode> settings;
	private Background background;

	private List<KeyCode> input;


	public InGameState(StateManager manager, GraphicsContext context, MatchSettings<GameMode> settings, int speedMultiplier) {

		super(manager, context);
		this.settings = settings;


		game = new Game(settings);

		background = new Background(50, 0.2f, game.getMap());
		mapOverlayRenderer = new MapOverlayRendering(game.getMap());

		drawables = new RenderingLayer();
		drawables.add(new DynamicTextBox(new Vec2f(0, 30), () -> String.valueOf(game.getFramesLeft()))
				.setVerticalAlignment(ALIGNMENT.CENTER).setHorizontalAlignment(ALIGNMENT.UPPER));
		drawables.add(new DynamicTextBox(new Vec2f(100, 30), () -> String.valueOf(game.getPlayer(0).getCreditPoints()))
				.setTextAlignment(ALIGNMENT.LEFT).setTextColor(game.getPlayer(0).getColor())
				.setVerticalAlignment(ALIGNMENT.LEFT).setHorizontalAlignment(ALIGNMENT.UPPER));
		drawables.add(new ImageElement(new Vec2f(70, 30), "Ressource.png", 25).setVerticalAlignment(ALIGNMENT.LEFT)
				.setHorizontalAlignment(ALIGNMENT.UPPER));
		drawables.add(new DynamicTextBox(new Vec2f(-100, 30), () -> String.valueOf(game.getPlayer(1).getCreditPoints()))
				.setTextAlignment(ALIGNMENT.RIGHT).setTextColor(game.getPlayer(1).getColor())
				.setVerticalAlignment(ALIGNMENT.RIGHT).setHorizontalAlignment(ALIGNMENT.UPPER));
		drawables.add(new ImageElement(new Vec2f(-70, 30), "Ressource.png", 25).setVerticalAlignment(ALIGNMENT.RIGHT)
				.setHorizontalAlignment(ALIGNMENT.UPPER));


		timeline = new Timeline();
		KeyFrame frame = new KeyFrame(
				Duration.millis((double) ResourceManager.getInstance().loadProperties(ClientProperties.class).getMilisPerFrame() / speedMultiplier), event ->
					frame()
				);

		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.getKeyFrames().add(frame);

		input = new ArrayList<>();
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

		mapOverlayRenderer.draw(context, background.getMapRendering().calcultateTransformation(context.getCanvas()));
		drawables.draw(context);
	}

	@Override
	public void handle(InputEvent event) {
		if (event instanceof KeyEvent) {
			KeyEvent keyEvent = (KeyEvent) event;
			if (keyEvent.getCode() == KeyCode.ESCAPE) {
				manager.pop();
			} else if (keyEvent.getEventType().equals(KeyEvent.KEY_TYPED)) {
				if(keyEvent.getCharacter().equals("p")){
					if (timeline.getStatus() == Status.RUNNING) {
						timeline.stop();
					} else {
						timeline.play();
					}
				}
			} else if(keyEvent.getEventType().equals(KeyEvent.KEY_PRESSED)){
				input.add(keyEvent.getCode());
				if(lastInputIs(konami)) {
					System.out.println("There is nothing here!"); //NOSONAR it's just an ester egg!
				}
			}
		}
		game.handle(event);
	}


	private final List<KeyCode> konami = Arrays.asList(KeyCode.UP, KeyCode.UP, KeyCode.DOWN, KeyCode.DOWN, KeyCode.LEFT, KeyCode.RIGHT, KeyCode.LEFT, KeyCode.RIGHT, KeyCode.B, KeyCode.A);
	private boolean lastInputIs(List<KeyCode> code){
		if(code.size() > input.size()){
			return false;
		}
		for(int i = 0; i < code.size(); i++) {
			if(!code.get(code.size()-1-i).equals(input.get(input.size()-1-i))){
				return false;
			}
		}
		return true;
	}
}
