package Logging;

import java.io.*;
import java.util.Date;

/**
 * Easy way of logging output and other infos
 * Supports logging to files and files inside of other folders
 * If the file can't be written to, this class will automatically try to reopen the file
 */
public class Logger
{
    private File f;
    private FileWriter toLog;
    private boolean namedFile = false;
    private String fileName = "";
    private String fileFolder = "";
    public boolean canWrite = true;
    public boolean open = false;
    
    public Logger() {
        open();
    }
    
    public Logger(String f, String n) {
        fileName = n;
        fileFolder = f;
        namedFile = true;
        open();
    }
    
    public void reopen() {
        close();
        open();
    }
    
    public void open() {
        new File("Logs").mkdir();
        if(namedFile && !fileFolder.equals("") ) new File("Logs/" + fileFolder).mkdirs();
        
        if(!namedFile) f = new File( "Logs/" + getNameByDate( new Date() ) + ".txt" );
        else if( fileFolder.equals("") ) f = new File("Logs/" + fileName + ".txt");
        else f = new File("Logs/" + fileFolder + "/" + fileName + ".txt");
        
        try{
            toLog = new FileWriter(f,true);
        }catch(Exception e) {
            canWrite=false;
        }
        
        canWrite = true;
        open = true;
        
        log("Opened Log!");
    }

    public void close() {
        try{
            canWrite = false;
            open = false;
            toLog.close();
        }catch(Exception e) {

        }
    }

    public void log(String s) {
        if(!canWrite || !open) return;
        
        try{
            toLog.write(getTimeAsString( new Date() )+" "+s);
            toLog.write(System.getProperty("line.separator"));
            toLog.flush();
        }catch(Exception e) {
            canWrite=false;
            reopen();
        }
    }

    public static String getNameByDate(Date date) {
        return (date.getMonth()+1)+"-"+date.getDate()+"-"+(date.getYear()+1900);
    }

    public static String getTimeAsString(Date date) {
        String ind = "AM";
        int h = date.getHours();
        if(h>12) {
            h-=12;
            ind= "PM";
        }
        
        int m2 = date.getMinutes();
        String m = m2+"";
        if(m2<10) m="0"+m2;
        
        int s2 = date.getSeconds();
        String s = s2+"";
        if(s2<10) s="0"+s2;
        
        return (date.getMonth()+1)+"/"+date.getDate()+"/"+(date.getYear()+1900)+" "+h+":"+m+":"+s+" "+ind;
    }
    
    public static String getTimeAsString() {
        return getTimeAsString( new Date() );
    }   
    
    public static String getDayByInt(int n) {
        String toReturn = "";

        if(n==0) toReturn = "Sunday";
        if(n==1) toReturn = "Monday";
        if(n==2) toReturn = "Tuesday";
        if(n==3) toReturn = "Wednesday";
        if(n==4) toReturn = "Thursday";
        if(n==5) toReturn = "Friday";
        if(n==6) toReturn = "Saturday";

        return toReturn;
    }
    
    //Returns a string representing a given date
    public static String getTimeAndDate(Date date) {
        //Date date = new Date();
        int yy = date.getYear()+1900;
        int mm = date.getMonth()+1;
        int d = date.getDate();
        int day = date.getDay();
        int h = date.getHours();
        int m = date.getMinutes();
        int s = date.getSeconds();

        String sString = s+"";
        if(s<10) sString = "0"+sString;
        String mString = m+"";
        if(m<10) mString = "0"+mString;

        String ret = "";
        ret+=getDayByInt(day)+", ";
        ret+=mm+"/"+d+"/"+yy+" ";
        ret+=h+":"+mString+":"+sString;

        return ret;
    }
    
    public static String getTimeAndDate() {
        return getTimeAndDate( new Date() );
    }
}