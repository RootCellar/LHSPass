package Util;

import java.util.ArrayList;
public class Command
{
    public static ArrayList<String> getArgs(String s) {

        ArrayList<String> s2 = new ArrayList<String>();
        while(s.length()>0) {
            try{
                s2.add(s.substring(0,s.indexOf(" ")));
                s=s.substring(s.indexOf(" ")+1);
            }catch(Exception e) {
                s2.add(s);
                s="";
                //e.printStackTrace();
            }
        }
        return s2;

    }

    public static int countSpaces(String s) {
        int n = 0;
        for(int i=0; i<s.length(); i++) {
            if(s.substring(i,i+1).equals(" ")) n++;

        }
        return n;
    }

    public static boolean is(String c, String e) {
        /*
        if(e.length()<c.length()) return false;

        if(e.substring(0,c.length()).equals(c)) {
            return true;
        }

        return false;
        */
       
       ArrayList<String> args = getArgs(e);
       if(args.size() < 1) return false;
       
       if( args.get(0).equals(c) ) return true;
       
       return false;
    }
}