package scene;

import javax.swing.JPanel;
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
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.SpringLayout;

import org.json.simple.parser.ParseException;

import java.awt.Font;

public class WinScene extends JPanel {

	public Container container;
	
	/**
	 * Create the panel.
	 */
	public WinScene(Container container) {
		this.container = container;
		setUp();
	}
	
	public void setUp() {
		setBackground(Color.PINK);
		setLayout(new BorderLayout(0, 0));
		
		JButton replay = new JButton("REPLAY");
		replay.setFont(new Font("Tahoma", Font.PLAIN, 20));
		replay.setForeground(Color.BLACK);
		replay.setBackground(Color.WHITE);
		add(replay, BorderLayout.CENTER);
		
		replay.addMouseListener(new CustomMouseListener());
	}
	
	
	class CustomMouseListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
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
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			
		}
		
	}
}
