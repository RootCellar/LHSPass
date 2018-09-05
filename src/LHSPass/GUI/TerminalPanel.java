package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import Util.*;

public class TerminalPanel extends JPanel implements KeyListener {
    private JTextArea text = new JTextArea(30,30);
    private JTextField input;
    private int logLength;
    private InputUser user;
    public TerminalPanel() {
        logLength=10000;
        this.setSize(100,100);
        text.setEditable(false);
        this.setLayout(new BorderLayout(1,1));

        JScrollPane scroll = new JScrollPane(text);
        text.setLineWrap(true);
        this.add(scroll, BorderLayout.CENTER);
        input = new JTextField();
        input.addKeyListener(this);
        this.add(input, BorderLayout.PAGE_END);

        for(int i=0; i<100; i++) {
            write("\n");
        }
        this.setVisible(true);
    }

    public void setUser(InputUser u) {
        user=u;
    }

    public void keyPressed(KeyEvent event) {
        try{
            String key = KeyEvent.getKeyText(event.getKeyCode());
            //System.out.println(key);
            if(key.equals("Enter") && input.getText().length()>0) {
                //Client.send(input.getText());
                write("> "+input.getText());
                String s = input.getText();
                input.setText(new String());
                user.inputText(s);
                input.setText("");
            }
        }catch(Exception e) {
            input.setText("");
            //e.printStackTrace();
        }
    }

    public void write(String w) {
        text.append(w+"\n");
        try{
            if(text.getDocument().getLength()>10000) {
                String text2=text.getDocument().getText(0, text.getDocument().getLength());
                String text3=text2.substring( text2.length()-logLength, text2.length() );
                text.setText(text3);
            }
        }catch(Exception e) {

        }
        text.setCaretPosition(text.getDocument().getLength());
    }
    
    public JTextArea getText() {
        return text;
    }

    public void keyReleased(KeyEvent event) {
    }

    public void keyTyped(KeyEvent event) {
    }
}