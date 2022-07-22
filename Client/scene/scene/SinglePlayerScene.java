package scene;

import javax.swing.JPanel;
import javax.swing.JTextField;

import org.json.simple.parser.ParseException;

import object.ZaWarudo;
import map.Sound;
import scene.PlayScene.CustomKeyListener;

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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import java.awt.Font;

public class SinglePlayerScene extends JPanel {

	private Container container;
	private PlayScene playScene;
	
	public GridBagConstraints gbc_panel;
	public JLabel lblScore;

	public JTextField txtSetAgent;

	JLabel counterLabel;
	Timer timer;	
	int second, minute;
	String ddSecond, ddMinute;	
	DecimalFormat dFormat = new DecimalFormat("00");
	
	public Sound sound = new Sound();
	
	/**
	 * Create the panel.
	 * @throws ParseException 
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public SinglePlayerScene(Container container) throws FileNotFoundException, IOException, ParseException {
		this.container = container;

		setUI();
		
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
		gbl_panel_1.rowHeights = new int[]{0, 0, 25, 0, 0, 0, 0};
		gbl_panel_1.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		JButton btnPause = new JButton("ll");
		btnPause.setFont(new Font("Tahoma", Font.BOLD, 30));
		btnPause.setForeground(Color.WHITE);
		btnPause.setBackground(Color.GRAY);
		btnPause.setFocusable(false);
		GridBagConstraints gbc_btnPause = new GridBagConstraints();
		gbc_btnPause.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnPause.insets = new Insets(0, 0, 5, 5);
		gbc_btnPause.gridx = 0;
		gbc_btnPause.gridy = 0;
		panel_1.add(btnPause, gbc_btnPause);
		btnPause.addActionListener(new BtnPause());
		
		btnPause.setMinimumSize(new Dimension(65, 25));
		btnPause.setPreferredSize(new Dimension(65, 25));
		btnPause.setMaximumSize(new Dimension(100, 50));
		
		counterLabel = new JLabel();
		counterLabel.setHorizontalAlignment(SwingConstants.CENTER);
		counterLabel.setForeground(Color.DARK_GRAY);
		counterLabel.setBackground(Color.WHITE);
		GridBagConstraints gbc_counterLabel = new GridBagConstraints();
		gbc_counterLabel.insets = new Insets(0, 0, 5, 5);
		gbc_counterLabel.gridx = 0;
		gbc_counterLabel.gridy = 1;
		panel_1.add(counterLabel, gbc_counterLabel);
		
		counterLabel.setMinimumSize(new Dimension(65, 25));
		counterLabel.setPreferredSize(new Dimension(65, 25));
		counterLabel.setMaximumSize(new Dimension(100, 50));
		counterLabel.setText("16:00");
		
		lblScore = new JLabel("0");
		lblScore.setHorizontalAlignment(SwingConstants.CENTER);
		lblScore.setForeground(Color.DARK_GRAY);
		lblScore.setBackground(Color.WHITE);
		GridBagConstraints gbc_lblScore = new GridBagConstraints();
		gbc_lblScore.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblScore.insets = new Insets(0, 0, 5, 5);
		gbc_lblScore.gridx = 0;
		gbc_lblScore.gridy = 2;
		panel_1.add(lblScore, gbc_lblScore);
		
		lblScore.setMinimumSize(new Dimension(65, 25));
		lblScore.setPreferredSize(new Dimension(65, 25));
		lblScore.setMaximumSize(new Dimension(100, 50));
		
		JLabel lblNumAgent = new JLabel("NumAgents");
		GridBagConstraints gbc_lblNumAgent = new GridBagConstraints();
		gbc_lblNumAgent.anchor = GridBagConstraints.SOUTH;
		gbc_lblNumAgent.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNumAgent.insets = new Insets(0, 0, 5, 5);
		gbc_lblNumAgent.gridx = 0;
		gbc_lblNumAgent.gridy = 3;
		panel_1.add(lblNumAgent, gbc_lblNumAgent);
		
		txtSetAgent = new JTextField();
		txtSetAgent.setSize(100, 50);
		GridBagConstraints gbc_txtSetAgent = new GridBagConstraints();
		gbc_txtSetAgent.anchor = GridBagConstraints.NORTH;
		gbc_txtSetAgent.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtSetAgent.insets = new Insets(0, 0, 5, 5);
		gbc_txtSetAgent.gridx = 0;
		gbc_txtSetAgent.gridy = 4;
		panel_1.add(txtSetAgent, gbc_txtSetAgent);
		
		JButton btnApply = new JButton("Apply");
		btnApply.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnApply.setForeground(Color.WHITE);
		btnApply.setBackground(Color.GRAY);
		btnApply.setFocusable(false);
		GridBagConstraints gbc_btnApply = new GridBagConstraints();
		gbc_btnApply.insets = new Insets(0, 0, 0, 5);
		gbc_btnApply.anchor = GridBagConstraints.NORTH;
		gbc_btnApply.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnApply.gridx = 0;
		gbc_btnApply.gridy = 5;
		panel_1.add(btnApply, gbc_btnApply);	
		btnApply.addActionListener(new BtnApply());
		
		btnApply.setMinimumSize(new Dimension(65, 25));
		btnApply.setPreferredSize(new Dimension(65, 25));
		btnApply.setMaximumSize(new Dimension(100, 50));
		second =0;
		minute =16;
		countdownTimer();
		timer.start();

	}
	
	private SinglePlayerScene getSinglePlayerScene() {
		return this;
	}
	public void countdownTimer() {
		
		timer = new Timer(1000, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				second--;
				ddSecond = dFormat.format(second);
				ddMinute = dFormat.format(minute);	
				counterLabel.setText(ddMinute + ":" + ddSecond);
				
				if(second==-1) {
					second = 59;
					minute--;
					ddSecond = dFormat.format(second);
					ddMinute = dFormat.format(minute);	
					counterLabel.setText(ddMinute + ":" + ddSecond);
				}
				if(minute==0 && second==0) {
					timer.stop();
				}
			}
		});		
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
			}
			if(key == KeyEvent.VK_W || key == KeyEvent.VK_UP) {
				playScene.player.msN = playScene.player.ms;
			}
			if(key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) {
				playScene.player.msW = playScene.player.ms;
			}
			if(key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) {
				playScene.player.msS = playScene.player.ms;
			
			}
			
			if(key == KeyEvent.VK_I) {
				if(!isIPress) {
					if(ZaWarudo.isZaWarudo) {
						playScene.player.blink.isBlink = true;
					}
					else if(!playScene.player.blink.blinkCD.isCD()) {
						playScene.player.blink.isBlink = true;
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
				turnOnMusic1(5);
			}
			if(key == KeyEvent.VK_W || key == KeyEvent.VK_UP) {
				playScene.player.msN = 0;
				turnOnMusic1(5);
			}
			if(key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) {
				playScene.player.msW = 0;
				turnOnMusic1(5);
			}
			if(key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) {
				playScene.player.msS = 0;
				turnOnMusic1(5);
			}
			
			if(key == KeyEvent.VK_I) {
				isIPress = false;
				turnOnMusic1(4);
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
	public void turnOnMusic1(int i){
	      this.sound.setFile(i);
	      this.sound.playSound();
	  }
}