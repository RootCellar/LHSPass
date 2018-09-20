package GUI;

import java.awt.*;
import javax.swing.*;

import Util.*;

public class Menu extends JFrame
{
    InputUser user;
    
    static int FONT_SIZE = 50;
    
    public Menu() {
        setPreferredSize( new Dimension( 1920, 1080 ) );
        
        //setPreferredSize( Toolkit.getDefaultToolkit().getScreenSize() );
        //setLocationRelativeTo(null);
    }
    
    public void setUser(InputUser u) {
        user = u;
    }
    
    public JLabel getLabel(String name) {
        JLabel toRet = new JLabel(name);
        
        toRet.setFont( toRet.getFont().deriveFont( FONT_SIZE ) );
        
        return toRet;
    }
}