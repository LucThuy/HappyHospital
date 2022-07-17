package scene;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class WaitScene extends JPanel {

	private Container container;
	
	/**
	 * Create the panel.
	 */
	public WaitScene(Container container) {
		this.container = container;
		
		setUI();

	}
	
	private void setUI() {
		setBackground(Color.WHITE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{100, 0, 0};
		gridBagLayout.rowHeights = new int[]{25, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 5.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 5.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JButton btnBack = new JButton("Back");
		btnBack.setForeground(Color.WHITE);
		btnBack.setBackground(Color.GRAY);
		GridBagConstraints gbc_btnBack = new GridBagConstraints();
		gbc_btnBack.insets = new Insets(0, 0, 5, 5);
		gbc_btnBack.gridx = 0;
		gbc_btnBack.gridy = 0;
		add(btnBack, gbc_btnBack);
		btnBack.addActionListener(new BtnBack());
		
		btnBack.setMinimumSize(new Dimension(100, 25));
		btnBack.setPreferredSize(new Dimension(100, 25));
		btnBack.setMaximumSize(new Dimension(300, 100));
	}

	class BtnBack implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				container.getClient().getRageThread().byeBye();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			container.showServerSelectScene(container.getClient().getChillThread().getServerAvailable());	
		}		
	}
}
