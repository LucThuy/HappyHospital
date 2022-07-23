package client;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import algorithm.Position;
import object.Blink;

public class PlayerC {
	public Position position = new Position();
	
	public String score;
	
	private Blink blink = new Blink();
	
	private Image img;
	
	private final int WIDTH = 28;
	private final int HEIGHT = 28;
	private final int SIZE = 28;
	
	private String name;
	
	public PlayerC(int x, int y, String name) throws IOException {
		this.position.setX(x);
		this.position.setY(y);
		this.name = name;
		
		this.blink.setBlink(false);
		
		BufferedImage bigImage = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
		bigImage = ImageIO.read(new File("data/agv.png"));
		img = bigImage.getSubimage(3, 5, 24, 24);
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.GREEN);
		g.drawRect(this.position.getX(), this.position.getY(), WIDTH, HEIGHT);
		g.drawImage(this.img, this.position.getX(), this.position.getY(), WIDTH, HEIGHT, null);
		
		g.setFont(new Font("Georgia", Font.PLAIN, 10));
		g.drawString(this.name, this.position.getX(), this.position.getY() - 3);
	}
	
	public void draw(Graphics g, String type) throws IOException {
		g.setColor(Color.GREEN);
		if(type.equals("host")) {
			g.setColor(Color.MAGENTA);
			BufferedImage bigImage = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
			bigImage = ImageIO.read(new File("data/agv2.png"));
			img = bigImage.getSubimage(3, 5, 24, 24);
		}
		else if(type.equals("guest")) {
			g.setColor(Color.GREEN);
			BufferedImage bigImage = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
			bigImage = ImageIO.read(new File("data/agv5.png"));
			img = bigImage.getSubimage(3, 5, 24, 24);
		}
		
		g.drawRect(this.position.getX(), this.position.getY(), WIDTH, HEIGHT);
		g.drawImage(this.img, this.position.getX(), this.position.getY(), WIDTH, HEIGHT, null);
		
		g.setFont(new Font("Georgia", Font.PLAIN, 10));
		g.drawString(this.name, this.position.getX(), this.position.getY() - 3);
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
