package object;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import algorithm.Cooldown;

public class ZaWarudo {
	public static boolean isZaWarudo = false;
	public static Cooldown zaWarudoCD = new Cooldown(5000);
	private int x;
	private int y;
	
	private Rectangle bound;
	
	private int size;
	
	public ZaWarudo() {
		
	}
	
	public ZaWarudo(int x, int y, int size) {
		this.x = x;
		this.y = y;
		this.size = size;
		
		setBound(new Rectangle(x, y, size, size));
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect(x + 3, y + 3, size - 6, size - 6);
	}

	public Rectangle getBound() {
		return bound;
	}

	public void setBound(Rectangle bound) {
		this.bound = bound;
	}
}
