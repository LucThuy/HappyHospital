package server;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;

import algorithm.Position;
import object.Blink;

public class PlayerS {
	public Position position = new Position();
	public int msE;
	public int msN;
	public int msW;
	public int msS;
	public int ms;
	
	public float score;
	
	public long[][] data;
	
	public Blink blink = new Blink();
	
	public Rectangle bound = new Rectangle();
	
	public final int WIDTH = 28;
	public final int HEIGHT = 28;
	public final int SIZE = 28;
	
	public PlayerS(int x, int y, long[][] data) {
		this.position.setX(x);
		this.position.setY(y);
		this.ms = 1;
		this.msE = 0;
		this.msN = 0;
		this.msW = 0;
		this.msS = 0;
		
		this.score = 0;
		
		this.data = data;
		
		this.blink.setBlink(false);
	}
	
	public void setBound() {
		this.bound.setBounds(this.position.getX(), this.position.getY(), WIDTH, HEIGHT);
	}
	
	public void move(Vector<Rectangle> block) {
		Position tmp = new Position(this.position.getX(), this.position.getY());
		
		if(this.msE != 0 && isRightWay(tmp, "E")) {
			tmp.setX(tmp.getX() + this.msE);
			if(this.blink.isBlink()) {
				tmp.setX(tmp.getX() + this.blink.BLINK_RANGE * SIZE);
				this.blink.setBlink(false);
			}
			while(!isOK(tmp, block)) {
				tmp.setX(tmp.getX() - 1);
			}
		}
		if(this.msN != 0 && isRightWay(tmp, "N")) {
			tmp.setY(tmp.getY() - this.msN);
			if(this.blink.isBlink()) {
				tmp.setY(tmp.getY() - this.blink.BLINK_RANGE * SIZE);
				this.blink.setBlink(false);
			}
			while(!isOK(tmp, block)) {
				tmp.setY(tmp.getY() + 1);
			}
		}
		if(this.msW != 0 && isRightWay(tmp, "W")) {
			tmp.setX(tmp.getX() - this.msW);
			if(this.blink.isBlink()) {
				tmp.setX(tmp.getX() - this.blink.BLINK_RANGE * SIZE);
				this.blink.setBlink(false);
			}
			while(!isOK(tmp, block)) {
				tmp.setX(tmp.getX() + 1);
			}
		}
		if(this.msS != 0 && isRightWay(tmp, "S")) {
			tmp.setY(tmp.getY() + this.msS);
			if(this.blink.isBlink()) {
				tmp.setY(tmp.getY() + this.blink.BLINK_RANGE * SIZE);
				this.blink.setBlink(false);
			}
			while(!isOK(tmp, block)) {
				tmp.setY(tmp.getY() - 1);
			}
		}
		
		this.position = tmp;
		setBound();
	}
	
	private boolean isRightWay(Position tmp, String direct) {
		int x = tmp.getX() / SIZE;
		int y = tmp.getY() / SIZE;
		int checkX = tmp.getX() % SIZE;
		int checkY = tmp.getY() % SIZE;
		if(direct == "E") {
			long check = data[x][y];
			if(check == 20 || check == 12 || check == 36)
				return false;
			if(checkX == 0 && checkY == 0) {
				long checkNext = data[x + 1][y];
				if(checkNext == 20 || checkNext == 12 || checkNext == 36)
					return false;
			}
		}
		else if(direct == "N") {
			long check = data[x][y];
			if(check == 20 || check == 28 || check == 36)
				return false;
			if(checkX == 0 && checkY == 0) {
				long checkNext = data[x][y - 1];
				if(checkNext == 20 || checkNext == 28 || checkNext == 36)
					return false;
			}
		}		
		else if(direct == "W") {
			long check = data[x][y];
			if(check == 20 || check == 28 || check == 12)
				return false;
			if(checkX == 0 && checkY == 0) {
				long checkNext = data[x - 1][y];
				if(checkNext == 20 || checkNext == 28 || checkNext == 12)
					return false;
			}
		}
		else if(direct == "S") {
			long check = data[x][y];
			if(check == 12 || check == 28 || check == 36)
				return false;
			if(checkX == 0 && checkY == 0) {
				long checkNext = data[x][y + 1];
				if(checkNext == 12 || checkNext == 28 || checkNext == 36)
					return false;
			}
		}
		return true;
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
}
