package scene;

import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import scene.HowToPlayScene.MouseBack;
import map.Sound;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.SpringLayout;

import org.json.simple.parser.ParseException;

import java.awt.Font;
import java.awt.Graphics;


import java.awt.Insets;
import javax.swing.SwingConstants;

public class WinScene extends JPanel {

	public Container container;
	public PlayScene playScene;
	private JButton btnHome;
	private JLabel lblScore;

	/**
	 * Create the panel.
	 */
	public WinScene(Container container) {
		this.container = container;
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{12.0, 2.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		setUp();
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		ImageIcon background = new ImageIcon("data/winScene.png");
		g.drawImage(background.getImage(), 0, 0, this.getWidth(), this.getHeight(), null);
	}
	
	public void setUp() {
		btnHome = new JButton();
		btnHome.setOpaque(false);
		btnHome.setFocusPainted(false);
		btnHome.setBorderPainted(false);
		btnHome.setContentAreaFilled(false);
		setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
		
		lblScore = new JLabel("");
		getLblScore().setFont(new Font("Segoe UI Semibold", Font.BOLD, 40));
		getLblScore().setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblScore = new GridBagConstraints();
		gbc_lblScore.insets = new Insets(0, 0, 5, 5);
		gbc_lblScore.gridx = 1;
		gbc_lblScore.gridy = 1;
		add(getLblScore(), gbc_lblScore);

		btnHome.setIcon(new ImageIcon("data/btnHome.png"));
		GridBagConstraints gbc_btnBack = new GridBagConstraints();
		gbc_btnBack.insets = new Insets(0, 0, 0, 5);
		gbc_btnBack.gridx = 1;
		gbc_btnBack.gridy = 2;
		add(btnHome, gbc_btnBack);
		btnHome.addMouseListener(new MouseHome());		
	}
	
	public JLabel getLblScore() {
		return lblScore;
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
}
