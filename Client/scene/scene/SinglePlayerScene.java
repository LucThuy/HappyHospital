package scene;

import javax.swing.JPanel;
import javax.swing.JTextField;

import org.json.simple.parser.ParseException;

import object.ZaWarudo;
import map.Sound;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.ImageIcon;

import java.awt.Font;

public class SinglePlayerScene extends JPanel {

	private Container container;
	private PlayScene playScene;
	
	public GridBagConstraints gbc_panel;
	public JLabel lblScore;

	private JButton btnMusic;

	public JTextField txtSetAgent;

	private JLabel lblTime;
	private Timer timer;	
	public int second, minute;
	private String ddSecond, ddMinute;	
	private DecimalFormat dFormat = new DecimalFormat("00");	
	private Sound sound = new Sound();
	
	private boolean isMusic = true;
	
	/**
	 * Create the panel.
	 * @throws ParseException 
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public SinglePlayerScene(Container container) throws FileNotFoundException, IOException, ParseException {
		this.container = container;

		setUI();
		
		second =0;
		minute =5;

		timer = new Timer(1000, new CustomActionListener());	
		timer.start();
		addKeyListener(new CustomKeyListener());
	}
	
	private void setUI() {
		setBackground(Color.WHITE);
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{300, 25, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{5.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 0, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
//		add(playScene, gbc_panel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 1;
		gbc_panel_1.gridy = 0;
		add(panel_1, gbc_panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{0, 0};
		gbl_panel_1.rowHeights = new int[]{0, 0, 0, 25, 0, 0, 0, 0};
		gbl_panel_1.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		JButton btnPause = new JButton("ll");
		btnPause.setFont(new Font("Tahoma", Font.BOLD, 30));
		btnPause.setForeground(Color.WHITE);
		btnPause.setBackground(Color.GRAY);
		btnPause.setFocusable(false);
		GridBagConstraints gbc_btnPause = new GridBagConstraints();
		gbc_btnPause.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnPause.insets = new Insets(0, 0, 5, 0);
		gbc_btnPause.gridx = 0;
		gbc_btnPause.gridy = 0;
		panel_1.add(btnPause, gbc_btnPause);
		btnPause.addActionListener(new BtnPause());
		
		btnPause.setMinimumSize(new Dimension(65, 25));
		btnPause.setPreferredSize(new Dimension(65, 25));
		btnPause.setMaximumSize(new Dimension(100, 50));
		
		btnMusic = new JButton(new ImageIcon("data/btnMusic.png"));
		btnMusic.setOpaque(false);
		btnMusic.setFocusable(false);
		btnMusic.setFocusPainted(false);
		btnMusic.setBorderPainted(false);
		btnMusic.setContentAreaFilled(false);
		btnMusic.addMouseListener(new MouseMusic());
		
		GridBagConstraints gbc_btnMusic = new GridBagConstraints();
		gbc_btnMusic.insets = new Insets(0, 0, 5, 0);
		gbc_btnMusic.gridx = 0;
		gbc_btnMusic.gridy = 1;
		panel_1.add(btnMusic, gbc_btnMusic);
		
		lblTime = new JLabel();
		lblTime.setHorizontalAlignment(SwingConstants.CENTER);
		lblTime.setForeground(Color.DARK_GRAY);
		lblTime.setBackground(Color.WHITE);
		GridBagConstraints gbc_lblTime = new GridBagConstraints();
		gbc_lblTime.insets = new Insets(0, 0, 5, 0);
		gbc_lblTime.gridx = 0;
		gbc_lblTime.gridy = 2;
		panel_1.add(lblTime, gbc_lblTime);
		
		lblTime.setMinimumSize(new Dimension(65, 25));
		lblTime.setPreferredSize(new Dimension(65, 25));
		lblTime.setMaximumSize(new Dimension(100, 50));
		lblTime.setText("5:00");
		
		lblScore = new JLabel("0");
		lblScore.setHorizontalAlignment(SwingConstants.CENTER);
		lblScore.setForeground(Color.DARK_GRAY);
		lblScore.setBackground(Color.WHITE);
		GridBagConstraints gbc_lblScore = new GridBagConstraints();
		gbc_lblScore.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblScore.insets = new Insets(0, 0, 5, 0);
		gbc_lblScore.gridx = 0;
		gbc_lblScore.gridy = 3;
		panel_1.add(lblScore, gbc_lblScore);
		
		lblScore.setMinimumSize(new Dimension(65, 25));
		lblScore.setPreferredSize(new Dimension(65, 25));
		lblScore.setMaximumSize(new Dimension(100, 50));
		
		JLabel lblNumAgent = new JLabel("NumAgents");
		GridBagConstraints gbc_lblNumAgent = new GridBagConstraints();
		gbc_lblNumAgent.anchor = GridBagConstraints.SOUTH;
		gbc_lblNumAgent.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNumAgent.insets = new Insets(0, 0, 5, 0);
		gbc_lblNumAgent.gridx = 0;
		gbc_lblNumAgent.gridy = 4;
		panel_1.add(lblNumAgent, gbc_lblNumAgent);
		
		txtSetAgent = new JTextField();
		txtSetAgent.setText("0");
		txtSetAgent.setHorizontalAlignment(SwingConstants.CENTER);
		txtSetAgent.setSize(100, 50);
		GridBagConstraints gbc_txtSetAgent = new GridBagConstraints();
		gbc_txtSetAgent.anchor = GridBagConstraints.NORTH;
		gbc_txtSetAgent.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtSetAgent.insets = new Insets(0, 0, 5, 0);
		gbc_txtSetAgent.gridx = 0;
		gbc_txtSetAgent.gridy = 5;
		panel_1.add(txtSetAgent, gbc_txtSetAgent);
		
		JButton btnApply = new JButton("Apply");
		btnApply.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnApply.setForeground(Color.WHITE);
		btnApply.setBackground(Color.GRAY);
		btnApply.setFocusable(false);
		GridBagConstraints gbc_btnApply = new GridBagConstraints();
		gbc_btnApply.anchor = GridBagConstraints.NORTH;
		gbc_btnApply.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnApply.gridx = 0;
		gbc_btnApply.gridy = 6;
		panel_1.add(btnApply, gbc_btnApply);	
		btnApply.addActionListener(new BtnApply());
		
		btnApply.setMinimumSize(new Dimension(65, 25));
		btnApply.setPreferredSize(new Dimension(65, 25));
		btnApply.setMaximumSize(new Dimension(100, 50));				
	}
	
	private SinglePlayerScene getSinglePlayerScene() {
		return this;
	}	
	
	class BtnPause implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			playScene.update.stop();
			
			container.showPauseScene(getSinglePlayerScene());	
		}	
	}
	
	class BtnApply implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			int count = Integer.valueOf(txtSetAgent.getText());
			PlayScene.setNumberOfAgents(count);		
			getSinglePlayerScene().requestFocusInWindow();
		}
		
	}
	
	class MouseMusic implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			if(isMusic) {
				isMusic = false;
				btnMusic.setIcon(new ImageIcon("data/btnMusicMute.png"));
				container.getSound().turnOffMusic();
			}
			else {
				isMusic = true;
				btnMusic.setIcon(new ImageIcon("data/btnMusic.png"));
				container.getSound().turnOnMusicLoop(1);
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {

		}

		@Override
		public void mouseExited(MouseEvent e) {

		}
		
	}
	
	class CustomActionListener implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(isMove && isMusic) {
				sound.turnOnMusic(5);
			}
			second--;	
			if(second==-1) {
				second = 59;
				minute--;
			}
			ddSecond = dFormat.format(second);
			ddMinute = dFormat.format(minute);	
			lblTime.setText(ddMinute + ":" + ddSecond);
			if(minute==0 && second==0) {
				timer.stop();
				container.showWinScene();			
			}		
		}	
	}
	
	public boolean isMove = false;
	
	class CustomKeyListener implements KeyListener {
		
		private boolean isIPress = false;
		
		@Override
		public void keyTyped(KeyEvent e) {

		}

		@Override
		public void keyPressed(KeyEvent e) {
			int key  = e.getKeyCode();
			
			if(key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) {
				playScene.player.msE = playScene.player.ms;
				isMove = true;
			}
			if(key == KeyEvent.VK_W || key == KeyEvent.VK_UP) {
				playScene.player.msN = playScene.player.ms;
				isMove = true;
			}
			if(key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) {
				playScene.player.msW = playScene.player.ms;
				isMove = true;
			}
			if(key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) {
				playScene.player.msS = playScene.player.ms;
				isMove = true;
			}
			
			if(key == KeyEvent.VK_I) {
				if(!isIPress) {
					if(ZaWarudo.isZaWarudo) {
						playScene.player.blink.isBlink = true;
					}
					else /*if(!playScene.player.blink.blinkCD.isCD())*/ {
						playScene.player.blink.isBlink = true;
						if(isMusic) {
							sound.turnOnMusic(4);
						}
					}
					isIPress = true;
				}
			}		
		}

		@Override
		public void keyReleased(KeyEvent e) {
			int key  = e.getKeyCode();
			
			if(key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) {
				playScene.player.msE = 0;
				isMove = false;
			}
			if(key == KeyEvent.VK_W || key == KeyEvent.VK_UP) {
				playScene.player.msN = 0;
				isMove = false;
			}
			if(key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) {
				playScene.player.msW = 0;
				isMove = false;
			}
			if(key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) {
				playScene.player.msS = 0;
				isMove = false;
			}
			
			if(key == KeyEvent.VK_I) {
				isIPress = false;
			}		
		}	
	}
	

	public PlayScene getPlayScene() {
		return this.playScene;
	}
	
	public void setPlayScene(PlayScene playScene) {
		this.playScene = playScene;
		this.playScene.player.setName(container.getClient().getClientName());
		add(this.playScene, gbc_panel);
	}

}