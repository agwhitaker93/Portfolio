import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import javax.swing.JFrame;

/*
 * Authors:		Andrew Whitaker & George Othen
 * Title:		Chat Client
 * Created:		05/02/2016
 * Version:		2.0
 */

public class ChatClient2 {
    private static PrintWriter out;
    private static Scanner in;
    static Socket clientSocket;
    private static String username;        //A String value is added to store the user's name
    private static String clientInput = "";

    public static void main(String[] args) throws IOException {
        System.out.println("Establishing connection. Please wait...");

        try {
            String server = args[0];
            int serverPort = Integer.parseInt(args[1]);
            clientSocket = new Socket(server, serverPort);

            System.out.println("Client socket created: " + clientSocket);

            out = new PrintWriter(clientSocket.getOutputStream());
            in = new Scanner(System.in);

            System.out.print("Enter Login Name: ");
            ChatListenThread t = new ChatListenThread(clientSocket);    //A thread is made to allow the user to receive messages from others
            t.start();        //The thread is then started

            username = in.nextLine();
            clientInput = username;
            out.println(clientInput);
            out.flush();

            while (!clientInput.equals(".bye") && clientInput.length() < 160)    //A new condition is added to the while, preventing the user from
            {                                                                    //Typing too much
                System.out.print(username + ">> ");
                clientInput = in.nextLine();
                out.println(clientInput);
                out.flush();
            }

            if (clientInput.length() > 159)                                    //An if statement is added to catch if the user has gone over the character limit
            {
                System.out.println("Too many characters.");
            }

            System.out.println("Disconnecting now...");
        } catch (UnknownHostException e) {
        } catch (IOException ioe) {
        } finally {
            clientSocket.close();
        }
    }

    public void printMessage(String mes) {
        System.out.println(mes);
    }
}
