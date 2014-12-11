package net.sf.taverna.t2.activities.table.utils;

/**
 * Specifies where a new column should be inserted.
 * 
 * @author christian
 */
public enum TableLocationType implements DescribableInterface{

    AFTER("After reference column","Insert the new column after the named column"),
    BEFORE("Before reference column","Insert the new column before the named column"), 
    END("End of row","Insert the new column after the last column");

    private final String userName;
    private final String description;
     
    private TableLocationType(String userName, String description){
        this.userName = userName;
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }

    @Override
    public String toString(){
        return userName;
    }

}
