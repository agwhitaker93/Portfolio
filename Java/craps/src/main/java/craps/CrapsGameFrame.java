package craps;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class CrapsGameFrame extends JFrame
{
	JPanel scorePanel;
	JLabel scoreTracker;
	JButton resetButton;
	JButton rollButton;
	public static CrapsGame gameInstance;
	final static int FRAME_WIDTH = 600;
	final static int FRAME_HEIGHT = 400;
	
	/**
	 * Constructor for the game frame. Instantiates a game instance,
	 * calls createComponents and sets the frame size
	 * @param game object fed in from main.java.craps.CrapsGameViewer
	 */
	public CrapsGameFrame(CrapsGame game)
	{
		gameInstance = game;
		createComponents();
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
	}
	
	/**
	 * Creates all of the components to be added to the game frame.
	 */
	private void createComponents()
	{
		scorePanel = new JPanel();
		scorePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		scoreTracker = new JLabel("sum = 0 point = 0");
		scorePanel.add(scoreTracker);
		add(scorePanel,BorderLayout.NORTH);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		rollButton = new JButton("Roll!");
		resetButton = new JButton("Reset?");
		ActionListener rollListener = new RollButtonListener();
		ActionListener resetListener = new ResetButtonListener();
		rollButton.addActionListener(rollListener);
		resetButton.addActionListener(resetListener);
		buttonPanel.add(rollButton);
		buttonPanel.add(resetButton);
		resetButton.setEnabled(false);
		add(buttonPanel,BorderLayout.SOUTH);
		
		JPanel panel = new DiceFacePanel();
		
		add(panel);
	}
	
	/**
	 * 
	 * @author FleaNovus
	 *
	 */
	class RollButtonListener implements ActionListener
	{
		
		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			String scoreText = "";
			
			switch (CrapsGameFrame.gameInstance.firstRoll)
			{
			case 0:
				scoreText = gameInstance.doFirstRoll();
				break;
			case 1:
				scoreText = gameInstance.rollAgain();
				break;
			}
			scoreTracker.setText(scoreText);
			revalidate();
			repaint();
			
			if (gameInstance.isOver())
			{
				resetButton.setEnabled(true);
				rollButton.setEnabled(false);
				if (gameInstance.isWon())
				{
					JOptionPane.showMessageDialog(null, "Winner!");
				}
				
				else JOptionPane.showMessageDialog(null, "Loser!");
			}
		}
	}
	
	/**
	 * 
	 * @author FleaNovus
	 *
	 */
	class ResetButtonListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e)
		{
			CrapsGameViewer.frame.dispose();
			
			CrapsGameViewer.main(null);
		}
	}
}
