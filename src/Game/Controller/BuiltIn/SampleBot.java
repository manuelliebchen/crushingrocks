package Game.Controller.BuiltIn;

import java.util.Random;

import Game.Controller.IPlayerController;
import Game.Controller.MapInfo;
import Game.Controller.PlayerInfo;
import Game.Logic.Vector;

/**
 * Simplistic sample bot.
 * @author Andreas
 *
 */
public class SampleBot implements IPlayerController {
	private final int NUM_STEPS_PER_DIR_UPDATE = 20;
	
	private Random rnd = new Random();
	private Vector lastDir = getRandomDirection();
	private Vector goalDir = getRandomDirection();
	private int stepCountSinceDirUpdate = 0;
	
	Vector getRandomDirection() {
		// Need to take square root to obtain equally distributed points within a unit circle
		float radius = (float)Math.sqrt(rnd.nextFloat());
		float angle = rnd.nextFloat() * (float)Math.PI * 2.0f;
		return new Vector((float)Math.sin(angle) * radius, (float)Math.cos(angle) * radius);
	}
		
	@Override
	public String getName() {
		return "1337 SampleBot";
	}

	@Override
	public String getAuthor() {
		return "Andreas Reich";
	}
	
	@Override
	public Vector think(MapInfo mapInfo, PlayerInfo ownPlayerInfo, PlayerInfo[] enemyPlayerInfo) {
		++stepCountSinceDirUpdate;
		if(stepCountSinceDirUpdate >= NUM_STEPS_PER_DIR_UPDATE) {
			lastDir = goalDir;
			goalDir = getRandomDirection();
			stepCountSinceDirUpdate = 0;
		}
		
		return lastDir.lerp(goalDir, ((float)stepCountSinceDirUpdate) / NUM_STEPS_PER_DIR_UPDATE);
	}


}

