package chatclientserver;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author absallh
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


/*
 * The Client with its GUI
 */
public class ClientGUI extends JFrame implements ActionListener {
    
    // will first hold "Username:", later on "Enter message"
    private JLabel label;
    // to hold the Username and later on the messages
    private JTextField tf;
    // to hold the server address an the port number
    private JTextField tfServer, tfPort;
    // to Logout and get the list of the users
    private JButton login, logout, whoIsIn;
    // for the chat room
    private JTextArea ta;
    // if it is for connection
    private boolean connected;
    // the Client object
    private Client client;
    // the default port number
    private int defaultPort;
    private String defaultHost;
    private String userName;

    // Constructor connection receiving a socket number
    ClientGUI(String host, int port, String userName) {

        super("Chat Client");
        this.userName = userName;
        defaultPort = port;
        defaultHost = host;

        // The NorthPanel with:
        JPanel northPanel = new JPanel(new GridLayout(3, 1));
        // the server name anmd the port number
        JPanel serverAndPort = new JPanel(new GridLayout(1, 5, 1, 3));
        // the two JTextField with default value for server address and port number
        tfServer = new JTextField(host);
        tfPort = new JTextField("" + port);
        tfPort.setHorizontalAlignment(SwingConstants.RIGHT);

        serverAndPort.add(new JLabel("Server Address:  "));
        serverAndPort.add(tfServer);
        serverAndPort.add(new JLabel("Port Number:  "));
        serverAndPort.add(tfPort);
        serverAndPort.add(new JLabel(""));
        // adds the Server an port field to the GUI
        northPanel.add(serverAndPort);

        // the Label and the TextField
        label = new JLabel("Try to enter chat", SwingConstants.CENTER);
        northPanel.add(label);
        tf = new JTextField(userName);
        tf.setEditable(false);
        //tf.setBackground(Color.WHITE);
        northPanel.add(tf);
        add(northPanel, BorderLayout.NORTH);

        // The CenterPanel which is the chat room
        ta = new JTextArea("Welcome to the Chat room\n", 80, 80);
        JPanel centerPanel = new JPanel(new GridLayout(1, 1));
        centerPanel.add(new JScrollPane(ta));
        ta.setEditable(false);
        add(centerPanel, BorderLayout.CENTER);

        // the 4 buttons

        login = new JButton("Enter To Chat");
        login.addActionListener(this);
        logout = new JButton("Logout");
        logout.addActionListener(this);
        logout.setEnabled(false); // you have to login before being able to logout
        whoIsIn = new JButton("Who is in");
        whoIsIn.addActionListener(this);
        whoIsIn.setEnabled(false); // you have to login before being able to Who is in

        JPanel southPanel = new JPanel();
        southPanel.add(login);
        southPanel.add(logout);
        southPanel.add(whoIsIn);
        add(southPanel, BorderLayout.SOUTH);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 600);
        setVisible(true);
        tf.requestFocus();
        enterChat();
    }

    // called by the Client to append text in the TextArea 
    void append(String str) {
        ta.append(str);
        ta.setCaretPosition(ta.getText().length() - 1);
    }
    // called by the GUI is the connection failed
    // we reset our buttons, label, textfield
    void connectionFailed() {
        login.setEnabled(true);
        logout.setEnabled(false);
        whoIsIn.setEnabled(false);
        label.setText("try to reconnect");
        tf.setText(this.userName);
        tf.setEditable(false);
        // reset port number and host name as a construction time
        tfPort.setText("" + defaultPort);
        tfServer.setText(defaultHost);
        // let the user change them
        tfServer.setEditable(true);
        tfPort.setEditable(true);
        // don't react to a <CR> after the username
        tf.removeActionListener(this);
        connected = false;
    }

    /*
     * Button or JTextField clicked
     */
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        // if it is the Logout button
        if (o == logout) {
            this.setVisible(false);
            new LoginFrame();
            client.sendMessage(new ChatMessage(ChatMessage.LOGOUT, ""));
            return;
        }
        // if it the who is in button
        if (o == whoIsIn) {
            client.sendMessage(new ChatMessage(ChatMessage.WHOISIN, ""));
            return;
        }

        // ok it is coming from the JTextField
        if (connected) {
            // just have to send the message
            client.sendMessage(new ChatMessage(ChatMessage.MESSAGE, tf.getText()));
            tf.setText("");
            return;
        }


        if (o == login) {
            enterChat();
        }

    }

    public void enterChat() {
        // ok it is a connection request

        /*//String username = tf.getText().trim();
        // empty username ignore it
        if (this.userName.length() == 0)
            return;*/
        // empty serverAddress ignore it
        String server = tfServer.getText().trim();
        if (server.length() == 0)
            return;
        // empty or invalid port numer, ignore it
        String portNumber = tfPort.getText().trim();
        if (portNumber.length() == 0)
            return;
        int port;
        try {
            port = Integer.parseInt(portNumber);
        } catch (Exception en) {
            return; // nothing I can do if port number is not valid
        }

        // try creating a new Client with GUI
        client = new Client(server, port, this.userName, this);
        // test if we can start the Client
        if (!client.start()){
            JOptionPane.showMessageDialog(this,"couldn't connect.","Alert",JOptionPane.WARNING_MESSAGE);
            return;
        }
        tf.setText("");
        tf.setEditable(true);
        label.setText("Enter your message below");
        connected = true;

        // disable login button
        login.setEnabled(false);
        // enable the 2 buttons
        logout.setEnabled(true);
        whoIsIn.setEnabled(true);
        // disable the Server and Port JTextField
        tfServer.setEditable(false);
        tfPort.setEditable(false);
        // Action listener for when the user enter a message
        tf.addActionListener(this);
    }


    public static void main(String[] args) {
        new LoginFrame();
    }
}