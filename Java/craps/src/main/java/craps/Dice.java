package craps;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class Dice
{
	
	double xPipCoord, yPipCoord, pipWidth, pipHeight;
	double xPipOffset, yPipOffset;
	Ellipse2D pipOne, pipTwo, pipThree, pipFour, pipFive, pipSix;
	Rectangle2D dieFace;
	int i = 0;
	Die newDie;
	int dieFaceValue;
	
	/**
	 * main.java.craps.Dice constructor
	 * @param xCoord base x coordinate for dieface
	 * @param yCoord base y coordinate for dieface
	 * @param width width of dieface
	 * @param height height of dieface
	 */
	public Dice(double xCoord, double yCoord, double width, double height, Die die)
	{
		dieFace = new Rectangle2D.Double(xCoord, yCoord, width, height);
		newDie = die;
		dieFaceValue = 6;
	}
	
	/**
	 * Draw method to paint the dieface, with switch statement for drawing pips given by die class
	 * @param g2 Graphics2d component passed in to the method for it to draw
	 */
	public void draw(Graphics2D g2)
	{
		g2.fill(dieFace);
		g2.setPaint(Color.WHITE);
		
		double pipSize = 0.05;
		
		pipWidth = (dieFace.getWidth() / 0.3) * pipSize;	//The original frame height is recalculated
		pipHeight = (dieFace.getHeight() / 0.3) * pipSize;	//And then the pip size is set to 3% of it
		xPipCoord = dieFace.getX() + (dieFace.getWidth()/2) - pipWidth/2;	//Then the pips are re-centered
		yPipCoord = dieFace.getY() + (dieFace.getHeight()/2) - pipHeight/2;
		
		xPipOffset = dieFace.getWidth() * 0.3;			//Offset values are set to reposition the pips for different
		yPipOffset = dieFace.getHeight() * 0.3;			//die values, which avoids the use of magic numbers
		if (CrapsGameFrame.gameInstance.firstRoll != 0)
			dieFaceValue = newDie.getFaceValue();
		
		switch (dieFaceValue)	//A switch statement is used to save space
		{
		case 1:
			onePip(g2);
			break;
		case 2:
			twoPips(g2);
			break;
		case 3:
			onePip(g2);				//Methods are reused to save me having to retype everything
			twoPips(g2);
			break;
		case 4:
			twoPips(g2);
			oppositePips(g2);
			break;
		case 5:
			onePip(g2);
			twoPips(g2);
			oppositePips(g2);			
			break;
		case 6:
			twoPips(g2);
			oppositePips(g2);
			sidePips(g2);
			break;
		}
	}
	
	/**
	 * Draws a single pip in the middle of the dieface
	 * @param g2 Graphics2d component passed in to the method for it to draw
	 */
	public void onePip(Graphics2D g2)
	{
		pipOne = new Ellipse2D.Double(xPipCoord, yPipCoord, pipWidth, pipHeight);
		g2.fill(pipOne);
	}
	
	/**
	 * Draws pips in the top right and bottom left corners of the dieface
	 * @param g2 Graphics2d component passed in to the method for it to draw
	 */
	public void twoPips(Graphics2D g2)
	{
		pipTwo = new Ellipse2D.Double(xPipCoord + xPipOffset, yPipCoord - yPipOffset, pipWidth, pipHeight);
		pipThree = new Ellipse2D.Double(xPipCoord - xPipOffset, yPipCoord + yPipOffset, pipWidth, pipHeight);
		g2.fill(pipTwo);
		g2.fill(pipThree);
	}
	
	/**
	 * Draws pips in the top left and bottom right corners of the dieface
	 * @param g2 Graphics2d component passed in to the method for it to draw
	 */
	public void oppositePips(Graphics2D g2)
	{
		pipFour = new Ellipse2D.Double(xPipCoord - xPipOffset, yPipCoord - yPipOffset, pipWidth, pipHeight);
		pipFive = new Ellipse2D.Double(xPipCoord + xPipOffset, yPipCoord + yPipOffset, pipWidth, pipHeight);
		g2.fill(pipFour);
		g2.fill(pipFive);		
	}
	
	/**
	 * Draws pips to the left and right of the center of the dieface
	 * @param g2 Graphics2d component passed in to the method for it to draw
	 */
	public void sidePips(Graphics2D g2)
	{
		pipOne = new Ellipse2D.Double(xPipCoord + xPipOffset, yPipCoord, pipWidth, pipHeight);
		pipSix = new Ellipse2D.Double(xPipCoord - xPipOffset, yPipCoord, pipWidth, pipHeight);
		g2.fill(pipOne);
		g2.fill(pipSix);
	}
}
