package agwhitaker93;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

@SuppressWarnings("serial")
public class JOGLPanel extends JPanel implements ChangeListener {
    static JSlider pixelsToDraw;
    static JLabel pixelCount;
    static JSlider pixelBias;
    static JLabel biasCount;
    static int pixels;
    static int bias;
    static Boolean circleBool;
    static JButton startButton;
    static JButton pauseButton;
    static JButton unPauseButton;
    static JCheckBox drawCircle;
    static JCheckBox redButton;
    static JCheckBox greenButton;
    static JCheckBox blueButton;
    static JCheckBox connectType;
    static Boolean eightConnect;
    static Boolean red;
    static Boolean green;
    static Boolean blue;
    static final int PIXELS_MIN = 10;
    static final int PIXELS_MAX = 10000;
    static final int PIXELS_INIT = 500;
    static final int BIAS_MIN = 1;
    static final int BIAS_MAX = 500;
    static final int BIAS_INIT = 50;

    public JOGLPanel() {
        createComponents();
    }

    private void createComponents() {
        pixelsToDraw = new JSlider(PIXELS_MIN, PIXELS_MAX, PIXELS_INIT);
        pixelsToDraw.setName("Pixels");
        pixels = PIXELS_INIT;
        pixelsToDraw.setPreferredSize(new Dimension(100, 25));
        pixelCount = new JLabel(pixelsToDraw.getValue() + " pixels", JLabel.CENTER);

        pixelBias = new JSlider(BIAS_MIN, BIAS_MAX, BIAS_INIT);
        pixelBias.setName("Bias");
        bias = BIAS_INIT;
        pixelBias.setPreferredSize(new Dimension(100, 25));
        biasCount = new JLabel("1 in " + pixelBias.getValue() + " probability to center");

        startButton = new JButton("Start");
        pauseButton = new JButton("Pause");
        unPauseButton = new JButton("Unpause");
        pixelsToDraw.addChangeListener(this);
        pixelBias.addChangeListener(this);

        circleBool = false;
        drawCircle = new JCheckBox("Draw Circle");
        drawCircle.setSelected(circleBool);

        red = false;
        redButton = new JCheckBox("Red");
        redButton.setSelected(red);

        green = false;
        greenButton = new JCheckBox("Green");
        greenButton.setSelected(green);

        blue = false;
        blueButton = new JCheckBox("Blue");
        greenButton.setSelected(blue);

        eightConnect = false;
        connectType = new JCheckBox("Eight Connect");
        connectType.setSelected(eightConnect);

        add(pixelCount);
        add(pixelsToDraw);
        add(pixelBias);
        add(biasCount);

        ButtonListener listener = new ButtonListener();

        startButton.addActionListener(listener);
        pauseButton.addActionListener(listener);
        unPauseButton.addActionListener(listener);
        drawCircle.addActionListener(listener);
        redButton.addActionListener(listener);
        greenButton.addActionListener(listener);
        blueButton.addActionListener(listener);
    }

    public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider) e.getSource();
        String sliderName = source.getName();
        if ("Pixels".equals(sliderName)) {
            pixels = (int) source.getValue();
            pixelCount.setText(pixels + " pixels");
        }

        if ("Bias".equals(sliderName)) {
            bias = (int) source.getValue();
            biasCount.setText("1 in " + pixelBias.getValue() + " probability to center");
        }
    }

    class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()) {
                case "Start":
                    GLEventListener.buttons(e.getActionCommand(), pixels, bias, circleBool, red, green, blue, eightConnect);
                    break;

                case "Pause":
                    GLEventListener.buttons(e.getActionCommand(), pixels, bias, circleBool, red, green, blue, eightConnect);
                    break;

                case "Unpause":
                    GLEventListener.buttons(e.getActionCommand(), pixels, bias, circleBool, red, green, blue, eightConnect);
                    break;

                case "Draw Circle":
                    if (circleBool)
                        circleBool = false;

                    else
                        circleBool = true;

                    GLEventListener.buttons(e.getActionCommand(), pixels, bias, circleBool, red, green, blue, eightConnect);
                    break;

                case "Red":
                    if (red)
                        red = false;

                    else
                        red = true;

                    GLEventListener.buttons(e.getActionCommand(), pixels, bias, circleBool, red, green, blue, eightConnect);
                    break;

                case "Green":
                    if (green)
                        green = false;

                    else
                        green = true;

                    GLEventListener.buttons(e.getActionCommand(), pixels, bias, circleBool, red, green, blue, eightConnect);
                    break;

                case "Blue":
                    if (blue)
                        blue = false;

                    else
                        blue = true;

                    GLEventListener.buttons(e.getActionCommand(), pixels, bias, circleBool, red, green, blue, eightConnect);
                    break;

                case "Eight Connect":
                    if (eightConnect)
                        eightConnect = false;

                    else
                        eightConnect = true;

                    GLEventListener.buttons(e.getActionCommand(), pixels, bias, circleBool, red, green, blue, eightConnect);
                    break;
            }
        }
    }
}
