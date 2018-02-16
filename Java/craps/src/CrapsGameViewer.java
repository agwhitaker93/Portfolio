import javax.swing.JFrame;

/*
	Author:		Andrew Whitaker
	Title:		CrapsGameViewer
	Created:	03/11/2015
	Version:	1.0
*/

// Link the game to the Dice stuff here!

/**
 * 
 * @author FleaNovus
 *
 */
public class CrapsGameViewer
{
	public static JFrame frame;
	public static void main(String[]args)
	{
		CrapsGame game = new CrapsGame();
		frame = new CrapsGameFrame(game);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setVisible(true);
	}
}
