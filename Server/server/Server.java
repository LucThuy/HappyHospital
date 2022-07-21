package server;

import java.awt.EventQueue;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.json.simple.parser.ParseException;

import java.awt.Color;

import java.awt.BorderLayout;

public class Server extends JFrame {

	private JPanel contentPane;
	public static Server frame;
	
	private ServerSocket baseServer;
	private Map<String, WorkerThread> clients = new HashMap<>();	
	private Map<String, SubServer> servers = new HashMap<>();
	
	private static int port = 0;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new Server();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws IOException 
	 */
	public Server() throws IOException {
		setUI();
		openBaseServer();
	}
	
	
	private void openBaseServer() throws IOException {
		baseServer = new ServerSocket(0112);
		
		RunBaseServer runBaseServer = new RunBaseServer();
		runBaseServer.start();
	}

	private void setUI() {
		setTitle("Server");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 350, 200);
		this.setLocationRelativeTo(null);

		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
	}
	
	public void openNewServer(String serverName) throws IOException {
		port++;
		
		SubServer newServer = new SubServer(serverName, port);		
		newServer.start();
	}
	
	public void closeServer(String uid) throws IOException {
		if(servers.get(uid) != null) {
			servers.get(uid).getServer().close();
			servers.remove(uid);
		}
	}
	
	public void byeClient(String uid) {
		clients.remove(uid);
	}
	
	class SubServer extends Thread {
		
		private String port;
		
		private String serverName;
		private String clientName;
		private ServerSocket server;
		
		private WorkerThread host;
		private WorkerThread guest;
		
		private Caculate caculate;
		
		public SubServer(String serverName, int port) throws IOException {
			this.serverName = serverName;
			this.server = new ServerSocket(port);
			this.port = String.valueOf(port);
		}
		
		@Override
		public void run() {
			try {
				Socket tmp = server.accept();
				host = new WorkerThread(tmp, Server.frame, this);
				servers.put(host.getUid(), this);
				host.start();
				
				Socket tmq = server.accept();
				guest = new WorkerThread(tmq, Server.frame, this);
				guest.start();
				
				host.getGo();
				
				this.caculate = new Caculate(this);
			} catch (IOException | ParseException e1) {
//				e1.printStackTrace();
			}	
		}
		
		public ServerSocket getServer() {
			return this.server;
		}
		
		public String getUid() {
			return this.host.getUid();
		}
		
		public String getServerName() {
			return this.serverName;
		}
		
		public String getClientname() {
			return this.clientName;
		}
		
		public String getPort() {
			return this.port;
		}

		public void sendToHost(String mess) {
			this.host.getPrint().println(mess);
		}	
		
		public void sendToGuest(String mess) {
			this.guest.getPrint().println(mess);
		}

		public void sendToAll(String mess) {
			this.host.getPrint().println(mess);
			this.guest.getPrint().println(mess);
		}
		
		public Caculate getCaculate() {
			return this.caculate;
		}
		
		public WorkerThread getHost() {
			return this.host;
		}
		
		public WorkerThread getGuest() {
			return this.guest;
		}
		
		public void setClientName(String name) {
			this.clientName = name;
		}
	}
	
	class RunBaseServer extends Thread {
		@Override
		public void run() {
			while(true) {
				Socket client;
				try {
					client = baseServer.accept();
					
					WorkerThread handler = new WorkerThread(client, Server.frame, null);
					clients.put(handler.getUid(), handler);
					handler.start();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public Map<String, String> getServerAvailable() {
		Map<String, String> serverAvailable = new HashMap<>();
		
		this.servers.forEach((uid, server) -> {
			serverAvailable.put(server.getServerName(), server.getPort());
		});

		return serverAvailable;
	}
	
	public int getPort() {
		return Server.port;
	}
}