package scene;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.Timer;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import algorithm.AStar;
import algorithm.CalScore;
import algorithm.Cooldown;
import algorithm.Node;
import algorithm.Position;
import map.EndPoint;
import map.Sound;
import map.Map;
import object.Agent;
import object.Agv;
import object.Player;
import object.ZaWarudo;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Color;
import javax.swing.BoxLayout;
import javax.swing.JButton;

public class PlayScene extends JPanel {

	public Container container;
	
	public Vector<Agv> agv = new Vector<>();
	private int agvID = 0;
	private Vector<EndPoint> endPointAgv = new Vector<>();
	public Cooldown agvCD;
	
	public Vector<Agent> agent = new Vector<>();
	private int agentID = 0;
	private Vector<EndPoint> endPointAgent = new Vector<>();
	public Cooldown agentCD;
	
	public Vector<ZaWarudo> zaWarudo = new Vector<>();	
	public Cooldown zaWarudoCD;
	
	private Rectangle endPointBound;
	private int endDoorID;
	
	public Map map;
	public static Player player;
	public Timer update;
	public AStar AStar;
	public Sound sound = new Sound();
	
	private Node preNode;
	private CalScore calScore;
	
	public final int FPS = 60;
	public final int SIZE = 28;
	private static int numberOfAgents;

	public static float son;
	
	
	public PlayScene(Container container) throws FileNotFoundException, IOException, ParseException {
		this.container = container;
		this.agvCD = new Cooldown(5000);
		this.agentCD = new Cooldown(5000);
		
		this.zaWarudoCD = new Cooldown(20000);
		
		this.map = new Map();
		this.AStar = new AStar(map.path.dataArr, (int) map.path.WIDTH, (int) map.path.HEIGHT);	

		setUp();
		
//		addKeyListener(new CustomKeyListener());
		this.update = new Timer(1000/FPS, new CustomActionListener());
		this.update.start();
	}
	
	public void setUp() throws IOException {
		container.getSinglePlayerScene().minute = 5;
		container.getSinglePlayerScene().second = 0;
		
		this.agvID = 0;
		endPointAgv.clear();
		agv.clear();
		
		this.agentID = 0;
		endPointAgent.clear();
		agent.clear();
		
		zaWarudo.clear();
		
		addPlayer();
	}
	
	public void paint(Graphics g) {
		g.setFont(new Font("Georgia", Font.PLAIN, 14));
		
		if(ZaWarudo.isZaWarudo) {
			for(int i = 0; i < this.map.layer.size(); i++) {
				this.map.layer.get(i).draw(g, true);
			}	
			
			if(this.agv != null) {
				for(int i = 0; i < this.agv.size(); i++) {
					this.agv.get(i).draw(g, true);
				}
			}
			
			if(this.agent != null) {
				for(int i = 0; i < this.agent.size(); i++) {
					this.agent.get(i).draw(g, true);
				}
			}			
		}
		else {
			for(int i = 0; i < this.map.layer.size(); i++) {
				this.map.layer.get(i).draw(g);
			}
			
			if(this.agv != null) {
				for(int i = 0; i < this.agv.size(); i++) {
					this.agv.get(i).draw(g);
				}
			}
			this.map.door.drawEnd(g, endPointAgv, "dog");
			
			if(this.agent != null) {
				for(int i = 0; i < this.agent.size(); i++) {
					this.agent.get(i).draw(g);
				}
			}			
			this.map.door.drawEnd(g, endPointAgent, "cat");
		}
		
		if(this.zaWarudo != null) {
			for(int i = 0; i < this.zaWarudo.size(); i++) {
				this.zaWarudo.get(i).draw(g);
			}
		}
		this.player.draw(g);
		this.map.door.drawEnd(g, endDoorID);
	}
	
	public int getNumberOfAgents() {
		return this.numberOfAgents;
	}
	public static void setNumberOfAgents(int count) {
		PlayScene.numberOfAgents = count;
	}
	
	

	public void addPlayer() throws IOException {
		Rectangle startPos = new Rectangle(this.map.elevator.bound.get(2));
		this.player = new Player(startPos.x, startPos.y, this.map.path.dataArr);
		
		preNode = this.AStar.map[startPos.x / SIZE][startPos.y / SIZE];
		calScore = new CalScore();
		genEndPoint();
	}
	
	public void addAgv() throws IOException {
		Random rd = new Random();
		int tmp = rd.nextInt(1000);
		if(tmp < 3) {
			if(!agvCD.isCD()) {
				int index = rd.nextInt(this.map.door.bound.size());
				Rectangle endPos = this.map.door.bound.get(index);
				
				Rectangle startPos = new Rectangle(this.map.elevator.bound.get(0));
				Node start = this.AStar.map[startPos.x / SIZE][startPos.y / SIZE];
				Node end = this.AStar.map[endPos.x / SIZE][endPos.y / SIZE];
				
				this.endPointAgv.add(new EndPoint(agvID, index));
				Vector<Node> path = this.AStar.AStarAlgorithm(start, end);
				Agv newAgv = new Agv(startPos.x, startPos.y, path, agvID++);
				this.agv.add(newAgv);
			}
		}
	}
	
	public void addAgent() throws IOException {
		int num = getNumberOfAgents();
		Random rd = new Random();
		if(this.agent.size() < num) {
			if(!agentCD.isCD()) {
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
					this.endPointAgent.add(new EndPoint(agentID, indexEnd));
					Agent newAgent = new Agent(startPos.x, startPos.y, path, agentID++);
					this.agent.add(newAgent);
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
				this.sound.turnOnMusic(10);
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
		
		this.player.score = (float) (Math.round(this.player.score * 100) /  100.0);
		this.container.getSinglePlayerScene().lblScore.setText(String.valueOf(this.player.score));
	}
	private boolean isEnd() {
		if(endPointBound.contains(this.player.bound)) {	
			this.sound.turnOnMusic(7);
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
		
		private Vector<Rectangle> agvBlock = new Vector<>();
		private Vector<Rectangle> agvBound = new Vector<>();
		
		private Vector<Rectangle> agentBlock = new Vector<>();
		private Vector<Rectangle> agentBound = new Vector<>();
		
		@Override
		public void actionPerformed(ActionEvent e) {	
			
			addZaWarudo();
			
			updateZaWarudo();
			
			if(!ZaWarudo.isZaWarudo) {
				try {
					addAgv();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
				agvBound.clear();
				
				agvBlock.clear();
				agvBlock.addAll(map.nopath.bound);
//				dogBlock.addAll(catBound);
				agvBlock.add(player.bound);
				for(int i = 0; i < agv.size(); i++) {
//					Vector<Rectangle> tmpBlock = new Vector<>();
//					for(int j = 0; j < agv.size(); j++) {
//						if(j == i) {
//							continue;
//						}
//						tmpBlock.add(agv.get(j).bound);
//					}
//					agvBlock.addAll(tmpBlock);
					agv.get(i).move(agvBlock);
//					agvBlock.removeAll(tmpBlock);
					
					if(agv.get(i).isAgvDone && agv.get(i).task == 1) {
						endPointAgv.get(i).doorID = -1;
						agv.get(i).isAgvDone = false;
						agv.get(i).task = 0;
						Random rd = new Random();
						int tmp = rd.nextInt(2);
						if(tmp == 0) {
							agv.get(i).path = AStar.AStarAlgorithm(agv.get(i).nextNode, AStar.map[50][13]);
						}
						else {
							agv.get(i).path = AStar.AStarAlgorithm(agv.get(i).nextNode, AStar.map[50][14]);
						}		
					}
					else if(agv.get(i).isAgvDone && agv.get(i).task == 0) {
						endPointAgv.remove(i);
						agv.remove(i);
					}
					else {
						agvBound.add(agv.get(i).bound);
						agvBlock.add(agv.get(i).bound);
					}
				}
				
				try {
					addAgent();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
				
				agentBound.clear();
				
				agentBlock.clear();
				agentBlock.addAll(map.nopath.bound);
				agentBlock.addAll(agvBound);
				agentBlock.add(player.bound);
				for(int i = 0; i < agent.size(); i++) {
//					Vector<Rectangle> tmpBlock = new Vector<>();
//					for(int j = 0; j < cat.size(); j++) {
//						if(j == i) {
//							continue;
//						}
//						tmpBlock.add(cat.get(j).bound);
//					}
//					catBlock.addAll(tmpBlock);
					agent.get(i).move(agentBlock);
//					catBlock.removeAll(tmpBlock);
					
					if(agent.get(i).isAgentDone) {
						endPointAgent.remove(i);
						agent.remove(i);
					}
					else {
						agentBound.add(agent.get(i).bound);
						agentBlock.add(agent.get(i).bound);
					}
				}
			}
			
			playerBlock.clear();
			playerBlock.addAll(map.nopath.bound);
			playerBlock.addAll(agvBound);
//			playerBlock.addAll(catBound);
			player.move(playerBlock);
			
			repaint();
			
			if(isEnd()) {
				calScore();				
				genEndPoint();
			}
			
			if(isWin()) {
				container.showWinScene();
				container.getSinglePlayerScene().isMove = false;
			}		
		}	
	}
	

	public void saveData(String link) throws IOException {
		JSONObject data = new JSONObject();
		
		JSONObject player = new JSONObject();
		player.put("x", this.player.position.x);
		player.put("y", this.player.position.y);
		player.put("score", this.player.score);

		data.put("player", player);
		
		data.put("numAgv", this.agv.size());
		data.put("agvID", agvID);
		JSONArray agv = new JSONArray();
		for(int i = 0; i < this.agv.size(); i++) {
			JSONObject tmp = new JSONObject();
			tmp.put("id", this.agv.get(i).agvID);
			tmp.put("x", this.agv.get(i).position.x);
			tmp.put("y", this.agv.get(i).position.y);
			tmp.put("xEnd", this.agv.get(i).path.get(0).position.x);
			tmp.put("yEnd", this.agv.get(i).path.get(0).position.y);
			tmp.put("task", this.agv.get(i).task);
			
			agv.add(tmp);
		}		
		data.put("agv", agv);
		
		data.put("numEndPointAgv", this.endPointAgv.size());
		JSONArray endPointAgv = new JSONArray();
		for(int i = 0; i < this.endPointAgv.size(); i++) {
			JSONObject tmp = new JSONObject();
			tmp.put("id", this.endPointAgv.get(i).ID);
			tmp.put("doorID", this.endPointAgv.get(i).doorID);
			
			endPointAgv.add(tmp);
		}
		data.put("endPointAgv", endPointAgv);
		
		data.put("numAgent", this.agent.size());
		data.put("agentID", agentID);
		JSONArray agent = new JSONArray();
		for(int i = 0; i < this.agent.size(); i++) {
			JSONObject tmp = new JSONObject();
			tmp.put("id", this.agent.get(i).agentID);
			tmp.put("x", this.agent.get(i).position.x);
			tmp.put("y", this.agent.get(i).position.y);
			tmp.put("xEnd", this.agent.get(i).path.get(0).position.x);
			tmp.put("yEnd", this.agent.get(i).path.get(0).position.y);
			
			agent.add(tmp);
		}		
		data.put("agent", agent);
		
		data.put("numEndPointAgent", this.endPointAgent.size());
		JSONArray endPointAgent = new JSONArray();
		for(int i = 0; i < this.endPointAgent.size(); i++) {
			JSONObject tmp = new JSONObject();
			tmp.put("id", this.endPointAgent.get(i).ID);
			tmp.put("doorID", this.endPointAgent.get(i).doorID);
			
			endPointAgent.add(tmp);
		}
		data.put("endPointAgent", endPointAgent);
		
		FileWriter file = new FileWriter(link, false);
		file.write(data.toJSONString());
		file.flush();
		
		System.out.println("done");
	}
	
	public void loadData(String link) throws FileNotFoundException, IOException, ParseException {
		Object obj = new JSONParser().parse(new FileReader(link));
		JSONObject data = (JSONObject)obj;
		
		JSONObject player = (JSONObject) data.get("player");	
		long x = (long) player.get("x");
		long y = (long) player.get("y");
		double score = (double)player.get("score");

		container.getSinglePlayerScene().lblScore.setText(String.valueOf(score));
		this.player = new Player((int)x, (int)y, map.path.dataArr);
		this.player.score = (float)score;
		
		preNode = this.AStar.map[(int)x / SIZE][(int)y / SIZE];
		calScore = new CalScore();
		genEndPoint();
		
		long nAgv = (long)data.get("numAgv");
		agvID = (int)((long)data.get("agvID"));
		if(nAgv != 0) {
			JSONArray agvList = (JSONArray)data.get("agv");
			for(int i = 0; i < (int)nAgv; i++) {
				JSONObject tmp = (JSONObject)agvList.get(i);
				long id = (long)tmp.get("id");
				long xAgv = (long)tmp.get("x");
				long yAgv = (long)tmp.get("y");
				long xEnd = (long)tmp.get("xEnd");
				long yEnd = (long)tmp.get("yEnd");
				long task = (long)tmp.get("task");
				
				Node start = this.AStar.map[(int)xAgv / SIZE][(int)yAgv / SIZE];
				Node end = this.AStar.map[(int)xEnd][(int)yEnd];
				
				Vector<Node> path = this.AStar.AStarAlgorithm(start, end);
				
				Agv tmpAgv = new Agv((int)xAgv / SIZE * SIZE, (int)yAgv / SIZE * SIZE, path, (int)id);

				tmpAgv.task = (int)task;
				agv.add(tmpAgv);
			}
			
			long nEndAgv = (long)data.get("numEndPointAgv");
			JSONArray endPointAgvList = (JSONArray)data.get("endPointAgv");
			for(int i = 0; i < (int)nEndAgv; i++) {
				JSONObject tmp = (JSONObject)endPointAgvList.get(i);
				long id = (long)tmp.get("id");
				long doorID = (long)tmp.get("doorID");
				
				endPointAgv.add(new EndPoint((int)id, (int)doorID));
			}
		}
		
		
		long nAgent = (long)data.get("numAgent");
		agentID = (int)((long)data.get("agentID"));
		if(nAgent != 0) {
			JSONArray agentList = (JSONArray)data.get("agent");
			for(int i = 0; i < (int)nAgent; i++) {
				JSONObject tmp = (JSONObject)agentList.get(i);
				long id = (long)tmp.get("id");
				long xAgent = (long)tmp.get("x");
				long yAgent = (long)tmp.get("y");
				long xEnd = (long)tmp.get("xEnd");
				long yEnd = (long)tmp.get("yEnd");
				
				Node start = this.AStar.mapND[(int)xAgent / SIZE][(int)yAgent / SIZE];
				Node end = this.AStar.mapND[(int)xEnd][(int)yEnd];
				
				Vector<Node> path = this.AStar.AStarAlgorithmND(start, end);

				agent.add(new Agent((int)xAgent / SIZE * SIZE, (int)yAgent / SIZE * SIZE, path, (int)id));

			}
			
			long nEndAgent = (long)data.get("numEndPointAgent");
			JSONArray endPointAgentList = (JSONArray)data.get("endPointAgent");
			for(int i = 0; i < (int)nEndAgent; i++) {
				JSONObject tmp = (JSONObject)endPointAgentList.get(i);
				long id = (long)tmp.get("id");
				long doorID = (long)tmp.get("doorID");
				
				endPointAgent.add(new EndPoint((int)id, (int)doorID));
			}
		}	
	}
}