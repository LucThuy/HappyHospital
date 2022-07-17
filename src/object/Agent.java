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

	public Position position = new Position();
	public Rectangle bound = new Rectangle();
	
	public int agentID;

	public int msE;
	public int msN;
	public int msW;
	public int msS;
	public int ms;
	
	public boolean isAgentDone = false;
	
	public Vector<Node> path;
	public Node nextNode = new Node();
	
	public Image img;
	
	public final int WIDTH = 28;
	public final int HEIGHT = 28;
	public final int SIZE = 28;

	public Agent(int x, int y, Vector<Node> path, int agentID) throws IOException {
		this.position.x = x;
		this.position.y = y;
		this.ms = 1;
		this.msE = 0;
		this.msN = 0;
		this.msW = 0;
		this.msS = 0;
		this.agentID = agentID;
		
		this.path = path;
		this.nextNode = path.remove(path.size() - 1);
		updateDirect(nextNode);	
		
		BufferedImage bigImage = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
		bigImage = ImageIO.read(new File("data/hospital.png"));
		img = bigImage.getSubimage(35, 67, 26, 24);
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.GREEN);
		g.drawRect(this.position.x, this.position.y, WIDTH, HEIGHT);
		g.drawImage(img, this.position.x, this.position.y, WIDTH, HEIGHT, null);
		g.setColor(Color.GRAY);
		g.drawString(String.valueOf(agentID), this.position.x + 15, this.position.y - 3);
	}
	
	public void draw(Graphics g, boolean isZaWarudo) {
		g.setColor(Color.BLACK);
		g.fillRect(this.position.x, this.position.y, WIDTH, HEIGHT);
	}
	
	public void setBound() {
		this.bound.setBounds(this.position.x, this.position.y, WIDTH, HEIGHT);
	}
	
	public void findNextMove() {
		if(isFinishMove()) {
			this.msE = 0;
			this.msN = 0;
			this.msW = 0;
			this.msS = 0;
			if(path.isEmpty()) {
				this.isAgentDone = true;
			}
			else {
				nextNode = path.remove(path.size() - 1);
				updateDirect(nextNode);
			}
		}
	}
	
	private boolean isFinishMove() {
		if(this.position.x == nextNode.position.x * SIZE && this.position.y == nextNode.position.y * SIZE) {
			return true;
		}
		return false;
	}
	
	private void updateDirect(Node nextNode) {
		if(this.msE == 0 && nextNode.position.x * SIZE > this.position.x) {
			this.msE = this.ms;
		}
		if(this.msN == 0 && nextNode.position.y * SIZE < this.position.y) {
			this.msN = this.ms;
		}
		if(this.msW == 0 && nextNode.position.x * SIZE < this.position.x) {
			this.msW = this.ms;
		}
		if(this.msS == 0 && nextNode.position.y * SIZE > this.position.y) {
			this.msS = this.ms;
		}
	}
	
	public void move(Vector<Rectangle> block) {
		findNextMove();
		Position tmp = new Position(this.position.x, this.position.y);
		
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
		
		this.position = tmp;
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
}
