package algorithm;

import java.util.Vector;

public class CalScore {
	
	private final static int SIZE = 28;
	
	public float expectedTime;
	public long preTime;
	
	public CalScore() {
		preTime = System.currentTimeMillis();
	}
	
	public void calExpectedTime(Node start, Node end, AStar AStar) {		
		Vector<Node> path = AStar.AStarAlgorithm(start, end);
		long length = path.size();
		
		this.expectedTime = (float)length * SIZE / 60;
	}
	
	public float calScore() {
		float time = System.currentTimeMillis() / 1000 - preTime / 1000 - expectedTime;
		preTime = System.currentTimeMillis();
	
		return (10 - time / 4) >= 0 ? (10 - time / 4) : 0;
	}
}
