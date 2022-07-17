package algorithm;

import java.util.Vector;

public class AStar {
	
	public Node[][] map;
	public Node[][] mapND;
	
	public int width;
	public int height;
	
	public AStar(long[][] data, int width, int height) {
		this.width = width;
		this.height = height;
		map = new Node[width][height];
		mapND = new Node[width][height];
		
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
				map[j][i] = new Node(position, direct);
				if((int)data[j][i] == 0) {
					mapND[j][i] = new Node(position, Direct.BLOCK);
				}
				else {
					mapND[j][i] = new Node(position, Direct.ALL);
				}
			}
		}
		setupNextNode();
	}
	
	private void setupNextNode() {
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				
				switch (map[j][i].direct) {
					case BLOCK:{
						break;
					}
					case E:{
						map[j][i].nextNode.add(map[j+1][i]);
						break;
					}
					case N:{
						map[j][i].nextNode.add(map[j][i-1]);
						break;
					}
					case W:{
						map[j][i].nextNode.add(map[j-1][i]);
						break;
					}
					case S:{
						map[j][i].nextNode.add(map[j][i+1]);
						break;
					}
					case ALL:{
						if(j + 1 < width && map[j+1][i].direct == Direct.E || map[j+1][i].direct == Direct.ALL) {
							map[j][i].nextNode.add(map[j+1][i]);
						}
						if(j - 1 >= 0 && map[j-1][i].direct == Direct.W || map[j-1][i].direct == Direct.ALL) {
							map[j][i].nextNode.add(map[j-1][i]);
						}
						if(i - 1 > 0 && map[j][i-1].direct == Direct.N || map[j][i-1].direct == Direct.ALL) {
							map[j][i].nextNode.add(map[j][i-1]);
						}
						if(i + 1 < height && map[j][i+1].direct == Direct.S || map[j][i+1].direct == Direct.ALL) {
							map[j][i].nextNode.add(map[j][i+1]);
						}
						break;
					}
				}
				
				if(j + 1 < width && mapND[j+1][i].direct != Direct.BLOCK) {
					mapND[j][i].nextNode.add(mapND[j+1][i]);
				}
				if(j - 1 >= 0 && mapND[j-1][i].direct != Direct.BLOCK) {
					mapND[j][i].nextNode.add(mapND[j-1][i]);
				}
				if(i - 1 > 0 && mapND[j][i-1].direct != Direct.BLOCK) {
					mapND[j][i].nextNode.add(mapND[j][i-1]);
				}
				if(i + 1 < height && mapND[j][i+1].direct != Direct.BLOCK) {
					mapND[j][i].nextNode.add(mapND[j][i+1]);
				}
			}
		}
	}
	
	public Vector<Node> AStarAlgorithm(Node start, Node end) {
		
		clearData();
		
		Vector<Node> open = new Vector<>();
		Vector<Node> close = new Vector<>();
		
		start.G = 0;
		start.H = Position.distance(start.position, end.position);
		start.F = start.G + start.H;
		
		open.add(start);
		
		while(open.size() != 0) {
			Node currentNode = open.get(0);
			for(int i = 0; i < open.size(); i++) {
				Node nodeIndex = open.get(i);
				if(nodeIndex.F < currentNode.F) {
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
					for(Node nextNode : currentNode.nextNode) {
						if(close.contains(nextNode)) {
							continue;
						}
						double tmpG = currentNode.G + Position.distance(currentNode.position, nextNode.position);
						if(!open.contains(nextNode) || tmpG < nextNode.G) {
							nextNode.preNode = currentNode;
							nextNode.G = tmpG;
							nextNode.H = Position.distance(nextNode.position, end.position);
							nextNode.F = nextNode.G + nextNode.H;
							
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
		
		start.G = 0;
		start.H = Position.distance(start.position, end.position);
		start.F = start.G + start.H;
		
		open.add(start);
		
		while(open.size() != 0) {
			Node currentNode = open.get(0);
			for(int i = 0; i < open.size(); i++) {
				Node nodeIndex = open.get(i);
				if(nodeIndex.F < currentNode.F) {
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
					for(Node nextNode : currentNode.nextNode) {
						if(close.contains(nextNode)) {
							continue;
						}
						double tmpG = currentNode.G + Position.distance(currentNode.position, nextNode.position);
						if(!open.contains(nextNode) || tmpG < nextNode.G) {
							nextNode.preNode = currentNode;
							nextNode.G = tmpG;
							nextNode.H = Position.distance(nextNode.position, end.position);
							nextNode.F = nextNode.G + nextNode.H;
							
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
			if(tmp.preNode == null) {
				return path;
			}
			path.add(tmp);
			tmp = tmp.preNode;
		}
		return path;		
	}
	
	private void clearData() {
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				map[j][i].F = 9999;
				map[j][i].G = 9999;
				map[j][i].H = 9999;
				map[j][i].preNode = null;
			}
		}
	}
	
	private void clearDataND() {
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				mapND[j][i].F = 9999;
				mapND[j][i].G = 9999;
				mapND[j][i].H = 9999;
				mapND[j][i].preNode = null;
			}
		}
	}
}
