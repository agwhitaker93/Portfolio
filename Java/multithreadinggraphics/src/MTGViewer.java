import java.awt.EventQueue;

import javax.swing.JFrame;

/*
 * Author:		Andrew
 * Title:		Multi-Threaded Graphics Viewer
 * Created:		03/12/2015
 * Version:		1.0
 */

public class MTGViewer {
    public static JFrame frame;

    public static void main(String[] args) {
        frame = new MTGFrame();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                frame.setVisible(true);
            }
        });
    }
}
