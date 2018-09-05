package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class DataDisplayPanel extends JPanel {
    public JLabel[] labels;
    public DataDisplayPanel(int n) {
        labels = new JLabel[n];
        this.setLayout(new GridLayout(20,10));
        this.setBorder(BorderFactory.createEmptyBorder(20,30,10,30));

        //Text Fields for numbers
        for(int i=0; i<n; i++) {
            labels[i]=new JLabel("NOT SET " + i);
            this.add(labels[i]);
        }
    }
    
    public void setText(int n, String s) {
        if(n>-1 && n<labels.length) {
            labels[n].setText(s);
        }
    }
}