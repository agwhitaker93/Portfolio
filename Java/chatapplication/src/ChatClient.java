import java.io.*;
import java.net.*;
import java.util.Scanner;

/*
 * Authors:		Andrew Whitaker & George Othen
 * Title:		Chat Client
 * Created:		05/02/2016
 * Version:		1.0
 */

public class ChatClient {

    public static void main(String[] args) throws IOException {
        Socket socket = null;    //The socket is created and instantiated
        System.out.println("Establishing connection. Please wait...");

        try {
            String server = args[0];    //The first argument given by the user is used as the server name
            int serverPort = Integer.parseInt(args[1]);        //The second argument given is used as the server port

            socket = new Socket(server, serverPort);        //A new socket is created with both given arguments

            System.out.println("Client socket created: " + socket);

            String input = "";                            //A string variable is created to hold the user input
            Scanner in = new Scanner(System.in);        //A scanner is created to take in user input from the command prompt
            PrintWriter out = new PrintWriter(socket.getOutputStream());    //A printwriter is created to send the user messages to the server

            while (!input.equals(".bye"))                //The server will continue to take user input until the user types ".bye"
            {
                input = in.nextLine();                    //Wait for user input
                out.println(input);                        //The input is buffered
                out.flush();                            //Then sent/flushed manually
            }
        } catch (UnknownHostException e) {
        } catch (IOException ioe) {
        } finally {
            socket.close();
        }
    }
}
