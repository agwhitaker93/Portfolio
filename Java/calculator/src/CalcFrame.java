import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.text.DefaultEditorKit;

/*
 * Author:		Andrew Whitaker
 * Title:		CalcFrame
 * Created:		14/11/2015
 * Version:		1.0
 */

/**
 * 
 * @author FleaNovus
 *
 */
@SuppressWarnings("serial")
public class CalcFrame extends JFrame
{
	JMenuBar menu;
	
	final static int FRAME_WIDTH = 220;
	final static int FRAME_HEIGHT = 315;
	
	static JTextField calculatorText;
	
	public CalcFrame(Calculator calc)
	{
		setTitle("Calculator");
		
		JPanel contentPanel = new JPanel();
		setContentPane(contentPanel);
		
		createComponents();
		setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		setMinimumSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		setResizable(false);
	}
	
	/**
	 * creates all of the components of the JFrame
	 */
	private void createComponents()
	{
		ActionListener menuButtons = new MenuListener();
		
		JMenuBar menuBar = new JMenuBar();
		JMenu editMenu, viewMenu, helpMenu;
		editMenu = new JMenu("Edit");
		menuBar.add(editMenu);
		viewMenu = new JMenu("View");
		menuBar.add(viewMenu);
		helpMenu = new JMenu("Help");
		menuBar.add(helpMenu);
		
		JMenuItem copy, paste, standard, scientific, digitGrouping, help, about;

		copy = new JMenuItem(new DefaultEditorKit.CopyAction());
		copy.setText("Copy");
//		copy = new JMenuItem("Copy");
//		copy.addActionListener(menuButtons);
		editMenu.add(copy);
		
		paste = new JMenuItem(new DefaultEditorKit.PasteAction());
		paste.setText("Paste");
//		paste = new JMenuItem("Paste");
//		paste.addActionListener(menuButtons);
		editMenu.add(paste);
		
		standard = new JMenuItem("Standard");
		viewMenu.add(standard);
		
		scientific = new JMenuItem("Scientific");
		viewMenu.add(scientific);
		
		viewMenu.addSeparator();
		
		digitGrouping = new JMenuItem("Digit grouping");
		digitGrouping.addActionListener(menuButtons);
		viewMenu.add(digitGrouping);
		
		help = new JMenuItem("Help Topics");
		help.addActionListener(menuButtons);
		helpMenu.add(help);
		
		helpMenu.addSeparator();
		
		about = new JMenuItem("About Calculator");
		about.addActionListener(menuButtons);
		helpMenu.add(about);
		
		setJMenuBar(menuBar);
		
		calculatorText = new JTextField(Calculator.display);
		calculatorText.setPreferredSize(new Dimension(196, 50));
		calculatorText.setHorizontalAlignment(JTextField.RIGHT);	//Sets the text to be drawn from the right
		add(calculatorText, BorderLayout.CENTER);
		
		JPanel calculatorPanel = new CalcPanel();					//Creates a CalcPanel containing all of the buttons
		add(calculatorPanel, BorderLayout.SOUTH);
	}
	
	/**
	 * Refreshes the user display
	 * @param text the new text for the display
	 */
	public static void refreshMethod(String text)
	{
		calculatorText.setText(text);
		CalcViewer.frame.revalidate();
		CalcViewer.frame.repaint();
	}
	
	class MenuListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			switch (e.getActionCommand())
			{
			case "Copy":
				Calculator.doMenuCommands(e.getActionCommand());
				break;
				
			case "Paste":
				Calculator.doMenuCommands(e.getActionCommand());
				break;
				
			case "Digit grouping":
				Calculator.doMenuCommands(e.getActionCommand());
				break;
				
			case "Help Topics":
				JOptionPane.showMessageDialog(null, "This should work almost identically to the default windows calculator.\nPlease refer to that, or send me an email, if you have any problems",
						"HELP ME", JOptionPane.INFORMATION_MESSAGE);
				break;
				
			case "About Calculator":
				JOptionPane.showMessageDialog(null, "Programmed by Andrew Whitaker\nEmail: eeu211@bangor.ac.uk", "About", JOptionPane.INFORMATION_MESSAGE);
				break;
			}
		}
		
	}
}