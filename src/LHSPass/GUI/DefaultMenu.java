package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import Util.*;

public class DefaultMenu extends Menu implements ActionListener
{
        
    JLabel topLabel = new JLabel("LHSPASS - By Darian Marvel");
    //JLabel topLabel = getLabel("LHSPASS - By Darian Marvel"); //Font size doesn't change
    
    JLabel label2 = new JLabel("Swipe your tag!");
    JLabel link = new JLabel("Source Code is at: Github.com/RootCellar/LHSPass");
    JLabel credit = new JLabel("Credit: Mr. Benshoof (Ideas, Awesome Builder Dude) and Darian Marvel (Programmer Dude)");
    JLabel dev = new JLabel("Development started 8/25/18, program ready for testing 9/19/18");
    JLabel quo = new JLabel(" \" if ( youWant ) youCan(); else youCant(); \" ");
    
    public DefaultMenu() {
        //setBorder( BorderFactory.createEmptyBorder(5,5,5,5) );
        super();
        
        topLabel.setHorizontalAlignment(JLabel.CENTER);
        label2.setHorizontalAlignment(JLabel.CENTER);
        link.setHorizontalAlignment(JLabel.CENTER);
        credit.setHorizontalAlignment(JLabel.CENTER);
        dev.setHorizontalAlignment(JLabel.CENTER);
        quo.setHorizontalAlignment(JLabel.CENTER);
        
        //topLabel.setForeground(Color.BLUE);
        //label2.setForeground(Color.YELLOW);
        
        JPanel top = new JPanel();
        top.setLayout( new GridLayout(15, 1) );
        
        top.add(topLabel);
        top.add(label2);
        top.add(link);
        top.add(credit);
        top.add(dev);
        top.add(quo);
        
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