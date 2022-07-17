package scene;

import javax.swing.JPanel;

import org.json.simple.parser.ParseException;

import java.awt.GridLayout;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.FlowLayout;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import scene.ServerSelectScene.ServerAvailable;

import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.JList;
import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.ListSelectionModel;

public class PauseScene extends JPanel {
	
	public Container container;
	private SinglePlayerScene singlePlayerScene;
	
	private JList<SaveFile> listSaveFile;
	private DefaultListModel<SaveFile> model;
	
	/**
	 * Create the panel.
	 */
	public PauseScene(Container container) {
		this.container = container;
		this.singlePlayerScene = singlePlayerScene;
			
		setUp();
	}
	
	public void setUp() {
		setBackground(Color.WHITE);
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 3.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JPanel pnlButton = new JPanel();
		pnlButton.setBackground(Color.LIGHT_GRAY);
		GridBagConstraints gbc_pnlButton = new GridBagConstraints();
		gbc_pnlButton.insets = new Insets(0, 0, 0, 5);
		gbc_pnlButton.fill = GridBagConstraints.BOTH;
		gbc_pnlButton.gridx = 0;
		gbc_pnlButton.gridy = 0;
		add(pnlButton, gbc_pnlButton);
		GridBagLayout gbl_pnlButton = new GridBagLayout();
		gbl_pnlButton.columnWidths = new int[]{0, 0};
		gbl_pnlButton.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl_pnlButton.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_pnlButton.rowWeights = new double[]{1.0, 4.0, 2.0, 0.0, 2.0, Double.MIN_VALUE};
		pnlButton.setLayout(gbl_pnlButton);
		
		JButton btnBack = new JButton("Back");
		btnBack.setForeground(Color.WHITE);
		btnBack.setBackground(Color.GRAY);
		GridBagConstraints gbc_btnBack = new GridBagConstraints();
		gbc_btnBack.fill = GridBagConstraints.BOTH;
		gbc_btnBack.insets = new Insets(0, 0, 5, 0);
		gbc_btnBack.gridx = 0;
		gbc_btnBack.gridy = 0;
		pnlButton.add(btnBack, gbc_btnBack);
		btnBack.addActionListener(new BtnBack());
		
		JButton btnSaveGame = new JButton("Save Game");
		btnSaveGame.setForeground(Color.WHITE);
		btnSaveGame.setBackground(Color.GRAY);
		GridBagConstraints gbc_btnSaveGame = new GridBagConstraints();
		gbc_btnSaveGame.fill = GridBagConstraints.BOTH;
		gbc_btnSaveGame.insets = new Insets(0, 0, 5, 0);
		gbc_btnSaveGame.gridx = 0;
		gbc_btnSaveGame.gridy = 2;
		pnlButton.add(btnSaveGame, gbc_btnSaveGame);
		
		JButton btnHome = new JButton("Home");
		btnHome.setForeground(Color.WHITE);
		btnHome.setBackground(Color.GRAY);
		GridBagConstraints gbc_btnHome = new GridBagConstraints();
		gbc_btnHome.fill = GridBagConstraints.BOTH;
		gbc_btnHome.gridx = 0;
		gbc_btnHome.gridy = 4;
		pnlButton.add(btnHome, gbc_btnHome);
		btnHome.addActionListener(new BtnHome());
		
		JPanel pnlSaveFile = new JPanel();
		pnlSaveFile.setBackground(Color.WHITE);
		GridBagConstraints gbc_pnlSaveFile = new GridBagConstraints();
		gbc_pnlSaveFile.fill = GridBagConstraints.BOTH;
		gbc_pnlSaveFile.gridx = 1;
		gbc_pnlSaveFile.gridy = 0;
		add(pnlSaveFile, gbc_pnlSaveFile);
		pnlSaveFile.setLayout(new BorderLayout(0, 0));
		
		listSaveFile = new JList();
		listSaveFile.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listSaveFile.setFont(new Font("Tahoma", Font.PLAIN, 20));
		pnlSaveFile.add(listSaveFile);
		model = new DefaultListModel<>();
		
		model.addElement(new SaveFile("Save Slot 1", "save/save1.json"));
		model.addElement(new SaveFile("Save Slot 2", "save/save2.json"));
		model.addElement(new SaveFile("Save Slot 3", "save/save3.json"));
		model.addElement(new SaveFile("Save Slot 4", "save/save4.json"));
		model.addElement(new SaveFile("Save Slot 5", "save/save5.json"));
		
		listSaveFile.setModel(model);				
	}
	
	public void setSinglePlayerScene(SinglePlayerScene singlePlayerScene) {
		this.singlePlayerScene = singlePlayerScene;
	}
	
	class SaveFile {
		private String name;
		private String link;
		
		public SaveFile(String name, String link) {
			this.name = name;
			this.link = link;
		}
		
		@Override
		public String toString() {
			return this.name;
		}
	}
	
	class BtnHome implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			container.showMenuScene();
		}
	}
	
	class BtnSaveGame implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			SaveFile saveFile = model.elementAt(listSaveFile.getSelectedIndex());
		}
		
	}
	
	class BtnBack implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			container.showSinglePlayerSceneAgain();
		}
		
	}

}
