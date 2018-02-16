/*
	Author:		Andrew Whitaker
	Title:		CrapsGameTester
	Created:	03/11/2015
	Version:	1.0
*/

// Game Logic Tester goes here!

public class CrapsGameTester
{
	public static void main(String args[])
	{
	 CrapsGame game = new CrapsGame();

	 String message = game.doFirstRoll();
	 System.out.println(message);

	 while(!game.isOver())
	 {
	 message = game.rollAgain();
	 System.out.println(message);
	 }

	 if(game.isWon())
	 System.out.println("You win, big man!");
	 else
	 System.out.println("Loser ...");
	}
}
