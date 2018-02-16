import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.*;
import javax.swing.text.*;

@SuppressWarnings("serial")
public class ClientFrame extends JFrame {
    final static int FRAME_WIDTH = 500;
    final static int FRAME_HEIGHT = 300;
    JPanel chatPanel, inputPanel;
    JTextArea chatBox;
    JScrollPane scrollPane;
    JTextField textField;
    JButton sendMessage;
    DefaultCaret caret;

    PrintWriter output;

    /**
     * Default constructor for the frame
     * The title is set, CreateComponents is called and the size of the frame is set
     */
    public ClientFrame() {
        setTitle("Chatroom Client");
        CreateComponents();
        setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        setMinimumSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        setResizable(false);
    }

    /**
     * Creates all of the components of the frame
     */
    public void CreateComponents() {
        chatPanel = new JPanel();
        inputPanel = new JPanel(new FlowLayout());
        chatBox = new JTextArea();
        scrollPane = new JScrollPane(chatBox);
        scrollPane.setPreferredSize(new Dimension(400, 200));
        textField = new JTextField();
        textField.setPreferredSize(new Dimension(430, 20));
        sendMessage = new JButton("Send");
        sendMessage.setPreferredSize(new Dimension(50, 20));
        ActionListener listener = new ButtonListener();
        sendMessage.addActionListener(listener);
        sendMessage.setMargin(new Insets(1, 1, 1, 1));

        caret = (DefaultCaret) chatBox.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        chatBox.setLineWrap(true);
        chatBox.setWrapStyleWord(true);
        chatBox.setEditable(false);
        chatPanel.add(scrollPane);
        inputPanel.add(textField);
        inputPanel.add(sendMessage);

        add(chatPanel, BorderLayout.NORTH);
        add(inputPanel, BorderLayout.SOUTH);
        getRootPane().setDefaultButton(sendMessage);
    }

    /**
     * Appends any given string to the chat box, followed by a new line
     *
     * @param message the message to be added
     */
    public void UpdateChat(String message) {
        chatBox.append(message + "\n");
    }

    /**
     * Called when the users presses enter. Gets the text from the text field, wipes it
     * Then evaluates the message given. If it is not ".bye" and is smaller than 160 characters,
     * it will be sent to the server
     * If there is no message, nothing will occur
     * If the message is ".bye" or greater than 160 characters, the user will be disconnected
     */
    public class ButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            // TODO Auto-generated method stub
            String message = textField.getText();
            textField.setText("");    //Clears the text box

            if (!message.equals(".bye") && message.length() < 160 && message.length() > 0)    //If the message meets the requirements given, it is printed
            {
                output.println(message);
                output.flush();
            } else if (message.length() == 0) {
            }                //If the message is empty, it will not be sent

            else if (message.length() > 159)                //If the message is 160 characters or greater, the user will be notified and disconnected
            {
                output.println("Message too long.\nDisconnecting Now...");
                output.flush();
                try {
                    ChatClient3.DisconnectClient();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else                                            //Otherwise, the user must have typed ".bye", in which case they will just be disconnected
            {
                output.println(message);
                output.println("Disconnecting Now...");
                output.flush();
                try {
                    ChatClient3.DisconnectClient();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Helper method used to get the printwriter from the client, which also prompts the user for their login and password
     *
     * @param out the printwriter given by the user, which is stored in a private variable
     */
    public void Setup(PrintWriter out) {
        output = out;
        //UpdateChat("Enter Login Name: ");
    }
}
