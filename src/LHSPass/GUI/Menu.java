package GUI;

import java.awt.*;
import javax.swing.*;

import Util.*;

public class Menu extends JFrame
{
    InputUser user;
    
    public Menu() {
        setPreferredSize( new Dimension( 400, 400 ) );
        
        //setPreferredSize( Toolkit.getDefaultToolkit().getScreenSize() );
        //setLocationRelativeTo(null);
    }
    
    public void setUser(InputUser u) {
        user = u;
    }
}