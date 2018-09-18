import java.util.ArrayList;
import javax.swing.*;

import Logging.*;
import GUI.*;
import Util.*;

public class UserHandler implements InputUser
{

    enum State
    {
        AWAIT_USER( new DefaultMenu() ), //Await user tag swipe
        //AWAIT_ADMIN, // Await admin override
        USER_MENU( new UserLeaveMenu() ), //User is at menu
        USER_GONE( new UserGoneMenu() ), //User left the room
        DEBUG( new DebugMenu() ), // Debug menu with buttons
        ;

        String name;
        int id;
        Menu menu;
        State(Menu m) {
            name = name();
            id = ordinal();
            menu = m;
            m.setVisible(false);
        }

        public String getName() {
            return name;
        }

        public int getId() {
            return id;
        }

        public Menu getMenu() {
            return menu;
        }

        public boolean equals(State other) {
            if( getName().equalsIgnoreCase( other.getName() ) ) return true;

            return false;
        }

        public String toString() {
            String toRet = "{ name: " + getName() + ", id: " + getId() + " }";
            return toRet;
        }
    }

    ArrayList<String> commands = new ArrayList<String>();

    ArrayList<State> menuQueue = new ArrayList<State>();

    State state = State.AWAIT_USER;
    Runner runner;

    User currentUser;
    String went = "";
    long menuSetTime = System.nanoTime();
    
    TwitterHandler twha;

    public UserHandler(Runner r) {
        runner = r;
        setup();
        
        twha = new TwitterHandler(r);
        
        twha.send( "User Handler up and running" );
    }
    
    public TwitterHandler getTwitterHandler() {
        return twha;
    }

    public ArrayList<State> getQueue() {
        return menuQueue;
    }

    public void out(String s) {
        runner.out("[GUI HANDLER] " + s);
    }

    public void setup() {
        setState(state);
    }

    public void closeAll() {
        for( State s : State.values() ) {
            s.getMenu().setVisible(false);
        }
    }

    public void resetUser() {
        for( State s : State.values() ) {
            s.getMenu().setUser( null );
        }
    }

    public void setState(String to) {
        for( State s : State.values() ) {
            if( to.equals( s.getName() ) ) setState(s);
        }
    }

    public void resetQueue() {
        while(menuQueue.size() > 0) menuQueue.remove(0);
    }

    /*
     * Go back to the previous menu in the queue
     * Queue adds to position 0 (FILO, or First In Last Out)
     */
    public void goBack() {
        out("Going back a menu...");
        state.getMenu().setVisible(false);
        
        if(menuQueue.size() > 0) menuQueue.remove(0);

        if(menuQueue.size() == 0) {
            out("Menu queue empty. Going to default menu...");
            stateSet(State.AWAIT_USER);
            return;
        }

        stateSet( menuQueue.get( menuQueue.size() - 1 ) );
        out(menuQueue.size() + " menus in queue after going back");
    }

    /*
     * Add a menu to the queue, allowing other menus to go back to the previous menu
     * Unlike setState, this method does not close the menus, or clear the menu queue
     * (which would make this method useless)
     */
    public void addState(State toAdd) {
        out("Adding a state to the queue...");
        
        menuQueue.add(0, toAdd);
        stateSet(toAdd);
        
        out(menuQueue.size() + " menus in queue after adding one");
    }

    //Set the menu, clearing the menu queue
    public void setState(State to) {
        closeAll();
        
        resetQueue();
        
        stateSet(to);
    }

    public void stateSet(State to) {
        out("Setting state...");
        
        resetUser();
        state = to;
        
        state.getMenu().setVisible(true);
        state.getMenu().setUser(this);
        
        menuSetTime = System.nanoTime();
        
        out(to.toString());
    }

    public void inputText(String i) {
        commands.add(i);
    }

    public void tick() {
        while(commands.size()>0) {
            run( commands.remove(0) );
        }
        
        if( state.equals(State.USER_GONE) ) {
            UserGoneMenu m = (UserGoneMenu) state.getMenu();
            
            if( currentUser != null ) m.setName( currentUser.getName() );
            
            m.setTime( System.nanoTime() - menuSetTime );
            
            m.setLoc( went );
        }
    }

    public void run(String s) {
        out("COMMAND: " + s);

        if(Command.is("/SWIPE",s)) {
            ArrayList<String> args = Command.getArgs(s);

            tagSwipe( args.get( args.size() - 1 ) );
            
            return;
        }

        if(state.equals(State.DEBUG)) {
            //if( s.equals("/stop") ) runner.inputText(s);
            if(s.equals("BACK")) {
                goBack();
            }
            else runner.inputText(s);
            
            return;
        }
        
        if(state.equals(State.USER_MENU)) {
            if(currentUser == null) setState( State.AWAIT_USER);
            
            UserLeaveMenu m = (UserLeaveMenu) state.getMenu();
            
            if(s.equals("BACK")) {
                goBack();
                
                userLog("Signed out!");
                currentUser = null;
            }
            
            if(s.equals("ADMIN") && currentUser.getPermissionList().hasType( "ADMIN" ) ) {
                addState( State.DEBUG );
            }
            
            if( s.equals("RESTROOM") || s.equals("LOCKER") || s.equals("WATER") || s.equals("OTHER") ) {
                
                boolean has = currentUser.getPermissionList().has( "USER_" + s );
                
                if(!has) {
                    JOptionPane.showMessageDialog( null, "You don't have permission: " + "USER_" + s );
                    out(currentUser.getName() + " denied permission for: " + s);
                    return;
                }
                
                addState(State.USER_GONE);
                
                went = s;
                
                out("Successfully allowed a student to leave the room: " + currentUser.getName() + " --> " + went );
                twha.send( currentUser.getName() + " left for " + went );
                
                
                userLog("Left to go to the " + s );
                
            }
            
            return;
        }
    }

    public void tagSwipe(String serial) {
        out("Received tag: " + serial);
        
        if( state.equals( State.AWAIT_USER ) ) {
            //addState( State.USER_MENU );

            currentUser = runner.getUserByTag(serial);
            
            if(currentUser == null) {
                out("User not recognized");
                twha.send("Unrecognized tag scanned!");
                return;
            }
            else {
                out("Found User: " + currentUser.toString() );
                addState( State.USER_MENU );
                UserLeaveMenu m = (UserLeaveMenu) state.getMenu();
                
                m.setAdmin(false);
                
                m.setName("Hello, " + currentUser.getName());
                
                out("IS AN ADMIN: " + currentUser.getPermissionList().hasType("ADMIN") );
                if(currentUser.getPermissionList().hasType("ADMIN")) {
                    m.setAdmin(true);
                }
                
                userLog("Signed in!");
            } 

        }
        
        if( state.equals( State.USER_GONE ) ) {
            
            if( currentUser == null ) setState( State.AWAIT_USER );
            
            if( serial.equals( currentUser.getTag() ) ) {
                out( currentUser.getName() + " is back" );
                
                userLog("Returned to the room from " + went);
                twha.send(currentUser.getName() + " returned to the room from " + went + " after " + ( ( System.nanoTime() - menuSetTime ) / 1000000000 ) + "s" );
                
                went = "Too bad you'll never see this. If you do, it's a bug.";
                
                setState( State.AWAIT_USER );
            }
            else
            {
                User person = runner.getUserByTag(serial);
                
                if(person == null) return;
                
                if(person.getPermissionList().has("ADMIN_OVERRIDE")) {
                    out("Admin scanned");
                    out("Going back...");
                    
                    userLog("Admin released lock");
                    
                    goBack();
                    
                    twha.send("Admin " + person.getName() + " released USER_GONE lock");
                }
                else
                {
                    person.log("Swiped tag while someone else was gone");
                    out(person.getName() + " swiped while " +currentUser.getName() + " was gone!");
                    twha.send(person.getName() + " swiped while " +currentUser.getName() + " was gone!");
                }
                
            }
        }
        
        if( state.equals( State.DEBUG ) ) {
            JOptionPane.showMessageDialog( null, "Read: " + serial );
        }
    }
    
    public void userLog(String s) {
        if(currentUser == null) return;
        
        currentUser.log(s);
        
        out("USER " + currentUser.getName() + " "  + s);
    }
}