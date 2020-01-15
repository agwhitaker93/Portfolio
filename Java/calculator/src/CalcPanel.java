import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/*
 * Author:		Andrew Whitaker
 * Title:		CalcPanel
 * Created:		14/11/2015
 * Version:		1.0
 */

/**
 * 
 * @author FleaNovus
 *
 */
@SuppressWarnings("serial")
public class CalcPanel extends JPanel
{
	JPanel panel;
	JButton[] button = new JButton[28];
	String[] buttonText = {"MC", "MR", "MS", "M+", "M-", "del", "CE", "C", "+/-", "sqrt",
			"7", "8", "9", "/", "%", "4", "5", "6", "*", "1/x", "1", "2", "3", "-", "=",
			"0", ".", "+"};
	int y;
	int buttonNum;
	int buttonValue;
	GridBagConstraints c;
	Dimension buttonDimensions;
	Insets internalButton = new Insets(0,0,0,0);
	
	public CalcPanel()
	{
		setLayout(new GridBagLayout());
		c = new GridBagConstraints();
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.NONE;
		c.insets = new Insets(2, 2, 2, 2);
		
		y = 0;
		buttonNum = 0;
		createInitialButtons();
		
		//Next 2 rows
		int x = 0;
		for (int i = 0; i < 4; i++)
		{
			button[buttonNum] = new JButton(buttonText[buttonNum]);
			SetButtonParams(button[buttonNum], buttonDimensions, internalButton);
			c.gridx = x;
			c.gridy = y;
			add(button[buttonNum], c);
			buttonNum++;
			x++;
		}
		
		// = button
		button[buttonNum] = new JButton(buttonText[buttonNum]);
		buttonDimensions = new Dimension(36, 60);
		SetButtonParams(button[buttonNum], buttonDimensions, internalButton);
		c.gridheight = 2;
		c.gridx = 4;
		c.gridy = y;
		add(button[buttonNum], c);
		c.gridheight = 1;
		y++;
		buttonNum++;
		
		// 0 button
		button[buttonNum] = new JButton(buttonText[buttonNum]);
		buttonDimensions = new Dimension(76, 28);
		SetButtonParams(button[buttonNum], buttonDimensions, internalButton);
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = y;
		add(button[buttonNum], c);
		c.gridwidth = 1;
		buttonNum++;

		buttonDimensions = new Dimension(36, 28);
		x = 2;
		for (int i = 0; i < 2; i++)
		{
			button[buttonNum] = new JButton(buttonText[buttonNum]);
			SetButtonParams(button[buttonNum], buttonDimensions, internalButton);
			c.gridx = x;
			c.gridy = y;
			x++;
			add(button[buttonNum], c);
			buttonNum++;
		}
		
		ButtonListener buttonList = new ButtonListener();
		
		for (int i = 0; i < buttonNum; i++)
		button[i].addActionListener(buttonList);
	}
	
	/**
	 * Used throughout to create the buttons. condenses 3 lines of code down to 1
	 * @param button The specific button to be set
	 * @param dimensions The dimensions given for the new button
	 * @param inset The inset given for the new button
	 */
	private void SetButtonParams(JButton button, Dimension dimensions, Insets inset)
	{
		button.setPreferredSize(dimensions);
		button.setMinimumSize(dimensions);
		button.setMargin(inset);
		
	}
	
	/**
	 * Creates the first 20 buttons as they are all the same size and consistently placed
	 */
	private void createInitialButtons()
	{
		int x = 0;
		buttonDimensions = new Dimension(36, 28);
		
		for (int i = 0; i < 4; i++)
		{
			for (int j = 0; j < 5; j++)
			{
				button[buttonNum] = new JButton(buttonText[buttonNum]);
				SetButtonParams(button[buttonNum], buttonDimensions, internalButton);
				c.gridx = x;
				c.gridy = y;
				x++;
				add(button[buttonNum], c);
				buttonNum++;
			}
			x = 0;
			y++;
		}
	}
	
	class ButtonListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			buttonValue=e.getActionCommand();
			setCommand(Calculator.ButtonType.Number, e.getActionCommand(), buttonValue);

			switch (e.getActionCommand())
			{
			case "0":
				buttonValue = 0;
				setCommand(Calculator.ButtonType.Number, e.getActionCommand(), buttonValue);
				break;
				
			case "1":
				buttonValue = 1;
				setCommand(Calculator.ButtonType.Number, e.getActionCommand(), buttonValue);
				break;
				
			case "2":
				buttonValue = 2;
				setCommand(Calculator.ButtonType.Number, e.getActionCommand(), buttonValue);
				break;
				
			case "3":
				buttonValue = 3;
				setCommand(Calculator.ButtonType.Number, e.getActionCommand(), buttonValue);
				break;
				
			case "4":
				buttonValue = 4;
				setCommand(Calculator.ButtonType.Number, e.getActionCommand(), buttonValue);
				break;
				
			case "5":
				buttonValue = 5;
				setCommand(Calculator.ButtonType.Number, e.getActionCommand(), buttonValue);
				break;
				
			case "6":
				buttonValue = 6;
				setCommand(Calculator.ButtonType.Number, e.getActionCommand(), buttonValue);
				break;
				
			case "7":
				buttonValue = 7;
				setCommand(Calculator.ButtonType.Number, e.getActionCommand(), buttonValue);
				break;
				
			case "8":
				buttonValue = 8;
				setCommand(Calculator.ButtonType.Number, e.getActionCommand(), buttonValue);
				break;
				
			case "9":
				buttonValue = 9;
				setCommand(Calculator.ButtonType.Number, e.getActionCommand(), buttonValue);
				break;
				
			case "+":
				setCommand(Calculator.ButtonType.Operator, e.getActionCommand());
				break;
				
			case "-":
				setCommand(Calculator.ButtonType.Operator, e.getActionCommand());
				break;
				
			case "*":
				setCommand(Calculator.ButtonType.Operator, e.getActionCommand());
				break;
				
			case "/":
				setCommand(Calculator.ButtonType.Operator, e.getActionCommand());
				break;
				
			case "%":
				setCommand(Calculator.ButtonType.UnaryOperator, e.getActionCommand());
				break;
				
			case "sqrt":
				setCommand(Calculator.ButtonType.UnaryOperator, e.getActionCommand());
				break;
				
			case "1/x":
				setCommand(Calculator.ButtonType.UnaryOperator, e.getActionCommand());
				break;
				
			case "+/-":
				setCommand(Calculator.ButtonType.Inversion, e.getActionCommand());
				break;
				
			case "=":
				setCommand(Calculator.ButtonType.Operator, e.getActionCommand());
				break;
				
			case ".":
				setCommand(Calculator.ButtonType.Float, e.getActionCommand());
				break;
				
			case "del":
				setCommand(Calculator.ButtonType.Clear, e.getActionCommand());
				break;
				
			case "CE":
				setCommand(Calculator.ButtonType.Clear, e.getActionCommand());
				break;
				
			case "C":
				setCommand(Calculator.ButtonType.Clear, e.getActionCommand());
				break;
				
			case "MC":
				setCommand(Calculator.ButtonType.Memory, e.getActionCommand());
				break;
				
			case "MR":
				setCommand(Calculator.ButtonType.Memory, e.getActionCommand());
				break;
				
			case "MS":
				setCommand(Calculator.ButtonType.Memory, e.getActionCommand());
				break;
				
			case "M+":
				setCommand(Calculator.ButtonType.Memory, e.getActionCommand());
				break;
				
			case "M-":
				setCommand(Calculator.ButtonType.Memory, e.getActionCommand());
				break;
			}
		}
		
		private void setCommand(Calculator.ButtonType command, String actionCommand)
		{
			Calculator.setCommands(command, actionCommand);
		}
		
		private void setCommand(Calculator.ButtonType command, String actionCommand, int value)
		{			
			Calculator.setCommands(command, actionCommand, value);
		}
		
	}
}
