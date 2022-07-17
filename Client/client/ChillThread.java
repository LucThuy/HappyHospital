package client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

import scene.Container;

public class ChillThread extends Thread {
	private Socket socket;
	private Client client;
	
	public ChillThread(Client client) {
		this.client = client;
	}
	
	private InputStream inputStream;
	private Scanner scan;
	
	private OutputStream outputStream;
	private PrintWriter print;
	
	public void run() {
		try {
			socket = new Socket("127.0.0.1", 0112);
			
			inputStream = socket.getInputStream();
		    scan = new Scanner(inputStream);
		    
		    outputStream = socket.getOutputStream();
		    print = new PrintWriter(outputStream, true);  
		} catch (IOException e) {
			e.printStackTrace();
		}		        
	}
	
	public Map<String, String> getServerAvailable() {
		print.println("loadServerAvailable");

		Map<String, String> re = new HashMap<>();
		
		int n = Integer.parseInt(scan.nextLine());
		
		for(int i = 1; i <= n; i++) {
			String strReceive = scan.nextLine();
			String[] strList = strReceive.split("\\s");
			re.put(strList[0], strList[1]);
		}
		
		return re;
	}
	
	public String newRoom() {
		print.println("newRoom " + this.client.getClientName());
		
		return scan.nextLine();
	}
	
	public void byeBye() throws IOException {
		print.println("chillByeBye");

		socket.close();
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}
}
