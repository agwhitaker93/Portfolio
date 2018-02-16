import java.io.IOException;
import java.net.*;
import java.util.Scanner;

/*
 * Authors:		Andrew Whitaker & George Othen
 * Title:		Chat Server
 * Created:		05/02/2016
 * Version:		1.0
 */

public class ChatServer {
    //create client socket
    private Socket socket;
    //create server socket
    private ServerSocket server;

    /**
     * @author Andrew Whitaker & George Othen
     * A method that creates a ChatServer and provides it with a port number
     * @args Takes in a portnumber for server
     */


    public static void main(String[] args) throws IOException {
        //create chatserver
        ChatServer server;

        //explain usage if user forgets port number
        if (args.length != 1)
            System.out.println("Usage: java ChatServer port");

            //define port number as args and initialise server
        else {
            int port = Integer.parseInt(args[0]);
            server = new ChatServer(port);
        }
    }

    /**
     * Defines the server, prints status to console
     *
     * @port the port number the server will use
     */

    public ChatServer(int port) throws IOException {
        try {
            //create the serversocket with port number
            server = new ServerSocket(port);

            //prints status
            System.out.println("Server started: " + server);
            System.out.println("Waiting for connection...");

            //allows connection from the client socket
            socket = server.accept();
            System.out.println("Socket created: " + socket);

            //declare scanner and define as input stream from the client socket
            Scanner in = new Scanner(socket.getInputStream());

            //defines boolean that keep server running
            boolean done = false;

            //if server is running, continue
            while (!done) {
                //string = messages from client
                String line = in.nextLine();
                //print the message to server console
                System.out.println(line);
                //stop server if client message is .bye
                done = line.equals(".bye");
            }
        }

        //catch and print exception
        catch (IOException e) {
            System.out.println(e);
        }

        //close the socket and inform user of status via console
        finally {
            socket.close();
            System.out.println("Connection closed...");
        }
    }
}
