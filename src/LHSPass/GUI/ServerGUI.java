package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import Util.*;

public class ServerGUI implements ActionListener {
    public JFrame frame = new JFrame();
    public InputUser user;
    
    //DEBUG TERMINAL
    public TerminalPanel term = new TerminalPanel();
    
    //MAIN TERMINAL
    public TerminalPanel term2 = new TerminalPanel();
    
    //USER LIST
    public TerminalPanel term3 = new TerminalPanel();
    
    //SECONDARY TERMINAL
    public TerminalPanel term4 = new TerminalPanel();
    
    
    public DataDisplayPanel dataDisplay;
    public ServerGUI(int dataCount) {
        dataDisplay = new DataDisplayPanel(dataCount);
        
        JTabbedPane tabbedPane = new JTabbedPane();

        JTabbedPane tabbedPane2 = new JTabbedPane();

        frame.setLayout( new GridLayout(1, 2) );

        //Create Main tab
        
        JPanel panel = new JPanel();
        //panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel.setLayout(new GridLayout(4, 2));

        panel.add( getNewButton("Halt Server","/halt") );
        panel.add( getNewButton("BLANK","BLANK") );
        panel.add( getNewButton("BLANK","BLANK") );
        panel.add( getNewButton("BLANK","BLANK") );
        panel.add( getNewButton("BLANK","BLANK") );
        panel.add( getNewButton("BLANK", "BLANK") );
        panel.add( getNewButton("BLANK", "BLANK") );
        panel.add( getNewButton("BLANK", "BLANK") );

        //Set up tabs
        
        tabbedPane.add("Main", panel);
        
        tabbedPane.add("Info", dataDisplay);
        
        tabbedPane.add("Debug", term);
        
        //Set up panels and buttons for Tabcursion
        
        JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayout(4, 2));

        panel3.add( getNewButton("BLANK", "BLANK") );
        panel3.add( getNewButton("BLANK", "BLANK") );
        panel3.add( getNewButton("BLANK", "BLANK") );
        panel3.add( getNewButton("BLANK", "BLANK") );
        panel3.add( getNewButton("BLANK", "BLANK") );
        panel3.add( getNewButton("BLANK", "BLANK") );
        panel3.add( getNewButton("BLANK", "BLANK") );
        panel3.add( getNewButton("BLANK", "BLANK") );

        JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayout(4, 2));

        panel4.add( getNewButton("BLANK", "BLANK") );
        panel4.add( getNewButton("BLANK", "BLANK") );
        panel4.add( getNewButton("BLANK", "BLANK") );
        panel4.add( getNewButton("BLANK", "BLANK") );
        panel4.add( getNewButton("BLANK", "BLANK") );
        panel4.add( getNewButton("BLANK", "BLANK") );
        panel4.add( getNewButton("BLANK", "BLANK") );
        panel4.add( getNewButton("BLANK", "BLANK") );
        
        JPanel panel5 = new JPanel();
        panel5.setLayout(new GridLayout(4, 2));

        panel5.add( getNewButton("BLANK", "BLANK") );
        panel5.add( getNewButton("BLANK", "BLANK") );
        panel5.add( getNewButton("BLANK", "BLANK") );
        panel5.add( getNewButton("BLANK", "BLANK") );
        panel5.add( getNewButton("BLANK", "BLANK") );
        panel5.add( getNewButton("BLANK", "BLANK") );
        panel5.add( getNewButton("BLANK", "BLANK") );
        panel5.add( getNewButton("BLANK", "BLANK") );

        //Set up the Tabcursion
        tabbedPane2.add("Buttons", panel3);
        tabbedPane2.add("More Buttons", panel4);
        tabbedPane2.add("Even More Buttons",panel5);
        
        tabbedPane.add("User List",term3);

        //Add the Tabcursion
        tabbedPane.add("Tabcursion",tabbedPane2);
        
        //SECONDARY TERM ADD
        tabbedPane.add("TERM2", term4);

        tabbedPane.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        term2.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        
        frame.add(tabbedPane);
        
        frame.add(term2);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Server GUI");
        frame.pack();
        //frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        
        user.inputText(command);
    }

    public void out(String s) {
        
    }

    public JButton getNewButton(String name, String command) {
        JButton button = new JButton(name);
        button.addActionListener(this);
        button.setActionCommand(command);
        return button;
    }
}