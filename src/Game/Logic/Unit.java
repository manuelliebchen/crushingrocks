package Game.Logic;

import java.util.List;

import Constants.GameConstants;
import Constants.GameConstants.UNIT_TYPE;
import Game.Controller.IPlayerController;
import Game.Types.Vector;

public class Unit {

	private Vector position;
	private Player owner;
	private int hp;

	private UNIT_TYPE type;

	private Vector orderedDirection;

	Unit(UNIT_TYPE ut, Player owner, Vector position) {
		this.type = ut;
		this.owner = owner;
		this.position = position;
		this.hp = GameConstants.INITIAL_UNIT_HP;
	}

	public Player getOwner() {
		return owner;
	}

	public Vector getPosition() {
		return position.copy();
	}

	public int getHP() {
		return hp;
	}

	public UNIT_TYPE getType() {
		return type;
	}

	public void setOrder(IPlayerController controller, Vector direction) {
		if (controller == owner.getController()) {
			orderedDirection = direction;
		}
	}

	Vector updatePosition(List<Unit> enemyUnits) {
		if (orderedDirection != null) {
			if (orderedDirection.length() > GameConstants.MAX_UNIT_SPEED) {
				orderedDirection = orderedDirection.normalize().mult(GameConstants.MAX_UNIT_SPEED);
			}
			position = position.add(orderedDirection);
			orderedDirection = null;
			position = new Vector(Math.min(1, Math.max(-1, position.getX())),
					Math.min(1, Math.max(-1, position.getY())));
		}
		return position;
	}

	void attackBy(int damage) {
		hp -= damage;
	}
}
