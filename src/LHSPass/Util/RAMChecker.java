package Util;

public class RAMChecker 
{
    long totalMem = 0;
    long freeMem = 0;
    long maxMem = 0;
    long used = 0;
    
    double percentUsed = 0;
    
    public void calc() {
        totalMem = Runtime.getRuntime().totalMemory();
        freeMem = Runtime.getRuntime().freeMemory();
        maxMem = Runtime.getRuntime().maxMemory();
        
        used = maxMem - freeMem;
        
        percentUsed = ((double) used) / ((double) maxMem);
        percentUsed *= 100;
    }
}