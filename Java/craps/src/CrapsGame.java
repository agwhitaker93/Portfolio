/*
	Author:		Andrew Whitaker
	Title:		CrapsGame
	Created:	03/11/2015
	Version:	1.0
*/

// Game Logic goes here!

/**
 * 
 * @author FleaNovus
 *
 */
public class CrapsGame
{
	public static Die dieRoller1;
	public static Die dieRoller2;
	public int firstRoll;
	public int dieSum;
	public int point;
	private int[] finishValues = {7, 11, 2, 3, 12};
	
	/**
	 * No-arg constructor sets variable firstRoll to 0 and instantiates 2 Die objects
	 */
	public CrapsGame()
	{
		firstRoll = 0;
		dieRoller1 = new Die();
		dieRoller2 = new Die();
	}
	
	/**
	 * Rolls both die for the first time. Makes use of the dieAdd method from the Die class.
	 * @return The sum, currently 0, and point, which is set in this method.
	 */
	public String doFirstRoll()
	{
		dieRoller1.throwDie();
		dieRoller2.throwDie();
		point = dieRoller2.dieAdd(dieRoller1);
		return "sum = " + dieSum + " point = " + point;
	}
	
	/**
	 * Rolls both die again, this time setting dieSum instead of point.
	 * @return The sum, which is set in this method, and point, which is set in doFirstRoll.
	 */
	public String rollAgain()
	{
		dieRoller1.throwDie();
		dieRoller2.throwDie();
		dieSum = dieRoller2.dieAdd(dieRoller1);
		
		return "sum = " + dieSum + " point = " + point;
	}
	
	/**
	 * Used to test if the craps game is over, which is useful when determining if the player won or not.
	 * @return A boolean value of true if any of the game over conditions are met, or false otherwise.
	 */
	public boolean isOver()
	{
		System.out.println(firstRoll);
		switch(firstRoll)
		{
		case 0:
			for (int i = 0; i < finishValues.length; i++)
			{
				if (point == finishValues[i])
				{
					return true;
				}
			}
			firstRoll = 1;
			break;
			
		case 1:
			if (dieSum == point || dieSum == 7)
				return true;
			break;
		}
		
		return false;
	}
	
	/**
	 * Used to test if the game was won or not. 
	 * @return A boolean value of true if any of the win conditions are met, or false otherwise.
	 * It is safe to return false if none of the win conditions are met because isWon will
	 * ideally not be called if the game is not over.
	 */
	public boolean isWon()
	{
		switch(firstRoll)
		{
		case 0:
			if (point == 7 || point == 11)
			{
				return true;
			}
			break;
			
		case 1:
			if (dieSum == point)
				return true;
			else if (dieSum == 7)
				return false;
			break;
		}
		
		return false;
	}
}
