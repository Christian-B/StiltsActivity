package net.sf.taverna.t2.activities.stilts.preprocess;

/**
 *
 * @author christian
 */
public class DeleteColumnPreProcessorBean extends ColumnListPreProcessorBean{
    
    /**
     * Serialization constructor
     */
    public DeleteColumnPreProcessorBean(){  
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
    public DeleteColumnPreProcessorBean(String columnList){  
        super(columnList);
    }

    @Override
    public String retrieveStilsCommand() {
        return "cmd=delcols \"" + this.getColumnList() + "\"";
    }

      
}
