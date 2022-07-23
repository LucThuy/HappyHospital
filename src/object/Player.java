package object;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;

import algorithm.Cooldown;
import algorithm.Position;

public class Player {
	
	private Position position = new Position();
	private int msE;
	private int msN;
	private int msW;
	private int msS;
	private int ms;
	
	private float score;
	
	private long[][] data;
	
	private Blink blink = new Blink();
	
	private Rectangle bound = new Rectangle();
	
	private Image img;
	
	private int WIDTH = 28;
	private int HEIGHT = 28;
	public final int SIZE = 28;
	
	private String name;
	
	public Player(int x, int y, long[][] data) throws IOException {
		this.getPosition().x = x;
		this.getPosition().y = y;
		this.data = data;
		this.setMs(1);
		this.setMsE(0);
		this.setMsN(0);
		this.setMsW(0);
		this.setMsS(0);
		
		this.setScore(0);
		
		this.name = "minh";
		
		this.getBlink().setBlink(false);
		
		BufferedImage bigImage = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
		bigImage = ImageIO.read(new File("data/agv.png"));
		img = bigImage.getSubimage(3, 5, 24, 24);
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.GREEN);
		g.drawRect(this.getPosition().x, this.getPosition().y, WIDTH, HEIGHT);
		g.drawImage(this.img, this.getPosition().x, this.getPosition().y, WIDTH, HEIGHT, null);
		
		g.setColor(Color.ORANGE);
		g.setFont(new Font("Georgia", Font.PLAIN, 10));
		g.drawString(this.name, this.getPosition().x, this.getPosition().y - 3);
	}
	
	public void setBound() {
		this.bound.setBounds(this.getPosition().x, this.getPosition().y, WIDTH, HEIGHT);
	}
	
	public void move(Vector<Rectangle> block) {
		Position tmp = new Position(this.getPosition().x, this.getPosition().y);
		
		if(this.getMsE() != 0 && isRightWay(tmp, "E")) {
			tmp.x += this.getMsE();
			if(this.getBlink().isBlink()) {
				tmp.x += this.getBlink().BLINK_RANGE * SIZE;
				this.getBlink().setBlink(false);
			}
			while(!isOK(tmp, block)) {
				tmp.x --;
			}
		}
		if(this.getMsN() != 0 && isRightWay(tmp, "N")) {
			tmp.y -= this.getMsN();
			if(this.getBlink().isBlink()) {
				tmp.y -= this.getBlink().BLINK_RANGE * SIZE;
				this.getBlink().setBlink(false);
			}
			while(!isOK(tmp, block)) {
				tmp.y ++;
			}
		}
		if(this.getMsW() != 0 && isRightWay(tmp, "W")) {
			tmp.x -= this.getMsW();
			if(this.getBlink().isBlink()) {
				tmp.x -= this.getBlink().BLINK_RANGE * SIZE;
				this.getBlink().setBlink(false);
			}
			while(!isOK(tmp, block)) {
				tmp.x ++;
			}
		}
		if(this.getMsS() != 0 && isRightWay(tmp, "S")) {
			tmp.y += this.getMsS();
			if(this.getBlink().isBlink()) {
				tmp.y += this.getBlink().BLINK_RANGE * SIZE;
				this.getBlink().setBlink(false);
			}
			while(!isOK(tmp, block)) {
				tmp.y --;
			}
		}
		
		this.setPosition(tmp);
		setBound();
	}
	
	private boolean isRightWay(Position tmp, String direct) {
		if(ZaWarudo.isZaWarudo) {
			return true;
		}
		int x = tmp.x / SIZE;
		int y = tmp.y / SIZE;
		int checkX = tmp.x % SIZE;
		int checkY = tmp.y % SIZE;
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
	
	private boolean isOK(Position tmp, Vector<Rectangle> block) {
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
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public Rectangle getBound() {
		return bound;
	}

	public void setBound(Rectangle bound) {
		this.bound = bound;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public int getMsN() {
		return msN;
	}

	public void setMsN(int msN) {
		this.msN = msN;
	}

	public int getMsE() {
		return msE;
	}

	public void setMsE(int msE) {
		this.msE = msE;
	}

	public int getMsW() {
		return msW;
	}

	public void setMsW(int msW) {
		this.msW = msW;
	}

	public int getMsS() {
		return msS;
	}

	public void setMsS(int msS) {
		this.msS = msS;
	}

	public int getMs() {
		return ms;
	}

	public void setMs(int ms) {
		this.ms = ms;
	}

	public Blink getBlink() {
		return blink;
	}

	public void setBlink(Blink blink) {
		this.blink = blink;
	}
}
