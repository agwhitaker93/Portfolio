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

public class ChatServerThread2 extends Thread {
    //create server
    ChatServer5 server;
    //create client socket
    Socket clientSocket;
    //create string to hold loginname of client
    String loginName;
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

    public ChatServerThread2(Socket sock, ChatServer5 serv) {
        //initialise clientsocket & server
        clientSocket = sock;
        server = serv;

        try {
            //initialise printwriter to send to inputstream of clientsocket
            out = new PrintWriter(clientSocket.getOutputStream());
            //intialise scanner to receive outputstream of clientsocket
            serverIn = new Scanner(clientSocket.getInputStream());
        } catch (IOException e) {
        }
    }

    /**
     * Checks the username given by the user against all of the recorded usernames
     *
     * @param username username given by the user
     * @return returns true if the username exists already, else returns false
     */
    private boolean CheckUsername(String username) {
        for (ChatCredentials arrayCredential : server.credentials) {
            if (arrayCredential.getUsername().equals(username)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Checks the password given by the user against recorded passwords
     *
     * @param username the username of the user
     * @param password the password given by the user
     * @return if the password exists in the same ChatCredential as the username return true, else return false
     */
    private boolean CheckPassword(String username, String password) {
        for (ChatCredentials arrayCredential : server.credentials) {

            if (arrayCredential.getUsername().equals(username)
                    && arrayCredential.getPassword().equals(password)) {
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

        //set login name as next string received from client
        out.println("Enter Login Name: ");
        out.flush();

        boolean validUsername = false;    //Booleans used to ensure the user's login details are valid
        boolean validPassword = false;

        while (!validUsername)    //While the user has not input a valid username
        {
            loginName = serverIn.nextLine();    //Take input from them
            validUsername = CheckUsername(loginName);    //Compare it to the list of credentials

            if (validUsername)    //If it is present, leave the loop
            {
                break;
            }

            //Otherwise prompt the user to create a new account
            out.println("You will need to register a new account.\nPlease enter your desired password: ");
            out.flush();

            //While the user has not input a valid password
            while (!validPassword) {
                String password = serverIn.nextLine();    //Take input from them

                out.println("Please re-enter the password: ");    //Then prompt them to re-enter it to ensure they entered it correctly
                out.flush();

                if (password.equals(serverIn.nextLine()))    //If the users input matches the password they input earlier
                {
                    ChatCredentials cred = new ChatCredentials(loginName, password);    //Create a new ChatCredential
                    server.credentials.add(cred);                                        //Add it to the array in the server
                    out.println("Password accepted. You may now finish logging in.");
                    out.flush();
                    validUsername = true;                                                //And leave all loops
                    validPassword = true;
                    break;
                } else                                        //Otherwise prompt the user to try again
                {
                    out.println("Invalid password. Please try again.\nPlease enter your desired password: ");
                    out.flush();
                }
            }
        }

        validPassword = false;

        out.println("Enter Password: ");
        out.flush();

        //Once the username has been confirmed, or a new account has been made

        while (!validPassword)    //While the user given password is not valid
        {
            String password = serverIn.nextLine();    //Take input from them
            validPassword = CheckPassword(loginName, password);    //Check if the password matches with that of the ChatCredential in the array with matching username

            if (validPassword)    //If the password is valid, allow the user to log in and break from the loop
            {
                out.println("Password Accepted!");
                out.flush();
                break;
            }

            out.println("Password incorrect, please try again");    //Otherwise prompt the user to re-enter their password
            out.flush();
        }

//		out.println("Password accepted.");
//		out.flush();

        //print new user connection to server gui
        server.WriteToConsole(loginName + ": Just connected");
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
                server.WriteToConsole(loginName + "'s Connection closed...");
                done = true;
                this.interrupt();
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
