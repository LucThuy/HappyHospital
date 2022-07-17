package scene;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreditScene extends JPanel {

	private Container container;
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
		
		JButton btnback = new JButton();
		btnback.setBackground(Color.WHITE);
		GridBagConstraints gbc_btnback = new GridBagConstraints();
		gbc_btnback.fill = GridBagConstraints.BOTH;
		gbc_btnback.gridx = 1;
		gbc_btnback.gridy = 1;
		add(btnback, gbc_btnback);
		btnback.addActionListener(new BtnBack());
	}
	
	class BtnBack implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			container.showCreditScene();
		}
	}
}
