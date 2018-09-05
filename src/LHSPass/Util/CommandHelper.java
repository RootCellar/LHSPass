package Util;

public class CommandHelper
{
    public static final char[] allowedSpecialChars = { ',', '.', '?', '!', '@', '#', '$', '%', '&', '*', '(', ')', '[', ']', '{', '}',
                                                       ':', ';', '/', '\\', '\"', '\'', ' ' };
    /**
     * Used to ensure that names of users and files have the correct length,
     * and that they only use letters and numbers
     */
    public static boolean isValid(String s, int min, int max, boolean special) {
        boolean isValid;
        char validChar;
        
        if(s.length()<min) {
            return false;
        }
        if(s.length()>max) {
            return false;
        }
        
        charLoop: for(int i=0; i<s.length(); i++) {
            
            if( (byte) s.charAt(i) == 0) continue;
            
            if(special) {
                
                for(int k=0; k<allowedSpecialChars.length; k++) {
                    if( s.charAt(i) == allowedSpecialChars[k] ) continue charLoop;
                }
                
            }
            
            isValid=false;
            validChar='a';
            for(int w=0; w<26; w++) {
                if(s.substring(i,i+1).equals(String.valueOf(validChar))) {
                    isValid=true;
                }
                validChar++;
            }

            validChar='A';
            for(int w=0; w<26; w++) {
                if(s.substring(i,i+1).equals(String.valueOf(validChar))) {
                    isValid=true;
                }
                validChar++;
            }

            validChar='0';
            for(int w=0; w<10; w++) {
                if(s.substring(i,i+1).equals(String.valueOf(validChar))) {
                    isValid=true;
                }
                validChar++;
            }
            
            if(isValid==false) {
                return false;
            }
        }
        
        return true;
    }
}