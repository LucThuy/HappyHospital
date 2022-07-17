package map;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Vector;

public class Path extends Layer {

	public Path(long id, long[] data, String name, Vector<Tile> tiles) {
		super(id, data, name, tiles);
	}
	
//	public void draw(Graphics g) {
//		for(int i = 0; i < HEIGHT; i++) {
//			for(int j = 0; j < WIDTH; j++) {
//				if(dataArr[j][i] != 0) {
//					g.setColor(Color.WHITE);
//					g.fillRect(j * SIZE, i * SIZE, SIZE, SIZE);
//				}
//			}
//		}
//	}
	
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

}
