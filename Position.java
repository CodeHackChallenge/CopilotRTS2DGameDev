package demo.main;

public class Position implements Component {
	
	public double x, y;

	public Position(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public int intX() {
		return (int) Math.round(x);
	}

	public void setX(int x) {
		this.x = x;
	}

	public int intY() {
		return (int) Math.round(y);
	}

	public void setY(int y) {
		this.y = y;
	}
	
	
}