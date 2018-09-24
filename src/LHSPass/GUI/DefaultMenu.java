package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import Util.*;

public class DefaultMenu extends Menu implements ActionListener
{
        
    //JLabel topLabel = new JLabel("LHSPASS - By Darian Marvel");
    JLabel topLabel = getLabel("LHSPASS - By Darian Marvel");
    
    /*
    JLabel label2 = new JLabel("Swipe your tag!");
    JLabel link = new JLabel("Source Code is at: Github.com/RootCellar/LHSPass");
    JLabel credit = new JLabel("Credit: Mr. Benshoof (Ideas, Awesome Builder Dude) and Darian Marvel (Programmer Dude)");
    JLabel dev = new JLabel("Development started 8/25/18, program ready for testing 9/19/18");
    JLabel quo = new JLabel(" \" if ( youWant ) youCan(); else youCant(); \" ");
    */
    
    JLabel label2 = getLabel("Swipe your tag!", 81); //Extra Large
    
    JLabel link = getLabel("Source Code is at: Github.com/RootCellar/LHSPass");
    JLabel credit = getLabel("Credit: Mr. Benshoof (Ideas, Awesome Builder Dude) and Darian Marvel (Programmer Dude)");
    JLabel dev = getLabel("Development started 8/25/18, program ready for testing 9/19/18");
    JLabel quo = getLabel(" \" if ( youWant ) youCan(); else youCant(); \" ");
    
    public DefaultMenu() {
        //setBorder( BorderFactory.createEmptyBorder(5,5,5,5) );
        super();
        
        topLabel.setHorizontalAlignment(JLabel.CENTER);
        label2.setHorizontalAlignment(JLabel.CENTER);
        link.setHorizontalAlignment(JLabel.CENTER);
        credit.setHorizontalAlignment(JLabel.CENTER);
        dev.setHorizontalAlignment(JLabel.CENTER);
        quo.setHorizontalAlignment(JLabel.CENTER);
        
        Color col = Color.BLACK;
        
        topLabel.setForeground(col);
        label2.setForeground(col);
        link.setForeground(col);
        credit.setForeground(col);
        dev.setForeground(col);
        quo.setForeground(col);
        
        
        //topLabel.setForeground(Color.BLUE);
        //label2.setForeground(Color.YELLOW);
        
        JPanel top = new JPanel();
        top.setLayout( new GridLayout(7, 1) );
        
        top.add(topLabel);
        top.add(label2);
        top.add(link);
        top.add(credit);
        top.add(dev);
        top.add(quo);
        
        //top.setBackground(Color.GREEN); //Some complain that it looks too bright
        top.setBackground( new Color( (float) 0, (float) 0.8, (float) 0) ); //A bit of a darker green, easier on the eyes
        
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