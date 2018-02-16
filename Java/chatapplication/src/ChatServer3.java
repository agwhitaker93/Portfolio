import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/*
 * Authors:		Andrew Whitaker & George Othen
 * Title:		Chat Server
 * Created:		05/02/2016
 * Version:		3.0
 */

public class ChatServer3 {
    //create client socket
    private Socket socket;
    //create server socket
    private ServerSocket server;
    //create string to hold serverInput
    static String serverInput;

    /**
     * @author Andrew Whitaker & George Othen
     * A method that creates a chatserver3 and take in a port number
     * @args takes in a port number for server
     */

    public static void main(String[] args) throws IOException {
        //create chatserver3
        ChatServer3 server;

        //inform user if port number wasn't defined as an argument
        if (args.length != 1)
            System.out.println("Usage: java ChatServer port");

            //initialise serverInput string, define port as portnumber in args, initialise chatserver3 with port number
        else {
            serverInput = "";
            int port = Integer.parseInt(args[0]);
            server = new ChatServer3(port);
        }
    }

    /**
     * @port the port number used for the server
     * constructor defines ChatServer3
     */

    public ChatServer3(int port) {
        try {
            //boolean to control server condition
            boolean stopServer = false;
            //intialise server socket with port number
            server = new ServerSocket(port);
            //report server status to console
            System.out.println("Server started: " + server);

            //accept connection to server through clientsocket
            while (!stopServer) {
                System.out.println("Waiting for connection...");
                socket = server.accept();

                //create thread for new connection
                new ChatServerThread(socket, this).start();
            }
        }

        //catch and print exception
        catch (IOException e) {
            System.out.println(e);
        }
    }

    /**
     * @message String that holds message from client
     * prints the message from client into server console
     */

    public void chatMessage(String message) {
        System.out.println("Message sent: " + message);
    }
}
