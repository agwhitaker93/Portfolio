import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JFrame;

/*
 * Authors:		Andrew Whitaker & George Othen
 * Title:		Chat Server
 * Created:		05/02/2016
 * Version:		5.0
 */

public class ChatServer5 {
    //create array for references to all threads
    private ArrayList<ChatServerThread2> clientThreadList;
    //create client socket
    private Socket clientSocket;
    //create server socket
    private ServerSocket server;
    //create string to hold messages from client
    static String serverInput;
    //create int to hold port number for server
    int port;
    //create a frame for the server gui
    static ServerFrame frame;

    //create an array to hold all usernames
    public ArrayList<ChatCredentials> credentials;

    /**
     * @author Andrew Whitaker & George Othen
     * @args takes in a port number
     * A method that creates a server and a frame for the server gui
     */

    public static void main(String[] args) throws IOException {
        //create chatserver5
        ChatServer5 server;
        //create a server frame of ServerFrame
        frame = new ServerFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        //center server frame on screen
        frame.setLocationRelativeTo(null);

        //inform user of correct usage of arguments (portnumber)
        if (args.length != 1)
            frame.UpdateServerConsole("Usage: java ChatServer port");

            //intialise serverInput string, define port as portnumber in args, create new chatserver with port number, call method that starts server
        else {
            serverInput = "";
            int port = Integer.parseInt(args[0]);
            server = new ChatServer5(port);
            server.StartServer();
        }
    }

    /**
     * @port takes in port number
     * constructor to set port integer as portnumber
     */

    public ChatServer5(int port) {
        credentials = new ArrayList<ChatCredentials>();
        this.port = port;
    }

    /**
     * A method that intialises starts the server and creates client connection threads
     */

    private void StartServer() {
        //Set condition of server
        boolean stopServer = false;
        try {
            //intialise array of client connection threads
            clientThreadList = new ArrayList<ChatServerThread2>();
            //intialise server socket with port number
            server = new ServerSocket(port);
            //print server status to gui
            frame.UpdateServerConsole("Server started: " + server);

            //if server is still running
            while (!stopServer) {
                //update server status on gui
                frame.UpdateServerConsole("Waiting for connection...");
                //accept new connections from client socket
                clientSocket = server.accept();

                //create thread for new connection with reference to the client socket & the server
                ChatServerThread2 thread = new ChatServerThread2(clientSocket, this);
                frame.UpdateServerConsole("Thread made");

                //add new client connection thread to array
                clientThreadList.add(thread);
                //start connection thread
                thread.start();
            }
        }

        //catch and print exception to server gui
        catch (IOException e) {
            frame.UpdateServerConsole(e.toString());
        }

    }

    /**
     * @username holds the username of the current client
     * @message hold the message from the current client
     * A method that sends client message to all clients
     */

    public void ChatMessage(String username, String message) throws IOException {
        //send message to server gui
        frame.UpdateServerConsole("Message sent: " + username + message);
        //for all client connection threads in array, send message to each
        for (ChatServerThread2 thread : clientThreadList) {
            thread.SendMessage(username + message);
        }
    }

    /**
     * @string holds string to send to server gui
     * a method that sends a string to the server gui
     */


    public void WriteToConsole(String string) {
        frame.UpdateServerConsole(string);
    }
}
