import java.util.ArrayList;

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
        //USER_GONE, //User left the room
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

    public UserHandler(Runner r) {
        runner = r;
        setup();
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
        state.getMenu().setVisible(false);
        
        if(menuQueue.size() > 0) menuQueue.remove(0);

        if(menuQueue.size() == 0) {
            stateSet(State.AWAIT_USER);
            return;
        }

        stateSet( menuQueue.get( menuQueue.size() - 1 ) );
    }

    /*
     * Add a menu to the queue, allowing other menus to go back to the previous menu
     * Unlike setState, this method does not close the menus, or clear the menu queue
     * (which would make this method useless)
     */
    public void addState(State toAdd) {
        menuQueue.add(0, toAdd);
        stateSet(toAdd);
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
        out(to.toString());
    }

    public void inputText(String i) {
        commands.add(i);
    }

    public void tick() {
        while(commands.size()>0) {
            run( commands.remove(0) );
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
            UserLeaveMenu m = (UserLeaveMenu) state.getMenu();
            
            if(s.equals("BACK")) {
                goBack();
                
                userLog("Signed out!");
                currentUser = null;
            }
            
            if(s.equals("ADMIN")) {
                addState( State.DEBUG );
            }
            
            return;
        }
    }

    public void tagSwipe(String serial) {
        if( state.equals( State.AWAIT_USER ) ) {
            //addState( State.USER_MENU );

            currentUser = runner.getUserByTag(serial);
            
            if(currentUser == null) {
                out("User not recognized");
                
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
    }
    
    public void userLog(String s) {
        if(currentUser == null) return;
        
        currentUser.log(s);
        
        out("USER " + currentUser.getName() + " "  + s);
    }
}
