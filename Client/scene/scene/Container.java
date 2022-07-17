package scene;

import java.awt.CardLayout;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import javax.swing.JPanel;

import org.json.simple.parser.ParseException;

import client.ChillThread;
import client.Client;

import java.awt.Color;

public class Container extends JPanel {

//	private PlayScene playScene;
	private WinScene winScene;
	private MenuScene menuScene;
	private ServerSelectScene serverSelectScene;
	private SinglePlayerScene singlePlayerScene;
	private MutilPlayerScene mutilPlayerScene;
	private WaitScene waitScene;
	private PauseScene pauseScene;
	private HowToPlayScene howToPlayScene;
	private CreditScene creditScene;
	
	private Client client;
	
	
	private CardLayout cardLayout;
	/**
	 * Create the panel.
	 * @throws ParseException 
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public Container(Client client) throws FileNotFoundException, IOException, ParseException {
		setBackground(Color.WHITE);
		this.setClient(client);
		
		this.cardLayout = new CardLayout();
		setLayout(this.cardLayout);
		
//		playScene = new PlayScene(this);
//		add(this.playScene, "playScene");
		winScene = new WinScene(this);
		add(this.winScene, "winScene");
		menuScene = new MenuScene(this);
		add(this.menuScene, "menuScene");
		serverSelectScene = new ServerSelectScene(this);
		add(this.serverSelectScene, "serverSelectScene");
		singlePlayerScene = new SinglePlayerScene(this);
		add(this.singlePlayerScene, "singlePlayerScene");
		mutilPlayerScene = new MutilPlayerScene(this);
		add(this.mutilPlayerScene, "mutilPlayerScene");
		waitScene = new WaitScene(this);
		add(this.waitScene, "waitScene");
		pauseScene = new PauseScene(this);
		add(this.pauseScene, "pauseScene");
		howToPlayScene = new HowToPlayScene(this);
		add(this.howToPlayScene, "howToPlayScene");
		creditScene = new CreditScene(this);
		add(this.creditScene, "creditScene");
		
		showMenuScene();
	}
	
	public void showMenuScene() {
		cardLayout.show(this, "menuScene");
		menuScene.setFocusable(true);
		menuScene.requestFocusInWindow();
	}

	public void showServerSelectScene(Map<String, String> serverAvailable) {
		serverSelectScene.setServerAvailabe(serverAvailable);
		cardLayout.show(this, "serverSelectScene");
		serverSelectScene.setFocusable(true);
		serverSelectScene.requestFocusInWindow();
	}
	
	public void showSinglePlayerScene() throws IOException, ParseException {
		singlePlayerScene.setPlayScene(new PlayScene(this));
		cardLayout.show(this, "singlePlayerScene");
		singlePlayerScene.setFocusable(true);
		singlePlayerScene.requestFocusInWindow();
	}
	
	public void showMutilPlayerScene() throws FileNotFoundException, IOException, ParseException {
		this.client.getRageThread().setPlayScene(mutilPlayerScene);
		
		mutilPlayerScene.setBoard(new Board(this));
		cardLayout.show(this, "mutilPlayerScene");
		mutilPlayerScene.setFocusable(true);
		mutilPlayerScene.requestFocusInWindow();
	}
	
	public void showWaitScene() {
		cardLayout.show(this, "waitScene");
		waitScene.setFocusable(true);
		waitScene.requestFocusInWindow();
	}
	
	public void showPauseScene(SinglePlayerScene singlePlayerScene) {
		pauseScene.setSinglePlayerScene(singlePlayerScene);
		cardLayout.show(this, "pauseScene");
		pauseScene.setFocusable(true);
		pauseScene.requestFocusInWindow();
	}
	
	public void showSinglePlayerSceneAgain() {
		cardLayout.show(this, "singlePlayerScene");
		this.singlePlayerScene.setFocusable(true);
		this.singlePlayerScene.requestFocusInWindow();
		this.singlePlayerScene.getPlayScene().update.restart();
	}
	
	public void showHowToPlayScene() {
		cardLayout.show(this, "howToPlayScene");
		howToPlayScene.setFocusable(true);
		howToPlayScene.requestFocusInWindow();
	}
	
	public void showCreditScene() {
		cardLayout.show(this, "creditScene");
		creditScene.setFocusable(true);
		creditScene.requestFocusInWindow();
	}
	
//	public void showPlayScene() throws FileNotFoundException, IOException, ParseException {
//		playScene.setUp();
//		cardLayout.show(this, "playScene");
//		playScene.setFocusable(true);
//		playScene.requestFocusInWindow();
//	}
	
	public void showWinScene() {
		cardLayout.show(this, "winScene");
		winScene.setFocusable(true);
		winScene.requestFocusInWindow();
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}
	
	public SinglePlayerScene getSinglePlayerScene() {
		return this.singlePlayerScene;
	}
	
	public MutilPlayerScene getMutilPlayerScene() {
		return this.mutilPlayerScene;
	}
}