package algorithm;

import java.util.Vector;

public class AStar {
	
	private Node[][] map;
	private Node[][] mapND;
	
	private int width;
	private int height;
	
	public AStar(long[][] data, int width, int height) {
		this.width = width;
		this.height = height;
		setMap(new Node[width][height]);
		setMapND(new Node[width][height]);
		
		loadMap(data);
	}
	
	private void loadMap(long[][] data) {
		for(int  i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				Position position = new Position(j, i);
				Direct direct;
				switch ((int)data[j][i]) {
					case 0:{
						direct = Direct.BLOCK;
						break;
					}
					case 12: {
						direct = Direct.N;
						break;
					}
					case 20: {
						direct = Direct.S;
						break;
					}
					case 28: {
						direct = Direct.E;
						break;
					}
					case 36: {
						direct = Direct.W;
						break;
					}
					default: {
						direct = Direct.ALL;
						break;
					}
				}
				getMap()[j][i] = new Node(position, direct);
				if((int)data[j][i] == 0) {
					getMapND()[j][i] = new Node(position, Direct.BLOCK);
				}
				else {
					getMapND()[j][i] = new Node(position, Direct.ALL);
				}
			}
		}
		setupNextNode();
	}
	
	private void setupNextNode() {
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				
				switch (getMap()[j][i].getDirect()) {
					case BLOCK:{
						break;
					}
					case E:{
						getMap()[j][i].getNextNode().add(getMap()[j+1][i]);
						break;
					}
					case N:{
						getMap()[j][i].getNextNode().add(getMap()[j][i-1]);
						break;
					}
					case W:{
						getMap()[j][i].getNextNode().add(getMap()[j-1][i]);
						break;
					}
					case S:{
						getMap()[j][i].getNextNode().add(getMap()[j][i+1]);
						break;
					}
					case ALL:{
						if(j + 1 < width && getMap()[j+1][i].getDirect() == Direct.E || getMap()[j+1][i].getDirect() == Direct.ALL) {
							getMap()[j][i].getNextNode().add(getMap()[j+1][i]);
						}
						if(j - 1 >= 0 && getMap()[j-1][i].getDirect() == Direct.W || getMap()[j-1][i].getDirect() == Direct.ALL) {
							getMap()[j][i].getNextNode().add(getMap()[j-1][i]);
						}
						if(i - 1 > 0 && getMap()[j][i-1].getDirect() == Direct.N || getMap()[j][i-1].getDirect() == Direct.ALL) {
							getMap()[j][i].getNextNode().add(getMap()[j][i-1]);
						}
						if(i + 1 < height && getMap()[j][i+1].getDirect() == Direct.S || getMap()[j][i+1].getDirect() == Direct.ALL) {
							getMap()[j][i].getNextNode().add(getMap()[j][i+1]);
						}
						break;
					}
				}
				
				if(j + 1 < width && getMapND()[j+1][i].getDirect() != Direct.BLOCK) {
					getMapND()[j][i].getNextNode().add(getMapND()[j+1][i]);
				}
				if(j - 1 >= 0 && getMapND()[j-1][i].getDirect() != Direct.BLOCK) {
					getMapND()[j][i].getNextNode().add(getMapND()[j-1][i]);
				}
				if(i - 1 > 0 && getMapND()[j][i-1].getDirect() != Direct.BLOCK) {
					getMapND()[j][i].getNextNode().add(getMapND()[j][i-1]);
				}
				if(i + 1 < height && getMapND()[j][i+1].getDirect() != Direct.BLOCK) {
					getMapND()[j][i].getNextNode().add(getMapND()[j][i+1]);
				}
			}
		}
	}
	
	public Vector<Node> AStarAlgorithm(Node start, Node end) {
		
		clearData();
		
		Vector<Node> open = new Vector<>();
		Vector<Node> close = new Vector<>();
		
		start.setG(0);
		start.setH(Position.distance(start.getPosition(), end.getPosition()));
		start.setF(start.getG() + start.getH());
		
		open.add(start);
		
		while(open.size() != 0) {
			Node currentNode = open.get(0);
			for(int i = 0; i < open.size(); i++) {
				Node nodeIndex = open.get(i);
				if(nodeIndex.getF() < currentNode.getF()) {
					currentNode = nodeIndex;
				}
				open.remove(currentNode);
				close.add(currentNode);
				if(currentNode == end) {
					open.clear();
					close.clear();
					return tracePath(end);
				}
				else {
					for(Node nextNode : currentNode.getNextNode()) {
						if(close.contains(nextNode)) {
							continue;
						}
						double tmpG = currentNode.getG() + Position.distance(currentNode.getPosition(), nextNode.getPosition());
						if(!open.contains(nextNode) || tmpG < nextNode.getG()) {
							nextNode.setPreNode(currentNode);
							nextNode.setG(tmpG);
							nextNode.setH(Position.distance(nextNode.getPosition(), end.getPosition()));
							nextNode.setF(nextNode.getG() + nextNode.getH());
							
							if(!open.contains(nextNode)) {
								open.add(nextNode);
							}
						}
					}
				}
			}
		}
	
		return null;	
	}
	
	public Vector<Node> AStarAlgorithmND(Node start, Node end) {
		
		clearDataND();
		
		Vector<Node> open = new Vector<>();
		Vector<Node> close = new Vector<>();
		
		start.setG(0);
		start.setH(Position.distance(start.getPosition(), end.getPosition()));
		start.setF(start.getG() + start.getH());
		
		open.add(start);
		
		while(open.size() != 0) {
			Node currentNode = open.get(0);
			for(int i = 0; i < open.size(); i++) {
				Node nodeIndex = open.get(i);
				if(nodeIndex.getF() < currentNode.getF()) {
					currentNode = nodeIndex;
				}
				open.remove(currentNode);
				close.add(currentNode);
				if(currentNode == end) {
					open.clear();
					close.clear();
					return tracePath(end);
				}
				else {
					for(Node nextNode : currentNode.getNextNode()) {
						if(close.contains(nextNode)) {
							continue;
						}
						double tmpG = currentNode.getG() + Position.distance(currentNode.getPosition(), nextNode.getPosition());
						if(!open.contains(nextNode) || tmpG < nextNode.getG()) {
							nextNode.setPreNode(currentNode);
							nextNode.setG(tmpG);
							nextNode.setH(Position.distance(nextNode.getPosition(), end.getPosition()));
							nextNode.setF(nextNode.getG() + nextNode.getH());
							
							if(!open.contains(nextNode)) {
								open.add(nextNode);
							}
						}
					}
				}
			}
		}
	
		return null;	
	}
	
	public Vector<Node> tracePath(Node t){
		Vector<Node> path = new Vector<>();
		
		Node tmp = t;
		while(tmp != null) {
			if(tmp.getPreNode() == null) {
				return path;
			}
			path.add(tmp);
			tmp = tmp.getPreNode();
		}
		return path;		
	}
	
	private void clearData() {
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				getMap()[j][i].setF(9999);
				getMap()[j][i].setG(9999);
				getMap()[j][i].setH(9999);
				getMap()[j][i].setPreNode(null);
			}
		}
	}
	
	private void clearDataND() {
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				getMapND()[j][i].setF(9999);
				getMapND()[j][i].setG(9999);
				getMapND()[j][i].setH(9999);
				getMapND()[j][i].setPreNode(null);
			}
		}
	}

	public Node[][] getMap() {
		return map;
	}

	public void setMap(Node[][] map) {
		this.map = map;
	}

	public Node[][] getMapND() {
		return mapND;
	}

	public void setMapND(Node[][] mapND) {
		this.mapND = mapND;
	}
}
