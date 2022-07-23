package scene;

import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import org.json.simple.parser.ParseException;

import java.awt.GridLayout;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.awt.FlowLayout;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import scene.ServerSelectScene.ServerAvailable;

import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JList;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.ListSelectionModel;

public class PauseScene extends JPanel {
	
	public Container container;
	private SinglePlayerScene singlePlayerScene;
	
	private JList<SaveFile> listSaveFile;
	private DefaultListModel<SaveFile> model;
	private JButton btnHome;
	private JButton btnBack;
	private JButton btnSaveGame;
	
	/**
	 * Create the panel.
	 */
	public PauseScene(Container container) {
		this.container = container;
		this.singlePlayerScene = singlePlayerScene;
			
		setUp();
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		ImageIcon background = new ImageIcon("data/background.png");
		g.drawImage(background.getImage(), 0, 0, this.getWidth(), this.getHeight(), null);
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
		pnlButton.setOpaque(false);
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
		
		btnBack = new JButton("");
		btnBack.setOpaque(false);
		btnBack.setFocusPainted(false);
		btnBack.setBorderPainted(false);
		btnBack.setContentAreaFilled(false);
		btnBack.setIcon(new ImageIcon("data/btnBack.png"));
		btnBack.setForeground(Color.WHITE);
		btnBack.setBackground(Color.GRAY);
		GridBagConstraints gbc_btnBack = new GridBagConstraints();
		gbc_btnBack.fill = GridBagConstraints.BOTH;
		gbc_btnBack.insets = new Insets(0, 0, 5, 0);
		gbc_btnBack.gridx = 0;
		gbc_btnBack.gridy = 0;
		pnlButton.add(btnBack, gbc_btnBack);
		btnBack.addMouseListener(new MouseBack());
		
		btnSaveGame = new JButton("");
		btnSaveGame.setOpaque(false);
		btnSaveGame.setFocusPainted(false);
		btnSaveGame.setBorderPainted(false);
		btnSaveGame.setContentAreaFilled(false);
		btnSaveGame.setIcon(new ImageIcon("data/btnSaveGame.png"));
		btnSaveGame.setForeground(Color.WHITE);
		btnSaveGame.setBackground(Color.GRAY);
		GridBagConstraints gbc_btnSaveGame = new GridBagConstraints();
		gbc_btnSaveGame.fill = GridBagConstraints.BOTH;
		gbc_btnSaveGame.insets = new Insets(0, 0, 5, 0);
		gbc_btnSaveGame.gridx = 0;
		gbc_btnSaveGame.gridy = 2;
		pnlButton.add(btnSaveGame, gbc_btnSaveGame);
		btnSaveGame.addMouseListener(new MouseSaveGame());
		
		btnHome = new JButton("");
		btnHome.setOpaque(false);
		btnHome.setFocusPainted(false);
		btnHome.setBorderPainted(false);
		btnHome.setContentAreaFilled(false);
		setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
		btnHome.setIcon(new ImageIcon("data/btnHome.png"));
		GridBagConstraints gbc_btnHome = new GridBagConstraints();
		gbc_btnHome.fill = GridBagConstraints.BOTH;
		gbc_btnHome.gridx = 0;
		gbc_btnHome.gridy = 4;
		pnlButton.add(btnHome, gbc_btnHome);
		btnHome.addMouseListener(new MouseHome());
		
		JPanel pnlSaveFile = new JPanel();
		pnlSaveFile.setOpaque(false);
		GridBagConstraints gbc_pnlSaveFile = new GridBagConstraints();
		gbc_pnlSaveFile.fill = GridBagConstraints.VERTICAL;
		gbc_pnlSaveFile.gridx = 1;
		gbc_pnlSaveFile.gridy = 0;
		add(pnlSaveFile, gbc_pnlSaveFile);
		pnlSaveFile.setLayout(new BorderLayout(0, 0));
		
		listSaveFile = new JList();
		listSaveFile.setOpaque(false);
		listSaveFile.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//		listSaveFile.setFont(new Font("Tahoma", Font.PLAIN, 20));
		pnlSaveFile.add(listSaveFile);
		model = new DefaultListModel<>();
		
		model.addElement(new SaveFile("Save Slot 1", "save/save1.json", 1));
		model.addElement(new SaveFile("Save Slot 2", "save/save2.json", 2));
		model.addElement(new SaveFile("Save Slot 3", "save/save3.json", 3));
		model.addElement(new SaveFile("Save Slot 4", "save/save4.json", 4));
		model.addElement(new SaveFile("Save Slot 5", "save/save5.json", 5));
		
		listSaveFile.setModel(model);	
		listSaveFile.setCellRenderer(new SaveFileRenderer());
	}
	
	public void setSinglePlayerScene(SinglePlayerScene singlePlayerScene) {
		this.singlePlayerScene = singlePlayerScene;
	}
	
	class SaveFile {
		private String name;
		private String link;
		private int id;
		
		public SaveFile(String name, String link, int id) {
			this.name = name;
			this.link = link;
			this.id = id;
		}
		
		@Override
		public String toString() {
			return this.name;
		}
	}
	
	class MouseSaveGame implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			SaveFile saveFile = model.elementAt(listSaveFile.getSelectedIndex());	
			try {
				container.getSinglePlayerScene().getPlayScene().saveData(saveFile.link);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			btnSaveGame.setIcon(new ImageIcon("data/btnSaveGamePress.png"));
		}

		@Override
		public void mouseExited(MouseEvent e) {
			btnSaveGame.setIcon(new ImageIcon("data/btnSaveGame.png"));
		}
		
	}
	
	
	class MouseBack implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			container.showSinglePlayerSceneAgain();
		}

		@Override
		public void mousePressed(MouseEvent e) {
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			btnBack.setIcon(new ImageIcon("data/btnBackPress.png"));
		}

		@Override
		public void mouseExited(MouseEvent e) {
			btnBack.setIcon(new ImageIcon("data/btnBack.png"));
		}
		
	}
	
	class MouseHome implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {

		}

		@Override
		public void mousePressed(MouseEvent e) {
			container.getSound().turnOffMusic();
			container.showMenuScene();
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			btnHome.setIcon(new ImageIcon("data/btnHomePress.png"));
		}

		@Override
		public void mouseExited(MouseEvent e) {
			btnHome.setIcon(new ImageIcon("data/btnHome.png"));
		}
		
	}
	
	class SaveFileRenderer extends JPanel implements ListCellRenderer<SaveFile> {
	 
		private JLabel name = new JLabel();
		
	    public SaveFileRenderer() {
	        setLayout(new BorderLayout(0, 0));
	        setOpaque(false);
	        
	        add(name, BorderLayout.CENTER);
	    }

		@Override
		public Component getListCellRendererComponent(JList<? extends SaveFile> list, SaveFile value, int index,
			boolean isSelected, boolean cellHasFocus) {
			
//			name.setText(value.name);
			
			int i = value.id ;
			if(isSelected) {
				name.setIcon(new ImageIcon("data/btnSaveFile" + i + "Press.png"));
			}
			else {
				name.setIcon(new ImageIcon("data/btnSaveFile" + i + ".png"));
			}
			
			return this;
		}
	}
}
