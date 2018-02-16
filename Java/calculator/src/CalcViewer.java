import javax.swing.JFrame;

/*
 * Author:		Andrew Whitaker
 * Title:		CalcViewer
 * Created:		14/11/2015
 * Version:		1.0
 */

/**
 * 
 * @author FleaNovus
 *
 */
public class CalcViewer
{
	public static JFrame frame;
	
	public static void main(String[]args)
	{
		Calculator calc = new Calculator();
		frame = new CalcFrame(calc);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setVisible(true);
	}
}
