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
	
	public PlayScene(Container container) throws FileNotFoundException, IOException, ParseException {
		this.container = container;
		this.agvCD = new Cooldown(5000);
		this.agentCD = new Cooldown(5000);
		
		this.zaWarudoCD = new Cooldown(20000);
		
		this.map = new Map();
		this.AStar = new AStar(map.getPath().getDataArr(), (int) map.getPath().WIDTH, (int) map.getPath().HEIGHT);	

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
			for(int i = 0; i < this.map.getLayer().size(); i++) {
				this.map.getLayer().get(i).draw(g, true);
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
			for(int i = 0; i < this.map.getLayer().size(); i++) {
				this.map.getLayer().get(i).draw(g);
			}
			
			if(this.agv != null) {
				for(int i = 0; i < this.agv.size(); i++) {
					this.agv.get(i).draw(g);
				}
			}
			this.map.getDoor().drawEnd(g, endPointAgv, "agv");
			
			if(this.agent != null) {
				for(int i = 0; i < this.agent.size(); i++) {
					this.agent.get(i).draw(g);
				}
			}			
			this.map.getDoor().drawEnd(g, endPointAgent, "agent");
		}
		
		if(this.zaWarudo != null) {
			for(int i = 0; i < this.zaWarudo.size(); i++) {
				this.zaWarudo.get(i).draw(g);
			}
		}
		this.player.draw(g);
		this.map.getDoor().drawEnd(g, endDoorID);
	}
	
	public int getNumberOfAgents() {
		return this.numberOfAgents;
	}
	public static void setNumberOfAgents(int count) {
		PlayScene.numberOfAgents = count;
	}
	
	

	public void addPlayer() throws IOException {
		Rectangle startPos = new Rectangle(this.map.getElevator().getBound().get(2));
		this.player = new Player(startPos.x, startPos.y, this.map.getPath().getDataArr());
		
		preNode = this.AStar.map[startPos.x / SIZE][startPos.y / SIZE];
		calScore = new CalScore();
		genEndPoint();
	}
	
	public void addAgv() throws IOException {
		Random rd = new Random();
		int tmp = rd.nextInt(1000);
		if(tmp < 3) {
			if(!agvCD.isCD()) {
				int index = rd.nextInt(this.map.getDoor().getBound().size());
				Rectangle endPos = this.map.getDoor().getBound().get(index);
				
				Rectangle startPos = new Rectangle(this.map.getElevator().getBound().get(0));
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
				int indexStart = rd.nextInt(this.map.getDoor().getBound().size());
				Rectangle startPos = this.map.getDoor().getBound().get(indexStart);
				int indexEnd = rd.nextInt(this.map.getDoor().getBound().size());
				while(indexEnd == indexStart) {
					indexEnd = rd.nextInt(this.map.getDoor().getBound().size());
				}
				Rectangle endPos = this.map.getDoor().getBound().get(indexEnd);			
				
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
				int index = rd.nextInt(this.map.getPath().getBound().size());
				int x = this.map.getPath().getBound().get(index).x;
				int y = this.map.getPath().getBound().get(index).y;
				ZaWarudo newZaWarudo = new ZaWarudo(x, y, this.map.getPath().SIZE);
				this.zaWarudo.add(newZaWarudo);
			}
		}
	}
	
	public void updateZaWarudo() {
		for(int i = 0; i < this.zaWarudo.size(); i++) {
			if(this.player.isCollision(this.player.getBound(), this.zaWarudo.get(i).getBound())) {
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
		endDoorID = rd.nextInt(this.map.getDoor().getBound().size());
		endPointBound = this.map.getDoor().getBound().get(endDoorID);
		
		Node nextNode = this.AStar.map[endPointBound.x / SIZE][endPointBound.y / SIZE];		
		calScore.calExpectedTime(preNode, nextNode, this.AStar);
		preNode = nextNode;
	}
	
	private void calScore() throws IOException {	
		this.player.setScore(this.player.getScore() + calScore.calScore());

		
		if(this.player.getScore() < 0) {
			this.player.setScore(0);
		}

		this.player.setScore((float) (Math.round(this.player.getScore() * 100) /  100.0)); 
		if(this.player.getScore() > 20 && this.player.getScore() < 40) {
			player.LevelUp(2);
		} else if(this.player.getScore() > 40) {
			player.LevelUp(3);
		}
		
		this.container.getSinglePlayerScene().lblScore.setText(String.valueOf(this.player.getScore()));

		
	}
	private boolean isEnd() {
		if(endPointBound.contains(this.player.getBound())) {	
			this.sound.turnOnMusic(7);
			return true;
		}
		return false;
	}
	
	public boolean isWin() {
		Rectangle endPos = new Rectangle(this.map.getElevator().getBound().get(1));
		endPos.add(this.map.getElevator().getBound().get(3));
		if(endPos.contains(this.player.getBound())) {
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
				agvBlock.addAll(map.getNopath().getBound());
//				dogBlock.addAll(catBound);
				agvBlock.add(player.getBound());
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
					
					if(agv.get(i).isAgvDone() && agv.get(i).getTask() == 1) {
						endPointAgv.get(i).setDoorID(-1);
						agv.get(i).setAgvDone(false);
						agv.get(i).setTask(0);
						Random rd = new Random();
						int tmp = rd.nextInt(2);
						if(tmp == 0) {
							agv.get(i).setPath(AStar.AStarAlgorithm(agv.get(i).getNextNode(), AStar.map[50][13]));
						}
						else {
							agv.get(i).setPath(AStar.AStarAlgorithm(agv.get(i).getNextNode(), AStar.map[50][14]));
						}		
					}
					else if(agv.get(i).isAgvDone() && agv.get(i).getTask() == 0) {
						endPointAgv.remove(i);
						agv.remove(i);
					}
					else {
						agvBound.add(agv.get(i).getBound());
						agvBlock.add(agv.get(i).getBound());
					}
				}
				
				try {
					addAgent();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
				
				agentBound.clear();
				
				agentBlock.clear();
				agentBlock.addAll(map.getNopath().getBound());
				agentBlock.addAll(agvBound);
				agentBlock.add(player.getBound());
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
					
					if(agent.get(i).isAgentDone()) {
						endPointAgent.remove(i);
						agent.remove(i);
					}
					else {
						agentBound.add(agent.get(i).getBound());
						agentBlock.add(agent.get(i).getBound());
					}
				}
			}
			
			playerBlock.clear();
			playerBlock.addAll(map.getNopath().getBound());
			playerBlock.addAll(agvBound);
//			playerBlock.addAll(catBound);
			player.move(playerBlock);
			
			repaint();
			
			if(isEnd()) {
				try {
					calScore();
				} catch (IOException e1) {
					e1.printStackTrace();
				}				
				genEndPoint();
			}
			
			if(isWin()) {
				container.getSinglePlayerScene().isMove = false;
				float score = player.getScore();
				try {
					setUp();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				update.stop();
				container.showWinScene(score);
			}		
		}	
	}
	

	public void saveData(String link) throws IOException {
		JSONObject data = new JSONObject();
		
		JSONObject player = new JSONObject();
		player.put("x", this.player.getPosition().x);
		player.put("y", this.player.getPosition().y);
		player.put("score", this.player.getScore());

		data.put("player", player);
		
		data.put("numAgv", this.agv.size());
		data.put("agvID", agvID);
		JSONArray agv = new JSONArray();
		for(int i = 0; i < this.agv.size(); i++) {
			JSONObject tmp = new JSONObject();
			tmp.put("id", this.agv.get(i).getAgvID());
			tmp.put("x", this.agv.get(i).getPosition().x);
			tmp.put("y", this.agv.get(i).getPosition().y);
			tmp.put("xEnd", this.agv.get(i).getPath().get(0).position.x);
			tmp.put("yEnd", this.agv.get(i).getPath().get(0).position.y);
			tmp.put("task", this.agv.get(i).getTask());
			
			agv.add(tmp);
		}		
		data.put("agv", agv);
		
		data.put("numEndPointAgv", this.endPointAgv.size());
		JSONArray endPointAgv = new JSONArray();
		for(int i = 0; i < this.endPointAgv.size(); i++) {
			JSONObject tmp = new JSONObject();
			tmp.put("id", this.endPointAgv.get(i).getID());
			tmp.put("doorID", this.endPointAgv.get(i).getDoorID());
			
			endPointAgv.add(tmp);
		}
		data.put("endPointAgv", endPointAgv);
		
		data.put("numAgent", this.agent.size());
		data.put("agentID", agentID);
		JSONArray agent = new JSONArray();
		for(int i = 0; i < this.agent.size(); i++) {
			JSONObject tmp = new JSONObject();
			tmp.put("id", this.agent.get(i).getAgentID());
			tmp.put("x", this.agent.get(i).getPosition().x);
			tmp.put("y", this.agent.get(i).getPosition().y);
			tmp.put("xEnd", this.agent.get(i).getPath().get(0).position.x);
			tmp.put("yEnd", this.agent.get(i).getPath().get(0).position.y);
			
			agent.add(tmp);
		}		
		data.put("agent", agent);
		
		data.put("numEndPointAgent", this.endPointAgent.size());
		JSONArray endPointAgent = new JSONArray();
		for(int i = 0; i < this.endPointAgent.size(); i++) {
			JSONObject tmp = new JSONObject();
			tmp.put("id", this.endPointAgent.get(i).getID());
			tmp.put("doorID", this.endPointAgent.get(i).getDoorID());
			
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
		this.player = new Player((int)x, (int)y, map.getPath().getDataArr());
		this.player.setScore((float)score);
		
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

				tmpAgv.setTask((int)task);
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