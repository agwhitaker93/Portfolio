import java.awt.Color;

/*
 * Author:		Andrew Whitaker
 * Title:		Ball
 * Created:		03/12/2015
 * Version:		1.0
 */

/**
 * @author FleaNovus
 */
public class Ball implements Runnable {
    float xCoord;
    float yCoord;
    float xDir;
    float yDir;
    int index;
    long speed;
    BallPanel panel;
    Boolean plusOnX;
    Boolean plusOnY;
    Boolean paused;
    int xMax;
    int yMax;
    float radius;
    Color colour;

    /**
     * constructor for the ball class
     *
     * @param x          the x coordinate to create the ball at
     * @param y          the y coordinate to create the ball at
     * @param xDirection the direction on the x axis that the ball can move along. Goes from -1 to 1
     * @param yDirection the direction on the y axis that the ball can move along. Goes from -1 to 1
     * @param index      keeps track of which ball this runnable is animating
     * @param speed      the speed at which to update the balls' position
     * @param pane       the ball panel that created the ball
     * @param xBoundary  the right boundary of the window
     * @param yBoundary  the upper boundary of the window
     * @param radius     the radius of the ball
     */
    public Ball(float x, float y, float xDirection, float yDirection, int index,
                long speed, BallPanel pane, int xBoundary, int yBoundary, float radius) {
        xCoord = x;
        yCoord = y;
        xDir = xDirection;
        yDir = yDirection;
        this.index = index;
        this.speed = speed;
        panel = pane;
        xMax = xBoundary;
        yMax = yBoundary;
        this.radius = radius;
        plusOnX = true;
        plusOnY = true;
        paused = false;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) //While the thread has not been interrupted
        {
            if (Thread.interrupted()) //if it is interrupted, pause the animation
                paused = true;

            if (!paused) //if it is not paused
            {
                if (plusOnX) //and it is set to add to the x axis
                {
                    xCoord += xDir; //add the xDir movement value to the x coordinate
                    if (xCoord + radius + 6 >= xMax || xCoord <= 0) //but if this new coordinate plus the radius makes it collide with one of the horizontal boundaries
                    {
                        plusOnX = false; //set it to decrement on the x axis
                    }
                } else //as above but decrementing
                {
                    xCoord -= xDir;
                    if (xCoord + radius + 6 >= xMax || xCoord <= 0) //and testing whether it should start incrementing
                    {
                        plusOnX = true;
                    }
                }

                if (plusOnY) //as above but on the y axis
                {
                    yCoord += yDir;
                    if (yCoord + radius + 85 >= yMax || yCoord <= 0) {
                        plusOnY = false;
                    }
                } else {
                    yCoord -= yDir;
                    if (yCoord + radius + 85 >= yMax || yCoord <= 0) {
                        plusOnY = true;
                    }
                }

                panel.updateLocation(xCoord, yCoord, index); //update the location of this ball in the panel
                speed = panel.getSpeed(); //check if the speed has been updated
                paused = panel.getBool(); //check if the user wants to pause the animation

                try //try waiting for the interval given before repeating
                {
                    Thread.sleep(speed);
                } catch (InterruptedException e) {
                    break;
                }
            } else if (paused) //if the user wants to pause the animation
            {
                paused = panel.getBool(); //check if the user wants to unpause

                try //try waiting for the interval given before repeating
                {
                    Thread.sleep(speed);
                } catch (InterruptedException e) {
                    break;
                }
            }
        }
        long start = System.currentTimeMillis(); //once the animation has been interrupted and has executed the required code
        try //start a time to check how accurate the sleep timer is, then wait for 5 seconds
        {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
        }
        long now = System.currentTimeMillis(); //after the 5 second wait, stop the timer
        System.out.println("Exiting process " + index + " after " + ((now - start) / 1000.0) + " seconds"); //print the index of the ball and the time it took to stop in seconds
        panel.resetPainter(); //then reset the panel
    }
}
