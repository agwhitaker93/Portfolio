import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/*
 * Author:		Andrew Whitaker
 * Title:		Multi-Threaded Graphics Frame
 * Created:		03/12/2015
 * Version:		1.0
 */

/**
 * @author FleaNovus
 */
@SuppressWarnings("serial")
public class MTGFrame extends JFrame implements ChangeListener {
    public final static int LEFT = -1;
    public final static int RIGHT = 1;

    public final static int DOWN = -1;
    public final static int UP = 1;

    final static int FRAME_WIDTH = 600;
    final static int FRAME_HEIGHT = 500;

    final static int SPEED_MIN = 1;
    final static int SPEED_MAX = 5;
    final static int SPEED_INIT = 3;

    JButton start;
    JButton pause;
    Boolean paused;
    JButton stop;
    JSlider speed;
    JCheckBox collision;
    Boolean enableCollisions;
    BallPanel balls;
    long interval;
    long intervalHolder;
    int totalPix;

    /**
     * no-arg constructor to initialize all of the variables with default values
     */
    public MTGFrame() {
        setTitle("Multi-Threaded Graphics");

        paused = false; //This boolean is used to dictate whether the program should pause or not

        intervalHolder = 7; //An interval of 7 ensure all of the speeds look smooth when animated
        interval = intervalHolder * 3; //The value is multiplied by 3, the middle value, because the slider initialises on 3

        createComponents();

        setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        setMinimumSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        setResizable(false); //Because the balls will be bouncing off the window dimensions, I have decided to disable resizing
    }

    /**
     * called in the constructor to create all of the components with default values
     */
    void createComponents() {
        balls = new BallPanel(); //This is the panel that actually draws the balls and animates them.
        balls.setup(interval, 10, this, paused);    //Because the ball panel will be painting components, it is necessary to invoke a setup method to initialise all of the variables
        add(balls);

        JPanel ui = new JPanel(); //A second panel is made for all of the UI elements

        start = new JButton("Start");
        pause = new JButton("Pause");
        stop = new JButton("Stop");
        speed = new JSlider(SPEED_MIN, SPEED_MAX, SPEED_INIT);
        speed.setName("Speed");
        speed.setMajorTickSpacing(1);
        speed.setPaintTicks(true);
        speed.setPaintLabels(true);
        speed.addChangeListener(this);
        collision = new JCheckBox("Collisions");

        ButtonListener bListener = new ButtonListener();

        start.addActionListener(bListener);
        pause.addActionListener(bListener);
        stop.addActionListener(bListener);
        collision.addActionListener(bListener);
        enableCollisions = false;

        ui.add(start);
        ui.add(pause);
        ui.add(stop);
        ui.add(speed);
        ui.add(collision);

        add(ui, BorderLayout.SOUTH);
    }

    /**
     * revalidates and repaints the frame
     */
    public void refresh() //This method is called in the ball panel class
    {
        revalidate();
        repaint();
    }

    class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String buttonPressed = e.getActionCommand();

            switch (buttonPressed) {
                case "Start":
                    balls.addBall(300.0f, 190.0f, FRAME_WIDTH, FRAME_HEIGHT); //Adds another ball at the center, providing the dimensions of the frame
                    break;

                case "Pause":
                    if (paused) //Becaues this button both pauses and unpauses, it is necessary to alternate using either an if/else or switch
                    {
                        paused = false;
                        balls.setBool(paused);
                    } else {
                        paused = true;
                        balls.setBool(paused);
                    }
                    break;

                case "Stop":
                    balls.reset(); //The stop button calls the reset method in the ball panel
                    break;

                case "Collisions": //I couldn't figure out how to implement this, so all it does is announce what it would do if it had the capabilities
                    if (enableCollisions) {
                        enableCollisions = false;
                    } else {
                        enableCollisions = true;
                    }

                    System.out.println("Enable collisions: " + enableCollisions);
                    break;
            }
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider) e.getSource();

        if (!source.getValueIsAdjusting()) {
            int speed = (int) source.getValue();

            switch (speed) {
                case 1:
                    interval = intervalHolder * 5; //The values from the slider are used to set the sleep duration
                    break;

                case 2:
                    interval = intervalHolder * 4;
                    break;

                case 3:
                    interval = intervalHolder * 3;
                    break;

                case 4:
                    interval = intervalHolder * 2;
                    break;

                case 5:
                    interval = intervalHolder * 1;
                    break;

                default:
                    break;
            }
            balls.setSpeed(interval);
        }
    }
}
