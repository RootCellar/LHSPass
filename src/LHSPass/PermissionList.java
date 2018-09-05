import java.util.ArrayList;

/**
 * The easy way of giving users permissions and keeping track of them
 */
public class PermissionList
{
    ArrayList<Permission> perms = new ArrayList<Permission>();
    
    public PermissionList() {
        
    }
    
    public void addAllPerms() {
        for( Permission p : Permission.values() ) {
            add(p);
        }
    }
    
    public void addByName(String n) {
        for( Permission p : Permission.values() ) {
            if( p.getName().equals(n) ) add(p);
        }
    }
    
    public void addByType(String n) {
        for( Permission p : Permission.values() ) {
            if( p.getType().equals(n) ) add(p);
        }
    }
    
    public void add(Permission p) {
        if( ! has(p) ) perms.add(p);
    }
    
    public void remove(Permission p) {
        perms.remove(p);
    }
    
    public void removeByName(String n) {
        for(int i=0; i<perms.size(); i++) {
            if( n.equals( perms.get(i).getName() ) ) {
                perms.remove(i);
                return;
            }
        }
    }
    
    public void removeByType(String n) {
        for( Permission p : Permission.values() ) {
            if( n.equals( p.getType() ) ) removeByName( p.getName() );
        }
    }
    
    public boolean hasType(String n) {
        for(int i=0; i<perms.size(); i++) {
            if( n.equals( perms.get(i).getType() ) || perms.get(i).getName().equals("DEV") ) return true;
        }
        
        return false;
    }
    
    public boolean has(String n) {
        for(int i=0; i<perms.size(); i++) {
            if( n.equals( perms.get(i).getName() ) || perms.get(i).getName().equals("DEV") ) return true;
        }
        
        return false;
    }
    
    public boolean has(Permission p) {
        if( has("DEV") ) return true;
        
        for(int i=0; i<perms.size(); i++) {
            if( p.equals( perms.get(i) ) ) return true;
        }
        
        return false;
    }
    
    public int size() {
        return perms.size();
    }
    
    public String toString() {
        String toRet = "{ \n";
        for(int i=0; i<perms.size(); i++) {
            toRet += "[ " + perms.get(i).toString() + " ]; \n";
        }
        
        toRet+= "};";
        
        return toRet;
    }
}