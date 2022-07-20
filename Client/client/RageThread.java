package client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import org.json.simple.parser.ParseException;

import scene.MutilPlayerScene;

public class RageThread extends Thread {
	private Socket socket;
	private Client client;
	private int port;
	
	private MutilPlayerScene playScene;
	
	private boolean run = true;
	
	public RageThread(Client client, int port) {
		this.client = client;
		this.port = port;
	}
	
	private InputStream inputStream;
	private Scanner scan;
	
	private OutputStream outputStream;
	private PrintWriter print;
	
	public void run() {
		try {
			socket = new Socket("127.0.0.1", port);
			
			inputStream = socket.getInputStream();
		    scan = new Scanner(inputStream);
		    
		    outputStream = socket.getOutputStream();
		    print = new PrintWriter(outputStream, true);
		    
		    while(run) {	    	
		    	if(scan.hasNextLine()) {
		    		String strReceive = scan.nextLine();

//			        System.out.println("Receive from client: " + strReceive);
			        if(strReceive != null) {
				        parseCommand(strReceive);
			        }
		    	}
		    }
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void parseCommand(String command) throws IOException {
		String[] cmdList = command.split("\\s");
		if(cmdList[0].equals("getGo")) {
			getGo();
		}
		else if(cmdList[0].equals("getClientName")) {
			getClientName();
		}
		else if(cmdList[0].equals("addPlayerHost")) {
			PlayerC player = new PlayerC(Integer.parseInt(cmdList[1]), Integer.parseInt(cmdList[2]), cmdList[3]);
			addPlayerHost(player);
		}
		else if(cmdList[0].equals("genEndPointHost")) {
			genEndPointHost(Integer.parseInt(cmdList[1]));
		}
		else if(cmdList[0].equals("addPlayerGuest")) {
			PlayerC player = new PlayerC(Integer.parseInt(cmdList[1]), Integer.parseInt(cmdList[2]), cmdList[3]);
			addPlayerGuest(player);
		}
		else if(cmdList[0].equals("genEndPointGuest")) {
			genEndPointGuest(Integer.parseInt(cmdList[1]));
		}
		else if(cmdList[0].equals("playerHostMove")) {
			playerHostMove(Integer.parseInt(cmdList[1]), Integer.parseInt(cmdList[2]));
		}
		else if(cmdList[0].equals("playerGuestMove")) {
			playerGuestMove(Integer.parseInt(cmdList[1]), Integer.parseInt(cmdList[2]));
		}
		else if(cmdList[0].equals("addAgent")) {
			addAgent(Integer.parseInt(cmdList[1]), Integer.parseInt(cmdList[2]), Integer.parseInt(cmdList[3]), Integer.parseInt(cmdList[4]));
		}
		else if(cmdList[0].equals("agentMove")) {
			agentMove(Integer.parseInt(cmdList[1]), Integer.parseInt(cmdList[2]), Integer.parseInt(cmdList[3]));
		}
		else if(cmdList[0].equals("agentDone")) {
			agentDone(Integer.parseInt(cmdList[1]));
		}
		else if(cmdList[0].equals("calScoreHost")) {
			calScoreHost(cmdList[1]);
		}
		else if(cmdList[0].equals("calScoreGuest")) {
			calScoreGuest(cmdList[1]);
		}
	}
	
	private void getGo() {
		try {
			this.client.aloContainer().showMutilPlayerScene();
			this.playScene = this.client.aloContainer().getMutilPlayerScene();
			this.playScene.setHost();
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
	}
	
	private void getClientName() {
		print.println("clientName " + this.client.getClientName());
	}
	
	private void addPlayerHost(PlayerC player) {
		playScene.getBoard().setPlayerHost(player);
	}
	
	private void addPlayerGuest(PlayerC player) {
		playScene.getBoard().setPlayerGuest(player);
	}
	
	private void genEndPointHost(int endDoorHostID) {
		playScene.getBoard().setEndDoorHostID(endDoorHostID);
	}
	
	private void genEndPointGuest(int endDoorGuestID) {
		playScene.getBoard().setEndDoorGuestID(endDoorGuestID);
	}
	
	private void playerHostMove(int x, int y) {
		playScene.getBoard().setPositionPlayerHost(x, y);
	}
	
	private void playerGuestMove(int x, int y) {
		playScene.getBoard().setPositionPlayerGuest(x, y);
	}
	
	private void addAgent(int x, int y, int id, int end) throws IOException {
		playScene.getBoard().addAgent(x, y, id, end);
	}
	
	private void agentMove(int x, int y, int i) {
		playScene.getBoard().agentMove(x, y, i);
	}
	
	private void agentDone(int i) {
		playScene.getBoard().agentDone(i);
	}
	
	private void calScoreHost(String score) {
		playScene.getBoard().setScoreHost(score);
	}
	
	private void calScoreGuest(String score) {
		playScene.getBoard().setScoreGuest(score);
	}
	
	public void keyPressed(String key, int isHost) {
		print.println("keyPressed " + key + " " + isHost);
	}
	
	public void keyReleased(String key, int isHost) {
		print.println("keyReleased " + key + " " + isHost);
	}
	
	public void byeBye() throws IOException {
		print.println("rageByeBye");
		
		socket.close();
		this.run = false;
	}
	
	public void setPlayScene(MutilPlayerScene playScene) {
		this.playScene = playScene;
	}
}
