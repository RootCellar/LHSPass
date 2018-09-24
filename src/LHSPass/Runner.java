import java.util.ArrayList;

import Logging.*;
import GUI.*;
import Util.*;

import java.io.*;
import java.util.Date;

public class Runner implements Runnable, InputUser
{   
    
    long startTime = System.nanoTime();
    
    Logger runLog = new Logger("Runner","Log");

    boolean going = false;
    int waitTime = 100;

    Terminal term = new Terminal();
    DataDisplay disp = new DataDisplay(20, 2);

    ArrayList<User> userList = new ArrayList<User>();
    ArrayList<String> commands = new ArrayList<String>();

    FileHandler schRead = new FileHandler(this);

    UserHandler handler = new UserHandler(this);
    
    SerialReader sr = new SerialReader( handler );

    Schedule sch;
    Period period;
    int periodNum = -2;
    String schPath = "Schedules";
    String schName = "Normal";
    String day = "";
    
    boolean DEBUG = false;

    public static void main(String args[]) {
        Runner r = new Runner();
        
        if(args.length>0 && args[0].equals("DEBUG")) {
            r.DEBUG = true;
            r.out("WARNING: PROGRAM IS IN DEBUG MODE");
        }
        
        r.start();
    }

    public Runner() {

    }

    public User getUserByTag(String t) {
        for( int i = 0; i < userList.size(); i++) {
            if( userList.get(i).getTag().equals(t) ) return userList.get(i);
        }

        return null;
    }

    public void openDebug() {
        term.setVisible(true);
    }

    public void setup() {
        out("Setting up...");
        
        term.setUser(this);

        term.setVisible(false);

        openDebug();

        userList = UserFileHandler.readAll();
        
        out("Done setting up");
    }

    public void start() {
        setup();
        
        out("Starting main thread...");

        going = true;
        new Thread(this).start();
    }

    public void stop() {
        going = false;
    }

    public void out(String o) {
        runLog.log(o);
        System.out.println(o);
        term.write(o);
    }

    public synchronized void run() {
        out("Starting loop...");
        out("Type '/help' for a list of commands");
        
        int exceptionsInRow = 0; //Keep track of the number interrupted loops in a row
        
        //handler.getTwitterHandler().send( "Program starting. Entering main loop..."); //Debug tweets are annoying
        
        if(DEBUG) out("WARNING: DEBUG MODE ON");
        
        out("Took " + ( (double) (System.nanoTime() - startTime) ) / 1000000000 + "s to start");
        
        loop: while(going) {
            try{
                if(!going) break loop;

                tick();

                runCommands();

                wait( waitTime );
                
                exceptionsInRow = 0; //No exceptions this loop. Reset counter
            }catch(Exception e) { //If there are too many exceptions in a row, close the program due to constant problems.
                e.printStackTrace();

                out("EXCEPTION IN RUNNER THREAD");
                
                wait(1000); //Let's not spam the terminal with exceptions
                
                exceptionsInRow++;
                if(exceptionsInRow > 10) {
                    out("Too many exceptions in a row. Closing...");
                    wait(3000);
                    break loop;
                }
                
            }
        }
        
        //handler.getTwitterHandler().send( "Program shutting down..." ); //Debug tweets are annoying

        out("Exited loop");

        runLog.close();
        term.setVisible(false);
        handler.closeAll();

        //inputText("/saveusers"); //Useless. What was I thinking? Input a command that doesn't execute unless a call is made to runCommands()? Let's just do it the other way
        saveUsers();

        out("Done");

        System.exit(0);
    }

    public void tick() {
        if(sch != null) sch.tick();

        if( ! day.equals( schRead.getDayByInt( new Date().getDay() ) ) ) setupSchedule(schName);

        if(periodNum != sch.currentPeriod) {
            out("Period Changed: " + periodNum + " --> " + sch.currentPeriod);
            periodNum = sch.currentPeriod;
            if(periodNum >= 0) {
                period = sch.periods.get(periodNum);
                //out(period.toString()); //Pointer, anyone?
            }
            else period = null;
        }

        //Data Display
        disp.setText(0, ( ( System.nanoTime() - startTime ) / 1000000000 ) + "s" );
        disp.setText(1, "PeriodNum: " + periodNum );
        if( period != null ) disp.setText(2, "Period: " + period.name );
        else disp.setText(2, "Period: NULL" );
        disp.setText(3, "Schedule: " + schName );
        disp.setText(4, "Day: " + day );
        //End Data Display

        handler.tick();
    }

    public void runCommands() {
        while(commands.size() > 0) {
            inputCommand( commands.remove(0) );
        }
    }

    public void inputText(String s) {
        commands.add(s);
    }

    public void inputCommand(String s) {
        out("[INPUT] " + s);

        if(s.equals("/help")) {
            out("/stop - stops program the correct way");
            out("/list - list loaded users");
            out("/saveusers - saves all users. Also done when program stops by normal means");

            out("/createuser <first name> <last name> - create new user by name");
            out("/setusertag <first name> <last name> <tag> - set a user's tag");

            out("/giveperm <first name> <last name> <permission> - give a user a permission");
            out("/removeperm <first name> <last name> <permission> - revoke a user's permission");

            out("/givepermbytype <first name> <last name> <permission> - give a user a permission by type");
            out("/removepermbytype <first name> <last name> <permission> - revoke a user's permission by type");

            out("/viewuser <first name> <last name> - view a user and their permissions");
            out("/listperms - list available permissions");

            out("/sch - view schedule");

            out("/state <name> - set GUI state");
            out("/states - display GUI states");

            out("/simswipe <String> - simulate a tag swipe by string");
            out("/statequeue - list the menu queue");
            
            out("/tweet <String> - send a tweet");
            
            out("/setusertitle <First Name> <Last Name> <String> - set a user's title");
            out("/admins - get admin list");
        }

        if(s.equals("/stop")) {
            stop();
            out("Stopping...");
        }

        if(s.equals("/list")) {
            out(userList.size() + " users");

            for(int i=0; i<userList.size(); i++) {
                User current = userList.get(i);

                out(current.getDisplayName() + " " + current.getTag());
            }
        }

        if(s.equals("/saveusers")) {
            saveUsers();
        }

        if(Command.is("/createuser",s)) {
            ArrayList<String> args = Command.getArgs(s);

            if(args.size() < 3) {
                out("Too few arguments!");
                return;
            }

            User u = new User( args.get(1), args.get(2) );
            userList.add(u);
            
            //u.getPermissionList().addByType("USER"); //There's always a better way
            u.giveUserPerms(); //Fix newly created users having no permissions
            
            u.save();

            out("User created!");
        }

        if(Command.is("/setusertag",s)) {
            ArrayList<String> args = Command.getArgs(s);

            if(args.size() < 4) {
                out("Too few arguments!");
                return;
            }

            User u = null;
            for(int i=0; i<userList.size(); i++) {
                if( userList.get(i).getName().equals( args.get(1) + " " + args.get(2) ) ) {
                    u = userList.get(i);
                }
            }

            if(u == null) {
                out("User not found");
                return;
            }

            u.setTag(args.get(3));

            out("User " + u.getName() + "'s tag set to " + args.get(3) );
        }

        if(Command.is("/giveperm",s)) {
            ArrayList<String> args = Command.getArgs(s);

            if(args.size() < 4) {
                out("Too few arguments!");
                return;
            }

            User u = null;
            for(int i=0; i<userList.size(); i++) {
                if( userList.get(i).getName().equals( args.get(1) + " " + args.get(2) ) ) {
                    u = userList.get(i);
                }
            }

            if(u == null) {
                out("User not found");
                return;
            }

            u.getPermissionList().addByName( args.get(3) );

            out("User " + u.getName() + " given permission " + args.get(3) );
        }

        if(Command.is("/removeperm",s)) {
            ArrayList<String> args = Command.getArgs(s);

            if(args.size() < 4) {
                out("Too few arguments!");
                return;
            }

            User u = null;
            for(int i=0; i<userList.size(); i++) {
                if( userList.get(i).getName().equals( args.get(1) + " " + args.get(2) ) ) {
                    u = userList.get(i);
                }
            }

            if(u == null) {
                out("User not found");
                return;
            }

            u.getPermissionList().removeByName( args.get(3) );

            out("User " + u.getName() + " lost permission " + args.get(3) );
        }

        if(Command.is("/givepermbytype",s)) {
            ArrayList<String> args = Command.getArgs(s);

            if(args.size() < 4) {
                out("Too few arguments!");
                return;
            }

            User u = null;
            for(int i=0; i<userList.size(); i++) {
                if( userList.get(i).getName().equals( args.get(1) + " " + args.get(2) ) ) {
                    u = userList.get(i);
                }
            }

            if(u == null) {
                out("User not found");
                return;
            }

            u.getPermissionList().addByType( args.get(3) );

            out("User " + u.getName() + " given permissions of type " + args.get(3) );
        }

        if(Command.is("/removepermbytype",s)) {
            ArrayList<String> args = Command.getArgs(s);

            if(args.size() < 4) {
                out("Too few arguments!");
                return;
            }

            User u = null;
            for(int i=0; i<userList.size(); i++) {
                if( userList.get(i).getName().equals( args.get(1) + " " + args.get(2) ) ) {
                    u = userList.get(i);
                }
            }

            if(u == null) {
                out("User not found");
                return;
            }

            u.getPermissionList().removeByType( args.get(3) );

            out("User " + u.getName() + " lost permissions of type " + args.get(3) );
        }

        if(Command.is("/viewuser",s)) {
            ArrayList<String> args = Command.getArgs(s);

            if(args.size() < 3) {
                out("Too few arguments!");
                return;
            }

            User u = null;
            for(int i=0; i<userList.size(); i++) {
                if( userList.get(i).getName().equals( args.get(1) + " " + args.get(2) ) ) {
                    u = userList.get(i);
                }
            }

            if(u == null) {
                out("User not found");
                return;
            }

            out(u.toString());
        }

        if(Command.is("/listperms",s)) {
            out("Listing permissions...");
            int x = 0;
            for( Permission p : Permission.values() ) {
                out(p.toString());
                x++;
            }
            out(x + " permissions");
        }

        if(Command.is("/sch",s)) {
            out(sch.toString());
        }

        if(Command.is("/state",s)) {
            ArrayList<String> args = Command.getArgs(s);
            if(args.size() < 2) {
                out("Too few arguments!");
                return;
            }

            handler.setState(args.get(1));
        }

        if(Command.is("/states",s)) {
            for( UserHandler.State state : UserHandler.State.values() ) {
                out("STATE: " + state.getName() + ", " + state.getId() );
            }
        }

        if(Command.is("/simswipe",s)) {
            ArrayList<String> args = Command.getArgs(s);
            if(args.size() < 2) {
                out("Too few arguments!");
                return;
            }

            handler.tagSwipe( args.get(1) );
        }

        if(Command.is("/statequeue",s)) {
            out("Menu Queue Size: " + handler.getQueue().size() );
            for( int i = 0; i < handler.getQueue().size(); i++) {
                out( handler.getQueue().get(i).toString() );
            }
        }
        
        if(Command.is("/tweet",s)) {
            String msg = s.substring( 7, s.length() );
            out(msg);
            
            handler.getTwitterHandler().send(msg);
        }
        
        if(Command.is("/setusertitle",s)) {
            ArrayList<String> args = Command.getArgs(s);

            if(args.size() < 4) {
                out("Too few arguments!");
                return;
            }

            User u = null;
            for(int i=0; i<userList.size(); i++) {
                if( userList.get(i).getName().equals( args.get(1) + " " + args.get(2) ) ) {
                    u = userList.get(i);
                }
            }

            if(u == null) {
                out("User not found");
                return;
            }
            
            String toSet = args.get(3);
            
            for(int i=4; i<args.size(); i++) {
                toSet += " "  + args.get(i);
            }

            u.setTitle(toSet);

            out("User " + u.getDisplayName() + "'s title set to " + toSet );
        }
        
        if(s.equals("/admins")) {
            int x = 0;
            out(userList.size() + " users in total");

            for(int i=0; i<userList.size(); i++) {
                User current = userList.get(i);

                if(current.getPermissionList().hasType("ADMIN")) {
                    out(current.getDisplayName() + " " + current.getTag());
                    x++;
                }
            }
            
            out(x + " admins");
        }
    }

    public void setupSchedule(String name) {
        new File(schPath +"/"+name).mkdir();
        schName = name;
        sch = schRead.readSchedule(schPath+"/"+schName);
        day = schRead.getDayByInt( new Date().getDay() );
    }

    public void wait(int n) {
        
        try{
            Thread.sleep(n);
        }catch(Exception e) {
            return;
        }

    }
    
    public void saveUsers() {
        out("Saving the world...");
        for(int i=0; i<userList.size(); i++) {
            userList.get(i).save();
        }
        out("Users saved.");
    }
}