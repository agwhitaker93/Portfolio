package craps;

import javax.swing.JFrame;

public class CrapsGameViewer
{
	public static JFrame frame;
	public static void main(String[]args)
	{
		System.out.println("Hello, welcome to craps!");
		CrapsGame game = new CrapsGame();
		frame = new CrapsGameFrame(game);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setVisible(true);
		System.out.println("The frame should be visible now!");
	}
}
