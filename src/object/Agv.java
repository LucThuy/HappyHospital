package object;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;

import algorithm.Node;
import algorithm.Position;

public class Agv {

	private Position position = new Position();
	private Rectangle bound = new Rectangle();
	
	private int agvID;

	private int msE;
	private int msN;
	private int msW;
	private int msS;
	private int ms;
	
	private boolean isAgvDone = false;
	private int task = 1;
	
	private Vector<Node> path;
	private Node nextNode = new Node();
	
	private Image img;
	
	public final int WIDTH = 28;
	public final int HEIGHT = 28;
	public final int SIZE = 28;

	public Agv(int x, int y, Vector<Node> path, int agvID) throws IOException {
		this.getPosition().x = x;
		this.getPosition().y = y;
		this.ms = 1;
		this.msE = 0;
		this.msN = 0;
		this.msW = 0;
		this.msS = 0;
		this.setAgvID(agvID);
		
		this.setPath(path);
		this.setNextNode(path.remove(path.size() - 1));
		updateDirect(getNextNode());
		
		BufferedImage bigImage = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
		bigImage = ImageIO.read(new File("data/agv.png"));
		img = bigImage.getSubimage(3, 5, 24, 24);
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.GREEN);
		g.drawRect(this.getPosition().x, this.getPosition().y, WIDTH, HEIGHT);
		g.drawImage(img, this.getPosition().x, this.getPosition().y, WIDTH, HEIGHT, null);
		g.setColor(Color.BLACK);
		g.drawString(String.valueOf(getAgvID()), this.getPosition().x + 3, this.getPosition().y - 3);
	}
	
	public void draw(Graphics g, boolean isZaWarudo) {
		g.setColor(Color.BLACK);
		g.fillRect(this.getPosition().x, this.getPosition().y, WIDTH, HEIGHT);
	}
	
	public void setBound() {
		this.bound.setBounds(this.getPosition().x, this.getPosition().y, WIDTH, HEIGHT);
	}
	
	public void findNextMove() {
		if(isFinishMove()) {
			this.msE = 0;
			this.msN = 0;
			this.msW = 0;
			this.msS = 0;
			if(getPath().isEmpty()) {
				this.setAgvDone(true);
			}
			else {
				setNextNode(getPath().remove(getPath().size() - 1));
				updateDirect(getNextNode());
			}
		}
	}
	
	private boolean isFinishMove() {
		if(this.getPosition().x == getNextNode().position.x * SIZE && this.getPosition().y == getNextNode().position.y * SIZE) {
			return true;
		}
		return false;
	}
	
	private void updateDirect(Node nextNode) {
		if(this.msE == 0 && nextNode.position.x * SIZE > this.getPosition().x) {
			this.msE = this.ms;
		}
		if(this.msN == 0 && nextNode.position.y * SIZE < this.getPosition().y) {
			this.msN = this.ms;
		}
		if(this.msW == 0 && nextNode.position.x * SIZE < this.getPosition().x) {
			this.msW = this.ms;
		}
		if(this.msS == 0 && nextNode.position.y * SIZE > this.getPosition().y) {
			this.msS = this.ms;
		}
	}
	
	public void move(Vector<Rectangle> block) {
		findNextMove();
		Position tmp = new Position(this.getPosition().x, this.getPosition().y);
		
		if(this.msE != 0) {
			tmp.x += this.msE;
			while(!isOK(tmp, block)) {
				tmp.x --;
			}
		}
		if(this.msN != 0) {
			tmp.y -= this.msN;
			while(!isOK(tmp, block)) {
				tmp.y ++;
			}
		}
		if(this.msW != 0) {
			tmp.x -= this.msW;
			while(!isOK(tmp, block)) {
				tmp.x ++;
			}
		}
		if(this.msS != 0) {
			tmp.y += this.msS;
			while(!isOK(tmp, block)) {
				tmp.y --;
			}
		}
		
		this.setPosition(tmp);
		setBound();
	}
	
	public boolean isOK(Position tmp, Vector<Rectangle> block) {
		Rectangle tmpBound = new Rectangle(tmp.x, tmp.y, WIDTH, HEIGHT);
		for(int i = 0; i < block.size(); i++) {
			if(isCollision(tmpBound, block.get(i))) {
				return false;
			}
		}
		return true;
	}
	
	public boolean isCollision(Rectangle a, Rectangle b) {
		if(a.intersects(b)) {
			return true;
		}
		return false;
	}

	public int getAgvID() {
		return agvID;
	}

	public void setAgvID(int agvID) {
		this.agvID = agvID;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public Rectangle getBound() {
		return bound;
	}

	public void setBound(Rectangle bound) {
		this.bound = bound;
	}

	public Node getNextNode() {
		return nextNode;
	}

	public void setNextNode(Node nextNode) {
		this.nextNode = nextNode;
	}

	public Vector<Node> getPath() {
		return path;
	}

	public void setPath(Vector<Node> path) {
		this.path = path;
	}

	public boolean isAgvDone() {
		return isAgvDone;
	}

	public void setAgvDone(boolean isAgvDone) {
		this.isAgvDone = isAgvDone;
	}

	public int getTask() {
		return task;
	}

	public void setTask(int task) {
		this.task = task;
	}
}
