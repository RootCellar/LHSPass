import java.util.*;
import java.io.*;
public class FileHandler
{
    Runner runner;
    String prefix = "[File Reader] ";
    public FileHandler(Runner r) {
        runner = r;
    }
    
    public void out(String s) {
        runner.out(prefix+s);
    }

    //Reads the schedule and sets it up
    public Schedule readSchedule(String folderPath) {
        new File(folderPath).mkdir();
        
        out("Reading Schedule..."+folderPath);
        Schedule schedule = new Schedule();

        setupSchedule(folderPath); //Make sure that it contains the proper files before reading
        
        File file;
        String day = getDayByInt( new Date().getDay() );

        out(day);
        out(folderPath + "/" + day + ".txt");

        file = new File(folderPath + "/" + day + ".txt");
        out(file.getName());
        if(!file.canRead()) {
            out("File for today not found. Creating new file...");
            try{
                file.createNewFile();
            }catch(Exception e) {
                e.printStackTrace();
                //return schedule;
            }
        }

        Scanner in;
        try{
            in = new Scanner(file);
        }catch(Exception e) {
            System.out.println("Failed to read "+day);
            out("Failed to read "+day);
            return schedule;
        }

        out("Starting read...");
        
        //While there is more stuff to read, read it, create a period, put the period in the schedule
        while(in.hasNextLine()) {
            out("Reading...");
            
            String name = "NOTFOUND";
            String start = "NOTFOUND";
            String end = "NOTFOUND";

            String input = in.nextLine();

            name = input.substring(0, input.indexOf(" ") );
            input = input.substring(input.indexOf(" ") + 1);

            start = input.substring(0, input.indexOf(" ") );
            input = input.substring(input.indexOf(" ") + 1);

            end = input;

            Period p = new Period(name, start, end);
            schedule.periods.add(p);
        }
        in.close();
        out("Read Finished");
        return schedule;
    }

    public String getDayByInt(int n) {
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

    //Create the files if they aren't there
    public void setupSchedule(String folderPath) {

        File file;

        try{

            file = new File(folderPath + "/Sunday.txt");
            if(!file.canRead()) file.createNewFile();
            
            file = new File(folderPath + "/Monday.txt");
            if(!file.canRead()) file.createNewFile();

            file = new File(folderPath + "/Tuesday.txt");
            if(!file.canRead()) file.createNewFile();

            file = new File(folderPath + "/Wednesday.txt");
            if(!file.canRead()) file.createNewFile();

            file = new File(folderPath + "/Thursday.txt");
            if(!file.canRead()) file.createNewFile();

            file = new File(folderPath + "/Friday.txt");
            if(!file.canRead()) file.createNewFile();
            
            file = new File(folderPath + "/Saturday.txt");
            if(!file.canRead()) file.createNewFile();

        }catch(Exception e) {
            e.printStackTrace();
        }

    }
}