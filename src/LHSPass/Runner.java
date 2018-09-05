import java.util.ArrayList;

import Logging.*;
import GUI.*;
import Util.*;

import java.io.*;
import java.util.Date;

public class Runner implements Runnable, InputUser
{
    /**
     * Might need some enums here at some point, for things like:
     * 
     * - Menus
     * - Program States
     * - RFID Reader States (could be in separate class that handles RFID input)
     * 
     */

    Logger runLog = new Logger("Runner","Log");

    boolean going = false;
    int waitTime = 100;

    Terminal term = new Terminal();

    long startTime = System.nanoTime();

    ArrayList<User> userList = new ArrayList<User>();
    ArrayList<String> commands = new ArrayList<String>();

    FileHandler schRead = new FileHandler(this);
    
    UserHandler handler = new UserHandler(this);

    Schedule sch;
    Period period;
    int periodNum = -2;
    String schPath = "Schedules";
    String schName = "Normal";
    String day = "";

    public static void main(String args[]) {
        Runner r = new Runner();
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
        term.setUser(this);

        term.setVisible(false);

        openDebug();

        userList = UserFileHandler.readAll();
    }

    public void start() {
        setup();

        new Thread(this).start();
        going = true;
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
        while(going) {
            try{
                if(!going) break;

                tick();
                
                runCommands();

                Thread.sleep(waitTime);
            }catch(Exception e) {
                e.printStackTrace();

                out("EXCEPTION IN RUNNER THREAD");
            }
        }

        out("Exited loop");

        runLog.close();
        term.setVisible(false);
        handler.closeAll();

        inputText("/saveusers");
        
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
                //out(period.toString());
            }
            else period = null;
        }
        
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
        }

        if(s.equals("/stop")) {
            stop();
            out("Stopping...");
        }

        if(s.equals("/list")) {
            out(userList.size() + " users");

            for(int i=0; i<userList.size(); i++) {
                User current = userList.get(i);

                out(current.getName() + " " + current.getTag());
            }
        }

        if(s.equals("/saveusers")) {
            out("Saving the world...");
            for(int i=0; i<userList.size(); i++) {
                userList.get(i).save();
            }
            out("Users saved.");
        }

        if(Command.is("/createuser",s)) {
            ArrayList<String> args = Command.getArgs(s);

            if(args.size() < 3) {
                out("Too few arguments!");
                return;
            }

            User u = new User( args.get(1), args.get(2) );
            userList.add(u);
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
    }

    public void setupSchedule(String name) {
        new File(schPath +"/"+name).mkdir();
        schName = name;
        sch = schRead.readSchedule(schPath+"/"+schName);
        day = schRead.getDayByInt( new Date().getDay() );
    }
}