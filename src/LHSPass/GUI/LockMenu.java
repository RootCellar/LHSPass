package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import Util.*;

public class LockMenu extends Menu implements ActionListener
{
    /*
    JLabel topLabel = new JLabel("LHSPASS");
    JLabel label2 = new JLabel("PROGRAM LOCKED BY ADMIN");
    */
    
    JLabel topLabel = getLabel("LHSPASS", 63);
    JLabel label2 = getLabel("PROGRAM LOCKED BY ADMIN", 63);
   
    public LockMenu() {
        //setBorder( BorderFactory.createEmptyBorder(5,5,5,5) );
        super();
        
        topLabel.setHorizontalAlignment(JLabel.CENTER);
        label2.setHorizontalAlignment(JLabel.CENTER);
        
        JPanel top = new JPanel();
        top.setLayout( new GridLayout(2, 1) );
        
        top.add(topLabel);
        top.add(label2);
        //top.add( new JLabel("ONLY ADMIN CAN UNLOCK") );
        
        //top.setBackground(Color.RED); //Alright, we can tone the red down some
        top.setBackground( new Color( (float) 0.6, (float) 0, (float) 0) );
        
        topLabel.setForeground(Color.WHITE);
        label2.setForeground(Color.WHITE);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout( new GridLayout(4, 1) );
        
        buttonPanel.add( getNewButton( "BLANK", "BLANK" ) );
        buttonPanel.add( getNewButton( "BLANK", "BLANK" ) );
        buttonPanel.add( getNewButton( "BLANK", "BLANK" ) );
        buttonPanel.add( getNewButton( "BLANK", "BLANK" ) );
        
        top.setBorder( BorderFactory.createEmptyBorder(10,10,10,10) );
        buttonPanel.setBorder( BorderFactory.createEmptyBorder(10,10,10,10) );
        
        this.add(top, BorderLayout.CENTER);
        //this.add(buttonPanel, BorderLayout.CENTER);
        this.pack();
        this.setTitle("LHSPASS");
        this.setVisible(true);
    }
    
    public void actionPerformed(ActionEvent e) {
        if(user == null) return;
        
        String command = e.getActionCommand();
        
        user.inputText(command);
    }
    
    public JButton getNewButton(String name, String command) {
        JButton button = new JButton(name);
        button.addActionListener(this);
        button.setActionCommand(command);
        return button;
    }
}