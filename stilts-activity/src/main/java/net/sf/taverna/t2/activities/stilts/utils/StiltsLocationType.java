package net.sf.taverna.t2.activities.stilts.utils;

/**
 * Specifies where a new column should be inserted.
 * 
 * @author christian
 */
public enum StiltsLocationType implements DescribableInterface{

    AFTER("Insert the new column after the named column"),
    BEFORE("Insert the new column before the named column"), 
    END("Insert the new column after the last column");

   private final String description;
     
    private StiltsLocationType(String description){
         this.description = description;
    }
    
    public String getDescription() {
        return description;
    }

}
