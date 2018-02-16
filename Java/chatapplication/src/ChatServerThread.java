import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/*
 * Authors:		Andrew Whitaker & George Othen
 * Title:		Chat Thread
 * Created:		05/02/2016
 * Version:		1.0
 */


public class ChatServerThread extends Thread {
    //create server
    ChatServer5 server;
    //create client socket
    Socket clientSocket;
    //create string to hold loginname of client
    String loginName;
    //passwords ns stuff
    String password;
    //create int to hold portnumber
    int portNumber;
    //create string to hold client message
    String line;
    //create printwriter to send messages to client
    PrintWriter out;
    //create scanner to accept messages from client
    Scanner serverIn;
    //	int id;
    //boolean to hold servers condition
    boolean done = false;

    /**
     * @sock reference to the clientsocket
     * @serv reference to the server
     * constructor to set variable references and intialise input/output
     */

    public ChatServerThread(Socket sock, ChatServer5 serv) {
        //initialise clientsocket & server
        clientSocket = sock;
        server = serv;

        try {
            //initialise printwriter to send to inputstream of clientsocket
            out = new PrintWriter(clientSocket.getOutputStream());
            //intialise scanner to receive outputstream of clientsocket
            serverIn = new Scanner(clientSocket.getInputStream());

            //set login name as next string received from client
            out.println("Enter Login Name: ");
            out.flush();
            SetLoginName(serverIn.nextLine());
            out.println("Enter Password: ");
            out.flush();
            boolean validPassword = false;


            while (!validPassword) {
                password = serverIn.nextLine();
                validPassword = CheckCredentials(password);

                if (validPassword) {
                    out.println("Password Accepted!");
                    out.flush();
                    break;
                }
                out.println("Password not recognised, please register by typing password again");
                out.flush();

                if (password.equals(serverIn.nextLine())) {
                    server.passwords.add(password);
                    out.println("Password registered! Please log in using your new password: ");
                    out.flush();
                } else {
                    out.println("Password incorrect, please try again");
                    out.flush();
                }
            }


//			out.println("Password accepted.");
//			out.flush();

            //print new user connection to server gui
            server.WriteToConsole(loginName + ": Just connected");
        } catch (IOException e) {
        }
    }

    /**
     * @password the password the user inputs to connect to the server
     * a method to check client input password against recorded password
     */

    private boolean CheckCredentials(String password) {
        for (String arrayPassword : server.passwords) {

            if (arrayPassword.equals(password)) {
                return true;
            }
        }

        return false;
    }

    /**
     * run method of thread
     */

    @Override
    public void run() {

        System.out.println("Socket created: " + clientSocket);
        try {
            //while connection is still available
            while (!done) {
                //receive message from clientsocket
                line = serverIn.nextLine();
                //send message to server
                server.ChatMessage(loginName, ">> " + line);
                //close connection if client message is .bye
                done = line.equals(".bye");
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                //close socket
                clientSocket.close();
                //inform console of connection status
                System.out.println("Connection closed...");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /**
     * @name the loginname the client user has chosen
     * A method to set the loginname string var for current connection
     */

    public void SetLoginName(String name) {
        loginName = name;
    }

    /**
     * A method to retrieve the username of this user
     */

    public String GetLoginName() {
        return loginName;
    }

    /**
     * A method to retrieve the port of this connection
     */

    public void GetPort() {
        portNumber = clientSocket.getPort();
    }

    /**
     * @mes the message the server wants to send back to the client
     * A method to send messages back to the client
     */

    public void SendMessage(String mes) throws IOException {
        try {
            //intialise printwriter to send through clientsocket
            out = new PrintWriter(clientSocket.getOutputStream());
            //set printwriters next line and send
            out.println(mes);
            out.flush();
        } catch (IOException e) {
        }
    }
}
