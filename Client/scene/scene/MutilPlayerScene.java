package scene;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import object.ZaWarudo;

public class MutilPlayerScene extends JPanel {

	private Container container;
	private Board board;
	
	public JLabel lblScoreHost;
	public JLabel lblScoreGuest;
	
	private int isHost = 0;
	
	private GridBagConstraints gbc_pnlGame;
	
	/**
	 * Create the panel.
	 */
	public MutilPlayerScene(Container container) {
		this.container = container;
		
		addKeyListener(new CustomKeyListener());
		
		setUI();
	}
	
	private void setUI() {
		setBackground(Color.WHITE);
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{500};
		gridBagLayout.rowHeights = new int[]{0};
		gridBagLayout.columnWeights = new double[]{1.0};
		gridBagLayout.rowWeights = new double[]{1.0};
		setLayout(gridBagLayout);
		
		JPanel pnlGame = new JPanel();
		pnlGame.setBackground(Color.WHITE);
		gbc_pnlGame = new GridBagConstraints();
		gbc_pnlGame.insets = new Insets(0, 0, 0, 5);
		gbc_pnlGame.fill = GridBagConstraints.BOTH;
		gbc_pnlGame.gridx = 0;
		gbc_pnlGame.gridy = 0;
		add(pnlGame, gbc_pnlGame);
		
		JPanel pnlButton = new JPanel();
		pnlButton.setBackground(Color.WHITE);
		GridBagConstraints gbc_pnlButton = new GridBagConstraints();
		gbc_pnlButton.fill = GridBagConstraints.BOTH;
		gbc_pnlButton.gridx = 1;
		gbc_pnlButton.gridy = 0;
		add(pnlButton, gbc_pnlButton);
		pnlButton.setLayout(new BoxLayout(pnlButton, BoxLayout.Y_AXIS));
		
		JButton btnBack = new JButton("Back");
		btnBack.setForeground(Color.WHITE);
		btnBack.setBackground(Color.GRAY);
		btnBack.setFocusable(false);
		pnlButton.add(btnBack);
		btnBack.addActionListener(new BtnBack());
		
		btnBack.setMinimumSize(new Dimension(65, 25));
		btnBack.setPreferredSize(new Dimension(65, 25));
		btnBack.setMaximumSize(new Dimension(100, 50));
		
		lblScoreHost = new JLabel("0");
		lblScoreHost.setHorizontalAlignment(SwingConstants.CENTER);
		lblScoreHost.setBackground(Color.WHITE);
		pnlButton.add(lblScoreHost);
		
		lblScoreHost.setMinimumSize(new Dimension(65, 25));
		lblScoreHost.setPreferredSize(new Dimension(65, 25));
		lblScoreHost.setMaximumSize(new Dimension(100, 50));
		
		lblScoreGuest = new JLabel("0");
		lblScoreGuest.setHorizontalAlignment(SwingConstants.CENTER);
		lblScoreGuest.setBackground(Color.WHITE);
		pnlButton.add(lblScoreGuest);
		
		lblScoreGuest.setMinimumSize(new Dimension(65, 25));
		lblScoreGuest.setPreferredSize(new Dimension(65, 25));
		lblScoreGuest.setMaximumSize(new Dimension(100, 50));
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
	
	class CustomKeyListener implements KeyListener {
		
		@Override
		public void keyTyped(KeyEvent e) {

		}

		@Override
		public void keyPressed(KeyEvent e) {
			int key  = e.getKeyCode();
			
			if(key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) {
				container.getClient().getRageThread().keyPressed("D", isHost);
			}
			if(key == KeyEvent.VK_W || key == KeyEvent.VK_UP) {
				container.getClient().getRageThread().keyPressed("W", isHost);
			}
			if(key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) {
				container.getClient().getRageThread().keyPressed("A", isHost);
			}
			if(key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) {
				container.getClient().getRageThread().keyPressed("S", isHost);
			}	
			if(key == KeyEvent.VK_I) {
				container.getClient().getRageThread().keyPressed("I", isHost);
			}		
		}

		@Override
		public void keyReleased(KeyEvent e) {
			int key  = e.getKeyCode();
			
			if(key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) {
				container.getClient().getRageThread().keyReleased("D", isHost);
			}
			if(key == KeyEvent.VK_W || key == KeyEvent.VK_UP) {
				container.getClient().getRageThread().keyReleased("W", isHost);
			}
			if(key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) {
				container.getClient().getRageThread().keyReleased("A", isHost);
			}
			if(key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) {
				container.getClient().getRageThread().keyReleased("S", isHost);
			}
			if(key == KeyEvent.VK_I) {
				container.getClient().getRageThread().keyReleased("I", isHost);
			}		
		}	
	}
	
	public void setBoard(Board board) {
		this.board = board;
		add(this.board, gbc_pnlGame);
	}
	
	public Board getBoard() {
		return this.board;
	}
	
	public void setHost() {
		this.isHost = 1;
	}
}
