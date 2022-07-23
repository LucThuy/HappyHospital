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

public class Agent {

	private Position position = new Position();
	private Rectangle bound = new Rectangle();
	
	private int agentID;

	private int msE;
	private int msN;
	private int msW;
	private int msS;
	private int ms;
	
	private boolean isAgentDone = false;
	
	private Vector<Node> path;
	private Node nextNode = new Node();
	
	private Image img;
	
	public final int WIDTH = 28;
	public final int HEIGHT = 28;
	public final int SIZE = 28;

	public Agent(int x, int y, Vector<Node> path, int agentID) throws IOException {
		this.getPosition().setX(x);
		this.getPosition().setY(y);
		this.ms = 1;
		this.msE = 0;
		this.msN = 0;
		this.msW = 0;
		this.msS = 0;
		this.setAgentID(agentID);
		
		this.setPath(path);
		this.nextNode = path.remove(path.size() - 1);
		updateDirect(nextNode);	
		
		BufferedImage bigImage = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
		bigImage = ImageIO.read(new File("data/hospital.png"));
		img = bigImage.getSubimage(35, 67, 26, 24);
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.GREEN);
		g.drawRect(this.getPosition().getX(), this.getPosition().getY(), WIDTH, HEIGHT);
		g.drawImage(img, this.getPosition().getX(), this.getPosition().getY(), WIDTH, HEIGHT, null);
		g.setColor(Color.GRAY);
		g.drawString(String.valueOf(getAgentID()), this.getPosition().getX() + 15, this.getPosition().getY() - 3);
	}
	
	public void draw(Graphics g, boolean isZaWarudo) {
		g.setColor(Color.BLACK);
		g.fillRect(this.getPosition().getX(), this.getPosition().getY(), WIDTH, HEIGHT);
	}
	
	public void setBound() {
		this.bound.setBounds(this.getPosition().getX(), this.getPosition().getY(), WIDTH, HEIGHT);
	}
	
	public void findNextMove() {
		if(isFinishMove()) {
			this.msE = 0;
			this.msN = 0;
			this.msW = 0;
			this.msS = 0;
			if(getPath().isEmpty()) {
				this.setAgentDone(true);
			}
			else {
				nextNode = getPath().remove(getPath().size() - 1);
				updateDirect(nextNode);
			}
		}
	}
	
	private boolean isFinishMove() {
		if(this.getPosition().getX() == nextNode.getPosition().getX() * SIZE && this.getPosition().getY() == nextNode.getPosition().getY() * SIZE) {
			return true;
		}
		return false;
	}
	
	private void updateDirect(Node nextNode) {
		if(this.msE == 0 && nextNode.getPosition().getX() * SIZE > this.getPosition().getX()) {
			this.msE = this.ms;
		}
		if(this.msN == 0 && nextNode.getPosition().getY() * SIZE < this.getPosition().getY()) {
			this.msN = this.ms;
		}
		if(this.msW == 0 && nextNode.getPosition().getX() * SIZE < this.getPosition().getX()) {
			this.msW = this.ms;
		}
		if(this.msS == 0 && nextNode.getPosition().getY() * SIZE > this.getPosition().getY()) {
			this.msS = this.ms;
		}
	}
	
	public void move(Vector<Rectangle> block) {
		findNextMove();
		Position tmp = new Position(this.getPosition().getX(), this.getPosition().getY());
		
		if(this.msE != 0) {
			tmp.setX(tmp.getX() + this.msE);
			while(!isOK(tmp, block)) {
				tmp.setX(tmp.getX() - 1);
			}
		}
		if(this.msN != 0) {
			tmp.setY(tmp.getY() - this.msN);
			while(!isOK(tmp, block)) {
				tmp.setY(tmp.getY() + 1);
			}
		}
		if(this.msW != 0) {
			tmp.setX(tmp.getX() - this.msW);
			while(!isOK(tmp, block)) {
				tmp.setX(tmp.getX() + 1);
			}
		}
		if(this.msS != 0) {
			tmp.setY(tmp.getY() + this.msS);
			while(!isOK(tmp, block)) {
				tmp.setY(tmp.getY() - 1);
			}
		}
		
		this.setPosition(tmp);
		setBound();
	}
	
	public boolean isOK(Position tmp, Vector<Rectangle> block) {
		Rectangle tmpBound = new Rectangle(tmp.getX(), tmp.getY(), WIDTH, HEIGHT);
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

	public boolean isAgentDone() {
		return isAgentDone;
	}

	public void setAgentDone(boolean isAgentDone) {
		this.isAgentDone = isAgentDone;
	}

	public Vector<Node> getPath() {
		return path;
	}

	public void setPath(Vector<Node> path) {
		this.path = path;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public int getAgentID() {
		return agentID;
	}

	public void setAgentID(int agentID) {
		this.agentID = agentID;
	}

	public Rectangle getBound() {
		return bound;
	}

	public void setBound(Rectangle bound) {
		this.bound = bound;
	}
}
