import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/*
 * Authors:		Andrew Whitaker & George Othen
 * Title:		Chat Server
 * Created:		05/02/2016
 * Version:		2.0
 */

public class ChatServer2 {
    //create client socket
    private Socket socket;
    //create server socket
    private ServerSocket server;
    //create string to hold server input
    static String serverInput;

    /**
     * A method that creates a server and provides it with a port number
     *
     * @author Andrew Whitaker & George Othen
     * @args takes in a port number for server
     */

    public static void main(String[] args) throws IOException {
        //create chatserver
        ChatServer2 server;

        //explain usage to user if port number argument isn't provided
        if (args.length != 1)
            System.out.println("Usage: java ChatServer port");

            //initialise server input string, define port as args, initialise chatserver with port number
        else {
            serverInput = "";
            int port = Integer.parseInt(args[0]);
            server = new ChatServer2(port);
        }
    }

    /**
     * @port the port number the chatserver will use
     * constructor defines chatserver
     */

    public ChatServer2(int port) throws IOException {
        try {
            //define boolean to control servers condition
            boolean stopServer = false;
            //intialise server socket with port number variable
            server = new ServerSocket(port);

            //if server is still running
            while (!stopServer) {
                //print server status
                System.out.println("Server started: " + server);
                System.out.println("Waiting for connection...");
                //accept a connection from client socket
                socket = server.accept();
                System.out.println("Socket created: " + socket);

                //create and initialise scanner to accept input stream from client socket
                Scanner in = new Scanner(socket.getInputStream());

                //create and initialise scanner to accept input from console
                Scanner serverIn = new Scanner(System.in);

                //boolean to control whether server console accepts input
                boolean done = false;

                //if server is still accepting input
                while (!done) {
                    //string line is string from client socket
                    String line = in.nextLine();
                    //print line on server console
                    System.out.println(line);
                    //server stops accepting input from client is .bye
                    done = line.equals(".bye");
                }
            }
        }

        //catch and print exception
        catch (IOException e) {
            System.out.println(e);
        }

        //close socket and inform user of status in server console
        finally {
            socket.close();
            System.out.println("Connection closed...");
        }
    }
}
