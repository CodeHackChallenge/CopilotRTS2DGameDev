public class Vector2D {
    public double x, y;

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double length() {
        return Math.sqrt(x * x + y * y);
    }

    public void normalize() {
        double len = length();
        if (len != 0) {
            x /= len;
            y /= len;
        }
    }

    public Vector2D scaled(double s) {
        return new Vector2D(x * s, y * s);
    }
}
