package net.sf.taverna.t2.activities.table.preprocess;

/**
 * Preprocess that keeps the tail/ last rows
 * <p>
 * Semantic Sugar for {@link UserSpecifiedPreProcessorBean UserSpecifiedPreProcessorBean} 
 * as it helps the user specify the rows to keep, deleting any others.
 * <p>
 * Based on
 *{@link http://www.star.bris.ac.uk/~mbt/stilts/sun256/sun256.html#tail}
 * 
 * 
 * @author Christian Brenninkmeijer
 * @version 1.0
 */
public class TailRowsPreProcessorBean extends RowCountPreProcessorBean{
    
    /**
     * Serialization constructor
     */
    public TailRowsPreProcessorBean(){  
    }

    /**
     * Parameterized constructor to help ensure the right information is passed in.
     * <p>
     * This method allows a single call to create the Bean and populate all the required fields.
     * This has the same effect as calling the unparameterized constructor and then all the setters.
     * None of the parameters should be null.
     * 
     * @param rowCount 
     */
    public TailRowsPreProcessorBean(int rowCount){  
        super(rowCount);
    }

    @Override
    public String retrieveStilsCommand() {
        return "cmd=tail " + this.getNumberOfRows();
    }

    @Override
    public String title() {
        return "Keep only the last X rows";
    }
     
}
