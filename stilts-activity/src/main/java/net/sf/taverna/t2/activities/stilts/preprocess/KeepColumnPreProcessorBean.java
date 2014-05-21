package net.sf.taverna.t2.activities.stilts.preprocess;

/**
 * Preprocess that keeps one or more columns, deleting any others not specified
 * <p>
 * Semantic Sugar for {@link UserSpecifiedPreProcessorBean UserSpecifiedPreProcessorBean} 
 * as it helps the user specify the column(s) to keep.
 * <p>
 * Based on
 *{@link http://www.star.bris.ac.uk/~mbt/stilts/sun256/sun256.html#keepcols}
 * 
 * 
 * @author Christian Brenninkmeijer
 * @version 1.0
 */
public class KeepColumnPreProcessorBean extends ColumnListPreProcessorBean{
    
    /**
     * Serialization constructor
     */
    public KeepColumnPreProcessorBean(){  
    }

    
    /**
     * Parameterized constructor to help ensure the right information is passed in.
     * <p>
     * This method allows a single call to create the Bean and populate all the required fields.
     * This has the same effect as calling the unparameterized constructor and then all the setters.
     * None of the parameters should be null.
     * 
     * @param columnList 
     */
    public KeepColumnPreProcessorBean(String columnList){  
        super(columnList);
    }

    @Override
    public String retrieveStilsCommand() {
        return "cmd=keepcols \"" + this.getColumnList() + "\"";
    }

    @Override
    public String title() {
        return "Keep only specified column(s)";
    }
  
}
