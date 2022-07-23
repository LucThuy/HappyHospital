package map;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Vector;

import javax.imageio.ImageIO;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Map {
	
	private Vector<Layer> layer = new Vector<>();
	
	private Ground ground;
	private Path path;
	private Room room;
	private Door door;
	private Wall wall;
	private NoPath nopath;
	private Gate gate;
	private Elevator elevator;
	private Bed bed;
	
	private Vector<Tile> tiles = new Vector<>();
	
	public Map() throws FileNotFoundException, IOException, ParseException {
		loadMap();
	}
	
	public void loadMap() throws FileNotFoundException, IOException, ParseException {
		Object obj = new JSONParser().parse(new FileReader("data/hospital.json"));
		JSONObject loadJSON = (JSONObject)obj;

		JSONArray loadLayers = (JSONArray) loadJSON.get("layers");
		
		for(int i = 0; i < loadLayers.size(); i++) {
			JSONObject layerIndex = (JSONObject) loadLayers.get(i);	
			String name = (String) layerIndex.get("name");
			long id = (long) layerIndex.get("id");
//			long height =  (long) layerIndex.get("height");
//			long width = (long) layerIndex.get("width");
//			long x = (long) layerIndex.get("x");
//			long y = (long) layerIndex.get("y");	
			JSONArray dataIndex = (JSONArray) layerIndex.get("data");
			long[] data = new long[dataIndex.size()];
			for(int j = 0; j < dataIndex.size(); j++) {
				data[j] = (long) dataIndex.get(j);
			}
			
			switch(name) {
				case "ground":{
					ground = new Ground(id, data, name, tiles);
					getLayer().add(ground);
					break;
				}
				case "path":{
					setPath(new Path(id, data, name, tiles));
					getLayer().add(getPath());
					break;
				}
				case "room":{
					room = new Room(id, data, name, tiles);
					getLayer().add(room);
					break;
				}
				case "door":{
					setDoor(new Door(id, data, name, tiles));
					getLayer().add(getDoor());
					break;
				}
				case "wall":{
					wall = new Wall(id, data, name, tiles);
					getLayer().add(wall);
					break;
				}
				case "nopath":{
					setNopath(new NoPath(id, data, name, tiles));
					getLayer().add(getNopath());
					break;
				}
				case "gate":{
					gate = new Gate(id, data, name, tiles);
					getLayer().add(gate);
					break;
				}
				case "elevator":{
					setElevator(new Elevator(id, data, name, tiles));
					getLayer().add(getElevator());
					break;
				}
				case "bed":{
					bed = new Bed(id, data, name, tiles);
					getLayer().add(bed);
					break;
				}
			}
		}
		
		JSONArray loadTileSets = (JSONArray) loadJSON.get("tilesets");
		JSONObject loadTmp = (JSONObject) loadTileSets.get(0);
		
		long imageheightL = (long) loadTmp.get("imageheight");
		int imageheight = (int) imageheightL;
		long imagewidthL = (long) loadTmp.get("imagewidth");
		int imagewidth = (int) imagewidthL;
		BufferedImage bigImage = new BufferedImage(imagewidth, imageheight, BufferedImage.TYPE_INT_ARGB);
		bigImage = ImageIO.read(new File("data/hospital.png"));
		
		long columnsL = (long) loadTmp.get("columns");
		int columns = (int) columnsL;
		long tileheightL = (long) loadTmp.get("tileheight");
		int tileheight = (int) tileheightL;
		long tilewidthL = (long) loadTmp.get("tilewidth");
		int tilewidth = (int) tilewidthL;	
		
		JSONArray loadTiles = (JSONArray) loadTmp.get("tiles");
		for(int i = 0; i < loadTiles.size(); i++) {
			JSONObject tileIndex = (JSONObject) loadTiles.get(i);
			long idL = (long) tileIndex.get("id");
			int id = (int) idL;
			Image img = bigImage.getSubimage(id % columns * tilewidth  , id / columns * tileheight, tilewidth, tileheight);
			Tile tile = new Tile(id, img);
			tiles.add(tile);
		}
	}

	public Path getPath() {
		return path;
	}

	public void setPath(Path path) {
		this.path = path;
	}

	public Elevator getElevator() {
		return elevator;
	}

	public void setElevator(Elevator elevator) {
		this.elevator = elevator;
	}

	public Door getDoor() {
		return door;
	}

	public void setDoor(Door door) {
		this.door = door;
	}

	public NoPath getNopath() {
		return nopath;
	}

	public void setNopath(NoPath nopath) {
		this.nopath = nopath;
	}

	public Vector<Layer> getLayer() {
		return layer;
	}

	public void setLayer(Vector<Layer> layer) {
		this.layer = layer;
	}
}
