import java.util.*;
public class Schedule
{
    ArrayList<Period> periods = new ArrayList<Period>();
    int currentPeriod = -1;
    public void tick() {
        
        currentPeriod = getPeriod();
        
    }
    
    public int getPeriod() {
        Date date = new Date();
        int hour = date.getHours();
        int minute = date.getMinutes();
        
        //Figures out what period it is
        for(int i=0; i<periods.size(); i++) {
            if( isPeriod(i, hour, minute) ) return i;
        }
        
        return -1;
    }
    
    //Finds out if it is the given period with the given time
    public boolean isPeriod(int num, int h, int m) {
        Period p = periods.get(num);
        if(h>p.startHour && h<p.endHour) {
            return true;
        }
        
        if(p.endHour == p.startHour) {
            if(h == p.endHour && (m>=p.startMin && m<p.endMin) ) return true;
            else return false;
        }
            
        if(h == p.endHour) {
            if(m<p.endMin) return true;
            else return false;
        }
        
        if(h == p.startHour) {
            if(m>=p.startMin) return true;
            else return false;
        }
        
        return false;
    }
    
    public String toString() {
        String ret = "SCHEDULE: \n";
        for(int i=0; i<periods.size(); i++) {
            Period p = periods.get(i);
            ret+= p.name+" START: "+p.startHour+":"+p.startMin+" END:"+p.endHour+":"+p.endMin;
            ret+="\n";
        }
        return ret;
    }
}