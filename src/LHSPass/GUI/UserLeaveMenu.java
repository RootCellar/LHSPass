package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import Util.*;

public class UserLeaveMenu extends Menu implements ActionListener
{
    
    /*
    JLabel topLabel = new JLabel("Where would you like to go?");
    JLabel nameLabel = new JLabel("NAME_OF_USER");
    */
    
    JLabel topLabel = getLabel("Where would you like to go?");
    JLabel nameLabel = getLabel("NAME_OF_USER");
    
    JButton restroomButton = getNewButton( "RESTROOM", "RESTROOM" );
    JButton lockerButton = getNewButton( "LOCKER", "LOCKER" );
    JButton waterButton = getNewButton( "WATER", "WATER" );
    JButton otherButton = getNewButton( "OTHER", "OTHER" );
    JButton backButton = getNewButton( "BACK", "BACK" );
    JButton adminButton = getNewButton( "ADMIN", "ADMIN" );
    
    public UserLeaveMenu() {
        //setBorder( BorderFactory.createEmptyBorder(5,5,5,5) );
        super();
        
        topLabel.setHorizontalAlignment(JLabel.CENTER);
        nameLabel.setHorizontalAlignment(JLabel.CENTER);
        
        JPanel top = new JPanel();
        top.setLayout( new GridLayout(4, 1) );
        
        top.add(nameLabel);
        top.add(topLabel);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout( new GridLayout(5, 1) );
        
        buttonPanel.add( restroomButton );
        buttonPanel.add( lockerButton );
        buttonPanel.add( waterButton );
        buttonPanel.add( otherButton );
        buttonPanel.add( backButton );
        buttonPanel.add( adminButton );
        
        adminButton.setEnabled(false);
        
        top.setBorder( BorderFactory.createEmptyBorder(10,10,10,10) );
        buttonPanel.setBorder( BorderFactory.createEmptyBorder(10,10,10,10) );
        
        this.add(top, BorderLayout.NORTH);
        this.add(buttonPanel, BorderLayout.CENTER);
        this.pack();
        
        this.setVisible(true);
    }
    
    public void setName(String s) {
        nameLabel.setText(s);
    }
    
    public void setAdmin(boolean adminMode) {
        adminButton.setEnabled(adminMode);
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