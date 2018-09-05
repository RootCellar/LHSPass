package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import Util.*;
import Client.*;

public class ClientWindow extends JFrame implements KeyListener, ActionListener, InputUser {
    private JTextArea text = new JTextArea(30,30);
    private JTextField input;
    private int logLength=10000;
    private InputUser user;
    //private Game game;
    private Client client;
    JButton disconnect;
    JButton connect;
    JTextField ipInput;
    JTextField portInput;
    
    TerminalPanel term;
    public ClientWindow(Client cl) {
        //game=g;
        client = cl;
        
        logLength=10000;
        this.setSize(100,100);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        text.setEditable(false);

        this.setLayout(new BorderLayout(1,1));

        //Panel 1, Center Panel
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(1,1));
        panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        JScrollPane scroll = new JScrollPane(text);
        text.setLineWrap(true);
        panel.add(scroll, BorderLayout.CENTER);
        input = new JTextField();
        input.addKeyListener(this);
        panel.add(input, BorderLayout.PAGE_END);

        JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayout(5,1));
        panel2.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        disconnect = new JButton("Disconnect");
        panel2.add(disconnect);
        disconnect.setActionCommand("disconnect");
        disconnect.addActionListener(this);

        connect = new JButton("Connect");
        connect.setActionCommand("connect");
        connect.addActionListener(this);
        panel2.add(connect);

        ipInput = new JTextField();
        portInput = new JTextField();

        JPanel panel3 = new JPanel();
        panel3.setLayout( new GridBagLayout() );
        panel3.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        GridBagConstraints c = new GridBagConstraints();

        //IP Label
        c = new GridBagConstraints();

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx= 0;
        c.gridy = 1;
        c.weightx = 1;

        panel3.add( new JLabel("IP: "),c );
        //End IP Label

        //IP Input
        c = new GridBagConstraints();

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.gridx = 1;
        c.gridy = 1;
        c.weightx = 1;
        c.ipadx = 200;

        panel3.add(ipInput,c);
        //End IP Input

        //PORT Label
        c = new GridBagConstraints();

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 2;
        c.weightx = 1;

        panel3.add( new JLabel("PORT: "),c );
        //End Port Label

        //Port Input
        c = new GridBagConstraints();

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.gridx = 1;
        c.gridy = 2;
        c.weightx = 1;
        c.ipadx = 200;

        panel3.add(portInput,c);
        //End port input
        
        //Term
        term = new TerminalPanel();
        
        c = new GridBagConstraints();

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.gridx = 1;
        c.gridy = 0;
        c.weightx = 0;
        c.ipadx = 0;
        c.ipady = 0;
        
        
        //panel3.add(term, c);
        //End Term
        

        //PixelCanvas screen = new PixelCanvas();
        //this.add(screen, BorderLayout.LINE_END);

        this.add(panel, BorderLayout.CENTER);
        this.add(panel2, BorderLayout.LINE_START);
        this.add(panel3, BorderLayout.LINE_END);
        

        this.setTitle("Client Window");
        this.pack();
        for(int i=0; i<100; i++) {
            write("\n");
        }
        this.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("connect")) {
            try{
                String ip = ipInput.getText();
                String port = portInput.getText();
                int port2 = Integer.parseInt( port );

                new Thread() { public void run() {
                        //game.connect(ip, port2);
                        client.connect(ip, port2);
                    }
                }.start();
            }catch(Exception k) {

            }
        }

        if(e.getActionCommand().equals("disconnect")) {
            //host.setEnabled(true);
            //connect.setEnabled(false);
            //game.reconnect();
            client.disconnect();
        }
        //write(e+"");
    }

    public void setUser(InputUser u) {
        user=u;
    }

    /**
     * Used to send the input and clear the text field when the user presses Enter
     */
    public void keyPressed(KeyEvent event) {
        try{
            String key = KeyEvent.getKeyText(event.getKeyCode());
            //System.out.println(key);
            if(key.equals("Enter") && input.getText().length()>0) {
                //Client.send(input.getText());
                write("> "+input.getText());
                String s = input.getText();
                input.setText(new String());

                //Executes on different thread to stop the terminal from freezing
                new Thread() { public void run() {
                        //user.inputText(s);
                        client.send(s);
                    }
                }.start();

                input.setText("");
            }
        }catch(Exception e) {
            input.setText("");
            //e.printStackTrace();
        }
    }

    /**
     * Writes a message to the terminal
     */
    public void write(String w) {
        text.append(w+"\n");
        try{
            if(text.getDocument().getLength()>logLength) { //Prevents the terminal from having more than 10,000 characters to display
                String text2=text.getDocument().getText(0, text.getDocument().getLength());
                String text3=text2.substring( text2.length()-logLength, text2.length() );
                text.setText(text3);
            }
        }catch(Exception e) {

        }
        text.setCaretPosition(text.getDocument().getLength());
    }

    public void keyReleased(KeyEvent event) {
    }

    public void keyTyped(KeyEvent event) {
    }
    
    public synchronized void inputText(String i) {
        //write(i);
    }
    
    public synchronized void inputObject(Object o) {
        
    }
}