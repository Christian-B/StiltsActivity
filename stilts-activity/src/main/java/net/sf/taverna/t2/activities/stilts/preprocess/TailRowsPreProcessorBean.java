package net.sf.taverna.t2.activities.stilts.preprocess;

/**
 *
 * @author christian
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

      
}
