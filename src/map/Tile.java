package map;

import java.awt.Image;

public class Tile {
	private int id;
	private Image img;
	
	public Tile(int id, Image img) {
		this.id = id;
		this.setImg(img);
	}

	public Image getImg() {
		return img;
	}

	public void setImg(Image img) {
		this.img = img;
	}
}
