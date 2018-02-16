import java.awt.*;
import java.awt.geom.*;
import java.util.*;

import javax.swing.*;

/*
 * Author:		Andrew Whitaker
 * Title:		Ball Panel
 * Created:		03/12/2015
 * Version:		1.0
 */

/**
 * @author FleaNovus
 */
@SuppressWarnings("serial")
public class BallPanel extends JPanel {
    long baseSpeed;
    static float ballSize;
    public float xCoord;
    public float yCoord;
    public ArrayList<Ellipse2D> balls;
    public ArrayList<Thread> threads;
    public int j;
    Random randNum = new Random();
    MTGFrame frame;
    Boolean paused;
    Boolean reset;
    Graphics2D g2;
    public ArrayList<Color> colour;

    /**
     * paints the components as they are animated
     */
    public void paintComponent(Graphics g) {
        g2 = (Graphics2D) g;

        this.setLayout(new BorderLayout());
        for (int i = 0; i < balls.size(); i++) //Iterates through the list of balls and paints them
        {
            g2.setPaint(colour.get(i));
            g2.fill(balls.get(i));
        }
    }

    /**
     * called from the frame to setup the panel with given values
     *
     * @param speed the base speed at which to update the balls' locations
     * @param size  the size to make the balls
     * @param frame the frame object is passed through to make communication between the panel and frame easier
     * @param pause a boolean to dictate whether to pause the animation or not
     */
    public void setup(long speed, float size, MTGFrame frame, Boolean pause) //This is necessary to initialize all of the variables without resetting them or needing a boolean
    {
        baseSpeed = speed;
        ballSize = size;
        balls = new ArrayList<Ellipse2D>();
        threads = new ArrayList<Thread>();
        colour = new ArrayList<Color>();
        j = 0;
        this.frame = frame;
        paused = pause;
        reset = false;
    }

    /**
     * adds a ball to the list of balls and generates a colour for it
     *
     * @param x         the x coordinate to spawn it at
     * @param y         the y coordinate to spawn it at
     * @param xBoundary how far right the ball can go before it is off screen
     * @param yBoundary how far up the ball can go before it is off screen
     */
    public void addBall(float x, float y, int xBoundary, int yBoundary) //Adds a ball to the ball arraylist, generates a colour to associate with it and passes the relevant values in to the Ball class
    {
        balls.add(new Ellipse2D.Float(x, y, ballSize, ballSize));
        float xDir = randNum.nextFloat() * 2 - 1;
        float yDir = randNum.nextFloat() * 2 - 1;
        Runnable r = new Ball(x, y, xDir, yDir, j, baseSpeed, this, xBoundary, yBoundary, ballSize);
        Thread t = new Thread(r);
        threads.add(t);
        t.start();
        Color col = new Color(randNum.nextInt(256), randNum.nextInt(256), randNum.nextInt(256));
        colour.add(col);
        j++;
    }

    /**
     * used to update the speed of the animation from the frame
     *
     * @param newSpeed the new speed to animate at
     */
    public void setSpeed(long newSpeed) //setters and getters are used to pass values directly from the button listener in the frame to the Ball class
    {
        baseSpeed = newSpeed;
    }

    /**
     * used in the ball class to get the speed
     *
     * @return animation speed
     */
    public long getSpeed() {
        return baseSpeed;
    }

    /**
     * used to pause and unpause the animation
     *
     * @param bool either true or false with regards to whether it is paused or not
     */
    public void setBool(Boolean bool) {
        paused = bool;
    }

    /**
     * used in the ball class to check if the animation should be paused
     *
     * @return pause status
     */
    public Boolean getBool() {
        return paused;
    }

    /**
     * interrupts each thread, clears all of the arrays and resets the integer used to track how many balls have been added
     */
    public void reset() //The purpose of this is similar to that of the setters and getter. It works as a middle ground between the frame buttons and the ball class
    { //It also resets the arraylists and iteration tracker "j"
        for (Thread t : threads) {
            t.interrupt();
        }
        threads.clear();
        balls.clear();
        colour.clear();
        j = 0;
    }

    /**
     * clears the panel
     */
    public void resetPainter() //This is called after the required 5 second delay to clear the panel
    {
        super.paintComponent(g2);
        frame.refresh();
    }

    /**
     * updates the coordinates of each ball as they are moved
     *
     * @param x     the new x coordinate
     * @param y     the new y coordinate
     * @param index the index of the ball moved
     */
    public void updateLocation(float x, float y, int index) //This is called from the ball class, updating each balls coordinates as they move
    {
        if (balls.size() > 0) {
            Ellipse2D ball = balls.get(index);

            ball = new Ellipse2D.Float(x, y, ballSize, ballSize);

            balls.set(index, ball);

            frame.refresh();
        }
    }
}
