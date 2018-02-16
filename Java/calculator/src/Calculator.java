/*
 * Author:		Andrew Whitaker
 * Title:		Calculator
 * Created:		14/11/2015
 * Version:		1.0
 */

/**
 * 
 * @author FleaNovus
 *
 */
public class Calculator
{
	public static String display;				//public String used to update the user display
	public static String operatorDisplay;		//a secondary display used when there are two values to be displayed
	public static ButtonType command;			//this is set by the type of button the user presses
	public static int valueGiven;				//this is the value of the button selected by the user
	
	private static String button;				//the name of the button is stored
	private static String previousButton;		//the name of the previous button is stored
	private static int decimalCount;			//keeps a record of how many decimal places there are
	private static float firstValue;			//the first value to be displayed
	private static float secondValue;			//second value to be displayed
	private static float storedValue;			//a third value used when manipulating values
	private static float memory;				//stores user defined memory
	private static Boolean pastFirst;			//defines whether the program has past the first operator
	private static Boolean invert;				//defines whether the value is to be inverted
	private static Boolean firstLastChanged;	//defines whether firstValue or secondValue were changed last
	private static Boolean digitGrouping;		//used to enable and disabled digit grouping
	
	/**
	 * Default, no-arg constructor
	 */
	public Calculator()
	{
		display = "0.0 ";
		resetProgram();
	}
	
	/**
	 * Method called when the user presses a button
	 */
	public static void calculate()
	{
		if (display == "0")
		{
			if (button == "0")
			{
				return;
			}
		}
		
		switch (command)
		{
		case Number:
			if (decimalCount > 1)
			{
				for (int i = 0; i < decimalCount; i++)
					storedValue *= 10;
			}
			
			if (!invert)
				secondValue = storedValue + valueGiven;
			
			else
				secondValue = storedValue - valueGiven;

			firstLastChanged = false;
			
			for (int i = 0; i < decimalCount; i++)
			{
				secondValue /= 10;
			}
				
			if (decimalCount > 0)
				decimalCount++;

			storedValue = secondValue;
			updateDisplay(button);
			
			if (decimalCount < 1)
				storedValue *= 10;
			break;
			
		case Operator:
			if (pastFirst)
			{
				decimalCount = 0;
				firstValue = operateOnValues(firstValue, secondValue, previousButton);
				firstLastChanged = true;
			}
			
			else
			{
				decimalCount = 0;
				firstValue = secondValue;
				firstLastChanged = true;
				pastFirst = true;
			}

			previousButton = button;
			secondValue = 0;
			operatorDisplay = firstValue + " " + button + " ";
			updateDisplay(button);

			storedValue = 0;
			invert = false;
			break;
			
		case UnaryOperator:
			if (button != "%")
			{
				firstValue = secondValue;
				firstLastChanged = true;
			}
			
			secondValue = operateUnaryValues(secondValue, firstValue, button);
			firstLastChanged = false;
			
			updateDisplay(button);
			break;
			
		case Memory:
			if (!firstLastChanged)
				firstValue = secondValue;
			
			memory = operateOnMemory(firstValue, button);
			break;
			
		case Clear:
			secondValue = clearSomething(secondValue, button);
			firstLastChanged = false;
			storedValue = secondValue * 10;
			updateDisplay(button);
			break;
			
		case Float:
			decimalCount = 1;
			break;
			
		case Inversion:
			secondValue = 0 - secondValue;
			firstLastChanged = false;
			storedValue = 0 - storedValue;
			updateDisplay(button);
			invert = true;
			break;
		}
		CalcFrame.refreshMethod(display);
	}
	
	/**
	 * Performs operations on the memory
	 * @param value1 a value, normally firstValue, to be stored, added or subtracted
	 * @param commandGiven used to differentiate which command is being requested
	 * @return the new value for memory
	 */
	private static float operateOnMemory(float value1, String commandGiven)
	{
		switch(commandGiven)
		{
		case "MC":
			return 0;
			
		case "MR":
			display = memory + " ";
			clearSomething(0, "C");
			return memory;
			
		case "MS":
			return value1;
			
		case "M+":
			return memory + value1;
			
		case "M-":
			return memory - value1;
		}
		
		return value1;
	}
	
	/**
	 * Clears a specific part of the current calculator instance
	 * @param value1 only used if del is pressed. defines which value needs to be manipulated
	 * @param commandGiven used to differentiate which command is being requested
	 * @return only really used when del is called
	 */
	private static float clearSomething(float value1, String commandGiven)
	{
		switch (commandGiven)
		{
		case "CE":
			resetProgram();
			return 0;
			
		case "C":
			float tempMem = memory;
			resetProgram();
			memory = tempMem;
			return 0;
			
		case "del":
			if (decimalCount > 0)
			{
				for (int i = 0; i < decimalCount; i++)
				{
					value1 *= 10;
				}
			}
			
			float temp = value1 % 10;
			value1 -= temp;
			
			if (decimalCount > 0)
			{
				for (int i = 0; i < decimalCount; i++)
				{
					value1 /= 10;
				}
				decimalCount--;
			}

			else
				value1 /= 10;
			
			return value1;
		}
		
		return 1.01010101010f;
	}
	
	/**
	 * Operates based on the operator selected by the user
	 * @param value1 the first value entered by the user
	 * @param value2 the second value entered by the user
	 * @param commandGiven used to differentiate which command is being requested
	 * @return returns the new value after the operation is executed
	 */
	private static float operateOnValues(float value1, float value2, String commandGiven)
	{
		
		switch (commandGiven)
		{
		case "+":
			return value1 + value2;
		case "-":
			return value1 - value2;
		case "*":
			return value1 * value2;
		case "/":
			return value1 / value2;
		case "=":
			return value1;
		case "+/-":
			return -value1;
		}
		return 0;
	}
	
	/**
	 * Operates based on the unary operator selected
	 * @param value1 the first value input by the user
	 * @param value2 the second value input by the user. only used when % is used
	 * @param commandGiven the unary operator selected by the user
	 * @return
	 */
	private static float operateUnaryValues(float value1, float value2, String commandGiven)
	{
		switch (commandGiven)
		{
		case "sqrt":
			value1 = (float) Math.sqrt(value1);
			return value1;
			
		case "%":
			value1 = value2 * (value1 / 100);
			return value1;
			
		case "1/x":
			value1 = 1 / value1;
			return value1;
		}
		
		return value1;
	}
	
	/**
	 * refreshes the user display
	 * @param commandGiven the display is different if the user pressed =
	 */
	private static void updateDisplay(String commandGiven)
	{
		if (commandGiven == "=")
		{
			display = "= " + firstValue + " ";
			resetProgram();
		}
			
		else
			display = operatorDisplay + secondValue + " ";
	}
	
	/**
	 * Resets all of the variables
	 */
	private static void resetProgram()
	{
		operatorDisplay = "";
		firstValue = 0;
		secondValue = 0;
		storedValue = 0;
		memory = 0;
		pastFirst = false;
		invert = false;
		decimalCount = 0;
		firstLastChanged = false;
		digitGrouping = true;
	}
	
	/**
	 * Used by the Calc* classes to pass values to this class
	 * @param userCommand the command type of the button pressed
	 * @param name the name of the button pressed
	 */
	public static void setCommands(ButtonType userCommand, String name)
	{
		command = userCommand;
		button = name;
		calculate();
	}
	
	/**
	 * The same as the above method, but with another value
	 * @param userCommand as above
	 * @param name as above
	 * @param userValue the numerical value of the button pressed
	 */
	public static void setCommands(ButtonType userCommand, String name, int userValue)
	{
		command = userCommand;
		button = name;
		valueGiven = userValue;
		calculate();
	}
	
	/**
	 * Used to implement copy, paste and digit grouping
	 * @param command the menu option selected by the user
	 */
	public static void doMenuCommands(String command)
	{
		switch (command)
		{
		case "Copy":
			break;
			
		case "Paste":
			break;
			
		case "Digit grouping":
			break;
		}
	}
	
	public enum ButtonType
	{
		Number, Operator, UnaryOperator, Memory, Clear, Float, Inversion
	}
}