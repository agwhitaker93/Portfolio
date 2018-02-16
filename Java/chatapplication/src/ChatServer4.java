import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/*
 * Authors:		Andrew Whitaker & George Othen
 * Title:		Chat Server
 * Created:		05/02/2016
 * Version:		3.0
 */

public class ChatServer4 {
    //create client socket
    private Socket clientSocket;
    //create server socket
    private ServerSocket server;
    //create string to hold server input from client
    static String serverInput;
    //create integer to hold port number
    int port;
    //create array of server connection threads
    private ArrayList<ChatServerThread> clientThreadList;

    /**
     * @author Andrew Whitaker & George Othen
     * @args takes in port number for server
     * A method that creates a server
     */

    public static void main(String[] args) throws IOException {
        //create server
        ChatServer4 server;

        //inform user usage of server arguments
        if (args.length != 1)
            System.out.println("Usage: java ChatServer port");

            //intialise serverinput string, define port number as args, create new chatserver4 with port number, call method to start server
        else {
            serverInput = "";
            int port = Integer.parseInt(args[0]);
            server = new ChatServer4(port);
            server.startServer();
        }
    }

    /**
     * @port define port variable as port number
     * constructor sets port number for server use
     */

    public ChatServer4(int port) {
        this.port = port;
    }

    /**
     * Starts server and creates, intialises and starts threads to handle connections
     */

    private void startServer() {
        //defines condition of server (ON/OFF)
        boolean stopServer = false;

        try {
            //initialise list of client connection threads
            clientThreadList = new ArrayList<ChatServerThread>();
            //intialise serversocket with port number
            server = new ServerSocket(port);
            //inform user of server status through console
            System.out.println("Server started: " + server);

            //if server is still running
            while (!stopServer) {
                //inform user of server status in server console
                System.out.println("Waiting for connection...");
                //accept a connection from client socket
                clientSocket = server.accept();
                //create new Chat Server Thread for each new connection with reference to client socket and server
                ChatServerThread thread = new ChatServerThread(clientSocket, this);
                //add thread reference to array
                clientThreadList.add(thread);
                //start thread
                thread.start();
            }
        }

        //catch and print exception
        catch (IOException e) {
            System.out.println(e);
        }

    }

    /**
     * @username the username the client has chosen
     * @message the message the client has sent to the server
     * A method that prints all client messages to all other clients and server console
     */

    public void chatMessage(String username, String message) throws IOException {
        //print message to server console
        System.out.println("Message sent: " + username + message);

        //for all threads in thread list except thread with current username, send message to each connection
        for (ChatServerThread thread : clientThreadList) {
            if (thread.getLoginName() != username)
                thread.sendMessage(username + message);
        }
    }

    /**
     * @string string to write to server console
     * a method that prints strings to server console
     */

    public void writeToConsole(String string) {
        System.out.println(string);
    }
}
