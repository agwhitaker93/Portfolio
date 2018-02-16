import java.awt.*;
import java.awt.event.*;
import java.io.PrintWriter;

import javax.swing.*;
import javax.swing.text.*;

@SuppressWarnings("serial")
public class ServerFrame extends JFrame {
    final static int FRAME_WIDTH = 500;
    final static int FRAME_HEIGHT = 300;
    JPanel chatPanel;
    JTextArea chatBox;
    JScrollPane scrollPane;
    JTextField textField;
    DefaultCaret caret;

    PrintWriter output;

    /**
     * Default constructor for the frame
     * The title is set, CreateComponents is called and the size of the frame is set
     */
    public ServerFrame() {
        setTitle("Chatroom Server");
        CreateComponents();
        setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        setMinimumSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        setResizable(false);
    }

    /**
     * Creates all components of the frame
     */
    public void CreateComponents() {
        chatPanel = new JPanel();
        chatBox = new JTextArea();
        scrollPane = new JScrollPane(chatBox);
        scrollPane.setPreferredSize(new Dimension(400, 200));
        textField = new JTextField();
        textField.setPreferredSize(new Dimension(480, 20));

        caret = (DefaultCaret) chatBox.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        chatBox.setLineWrap(true);
        chatBox.setWrapStyleWord(true);
        chatBox.setEditable(false);
        chatPanel.add(scrollPane);

        add(chatPanel, BorderLayout.CENTER);
    }

    /**
     * Appends any given string to the chat box, followed by a new line
     *
     * @param message the message to be added
     */
    public void UpdateServerConsole(String message) {
        chatBox.append(message + "\n");
    }
}
