package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import Util.*;

public class KeypadMenu extends Menu implements ActionListener
{

    static final int maxLength = 20;
    
    boolean uppercase = false;
    
    JLabel topLabel = new JLabel("TYPE RESPONSE");
    JLabel label2 = new JLabel("");
    
    String text = "";

    public KeypadMenu() {
        //setBorder( BorderFactory.createEmptyBorder(5,5,5,5) );
        super();

        setTitle("Keypad");

        topLabel.setHorizontalAlignment(JLabel.CENTER);
        label2.setHorizontalAlignment(JLabel.CENTER);

        JPanel top = new JPanel();
        top.setLayout( new GridLayout(3, 1) );

        top.add(topLabel);
        top.add(label2);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout( new GridLayout(4, 8) );
        
        char c = 'a';
        for(int i=0; i<26; i++) {
            buttonPanel.add( getNewButton( c + "", c + "" ) );
            c++;
        }
        
        buttonPanel.add( getNewButton( "ENTER", "ENTER" ) );
        buttonPanel.add( getNewButton( "DEL", "DEL" ) );
        
        buttonPanel.add( getNewButton( "CASE", "CASE" ) );

        /*
        buttonPanel.add( getNewButton( "BLANK", "BLANK" ) );
        buttonPanel.add( getNewButton( "BLANK", "BLANK" ) );
        buttonPanel.add( getNewButton( "BLANK", "BLANK" ) );
        buttonPanel.add( getNewButton( "BLANK", "BLANK" ) );

        buttonPanel.add( getNewButton( "BLANK", "BLANK" ) );
        buttonPanel.add( getNewButton( "BLANK", "BLANK" ) );
        buttonPanel.add( getNewButton( "BLANK", "BLANK" ) );
        buttonPanel.add( getNewButton( "BLANK", "BLANK" ) );

        buttonPanel.add( getNewButton( "BLANK", "BLANK" ) );
        buttonPanel.add( getNewButton( "BLANK", "BLANK" ) );
        buttonPanel.add( getNewButton( "BLANK", "BLANK" ) );
        buttonPanel.add( getNewButton( "BLANK", "BLANK" ) );

        buttonPanel.add( getNewButton( "BLANK", "BLANK" ) );
        buttonPanel.add( getNewButton( "BLANK", "BLANK" ) );
        buttonPanel.add( getNewButton( "BLANK", "BLANK" ) );
        buttonPanel.add( getNewButton( "BLANK", "BLANK" ) );

        buttonPanel.add( getNewButton( "BLANK", "BLANK" ) );
        buttonPanel.add( getNewButton( "BLANK", "BLANK" ) );
        buttonPanel.add( getNewButton( "BLANK", "BLANK" ) );
        buttonPanel.add( getNewButton( "BLANK", "BLANK" ) );

        buttonPanel.add( getNewButton( "BLANK", "BLANK" ) );
        buttonPanel.add( getNewButton( "BLANK", "BLANK" ) );
        buttonPanel.add( getNewButton( "BLANK", "BLANK" ) );
        buttonPanel.add( getNewButton( "BLANK", "BLANK" ) );

        buttonPanel.add( getNewButton( "ENTER", "ENTER" ) );
        buttonPanel.add( getNewButton( "BLANK", "BLANK" ) );
        buttonPanel.add( getNewButton( "BLANK", "BLANK" ) );
        buttonPanel.add( getNewButton( "BLANK", "BLANK" ) );

        buttonPanel.add( getNewButton( "BLANK", "BLANK" ) );
        buttonPanel.add( getNewButton( "BLANK", "BLANK" ) );
        buttonPanel.add( getNewButton( "BLANK", "BLANK" ) );
        buttonPanel.add( getNewButton( "BLANK", "BLANK" ) );
        */

        top.setBorder( BorderFactory.createEmptyBorder(10,10,10,10) );
        buttonPanel.setBorder( BorderFactory.createEmptyBorder(10,10,10,10) );

        this.add(top, BorderLayout.NORTH);
        this.add(buttonPanel, BorderLayout.CENTER);
        this.pack();

        this.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        //if(user == null) return;

        String command = e.getActionCommand();
        
        if(uppercase) command = command.toUpperCase();
        
        if(command.equals("ENTER")) {
            if( user!=null )user.inputText(command);
        }
        else if(command.equals("DEL")) {
            if(text.length() < 1) return;
            
            text = text.substring(0, text.length() - 1);
            label2.setText( text );
        }
        else if(command.equals("CASE")) {
            uppercase = !uppercase;
        }
        else {
            if( text.length() >= 20 ) return;
            text += command;
            label2.setText( text );
        }
    }

    public JButton getNewButton(String name, String command) {
        JButton button = new JButton(name);
        button.addActionListener(this);
        button.setActionCommand(command);
        return button;
    }
    
    public void append(String s) {
        text += s;
    }
}