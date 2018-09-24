/**
 * The Grand all-powerful enum of Permissions
 * 
 * Simple Enum API
 * ---------------
 * 
 * ordinal() - get order of definition
 * 
 * name() - get defined name
 */

public enum Permission
{
    DEV("DEV"),
    USER_RESTROOM("USER"),
    USER_LOCKER("USER"),
    USER_WATER("USER"),
    USER_OTHER("USER"),
    ADMIN_LOCK("ADMIN"),
    ADMIN_UNLOCK("ADMIN"),
    ADMIN_OVERRIDE("ADMIN"),
    ADMIN_TAG_CHECK("ADMIN"),
    ADMIN_USER_VIEW("ADMIN"),
    ADMIN_USER_MODIFY("ADMIN"),
    ADMIN_SCHEDULE_SET("ADMIN"),
    ADMIN_PERM_ADD("ADMIN"),
    ADMIN_PERM_REMOVE("ADMIN"),
    ADMIN_USER_CREATE("ADMIN"),
    ADMIN_USER_DELETE("ADMIN"),
    ;
    
    String name;
    String type;
    int id;
    Permission(String t) {
        name = name();
        type = t;
        id = ordinal();
    }
    
    public String getName() {
        return name;
    }
    
    public String getType() {
        return type;
    }
    
    public int getId() {
        return id;
    }
    
    public boolean equals(Permission p) {
        if( id == p.getId() ) return true;
        
        return false;
    }
    
    public String toString() {
        return "name: " + getName() + " type: " + getType() + " id: "+getId();
    }
}   