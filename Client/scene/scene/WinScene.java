package scene;

import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
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

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.SpringLayout;

import org.json.simple.parser.ParseException;

import java.awt.Font;
import java.awt.Graphics;

public class WinScene extends JPanel {

	public Container container;
	private JButton btnHome;
	
	/**
	 * Create the panel.
	 */
	public WinScene(Container container) {
		this.container = container;
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0};
		gridBagLayout.columnWeights = new double[]{5.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{5.0, 1.0, Double.MIN_VALUE};
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
		btnHome.setIcon(new ImageIcon("data/btnHome.png"));
		GridBagConstraints gbc_btnBack = new GridBagConstraints();
		gbc_btnBack.gridx = 1;
		gbc_btnBack.gridy = 1;
		add(btnHome, gbc_btnBack);
		btnHome.addMouseListener(new MouseHome());
	}
	
	
	
	class MouseHome implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			try {
				container.getSinglePlayerScene().getPlayScene().setUp();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			container.showMenuScene();
		}

		@Override
		public void mousePressed(MouseEvent e) {
			
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
