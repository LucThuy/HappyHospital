package scene;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

public class WaitScene extends JPanel {

	private Container container;
	private JButton btnBack;
	
	/**
	 * Create the panel.
	 */
	public WaitScene(Container container) {
		this.container = container;
		
		setUI();

	}
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		ImageIcon waiting = new ImageIcon("data/waiting.png");
		ImageIcon background = new ImageIcon("data/background.png");
		
		g.drawImage(background.getImage(), 0, 0, this.getWidth(), this.getHeight(), null);
		g.drawImage(waiting.getImage(), this.getWidth()/4, this.getHeight()/4, this.getWidth()/2, this.getHeight()/4, null);

	}
	
	private void setUI() {
		//setBackground(new Color(1.0f ,1.0f,1.0f,0.0f));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{100, 0, 0};
		gridBagLayout.rowHeights = new int[]{25, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 5.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 5.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		btnBack = new JButton("");
		btnBack.setOpaque(false);
		btnBack.setFocusPainted(false);
		btnBack.setBorderPainted(false);
		btnBack.setContentAreaFilled(false);
		btnBack.setIcon(new ImageIcon("data/btnBack.png"));
		GridBagConstraints gbc_btnBack = new GridBagConstraints();
		gbc_btnBack.insets = new Insets(0, 0, 0, 0);
		gbc_btnBack.gridx = 0;
		gbc_btnBack.gridy = 0;
		add(btnBack, gbc_btnBack);
		btnBack.addMouseListener(new MouseBack());
		
//		btnBack.setMinimumSize(new Dimension(100, 25));
//		btnBack.setPreferredSize(new Dimension(100, 25));
//		btnBack.setMaximumSize(new Dimension(300, 100));
	}

	
	class MouseBack implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			try {
				container.getClient().getRageThread().byeBye();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			container.showServerSelectScene(container.getClient().getChillThread().getServerAvailable());	
				
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
}
