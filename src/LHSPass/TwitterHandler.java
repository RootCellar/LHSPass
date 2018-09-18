import twitter4j.*;
import twitter4j.auth.AccessToken;

import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;

import Logging.Logger;

public class TwitterHandler
{

    private String consumerKey = "You thought";
    private String consumerSecret = "I was";
    private String accessToken = "Going to";
    private String accessTokenSecret = "Tell you?";

    TwitterFactory twitterFactory;
    Twitter twitter;

    boolean working = false;

    boolean setup = false;

    Runner runner = null;

    Logger logger = new Logger("TwitterHandler", "Tweets");

    public TwitterHandler(Runner r) {
        runner = r;

        try{
            twitterFactory = new TwitterFactory();
            twitter = twitterFactory.getInstance();
        }catch(Throwable e) {
            e.printStackTrace();
        }

        readInfo();
    }

    public void readInfo() {

        out("Starting read...");

        try{
            new File("TwitterSecret.txt").createNewFile();

            out("Ensured file is available.");
        }catch(Exception e) { //Can't create file
            return;
        }

        File f = new File("TwitterSecret.txt");

        Scanner scanny;

        try{
            scanny = new Scanner(f); //Good ol' Scanny
        }catch(Exception e) { //File not found
            return;
        }

        ArrayList<String> stuff = new ArrayList<String>();

        out("Starting read...");

        while(scanny.hasNextLine()) {
            stuff.add( scanny.nextLine() );
        }

        out("Finished read");

        if(stuff.size() < 4) {
            out("Not enough info");
            return;
        }

        out("Got the info. Setting keys...");

        consumerKey = stuff.get(0);
        consumerSecret = stuff.get(1);
        accessToken = stuff.get(2);
        accessTokenSecret = stuff.get(3);

        scanny.close();

        out("Setting up twitter connection...");

        setup();
        working = true;
    }

    public void setup() {

        try{
            twitter.setOAuthConsumer( consumerKey, consumerSecret );
            twitter.setOAuthAccessToken( new AccessToken( accessToken, accessTokenSecret ) );
        }catch(Throwable e) {
            setup = false;
            return;
        }

        setup = true;
    }

    public void out(String s) {
        //System.out.println("[TWITTER] " + s);
        logger.log("[TWITTER] " + s);

        if(runner != null) {
            runner.out("[TWITTER] " + s);
        }
    }

    public int randomize() {
        return (int) ( ( Math.random() ) * 10000);
    }

    public void send(String s) {
        if(!setup) {
            out("CAN'T SEND: NOT SET UP");
            return;
        }

        out("Attempting to send: " + s);
        try{
            StatusUpdate update = new StatusUpdate( randomize() + ": " + s );
            Status status = twitter.updateStatus( update );

            working = true;

            out("Successfully sent: " + s);
        }catch(Exception e) {
            working = false;
            out("Failed");

            for(int i = 0; i < e.getStackTrace().length; i++) {
                out( "at " + e.getStackTrace()[i].toString() );
            }
            //e.printStackTrace();
        }
    }

}