import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/*
 * Authors:		Andrew Whitaker & George Othen
 * Title:		Chat Client
 * Created:		05/02/2016
 * Version:		3.0
 */

public class ChatClient3 {
    private static PrintWriter out;
    static Socket clientSocket;
    public static boolean keepRunning = true;

    static ClientFrame frame;

    public static void main(String[] args) throws IOException {
        frame = new ClientFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.UpdateChat("Establishing connection. Please wait...");    //Method called to add messages to the GUI

        try {
            String server = args[0];
            int serverPort = Integer.parseInt(args[1]);
            clientSocket = new Socket(server, serverPort);
            frame.UpdateChat("Client socket created: " + clientSocket);

            out = new PrintWriter(clientSocket.getOutputStream());

            frame.Setup(out);    //The printwriter is sent to the frame to make sending messages easier

            ChatListenThread t = new ChatListenThread(clientSocket, frame);        //A new thread is made to receive messages
            t.start();

            frame.addWindowListener(new WindowAdapter()            //A window listener is added to close the socket and interrupt the listener thread when the user closes the window
            {
                public void windowClosing(WindowEvent e) {
                    try {
                        clientSocket.close();
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }

                    t.interrupt();
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                }
            });

            while (keepRunning) {
            }
        } catch (UnknownHostException e) {
        } catch (IOException ioe) {
        } finally {
            clientSocket.close();
            System.exit(0);
        }
    }

    public void PrintMessage(String mes) {
        frame.UpdateChat(mes);
    }

    /**
     * Called from the frame to disconnect the client from the server
     */
    public static void DisconnectClient() throws IOException {
        clientSocket.close();
    }
}
