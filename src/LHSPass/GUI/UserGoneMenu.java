package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import Util.*;

public class UserGoneMenu extends Menu implements ActionListener
{
    
    /*
    JLabel topLabel = new JLabel("User has gone");
    JLabel nameLabel = new JLabel("NAME_OF_USER");
    JLabel loc = new JLabel("NULL");
    JLabel time = new JLabel("-1s");
    */
    
    JLabel topLabel = getLabel("User has gone");
    JLabel nameLabel = getLabel("NAME_OF_USER");
    JLabel loc = getLabel("NULL");
    JLabel time = getLabel("-1s");
    
    JButton adminButton = getNewButton( "USELESS BUTTON", "USELESS BUTTON" );
    
    public UserGoneMenu() {
        //setBorder( BorderFactory.createEmptyBorder(5,5,5,5) );
        super();
        
        topLabel.setHorizontalAlignment(JLabel.CENTER);
        nameLabel.setHorizontalAlignment(JLabel.CENTER);
        loc.setHorizontalAlignment(JLabel.CENTER);
        time.setHorizontalAlignment(JLabel.CENTER);
        
        Color col = Color.BLACK;
        
        topLabel.setForeground(col);
        nameLabel.setForeground(col);
        loc.setForeground(col);
        time.setForeground(col);
        
        JPanel top = new JPanel();
        top.setLayout( new GridLayout(4, 1) );
        
        top.add(nameLabel);
        top.add(topLabel);
        top.add(loc);
        top.add(time);
        
        top.setBackground(Color.RED);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout( new GridLayout(1, 1) );
        
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
    
    public void setTime(long t) {
        time.setText( t / 1000000000 + "s" );
    }
    
    public void setLoc(String s) {
        loc.setText(s);
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