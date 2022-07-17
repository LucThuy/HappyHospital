package algorithm;

public class Position {
	public int x;
	public int y;
	
	public Position() {
		
	}
	
	public Position(int x, int y) {
		 this.x = x;
		 this.y = y;
	}
	
	public static double distance(Position X, Position Y) {
		return Math.sqrt(Math.pow(X.x - Y.x,2) + Math.pow(X.y - Y.y,2));
	}
}
