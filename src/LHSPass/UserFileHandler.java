import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;

public class UserFileHandler
{

    static final String PREFIX = "[User File Handler] ";
    static final String DIR = "Users";
    static final boolean DEBUG = false;
    //static final String SEPARATOR = "\n";
    static final String SEPARATOR = System.getProperty("line.separator");

    static {
        new File(DIR).mkdir();
    }

    boolean working = true;

    User user;
    File path;
    File permFile;
    File tagFile;
    File titleFile;

    public UserFileHandler(User u) {
        user = u;

        //makeFiles(); //Not necessary, called when reading files

        read();
    }

    public static void out(String s) {
        if(DEBUG) System.out.println(PREFIX + s);
    }
    
    public static void out2(String s) {
        System.out.println(PREFIX + s);
    }

    public String getPath() {
        return path.getPath();
    }

    public void makeFiles() {
        path = new File( DIR + "/" + user.getFirstName() + "_" + user.getLastName() );
        path.mkdir();

        permFile = new File( getPath() + "/" + "perms.txt");
        tagFile = new File( getPath() + "/" + "tag.txt");
        titleFile = new File( getPath() + "/" + "title.txt");

        try{

            if( !permFile.exists() ) permFile.createNewFile();
            if( !tagFile.exists() ) tagFile.createNewFile();
            if( !titleFile.exists() ) titleFile.createNewFile();

        }catch(Exception e) {
            working = false;
            return;
        }

    }

    public void save() {
        if(!working) return;

        makeFiles();
        
        /*
         * Write permissions
         */

        FileWriter writer;

        try{
            writer = new FileWriter(permFile);
        }catch(Exception e) {
            working = false;
            return;
        }
        
        out2("Saving " + user.getName());

        for( Permission p : Permission.values() ) {
            try{

                if( user.getPermissionList().has(p) ) {
                    writer.write( p.getName() + SEPARATOR);
                    writer.flush();
                }

            }catch(Exception e) {
                working = false;
                e.printStackTrace();
                return;
            }
        }

        try{
            writer.close();
        }catch(Exception e) {

        }
        
        /*
         * Write tag
         */
        
        try{
            writer = new FileWriter(tagFile);
        }catch(Exception e) {
            working = false;
            return;
        }
        
        try{
            writer.write( user.getTag() );
            writer.flush();
        }catch(Exception e) {
            working = false;
            return;
        }
        
        try{
            writer.close();
        }catch(Exception e) {

        }
        
        out2("User saved.");
        
        //Yay it worked
    }

    public void read() {
        if(!working) return;

        makeFiles();

        /*
         * Read permissions first
         */

        Scanner reader;

        try{
            reader = new Scanner(permFile);
        }catch(Exception e) {
            working = false;
            return;
        }

        out("Starting read...");

        while(reader.hasNextLine()) {

            out("Reading...");

            String in = reader.nextLine();

            out("Read "+in);

            if( in==null ) continue;

            out("Not null");

            user.getPermissionList().addByName(in);
        }

        reader.close();

        out("Closed File");

        /*
         * Read tag
         */

        try{
            reader = new Scanner(tagFile);
        }catch(Exception e) {
            working = false;
            return;
        }

        out("Reading tag information...");

        if(reader.hasNext()) {
            String in = reader.nextLine();

            out("Read "+in);

            user.setTag(in);
        }

        reader.close();
        
        try{
            reader = new Scanner(titleFile);
        }catch(Exception e) {
            working = false;
            return;
        }
        
        if(reader.hasNext()) {
            String in = reader.nextLine();

            out("Read Title: " + in);

            user.setTitle(in);
        }

        reader.close();
        
        out2("Read user "+user.getName() + " ( " + user.getTag() + " ) with " + user.getPermissionList().size() + " permissions");

        out("Finished");
    }

    public static ArrayList<User> readAll() {
        out2("Starting...");
        
        ArrayList<User> toRet = new ArrayList<User>();
        
        File[] userFolders = new File(DIR).listFiles();
        
        out2("Found " + userFolders.length + " user folders. Starting to do the stuff...");
        
        folderLoop: for(int i=0; i<userFolders.length; i++) {
            out( userFolders[i].getName() );
            
            String[] name = userFolders[i].getName().split("_");
            
            if(name.length < 2) {
                out("INVALID NAME: " + userFolders[i].getName() );
                continue folderLoop;
            }
            
            User u = new User( name[0], name[1] );
            
            out(u.getName());
            
            toRet.add(u);
            
        }
        
        out2("Ended up with " + toRet.size() + " users");

        return toRet;
    }
    
    public static void createUser(String f, String l, String t) {
        User u = new User(f,l);
        u.setTag(t);
        u.getPermissionList().addByType("USER");
        u.save();
    }
}