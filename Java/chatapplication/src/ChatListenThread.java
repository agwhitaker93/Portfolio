import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ChatListenThread extends Thread {
    Socket socket;
    Scanner servIn;
    ClientFrame frame;

    /**
     * Creates a thread to allow chat client users to receive messages from other users
     *
     * @param socket this variable was used in a previous iteration of this method to receive messages from the server
     * @param frame  replaces the socket variable, as it is needed to print received messages to the GUI
     */
    public ChatListenThread(Socket socket, ClientFrame frame) {
        this.socket = socket;
        this.frame = frame;
        try {
            servIn = new Scanner(socket.getInputStream());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * The thread will wait for messages from the server, then update the chat box on the client's GUI
     */
    public void run() {
        while (true) {
            String serverResponse = servIn.nextLine();
            frame.UpdateChat(serverResponse);
        }
    }
}
