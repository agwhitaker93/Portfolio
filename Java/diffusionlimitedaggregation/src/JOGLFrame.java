import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.Animator;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @author cgray
 */

@SuppressWarnings("serial")
public class JOGLFrame extends JFrame {
    public final static int SIZE_X = 600;
    public final static int SIZE_Y = 600;

    static JTextField pixelCount;
    static Animator animator = null;

    static JOGLFrame instance;

    public JOGLFrame() throws HeadlessException {
        super("eeu211 - DLA");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GLProfile glprofile = GLProfile.getDefault();
        GLCapabilities glcapabilities = new GLCapabilities(glprofile);
        final GLCanvas glcanvas = new GLCanvas(glcapabilities);
        glcanvas.setPreferredSize(new Dimension(SIZE_X, SIZE_Y));
        glcanvas.addGLEventListener(new GLEventListener());
        animator = new Animator(glcanvas);
        getContentPane().add(glcanvas);
        pack();
        centerWindow(this);
        createComponents();
    }

    void createComponents() {
        pixelCount = new JTextField(GLEventListener.pixelCount);
        pixelCount.setPreferredSize(new Dimension(100, 25));
        pixelCount.setHorizontalAlignment(JTextField.CENTER);
        add(pixelCount, BorderLayout.NORTH);

        JPanel panel = new JOGLPanel();
        add(panel, BorderLayout.SOUTH);

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(JOGLPanel.startButton);
        menuBar.add(JOGLPanel.pauseButton);
        menuBar.add(JOGLPanel.unPauseButton);
        menuBar.add(JOGLPanel.drawCircle);
        menuBar.add(JOGLPanel.redButton);
        menuBar.add(JOGLPanel.greenButton);
        menuBar.add(JOGLPanel.blueButton);
        menuBar.add(JOGLPanel.connectType);

        setJMenuBar(menuBar);
    }

    public static void refreshCounter(String text) {
        pixelCount.setText(text);
        pixelCount.revalidate();
        pixelCount.repaint();
    }

    public void centerWindow(Component frame) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = frame.getSize();

        if (frameSize.width > screenSize.width) frameSize.width = screenSize.width;
        if (frameSize.height > screenSize.height) frameSize.height = screenSize.height;

        frame.setLocation(
                (screenSize.width - frameSize.width) >> 1,
                (screenSize.height - frameSize.height) >> 1
        );
    }

    public static void main(String[] args) {
        instance = new JOGLFrame();

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                instance.setVisible(true);
            }
        });

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                animator.start();
            }
        });
    }
}