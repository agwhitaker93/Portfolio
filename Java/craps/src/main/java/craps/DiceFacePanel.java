package craps;

import java.awt.*;

import javax.swing.*;

public class DiceFacePanel extends JPanel
{
	JLabel scoreTracker;
	int tester = 0;
	JPanel scorePanel;
	
	/**
	 * paintComponent is called by Swing when it is needed.
	 */
	public void paintComponent (Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		
		this.setLayout(new BorderLayout());
		
		double dieSize = 150;
		double dieWidth = dieSize;
		double dieHeight = dieSize;
		double xDieCoord = getWidth()/2 - dieWidth/2;
		double yDieCoord = getHeight()/2 - dieHeight/2;

		Dice die1 = new Dice(xDieCoord - 150, yDieCoord, dieWidth, dieHeight, CrapsGame.dieRoller1);
		Dice die2 = new Dice(xDieCoord + 150, yDieCoord, dieWidth, dieHeight, CrapsGame.dieRoller2);
		g2.setPaint(Color.BLACK);
		die1.draw(g2);
		g2.setPaint(Color.RED);
		die2.draw(g2);
	}
	
	
}
