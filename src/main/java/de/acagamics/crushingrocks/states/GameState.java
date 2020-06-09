package de.acagamics.crushingrocks.states;

import de.acagamics.crushingrocks.logic.Game;
import de.acagamics.crushingrocks.logic.IPlayerController;
import de.acagamics.crushingrocks.logic.Player;
import de.acagamics.crushingrocks.rendering.Background;
import de.acagamics.crushingrocks.rendering.MapOverlayRendering;
import de.acagamics.crushingrocks.rendering.RenderingProperties;
import de.acagamics.crushingrocks.types.MatchSettings;
import de.acagamics.framework.geometry.Illustrator;
import de.acagamics.framework.geometry.Vec2f;
import de.acagamics.framework.interfaces.IIllustrating;
import de.acagamics.framework.resources.ResourceManager;
import de.acagamics.framework.ui.StateManager;
import de.acagamics.framework.ui.elements.DynamicTextBox;
import de.acagamics.framework.ui.elements.ImageElement;
import de.acagamics.framework.ui.elements.RenderingLayer;
import de.acagamics.framework.ui.interfaces.ALIGNMENT;
import de.acagamics.framework.ui.interfaces.SelfUpdatingState;
import javafx.animation.Animation.Status;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.transform.Affine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Claudius Grimm (claudius@acagamics.de)
 * @author Manuel Liebchen
 */
public final class GameState extends SelfUpdatingState {

	private Game game;
	private MapOverlayRendering mapOverlayRenderer;

	private MatchSettings settings;
	private Background background;

	private List<KeyCode> input;

	private boolean clean;

	private int speedMultiplier;

	public GameState(StateManager manager, GraphicsContext context, MatchSettings settings, int speedMultiplier) {
		super(manager, context);
		this.settings = settings;
		this.speedMultiplier = speedMultiplier;
		input = new ArrayList<>();
	}

	@Override
	public void entered() {
		game = (Game) settings.get();
		background = new Background(50, 0.2f, game.getMap());
		mapOverlayRenderer = new MapOverlayRendering(game.getMap());
		RenderingProperties renderingProperties = ResourceManager.getInstance().loadProperties(RenderingProperties.class);
		drawables = new RenderingLayer();
		drawables.add(new DynamicTextBox(new Vec2f(0, 30), () -> String.valueOf(game.getFramesLeft()))
				.setVerticalAlignment(ALIGNMENT.CENTER).setHorizontalAlignment(ALIGNMENT.UPPER));
		drawables.add(new DynamicTextBox(new Vec2f(100, 30), () -> String.valueOf(game.getPlayer(0).getCreditPoints()))
				.setTextAlignment(ALIGNMENT.LEFT).setTextColor(renderingProperties.getPlayerColors(game.getPlayer(0)))
				.setVerticalAlignment(ALIGNMENT.LEFT).setHorizontalAlignment(ALIGNMENT.UPPER));
		drawables.add(new ImageElement(new Vec2f(70, 30), "Ressource.png", 25).setVerticalAlignment(ALIGNMENT.LEFT)
				.setHorizontalAlignment(ALIGNMENT.UPPER));
		drawables.add(new DynamicTextBox(new Vec2f(-100, 30), () -> String.valueOf(game.getPlayer(1).getCreditPoints()))
				.setTextAlignment(ALIGNMENT.RIGHT).setTextColor(renderingProperties.getPlayerColors(game.getPlayer(1)))
				.setVerticalAlignment(ALIGNMENT.RIGHT).setHorizontalAlignment(ALIGNMENT.UPPER));
		drawables.add(new ImageElement(new Vec2f(-70, 30), "Ressource.png", 25).setVerticalAlignment(ALIGNMENT.RIGHT)
				.setHorizontalAlignment(ALIGNMENT.UPPER));

		super.entered();
	}

	/**
	 * Updates all needed display objects, e.g. player, map.
	 */
	@Override
	public void update() {
		for(int i = 0; i < speedMultiplier; ++i){
			game.tick();
			if (!game.isAlive()) {
				manager.switchCurrentState(new GameStatisticState(manager, context, game.getStatistic(), game.getMap()));
				return;
			}
		}
		redraw();
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

		if(!clean) {
			Affine transformation = background.getMapRendering().calcultateTransformation(context.getCanvas());
			mapOverlayRenderer.draw( context, transformation);

			context.save();
			context.setTransform(transformation);
			context.setLineWidth(ResourceManager.getInstance().loadProperties(RenderingProperties.class).getOverlayLineWidth());
			for(int i = 0; i < game.getNumPlayers(); ++i){
				IPlayerController cont = game.getPlayerControllers(i);
				Player p = game.getPlayer(i);
				if(cont instanceof IIllustrating){
					context.setFill(ResourceManager.getInstance().loadProperties(RenderingProperties.class).getPlayerColors(p));
					context.setStroke(ResourceManager.getInstance().loadProperties(RenderingProperties.class).getPlayerColors(p));
					((IIllustrating) cont).draw(new Illustrator(context));
				}
			}
			context.restore();

			drawables.draw(context);
		}
	}

	@Override
	public void handle(InputEvent event) {
		if (event instanceof KeyEvent) {
			KeyEvent keyEvent = (KeyEvent) event;
			if (keyEvent.getCode() == KeyCode.ESCAPE) {
				manager.pop();
			} else if (keyEvent.getEventType().equals(KeyEvent.KEY_TYPED)) {
				if(keyEvent.getCharacter().equals(" ")){
					if (timeline.getStatus() == Status.RUNNING) {
						timeline.stop();
					} else {
						timeline.play();
					}
				} else if(keyEvent.getCharacter().equals("c")){
					clean = !clean;
				}
			} else if(keyEvent.getEventType().equals(KeyEvent.KEY_PRESSED)){
				input.add(keyEvent.getCode());
				if(lastInputIs(konami)) {
					System.out.println("There is nothing here!"); //NOSONAR it's an ester egg!
				}
			}
		}
		for(IPlayerController controller : game.getPlayerControllers()){
			if (controller instanceof EventHandler) {
				((EventHandler<InputEvent>) controller).handle(event);
			}
		}
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
