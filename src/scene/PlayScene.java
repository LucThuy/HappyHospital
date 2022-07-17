package scene;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.Timer;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import algorithm.AStar;
import algorithm.CalScore;
import algorithm.Cooldown;
import algorithm.Node;
import algorithm.Position;
import map.EndPoint;
import map.Map;
import minhdeptrai.Cat;
import minhdeptrai.Dog;
import minhdeptrai.Player;
import minhdeptrai.ZaWarudo;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Color;
import javax.swing.BoxLayout;
import javax.swing.JButton;

public class PlayScene extends JPanel {

	public Container container;
	
	public Vector<Dog> dog = new Vector<>();
	private int dogID = 0;
	private Vector<EndPoint> endPointDog = new Vector<>();
	public Cooldown dogCD;
	
	public Vector<Cat> cat = new Vector<>();
	private int catID = 0;
	private Vector<EndPoint> endPointCat = new Vector<>();
	public Cooldown catCD;
	
	public Vector<ZaWarudo> zaWarudo = new Vector<>();	
	public Cooldown zaWarudoCD;
	
	private Rectangle endPointBound;
	private int endDoorID;
	
	public Map map;
	public Player player;
	public Timer update;
	public AStar AStar;
	
	private Node preNode;
	private CalScore calScore;
	
	public final int FPS = 60;
	public final int SIZE = 28;
	
	
	public PlayScene(Container container) throws FileNotFoundException, IOException, ParseException {
		this.container = container;
		this.dogCD = new Cooldown(5000);
		this.catCD = new Cooldown(5000);
		
		this.zaWarudoCD = new Cooldown(20000);
		
		this.map = new Map();
		this.AStar = new AStar(map.path.dataArr, (int) map.path.WIDTH, (int) map.path.HEIGHT);	

		setUp();
		
//		addKeyListener(new CustomKeyListener());
		this.update = new Timer(1000/FPS, new CustomActionListener());
		this.update.start();
	}
	
	public void setUp() throws IOException {
		this.dogID = 0;
		endPointDog.clear();
		dog.clear();
		
		this.catID = 0;
		endPointCat.clear();
		cat.clear();
		
		zaWarudo.clear();
		
		addPlayer();
	}
	
	public void paint(Graphics g) {
		g.setFont(new Font("Georgia", Font.PLAIN, 14));
		
		if(ZaWarudo.isZaWarudo) {
			for(int i = 0; i < this.map.layer.size(); i++) {
				this.map.layer.get(i).draw(g, true);
			}	
			
			if(this.dog != null) {
				for(int i = 0; i < this.dog.size(); i++) {
					this.dog.get(i).draw(g, true);
				}
			}
			
			if(this.cat != null) {
				for(int i = 0; i < this.cat.size(); i++) {
					this.cat.get(i).draw(g, true);
				}
			}			
		}
		else {
			for(int i = 0; i < this.map.layer.size(); i++) {
				this.map.layer.get(i).draw(g);
			}
			
			if(this.dog != null) {
				for(int i = 0; i < this.dog.size(); i++) {
					this.dog.get(i).draw(g);
				}
			}
			this.map.door.drawEnd(g, endPointDog, "dog");
			
			if(this.cat != null) {
				for(int i = 0; i < this.cat.size(); i++) {
					this.cat.get(i).draw(g);
				}
			}			
			this.map.door.drawEnd(g, endPointCat, "cat");
		}
		
		if(this.zaWarudo != null) {
			for(int i = 0; i < this.zaWarudo.size(); i++) {
				this.zaWarudo.get(i).draw(g);
			}
		}
		this.player.draw(g);
		this.map.door.drawEnd(g, endDoorID);
	}
	

	public void addPlayer() throws IOException {
		Rectangle startPos = new Rectangle(this.map.elevator.bound.get(2));
		this.player = new Player(startPos.x, startPos.y, this.map.path.dataArr);
		
		preNode = this.AStar.map[startPos.x / SIZE][startPos.y / SIZE];
		calScore = new CalScore();
		genEndPoint();
	}
	
	public void addDog() throws IOException {
		Random rd = new Random();
		int tmp = rd.nextInt(1000);
		if(tmp < 3) {
			if(!dogCD.isCD()) {
				int index = rd.nextInt(this.map.door.bound.size());
				Rectangle endPos = this.map.door.bound.get(index);
				
				Rectangle startPos = new Rectangle(this.map.elevator.bound.get(0));
				Node start = this.AStar.map[startPos.x / SIZE][startPos.y / SIZE];
				Node end = this.AStar.map[endPos.x / SIZE][endPos.y / SIZE];
				
				this.endPointDog.add(new EndPoint(dogID, index));
				Vector<Node> path = this.AStar.AStarAlgorithm(start, end);
				Dog newDog = new Dog(startPos.x, startPos.y, path, dogID++);
				this.dog.add(newDog);
			}
		}
	}
	
	public void addCat() throws IOException {
		Random rd = new Random();
		int tmp = rd.nextInt(1000);
		if(tmp < 10) {
			if(!catCD.isCD()) {
				int indexStart = rd.nextInt(this.map.door.bound.size());
				Rectangle startPos = this.map.door.bound.get(indexStart);
				int indexEnd = rd.nextInt(this.map.door.bound.size());
				while(indexEnd == indexStart) {
					indexEnd = rd.nextInt(this.map.door.bound.size());
				}
				Rectangle endPos = this.map.door.bound.get(indexEnd);			
				
				Node start = this.AStar.mapND[startPos.x / SIZE][startPos.y / SIZE];
				Node end = this.AStar.mapND[endPos.x / SIZE][endPos.y / SIZE];	
				
				Vector<Node> path = this.AStar.AStarAlgorithmND(start, end);
				if(path != null) {
					this.endPointCat.add(new EndPoint(catID, indexEnd));
					Cat newCat = new Cat(startPos.x, startPos.y, path, catID++);
					this.cat.add(newCat);
				}
			}
		}
	}
	
	public void addZaWarudo() {
		Random rd = new Random();
		int tmp = rd.nextInt(1000);
		if(tmp < 1) {
			if(!this.zaWarudoCD.isCD()) {
				int index = rd.nextInt(this.map.path.bound.size());
				int x = this.map.path.bound.get(index).x;
				int y = this.map.path.bound.get(index).y;
				ZaWarudo newZaWarudo = new ZaWarudo(x, y, this.map.path.SIZE);
				this.zaWarudo.add(newZaWarudo);
			}
		}
	}
	
	public void updateZaWarudo() {
		for(int i = 0; i < this.zaWarudo.size(); i++) {
			if(this.player.isCollision(this.player.bound, this.zaWarudo.get(i).bound)) {
				ZaWarudo.isZaWarudo = true;
				ZaWarudo.zaWarudoCD.setTime();
				this.zaWarudo.remove(i);
			}
		}
		if(ZaWarudo.isZaWarudo && !ZaWarudo.zaWarudoCD.isCD()) {
			ZaWarudo.isZaWarudo = false;
		}
	}
	
	private void genEndPoint() {
		Random rd = new Random();
		endDoorID = rd.nextInt(this.map.door.bound.size());
		endPointBound = this.map.door.bound.get(endDoorID);
		
		Node nextNode = this.AStar.map[endPointBound.x / SIZE][endPointBound.y / SIZE];		
		calScore.calExpectedTime(preNode, nextNode, this.AStar);
		preNode = nextNode;
	}
	
	private void calScore() {	
		this.player.score += calScore.calScore();
		
		if(this.player.score < 0) {
			this.player.score = 0;
		}
		
		this.container.getSinglePlayerScene().lblScore.setText(String.valueOf(this.player.score));
	}
	
	private boolean isEnd() {
		if(endPointBound.contains(this.player.bound)) {		
			return true;
		}
		return false;
	}
	
	public boolean isWin() {
		Rectangle endPos = new Rectangle(this.map.elevator.bound.get(1));
		endPos.add(this.map.elevator.bound.get(3));
		if(endPos.contains(this.player.bound)) {
			return true;
		}
		return false;
	}
	
	
	class CustomActionListener implements ActionListener {
		
		private Vector<Rectangle> playerBlock = new Vector<>();
		
		private Vector<Rectangle> dogBlock = new Vector<>();
		private Vector<Rectangle> dogBound = new Vector<>();
		
		private Vector<Rectangle> catBlock = new Vector<>();
		private Vector<Rectangle> catBound = new Vector<>();
		
		@Override
		public void actionPerformed(ActionEvent e) {	
			
			addZaWarudo();
			
			updateZaWarudo();
			
			if(!ZaWarudo.isZaWarudo) {
				try {
					addDog();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
				dogBound.clear();
				
				dogBlock.clear();
				dogBlock.addAll(map.nopath.bound);
//				dogBlock.addAll(catBound);
				dogBlock.add(player.bound);
				for(int i = 0; i < dog.size(); i++) {
					Vector<Rectangle> tmpBlock = new Vector<>();
					for(int j = 0; j < dog.size(); j++) {
						if(j == i) {
							continue;
						}
						tmpBlock.add(dog.get(j).bound);
					}
					dogBlock.addAll(tmpBlock);
					dog.get(i).move(dogBlock);
					dogBlock.removeAll(tmpBlock);
					
					if(dog.get(i).isDogDone && dog.get(i).task == 1) {
						endPointDog.get(i).doorID = -1;
						dog.get(i).isDogDone = false;
						dog.get(i).task = 0;
						Random rd = new Random();
						int tmp = rd.nextInt(2);
						if(tmp == 0) {
							dog.get(i).path = AStar.AStarAlgorithm(dog.get(i).nextNode, AStar.map[50][13]);
						}
						else {
							dog.get(i).path = AStar.AStarAlgorithm(dog.get(i).nextNode, AStar.map[50][14]);
						}
						
					}
					else if(dog.get(i).isDogDone && dog.get(i).task == 0) {
						endPointDog.remove(i);
						dog.remove(i);
					}
					else {
						dogBound.add(dog.get(i).bound);
					}
				}
				
				try {
					addCat();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
				
				catBound.clear();
				
				catBlock.clear();
				catBlock.addAll(map.nopath.bound);
				catBlock.addAll(dogBound);
				catBlock.add(player.bound);
				for(int i = 0; i < cat.size(); i++) {
//					Vector<Rectangle> tmpBlock = new Vector<>();
//					for(int j = 0; j < cat.size(); j++) {
//						if(j == i) {
//							continue;
//						}
//						tmpBlock.add(cat.get(j).bound);
//					}
//					catBlock.addAll(tmpBlock);
					cat.get(i).move(catBlock);
//					catBlock.removeAll(tmpBlock);
					
					if(cat.get(i).isCatDone) {
						endPointCat.remove(i);
						cat.remove(i);
					}
					else {
						catBound.add(cat.get(i).bound);
						catBlock.add(cat.get(i).bound);
					}
				}
			}
			
			playerBlock.clear();
			playerBlock.addAll(map.nopath.bound);
			playerBlock.addAll(dogBound);
//			playerBlock.addAll(catBound);
			player.move(playerBlock);
			
			repaint();
			
			if(isEnd()) {
				calScore();				
				genEndPoint();
			}
			
			if(isWin()) {
				container.showWinScene();	
			}		
		}	
	}
	
	
	class CustomKeyListener implements KeyListener {
		
		private boolean isIPress = false;
		
		@Override
		public void keyTyped(KeyEvent e) {

		}

		@Override
		public void keyPressed(KeyEvent e) {	
			int key  = e.getKeyCode();
			
			if(key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) {
				player.msE = player.ms;
			}
			if(key == KeyEvent.VK_W || key == KeyEvent.VK_UP) {
				player.msN = player.ms;
			}
			if(key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) {
				player.msW = player.ms;
			}
			if(key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) {
				player.msS = player.ms;
			}
			
			if(key == KeyEvent.VK_I) {
				if(!isIPress) {
					if(ZaWarudo.isZaWarudo) {
						player.blink.isBlink = true;
					}
					else if(!player.blink.blinkCD.isCD()) {
						player.blink.isBlink = true;
					}
					isIPress = true;
				}
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			int key  = e.getKeyCode();
			
			if(key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) {
				player.msE = 0;
			}
			if(key == KeyEvent.VK_W || key == KeyEvent.VK_UP) {
				player.msN = 0;
			}
			if(key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) {
				player.msW = 0;
			}
			if(key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) {
				player.msS = 0;
			}
			
			if(key == KeyEvent.VK_I) {
				isIPress = false;
			}
		}
	}
	
	public void saveData() {
		JSONObject data = new JSONObject();
		
		JSONObject player = new JSONObject();
		player.put("x", this.player.position.x);
		player.put("y", this.player.position.y);
		player.put("score", this.player.score);

		data.put("player", player);
		
		data.put("numDog", this.dog.size());
		JSONArray dog = new JSONArray();
		for(int i = 0; i < this.dog.size(); i++) {
			JSONObject tmp = new JSONObject();
			tmp.put("id", this.dog.get(i).dogID);
			tmp.put("x", this.dog.get(i).position.x);
			tmp.put("y", this.dog.get(i).position.y);
			tmp.put("xEnd", this.dog.get(i).path.get(0).position.x);
			tmp.put("yEnd", this.dog.get(i).path.get(0).position.y);
		}
	}
}
