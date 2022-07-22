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
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
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
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));
		
		JButton btnPause = new JButton("ll");
		btnPause.setFont(new Font("Tahoma", Font.BOLD, 30));
		btnPause.setForeground(Color.WHITE);
		btnPause.setBackground(Color.GRAY);
		btnPause.setFocusable(false);
		panel_1.add(btnPause);
		btnPause.addActionListener(new BtnPause());
		
		btnPause.setMinimumSize(new Dimension(65, 25));
		btnPause.setPreferredSize(new Dimension(65, 25));
		btnPause.setMaximumSize(new Dimension(100, 50));
		
		lblScore = new JLabel("0");
		lblScore.setHorizontalAlignment(SwingConstants.CENTER);
		lblScore.setForeground(Color.DARK_GRAY);
		lblScore.setBackground(Color.WHITE);
		panel_1.add(lblScore);
		
		lblScore.setMinimumSize(new Dimension(65, 25));
		lblScore.setPreferredSize(new Dimension(65, 25));
		lblScore.setMaximumSize(new Dimension(100, 50));
		
		counterLabel = new JLabel();
		counterLabel.setHorizontalAlignment(SwingConstants.CENTER);
		counterLabel.setForeground(Color.DARK_GRAY);
		counterLabel.setBackground(Color.WHITE);
		panel_1.add(counterLabel);
		
		counterLabel.setMinimumSize(new Dimension(65, 25));
		counterLabel.setPreferredSize(new Dimension(65, 25));
		counterLabel.setMaximumSize(new Dimension(100, 50));
		counterLabel.setText("16:00");
		second =0;
		minute =16;
		countdownTimer();
		timer.start();
		
		JLabel setAgent_ = new JLabel("Number of agents");
		JTextField setAgent = new JTextField();
		JButton app = new JButton("Apply");
		app.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int count = Integer.valueOf(setAgent.getText());
				PlayScene.setNumberOfAgents(count);
				app.setFocusable(false);
			}
		});
		setAgent.setSize(100, 50);
		panel_1.add(setAgent_);
		panel_1.add(setAgent);
		panel_1.add(app);		
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
