import Logging.Logger;

public class User
{
    private PermissionList permList = new PermissionList();
    private String firstName;
    private String lastName;
    private String title = "USER";
    private UserFileHandler fileHandler;
    private String tag = "0";
    private Logger logger = null;
    
    public User(String n, String l) {
        firstName = n;
        lastName = l;
        
        fileHandler = new UserFileHandler(this);
        
        logger = new Logger("UserLogs", firstName + "_" + lastName);
    }
    
    public void log(String s) {
        if(logger == null) return;
        
        logger.log(s);
    }
    
    public void save() {fileHandler.save();}
    
    public void setTitle(String t) {
        title = t;
    }
    
    public void setTag(String t) {
        tag = t;
    }
    
    public String getTag() {
        return tag;
    }
    
    public void giveUserPerms() {
        permList.addByType("USER");
    }
    
    public void giveAdminPerms() {
        permList.addByType("ADMIN");
        giveUserPerms();
    }
    
    public String getFirstName() {return firstName;}
    
    public String getLastName() {return lastName;}
    
    public String getName() {return firstName+" "+lastName;}
    
    public String getTitle() {return title;}
    
    public String getDisplayName() {
        return "[" + getTitle() + "] " + getName();
    }
    
    public PermissionList getPermissionList() {
        return permList;
    }
    
    public String toString() {
        String toRet = "name: " + getName() + " ";
        
        toRet += "tag: " + getTag() + " ";
        
        toRet += "perms: " + permList.toString();
        
        return toRet;
    }
}