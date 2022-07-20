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
	
	public Vector<Layer> layer = new Vector<>();
	
	public Ground ground;
	public Path path;
	public Room room;
	public Door door;
	public Wall wall;
	public NoPath nopath;
	public Gate gate;
	public Elevator elevator;
	public Bed bed;
	
	public Vector<Tile> tiles = new Vector<>();
	
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
					layer.add(ground);
					break;
				}
				case "path":{
					path = new Path(id, data, name, tiles);
					layer.add(path);
					break;
				}
				case "room":{
					room = new Room(id, data, name, tiles);
					layer.add(room);
					break;
				}
				case "door":{
					door = new Door(id, data, name, tiles);
					layer.add(door);
					break;
				}
				case "wall":{
					wall = new Wall(id, data, name, tiles);
					layer.add(wall);
					break;
				}
				case "nopath":{
					nopath = new NoPath(id, data, name, tiles);
					layer.add(nopath);
					break;
				}
				case "gate":{
					gate = new Gate(id, data, name, tiles);
					layer.add(gate);
					break;
				}
				case "elevator":{
					elevator = new Elevator(id, data, name, tiles);
					layer.add(elevator);
					break;
				}
				case "bed":{
					bed = new Bed(id, data, name, tiles);
					layer.add(bed);
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
}
