public class Period
{
    String name;
    
    int startHour = 0;
    int startMin = 0;
    
    int endHour = 0;
    int endMin = 0;
    
    //Creates the period by looking for a name and start and end times in the arguments
    public Period(String n, String start, String end) {
        name = n;
        
        String startTime = start.substring(0, start.indexOf(":") );
        start = start.substring( start.indexOf(":") + 1 );
        startHour = Integer.parseInt(startTime);
        startTime = start;
        startMin = Integer.parseInt(startTime);
        
        String endTime = end.substring(0, end.indexOf(":") );
        end = end.substring( end.indexOf(":") + 1 );
        endHour = Integer.parseInt(endTime);
        endTime = end;
        endMin = Integer.parseInt(endTime);
    }
}