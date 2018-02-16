/*
	Author:		Andrew Whitaker
	Title:		Die
	Created:	05/03/2015
	Version:	1.0
*/

import java.util.*;

/**
 * 
 * @author FleaNovus
 *
 */
public class Die
{
	private int faceValue;
	private int sides;
	private Random generator = new Random();
	
	/**
	 * No-arg constructor gives default side value 6
	 */
	public Die()
	{
		this.sides = 6;
	}
	
	/**
	 * Constructor to give the user more flexibility in how many sides to give the die
	 * @param s value fed in by the user
	 */
	public Die(int s)
	{
		if (s >=3)
			this.sides = s;
	}
	
	/**
	 * Generates the face value by giving a random number between 1 and the value stored in sides
	 */
	public void throwDie()
	{
		this.faceValue = generator.nextInt(sides) + 1;
	}
	
	/**
	 * 
	 * @return Face Value
	 */
	public int getFaceValue()
	{
		return faceValue;
	}
	
	/**
	 * Adds the face values of two given die
	 * @param die2 the second die object given as an object
	 * @param dieCast the second die object cast to type Die
	 * @return Summed value of the die
	 */
	public int dieAdd(Object die2)
	{
		if (die2 instanceof Die)
		{
			Die dieCast = (Die) die2;
			int total = this.faceValue + dieCast.faceValue;
			return total;
		}
		
		else
			return 0;
	}
	
	@Override
	public String toString() {
		return "Die [faceValue=" + faceValue + ", sides=" + sides
				+ "]";
	}
}