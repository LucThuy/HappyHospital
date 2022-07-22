package scene;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.Timer;

import org.json.simple.parser.ParseException;

import algorithm.AStar;
import algorithm.Cooldown;
import client.AgentC;
import client.PlayerC;
import map.EndPoint;
import map.Map;
import object.ZaWarudo;
import scene.PlayScene.CustomActionListener;

public class Board extends JPanel {

	private Container container;
	
	private Vector<AgentC> agent = new Vector<>();
	private Vector<EndPoint> endPointAgent = new Vector<>();
	
	private Vector<ZaWarudo> zaWarudo = new Vector<>();	
	
	private int endDoorHostID;
	private int endDoorGuestID;
	
	private Map map;
	private PlayerC playerHost;
	private PlayerC playerGuest;
	private Timer update;
	
	private final int FPS = 60;
	private final int SIZE = 28;
	
	/**
	 * Create the panel.
	 * @throws ParseException 
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public Board(Container container) throws FileNotFoundException, IOException, ParseException {
		this.container = container;
		
		playerHost = new PlayerC(-10000, -10000, "m");
		playerGuest = new PlayerC(-5000, -5000, "n");
		
		this.map = new Map();
		
		this.update = new Timer(1000/FPS, new CustomActionListener());
		this.update.start();
	}
	
	public void paint(Graphics g) {
		g.setFont(new Font("Georgia", Font.PLAIN, 14));
		
		if(ZaWarudo.isZaWarudo) {
			for(int i = 0; i < this.map.layer.size(); i++) {
				this.map.layer.get(i).draw(g, true);
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
		try {
			this.playerHost.draw(g, "host");
			this.playerGuest.draw(g, "guest");
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.map.door.drawEnd(g, endDoorHostID, "host");
		this.map.door.drawEnd(g, endDoorGuestID, "guest");
	}
	
	class CustomActionListener implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {	
			repaint();		
		}	
	}
	
	public void addAgent(int x, int y, int id, int end) throws IOException {
		this.endPointAgent.add(new EndPoint(id, end));
		AgentC newAgent = new AgentC(x, y, id);
		this.agent.add(newAgent);
	}
	
	public void agentMove(int x, int y, int i) {
		agent.get(i).position.x = x;
		agent.get(i).position.y = y;
	}
	
	public void agentDone(int i) {
		endPointAgent.remove(i);
		agent.remove(i);
	}
	
	public PlayerC getPlayerHost() {
		return this.playerHost;
	}
	
	public PlayerC getPlayerGuest() {
		return this.playerGuest;
	}
	
	public void setPlayerHost(PlayerC player) {
		this.playerHost = player;
	}
	
	public void setPlayerGuest(PlayerC player) {
		this.playerGuest = player;
	}
	
	public void setEndDoorHostID(int endDoorHostID) {
		this.endDoorHostID = endDoorHostID;
	}
	
	public void setEndDoorGuestID(int endDoorGuestID) {
		this.endDoorGuestID = endDoorGuestID;
	}
	
	public void setScoreHost(String score) {
		this.playerHost.score = score;
		
		this.container.getMutilPlayerScene().lblScoreHost.setText(score);
	}
	
	public void setScoreGuest(String score) {
		this.playerGuest.score = score;
		
		this.container.getMutilPlayerScene().lblScoreGuest.setText(score);
	}
	
	public void setPositionPlayerHost(int x, int y) {
		this.playerHost.position.x = x;
		this.playerHost.position.y = y;
	}
	
	public void setPositionPlayerGuest(int x, int y) {
		this.playerGuest.position.x = x;
		this.playerGuest.position.y = y;
	}
}
