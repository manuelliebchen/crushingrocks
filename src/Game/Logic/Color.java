package Game.Logic;

public class Color {
	private float red,green,blue;
	
	public Color(float red, float green, float blue) {
		this.red = red;
		this.green = green;
		this.blue = blue;
	}
	
	public float[] getColor() {
		return new float[]{red,green,blue};
	}
}
