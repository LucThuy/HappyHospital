package scene;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.GridBagLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import javax.swing.JButton;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.UIManager;

import org.json.simple.parser.ParseException;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;

import map.Sound;

public class MenuScene extends JPanel {

	private Container container;
	private JTextField txtNameInput;
	private JButton btnSinglePlayer;
	private JButton btnMutilPlayer;
	private JButton btnHowToPlay;
	private JButton btnCredit;
	
	public Sound sound = new Sound();
	
	/**
	 * Create the panel.
	 */
	public MenuScene(Container container) {
		this.container = container;
		setUI();
		this.sound.turnOnMusic(6);
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		ImageIcon background = new ImageIcon("data/menuScene.png");
		g.drawImage(background.getImage(), 0, 0, this.getWidth(), this.getHeight(), null);
	}
	
	private void setUI() {
		setBackground(Color.WHITE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{15.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblNameInput = new JLabel("Name Your Warrior");
		lblNameInput.setForeground(Color.WHITE);
		lblNameInput.setFont(new Font("Tahoma", Font.BOLD, 35));
		lblNameInput.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblNameInput = new GridBagConstraints();
		gbc_lblNameInput.fill = GridBagConstraints.VERTICAL;
		gbc_lblNameInput.gridwidth = 2;
		gbc_lblNameInput.insets = new Insets(0, 0, 5, 5);
		gbc_lblNameInput.gridx = 0;
		gbc_lblNameInput.gridy = 1;
		add(lblNameInput, gbc_lblNameInput);
		
		txtNameInput = new JTextField();
		txtNameInput.setForeground(Color.WHITE);
		txtNameInput.setBackground(new Color(255, 228, 225));
		txtNameInput.setOpaque(false);
//		txtNameInput.setFocusPainted(false);
//		txtNameInput.setBorderPainted(false);
//		txtNameInput.setContentAreaFilled(false);
		setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
		txtNameInput.setFont(new Font("Tahoma", Font.BOLD, 25));
		txtNameInput.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_txtInputName = new GridBagConstraints();
		gbc_txtInputName.gridwidth = 2;
		gbc_txtInputName.insets = new Insets(0, 0, 5, 0);
		gbc_txtInputName.gridx = 0;
		gbc_txtInputName.gridy = 2;
		add(txtNameInput, gbc_txtInputName);
		txtNameInput.setColumns(10);
		
		txtNameInput.setMinimumSize(new Dimension(400, 40));
		txtNameInput.setPreferredSize(new Dimension(400, 40));
		txtNameInput.setMaximumSize(new Dimension(400, 40));
		
		btnSinglePlayer = new JButton("");
		btnSinglePlayer.setOpaque(false);
		btnSinglePlayer.setFocusPainted(false);
		btnSinglePlayer.setBorderPainted(false);
		btnSinglePlayer.setContentAreaFilled(false);
		setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
		btnSinglePlayer.setIcon(new ImageIcon("data/btnSinglePlayer.png"));
		GridBagConstraints gbc_btnSinglePlayer = new GridBagConstraints();
		gbc_btnSinglePlayer.insets = new Insets(0, 0, 5, 5);
		gbc_btnSinglePlayer.gridx = 0;
		gbc_btnSinglePlayer.gridy = 3;
		add(btnSinglePlayer, gbc_btnSinglePlayer);
//		btnSinglePlayer.addActionListener(new BtnSinglePlayer());
		btnSinglePlayer.addMouseListener(new MouseSinglePlayer());
		
		btnMutilPlayer = new JButton("");
		btnMutilPlayer.setOpaque(false);
		btnMutilPlayer.setFocusPainted(false);
		btnMutilPlayer.setBorderPainted(false);
		btnMutilPlayer.setContentAreaFilled(false);
		setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
		btnMutilPlayer.setIcon(new ImageIcon("data/btnMutilPlayer.png"));
		GridBagConstraints gbc_btnMutilPlayer = new GridBagConstraints();
		gbc_btnMutilPlayer.insets = new Insets(0, 0, 5, 0);
		gbc_btnMutilPlayer.gridx = 1;
		gbc_btnMutilPlayer.gridy = 3;
		add(btnMutilPlayer, gbc_btnMutilPlayer);
//		btnMutilPlayer.addActionListener(new BtnMutilPlayer());
		btnMutilPlayer.addMouseListener(new MouseMutilPlayer());
		
		btnHowToPlay = new JButton("");
		btnHowToPlay.setOpaque(false);
		btnHowToPlay.setFocusPainted(false);
		btnHowToPlay.setBorderPainted(false);
		btnHowToPlay.setContentAreaFilled(false);
		setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
		btnHowToPlay.setIcon(new ImageIcon("data/btnHowToPlay.png"));
		GridBagConstraints gbc_btnHowToPlay = new GridBagConstraints();
		gbc_btnHowToPlay.insets = new Insets(0, 0, 0, 5);
		gbc_btnHowToPlay.gridx = 0;
		gbc_btnHowToPlay.gridy = 4;
		add(btnHowToPlay, gbc_btnHowToPlay);
		btnHowToPlay.addMouseListener(new MouseHowToPlay());
		
		btnCredit = new JButton("");
		btnCredit.setOpaque(false);
		btnCredit.setFocusPainted(false);
		btnCredit.setBorderPainted(false);
		btnCredit.setContentAreaFilled(false);
		setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
		btnCredit.setIcon(new ImageIcon("data/btnCredit.png"));
		GridBagConstraints gbc_btnCredit = new GridBagConstraints();
		gbc_btnCredit.gridx = 1;
		gbc_btnCredit.gridy = 4;
		add(btnCredit, gbc_btnCredit);
		btnCredit.addMouseListener(new MouseCredit());
			
	}
	
	class MouseSinglePlayer implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
				
		}

		@Override
		public void mousePressed(MouseEvent e) {
			String name = txtNameInput.getText();
			container.getClient().setClientName(name);
			
			try {
				container.showSinglePlayerScene();
			} catch (IOException | ParseException e1) {
				e1.printStackTrace();
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			btnSinglePlayer.setIcon(new ImageIcon("data/btnSinglePlayerPress.png"));
		}

		@Override
		public void mouseExited(MouseEvent e) {
			btnSinglePlayer.setIcon(new ImageIcon("data/btnSinglePlayer.png"));
		}
		
	}
	
	class MouseMutilPlayer implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			String name = txtNameInput.getText();
			container.getClient().setClientName(name);
			
			container.showServerSelectScene(container.getClient().getChillThread().getServerAvailable());	
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			btnMutilPlayer.setIcon(new ImageIcon("data/btnMutilPlayerPress.png"));
		}

		@Override
		public void mouseExited(MouseEvent e) {
			btnMutilPlayer.setIcon(new ImageIcon("data/btnMutilPlayer.png"));
		}
		
	}
	
	class MouseHowToPlay implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			container.showHowToPlayScene();
		}

		@Override
		public void mousePressed(MouseEvent e) {
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			btnHowToPlay.setIcon(new ImageIcon("data/btnHowToPlayPress.png"));
		}

		@Override
		public void mouseExited(MouseEvent e) {
			btnHowToPlay.setIcon(new ImageIcon("data/btnHowToPlay.png"));
		}
		
	}

	class MouseCredit implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			container.showHowToPlayScene();
		}

		@Override
		public void mousePressed(MouseEvent e) {
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			btnCredit.setIcon(new ImageIcon("data/btnCreditPress.png"));
		}

		@Override
		public void mouseExited(MouseEvent e) {
			btnCredit.setIcon(new ImageIcon("data/btnCredit.png"));
		}
		
	}
}
