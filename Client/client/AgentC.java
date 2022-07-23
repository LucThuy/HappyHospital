package client;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import algorithm.Position;

public class AgentC {
	public Position position = new Position();
	
	public int id;
	
	public Image img;
	
	public final int WIDTH = 28;
	public final int HEIGHT = 28;
	public final int SIZE = 28;
	
	public AgentC(int x, int y, int id) throws IOException {
		this.position.setX(x);
		this.position.setY(y);
		this.id = id;
		
		BufferedImage bigImage = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
		bigImage = ImageIO.read(new File("data/hospital.png"));
		img = bigImage.getSubimage(35, 67, 26, 24);
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.GREEN);
		g.drawRect(this.position.getX(), this.position.getY(), WIDTH, HEIGHT);
		g.drawImage(img, this.position.getX(), this.position.getY(), WIDTH, HEIGHT, null);
		g.setColor(Color.GRAY);
		g.drawString(String.valueOf(id), this.position.getX() + 15, this.position.getY() - 3);
	}
	
	public void draw(Graphics g, boolean isZaWarudo) {
		g.setColor(Color.BLACK);
		g.fillRect(this.position.getX(), this.position.getY(), WIDTH, HEIGHT);
	}
}
