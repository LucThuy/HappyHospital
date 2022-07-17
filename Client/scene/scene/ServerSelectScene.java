package scene;

import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.json.simple.parser.ParseException;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;

import java.awt.BorderLayout;
import javax.swing.JList;
import java.awt.FlowLayout;
import javax.swing.JButton;
import java.awt.CardLayout;
import java.awt.Font;
import javax.swing.ListSelectionModel;

public class ServerSelectScene extends JPanel {

	private Container container;
	
	private JList<ServerAvailable> listServer;
	private DefaultListModel<ServerAvailable> model;
	
	private Map<String, String> serverAvailable = new HashMap<>();
	
	/**
	 * Create the panel.
	 */	
	public ServerSelectScene(Container container) {
		setUI();
		this.container = container;
	}
	
	private void setUI() {
		setBackground(Color.WHITE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {0, 0, 0};
		gridBagLayout.rowHeights = new int[] {0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 3.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JPanel pnlButton = new JPanel();
		pnlButton.setBackground(Color.LIGHT_GRAY);
		GridBagConstraints gbc_pnlButton = new GridBagConstraints();
		gbc_pnlButton.fill = GridBagConstraints.BOTH;
		gbc_pnlButton.insets = new Insets(0, 0, 0, 5);
		gbc_pnlButton.gridx = 0;
		gbc_pnlButton.gridy = 0;
		add(pnlButton, gbc_pnlButton);
		GridBagLayout gbl_pnlButton = new GridBagLayout();
		gbl_pnlButton.columnWidths = new int[]{0};
		gbl_pnlButton.rowHeights = new int[]{25, 25, 25, 25, 25};
		gbl_pnlButton.columnWeights = new double[]{4.9E-324};
		gbl_pnlButton.rowWeights = new double[]{1.0, 4.0, 2.0, 0.0, 2.0};
		pnlButton.setLayout(gbl_pnlButton);
		
		JButton btnBack = new JButton("Back");
		btnBack.setForeground(Color.WHITE);
		btnBack.setBackground(Color.GRAY);
		btnBack.setAlignmentX(Component.CENTER_ALIGNMENT);		
		GridBagConstraints gbc_btnBack = new GridBagConstraints();
		gbc_btnBack.fill = GridBagConstraints.BOTH;
		gbc_btnBack.insets = new Insets(0, 0, 5, 0);
		gbc_btnBack.gridx = 0;
		gbc_btnBack.gridy = 0;
		pnlButton.add(btnBack, gbc_btnBack);
		btnBack.addActionListener(new BtnBack());
		
		JButton btnNewRoom = new JButton("New Room");
		btnNewRoom.setForeground(Color.WHITE);
		btnNewRoom.setBackground(Color.GRAY);
		btnNewRoom.setAlignmentX(Component.CENTER_ALIGNMENT);
		GridBagConstraints gbc_btnNewRoom = new GridBagConstraints();
		gbc_btnNewRoom.fill = GridBagConstraints.BOTH;
		gbc_btnNewRoom.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewRoom.gridx = 0;
		gbc_btnNewRoom.gridy = 2;
		pnlButton.add(btnNewRoom, gbc_btnNewRoom);		
		btnNewRoom.addActionListener(new BtnNewRoom());
		
		JButton btnGetGo = new JButton("GetGo");
		btnGetGo.setForeground(Color.WHITE);
		btnGetGo.setBackground(Color.GRAY);
		btnGetGo.setAlignmentX(Component.CENTER_ALIGNMENT);
		GridBagConstraints gbc_btnGetGo = new GridBagConstraints();
		gbc_btnGetGo.fill = GridBagConstraints.BOTH;
		gbc_btnGetGo.gridx = 0;
		gbc_btnGetGo.gridy = 4;
		pnlButton.add(btnGetGo, gbc_btnGetGo);		
		btnGetGo.addActionListener(new BtnGetGo());
		
		JPanel pnlServer = new JPanel();
		pnlServer.setBackground(Color.LIGHT_GRAY);
		GridBagConstraints gbc_pnlServer = new GridBagConstraints();
		gbc_pnlServer.fill = GridBagConstraints.BOTH;
		gbc_pnlServer.gridx = 1;
		gbc_pnlServer.gridy = 0;
		add(pnlServer, gbc_pnlServer);
		pnlServer.setLayout(new CardLayout(0, 0));
		
		listServer = new JList<>();
		listServer.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listServer.setFont(new Font("Tahoma", Font.PLAIN, 20));
		pnlServer.add(listServer, "listServer");
		model = new DefaultListModel<>();
		listServer.setModel(model);			
	}
	
	public void setServerAvailabe(Map<String, String> serverAvailable) {
		this.serverAvailable = serverAvailable;
		model.clear();
		serverAvailable.forEach((name, port) -> {
			model.addElement(new ServerAvailable(name, port));
		});
	}

	class ServerAvailable {
		private String name;
		private String port;
		
		public ServerAvailable(String name, String port) {
			this.setName(name);
			this.setPort(port);
		}
		
		@Override
		public String toString() {
			return getName();
		}

		public String getPort() {
			return port;
		}

		public void setPort(String port) {
			this.port = port;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}
	
	class BtnBack implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			container.showMenuScene();	
		}		
	}
	
	class BtnNewRoom implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String tmp = container.getClient().getChillThread().newRoom();
			container.getClient().createRageThread(Integer.parseInt(tmp));
			
			container.showWaitScene();
		}
	}
	
	class BtnGetGo implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			ServerAvailable serverSelected = model.elementAt(listServer.getSelectedIndex());
			container.getClient().createRageThread(Integer.parseInt(serverSelected.port));
			try {
				container.showMutilPlayerScene();
			} catch (IOException | ParseException e1) {
				e1.printStackTrace();
			}
		}	
	}
}
