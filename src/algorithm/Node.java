package algorithm;

import java.util.Vector;

public class Node {
	
	public Position position;
	public Direct direct;
	public double F;
	public double G;
	public double H;
	public Node preNode;
	public Vector<Node> nextNode = new Vector<>();
	
	public Node() {
		
	}
	
	public Node(Position position, Direct direct) {
		this.position = position;
		this.direct = direct;
	}
}
