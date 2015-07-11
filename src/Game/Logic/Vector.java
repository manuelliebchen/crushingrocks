package Game.Logic;

/**
 * A class for defining a 2-dimensional vector. With different helpful 
 * methods to work with and manipulate vectors.
 * @author Jan-Cord Gerken (jancord@acagamics.de)
 */
public final class Vector {
	
	// No nice code, but we have no const in java.
	public static final Vector ZERO() {
		return new Vector(0F, 0F);
	}
	public static final Vector ONE() {
		return new Vector(1F, 1F);
	}
	public static final Vector UNIT_X() {
		return new Vector(1F, 0F);
	}
	public static final Vector UNIT_Y() {
		return new Vector(0F, 1F);
	}
	
	public float x = 0;
	public float y = 0;
	
	public Vector() {
	}
	
	public Vector(Vector vec) {
		this.x = vec.x;
		this.y = vec.y;
	}
	
	public Vector(float x, float y) {
		this.x = x;
		this.y = y;
	}
	/**
	 * Get the x component from the vector.
	 */
	public float getX(){
		return this.x;
	}
	/**
	 * Get the x component from the vector.
	 */	
	public float getY(){
		return this.y;
	}
	
	/**
	 * Adds vectors component-wise and returns a new instance. (Won't change this instance)
	 * @param vec incoming vector to add
	 * @return new resulting vector
	 */
	public Vector add(Vector vec) {
		return new Vector(this.x + vec.x, this.y + vec.y);
	}
	
	/**
	 * Adds vector component-wise to a copy of this instance. (Won't change this instance)
	 * @param x incoming x-Value to add
	 * @param y incoming y-Value to add
	 * @return new resulting vector
	 */
	public Vector add(float x, float y) {
		return new Vector(this.x + x, this.y + y);
	}
	
	/**
	 * Subtracts vectors component-wise and returns a new instance. (Won't change this instance)
	 * @param vec incoming vector to subtract
	 * @return new resulting vector
	 */
	public Vector sub(Vector vec) {
		return new Vector(this.x - vec.x, this.y - vec.y);
	}
	
	/**
	 * Subtracts vector component-wise from a copy of this instance. (Won't change this instance)
	 * @param x incoming x-Value to subtract
	 * @param y incoming y-Value to subtract
	 * @return new resulting vector
	 */
	public Vector sub(float x, float y) {
		return new Vector(this.x - x, this.y - y);
	}
	
	/**
	 * Multiplies both components of a copy of this instance with factor. (Won't change this instance)
	 * @param a incoming factor to multiply
	 * @return new resulting vector
	 */
	public Vector mult(float a){
		return new Vector(this.x * a, this.y * a);
	}
	
	/**
	 * Multiplies vectors component-wise and returns a new instance. (Won't change this instance)
	 * @param x incoming x-Value to multiply
	 * @param y incoming y-Value to multiply
	 * @return new resulting vector
	 */
	public Vector mult(float x, float y) {
		return new Vector(this.x * x, this.y * y);
	}
	
	/**
	 * Multiplies vectors component-wise and returns a new instance. (Won't change this instance)
	 * @param vec incoming vector to multiply
	 * @return new resulting vector
	 */
	public Vector mult(Vector vec) {
		return new Vector(this.x * vec.x, this.y * vec.y);
	}
		
	/**
	 * Divides both components of a copy of this instance by dividend. (Won't change this instance)
	 * @param a incoming dividend for division
	 * @return new resulting vector
	 */
	public Vector div(float a){
		return new Vector(this.x / a, this.y / a);
	}
	
	/**
	 * Divides vectors component-wise and returns a new instance. (Won't change this instance)
	 * @param x incoming x-Value to divide
	 * @param y incoming y-Value to divide
	 * @return new resulting vector
	 */
	public Vector div(float x, float y) {
		return new Vector(this.x / x, this.y / y);
	}
	
	/**
	 * Divides vectors component-wise and returns a new instance. (Won't change this instance)
	 * @param vec incoming vector to divide
	 * @return new resulting vector
	 */
	public Vector div(Vector vec) {
		return new Vector(this.x / vec.x, this.y / vec.y);
	}
	
	/**
	 * Returns dot product (scalar product) of vectors.
	 * @param vec incoming vector
	 * @return dot product (scalar product)
	 */
	public float dot(Vector vec)
	{
		return this.x * vec.x + this.y * vec.y;
	}
	
	/**
	 * Returns the length of this vector.
	 * @return length
	 */
	public float length()
	{
		return (float)Math.sqrt(x*x + y*y);
	}

	/**
	 * Returns the squared length of this vector.
	 * @return squared length
	 */
	public float lengthSqr()
	{
		return x*x + y*y;
	}
	
	@Override
	public String toString() {
		return "[" + x + ", " + y + "]";
	}

	/**
	 * Performs linear interpolation between two vectors.
	 * @param vec second vector
	 * @param t interpolation value [0,1]
	 * @return interpolated vector rounded integers
	 */
	public Vector lerp(Vector vec, float t) {
		float x = t * (vec.x - this.x) + this.x;
		float y = t * (vec.y - this.y) + this.y;
		return new Vector(x, y);
	}

	/**
	 * Computes the Euclidean distance between two vectors.
	 * @param p second vector
	 * @return the Euclidean distance between this and the second vector
	 */
	public float distance(Vector p) {
		return this.sub(p).length();
	}
	
	/**
	 * Computes the squared Euclidean distance between two vectors.
	 * @param p second vector
	 * @return squared Euclidean distance between this and the second vector
	 */
	public float distanceSqr(Vector p) {
		return this.sub(p).lengthSqr();
	}
	
	/**
	 * Checks if vectors have same values.
	 * @param other vector to be checked for equality
	 * @return if vectors have same values
	 */
	public boolean equals(Vector other) {
		return (x == other.x || y == other.y);
	}
	
	/**
	 * Normalize this vector. If this is a Zero-Vector, components will be set to NaN.
	 */
	public void normalize() {
		float length = this.length();
		x = x / length;
		y = y / length;
	}
	
	/**
	 * Get the normalized vector. (Won't change this instance)
	 * @return This vector scaled to the length of 1. If this is a Zero-Vector, components will be set to NaN.
	 */
	public Vector getNormalized() {
		return this.div(this.length());
	}
}
