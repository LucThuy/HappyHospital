package server;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;
import java.util.Vector;

import javax.swing.Timer;

import org.json.simple.parser.ParseException;

import algorithm.AStar;
import algorithm.CalScore;
import algorithm.Cooldown;
import algorithm.Node;
import map.EndPoint;
import map.Map;
import object.Agent;
import object.Player;
import object.ZaWarudo;
import server.Server.SubServer;

public class Caculate {
	
	private SubServer server;
	
	private Vector<Agent> agent = new Vector<>();
	private int agentID = 0;
	private Vector<EndPoint> endPointAgent = new Vector<>();
	private Cooldown agentCD;
	
	private Vector<ZaWarudo> zaWarudo = new Vector<>();	
	private Cooldown zaWarudoCD;
	
	private Rectangle endPointHostBound;
	private int endDoorHostID;
	
	private Rectangle endPointGuestBound;
	private int endDoorGuestID;
	
	private Map map;
	private Timer update;
	private AStar AStar;
	
	private PlayerS playerHost;
	private PlayerS playerGuest;
	
	private String hostName;
	private String guestName;
	
	private Node preNodeHost;
	private CalScore calScoreHost;
	
	private Node preNodeGuest;
	private CalScore calScoreGuest;
	
	private final int CPS = 60;
	private final int SIZE = 28;
	
	public Caculate(SubServer server) throws FileNotFoundException, IOException, ParseException {
		this.server = server;		
		this.server.getGuest().getClientName();

		this.agentCD = new Cooldown(5000);
		
		this.zaWarudoCD = new Cooldown(20000);
		
		this.map = new Map();
		this.AStar = new AStar(map.path.dataArr, (int) map.path.WIDTH, (int) map.path.HEIGHT);	

		setUp();
		
		addPlayerHost();
		addPlayerGuest();
		
		this.update = new Timer(1000/CPS, new CustomActionListener());
		this.update.start();
	}
	
	public void setUp() throws IOException {		
		this.agentID = 0;
		endPointAgent.clear();
		agent.clear();
		
		zaWarudo.clear();
	}
	
	private void addPlayerHost() {
		Rectangle startPos = new Rectangle(this.map.elevator.bound.get(0));
		this.hostName = this.server.getServerName();
		this.playerHost = new PlayerS(startPos.x, startPos.y, this.map.path.dataArr);	
		this.server.sendToAll("addPlayerHost " + startPos.x + " " + startPos.y + " " + hostName);
		
		preNodeHost = this.AStar.map[startPos.x / SIZE][startPos.y / SIZE];
		calScoreHost = new CalScore();
		
		genEndPointHost();
	}
	
	private void addPlayerGuest() {
		Rectangle startPos = new Rectangle(this.map.elevator.bound.get(2));
		this.guestName = this.server.getClientname();
		this.playerGuest = new PlayerS(startPos.x, startPos.y, this.map.path.dataArr);		
		this.server.sendToAll("addPlayerGuest " + startPos.x + " " + startPos.y + " " + guestName);
		
		preNodeGuest = this.AStar.map[startPos.x / SIZE][startPos.y / SIZE];
		calScoreGuest = new CalScore();
		
		genEndPointGuest();
	}
	
	private void genEndPointHost() {
		Random rd = new Random();
		endDoorHostID = rd.nextInt(this.map.door.bound.size());
		endPointHostBound = this.map.door.bound.get(endDoorHostID);
		
		Node nextNodeHost = this.AStar.map[endPointHostBound.x / SIZE][endPointHostBound.y / SIZE];		
		calScoreHost.calExpectedTime(preNodeHost, nextNodeHost, this.AStar);
		preNodeHost = nextNodeHost;
		
		this.server.sendToAll("genEndPointHost " + endDoorHostID);
	}
	
	private void genEndPointGuest() {
		Random rd = new Random();
		endDoorGuestID = rd.nextInt(this.map.door.bound.size());
		endPointGuestBound = this.map.door.bound.get(endDoorGuestID);
		
		Node nextNodeGuest = this.AStar.map[endPointGuestBound.x / SIZE][endPointGuestBound.y / SIZE];		
		calScoreGuest.calExpectedTime(preNodeGuest, nextNodeGuest, this.AStar);
		preNodeGuest = nextNodeGuest;
		
		this.server.sendToAll("genEndPointGuest " + endDoorGuestID);
	}
	
	private void calScoreHost() {	
		this.playerHost.score += calScoreHost.calScore();
		
		if(this.playerHost.score < 0) {
			this.playerHost.score = 0;
		}
		
		this.server.sendToAll("calScoreHost " + this.playerHost.score);
	}
	
	private void calScoreGuest() {	
		this.playerGuest.score += calScoreGuest.calScore();
		
		if(this.playerGuest.score < 0) {
			this.playerGuest.score = 0;
		}
		
		this.server.sendToAll("calScoreGuest " + this.playerGuest.score);
	}
	
	private void addCat() throws IOException {
		Random rd = new Random();
		int tmp = rd.nextInt(1000);
		if(tmp < 10) {
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
					Agent newAgent = new Agent(startPos.x, startPos.y, path, agentID);
					this.agent.add(newAgent);
					
					this.server.sendToAll("addCat " + startPos.x + " " + startPos.y + " " + agentID + " " + indexEnd);
					agentID++;
				}
			}
		}
	}
	
	private boolean isHostEnd() {
		if(endPointHostBound.contains(this.playerHost.bound)) {
			return true;
		}
		return false;
	}
	
	private boolean isGuestEnd() {
		if(endPointGuestBound.contains(this.playerGuest.bound)) {
			return true;
		}
		return false;
	}
	
	private void catMove(int i) {
		this.server.sendToAll("catMove " + agent.get(i).getPosition().x + " " + agent.get(i).getPosition().y + " " + i);
	}
	
	private void catDone(int i) {
		this.server.sendToAll("catDone " + i);
	}
	
	private void playerHostMove() {
		this.server.sendToAll("playerHostMove " + playerHost.position.x + " " + playerHost.position.y);
	}
	
	private void playerGuestMove() {
		this.server.sendToAll("playerGuestMove " + playerGuest.position.x + " " + playerGuest.position.y);
	}
	
	private boolean isIHostPress = false;
	private boolean isIGuestPress = false;
	
	public void keyPressed(String key, String isHost) {
		if(isHost.equals("1")) {
			if(key.equals("D")) {
				playerHost.msE = playerHost.ms;
			}
			if(key.equals("W")) {
				playerHost.msN = playerHost.ms;
			}
			if(key.equals("A")) {
				playerHost.msW = playerHost.ms;
			}
			if(key.equals("S")) {
				playerHost.msS = playerHost.ms;
			}
			
			if(key.equals("I")) {
				if(!isIHostPress) {
					if(ZaWarudo.isZaWarudo) {
						playerHost.blink.isBlink = true;
					}
					else if(!playerHost.blink.blinkCD.isCD()) {
						playerHost.blink.isBlink = true;
					}
					isIHostPress = true;
				}
			}
		}	
		else if(isHost.equals("0")) {
			if(key.equals("D")) {
				playerGuest.msE = playerGuest.ms;
			}
			if(key.equals("W")) {
				playerGuest.msN = playerGuest.ms;
			}
			if(key.equals("A")) {
				playerGuest.msW = playerGuest.ms;
			}
			if(key.equals("S")) {
				playerGuest.msS = playerGuest.ms;
			}
			
			if(key.equals("I")) {
				if(!isIGuestPress) {
					if(ZaWarudo.isZaWarudo) {
						playerGuest.blink.isBlink = true;
					}
					else if(!playerGuest.blink.blinkCD.isCD()) {
						playerGuest.blink.isBlink = true;
					}
					isIGuestPress = true;
				}
			}
		}	
	}
	
	public void keyReleased(String key, String isHost) {
		if(isHost.equals("1")) {
			if(key.equals("D")) {
				playerHost.msE = 0;
			}
			if(key.equals("W")) {
				playerHost.msN = 0;
			}
			if(key.equals("A")) {
				playerHost.msW = 0;
			}
			if(key.equals("S")) {
				playerHost.msS = 0;
			}
			
			if(key.equals("I")) {
				isIHostPress = false;
			}
		}	
		if(isHost.equals("0")) {
			if(key.equals("D")) {
				playerGuest.msE = 0;
			}
			if(key.equals("W")) {
				playerGuest.msN = 0;
			}
			if(key.equals("A")) {
				playerGuest.msW = 0;
			}
			if(key.equals("S")) {
				playerGuest.msS = 0;
			}
			
			if(key.equals("I")) {
				isIGuestPress = false;
			}
		}	
	}
	

	
	class CustomActionListener implements ActionListener {
		private Vector<Rectangle> playerBlock = new Vector<>();
		
		private Vector<Rectangle> catBlock = new Vector<>();
		private Vector<Rectangle> catBound = new Vector<>();
		
		@Override
		public void actionPerformed(ActionEvent e) {	
			
//			addZaWarudo();
//			
//			updateZaWarudo();
			
			if(!ZaWarudo.isZaWarudo) {				
				try {
					addCat();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
				catBound.clear();
				
				catBlock.clear();
				catBlock.addAll(map.nopath.bound);
				catBlock.add(playerHost.bound);
				catBlock.add(playerGuest.bound);
				for(int i = 0; i < agent.size(); i++) {
//					Vector<Rectangle> tmpBlock = new Vector<>();
//					for(int j = 0; j < cat.size(); j++) {
//						if(j == i) {
//							continue;
//						}
//						tmpBlock.add(cat.get(j).bound);
//					}
//					catBlock.addAll(tmpBlock);
					agent.get(i).move(catBlock);
					catMove(i);
					
//					catBlock.removeAll(tmpBlock);
					
					if(agent.get(i).isAgentDone()) {
						endPointAgent.remove(i);
						agent.remove(i);
						catDone(i);
					}
					else {
						catBound.add(agent.get(i).getBound());
						catBlock.add(agent.get(i).getBound());
					}
				}
			}
			
			playerBlock.clear();
			playerBlock.addAll(map.nopath.bound);
//			playerBlock.addAll(catBound);
			playerBlock.add(playerGuest.bound);
			playerHost.move(playerBlock);
			playerHostMove();
			playerBlock.remove(playerGuest.bound);
			playerBlock.add(playerHost.bound);
			playerGuest.move(playerBlock);
			playerGuestMove();
			
			if(isHostEnd()) {
				calScoreHost();
				genEndPointHost();
			}	
			
			if(isGuestEnd()) {
				calScoreGuest();
				genEndPointGuest();
			}	
			
		}
	}
}