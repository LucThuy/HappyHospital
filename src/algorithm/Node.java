package algorithm;

import java.util.Vector;

public class Node {
	
	private Position position;
	private Direct direct;
	private double F;
	private double G;
	private double H;
	private Node preNode;
	private Vector<Node> nextNode = new Vector<>();
	
	public Node() {
		
	}
	
	public Node(Position position, Direct direct) {
		this.setPosition(position);
		this.setDirect(direct);
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public Vector<Node> getNextNode() {
		return nextNode;
	}

	public void setNextNode(Vector<Node> nextNode) {
		this.nextNode = nextNode;
	}

	public Node getPreNode() {
		return preNode;
	}

	public void setPreNode(Node preNode) {
		this.preNode = preNode;
	}

	public double getG() {
		return G;
	}

	public void setG(double g) {
		G = g;
	}

	public Direct getDirect() {
		return direct;
	}

	public void setDirect(Direct direct) {
		this.direct = direct;
	}

	public double getH() {
		return H;
	}

	public void setH(double h) {
		H = h;
	}

	public double getF() {
		return F;
	}

	public void setF(double f) {
		F = f;
	}
}
