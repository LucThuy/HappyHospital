package algorithm;

public class Position {
	private int x;
	private int y;
	
	public Position() {
		
	}
	
	public Position(int x, int y) {
		 this.setX(x);
		 this.setY(y);
	}
	
	public static double distance(Position X, Position Y) {
		return Math.sqrt(Math.pow(X.getX() - Y.getX(),2) + Math.pow(X.getY() - Y.getY(),2));
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}
}
