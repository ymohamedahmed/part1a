public class Vector2D {
	private float x;
	private float y;

	public Vector2D(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float magnitude() {
		return (float) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
	}

	public void add(Vector2D v) {
		x += v.getX();
		y += v.getY();
	}

	public float scalar(Vector2D v) {
		return (x * v.getX()) + (y + v.getY());
	}

	public void normal() {
		x /= magnitude();
		y /= magnitude();
	}
}
