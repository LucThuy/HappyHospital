package scene;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class CreditScene extends JPanel {

	private Container container;
	
	private JButton btnBack;
	/**
	 * Create the panel.
	 */
	public CreditScene(Container container) {
		this.container = container;
		
		setUp();
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		ImageIcon background = new ImageIcon("data/background.png");
		g.drawImage(background.getImage(), 0, 0, this.getWidth(), this.getHeight(), null);
	}
	
	private void setUp() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0};
		gridBagLayout.rowHeights = new int[]{31, 0, 0};
		gridBagLayout.columnWeights = new double[]{5.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{5.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		btnBack = new JButton();
		btnBack.setOpaque(false);
		btnBack.setFocusPainted(false);
		btnBack.setBorderPainted(false);
		btnBack.setContentAreaFilled(false);
		setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
		btnBack.setIcon(new ImageIcon("data/btnBack.png"));
		GridBagConstraints gbc_btnback = new GridBagConstraints();
		gbc_btnback.fill = GridBagConstraints.BOTH;
		gbc_btnback.gridx = 1;
		gbc_btnback.gridy = 1;
		add(btnBack, gbc_btnback);
		btnBack.addMouseListener(new MouseBack());
	}
	
	class MouseBack implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			container.showMenuScene();
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
}
