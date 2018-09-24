package GUI;

import java.awt.*;
import javax.swing.*;

import Util.*;

public class Menu extends JFrame
{
    InputUser user;
    
    static int FONT_SIZE = 27;
    
    static Font FONT_NORMAL = new Font("Serif", Font.PLAIN, FONT_SIZE);
    
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
        
        //toRet.setFont( toRet.getFont().deriveFont( FONT_SIZE ) );
        toRet.setFont( FONT_NORMAL );
        
        return toRet;
    }
    
    public JLabel getLabel(String name, int size) {
        JLabel toRet = new JLabel(name);
        
        //toRet.setFont( toRet.getFont().deriveFont( FONT_SIZE ) );
        toRet.setFont( new Font("Serif", Font.PLAIN, size) );
        
        return toRet;
    }
}