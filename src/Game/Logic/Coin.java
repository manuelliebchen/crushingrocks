package Game.Logic;

/**
 * Coins can be collected by Players for increasing their score.
 * @author Jan-Cord Gerken (jancord@acagamics.de)
 *
 */
public class Coin {
	
	Vector position;
	float radius;
	float value;
	
	public Coin(Vector position){
		this.position = position;
		this.radius = 1F;
		this.value = 1F;
	}

	/**
	 * Get the position of the coin.
	 * @return position
	 */
	public Vector getPosition() {
		return position;
	}

	/**
	 * Get the radius of the coin.
	 * @return radius
	 */
	public float getRadius() {
		return radius;
	}

	/**
	 * Get the value of the coin.
	 * @return value
	 */
	public float getValue() {
		return value;
	}
}
