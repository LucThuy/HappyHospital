package map;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.util.Vector;

public class Door extends Layer {
	
	public Door(long id, long[] data, String name, Vector<Tile> tiles) {
		super(id, data, name, tiles);
	}
	
//	public void draw(Graphics g) {
//		for(int i = 0; i < HEIGHT; i++) {
//			for(int j = 0; j < WIDTH; j++) {
//				if(dataArr[j][i] != 0) {
//					g.setColor(Color.MAGENTA);
//					g.fillRect(j * SIZE, i * SIZE, SIZE, SIZE);
//				}
//			}
//		}
//	}
	
	public void drawEnd(Graphics g, int endDoorID, String type) {
		Stroke stroke = new BasicStroke(3f);
		Graphics2D g2d = (Graphics2D)g;
		g2d.setStroke(stroke);
		
		g2d.setColor(Color.GREEN);
		if(type.equals("host")) {
			g2d.setColor(Color.ORANGE);		
		}
		else if(type.equals("guest")){
			g2d.setColor(Color.GREEN);
		}
		g2d.drawRect(super.getBound().get(endDoorID).x - 1, super.getBound().get(endDoorID).y - 1, super.SIZE + 2, super.SIZE + 2);
	}
	
	public void drawEnd(Graphics g, int endDoorID) {
		Stroke stroke = new BasicStroke(3f);
		Graphics2D g2d = (Graphics2D)g;
		g2d.setStroke(stroke);
		
		g2d.setColor(Color.ORANGE);
		g2d.drawRect(super.getBound().get(endDoorID).x - 1, super.getBound().get(endDoorID).y - 1, super.SIZE + 2, super.SIZE + 2);
	}
	
	public void drawEnd(Graphics g, Vector<EndPoint> endPoint, String name) {
		for(int i = 0; i < endPoint.size(); i++) {
			int ID = endPoint.get(i).getID();
			int doorID = endPoint.get(i).getDoorID();
			if(doorID == -1) {
				continue;
			}
			long check = super.getDataArr()[super.getBound().get(doorID).x / super.SIZE][super.getBound().get(doorID).y / super.SIZE];
			if(name == "agv") {
				g.setColor(Color.BLACK);
				if(check == 19) {
					g.drawString(String.valueOf(ID), super.getBound().get(doorID).x + 3, super.getBound().get(doorID).y - 3);
				}
				else if(check == 26) {
					g.drawString(String.valueOf(ID), super.getBound().get(doorID).x + 3, super.getBound().get(doorID).y + super.SIZE + 10);
				}
			}
			if(name == "agent") {
				g.setColor(Color.GRAY);
				if(check == 19) {
					g.drawString(String.valueOf(ID), super.getBound().get(doorID).x + 15, super.getBound().get(doorID).y - 3);
				}
				else if(check == 26) {
					g.drawString(String.valueOf(ID), super.getBound().get(doorID).x + 15, super.getBound().get(doorID).y + super.SIZE + 10);
				}
			}				
		}
	}
	
	public void draw(Graphics g, boolean isZaWarudo) {
		for(int i = 0; i < HEIGHT; i++) {
			for(int j = 0; j < WIDTH; j++) {
				if(getDataArr()[j][i] != 0) {
					g.setColor(Color.LIGHT_GRAY);
					g.fillRect(j * SIZE, i * SIZE, SIZE, SIZE);
				}
			}
		}
	}
}
