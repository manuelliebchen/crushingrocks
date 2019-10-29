package Game.Logic;

import Constants.GameConstants;
import Constants.GameConstants.UNIT_TYPE;
import Game.Types.Vector;

public class Unit {
	
	Vector position;
	Player owner;
	int hp;
	
	UNIT_TYPE type;
	
	Vector orderedDirection;
	
	Unit(UNIT_TYPE ut, Player owner, Vector position){
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
	
	public void setOrder(Vector direction) {
		orderedDirection = direction;
	}
	
	Vector updatePosition() {
		if(orderedDirection != null) { 
			if(orderedDirection.length() > GameConstants.MAX_UNIT_SPEED) {
				orderedDirection = orderedDirection.normalize().mult(GameConstants.MAX_UNIT_SPEED);
			}
			position = position.add(orderedDirection);
			orderedDirection = null;
			if(position.length() > GameConstants.MAP_RADIUS) {
				position = position.normalize().mult(GameConstants.MAP_RADIUS);
			}
		}
		return position; 
	}
}
