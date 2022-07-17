package map;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Vector;

public class Elevator extends Layer {
	
//	public Vector<Rectangle> checkPoint = new Vector<>();
	
	public Elevator(long id, long[] data, String name, Vector<Tile> tiles) {
		super(id, data, name, tiles);
		
		//can fix
//		setCheckPoint(0, 2);
//		setCheckPoint(1, 3);
	}
	
	public void draw(Graphics g, boolean isZaWarudo) {
		for(int i = 0; i < HEIGHT; i++) {
			for(int j = 0; j < WIDTH; j++) {
				if(dataArr[j][i] != 0) {
					g.setColor(Color.LIGHT_GRAY);
					g.fillRect(j * SIZE, i * SIZE, SIZE, SIZE);
				}
			}
		}
	}
	
//	public void draw(Graphics g) {
//		for(int i = 0; i < HEIGHT; i++) {
//			for(int j = 0; j < WIDTH; j++) {
//				if(dataArr[j][i] != 0) {
//					g.setColor(Color.YELLOW);
//					g.fillRect(j * SIZE, i * SIZE, SIZE, SIZE);
//				}
//			}
//		}
//	}
	
	
	//can fix
//	public void setCheckPoint(int a, int b) {
//		Rectangle newCheckPoint = new Rectangle(super.bound.get(a));
//		newCheckPoint.add(super.bound.get(b));
//		this.checkPoint.add(newCheckPoint);
//	}
}
