//package scene;
//
//import javax.swing.JPanel;
//
//import org.json.simple.parser.ParseException;
//
//import java.awt.BorderLayout;
//import javax.swing.JButton;
//import java.awt.Color;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.io.PrintWriter;
//import java.net.Socket;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Scanner;
//import java.awt.GridBagLayout;
//import java.awt.Component;
//import java.awt.Dimension;
//
//import javax.swing.Box;
//import java.awt.GridBagConstraints;
//import java.awt.Insets;
//import javax.swing.BoxLayout;
//import javax.swing.JTextField;
//import javax.swing.JLabel;
//import java.awt.Font;
//import javax.swing.SwingConstants;
//
//public class MenuScene3 extends JPanel {
//
//	private Container container;
//	private JTextField txtNameInput;
//	
//	/**
//	 * Create the panel.
//	 */
//	public MenuScene3(Container container) {	
//		this.container = container;
//		setUI();				
//	}
//	
//	private void setUI() {
//		setBackground(Color.WHITE);
//		
//		GridBagLayout gridBagLayout = new GridBagLayout();
//		gridBagLayout.columnWidths = new int[]{300, 0, 0};
//		gridBagLayout.rowHeights = new int[]{200, 50, 0};
//		gridBagLayout.columnWeights = new double[]{2.0, 1.0, Double.MIN_VALUE};
//		gridBagLayout.rowWeights = new double[]{2.0, 1.0, Double.MIN_VALUE};
//		setLayout(gridBagLayout);
//		
//		JPanel pnlPic = new JPanel();
//		pnlPic.setBackground(Color.LIGHT_GRAY);
//		GridBagConstraints gbc_pnlPic = new GridBagConstraints();
//		gbc_pnlPic.insets = new Insets(0, 0, 5, 5);
//		gbc_pnlPic.fill = GridBagConstraints.BOTH;
//		gbc_pnlPic.gridx = 0;
//		gbc_pnlPic.gridy = 0;
//		add(pnlPic, gbc_pnlPic);
//		GridBagLayout gbl_pnlPic = new GridBagLayout();
//		gbl_pnlPic.columnWidths = new int[]{200, 0};
//		gbl_pnlPic.rowHeights = new int[]{100, 100, 0};
//		gbl_pnlPic.columnWeights = new double[]{1.0, Double.MIN_VALUE};
//		gbl_pnlPic.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
//		pnlPic.setLayout(gbl_pnlPic);
//		
//		JLabel lblHappy = new JLabel("HAPPY");
//		lblHappy.setFont(new Font("Tahoma", Font.BOLD, 35));
//		lblHappy.setForeground(Color.WHITE);
//		lblHappy.setBackground(Color.DARK_GRAY);
//		lblHappy.setHorizontalAlignment(SwingConstants.CENTER);
//		GridBagConstraints gbc_lblHappy = new GridBagConstraints();
//		gbc_lblHappy.anchor = GridBagConstraints.SOUTH;
//		gbc_lblHappy.insets = new Insets(0, 0, 5, 0);
//		gbc_lblHappy.gridx = 0;
//		gbc_lblHappy.gridy = 0;
//		pnlPic.add(lblHappy, gbc_lblHappy);
//		
//		JLabel lblHospital = new JLabel("HOSPITAL");
//		lblHospital.setHorizontalAlignment(SwingConstants.CENTER);
//		lblHospital.setFont(new Font("Tahoma", Font.BOLD, 35));
//		lblHospital.setForeground(Color.WHITE);
//		lblHospital.setBackground(Color.DARK_GRAY);
//		GridBagConstraints gbc_lblHospital = new GridBagConstraints();
//		gbc_lblHospital.anchor = GridBagConstraints.NORTH;
//		gbc_lblHospital.gridx = 0;
//		gbc_lblHospital.gridy = 1;
//		pnlPic.add(lblHospital, gbc_lblHospital);
//						
//		JPanel pnlNameInput = new JPanel();
//		pnlNameInput.setForeground(Color.BLACK);
//		pnlNameInput.setBackground(Color.GRAY);
//		GridBagConstraints gbc_pnlNameInput = new GridBagConstraints();
//		gbc_pnlNameInput.fill = GridBagConstraints.BOTH;
//		gbc_pnlNameInput.insets = new Insets(0, 0, 0, 5);
//		gbc_pnlNameInput.gridx = 0;
//		gbc_pnlNameInput.gridy = 1;
//		add(pnlNameInput, gbc_pnlNameInput);
//		GridBagLayout gbl_pnlNameInput = new GridBagLayout();
//		gbl_pnlNameInput.columnWidths = new int[] {0, 30, 0};
//		gbl_pnlNameInput.rowHeights = new int[] {10, 10};
//		gbl_pnlNameInput.columnWeights = new double[]{0.0, 0.0, 0.0};
//		gbl_pnlNameInput.rowWeights = new double[]{1.0, 1.0};
//		pnlNameInput.setLayout(gbl_pnlNameInput);
//		
//		JLabel lblNameInput = new JLabel("Name Your Dog Warrior ");
//		lblNameInput.setHorizontalAlignment(SwingConstants.CENTER);
//		lblNameInput.setFont(new Font("Tahoma", Font.BOLD, 12));
//		lblNameInput.setForeground(Color.WHITE);
//		lblNameInput.setBackground(Color.DARK_GRAY);
//		GridBagConstraints gbc_lblNameInput = new GridBagConstraints();
//		gbc_lblNameInput.anchor = GridBagConstraints.SOUTH;
//		gbc_lblNameInput.insets = new Insets(0, 0, 5, 5);
//		gbc_lblNameInput.gridx = 1;
//		gbc_lblNameInput.gridy = 0;
//		pnlNameInput.add(lblNameInput, gbc_lblNameInput);
//		
//		lblNameInput.setMinimumSize(new Dimension(300, 25));
//		lblNameInput.setPreferredSize(new Dimension(300, 25));
//		lblNameInput.setMaximumSize(new Dimension(500, 100));	
//		
//		txtNameInput = new JTextField();
//		txtNameInput.setFont(new Font("Tahoma", Font.PLAIN, 12));
//		txtNameInput.setHorizontalAlignment(SwingConstants.CENTER);
//		lblNameInput.setLabelFor(txtNameInput);
//		GridBagConstraints gbc_txtNameInput = new GridBagConstraints();
//		gbc_txtNameInput.anchor = GridBagConstraints.NORTH;
//		gbc_txtNameInput.insets = new Insets(0, 0, 0, 5);
//		gbc_txtNameInput.fill = GridBagConstraints.HORIZONTAL;
//		gbc_txtNameInput.gridx = 1;
//		gbc_txtNameInput.gridy = 1;
//		pnlNameInput.add(txtNameInput, gbc_txtNameInput);
//		txtNameInput.setColumns(10);
//		
//		txtNameInput.setMinimumSize(new Dimension(100, 25));
//		txtNameInput.setPreferredSize(new Dimension(100, 25));
//		txtNameInput.setMaximumSize(new Dimension(300, 50));
//		
//		GridBagConstraints gbc_pnlBtn = new GridBagConstraints();
//		gbc_pnlBtn.fill = GridBagConstraints.BOTH;
//		gbc_pnlBtn.insets = new Insets(0, 0, 0, 0);
//		gbc_pnlBtn.gridx = 1;
//		gbc_pnlBtn.gridy = 1;
//		
//		JPanel pnlBtn = new JPanel();
//		pnlBtn.setBackground(Color.LIGHT_GRAY);
//		add(pnlBtn, gbc_pnlBtn);
//		GridBagLayout gbl_pnlBtn = new GridBagLayout();
//		gbl_pnlBtn.columnWidths = new int[]{0};
//		gbl_pnlBtn.rowHeights = new int[]{25, -10, 25};
//		gbl_pnlBtn.columnWeights = new double[]{4.9E-324};
//		gbl_pnlBtn.rowWeights = new double[]{2.0, 0.0, 2.0};
//		pnlBtn.setLayout(gbl_pnlBtn);
//		
//		JButton btnSinglePlayer = new JButton("SinglePlayer");
//		btnSinglePlayer.setForeground(Color.WHITE);
//		btnSinglePlayer.setBackground(Color.GRAY);
//		btnSinglePlayer.setAlignmentX(Component.CENTER_ALIGNMENT);
//		GridBagConstraints gbc_btnSinglePlayer = new GridBagConstraints();
//		gbc_btnSinglePlayer.insets = new Insets(0, 0, 5, 0);
//		gbc_btnSinglePlayer.fill = GridBagConstraints.BOTH;
//		gbc_btnSinglePlayer.gridx = 0;
//		gbc_btnSinglePlayer.gridy = 0;
//		pnlBtn.add(btnSinglePlayer, gbc_btnSinglePlayer);
//		btnSinglePlayer.addActionListener(new BtnSinglePlayer());
//		
//		JButton btnMutilPlayer = new JButton("MutilPlayer");
//		btnMutilPlayer.setForeground(Color.WHITE);
//		btnMutilPlayer.setAlignmentX(Component.CENTER_ALIGNMENT);
//		btnMutilPlayer.setBackground(Color.GRAY);
//		GridBagConstraints gbc_btnMutilPlayer = new GridBagConstraints();
//		gbc_btnMutilPlayer.fill = GridBagConstraints.BOTH;
//		gbc_btnMutilPlayer.gridx = 0;
//		gbc_btnMutilPlayer.gridy = 2;
//		pnlBtn.add(btnMutilPlayer, gbc_btnMutilPlayer);
//		btnMutilPlayer.addActionListener(new BtnMutilPlayer());			
//	}
//
//	
//	class BtnSinglePlayer implements ActionListener {
//
//		@Override
//		public void actionPerformed(ActionEvent e) {
//			String name = txtNameInput.getText();
//			container.getClient().setClientName(name);
//			
//			try {
//				container.showSinglePlayerScene();
//			} catch (IOException | ParseException e1) {
//				e1.printStackTrace();
//			}			
//		}	
//	}
//	
//	class BtnMutilPlayer implements ActionListener {
//		
//		@Override
//		public void actionPerformed(ActionEvent e) {
//			String name = txtNameInput.getText();
//			container.getClient().setClientName(name);
//			
//			container.showServerSelectScene(container.getClient().getChillThread().getServerAvailable());
//		}	
//	}
//}
