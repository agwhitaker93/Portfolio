// Class not used
//
//import java.awt.EventQueue;
//import java.awt.Graphics2D;
//import java.util.*;
//
///*
// * Author:		Andrew Whitaker
// * Title:		Multi-Threaded Graphics Logic
// * Created:		03/12/2015
// * Version:		1.0
// */
//
//public class MTGraphics
//{
//	public final static int LEFT = -1;
//	public final static int RIGHT = 1;
//
//	public final static int DOWN = -1;
//	public final static int UP = 1;
//
//	public List<Ball> balls;
//
//	Random randNum;
//
//	public MTGraphics()
//	{
//		balls = new ArrayList<Ball>();
//		randNum = new Random();
//		int x = 0;
//		int y = 0;
//		float xDir = randNum.nextFloat() * 2 - 1;
//		float yDir = randNum.nextFloat() * 2 - 1;
//
//		EventQueue.invokeLater(new Ball(x, y, xDir, yDir));
//	}
//
//	public void updateSpeed(float newMultiplier)
//	{
//		for (int i = 0; i < balls.size(); i++)
//		{
//			balls.get(i).setSpeed(newMultiplier);
//		}
//	}
//}