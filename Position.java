public class Position implements Component {
    
	//public int x, y; 
	public double x;
	public double y; 
	
	public Position(double x, double y) { this.x = x; this.y = y; }
    public Position(int x, int y) { this.x = x; this.y = y; }
    
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	
	public int intX() {
        return (int) Math.round(x);
    }

    public int intY() {
        return (int) Math.round(y);
    }
    
}
