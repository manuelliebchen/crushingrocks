package Game.Types;

public class Color {
	private float red,green,blue;
	
	public Color(int red, int green, int blue) {
		this.red = red / 256f;
		this.green = green / 256f;
		this.blue = blue / 256f;
	}
	
	public Color(float red, float green, float blue) {
		this.red = red;
		this.green = green;
		this.blue = blue;
	}
	
	public float[] getColor() {
		return new float[]{red,green,blue};
	}

	public Color copy() {
		return new Color(red,green,blue);
	}
}
